package com.seecen.health.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 配置类
 * 暂时禁用所有安全保护，允许所有请求直接访问
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 禁用CSRF保护
            .csrf(csrf -> csrf.disable())
            // 允许所有请求
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().permitAll()
            )
            // 禁用表单登录
            .formLogin(form -> form.disable())
            // 禁用HTTP基本认证
            .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

}