package com.seecen.health.store.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.seecen.health.store.config.OssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * OSS工具类
 * 提供文件上传到阿里云OSS的功能
 */
@Component
public class OssUtil {
    
    @Autowired(required = false)
    private OSS ossClient;
    
    @Autowired(required = false)
    private OssConfig ossConfig;
    
    /**
     * 上传文件到阿里云OSS
     * @param file 要上传的文件
     * @param directory 存储目录
     * @return 文件访问URL
     * @throws IOException 文件读取异常
     */
    public String upload(MultipartFile file, String directory) throws IOException {
        // 生成唯一的文件名，使用UUID确保唯一性，保留原始文件扩展名
        String originalFilename = file.getOriginalFilename();
        // 提取文件扩展名，确保文件名包含正确的后缀
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        // 使用UUID生成唯一文件名，避免文件名冲突
        String fileName = UUID.randomUUID().toString() + fileExtension;
        // 构建对象存储路径，使用指定目录组织图片，便于管理
        String objectName = directory + "/" + fileName;

        // 如果OSS客户端或配置为null，返回本地文件路径（模拟）
        if (ossClient == null || ossConfig == null) {
            return "/api/upload/" + objectName;
        }

        // 获取文件输入流，使用try-with-resources确保流自动关闭，避免资源泄漏
        try (InputStream inputStream = file.getInputStream()) {
            // 创建OSS上传请求对象，指定桶名称、对象名称和输入流
            PutObjectRequest putObjectRequest = new PutObjectRequest(ossConfig.getBucketName(), objectName, inputStream);
            // 执行上传操作，返回上传结果
            PutObjectResult result = ossClient.putObject(putObjectRequest);

            // 生成文件访问URL，格式为：https://bucketName.endpoint/objectName
            String endpoint = ossConfig.getEndpoint();

            // 构建完整的图片访问URL
            return "https://" + ossConfig.getBucketName() + "." + endpoint + "/" + objectName;
        }
    }
    
    /**
     * 上传套餐图片到阿里云OSS
     * @param file 要上传的图片文件
     * @return 图片访问URL
     * @throws IOException 文件读取异常
     */
    public String uploadSetmealImage(MultipartFile file) throws IOException {
        return upload(file, "setmeal-images");
    }
    
    /**
     * 上传体检报告到阿里云OSS
     * @param file 要上传的体检报告文件
     * @return 文件访问URL
     * @throws IOException 文件读取异常
     */
    public String uploadMedicalReport(MultipartFile file) throws IOException {
        return upload(file, "medical-reports");
    }
}
