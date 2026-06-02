package com.seecen.health.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 提供日期格式化、解析等常用操作
 */
public class DateUtils {

    /**
     * 标准日期格式：yyyy-MM-dd
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 标准时间格式：HH:mm:ss
     */
    public static final String TIME_PATTERN = "HH:mm:ss";

    /**
     * 标准日期时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化日期为字符串
     * @param date 日期对象
     * @param pattern 日期格式
     * @return 格式化后的日期字符串
     */
    public static String format(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 格式化日期为标准日期字符串 (yyyy-MM-dd)
     * @param date 日期对象
     * @return 格式化后的日期字符串
     */
    public static String formatDate(Date date) {
        return format(date, DATE_PATTERN);
    }

    /**
     * 格式化日期为标准时间字符串 (HH:mm:ss)
     * @param date 日期对象
     * @return 格式化后的时间字符串
     */
    public static String formatTime(Date date) {
        return format(date, TIME_PATTERN);
    }

    /**
     * 格式化日期为标准日期时间字符串 (yyyy-MM-dd HH:mm:ss)
     * @param date 日期对象
     * @return 格式化后的日期时间字符串
     */
    public static String formatDateTime(Date date) {
        return format(date, DATE_TIME_PATTERN);
    }

    /**
     * 解析字符串为日期对象
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return 解析后的日期对象
     * @throws Exception 解析异常
     */
    public static Date parse(String dateStr, String pattern) throws Exception {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.parse(dateStr);
    }

    /**
     * 解析标准日期字符串为日期对象 (yyyy-MM-dd)
     * @param dateStr 日期字符串
     * @return 解析后的日期对象
     * @throws Exception 解析异常
     */
    public static Date parseDate(String dateStr) throws Exception {
        return parse(dateStr, DATE_PATTERN);
    }

    /**
     * 解析标准日期时间字符串为日期对象 (yyyy-MM-dd HH:mm:ss)
     * @param dateStr 日期时间字符串
     * @return 解析后的日期对象
     * @throws Exception 解析异常
     */
    public static Date parseDateTime(String dateStr) throws Exception {
        return parse(dateStr, DATE_TIME_PATTERN);
    }
}
