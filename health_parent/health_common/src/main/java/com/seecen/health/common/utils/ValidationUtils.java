package com.seecen.health.common.utils;

import java.util.regex.Pattern;

/**
 * 验证工具类
 * 提供各种数据验证方法
 */
public class ValidationUtils {

    /**
     * 手机号正则表达式
     */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");

    /**
     * 邮箱正则表达式
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    /**
     * 密码正则表达式：至少8位，包含字母和数字
     */
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");

    /**
     * 验证手机号格式
     * @param phone 手机号
     * @return 是否有效
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 验证邮箱格式
     * @param email 邮箱
     * @return 是否有效
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * 验证密码格式
     * @param password 密码
     * @return 是否有效
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * 验证字符串是否为空
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 验证对象是否为空
     * @param obj 对象
     * @return 是否为空
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 验证整数是否为正数
     * @param num 整数
     * @return 是否为正数
     */
    public static boolean isPositive(Integer num) {
        return num != null && num > 0;
    }

    /**
     * 验证长整数是否为正数
     * @param num 长整数
     * @return 是否为正数
     */
    public static boolean isPositive(Long num) {
        return num != null && num > 0;
    }
}
