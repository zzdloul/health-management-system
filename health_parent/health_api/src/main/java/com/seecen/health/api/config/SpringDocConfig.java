package com.seecen.health.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDoc OpenAPI 配置类
 * 用于配置 API 文档的基本信息
 */
@Configuration
public class SpringDocConfig {

    /**
     * 配置 OpenAPI 信息
     * @return OpenAPI 对象
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("健康管理系统 API")
                        .version("1.0")
                        .description("健康管理系统的 RESTful API 文档")
                        .termsOfService("http://swagger.io/terms/")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("http://springdoc.org")
                        )
                );
    }
}
