package com.xxxx.mail;


import com.xxxx.server.config.constant.MailConstants;
import com.xxxx.server.pojo.Employee;
import java.util.Date;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * 消息接收者
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Component
public class MailReceiver {

  private static final Logger LOGGER = LoggerFactory.getLogger(MailReceiver.class);
  //邮件发送
  @Autowired
  private JavaMailSender javaMailSender;
  //邮件配置
  @Autowired
  private MailProperties mailProperties;
  //邮件引擎
  @Autowired
  private TemplateEngine templateEngine;
  //Redis缓存数据库
  private RedisTemplate redisTemplate;

  //接收者监听
  @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
  public void handler(Employee employee) {
    //创建邮件消息队列
    MimeMessage message = javaMailSender.createMimeMessage();
    //设置utf-8或GBK编码，否则邮件会有乱码，true表示为multipart邮件
    //MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
    MimeMessageHelper helper = new MimeMessageHelper(message);
    try {
      //发件人
      helper.setFrom(mailProperties.getUsername());
      //收件人
      helper.setTo(employee.getEmail());
      //主题
      helper.setSubject("入职欢迎邮件");
      //发送日期
      helper.setSentDate(new Date());
      //发送内容
      Context context = new Context();
      //员工名称
      context.setVariable("name", employee.getName());
      //员工职位
      context.setVariable("posName", employee.getPosition().getName());
      //员工职称
      context.setVariable("joblevelName", employee.getJoblevel().getName());
      //所属部门
      context.setVariable("departmentName", employee.getDepartment().getName());
      //通过templateEngine获取mail模板，并将邮件放入模板，最后生成mail
      String mail = templateEngine.process("mail", context);
      //确认邮件模板样式
      helper.setText(mail, true);
      //发送邮件
      javaMailSender.send(message);
    } catch (Exception e) {
      LOGGER.error("MailReceiver + 邮件发送失败========{}", e.getMessage());
    }
  }

}
