package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.MenuRole;
import com.xxxx.server.pojo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
public interface IMenuRoleService extends IService<MenuRole> {

  /**
   * 更新角色菜单
   * @param rid
   * @param mids
   * @return
   */
  RespBean updateMenuRole(Integer rid, Integer[] mids);
}
