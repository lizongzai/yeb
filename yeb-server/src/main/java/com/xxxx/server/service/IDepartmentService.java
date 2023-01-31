package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Department;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
public interface IDepartmentService extends IService<Department> {

  /**
   * 获取所有部门
   * @return
   */
  List<Department> getAllDepartments();
}
