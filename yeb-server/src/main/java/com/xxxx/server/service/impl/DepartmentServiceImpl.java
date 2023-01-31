package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.DepartmentMapper;
import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IDepartmentService;
import java.util.List;
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
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements
    IDepartmentService {

  @Autowired
  @Resource
  private DepartmentMapper departmentMapper;

  /**
   * 获取所有部门
   *
   * @return
   */
  @Override
  public List<Department> getAllDepartments() {
    return departmentMapper.getAllDepartments(-1);
  }

  /**
   * 添加部门
   *
   * @param department
   * @return
   */
  @Override
  public RespBean addDepartment(Department department) {
    //设置默认启用部门
    department.setEnabled(true);
    //添加部门
    departmentMapper.addDepartment(department);
    //判断添加部门是否成功,并返回结果
    if (department.getResult() == 1) {
      return RespBean.success("添加成功!", department);
    }
    return RespBean.error("添加失败!");
  }

  /**
   * 删除部门
   *
   * @param id
   * @return
   */
  @Override
  public RespBean deleteDep(Integer id) {
    Department department = new Department();
    department.setId(id);
    departmentMapper.deleteDep(department);
    if (department.getResult() == -2) {
      return RespBean.error("该部门下还有子部门,删除失败!");
    } else if (department.getResult() == -1) {
      return RespBean.error("该部门下还有员工,删除失败!");
    } else if (department.getResult() == 1) {
      RespBean.success("删除成功!");
    }
    return RespBean.error("删除失败!");

//    Department department = new Department();
//    department.setId(id);
//    departmentMapper.deleteDep(department);
//    if (-2 == department.getResult()) {
//      return RespBean.error("该部门下还有子部门, 删除失败!");
//    } else if (-1 == department.getResult()) {
//      return RespBean.error("该部门下还有员工,删除失败!");
//    } else if (1 == department.getResult()) {
//      return RespBean.success("删除成功!");
//    }
//    return RespBean.error("删除失败!");
  }
}
