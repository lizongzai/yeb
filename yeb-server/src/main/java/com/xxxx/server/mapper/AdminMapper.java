package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Admin;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
public interface AdminMapper extends BaseMapper<Admin> {

  /**
   * 获取所有操作员
   *
   * @param id
   * @param keywords
   * @return
   */
  List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);
}
