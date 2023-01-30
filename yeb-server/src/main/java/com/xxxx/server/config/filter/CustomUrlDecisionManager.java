package com.xxxx.server.config.filter;

import java.util.Collection;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 权限控制,判断用户角色是否为url所需的角色
 */
@Configuration
public class CustomUrlDecisionManager implements AccessDecisionManager {

  @Override
  public void decide(Authentication authentication, Object object,
      Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException, InsufficientAuthenticationException {
    //配置集合
    for (ConfigAttribute configAttribute : configAttributes) {
      //获取当前所需url的角色
      String urlRole = configAttribute.getAttribute();
      //System.out.println("用户角色 = " + urlRole);

      //判断角色是否登录即可访问的角色，此角色在CustomFilter中设置
      if ("ROLE_LOGIN".equals(urlRole)) {
        //判断是否登录
        if (authentication instanceof AnonymousAuthenticationToken) {
          throw new AccessDeniedException("CustomUrlDecisionManager --> 尚未登录,请登录!");
        } else {
          return;
        }
      }

      //判断用户角色是否为url所需的角色
      Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
      //System.out.println("判断用户角色是否为url所需的角色 = " + authorities);
      for (GrantedAuthority authority : authorities) {
        if (authority.getAuthority().equals(urlRole)) {
          return;
        }
      }
    }
    throw new AccessDeniedException("CustomUrlDecisionManager --> 权限不足,请联系管理员");
  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return false;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return false;
  }
}
