package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.mapper.EmployeeMapper;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.RespPageBean;
import com.xxxx.server.service.IEmployeeService;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
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
    Page<Employee> page = new Page<>(currentPage, size);
    IPage<Employee> employeeIPage = employeeMapper.getEmployeeByPage(page, employee,
        beginDateScope);
    RespPageBean respPageBean = new RespPageBean(employeeIPage.getTotal(),
        employeeIPage.getRecords());
    return respPageBean;
  }

  /**
   * 获取工号
   *
   * @return
   */
  @Override
  public RespBean getMaxWorkId() {

    //获取员工Key和value
    List<Map<String, Object>> maps = employeeMapper.selectMaps(
        new QueryWrapper<Employee>().select("max(workId)"));
    System.out.println("工号 = " + maps);

    //System.out.println("maps = " + maps);
    //maps = [{max(workId)=00000100}]
    //只有一个最大值，那么get(0).get("max(workId")即可获取它的值
    //工号强制转换整型，然后再加1
    //maps.get(0).get("max(workId)"是一个Object对象，先将其转String
    //%8d，表示不足长度时，自动左补位，补位数为0
    return RespBean.success(null,
        String.format("%8d", Integer.parseInt(maps.get(0).get("max(workId)").toString()) + 1));
  }

  /**
   * 添加员工
   *
   * @param employee
   * @return
   */
  @Override
  public RespBean addEmployee(Employee employee) {
    //合同期限处理，保留两位小数
    LocalDate beginContract = employee.getBeginContract();
    LocalDate endContract = employee.getEndContract();
    //计算合同总天数,使用枚举方式
    long days = beginContract.until(endContract, ChronoUnit.DAYS);
    //算计年数，保留两位小数点
    DecimalFormat decimalFormat = new DecimalFormat("##.00");
    //强制转换Double类型
    employee.setContractTerm(Double.parseDouble(decimalFormat.format(days / 365)));
    //System.out.println("合同年数 = " + employee.getContractTerm());

    //若添加成功，则返回int类型。
    if (employeeMapper.insert(employee) == 1) {
      return RespBean.success("添加成功!");
    }
    return RespBean.error("添加失败!");
  }

}

