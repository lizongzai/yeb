package com.xxxx.server.config.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 自定义Jwt授权拦截器
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
  //Jwt存储的请求头
  @Value("${jwt.tokenHeader}")
  private String tokenHeader;
  //Jwt负载中拿到开头
  @Value("${jwt.tokenHead}")
  private String tokenHead;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Autowired
  private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authHeader = request.getHeader(tokenHeader);
    //存在token
    if (authHeader != null && authHeader.startsWith(tokenHead)) {
      //获取授权token
      String authToken = authHeader.substring(tokenHead.length());
      //获取用户名
      String username = jwtTokenUtil.getUserNameFromToken(authToken);
      //token存在用户名,但是未登录
      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        //登录，若拿到userDetails则表示已登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //验证token是否有效,重新设置用户对象
        if (jwtTokenUtil.validateToken(authToken, userDetails)) {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }
    //放行所有
    filterChain.doFilter(request, response);
  }

}