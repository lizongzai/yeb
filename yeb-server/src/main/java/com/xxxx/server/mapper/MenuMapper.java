package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.Role;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
public interface MenuMapper extends BaseMapper<Menu> {

  /**
   * 根据用户id获取菜单列表
   * @param adminId
   * @return
   */
  List<Menu> getMenusByAdminId(Integer adminId);

  /**
   * 根据角色获取菜单列表
   * @return
   */
  List<Menu> getMenusWithRole();

  /**
   * 查询所有菜单
   * @return
   */
  List<Menu> getAllMenus();
}
