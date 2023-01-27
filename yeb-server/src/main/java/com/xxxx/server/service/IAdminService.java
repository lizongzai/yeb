package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lizongzai
 * @since 2023-01-19
 */
public interface IAdminService extends IService<Admin> {

  /**
   * 用户登录之后返回token
   *
   * @param username
   * @param password
   * @param request
   * @return
   */
  RespBean login(String username, String password, HttpServletRequest request);

  /**
   * 获取当前登录用户的信息
   *
   * @param username
   * @return
   */
  Admin getAdminByUsername(String username);
}
