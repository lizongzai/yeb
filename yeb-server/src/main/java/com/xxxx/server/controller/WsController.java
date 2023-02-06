package com.xxxx.server.controller;

import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.ChatMsg;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

/**
 * 后端: Websocket连接
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Controller
public class WsController {

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @MessageMapping("/ws/chat")
  public void handleMsg(Authentication authentication, ChatMsg chatMsg) {
    //获取当前登录用户
    Admin admin = (Admin) authentication.getPrincipal();
    //发送者的用户名
    chatMsg.setFrom(admin.getUsername());
    //发送者的昵称
    chatMsg.setFromNickName(admin.getName());
    //发送的时间
    chatMsg.setDate(LocalDateTime.now());
    //参数：消息接收者,队列,发送内容
    simpMessagingTemplate.convertAndSendToUser(chatMsg.getTo(), "/queue/chat", chatMsg);

  }
}
