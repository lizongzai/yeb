package com.xxxx.server.config.rabbitmq;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxxx.server.config.constant.MailConstants;
import com.xxxx.server.pojo.MailLog;
import com.xxxx.server.service.IMailLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description //TODO RabbitMQ配置
 * 1.准备消息队列和交换机，并通过路由键将消息队列与交换机进行绑定
 * 2.注入缓存连接工厂，并将缓存工厂放入RabbitTemplate模板
 * 3.消息确认回调,确认消息是否达到broker
 * 4.消息失败回调，比如router不到Queue队列时回调
 *
 * @Author lizongzai
 * @Since 1.0.0
 */
@Configuration
public class RabbitMQConfig {

  private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConfig.class);
  @Autowired
  private CachingConnectionFactory connectionFactory;
  @Autowired
  private IMailLogService mailLogService;

  @Bean
  public RabbitTemplate rabbitTemplate() {

    //开启confirms确认模式
    connectionFactory.setPublisherConfirms(true);

    //将缓存连接工厂注入RabbitMQ模板
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

    /**
     * 消息确认回调, 确认消息是否达到broker
     * data:消息唯一标识
     * ack:确认结果
     * cause:失败原因
     * @return
     */
    rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
      String msgId = data.getId();
      System.out.println("RabbitMQConfig: msgId = " + msgId);
      if (ack) {
        LOGGER.info("{}==========>消息发送成功", msgId);
        mailLogService.update(new UpdateWrapper<MailLog>().set("status", 1).eq("msgid", msgId));
      } else {
        LOGGER.info("{}==========>消息发送失败", msgId);
      }
    });

    /**
     * 消息失败回调，比如router不到Queue队列时回调
     * msg:消息主题
     * repCode:响应码
     * repText:响应描述
     * exchange:交换机
     * routingKey:路由键
     */
    rabbitTemplate.setReturnCallback((msg, repCode, repText, exchange, routingKey) -> {
      LOGGER.error("{}==================消息发送到Queue队列时失败", msg.getBody());
    });
    return rabbitTemplate;
  }

  /**
   * 消息队列
   *
   * @return
   */
  @Bean
  public Queue queue() {
//    return new Queue(MailConstants.MAIL_QUEUE_NAME, true, false, false);
    return new Queue(MailConstants.MAIL_QUEUE_NAME);
  }

  /**
   * 交换机
   *
   * @return
   */
  @Bean
  public DirectExchange directExchange() {
//    return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME, true, false);
    return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME);
  }

  /**
   * 使用路由键将消息队列与交换机进行绑定
   *
   * @return
   */
  public Binding binding() {
    return BindingBuilder.bind(queue()).to(directExchange())
        .with(MailConstants.MAIL_ROUTING_KEY_NAME);
  }

}
