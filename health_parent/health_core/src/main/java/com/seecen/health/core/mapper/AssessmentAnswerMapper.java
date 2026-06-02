package com.seecen.health.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seecen.health.common.entity.AssessmentAnswer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 评估答案数据访问接口
 * 继承自MyBatis-Plus的BaseMapper，提供基础的CRUD操作
 */
public interface AssessmentAnswerMapper extends BaseMapper<AssessmentAnswer> {
    
    /**
     * 根据评估ID查询答案
     * @param assessmentId 评估ID
     * @return 答案列表
     */
    List<AssessmentAnswer> findByAssessmentId(@Param("assessmentId") Integer assessmentId);
    
    /**
     * 批量插入评估答案
     * @param answers 答案列表
     */
    void batchInsert(@Param("answers") List<AssessmentAnswer> answers);
    
}