package com.xxxx.server.controller;


import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IDepartmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
@RestController
@Api(tags = "DepartmentController")
@RequestMapping("/system/basic/department")
public class DepartmentController {

  @Autowired
  private IDepartmentService departmentService;

  @ApiOperation(value = "获取所有部门")
  @GetMapping("/")
  public List<Department> getAllDepartments() {
    return departmentService.getAllDepartments();
  }

  @ApiOperation(value = "添加部门")
  @PostMapping("/")
  public RespBean addDepartment(@RequestBody Department department) {
    return departmentService.addDepartment(department);
  }

  @ApiOperation(value = "删除部门")
  @DeleteMapping("/{id}")
  public RespBean deleteDepartment(@PathVariable Integer id) {
    return departmentService.deleteDepartment(id);
  }

}
