package com.xxxx.server.controller;


import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@RestController
@Api(tags = "EmployeeController")
@RequestMapping("/employee/basic")
public class EmployeeController {

  @Autowired
  private IEmployeeService employeeService;

  @ApiOperation(value = "获取所有员工(分页查询)")
  @GetMapping("/")
  public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer currentPage,
      @RequestParam(defaultValue = "10") Integer size, Employee employee,
      LocalDate beginDateScope) {

    return employeeService.getEmployeeByPage(currentPage, size, employee, beginDateScope);
  }

  @ApiOperation(value = "获取工号")
  @GetMapping("/maxWorkId")
  public RespBean getMaxWorkId() {
    return employeeService.getMaxWorkId();
  }

  @ApiOperation(value = "添加员工")
  @PostMapping("/")
  public RespBean addEmployee(@RequestBody Employee employee) {
    return employeeService.addEmployee(employee);
  }



}
