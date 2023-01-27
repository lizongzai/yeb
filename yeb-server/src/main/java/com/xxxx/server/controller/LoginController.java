package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminLoginParam;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录功能
 */

@RestController
@Api(tags = "LoginController")
public class LoginController {

  @Autowired
  private IAdminService adminService;

  @ApiOperation(value = "用户登录之后返回token")
  @PostMapping("/login")
  public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request) {
    return adminService.login(adminLoginParam.getUsername(), adminLoginParam.getPassword(), request);
  }

  @ApiOperation(value = "获取当前登录用户的信息")
  @GetMapping("/admin/info")
  public Admin getAdminInfo(Principal principal) {
    //判断principal对象是否为空
    if (principal == null) {
      return null;
    }
    //获取用户名
    String username = principal.getName();
    Admin admin = adminService.getAdminByUsername(username);
    admin.setPassword(null);
    return admin;

  }

  @ApiOperation(value = "退出登录")
  @PostMapping("/logout")
  public RespBean logout() {
    return RespBean.success("注销成功");
  }

}
