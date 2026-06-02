package com.seecen.health.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seecen.health.common.entity.MedicalReport;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.core.mapper.MedicalReportMapper;
import com.seecen.health.core.service.MedicalReportService;
import com.seecen.health.store.utils.OssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 体检报告服务实现类
 * 实现MedicalReportService接口，提供体检报告相关的业务逻辑
 */
@Service
public class MedicalReportServiceImpl extends ServiceImpl<MedicalReportMapper, MedicalReport> implements MedicalReportService {
    
    @Autowired
    private MedicalReportMapper medicalReportMapper;
    
    @Autowired(required = false)
    private OssUtil ossUtil;
    
    /**
     * 体检报告分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    @Override
    public PageResult<MedicalReport> pageQuery(QueryPageBean queryPageBean) {
        // 创建分页对象
        Page<MedicalReport> page = new Page<>(queryPageBean.getPageNum(), queryPageBean.getPageSize());
        
        // 构建查询条件
        QueryWrapper<MedicalReport> queryWrapper = new QueryWrapper<>();
        
        // 如果有查询字符串，添加模糊查询条件
        if (queryPageBean.getQueryString() != null && !queryPageBean.getQueryString().isEmpty()) {
            queryWrapper.like("name", queryPageBean.getQueryString())
                      .or().like("description", queryPageBean.getQueryString());
        }
        
        // 执行分页查询
        Page<MedicalReport> page1 = baseMapper.selectPage(page, queryWrapper);
        
        // 构建分页结果
        return new PageResult<>(page1.getTotal(), page1.getRecords());
    }
    
    /**
     * 上传体检报告
     * @param file 上传的文件
     * @param medicalReport 体检报告信息
     * @return 上传结果
     */
    @Override
    public MedicalReport upload(MultipartFile file, MedicalReport medicalReport) throws IOException {
        // 上传文件到OSS
        String filePath;
        if (ossUtil != null) {
            filePath = ossUtil.uploadMedicalReport(file);
        } else {
            // 如果OssUtil不可用，返回模拟的文件路径
            filePath = "/api/upload/medical-reports/" + file.getOriginalFilename();
        }
        
        // 设置报告信息
        medicalReport.setFilePath(filePath);
        medicalReport.setUploadTime(new Date());
        medicalReport.setStatus(0); // 初始状态为待审核
        
        // 保存到数据库
        baseMapper.insert(medicalReport);
        
        return medicalReport;
    }
    
    /**
     * 根据ID删除体检报告
     * @param id 体检报告ID
     */
    @Override
    public void deleteById(Integer id) {
        baseMapper.deleteById(id);
    }
    
    /**
     * 根据ID查询体检报告
     * @param id 体检报告ID
     * @return 体检报告信息
     */
    @Override
    public MedicalReport getById(Integer id) {
        return baseMapper.selectById(id);
    }
    
    /**
     * 查询所有体检报告
     * @return 体检报告列表
     */
    @Override
    public List<MedicalReport> findAll() {
        return baseMapper.selectList(null);
    }
    
    /**
     * 根据会员ID查询体检报告
     * @param memberId 会员ID
     * @return 体检报告列表
     */
    @Override
    public List<MedicalReport> findByMemberId(Integer memberId) {
        QueryWrapper<MedicalReport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberId);
        return baseMapper.selectList(queryWrapper);
    }
    
    /**
     * 审核体检报告
     * @param id 体检报告ID
     * @param status 审核状态
     * @param reviewerId 审核人ID
     * @param reviewComment 审核意见
     */
    @Override
    public void review(Integer id, Integer status, Integer reviewerId, String reviewComment) {
        MedicalReport medicalReport = baseMapper.selectById(id);
        if (medicalReport != null) {
            medicalReport.setStatus(status);
            medicalReport.setReviewerId(reviewerId);
            medicalReport.setReviewTime(new Date());
            medicalReport.setReviewComment(reviewComment);
            baseMapper.updateById(medicalReport);
        }
    }
}