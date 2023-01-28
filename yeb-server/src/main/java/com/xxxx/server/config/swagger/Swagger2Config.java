//package com.xxxx.server.config.swagger;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableSwagger2
//public class Swagger2Config {
//
//  @Bean
//  public Docket createRestApi() {
//    return new Docket(DocumentationType.SWAGGER_2)
//        .apiInfo(apiInfo())
//        .select()
//        .apis(RequestHandlerSelectors.basePackage("com.xxxx.server.controller"))
//        .paths(PathSelectors.any())
//        .build()
//        .securityContexts(securityContexts())
//        .securitySchemes(securitySchemes());
//  }
//
//  private ApiInfo apiInfo() {
//    return new ApiInfoBuilder()
//        .title("Restful API's Document")
//        .description("Restful API's Document")
//        .contact(new Contact("lizongzai", "http://localhost:8086/doc.html", "lizongzai@gmail.com"))
//        .build();
//  }
//
//  private List<SecurityContext> securityContexts() {
//    List<SecurityContext> result = new ArrayList<>();
//    result.add(getContextByPath("/hello/.*"));
//    return result;
//  }
//
//  private SecurityContext getContextByPath(String pathRegex) {
//    return SecurityContext.builder().securityReferences(defaultAuth())
//        .forPaths(PathSelectors.regex(pathRegex)).build();
//  }
//
//  private List<SecurityReference> defaultAuth() {
//    List<SecurityReference> result = new ArrayList<>();
//    //授权范围
//    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//    authorizationScopes[0] = authorizationScope;
//    result.add(new SecurityReference("Authorization", authorizationScopes));
//    return result;
//  }
//
//  private List<ApiKey> securitySchemes() {
//    //设置请求头信息
//    List<ApiKey> result = new ArrayList<>();
//    ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
//    result.add(apiKey);
//    return result;
//  }
//
//}

package com.xxxx.server.config.swagger;

import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 配置类
 * @author lizongzai
 * @since 1.0.0
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {
  @Bean
  public Docket createRestApi() {

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.xxxx.server.controller"))
        .paths(PathSelectors.any())
        .build()
        .securityContexts(securityContexts())
        .securitySchemes(securitySchemes());

  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Restful API Document")
        .description("Restful API Document")
        .contact(new Contact("lizongzai", "http://localhost:8081/doc.html", "lizongzai@gmail.com"))
        .version("1.0")
        .build();
  }

  private List<ApiKey> securitySchemes() {
    //设置请求头信息
    List<ApiKey> result = new ArrayList<>();
    ApiKey apiKey = new ApiKey("Authorization", "Authorization", "Header");
    result.add(apiKey);
    return result;
  }

  private List<SecurityContext> securityContexts() {
    //设置需要登录认证的路径
    List<SecurityContext> result = new ArrayList<>();
    result.add(getContextByPath("/hello/.*"));
    return result;
  }

  private SecurityContext getContextByPath(String pathRegex) {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(PathSelectors.regex(pathRegex))
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    List<SecurityReference> result = new ArrayList<>();
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    result.add(new SecurityReference("Authorization", authorizationScopes));
    return result;
  }
}
