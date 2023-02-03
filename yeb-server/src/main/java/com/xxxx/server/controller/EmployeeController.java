package com.xxxx.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.xxxx.server.pojo.Department;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.Joblevel;
import com.xxxx.server.pojo.Nation;
import com.xxxx.server.pojo.PoliticsStatus;
import com.xxxx.server.pojo.Position;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.IDepartmentService;
import com.xxxx.server.service.IEmployeeService;
import com.xxxx.server.service.IJoblevelService;
import com.xxxx.server.service.INationService;
import com.xxxx.server.service.IPoliticsStatusService;
import com.xxxx.server.service.IPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

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
  @Autowired
  private INationService nationService;
  @Autowired
  private IPoliticsStatusService politicsStatusService;
  @Autowired
  private IPositionService positionService;
  @Autowired
  private IJoblevelService joblevelService;
  @Autowired
  private IDepartmentService departmentService;

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
      response.setHeader("content-disposition",
          "attachment;filename=" + URLEncoder.encode("员工表.xls", "utf-8"));
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

  @ApiOperation(value = "导入员工数据")
  @PostMapping("/import")
  public RespBean importEmployeeInfo(MultipartFile file) {
//
//    //准备导入参数
//    ImportParams params = new ImportParams();
//    //删除title标题行
//    params.setTitleRows(1);
//    //民族
//    List<Nation> nations = nationService.list();
//    //政治面貌
//    List<PoliticsStatus> politicsStatuses = politicsStatusService.list();
//    //职位
//    List<Position> positions = positionService.list();
//    //职称
//    List<Joblevel> joblevels = joblevelService.list();
//    //部门
//    List<Department> departments = departmentService.list();
//
//    try {
//      List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class, params);
//      list.forEach(employee -> {
//        //民族
//        employee.setNationId(nations.get(nations.indexOf(new Nation(employee.getNation().getName()))).getId());
//        //政治面貌
//        employee.setPoliticId(politicsStatuses.get(politicsStatuses.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName()))).getId());
//        //职位
//        employee.setPosId(positions.get(positions.indexOf(new Position(employee.getPosition().getName()))).getId());
//        //职称
//        employee.setJobLevelId(joblevels.get(joblevels.indexOf(new Joblevel(employee.getJoblevel().getName()))).getId());
//        //部门
//        employee.setDepartmentId(departments.get(departments.indexOf(new Department(employee.getDepartment().getName()))).getId());
//      });
//
//      if (employeeService.saveBatch(list)) {
//        return RespBean.success("导入成功!");
//      }
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//    return RespBean.error("导入失败!");
//  }

    //准备导入参数
    ImportParams params = new ImportParams();
    //去掉标题行
    params.setTitleRows(1);
    //民族
    List<Nation> nations = nationService.list();
    //政治面貌
    List<PoliticsStatus> politicsStatuses = politicsStatusService.list();
    //部门
    List<Department> departments = departmentService.list();
    //职位
    List<Position> positions = positionService.list();
    //职称
    List<Joblevel> joblevels = joblevelService.list();
    try {
      //使用流形式导入数据
      List<Employee> list = ExcelImportUtil.importExcel(file.getInputStream(), Employee.class,
          params);
      //使用循环导入多行
      list.forEach(employee -> {
        //民族id
        employee.setNationId(
            nations.get(nations.indexOf(new Nation(employee.getNation().getName()))).getId());
        //政治面貌id
        employee.setPoliticId(politicsStatuses.get(
                politicsStatuses.indexOf(new PoliticsStatus(employee.getPoliticsStatus().getName())))
            .getId());
        //部门id
        employee.setDepartmentId(
            departments.get(departments.indexOf(new Department(employee.getDepartment().getName())))
                .getId());
        //职位id
        employee.setPosId(
            positions.get(positions.indexOf(new Position(employee.getPosition().getName())))
                .getId());
        //职称id
        employee.setJobLevelId(
            joblevels.get(joblevels.indexOf(new Joblevel(employee.getJoblevel().getName())))
                .getId());
      });
      //导入数据
      if (employeeService.saveBatch(list)) {
        return RespBean.success("导入成功!");
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    return RespBean.error("导入失败!");
  }
}
