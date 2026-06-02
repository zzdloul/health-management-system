package com.seecen.health.api.controller;

import com.seecen.health.common.entity.MedicalReport;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.common.result.Result;
import com.seecen.health.core.service.MedicalReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 体检报告控制器
 * 处理体检报告相关的HTTP请求
 */
@RestController
@RequestMapping("/medicalReports")
@Tag(name = "体检报告管理", description = "体检报告相关的接口")
public class MedicalReportController {
    
    @Autowired
    private MedicalReportService medicalReportService;
    
    /**
     * 体检报告分页查询
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param queryString 查询字符串
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "体检报告分页查询", description = "根据查询条件分页获取体检报告列表")
    public Result<PageResult<MedicalReport>> pageQuery(@RequestParam(defaultValue = "1") Integer pageNum, 
                                             @RequestParam(defaultValue = "10") Integer pageSize, 
                                             @RequestParam(required = false) String queryString) {
        QueryPageBean queryPageBean = new QueryPageBean();
        queryPageBean.setPageNum(pageNum);
        queryPageBean.setPageSize(pageSize);
        queryPageBean.setQueryString(queryString);
        PageResult<MedicalReport> result = medicalReportService.pageQuery(queryPageBean);
        return Result.success("查询成功", result);
    }
    
    /**
     * 上传体检报告
     * @param file 上传的文件

     * @return 上传结果
     */
    @PostMapping("/upload")
    @Operation(summary = "上传体检报告", description = "上传体检报告文件")
    public Result<MedicalReport> upload(@RequestParam("file") MultipartFile file, 
                                       @RequestParam("memberId") Integer memberId, 
                                       @RequestParam("name") String name, 
                                       @RequestParam("description") String description) {
        try {
            MedicalReport medicalReport = new MedicalReport();
            medicalReport.setMemberId(memberId);
            medicalReport.setName(name);
            medicalReport.setDescription(description);
            MedicalReport result = medicalReportService.upload(file, medicalReport);
            return Result.success("上传成功", result);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error("上传失败");
        }
    }
    
    /**
     * 删除体检报告
     * @param id 体检报告ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除体检报告", description = "根据ID删除体检报告")
    public Result<String> deleteById(@PathVariable Integer id) {
        medicalReportService.deleteById(id);
        return Result.success("删除成功");
    }
    
    /**
     * 根据ID查询体检报告
     * @param id 体检报告ID
     * @return 体检报告信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询体检报告详情", description = "根据ID查询体检报告详细信息")
    public Result<MedicalReport> getById(@PathVariable Integer id) {
        MedicalReport medicalReport = medicalReportService.getById(id);
        return Result.success("查询成功", medicalReport);
    }
    
    /**
     * 查询所有体检报告
     * @return 体检报告列表
     */
    @GetMapping("/all")
    @Operation(summary = "查询所有体检报告", description = "获取所有体检报告列表")
    public Result<List<MedicalReport>> findAll() {
        List<MedicalReport> medicalReports = medicalReportService.findAll();
        return Result.success("查询成功", medicalReports);
    }
    
    /**
     * 根据会员ID查询体检报告
     * @param memberId 会员ID
     * @return 体检报告列表
     */
    @GetMapping("/member/{memberId}")
    @Operation(summary = "根据会员ID查询体检报告", description = "获取指定会员的所有体检报告")
    public Result<List<MedicalReport>> findByMemberId(@PathVariable Integer memberId) {
        List<MedicalReport> medicalReports = medicalReportService.findByMemberId(memberId);
        return Result.success("查询成功", medicalReports);
    }
    
    /**
     * 审核体检报告
     * @param id 体检报告ID
     * @param status 审核状态
     * @param reviewerId 审核人ID
     * @param reviewComment 审核意见
     * @return 操作结果
     */
    @PutMapping("/review/{id}")
    @Operation(summary = "审核体检报告", description = "审核体检报告并更新状态")
    public Result<String> review(@PathVariable Integer id, 
                                @RequestParam Integer status, 
                                @RequestParam Integer reviewerId, 
                                @RequestParam(required = false) String reviewComment) {
        medicalReportService.review(id, status, reviewerId, reviewComment);
        return Result.success("审核成功");
    }
}