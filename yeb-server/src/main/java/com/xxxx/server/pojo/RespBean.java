package com.xxxx.server.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RespBean对象", description = "")
public class RespBean {

  private long code;
  private String message;
  private Object object;

  /**
   * 成功返回
   *
   * @param message
   * @return
   */
  public static RespBean success(String message) {
    return new RespBean(200, message, null);
  }

  /**
   * 成功发回
   *
   * @param message
   * @param object
   * @return
   */
  public static RespBean success(String message, Object object) {
    return new RespBean(200, message, object);
  }

  /**
   * 失败返回
   *
   * @param message
   * @return
   */
  public static RespBean error(String message) {
    return new RespBean(500, message, null);
  }

  /**
   * 失败返回
   *
   * @param message
   * @param object
   * @return
   */
  public static RespBean error(String message, Object object) {
    return new RespBean(500, message, object);
  }

}

/**
 * 公共返回对象
 *
 * @Author lizongzai
 * @Date 2023/01/26 10:36
 * @since 1.0.0
 */
