package com.xxxx.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.server.pojo.RespBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 当放翁接口无权限时,自定义返回的结果
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    response.setCharacterEncoding("utf-8");
    response.setContentType("application/json");
    PrintWriter out = response.getWriter();
    RespBean bean = RespBean.error("权限不足，请联系管理员!");
    bean.setCode(403);
    out.write(new ObjectMapper().writeValueAsString(bean));
    out.flush();
    out.close();

  }
}
