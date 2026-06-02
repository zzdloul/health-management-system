package com.seecen.health.api.config;

import com.seecen.health.api.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 配置类
 * 用于配置 Web 相关的组件，如拦截器、资源映射等
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LogInterceptor logInterceptor;

    /**
     * 注册拦截器
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册日志拦截器，并指定拦截路径
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/api/**")  // 拦截所有 API 请求
                .excludePathPatterns("/api/auth/**");  // 排除认证相关的路径
    }

    /**
     * 配置跨域：允许外部服务 访问当前服务
     * @param registry 跨域注册表
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")  // 使用 allowedOriginPatterns 替代 allowedOrigins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    // 前端： http://127.0.0.1:3001   axios
    // 后端： http://127.0.0.1:8001   /api/user/add

}
