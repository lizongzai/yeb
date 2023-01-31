package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.DepartmentMapper;
import com.xxxx.server.pojo.Department;
import com.xxxx.server.service.IDepartmentService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

  @Autowired
  @Resource
  private DepartmentMapper departmentMapper;
  /**
   * 获取所有部门
   * @return
   */
  @Override
  public List<Department> getAllDepartments() {
    return departmentMapper.getAllDepartments(-1);
  }
}
