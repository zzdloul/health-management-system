package com.seecen.health.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员实体类
 * 对应数据库表 t_member
 */
@Data
@TableName("t_member")
public class Member implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 会员档案号
     */
    // private String fileNumber;
    
    /**
     * 会员姓名
     */
    private String name;
    
    /**
     * 性别
     */
    private String sex;
    
    /**
     * 身份证号
     */
    @TableField("idcard")
    private String idCard;
    
    /**
     * 手机号
     */
    @TableField("phonenumber")
    private String phoneNumber;
    
    /**
     * 注册时间
     */
    @TableField("regtime")
    private Date regTime;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 出生日期
     */
    @TableField("birthday")
    private Date birthday;
    
    /**
     * 备注
     */
    private String remark;
    
}
