package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
public interface IEmployeeService extends IService<Employee> {

  /**
   * 获取所有员工(分页查询)
   *
   * @param currentPage
   * @param size
   * @param employee
   * @param beginDateScope
   * @return
   */
  RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee,
      LocalDate beginDateScope);

  /**
   * 获取工号
   *
   * @return
   */
  RespBean getMaxWorkId();

  /**
   * 添加员工
   *
   * @param employee
   * @return
   */
  RespBean addEmployee(Employee employee);

  /**
   * 导出员工数据
   *
   * @param id
   * @return
   */
  List<Employee> getEmployeeInfo(Integer id);

  /**
   * 获取所有员工工资账套
   *
   * @param currentPage
   * @param size
   * @return
   */
  RespPageBean getEmployeeWithSalary(Integer currentPage, Integer size);
}
