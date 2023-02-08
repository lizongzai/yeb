package com.xxxx.server.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxxx.server.config.constant.MailConstants;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.MailLog;
import com.xxxx.server.service.IEmployeeService;
import com.xxxx.server.service.IMailLogService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description //TODO 邮件发送定时任务
 * 1.先查询状态为投递失败"0",并且重试时间小于当前时间的信息
 * 2.若重试次数超过3次,则更新状态为投递失败”2“并且不在更新
 * 3.重试3次失败之后,更新基本信息(重试次数、更新时间、重试时间)
 * 4.通过员工id获取员工对象 5.发送消息
 *
 * @Author lizongzai
 * @Since 1.0.0
 */
@Component
public class MailTask {

  @Autowired
  private IMailLogService mailLogService;
  @Autowired
  private IEmployeeService employeeService;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  /**
   * 邮件发送定时任务 10秒执行一次
   */
  @Scheduled(cron = "0/10 * * * * ?")
  public void mailTask() {

    //查询状态为投递失败"0",并且重试时间小于当前时间的信息
    List<MailLog> list = mailLogService.list(
        new QueryWrapper<MailLog>().eq("status", 0).lt("tryTime", LocalDateTime.now()));

    list.forEach(mailLog -> {
      //若重试次数超过3次,则更新状态为投递失败,且不在重试
      if (3 <= mailLog.getCount()) {
        mailLogService.update(
            new UpdateWrapper<MailLog>().set("status", 2).eq("msgId", mailLog.getMsgId()));
      }

      //重试3次失败之后,更新基本信息(重试次数、更新时间、重试时间)
      mailLogService.update(new UpdateWrapper<MailLog>()
          .set("count", mailLog.getCount() + 1) //重试次数
          .set("updateTime", LocalDateTime.now())   //更新时间
          .set("tryTime", LocalDateTime.now().plusMinutes(MailConstants.MSG_TIMEOUT)) //重试时间
          .eq("msgId", mailLog.getMsgId())); //消息msgId

      //通过员工id获取员工对象
      Employee employee = employeeService.getEmployeeInfo(mailLog.getEid()).get(0);
      System.out.println("MailTask Employee = " + employee);
      System.out.println("MailTask msgId = " + mailLog.getMsgId());

      //发送消息
      rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,
          MailConstants.MAIL_ROUTING_KEY_NAME, employee, new CorrelationData(mailLog.getMsgId()));
    });
  }
}
