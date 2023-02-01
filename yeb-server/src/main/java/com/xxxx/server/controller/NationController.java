package com.xxxx.server.controller;


import com.xxxx.server.pojo.Nation;
import com.xxxx.server.service.INationService;
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
@Api(tags = "NationController")
@RequestMapping("/employee/basic")
public class NationController {

  @Autowired
  private INationService nationService;

  @ApiOperation(value = "获取所有民族")
  @GetMapping("/nation")
  public List<Nation> getAllNations() {
    return nationService.list();
  }

}
