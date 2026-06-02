package com.seecen.health.api.controller;

import com.seecen.health.common.entity.Member;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.common.result.Result;
import com.seecen.health.core.service.MemberStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 会员统计控制器
 * 处理会员统计相关的HTTP请求
 */
@RestController
@RequestMapping("/memberStatistics")
@Tag(name = "会员统计", description = "会员统计相关的接口")
public class MemberStatisticsController {
    
    @Autowired
    private MemberStatisticsService memberStatisticsService;
    
    /**
     * 会员数量统计
     * @return 会员总数
     */
    @GetMapping("/total")
    @Operation(summary = "会员数量统计", description = "获取会员总数")
    public Result<Long> countTotalMembers() {
        long total = memberStatisticsService.countTotalMembers();
        return Result.success("查询成功", total);
    }
    
    /**
     * 会员性别分布统计
     * @return 性别分布数据
     */
    @GetMapping("/gender")
    @Operation(summary = "会员性别分布统计", description = "获取会员性别分布数据")
    public Result<Map<String, Long>> countGenderDistribution() {
        Map<String, Long> genderMap = memberStatisticsService.countGenderDistribution();
        return Result.success("查询成功", genderMap);
    }
    
    /**
     * 会员年龄分布统计
     * @return 年龄分布数据
     */
    @GetMapping("/age")
    @Operation(summary = "会员年龄分布统计", description = "获取会员年龄分布数据")
    public Result<Map<String, Long>> countAgeDistribution() {
        Map<String, Long> ageMap = memberStatisticsService.countAgeDistribution();
        return Result.success("查询成功", ageMap);
    }
    
    /**
     * 会员月度增长统计
     * @param months 统计月份数
     * @return 月度增长数据
     */
    @GetMapping("/monthly")
    @Operation(summary = "会员月度增长统计", description = "获取会员月度增长数据")
    public Result<Map<String, Long>> countMonthlyGrowth(@RequestParam(defaultValue = "6") int months) {
        Map<String, Long> monthlyMap = memberStatisticsService.countMonthlyGrowth(months);
        return Result.success("查询成功", monthlyMap);
    }
    
    /**
     * 会员套餐使用统计
     * @return 套餐使用数据
     */
    @GetMapping("/setmeal")
    @Operation(summary = "会员套餐使用统计", description = "获取会员套餐使用数据")
    public Result<Map<String, Long>> countSetmealUsage() {
        Map<String, Long> setmealMap = memberStatisticsService.countSetmealUsage();
        return Result.success("查询成功", setmealMap);
    }
    
    /**
     * 会员活跃度统计
     * @return 活跃度分布数据
     */
    @GetMapping("/activity")
    @Operation(summary = "会员活跃度统计", description = "获取会员活跃度分布数据")
    public Result<Map<String, Long>> countActivityLevel() {
        Map<String, Long> activityMap = memberStatisticsService.countActivityLevel();
        return Result.success("查询成功", activityMap);
    }
    
    /**
     * 会员分页查询（支持统计筛选）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param queryString 查询字符串
     * @return 分页结果
     */
    @GetMapping("/page")
    @Operation(summary = "会员分页查询", description = "根据查询条件分页获取会员列表")
    public Result<PageResult<Member>> pageQuery(@RequestParam(defaultValue = "1") Integer pageNum, 
                                             @RequestParam(defaultValue = "10") Integer pageSize, 
                                             @RequestParam(required = false) String queryString) {
        QueryPageBean queryPageBean = new QueryPageBean();
        queryPageBean.setPageNum(pageNum);
        queryPageBean.setPageSize(pageSize);
        queryPageBean.setQueryString(queryString);
        PageResult<Member> result = memberStatisticsService.pageQuery(queryPageBean);
        return Result.success("查询成功", result);
    }
}