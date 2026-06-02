package com.seecen.health.store.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OSS客户端配置类
 * 用于初始化和管理OSS客户端实例
 */
@Configuration
public class OssClientConfig {
    
    @Autowired
    private OssConfig ossConfig;
    
    /**
     * 创建OSS客户端实例
     * @return OSS客户端实例
     */
    @Bean
    public OSS ossClient() {
        // 使用OSS配置创建客户端实例
        return new OSSClientBuilder().build(
            ossConfig.getEndpoint(),
            ossConfig.getAccessKeyId(),
            ossConfig.getAccessKeySecret()
        );
    }
}
