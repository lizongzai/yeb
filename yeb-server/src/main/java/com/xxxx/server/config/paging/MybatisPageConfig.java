package com.xxxx.server.config.paging;

import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPage分页插件配置
 *
 * @author lizongzai
 * @since 1.0.0
 */
@Configuration
public class MybatisPageConfig {

  @Bean
  public PaginationInnerInterceptor paginationInnerInterceptor() {
    return new PaginationInnerInterceptor();
  }

}
