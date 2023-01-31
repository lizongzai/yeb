package com.xxxx.server.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.MenuRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IMenuRoleService;
import com.xxxx.server.service.IMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 权限控制
 */
@RestController
@Api(tags = "PermissionController")
@RequestMapping("/system/basic/permission")
public class PermissionController {

  @Autowired
  private IMenuService menuService;
  @Autowired
  private IMenuRoleService menuRoleService;

  @ApiOperation(value = "查询所有菜单")
  @GetMapping("/menus")
  public List<Menu> getAllMenus() {
    return menuService.getAllMenus();
  }

  @ApiOperation(value = "根据角色ID查询菜单ID")
  @GetMapping("/mid/rid")
  public List<Integer> getMidByRid(Integer rid) {
    return menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid)).stream()
        .map(MenuRole::getMid).collect( //根据角色ID查询菜单ID,并转成数组
            Collectors.toList());
  }

  @ApiOperation(value = "更新角色菜单")
  @PutMapping("/role")
  public RespBean updateMenuRole(Integer rid, Integer[] mids) {
    return menuRoleService.updateMenuRole(rid, mids);
  }

}
