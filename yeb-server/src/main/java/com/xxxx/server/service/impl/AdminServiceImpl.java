package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.config.security.JwtTokenUtil;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.mapper.AdminRoleMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.utils.AdminUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
  @Autowired
  @Resource
  private AdminRoleMapper adminRoleMapper;

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


  /**
   * 获取所有操作员
   *
   * @param keywords
   * @return
   */
  @Override
  public List<Admin> getAllAdmins(String keywords) {
    //获取当前登录用户ID
    //Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    return adminMapper.getAllAdmins(AdminUtils.adminUtils().getId(), keywords);
  }

  /**
   * 更新操作员角色
   *
   * @param adminId
   * @param rids
   * @return
   */
  @Override
  @Transactional
  public RespBean updateAdminRole(Integer adminId, Integer[] rids) {

    //先删除操作员角色，然后在重新添加当前用户的角色
    adminRoleMapper.delete(new QueryWrapper<AdminRole>().eq("adminId", adminId));
    if (rids == null || rids.length == 0) {
      return RespBean.success("更新成功");
    }

    //添加当前操作员的角色
    Integer result = adminRoleMapper.addAdminRole(adminId, rids);
    if (result == rids.length) {
      return RespBean.success("更新成功");
    }
    return RespBean.error("更新失败!");
  }

  /**
   * 更新用户密码
   *
   * @param oldPass
   * @param pass
   * @param adminId
   * @return
   */
  @Override
  public RespBean updateAdminPassword(String oldPass, String pass, Integer adminId) {

    //获取用户
    Admin admin = (Admin) adminMapper.selectById(adminId);
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    //判断旧密码是否正确,若输入正确则更新新密码
    if (encoder.matches(oldPass,admin.getPassword())) {
      //设置新密码
      admin.setPassword(encoder.encode(pass));
      //更新密码
      Integer result = adminMapper.updateById(admin);
      //判断密码是否更新成功
      if (result == 1) {
        return RespBean.success("更新成功!");
      }
    }
    return RespBean.error("更新失败!");
  }

}
