package com.xxxx.server.controller;


import com.xxxx.server.pojo.PoliticsStatus;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IPoliticsStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
@Api(tags = "PoliticsStatusController")
@RequestMapping("/employee/basic")
public class PoliticsStatusController {

  @Autowired
  private IPoliticsStatusService politicsStatusService;

  @ApiOperation(value = "获取所有政治面貌")
  @GetMapping("/politicsStatus")
  public List<PoliticsStatus> getPoliticsStatus() {
    return politicsStatusService.list();
  }
}
