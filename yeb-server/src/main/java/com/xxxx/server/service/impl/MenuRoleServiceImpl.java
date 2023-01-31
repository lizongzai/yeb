package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.MenuRoleMapper;
import com.xxxx.server.pojo.MenuRole;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IMenuRoleService;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements
    IMenuRoleService {

  @Autowired
  @Resource
  private MenuRoleMapper menuRoleMapper;

  /**
   * @param rid
   * @param mids
   * @return
   * @Description //TODO 更新角色菜单,先删除角色，然后重新添加该角色对应的菜单
   */
  @Override
  public RespBean updateMenuRole(Integer rid, Integer[] mids) {

    //根据rid删除菜单角色
    menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid", rid));
    if (mids == null || mids.length == 0) {
      return RespBean.success("更新成功!");
    }

    //添加菜单角色
    Integer result = menuRoleMapper.insertRecord(rid, mids);
    if (result == mids.length) {
      return RespBean.success("更新成功!");
    }
    return RespBean.success("更新失败!");
  }
}
