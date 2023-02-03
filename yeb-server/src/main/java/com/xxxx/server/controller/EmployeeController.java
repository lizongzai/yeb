package com.xxxx.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.IEmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
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

  @ApiOperation(value = "更新员工")
  @PutMapping("/")
  public RespBean updateEmployee(@RequestBody Employee employee) {
    if (employeeService.updateById(employee)) {
      return RespBean.success("更新成功!");
    }
    return RespBean.error("更新失败!");
  }

  @ApiOperation(value = "删除员工")
  @DeleteMapping("/{id}")
  public RespBean removeEmployeeById(@PathVariable Integer id) {
    if (employeeService.removeById(id)) {
      return RespBean.success("删除成功!");
    }
    return RespBean.error("删除失败!");
  }

  @ApiOperation(value = "导出员工数据")
  @GetMapping(value = "/export", produces = "application/octet-stream")
  public void exportEmployees(HttpServletResponse response) {

    //查询数据
    List<Employee> employeeList = employeeService.getEmployeeInfo(null);

    //实例化Excel导出参数
    ExportParams params = new ExportParams("员工表", "员工表", ExcelType.HSSF);

    //使用ExcelExportUtil工具导出,类型为工作簿
    Workbook workbook = ExcelExportUtil.exportExcel(params, Employee.class, employeeList);

    OutputStream out = null;
    try {
      //流形式传输
      response.setHeader("content-type", "application/octet-stream");
      //防止乱码
      response.setHeader("content-disposition","attachment;filename=" + URLEncoder.encode("员工表.xls", "utf-8"));
      //使用流导出文件
      out = response.getOutputStream();
      //保存文件,仅供下载使用
      workbook.write(out);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (out != null) {
        try {
          //关闭输出流
          out.flush();
          out.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }
}
