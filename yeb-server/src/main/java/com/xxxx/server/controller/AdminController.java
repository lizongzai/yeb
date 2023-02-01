package com.xxxx.server.controller;


import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IAdminRoleService;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@RestController
@Api(tags = "AdminController")
@RequestMapping("/system/admin")
public class AdminController {

  @Autowired
  private IAdminService adminService;
  @Autowired
  private IRoleService roleService;

  @ApiOperation(value = "获取所有操作员")
  @GetMapping("/")
  public List<Admin> getAllAdmins(String keywords) {
    return adminService.getAllAdmins(keywords);
  }

  @ApiOperation(value = "更新操作员")
  @PutMapping("/")
  public RespBean updateAdmin(@RequestBody Admin admin) {
    if (adminService.updateById(admin)) {
      return RespBean.success("更新成功!");
    }
    return RespBean.error("更新失败!");
  }

  @ApiOperation(value = "删除操作员")
  @DeleteMapping("/{id}")
  public RespBean removeAdminById(@PathVariable Integer id) {
    if (adminService.removeById(id)) {
      return RespBean.success("删除成功!");
    }
    return RespBean.error("删除失败!");
  }

  @ApiOperation(value = "获取所有角色")
  @GetMapping("/roles")
  public List<Role> getAllRoles() {
    return roleService.list();
  }

  @ApiOperation(value = "更新操作员角色")
  @PutMapping("/role")
  public RespBean updateAdminRole(Integer adminId, Integer[] rids) {
    return adminService.updateAdminRole(adminId, rids);
  }

}
