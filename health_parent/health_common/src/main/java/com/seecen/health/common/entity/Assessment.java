package com.seecen.health.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 健康评估实体类
 * 对应数据库表 t_assessment
 */
@Data
@TableName("t_assessment")
public class Assessment implements Serializable {
    
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
     * 评估类型（1：体质测评，2：心理测评，3：风险测评）
     */
    private Integer type;
    
    /**
     * 评估日期
     */
    private Date assessmentDate;
    
    /**
     * 评估结果
     */
    private String result;
    
    /**
     * 评估分数
     */
    private Integer score;
    
    /**
     * 健康管理师id
     */
    private Integer healthManagerId;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
}