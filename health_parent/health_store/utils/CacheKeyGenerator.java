package com.seecen.health.store.utils;

/**
 * 缓存键生成器
 * 用于生成统一格式的缓存键
 */
public class CacheKeyGenerator {
    
    // 缓存键前缀
    private static final String PREFIX = "health:";
    
    // 分隔符
    private static final String SEPARATOR = ":";
    
    /**
     * 生成缓存键
     * @param module 模块名称
     * @param id 唯一标识
     * @return 缓存键
     */
    public static String generateKey(String module, Object id) {
        return PREFIX + module + SEPARATOR + id;
    }
    
    /**
     * 生成缓存键（带多个参数）
     * @param module 模块名称
     * @param params 参数数组
     * @return 缓存键
     */
    public static String generateKey(String module, Object... params) {
        StringBuilder key = new StringBuilder(PREFIX).append(module);
        for (Object param : params) {
            key.append(SEPARATOR).append(param);
        }
        return key.toString();
    }
    
    /**
     * 生成检查项缓存键
     * @param id 检查项ID
     * @return 缓存键
     */
    public static String generateCheckItemKey(Integer id) {
        return generateKey("checkitem", id);
    }
    
    /**
     * 生成检查组缓存键
     * @param id 检查组ID
     * @return 缓存键
     */
    public static String generateCheckGroupKey(Integer id) {
        return generateKey("checkgroup", id);
    }
    
    /**
     * 生成套餐缓存键
     * @param id 套餐ID
     * @return 缓存键
     */
    public static String generateSetmealKey(Integer id) {
        return generateKey("setmeal", id);
    }
    
    /**
     * 生成会员缓存键
     * @param id 会员ID
     * @return 缓存键
     */
    public static String generateMemberKey(Integer id) {
        return generateKey("member", id);
    }
    
    /**
     * 生成评估缓存键
     * @param id 评估ID
     * @return 缓存键
     */
    public static String generateAssessmentKey(Integer id) {
        return generateKey("assessment", id);
    }
    
    /**
     * 生成评估问卷缓存键
     * @param type 评估类型
     * @return 缓存键
     */
    public static String generateAssessmentQuestionKey(Integer type) {
        return generateKey("assessment:question", type);
    }
    
    /**
     * 生成列表缓存键
     * @param module 模块名称
     * @param page 页码
     * @param size 每页大小
     * @return 缓存键
     */
    public static String generateListKey(String module, int page, int size) {
        return generateKey(module, "list", page, size);
    }
    
    /**
     * 生成带查询条件的列表缓存键
     * @param module 模块名称
     * @param query 查询条件
     * @param page 页码
     * @param size 每页大小
     * @return 缓存键
     */
    public static String generateListKey(String module, String query, int page, int size) {
        return generateKey(module, "list", query, page, size);
    }
    
}