package com.seecen.health.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 预约设置实体类
 * 对应数据库表 t_ordersetting
 */
@Data
@TableName("t_ordersetting")
public class OrderSetting implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 预约日期
     */
    @TableField("orderdate")
    private Date orderDate;
    
    /**
     * 可预约人数
     */
    private Integer number;
    
    /**
     * 已预约人数
     */
    @TableField("reservations")
    private Integer reservations;
    
}
