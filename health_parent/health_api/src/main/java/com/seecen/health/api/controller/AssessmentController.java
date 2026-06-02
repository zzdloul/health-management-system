package com.seecen.health.api.controller;

import com.seecen.health.api.request.RequestParams;
import com.seecen.health.common.constant.MessageConstant;
import com.seecen.health.common.entity.Assessment;
import com.seecen.health.common.entity.AssessmentAnswer;
import com.seecen.health.common.entity.AssessmentQuestion;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.common.result.Result;
import com.seecen.health.core.service.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 健康评估控制器
 * 提供健康评估相关的API接口
 */
@RestController
@RequestMapping("/assessments")
@Tag(name="健康评估管理", description = "健康评估相关操作")
public class AssessmentController {
    
    @Autowired
    private AssessmentService assessmentService;
    
    /**
     * 评估分页查询
     * @param queryPageBean 查询条件
     * @return 分页结果
     */
    @PostMapping("/page")
    @Operation(summary = "评估分页查询", description = "根据条件分页查询评估记录")
    public Result<PageResult<Assessment>> page(@RequestBody QueryPageBean queryPageBean){
        PageResult<Assessment> pageResult = assessmentService.pageQuery(queryPageBean);
        return Result.success(MessageConstant.QUERY_SUCCESS, pageResult);
    }
    
    /**
     * 新增评估
     * @param requestBody 包含评估信息和答案
     * @return 操作结果
     */
    @PostMapping
    @Operation(summary = "新增评估", description = "创建新的健康评估")
    public Result<?> add(@RequestBody Map<String, Object> requestBody){
        Assessment assessment = (Assessment) requestBody.get("data");
        List<AssessmentAnswer> answers = (List<AssessmentAnswer>) requestBody.get("answers");
        Integer assessmentId = assessmentService.add(assessment, answers);
        return Result.success(MessageConstant.ADD_SUCCESS, assessmentId);
    }
    
    /**
     * 根据ID查询评估
     * @param id 评估ID
     * @return 评估信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询评估", description = "获取评估详情")
    public Result<Assessment> getById(@PathVariable Integer id){
        return Result.success(MessageConstant.QUERY_SUCCESS, assessmentService.getById(id));
    }
    
    /**
     * 根据评估ID查询答案
     * @param assessmentId 评估ID
     * @return 答案列表
     */
    @GetMapping("/answers/{assessmentId}")
    @Operation(summary = "查询评估答案", description = "获取评估的所有答案")
    public Result<List<AssessmentAnswer>> findAnswers(@PathVariable Integer assessmentId){
        return Result.success(MessageConstant.QUERY_SUCCESS, assessmentService.findAnswersByAssessmentId(assessmentId));
    }
    
    /**
     * 根据评估类型查询问卷问题
     * @param assessmentType 评估类型
     * @return 问题列表
     */
    @GetMapping("/questions/{assessmentType}")
    @Operation(summary = "查询评估问卷问题", description = "根据评估类型获取问卷问题")
    public Result<List<AssessmentQuestion>> findQuestions(@PathVariable Integer assessmentType){
        return Result.success(MessageConstant.QUERY_SUCCESS, assessmentService.findQuestionsByType(assessmentType));
    }
    
    /**
     * 分析评估结果
     * @param id 评估ID
     * @return 分析结果
     */
    @GetMapping("/analyze/{id}")
    @Operation(summary = "分析评估结果", description = "分析评估结果并生成建议")
    public Result<Map<String, Object>> analyze(@PathVariable Integer id){
        Assessment assessment = assessmentService.getById(id);
        List<AssessmentAnswer> answers = assessmentService.findAnswersByAssessmentId(id);
        Map<String, Object> analysisResult = assessmentService.analyzeAssessmentResult(assessment, answers);
        return Result.success(MessageConstant.QUERY_SUCCESS, analysisResult);
    }
    
    /**
     * 生成评估报告
     * @param id 评估ID
     * @return 报告内容
     */
    @GetMapping("/report/{id}")
    @Operation(summary = "生成评估报告", description = "生成详细的评估报告")
    public Result<Map<String, Object>> generateReport(@PathVariable Integer id){
        Map<String, Object> report = assessmentService.generateAssessmentReport(id);
        return Result.success(MessageConstant.QUERY_SUCCESS, report);
    }
    
    /**
     * 根据会员ID查询评估历史
     * @param memberId 会员ID
     * @return 评估列表
     */
    @GetMapping("/member/{memberId}")
    @Operation(summary = "查询会员评估历史", description = "获取会员的评估历史记录")
    public Result<List<Assessment>> findByMemberId(@PathVariable Integer memberId){
        return Result.success(MessageConstant.QUERY_SUCCESS, assessmentService.findByMemberId(memberId));
    }
    
}