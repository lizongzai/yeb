package com.xxxx.server.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "HelloController")
public class HelloController {

  @ApiOperation(value = "心跳测试")
  @GetMapping("/hello")
  public String hello() {
    return "hello world";
  }

  @GetMapping("/employee/basic/hello")
  public String hello2() {
    return "/employee/basic/hello";
  }

  @GetMapping("/employee/advanced/hello")
  public String hello3() {
    return "/employee/advanced/hello";
  }

}
