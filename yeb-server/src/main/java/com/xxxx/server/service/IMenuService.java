package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.Role;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
public interface IMenuService extends IService<Menu> {

  /**
   * 根据用户id获取菜单列表
   * @return
   */
  List<Menu> getMenusByAdminId();

  /**
   * 根据角色获取菜单列表
   * @return
   */
  List<Menu> getMenusWithRole();

}
