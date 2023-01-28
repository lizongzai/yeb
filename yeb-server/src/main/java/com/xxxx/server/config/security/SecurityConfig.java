//package com.xxxx.server.config.security;
//
//import com.xxxx.server.pojo.Admin;
//import com.xxxx.server.service.IAdminService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//  @Autowired
//  private IAdminService adminService;
//  @Autowired
//  private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
//  @Autowired
//  private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
//  @Override
//  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
//  }
//
//  @Override
//  public void configure(WebSecurity web) throws Exception {
//    web.ignoring().antMatchers(
//        "/login",
//        "/logout",
//        "/ws/**",
//        "/css/**",
//        "/js/**",
//        "/index.html",
//        "/img/**",
//        "/fonts/**",
//        "favicon.ico",
//        "/doc.html",
//        "/webjars/**",
//        "/swagger-resources/**",
//        "/v2/api-docs/**",
//        "/captcha",
//        "/ws/**"
//    );
//  }
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    //使用token, 不需要csrf
//    http.csrf()
//        .disable()
//        //基于token,不需要session
//        .sessionManagement()
//        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        .and()
//        .authorizeRequests()
//        //.antMatchers("/login", "/logout")
//        //.permitAll()
//        //除了上面，其它请求都需要认证
//        .anyRequest()
//        .authenticated()
//        .and()
//        //禁用缓存
//        .headers()
//        .cacheControl();
//    //添加token授权拦截器
//    http.addFilterBefore(jwtAuthenticationTokenFilter(),
//        UsernamePasswordAuthenticationFilter.class);
//    //添加自定义未授权和未返回登录结果
//    http.exceptionHandling()
//        .accessDeniedHandler(restfulAccessDeniedHandler)
//        .authenticationEntryPoint(restAuthenticationEntryPoint);
//  }
//
//  @Override
//  @Bean
//  public UserDetailsService userDetailsService() {
//    return username -> {
//      Admin admin = adminService.getAdminByUsername(username);
//      if (admin != null) {
//        return admin;
//      }
//      return null;
//    };
//  }
//
//  @Bean
//  public PasswordEncoder passwordEncoder() {
//    return new BCryptPasswordEncoder();
//  }
//
//  @Bean
//  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
//    return new JwtAuthenticationTokenFilter();
//  }
//
//
//}
package com.xxxx.server.config.security;


import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Description //TODO security配置类
 * @Author lizongzai
 * @Since 1.0.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private IAdminService adminService;
  @Autowired
  private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
  @Autowired
  private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(
        "/login",
        "/logout",
        "/ws/**",
        "/css/**",
        "/js/**",
        "/index.html",
        "/img/**",
        "/fonts/**",
        "favicon.ico",
        "/doc.html",
        "/webjars/**",
        "/swagger-resources/**",
        "/v2/api-docs/**",
        "/captcha",
        "/ws/**"
    );
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //使用JWT，不需要csrf
    http.csrf()
        .disable()
        //基于token,不需要session
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        //.antMatchers(HttpMethod.OPTIONS) //跨域请求先进行一次options请求
        //.permitAll()
        //需要认证
        .anyRequest()
        .authenticated()
        .and()
        .headers()
        //禁用缓存
        .cacheControl();
    //添加jwt登录授权过滤器或拦截器
    http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    //添加自定义未授权和未登录结果返回
    http.exceptionHandling()
        .accessDeniedHandler(restfulAccessDeniedHandler)
        .authenticationEntryPoint(restAuthenticationEntryPoint);
  }

  /**
   * 设置执行自定义认证登录
   *
   * @param auth
   * @throws Exception
   */
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
  }

  /**
   * 重写 UserDetailsService
   *
   * @return
   * @throws Exception
   */
  @Override
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      Admin admin = adminService.getAdminByUserName(username);
      if (admin != null) {
        return admin;
      }
      throw new UsernameNotFoundException("用户名和密码不正确!");
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
    return new JwtAuthenticationTokenFilter();
  }

}
