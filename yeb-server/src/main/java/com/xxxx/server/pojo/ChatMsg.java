package com.xxxx.server.pojo;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true) //开启链式编程
public class ChatMsg {

  //发送端
  private String from;

  //接收端
  private String to;

  //发送内容
  private String content;

  //发送时间
  private LocalDateTime date;

  //昵称
  private String fromNickName;
}
