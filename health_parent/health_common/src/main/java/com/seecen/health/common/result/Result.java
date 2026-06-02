package com.seecen.health.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 通用返回结果类
 * 用于统一API响应格式
 * @param <T> 响应数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    
    /**
     * 响应状态：2000 表示成功， 4000表示失败
     */
    private int code;
    
    /**
     * 响应消息
     */
    private String message;
    
    /**
     * 响应数据
     */
    private T data;
    
    /**
     * 无数据的成功响应
     * @param message 响应消息
     * @return 成功响应对象
     */
    public static <T> Result<T> success(String message) {
        return new Result<>(2000, message, null);
    }
    
    /**
     * 带数据的成功响应
     * @param message 响应消息
     * @param data 响应数据
     * @return 成功响应对象
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(2000, message, data);
    }
    
    /**
     * 失败响应
     * @param message 响应消息
     * @return 失败响应对象
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(4000, message, null);
    }
    
    /**
     * 带数据的失败响应
     * @param message 响应消息
     * @param data 响应数据
     * @return 失败响应对象
     */
    public static <T> Result<T> error(String message, T data) {
        return new Result<>(4000, message, data);
    }
    
}
