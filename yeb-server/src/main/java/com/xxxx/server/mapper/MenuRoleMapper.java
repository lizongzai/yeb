package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

  /**
   * 更新角色菜单
   *
   * @param rid
   * @param mids
   * @return
   */
  Integer insertRecord(@Param("rid") Integer rid, @Param("mids") Integer[] mids);
}
