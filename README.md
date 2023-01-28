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
# 使用容器创建mysql数据库
$ docker run -d -p 3306:3306 --name mysql -v /mysqldata/mysql/log:/var/log/mysql  -v /mysqldata/mysql/data:/var/lib/mysql  -v /mysqldata/mysql/conf:/etc/mysql -e MYSQL_ROOT_PASSWORD=password   mysql:5.7

$ docker exec -it mysql-master /bin/bash
# mysql -u root -p
# create databases yeb;
# show databases;
# use yeb

2.2 初始化数据库
在mysqldb容器里执行SQL语句./database/yeb.sql
