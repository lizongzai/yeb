package com.xxxx.mail;

import com.xxxx.server.config.constant.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MailApplication {

  public static void main(String[] args) {
    SpringApplication.run(MailApplication.class, args);
  }

  /**
   * 监听消息队列
   *
   * @return
   * @author lizongzai
   * @since 1.0.0
   */
  @Bean
  public Queue queue() {
    return new Queue(MailConstants.MAIL_QUEUE_NAME);
  }
}