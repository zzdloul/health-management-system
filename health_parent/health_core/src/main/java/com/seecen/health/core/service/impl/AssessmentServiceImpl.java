package com.seecen.health.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seecen.health.common.entity.Assessment;
import com.seecen.health.common.entity.AssessmentAnswer;
import com.seecen.health.common.entity.AssessmentQuestion;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.core.mapper.AssessmentAnswerMapper;
import com.seecen.health.core.mapper.AssessmentMapper;
import com.seecen.health.core.mapper.AssessmentQuestionMapper;
import com.seecen.health.core.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 健康评估服务实现类
 * 实现AssessmentService接口，提供健康评估相关的业务逻辑
 * 包含缓存支持，提升系统性能
 */
@Service
public class AssessmentServiceImpl extends ServiceImpl<AssessmentMapper, Assessment> implements AssessmentService {
    
    @Autowired
    private AssessmentMapper assessmentMapper;
    
    @Autowired
    private AssessmentQuestionMapper assessmentQuestionMapper;
    
    @Autowired
    private AssessmentAnswerMapper assessmentAnswerMapper;
    
    // 暂时注释掉缓存服务，避免Redis相关的问题
    // @Autowired
    // private CacheService cacheService;
    
    /**
     * 评估分页查询
     * @param queryPageBean 查询条件
     * @return 分页结果
     */
    @Override
    public PageResult<Assessment> pageQuery(QueryPageBean queryPageBean) {
        // 创建分页对象
        Page<Assessment> page = new Page<>(queryPageBean.getPageNum(), queryPageBean.getPageSize());
        
        // 构建查询条件
        QueryWrapper<Assessment> queryWrapper = new QueryWrapper<>();
        
        // 如果有查询字符串，添加模糊查询条件
        if (queryPageBean.getQueryString() != null && !queryPageBean.getQueryString().isEmpty()) {
            queryWrapper.like("result", queryPageBean.getQueryString())
                    .or().like("remark", queryPageBean.getQueryString());
        }
        
        // 执行分页查询
        Page<Assessment> page1 = baseMapper.selectPage(page, queryWrapper);
        
        // 构建分页结果
        PageResult<Assessment> result = new PageResult<>(page1.getTotal(), page1.getRecords());
        
        return result;
    }
    
    /**
     * 新增评估
     * @param assessment 评估信息
     * @param answers 评估答案
     * @return 评估ID
     */
    @Transactional
    @Override
    public Integer add(Assessment assessment, List<AssessmentAnswer> answers) {
        // 保存评估基本信息
        baseMapper.insert(assessment);
        
        // 设置评估ID到答案中
        Integer assessmentId = assessment.getId();
        for (AssessmentAnswer answer : answers) {
            answer.setAssessmentId(assessmentId);
        }
        
        // 批量保存答案
        if (!answers.isEmpty()) {
            assessmentAnswerMapper.batchInsert(answers);
        }
        
        // 分析评估结果
        Map<String, Object> analysisResult = analyzeAssessmentResult(assessment, answers);
        
        // 更新评估结果
        assessment.setResult((String) analysisResult.get("result"));
        assessment.setScore((Integer) analysisResult.get("totalScore"));
        baseMapper.updateById(assessment);
        
        return assessmentId;
    }
    
    /**
     * 根据ID查询评估
     * @param id 评估ID
     * @return 评估信息
     */
    @Override
    public Assessment getById(Integer id) {
        // 从数据库查询
        Assessment assessment = baseMapper.selectById(id);
        
        return assessment;
    }
    
    /**
     * 根据评估ID查询答案
     * @param assessmentId 评估ID
     * @return 答案列表
     */
    @Override
    public List<AssessmentAnswer> findAnswersByAssessmentId(Integer assessmentId) {
        // 从数据库查询
        List<AssessmentAnswer> answers = assessmentAnswerMapper.findByAssessmentId(assessmentId);
        
        return answers;
    }
    
    /**
     * 根据评估类型查询问卷问题
     * @param assessmentType 评估类型
     * @return 问题列表
     */
    @Override
    public List<AssessmentQuestion> findQuestionsByType(Integer assessmentType) {
        // 从数据库查询
        List<AssessmentQuestion> questions = assessmentQuestionMapper.findByAssessmentType(assessmentType);
        
        return questions;
    }
    
    /**
     * 分析评估结果
     * @param assessment 评估信息
     * @param answers 评估答案
     * @return 分析结果
     */
    @Override
    public Map<String, Object> analyzeAssessmentResult(Assessment assessment, List<AssessmentAnswer> answers) {
        Map<String, Object> result = new HashMap<>();
        
        // 计算总得分
        int totalScore = 0;
        for (AssessmentAnswer answer : answers) {
            totalScore += answer.getScore() != null ? answer.getScore() : 0;
        }
        
        // 根据评估类型和得分生成评估结果
        String assessmentResult = generateResultByTypeAndScore(assessment.getType(), totalScore);
        
        // 生成健康建议
        String suggestion = generateSuggestion(assessment.getType(), totalScore);
        
        // 构建分析结果
        result.put("totalScore", totalScore);
        result.put("result", assessmentResult);
        result.put("suggestion", suggestion);
        result.put("assessmentType", assessment.getType());
        
        return result;
    }
    
    /**
     * 生成评估报告
     * @param assessmentId 评估ID
     * @return 报告内容
     */
    @Override
    public Map<String, Object> generateAssessmentReport(Integer assessmentId) {
        // 获取评估信息
        Assessment assessment = baseMapper.selectById(assessmentId);
        if (assessment == null) {
            Map<String, Object> report = new HashMap<>();
            report.put("error", "评估信息不存在");
            return report;
        }
        
        // 获取评估答案
        List<AssessmentAnswer> answers = assessmentAnswerMapper.findByAssessmentId(assessmentId);
        
        // 分析评估结果
        Map<String, Object> analysisResult = analyzeAssessmentResult(assessment, answers);
        
        // 构建报告内容
        Map<String, Object> report = new HashMap<>();
        report.put("assessment", assessment);
        report.put("answers", answers);
        report.put("analysis", analysisResult);
        report.put("reportDate", new java.util.Date());
        
        return report;
    }
    
    /**
     * 根据会员ID查询评估历史
     * @param memberId 会员ID
     * @return 评估列表
     */
    @Override
    public List<Assessment> findByMemberId(Integer memberId) {
        // 从数据库查询
        List<Assessment> assessments = assessmentMapper.findByMemberId(memberId);
        
        return assessments;
    }
    
    /**
     * 根据评估类型和得分生成评估结果
     * @param assessmentType 评估类型
     * @param score 得分
     * @return 评估结果
     */
    private String generateResultByTypeAndScore(Integer assessmentType, int score) {
        switch (assessmentType) {
            case 1: // 体质测评
                if (score >= 80) {
                    return "体质优秀"; 
                } else if (score >= 60) {
                    return "体质良好"; 
                } else if (score >= 40) {
                    return "体质一般"; 
                } else {
                    return "体质较差"; 
                }
            case 2: // 心理测评
                if (score >= 80) {
                    return "心理健康"; 
                } else if (score >= 60) {
                    return "心理状态良好"; 
                } else if (score >= 40) {
                    return "心理状态一般"; 
                } else {
                    return "心理状态较差"; 
                }
            case 3: // 风险测评
                if (score >= 80) {
                    return "健康风险低"; 
                } else if (score >= 60) {
                    return "健康风险较低"; 
                } else if (score >= 40) {
                    return "健康风险中等"; 
                } else {
                    return "健康风险较高"; 
                }
            default:
                return "评估结果未知";
        }
    }
    
    /**
     * 生成健康建议
     * @param assessmentType 评估类型
     * @param score 得分
     * @return 健康建议
     */
    private String generateSuggestion(Integer assessmentType, int score) {
        switch (assessmentType) {
            case 1: // 体质测评
                if (score >= 80) {
                    return "继续保持良好的生活习惯，定期进行体检，保持适量运动。";
                } else if (score >= 60) {
                    return "建议加强锻炼，保持均衡饮食，保证充足睡眠。";
                } else if (score >= 40) {
                    return "建议制定运动计划，改善饮食结构，增加休息时间。";
                } else {
                    return "建议咨询医生，制定个性化的健康改善计划，加强锻炼，改善生活习惯。";
                }
            case 2: // 心理测评
                if (score >= 80) {
                    return "保持积极乐观的心态，继续保持良好的心理状态。";
                } else if (score >= 60) {
                    return "建议适当放松，培养兴趣爱好，保持良好的人际关系。";
                } else if (score >= 40) {
                    return "建议学习压力管理技巧，保持规律作息，必要时寻求心理咨询。";
                } else {
                    return "建议及时寻求专业心理咨询，调整生活方式，保持良好的社会支持。";
                }
            case 3: // 风险测评
                if (score >= 80) {
                    return "保持健康的生活方式，定期体检，预防疾病。";
                } else if (score >= 60) {
                    return "建议改善饮食结构，增加运动，定期进行健康检查。";
                } else if (score >= 40) {
                    return "建议制定健康管理计划，控制风险因素，定期监测健康指标。";
                } else {
                    return "建议咨询医生，制定详细的健康干预计划，积极控制风险因素。";
                }
            default:
                return "请根据自身情况，保持健康的生活方式。";
        }
    }
}