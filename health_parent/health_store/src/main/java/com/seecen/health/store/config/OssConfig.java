package com.seecen.health.store.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * OSS配置类
 * 用于读取阿里云OSS相关配置信息
 */
@Data
@Configuration
public class OssConfig {
    
    /**
     * OSS服务端点
     */
    @Value("${oss.endpoint}")
    private String endpoint;
    
    /**
     * OSS访问密钥ID
     */
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    
    /**
     * OSS访问密钥密钥
     */
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    
    /**
     * OSS存储桶名称
     */
    @Value("${oss.bucketName}")
    private String bucketName;
    
    /**
     * 获取存储桶名称
     * @return 存储桶名称
     */
    public String getBucketName() {
        return bucketName;
    }
    
    /**
     * 获取服务端点
     * @return 服务端点
     */
    public String getEndpoint() {
        return endpoint;
    }
}
