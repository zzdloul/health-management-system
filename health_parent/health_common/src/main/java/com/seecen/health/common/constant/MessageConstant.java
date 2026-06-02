package com.seecen.health.common.constant;

/**
 * 消息常量类
 * 用于定义系统中使用的各种消息提示
 */
public class MessageConstant {
    
    // 通用消息
    public static final String SUCCESS = "操作成功";
    public static final String FAILURE = "操作失败";
    public static final String SERVER_ERROR = "服务器内部错误";
    public static final String ADD_SUCCESS = "新增成功";
    public static final String QUERY_SUCCESS = "查询成功";
    
    // 检查项相关消息
    public static final String ADD_CHECKITEM_SUCCESS = "新增检查项成功";
    public static final String ADD_CHECKITEM_FAIL = "新增检查项失败";
    public static final String EDIT_CHECKITEM_SUCCESS = "编辑检查项成功";
    public static final String EDIT_CHECKITEM_FAIL = "编辑检查项失败";
    public static final String DELETE_CHECKITEM_SUCCESS = "删除检查项成功";
    public static final String DELETE_CHECKITEM_FAIL = "删除检查项失败";
    public static final String QUERY_CHECKITEM_SUCCESS = "查询检查项成功";
    public static final String QUERY_CHECKITEM_FAIL = "查询检查项失败";
    public static final String CHECKITEM_HAS_ASSOCIATION = "检查项已被关联，无法删除";
    
    // 检查组相关消息
    public static final String ADD_CHECKGROUP_SUCCESS = "新增检查组成功";
    public static final String ADD_CHECKGROUP_FAIL = "新增检查组失败";
    public static final String EDIT_CHECKGROUP_SUCCESS = "编辑检查组成功";
    public static final String EDIT_CHECKGROUP_FAIL = "编辑检查组失败";
    public static final String DELETE_CHECKGROUP_SUCCESS = "删除检查组成功";
    public static final String DELETE_CHECKGROUP_FAIL = "删除检查组失败";
    public static final String QUERY_CHECKGROUP_SUCCESS = "查询检查组成功";
    public static final String QUERY_CHECKGROUP_FAIL = "查询检查组失败";
    public static final String CHECKGROUP_HAS_ASSOCIATION = "检查组已被关联，无法删除";
    
    // 套餐相关消息
    public static final String ADD_SETMEAL_SUCCESS = "新增套餐成功";
    public static final String ADD_SETMEAL_FAIL = "新增套餐失败";
    public static final String EDIT_SETMEAL_SUCCESS = "编辑套餐成功";
    public static final String EDIT_SETMEAL_FAIL = "编辑套餐失败";
    public static final String DELETE_SETMEAL_SUCCESS = "删除套餐成功";
    public static final String DELETE_SETMEAL_FAIL = "删除套餐失败";
    public static final String QUERY_SETMEAL_SUCCESS = "查询套餐成功";
    public static final String QUERY_SETMEAL_FAIL = "查询套餐失败";
    public static final String SETMEAL_HAS_ASSOCIATION = "套餐已被关联，无法删除";
    
    // 会员相关消息
    public static final String ADD_MEMBER_SUCCESS = "新增会员成功";
    public static final String ADD_MEMBER_FAIL = "新增会员失败";
    public static final String EDIT_MEMBER_SUCCESS = "编辑会员成功";
    public static final String EDIT_MEMBER_FAIL = "编辑会员失败";
    public static final String DELETE_MEMBER_SUCCESS = "删除会员成功";
    public static final String DELETE_MEMBER_FAIL = "删除会员失败";
    public static final String QUERY_MEMBER_SUCCESS = "查询会员成功";
    public static final String QUERY_MEMBER_FAIL = "查询会员失败";
    
    // 预约相关消息
    public static final String ORDER_SUCCESS = "预约成功";
    public static final String ORDER_FAIL = "预约失败";
    public static final String ORDERSETTING_SUCCESS = "预约设置成功";
    public static final String ORDERSETTING_FAIL = "预约设置失败";
    public static final String ORDERSETTING_DATE_REPEAT = "预约日期重复";
    public static final String ORDERSETTING_NUMBER_ERROR = "可预约人数不能小于0";
    
    // 登录相关消息
    public static final String LOGIN_SUCCESS = "登录成功";
    public static final String LOGIN_FAIL = "登录失败，用户名或密码错误";
    public static final String LOGOUT_SUCCESS = "退出成功";
    public static final String SESSION_TIMEOUT = "会话超时，请重新登录";
    public static final String NO_PERMISSION = "无权限操作";
    
    // 文件上传相关消息
    public static final String UPLOAD_SUCCESS = "文件上传成功";
    public static final String UPLOAD_FAIL = "文件上传失败";
    public static final String FILE_TYPE_ERROR = "文件类型错误";
    public static final String FILE_SIZE_ERROR = "文件大小超出限制";
    
    // 数据验证相关消息
    public static final String PARAMETER_ERROR = "参数错误";
    public static final String DATA_NOT_EXIST = "数据不存在";
    public static final String DATA_ALREADY_EXIST = "数据已存在";
    
}
