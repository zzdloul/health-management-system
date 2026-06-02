package com.seecen.health.store.service.impl;

import com.seecen.health.store.service.CacheService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 内存缓存服务实现类
 * 使用内存缓存作为缓存存储
 */
@Service
public class RedisCacheServiceImpl implements CacheService {
    
    // 内存缓存
    private final Map<String, CachedValue> memoryCache = new ConcurrentHashMap<>();
    
    /**
     * 缓存值包装类，包含过期时间
     */
    private static class CachedValue {
        private final Object value;
        private final long expiryTime;
        
        public CachedValue(Object value, long expiryTime) {
            this.value = value;
            this.expiryTime = expiryTime;
        }
        
        public Object getValue() {
            return value;
        }
        
        public boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }
    
    /**
     * 设置缓存
     * @param key 缓存键
     * @param value 缓存值
     */
    @Override
    public void set(String key, Object value) {
        memoryCache.put(key, new CachedValue(value, Long.MAX_VALUE));
    }
    
    /**
     * 设置缓存并指定过期时间
     * @param key 缓存键
     * @param value 缓存值
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     */
    @Override
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        long expiryTime = System.currentTimeMillis() + timeUnit.toMillis(timeout);
        memoryCache.put(key, new CachedValue(value, expiryTime));
    }
    
    /**
     * 获取缓存
     * @param key 缓存键
     * @param <T> 返回类型
     * @return 缓存值
     */
    @Override
    public <T> T get(String key) {
        CachedValue cachedValue = memoryCache.get(key);
        if (cachedValue != null && !cachedValue.isExpired()) {
            return (T) cachedValue.getValue();
        } else if (cachedValue != null && cachedValue.isExpired()) {
            // 清理过期的内存缓存
            memoryCache.remove(key);
        }
        return null;
    }
    
    /**
     * 删除缓存
     * @param key 缓存键
     * @return 是否删除成功
     */
    @Override
    public boolean delete(String key) {
        return memoryCache.remove(key) != null;
    }
    
    /**
     * 检查缓存是否存在
     * @param key 缓存键
     * @return 是否存在
     */
    @Override
    public boolean exists(String key) {
        CachedValue cachedValue = memoryCache.get(key);
        return cachedValue != null && !cachedValue.isExpired();
    }
    
    /**
     * 设置缓存过期时间
     * @param key 缓存键
     * @param timeout 过期时间
     * @param timeUnit 时间单位
     * @return 是否设置成功
     */
    @Override
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        CachedValue cachedValue = memoryCache.get(key);
        if (cachedValue != null) {
            long expiryTime = System.currentTimeMillis() + timeUnit.toMillis(timeout);
            memoryCache.put(key, new CachedValue(cachedValue.getValue(), expiryTime));
            return true;
        }
        return false;
    }
    
    /**
     * 根据模式删除缓存
     * @param pattern 缓存键模式，支持通配符
     */
    @Override
    public void deletePattern(String pattern) {
        // 将模式转换为正则表达式
        String regex = pattern.replace("*", ".*");
        // 遍历所有缓存键，删除匹配的缓存
        memoryCache.keySet().removeIf(key -> key.matches(regex));
    }
    
}