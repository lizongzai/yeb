# Backend Project: yeb


### 1. 项目介绍

本项目目的是实现中小型企业的在线办公系统，云E办在线办公系统是一个用来管理日常的办公事务的 一个系统，他能够管的内容有：日常的各种流程审批，新闻，通知，公告，文件信息，财务，人事，费 用，资产，行政，项目，移动办公等等。它的作用就是通过软件的方式，方便管理，更加简单，更加扁 平。更加高效，更加规范，能够提高整体的管理运营水平。

本项目在技术方面采用最主流的前后端分离开发模式，使用业界最流行、社区非常活跃的开源框架 Spring Boot来构建后端，旨在实现云E办在线办公系统。包括职位管理、职称管理、部门管理、员工管 理、工资管理、在线聊天等模块。项目中还会使用业界主流的第三方组件扩展大家的知识面和技能池。


- Swagger2：接口文档
- SpringSecurity：安全框架
- JWT：令牌
- Kaptcha：图形验证码
- Redis：缓存器
- EasyPOI：文档导入导出
- RabbitMQ：消息队列。异步处理
- Mail：发送邮件
- WebSocket：在线聊天功能
- FastDFS：文件服务器，静态资源相应的文件。


### 2. 数据库
2.1 创建数据库
![image](https://user-images.githubusercontent.com/49580847/215239268-5aaa565a-ad14-42a2-aacc-af731c84cada.png)

$ docker run -d -p 3306:3306 --name mysql -v /mysqldata/mysql/log:/var/log/mysql  -v /mysqldata/mysql/data:/var/lib/mysql  -v /mysqldata/mysql/conf:/etc/mysql -e MYSQL_ROOT_PASSWORD=password   mysql:5.7

$ docker exec -it mysql-master /bin/bash

mysql -u root -p

create databases yeb;

show databases;

use yeb;

2.2 初始化数据库
在mysqldb容器里执行SQL语句./database/yeb.sql


### 3. 创建yeb项目

#### 3.1 父工程


`父工程：引入pom依赖包`

```xaml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <modules>
    <module>yeb-server</module>
    <module>yeb-generator</module>
  </modules>

  <parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.7</version>
    <relativePath/> <!-- lookup parent from repository -->
  </parent>

  <groupId>com.xxxx</groupId>
  <artifactId>yeb</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>pom</packaging>
  <name>yeb</name>
  <description>demo project for spring boot</description>

  <properties>
    <java.version>1.8</java.version>
  </properties>
</project>

```
#### 3.2 子工程


`application.yml`

```properties
server:
  port: 8081

spring:
  main:
    allow-circular-references: true
  mvc:
    pathmatch:
      matching-strategy: ANT_PATH_MATCHER
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: "jdbc:mysql://localhost:3306/yeb_back?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
    username: root
    password: password
    redis:
      timeout: 10000ms
      host: localhost
      port: 6379
      database: 0 # 选择哪个库，默认0库
      password: root
      lettuce:
        pool:
          max-active: 1024 # 最大连接数，默认 8
          max-wait: 10000ms # 最大连接阻塞等待时间，单位毫秒，默认 -1
          max-idle: 200 # 最大空闲连接，默认 8
          min-idle: 5
    # rabbitmq配置
    rabbitmq:
      # 用户名
      username: guest
      # 密码
      password: guest
      # 服务器地址
      host: localhost
      # 端口
      port: 5672
      # 消息确认回调
      publisher-confirm-type: correlated
      # 消息失败回调
      publisher-returns: true

    hikari:
      # 连接池名
      pool-name: DateHikariCP
      # 最小空闲连接数
      minimum-idle: 5
      # 空闲连接存活最大时间，默认值为6000000
      idle-timeout: 180000
      # 最大连接数,默认10
      maximum-pool-size: 10
      # 从连接池返回的连接的自动提交
      auto-commit: true
      # 连接最大存活时间，0表示永久存活，默认1800000(30分钟)
      max-lifetime: 1800000
      # 连接超时时间, 默认30000(30秒)
      connection-timeout: 30000
      # 测试连接是否可用的查询语句
      connection-test-query: SELECT 1

# Mybatis-plus配置
mybatis-plus:
  # 配置Mapper映射文件
  mapper-locations: classpath*:/mapper/*Mapper.xml
  #配置mybatis数据返回类型别名
  type-aliases-package: com.ibm.server.pojo
  configuration:
    # 自动驼峰命名
    map-underscore-to-camel-case: false

# Mybatis SQL打印(方法接口所在的包，不是Mapper.xml所在的包)
logging:
  level:
    com.xxxx.server.mapper: debug

jwt:
  # Jwt存储的请求头
  tokenHeader: Authorization
  # Jwt加密秘钥
  secret: yeb-secret
  # Jwt 的超期限时间（60*60）*24
  expiration: 604800
  # Jwt负载中拿到开头
  tokenHead: Bearer
```



`创建启动类`

```java
package com.xxxx.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author lizongzai
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("com.xxxx.server.mapper")
public class YebApplication {
  public static void main(String[] args) {
    SpringApplication.run(YebApplication.class,args);
  }

}
```


