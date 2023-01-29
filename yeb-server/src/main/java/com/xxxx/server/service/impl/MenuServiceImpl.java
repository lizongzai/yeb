package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.MenuMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IMenuService;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

  @Autowired
  @Resource
  private MenuMapper menuMapper;

  @Autowired
  private RedisTemplate redisTemplate;

  /**
   * 根据用户id获取菜单列表
   *
   * @return
   */
  @Override
  public List<Menu> getMenusByAdminId() {
    //获取用户ID
    Integer adminId = ((Admin) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
    //从redis读取菜单数据
    List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
    //若redis缓存为空,则从数据库获取菜单数据
    if (CollectionUtils.isEmpty(menus)) {
      menus = menuMapper.getMenusByAdminId(adminId);
      //将菜单数据设置在redis中
      valueOperations.set("menu_" + adminId,menus);
    }
    //返回结果
    return menus;
  }
}
