package com.seecen.health.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 套餐实体类
 * 对应数据库表 t_setmeal
 */
@Data
@TableName("t_setmeal")
public class Setmeal implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 套餐名称
     */
    private String name;
    
    /**
     * 套餐编码
     */
    private String code;
    
    /**
     * 助记码
     */@TableField("helpCode")
    private String helpCode;
    
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
     * 备注
     */
    private String remark;
    
    /**
     * 注意事项
     */
    private String attention;
    
    /**
     * 套餐图片路径
     */
    private String img;
    
    /**
     * 套餐状态（0：禁用，1：启用）
     */
    private Integer status;
    
    /**
     * 排序顺序
     */
    private Integer sort;
    
}
