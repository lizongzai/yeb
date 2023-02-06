package com.xxxx.server.controller;


import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 个人中心
 *
 * @author lizongzai
 * @since 1.0.0
 */
@RestController
public class AdminInfoController {

  @Autowired
  private IAdminService adminService;

  @ApiOperation(value = "更新当前用户信息")
  @PutMapping("/admin/info")
  public RespBean updateAdminInfo(@RequestBody Admin admin, Authentication authentication) {
    if (adminService.updateById(admin)) {
      //获取认证token
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          admin, null, authentication.getAuthorities());
      //设置全局上下文
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      return RespBean.success("更新成功!");
    }
    return RespBean.error("更新失败!");
  }

  @ApiOperation(value = "更用用户密码")
  @PutMapping("/admin/pass")
  public RespBean updateAdminPassword(@RequestBody Map<String, Object> info) {
    String oldPass = info.get("oldPass").toString();
    String pass = info.get("pass").toString();
    Integer adminId = (Integer) info.get("adminId");
    return adminService.updateAdminPassword(oldPass, pass, adminId);
  }

}
