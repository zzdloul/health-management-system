package com.seecen.health.boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * it 健康管理项目的 服务启动类
 * 用于启动整个项目
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.seecen.health", excludeFilters = {
    @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.seecen\\.health\\.store\\.config\\.RedisConfig"),
    @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.seecen\\.health\\.store\\.utils\\.RedisUtil")
})
@MapperScan("com.seecen.health.core.mapper")
public class HealthBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(HealthBootApplication.class, args);
    }

}
