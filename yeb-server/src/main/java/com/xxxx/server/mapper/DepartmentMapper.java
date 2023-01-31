package com.xxxx.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxxx.server.pojo.Department;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
public interface DepartmentMapper extends BaseMapper<Department> {

  /**
   * 获取所有部门
   *
   * @return
   */
  List<Department> getAllDepartments(Integer parentId);

  /**
   * 添加部门
   *
   * @param department
   */
  void addDepartment(Department department);

  /**
   * 删除部门
   *
   * @param department
   */
  void deleteDep(Department department);
}
