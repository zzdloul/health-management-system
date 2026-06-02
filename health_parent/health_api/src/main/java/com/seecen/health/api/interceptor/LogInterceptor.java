package com.seecen.health.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 日志拦截器
 * 用于记录 HTTP 请求的日志信息
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 请求处理前的拦截
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @param handler 处理程序
     * @return 是否继续执行
     * @throws Exception 异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录请求开始时间和请求信息
        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String ip = request.getRemoteAddr();
        String time = LocalDateTime.now().format(formatter);
        
        logger.info("[{}] {} {} from {}", time, method, requestURI, ip);
        
        // 继续执行
        return true;
    }

    /**
     * 请求处理后的拦截
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @param handler 处理程序
     * @param modelAndView 模型和视图
     * @throws Exception 异常
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        // 可以在这里添加请求处理后的逻辑
    }

    /**
     * 视图渲染后的拦截
     * @param request HTTP 请求
     * @param response HTTP 响应
     * @param handler 处理程序
     * @param ex 异常
     * @throws Exception 异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 记录响应状态码
        String requestURI = request.getRequestURI();
        int status = response.getStatus();
        String time = LocalDateTime.now().format(formatter);
        
        if (ex != null) {
            logger.error("[{}] Request {} completed with error: {}", time, requestURI, ex.getMessage());
        } else {
            logger.info("[{}] Request {} completed with status: {}", time, requestURI, status);
        }
    }
}
