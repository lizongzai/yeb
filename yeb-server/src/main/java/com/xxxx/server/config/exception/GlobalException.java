package com.xxxx.server.config.exception;

import com.xxxx.server.pojo.RespBean;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author lizongzai
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalException {
  @ExceptionHandler({SQLException.class})
  public RespBean mySqlException(SQLException sqlException) {
    if (sqlException instanceof SQLIntegrityConstraintViolationException) {
      return RespBean.error("该数据有关联数据约束,操作失败!");
    }
    return RespBean.error("数据库异常,操作失败!");
  }
}
