package com.seecen.health.store.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * Redis连接工厂条件类
 * 用于判断RedisConnectionFactory是否可用
 */
public class RedisConnectionFactoryCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        // 总是返回false，因为Redis不可用
        return false;
    }
}
