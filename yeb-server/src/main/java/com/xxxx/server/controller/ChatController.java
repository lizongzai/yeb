package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 前端:在线聊天
 * @author lizongzai
 * @since 1.0.0
 */
@RestController
@Api(tags = "ChatController")
@RequestMapping("/chat")
public class ChatController {

  @Autowired
  private IAdminService adminService;

  @ApiOperation(value = "获取所有操作员")
  @GetMapping("/admin")
  public List<Admin> getAllAdmins(String keywords) {
    return adminService.getAllAdmins(keywords);
  }
}
