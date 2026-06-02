package com.seecen.health.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单实体类
 * 对应数据库表 t_order
 */
@Data
@TableName("t_order")
public class Order implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 会员id
     */
    private Integer memberId;
    
    /**
     * 预约日期
     */
    @TableField("orderdate")
    private Date orderDate;
    
    /**
     * 预约类型（电话预约/微信预约）
     */
    @TableField("ordertype")
    private String orderType;
    
    /**
     * 预约状态（是否到诊）
     */
    @TableField("orderstatus")
    private String orderStatus;
    
    /**
     * 套餐id
     */
    @TableField("setmeal_id")
    private Integer setmealId;
    
}
