package com.seecen.health.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seecen.health.common.entity.AssessmentQuestion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评估问卷问题数据访问接口
 * 继承自MyBatis-Plus的BaseMapper，提供基础的CRUD操作
 */
public interface AssessmentQuestionMapper extends BaseMapper<AssessmentQuestion> {
    
    /**
     * 根据评估类型查询启用的问卷问题
     * @param assessmentType 评估类型
     * @return 问题列表
     */
    List<AssessmentQuestion> findByAssessmentType(@Param("assessmentType") Integer assessmentType);
    
}