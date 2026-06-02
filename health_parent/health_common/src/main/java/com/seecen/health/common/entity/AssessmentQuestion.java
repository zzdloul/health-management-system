package com.seecen.health.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 评估问卷问题实体类
 * 对应数据库表 t_assessment_question
 */
@Data
@TableName("t_assessment_question")
public class AssessmentQuestion implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 评估类型（1：体质测评，2：心理测评，3：风险测评）
     */
    private Integer assessmentType;
    
    /**
     * 问题内容
     */
    private String content;
    
    /**
     * 问题类型（1：单选题，2：多选题，3：填空题）
     */
    private Integer type;
    
    /**
     * 选项（JSON格式）
     */
    private String options;
    
    /**
     * 分数（JSON格式，对应选项的分数）
     */
    private String scores;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 状态（1：启用，0：禁用）
     */
    private Integer status;
    
}