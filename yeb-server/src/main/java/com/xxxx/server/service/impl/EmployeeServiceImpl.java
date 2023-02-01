package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.EmployeeMapper;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.IEmployeeService;
import java.time.LocalDate;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements
    IEmployeeService {

  @Autowired
  @Resource
  private EmployeeMapper employeeMapper;
  /**
   * 获取所有员工(分页查询)
   *
   * @param currentPage
   * @param size
   * @param employee
   * @param beginDateScope
   * @return
   */
  @Override
  public RespPageBean getEmployeeByPage(Integer currentPage, Integer size, Employee employee,
      LocalDate beginDateScope) {
    //开启分页查询
    Page<Employee> page = new Page<>(currentPage,size);
    IPage<Employee> employeeIPage = employeeMapper.getEmployeeByPage(page,employee,beginDateScope);
    RespPageBean respPageBean = new RespPageBean(employeeIPage.getTotal(),employeeIPage.getRecords());
    return respPageBean;
  }
}
