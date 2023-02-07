package com.xxxx.server.config.filter;

import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.Role;
import com.xxxx.server.service.IMenuService;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

/**
 * 根据请求url分析,请求所需的角色
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Configuration
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

  @Autowired
  private IMenuService menuService;

  AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Override
  public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

    //获取请求url
    String requestUrl = ((FilterInvocation) object).getRequestUrl();
    //System.out.println("获取请求的url = " + requestUrl);
    //根据角色获取菜单列表
    List<Menu> menus = menuService.getMenusWithRole();
    //System.out.println("根据角色获取菜单列表 = " + menus);
    //循环遍历Menus
    for (Menu menu : menus) {
      //根据请求url与菜单角的url是否匹配
      if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
        //System.out.println("menu = " + menu.getUrl());
        String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
        //System.out.println("str = " + str);
        return SecurityConfig.createList(str);
      }
    }
    //若无匹配的url，则默认登录即可访问
    return SecurityConfig.createList("ROLE_LOGIN");

  }

  @Override
  public Collection<ConfigAttribute> getAllConfigAttributes() {
    return null;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }
}
