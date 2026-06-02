package com.seecen.health.api.controller;

import com.seecen.health.common.result.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试控制器
 * 测试 API 文档接口是否正常
 */
@RestController
@RequestMapping("/test")
@Tag(name = "HelloWorld测试接口", description = "用于测试 API 接口是否正常")
public class HelloWorldController {

    @GetMapping("/hello")
    @Tag(name = "测试接口", description = "返回Hello World 响应")
    public Result<String> hello(){
        return Result.success("Hello World!");
    }

    @GetMapping("/health")
    @Tag(name = "健康检查", description = "健康检查API服务")
    public Result<String> health(){
        return Result.success("API 服务健康");
    }
}
