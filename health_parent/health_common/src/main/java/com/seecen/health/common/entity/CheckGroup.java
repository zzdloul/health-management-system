package com.seecen.health.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 检查组实体类
 * 对应数据库表 t_checkgroup
 */
@Data
@TableName("t_checkgroup")
public class CheckGroup implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 检查组编码
     */
    private String code;
    
    /**
     * 检查组名称
     */
    private String name;
    
    /**
     * 助记码
     */
    @TableField("helpcode")//直接映射到这个字段
    private String helpCode;
    
    /**
     * 性别（0：不限，1：男，2：女）
     */
    private String sex;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 注意事项
     */
    private String attention;
    
}
