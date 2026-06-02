package com.seecen.health.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.seecen.health.common.entity.Member;
import com.seecen.health.common.entity.Order;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.core.mapper.MemberMapper;
import com.seecen.health.core.mapper.OrderMapper;
import com.seecen.health.core.service.MemberStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员统计服务实现类
 * 实现MemberStatisticsService接口，提供会员统计相关的业务逻辑
 */
@Service
public class MemberStatisticsServiceImpl implements MemberStatisticsService {
    
    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired
    private OrderMapper orderMapper;
    
    /**
     * 会员数量统计
     * @return 会员总数
     */
    @Override
    public long countTotalMembers() {
        return memberMapper.selectCount(null);
    }
    
    /**
     * 会员性别分布统计
     * @return 性别分布数据，key为性别，value为数量
     */
    @Override
    public Map<String, Long> countGenderDistribution() {
        Map<String, Long> genderMap = new HashMap<>();
        
        // 统计男性会员数量
        QueryWrapper<Member> maleWrapper = new QueryWrapper<>();
        maleWrapper.eq("sex", "男");
        long maleCount = memberMapper.selectCount(maleWrapper);
        genderMap.put("男", maleCount);
        
        // 统计女性会员数量
        QueryWrapper<Member> femaleWrapper = new QueryWrapper<>();
        femaleWrapper.eq("sex", "女");
        long femaleCount = memberMapper.selectCount(femaleWrapper);
        genderMap.put("女", femaleCount);
        
        return genderMap;
    }
    
    /**
     * 会员年龄分布统计
     * @return 年龄分布数据，key为年龄段，value为数量
     */
    @Override
    public Map<String, Long> countAgeDistribution() {
        Map<String, Long> ageMap = new HashMap<>();
        
        // 统计各年龄段会员数量
        List<Member> members = memberMapper.selectList(null);
        for (Member member : members) {
            if (member.getBirthday() != null) {
                // 根据出生日期计算年龄
                int age = calculateAge(member.getBirthday());
                String ageRange;
                if (age < 18) {
                    ageRange = "0-17岁";
                } else if (age < 30) {
                    ageRange = "18-29岁";
                } else if (age < 45) {
                    ageRange = "30-44岁";
                } else if (age < 60) {
                    ageRange = "45-59岁";
                } else {
                    ageRange = "60岁及以上";
                }
                ageMap.put(ageRange, ageMap.getOrDefault(ageRange, 0L) + 1);
            }
        }
        
        return ageMap;
    }
    
    /**
     * 根据出生日期计算年龄
     * @param birthday 出生日期
     * @return 年龄
     */
    private int calculateAge(Date birthday) {
        LocalDate birthDate = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
    
    /**
     * 会员月度增长统计
     * @param months 统计月份数
     * @return 月度增长数据，key为月份，value为新增会员数
     */
    @Override
    public Map<String, Long> countMonthlyGrowth(int months) {
        Map<String, Long> monthlyMap = new HashMap<>();
        
        // 计算开始日期
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(months - 1);
        
        // 统计每个月的新增会员数
        while (!startDate.isAfter(endDate)) {
            String monthStr = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
            QueryWrapper<Member> wrapper = new QueryWrapper<>();
            wrapper.apply("DATE_FORMAT(reg_time, '%Y-%m') = {0}", monthStr);
            long count = memberMapper.selectCount(wrapper);
            monthlyMap.put(monthStr, count);
            startDate = startDate.plusMonths(1);
        }
        
        return monthlyMap;
    }
    
    /**
     * 会员套餐使用统计
     * @return 套餐使用数据，key为套餐ID，value为使用次数
     */
    @Override
    public Map<String, Long> countSetmealUsage() {
        Map<String, Long> setmealMap = new HashMap<>();
        
        // 统计各套餐的使用次数
        List<Order> orders = orderMapper.selectList(null);
        for (Order order : orders) {
            if (order.getSetmealId() != null) {
                setmealMap.put(String.valueOf(order.getSetmealId()), setmealMap.getOrDefault(String.valueOf(order.getSetmealId()), 0L) + 1);
            }
        }
        
        return setmealMap;
    }
    
    /**
     * 会员活跃度统计
     * @return 活跃度分布数据，key为活跃度等级，value为会员数
     */
    @Override
    public Map<String, Long> countActivityLevel() {
        Map<String, Long> activityMap = new HashMap<>();
        
        // 统计各活跃度等级的会员数
        List<Member> members = memberMapper.selectList(null);
        for (Member member : members) {
            // 根据会员的订单数量判断活跃度
            QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("member_id", member.getId());
            long orderCount = orderMapper.selectCount(wrapper);
            
            String activityLevel;
            if (orderCount == 0) {
                activityLevel = "低活跃度";
            } else if (orderCount < 3) {
                activityLevel = "中活跃度";
            } else {
                activityLevel = "高活跃度";
            }
            activityMap.put(activityLevel, activityMap.getOrDefault(activityLevel, 0L) + 1);
        }
        
        return activityMap;
    }
    
    /**
     * 会员分页查询（支持统计筛选）
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    @Override
    public PageResult<Member> pageQuery(QueryPageBean queryPageBean) {
        // 创建分页对象
        Page<Member> page = new Page<>(queryPageBean.getPageNum(), queryPageBean.getPageSize());
        
        // 构建查询条件
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        
        // 如果有查询字符串，添加模糊查询条件
        if (queryPageBean.getQueryString() != null && !queryPageBean.getQueryString().isEmpty()) {
            queryWrapper.like("name", queryPageBean.getQueryString())
                      .or().like("phoneNumber", queryPageBean.getQueryString())
                      .or().like("idCard", queryPageBean.getQueryString());
        }
        
        // 执行分页查询
        Page<Member> page1 = memberMapper.selectPage(page, queryWrapper);
        
        // 构建分页结果
        return new PageResult<>(page1.getTotal(), page1.getRecords());
    }
}