package com.xxxx.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.server.pojo.RespBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *当未登录或token失效时访问接口时，自定义返回的结果
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    RespBean bean = RespBean.error("尚未登录, 请登录!");
    bean.setCode(401);
    out.write(new ObjectMapper().writeValueAsString(bean));
    out.flush();
    out.close();
  }
}
