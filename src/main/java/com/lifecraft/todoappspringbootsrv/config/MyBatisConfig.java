package com.lifecraft.todoappspringbootsrv.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.lifecraft.todoappspringbootsrv.mapper")
public class MyBatisConfig {}
