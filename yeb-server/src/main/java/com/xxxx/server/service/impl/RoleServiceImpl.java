package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.AdminRoleMapper;
import com.xxxx.server.mapper.MenuRoleMapper;
import com.xxxx.server.mapper.RoleMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.AdminRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IRoleService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

  @Autowired
  @Resource
  private RoleMapper roleMapper;
  @Autowired
  private AdminRoleMapper adminRoleMapper;


  /**
   * 根据用户ID获取角色列表
   *
   * @param adminId
   * @return
   */
  @Override
  public List<Role> getRolesByAdminId(Integer adminId) {
    return roleMapper.getRolesByAdminId(adminId);
  }

}
