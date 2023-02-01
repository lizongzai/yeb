package com.xxxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.pojo.Role;
import java.util.List;
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
   * 登录之后返回token
   *
   * @param username
   * @param password
   * @param code
   * @param request
   * @return
   */
  RespBean login(String username, String password, String code, HttpServletRequest request);

  /**
   * 获取当前登录用户的信息
   *
   * @param username
   * @return
   */
  Admin getAdminByUserName(String username);

  /**
   * 获取所有操作员
   *
   * @param keywords
   * @return
   */
  List<Admin> getAllAdmins(String keywords);
}
