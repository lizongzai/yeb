package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
public interface IRoleService extends IService<Role> {

  /**
   * 根据用户ID获取角色列表
   *
   * @param adminId
   * @return
   */
  List<Role> getRolesByAdminId(Integer adminId);

}
