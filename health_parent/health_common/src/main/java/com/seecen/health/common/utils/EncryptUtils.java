package com.seecen.health.common.utils;

import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * 加密工具类
 * 提供密码加密、生成UUID等常用操作
 */
public class EncryptUtils {

    /**
     * 使用MD5加密字符串
     * @param str 原始字符串
     * @return 加密后的字符串
     */
    public static String md5(String str) {
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * 生成UUID字符串
     * @return UUID字符串
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成指定长度的随机字符串
     * @param length 字符串长度
     * @return 随机字符串
     */
    public static String generateRandomString(int length) {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }
}
