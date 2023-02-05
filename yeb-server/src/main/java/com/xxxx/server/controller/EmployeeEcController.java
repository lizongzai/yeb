package com.xxxx.server.controller;


import com.xxxx.server.pojo.EmployeeEc;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IEmployeeEcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@Api(tags = "EmployeeEcController")
@RequestMapping("/personnel/ec/employee-ec")
public class EmployeeEcController {

  @Autowired
  private IEmployeeEcService employeeEcService;

  @ApiOperation(value = "获取所有奖惩信息")
  @GetMapping("/")
  public List<EmployeeEc> getAllEmployeeEc() {
    return employeeEcService.list();
  }

  @ApiOperation(value = "添加奖惩")
  @PostMapping("/")
  public RespBean addEmployeeEc(@RequestBody EmployeeEc employeeEc) {
    employeeEc.setEcDate(LocalDate.now());
    if (employeeEcService.save(employeeEc)) {
      return RespBean.success("添加成功!");
    }
    return RespBean.error("添加失败!");
  }

  @ApiOperation(value = "更新奖惩")
  @PutMapping("/")
  public RespBean updateEmployeeEc(@RequestBody EmployeeEc employeeEc) {
    if (employeeEcService.updateById(employeeEc)) {
      return RespBean.success("修改成功");
    }
    return RespBean.error("修改失败");
  }

  @ApiOperation(value = "删除奖惩")
  @DeleteMapping("/{id}")
  public RespBean removeEmployeeEcById(@PathVariable Integer id) {
    if (employeeEcService.removeById(id)) {
      return RespBean.success("删除成功!");
    }
    return RespBean.error("删除失败!");
  }

  @ApiOperation(value = "批量删除奖惩")
  @DeleteMapping("/")
  private RespBean deleteEmployeeEcs(Integer[] ids){
    if (employeeEcService.removeByIds(Arrays.asList(ids))) {
      return RespBean.success("删除成功!");
    }
    return RespBean.error("删除失败!");
  }


}
