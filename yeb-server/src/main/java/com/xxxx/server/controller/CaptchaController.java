package com.xxxx.server.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description //TODO 验证码
 * @Author lizongzai
 * @Since 1.0.0
 */
@RestController
@Api(tags = "CaptchaController")
public class CaptchaController {

  @Autowired
  private DefaultKaptcha defaultKaptcha;

  @ApiOperation(value = "验证码")
  @GetMapping(value = "/captcha", produces = "image/jpeg")
  public void captcha(HttpServletRequest request, HttpServletResponse response) {

    // 定义response输出类型为image/jpeg类型
    response.setDateHeader("Expires", 0);
    // Set standard HTTP/1.1 no-cache headers.
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    // Set standard HTTP/1.0 no-cache header.
    response.setHeader("Pragma", "no-cache");
    // return a jpeg
    response.setContentType("image/jpeg");

    //获取验证码文本内容
    String text = defaultKaptcha.createText();
    System.out.println("验证码内容: " + text);
    //将文本验证码内放入session
    request.getSession().setAttribute("captcha", text);
    //根据文本验证码生成图形验证码
    BufferedImage image = defaultKaptcha.createImage(text);

    ServletOutputStream outputStream = null;
    try {
      outputStream = response.getOutputStream();
      ImageIO.write(image, "jpeg", outputStream);
      outputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (outputStream != null) {
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

  }

}
