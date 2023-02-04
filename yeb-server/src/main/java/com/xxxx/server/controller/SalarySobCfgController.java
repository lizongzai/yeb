package com.xxxx.server.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.pojo.Salary;
import com.xxxx.server.service.IEmployeeService;
import com.xxxx.server.service.ISalaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.Builder.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "SalarySobCfgController")
@RequestMapping("/salary/sobcfg")
public class SalarySobCfgController {

  @Autowired
  private ISalaryService salaryService;
  @Autowired
  private IEmployeeService employeeService;

  @ApiOperation(value = "获取所有工资账套")
  @GetMapping("/salaries")
  public List<Salary> getAllSalary() {
    return salaryService.list();
  }

  @ApiOperation(value = "获取所有员工工资账套")
  @GetMapping("/")
  public RespPageBean getEmployeeWithSalary(@RequestParam(defaultValue = "1") Integer currentPage,
      @RequestParam(defaultValue = "10") Integer size) {
    return employeeService.getEmployeeWithSalary(currentPage, size);
  }

  @ApiOperation(value = "更新员工工资账套")
  @PutMapping("/")
  public RespBean updateEmployeeSalary(Integer sid, Integer eid) {
    if (employeeService.update(new UpdateWrapper<Employee>().set("salaryId", sid).eq("id", eid))) {
      return RespBean.success("更新成功!");
    }
    return RespBean.error("更新失败!");
  }

}
