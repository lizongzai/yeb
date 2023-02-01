package com.xxxx.server.controller;


import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@Api(tags = "RoleController")
@RequestMapping("/system/basic/permission")
public class RoleController {

  @Autowired
  private IRoleService roleService;

  @ApiOperation(value = "查询所有角色")
  @GetMapping("/")
  public List<Role> getAllRoles() {
    return roleService.list();
  }

  @ApiOperation(value = "添加角色")
  @PostMapping("/")
  public RespBean addRole(@RequestBody Role role) {
    if (!role.getName().startsWith("ROLE_")) {
      role.setName("ROLE_" + role.getName());
    }

    if (roleService.save(role)) {
      return RespBean.success("添加成功!");
    }
    return RespBean.error("添加失败!");
  }

  @ApiOperation(value = "删除角色")
  @DeleteMapping("/{id}")
  public RespBean removeRoleById(@PathVariable Integer id) {
    if (roleService.removeById(id)) {
      return RespBean.success("删除成功!");
    }
    return RespBean.error("删除失败!");
  }

}
