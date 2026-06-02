package com.seecen.health.core.service;

import com.seecen.health.common.entity.Member;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;

import java.util.Map;

/**
 * 会员统计服务接口
 * 定义会员统计相关的业务逻辑规范
 */
public interface MemberStatisticsService {
    
    /**
     * 会员数量统计
     * @return 会员总数
     */
    long countTotalMembers();
    
    /**
     * 会员性别分布统计
     * @return 性别分布数据，key为性别，value为数量
     */
    Map<String, Long> countGenderDistribution();
    
    /**
     * 会员年龄分布统计
     * @return 年龄分布数据，key为年龄段，value为数量
     */
    Map<String, Long> countAgeDistribution();
    
    /**
     * 会员月度增长统计
     * @param months 统计月份数
     * @return 月度增长数据，key为月份，value为新增会员数
     */
    Map<String, Long> countMonthlyGrowth(int months);
    
    /**
     * 会员套餐使用统计
     * @return 套餐使用数据，key为套餐名称，value为使用次数
     */
    Map<String, Long> countSetmealUsage();
    
    /**
     * 会员活跃度统计
     * @return 活跃度分布数据，key为活跃度等级，value为会员数
     */
    Map<String, Long> countActivityLevel();
    
    /**
     * 会员分页查询（支持统计筛选）
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    PageResult<Member> pageQuery(QueryPageBean queryPageBean);
}