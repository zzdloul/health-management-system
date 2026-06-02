package com.seecen.health.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 体检报告实体类
 * 对应数据库表 t_medical_report
 */
@Data
@TableName("t_medical_report")
public class MedicalReport implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    
    /**
     * 会员id
     */
    @TableField("member_id")
    private Integer memberId;
    
    /**
     * 报告名称
     */
    private String name;
    
    /**
     * 报告描述
     */
    private String description;
    
    /**
     * 报告文件路径
     */
    @TableField("file_path")
    private String filePath;
    
    /**
     * 报告上传时间
     */
    @TableField("upload_time")
    private Date uploadTime;
    
    /**
     * 报告状态（0：待审核，1：已审核，2：驳回）
     */
    private Integer status;
    
    /**
     * 审核人id
     */
    @TableField("reviewer_id")
    private Integer reviewerId;
    
    /**
     * 审核时间
     */
    @TableField("review_time")
    private Date reviewTime;
    
    /**
     * 审核意见
     */
    @TableField("review_comment")
    private String reviewComment;
    
    /**
     * 会员信息
     */
    @TableField(exist = false)
    private Member member;
}