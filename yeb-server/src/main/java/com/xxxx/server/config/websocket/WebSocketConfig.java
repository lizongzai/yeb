package com.xxxx.server.config.websocket;

import com.xxxx.server.config.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @Description WebSocket配置类
 * @Author lizongzai
 * @Since 1.0.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * 添加endpoint, 这样在网页里面可以通过websocket连接上后端服务. 也就是我们配置websocket服务地址,并且可以指定是否可以使用socketJS.
   *
   * @param registry
   */
  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    /**
     * 1.将”/ws/ep“路径注册为端点, 用户连接了这个端点就可以进行websocket通讯,并支持websocketJS
     * 2.setAllowedOrigins("*"): 允许跨域
     * 3.withSocketJS(): 支持socketJS连接
     */
    registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();
  }

  /**
   * 输入通道参数配置
   *
   * @param registration
   */
  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(new ChannelInterceptor() {

      @Value("${jwt.tokenHead}")
      private String tokenHead;
      @Autowired
      private JwtTokenUtil jwtTokenUtil;
      @Autowired
      private UserDetailsService userDetailsService;

      @Override
      public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        // 判断是否为连接，如果是， 需要获取token， 并且设置用户对象
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
          //获取token, 此外参数“Auth-Token”是前端传递过来值
          String token = accessor.getFirstNativeHeader("Auth-token");
          //判断token是否为空
          if (!StringUtils.isEmpty(token)) {
            //获取授权token
            String authToken = token.substring(tokenHead.length());
            //从token中获取用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);
            if (!StringUtils.isEmpty(username)) {
              //登录即可获取用户信息
              UserDetails userDetails = userDetailsService.loadUserByUsername(username);
              //验证token是否有效
              if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()); //userDetails.getAuthorities()表示权限列表
                //获取Security全局上下文
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                accessor.setUser(authenticationToken);
              }
            }
          }
        }
        return message;
        //return ChannelInterceptor.super.preSend(message, channel);
      }
    });
  }

  /**
   * 配置消息代理
   *
   * @param registry
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    //配置代理域, 可以配置多个,配置代理目的地前缀为"/queue",可以在配置域上向客户端推送消息
    registry.enableSimpleBroker("/queue");
  }
}
