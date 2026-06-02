package com.seecen.health.store.service;

import java.util.concurrent.TimeUnit;

/**
 * 缓存服务接口
 * 定义缓存操作的统一规范
 */
public interface CacheService {
    
    /**
     * 设置缓存
     * @param key 缓存键
     * @param value 缓存值
     */
    void set(String key, Object value);
    
    /**
     * 设置缓存并指定过期时间
     * @param key 缓存键
     * @param value 缓存值
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     */
    void set(String key, Object value, long timeout, TimeUnit timeUnit);
    
    /**
     * 获取缓存
     * @param key 缓存键
     * @param <T> 返回类型
     * @return 缓存值
     */
    <T> T get(String key);
    
    /**
     * 删除缓存
     * @param key 缓存键
     * @return 是否删除成功
     */
    boolean delete(String key);
    
    /**
     * 检查缓存是否存在
     * @param key 缓存键
     * @return 是否存在
     */
    boolean exists(String key);
    
    /**
     * 设置缓存过期时间
     * @param key 缓存键
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     * @return 是否设置成功
     */
    boolean expire(String key, long timeout, TimeUnit timeUnit);
    
    /**
     * 根据模式删除缓存
     * @param pattern 缓存键模式，支持通配符
     */
    void deletePattern(String pattern);
    
}
