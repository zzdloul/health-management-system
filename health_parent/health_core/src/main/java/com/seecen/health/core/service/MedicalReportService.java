package com.seecen.health.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seecen.health.common.entity.MedicalReport;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 体检报告服务接口
 * 定义体检报告相关的业务逻辑规范
 */
public interface MedicalReportService extends IService<MedicalReport> {
    
    /**
     * 体检报告分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    PageResult<MedicalReport> pageQuery(QueryPageBean queryPageBean);
    
    /**
     * 上传体检报告
     * @param file 上传的文件
     * @param medicalReport 体检报告信息
     * @return 上传结果
     */
    MedicalReport upload(MultipartFile file, MedicalReport medicalReport) throws IOException;
    
    /**
     * 根据ID删除体检报告
     * @param id 体检报告ID
     */
    void deleteById(Integer id);
    
    /**
     * 根据ID查询体检报告
     * @param id 体检报告ID
     * @return 体检报告信息
     */
    MedicalReport getById(Integer id);
    
    /**
     * 查询所有体检报告
     * @return 体检报告列表
     */
    List<MedicalReport> findAll();
    
    /**
     * 根据会员ID查询体检报告
     * @param memberId 会员ID
     * @return 体检报告列表
     */
    List<MedicalReport> findByMemberId(Integer memberId);
    
    /**
     * 审核体检报告
     * @param id 体检报告ID
     * @param status 审核状态
     * @param reviewerId 审核人ID
     * @param reviewComment 审核意见
     */
    void review(Integer id, Integer status, Integer reviewerId, String reviewComment);
}