package com.xxxx.server.controller;


import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

  @Autowired
  private IMenuService menuService;

  @ApiOperation(value = "根据用户ID获取菜单列表")
  @GetMapping("/menus")
  public List<Menu> getMenusByAdminId() {
    return menuService.getMenusByAdminId();
  }

}
