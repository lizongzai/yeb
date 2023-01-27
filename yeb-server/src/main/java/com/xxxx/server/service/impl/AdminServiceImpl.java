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
   * 用户登录之后返回token
   *
   * @param username
   * @param password
   * @param request
   * @return
   */
  @Override
  public RespBean login(String username, String password, HttpServletRequest request) {

    //登录
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    if (username == null || !passwordEncoder.matches(password, userDetails.getPassword())) {
      return RespBean.error("用户名或密码不正确!");
    }

    //判断账号是否被禁止
    if (!userDetails.isEnabled()) {
      return RespBean.error("账号被禁止,请联系管理员!");
    }

    //更新security登录用户对象
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    //生成token
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
  public Admin getAdminByUsername(String username) {
    return adminMapper.selectOne(
        new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
  }
}
