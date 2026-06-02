package com.seecen.health.common.exception;

/**
 * 业务异常类
 * 用于处理业务逻辑中的异常情况
 */
public class BusinessException extends RuntimeException {
    
    /**
     * 错误代码
     */
    private String errorCode;
    
    /**
     * 错误消息
     */
    private String errorMessage;
    
    /**
     * 构造方法
     * @param errorMessage 错误消息
     */
    public BusinessException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
    
    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param errorMessage 错误消息
     */
    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    /**
     * 构造方法
     * @param errorMessage 错误消息
     * @param cause 异常原因
     */
    public BusinessException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
    }
    
    /**
     * 构造方法
     * @param errorCode 错误代码
     * @param errorMessage 错误消息
     * @param cause 异常原因
     */
    public BusinessException(String errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    
    // getter and setter
    public String getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
}
