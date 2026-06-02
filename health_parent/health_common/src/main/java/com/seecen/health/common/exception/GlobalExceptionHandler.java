package com.seecen.health.common.exception;

import com.seecen.health.common.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * 用于统一处理所有异常，返回统一的响应格式
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * 处理业务异常
     * @param e 业务异常
     * @return 统一响应结果
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result<?> handleBusinessException(BusinessException e) {
        logger.error("业务异常: {}", e.getMessage(), e);
        return Result.error(e.getErrorMessage());
    }
    
    /**
     * 处理系统异常
     * @param e 系统异常
     * @return 统一响应结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> handleSystemException(Exception e) {
        logger.error("系统异常: {}", e.getMessage(), e);
        return Result.error("服务器内部错误，请联系管理员");
    }
    
    /**
     * 处理运行时异常
     * @param e 运行时异常
     * @return 统一响应结果
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result<?> handleRuntimeException(RuntimeException e) {
        logger.error("运行时异常: {}", e.getMessage(), e);
        return Result.error("操作失败: " + e.getMessage());
    }
    
}
