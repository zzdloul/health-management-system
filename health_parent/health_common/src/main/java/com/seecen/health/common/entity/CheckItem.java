package com.seecen.health.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 检查项实体类
 * 对应数据库表 t_checkitem
 */
@Data
@TableName("t_checkitem")
public class CheckItem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 检查项编码
     */
    private String code;
    
    /**
     * 检查项名称
     */
    private String name;
    
    /**
     * 性别（0：不限，1：男，2：女）
     */
    private String sex;
    
    /**
     * 适用年龄
     */
    private String age;
    
    /**
     * 价格
     */
    private Float price;
    
    /**
     * 检查项类型（1：检查，2：检验）
     */
    private String type;
    
    /**
     * 注意事项
     */
    private String attention;
    
    /**
     * 备注
     */
    private String remark;
    
}
