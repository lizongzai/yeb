package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.config.security.JwtTokenUtil;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

  @Autowired
  private UserDetailsService userDetailsService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;
  @Value("${jwt.tokenHead}")
  private String tokenHead;
  @Autowired
  @Resource
  private AdminMapper adminMapper;

  /**
   * 登录之后返回token
   *
   * @param username
   * @param password
   * @param code
   * @param request
   * @return
   */
  @Override
  public RespBean login(String username, String password, String code, HttpServletRequest request) {
    //验证码
    String captcha = (String) request.getSession().getAttribute("captcha");
    //验证码为空，或者忽略大小写并且匹配不上
    if (StringUtils.isEmpty(captcha) || !captcha.equalsIgnoreCase(code)) {
      return RespBean.error("验证码输入错误, 请重新输入验证码!");
    }

    //登录
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (username == null && passwordEncoder.matches(password, userDetails.getPassword())) {
      return RespBean.error("用户名或密码不正确!");
    }

    //判断账号是否被禁用
    if (!userDetails.isEnabled()) {
      return RespBean.error("账号被禁用,请联系管理员!");
    }

    //更新security登录用户对象
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    //获取token
    String token = jwtTokenUtil.generateToken(userDetails);
    Map<String, Object> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    tokenMap.put("tokenHead", tokenHead);
    return RespBean.success("登录成功!", tokenMap);
  }

  /**
   * 获取当前登录用户的信息
   *
   * @param username
   * @return
   */
  @Override
  public Admin getAdminByUserName(String username) {
    return adminMapper.selectOne(
        new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
  }
}
