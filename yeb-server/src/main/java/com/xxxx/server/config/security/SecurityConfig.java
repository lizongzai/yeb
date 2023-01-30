package com.xxxx.server.config.security;


import com.xxxx.server.config.filter.CustomFilter;
import com.xxxx.server.config.filter.CustomUrlDecisionManager;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
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
  @Autowired
  private IRoleService roleService;
  @Autowired
  private CustomFilter customFilter;
  @Autowired
  private CustomUrlDecisionManager customUrlDecisionManager;

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
        //.antMatchers("/login", "/logout") //跨域请求先进行一次options请求
        //.permitAll()
        //需要认证
        .anyRequest()
        .authenticated()
        //设置动态权限配置
        .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
          @Override
          public <O extends FilterSecurityInterceptor> O postProcess(O object) {
            object.setAccessDecisionManager(customUrlDecisionManager);
            object.setSecurityMetadataSource(customFilter);
            return object;
          }
        })
        .and()
        .headers()
        //禁用缓存
        .cacheControl();
    //添加jwt登录授权过滤器或拦截器
    http.addFilterBefore(jwtAuthenticationTokenFilter(),
        UsernamePasswordAuthenticationFilter.class);
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
        admin.setRoles(roleService.getRolesByAdminId(admin.getId()));
        return admin;
      }
      throw new UsernameNotFoundException(
          "SecurityConfig --> UserDetailsService: 用户名和密码不正确!");
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
