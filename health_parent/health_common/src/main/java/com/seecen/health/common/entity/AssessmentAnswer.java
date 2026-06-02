package com.seecen.health.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 评估答案实体类
 * 对应数据库表 t_assessment_answer
 */
@Data
@TableName("t_assessment_answer")
public class AssessmentAnswer implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 评估id
     */
    private Integer assessmentId;
    
    /**
     * 问题id
     */
    private Integer questionId;
    
    /**
     * 答案内容
     */
    private String answer;
    
    /**
     * 得分
     */
    private Integer score;
    
}