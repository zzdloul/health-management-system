package com.seecen.health.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seecen.health.common.entity.Assessment;
import com.seecen.health.common.entity.AssessmentAnswer;
import com.seecen.health.common.entity.AssessmentQuestion;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;

import java.util.List;
import java.util.Map;

/**
 * 健康评估服务接口
 * 定义健康评估相关的业务逻辑
 */
public interface AssessmentService extends IService<Assessment> {
    
    /**
     * 评估分页查询
     * @param queryPageBean 查询条件
     * @return 分页结果
     */
    PageResult<Assessment> pageQuery(QueryPageBean queryPageBean);
    
    /**
     * 新增评估
     * @param assessment 评估信息
     * @param answers 评估答案
     * @return 评估ID
     */
    Integer add(Assessment assessment, List<AssessmentAnswer> answers);
    
    /**
     * 根据ID查询评估
     * @param id 评估ID
     * @return 评估信息
     */
    Assessment getById(Integer id);
    
    /**
     * 根据评估ID查询答案
     * @param assessmentId 评估ID
     * @return 答案列表
     */
    List<AssessmentAnswer> findAnswersByAssessmentId(Integer assessmentId);
    
    /**
     * 根据评估类型查询问卷问题
     * @param assessmentType 评估类型
     * @return 问题列表
     */
    List<AssessmentQuestion> findQuestionsByType(Integer assessmentType);
    
    /**
     * 分析评估结果
     * @param assessment 评估信息
     * @param answers 评估答案
     * @return 分析结果
     */
    Map<String, Object> analyzeAssessmentResult(Assessment assessment, List<AssessmentAnswer> answers);
    
    /**
     * 生成评估报告
     * @param assessmentId 评估ID
     * @return 报告内容
     */
    Map<String, Object> generateAssessmentReport(Integer assessmentId);
    
    /**
     * 根据会员ID查询评估历史
     * @param memberId 会员ID
     * @return 评估列表
     */
    List<Assessment> findByMemberId(Integer memberId);
    
}
