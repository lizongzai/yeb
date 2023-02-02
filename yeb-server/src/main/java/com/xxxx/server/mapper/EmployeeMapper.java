package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxxx.server.pojo.Employee;
import java.time.LocalDate;
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
public interface EmployeeMapper extends BaseMapper<Employee> {

  /**
   * 获取所有员工(分页查询)
   *
   * @param page
   * @param employee
   * @param beginDateScope
   * @return
   */
  IPage<Employee> getEmployeeByPage(Page<Employee> page, @Param("employee") Employee employee,
      @Param("beginDateScope") LocalDate beginDateScope);

  /**
   * 导出员工数据
   *
   * @param id
   * @return
   */
  List<Employee> getEmployeeInfo(@Param("id") Integer id);
}
