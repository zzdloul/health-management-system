package com.seecen.health.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seecen.health.common.entity.OrderSetting;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.core.mapper.OrderSettingMapper;
import com.seecen.health.core.service.OrderSettingService;
import com.seecen.health.store.service.CacheService;
import com.seecen.health.store.utils.CacheKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 预约设置服务实现类
 * 实现OrderSettingService接口，提供预约设置相关的业务逻辑
 * 包含缓存支持，提升系统性能
 */
@Service
public class OrderSettingServiceImpl extends ServiceImpl<OrderSettingMapper, OrderSetting> implements OrderSettingService {
    
    @Autowired
    private OrderSettingMapper orderSettingMapper;
    
    @Autowired
    private CacheService cacheService;
    
    /**
     * 预约设置分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    @Override
    public PageResult<OrderSetting> pageQuery(QueryPageBean queryPageBean) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateListKey("ordersetting", 
                queryPageBean.getQueryString() != null ? queryPageBean.getQueryString() : "", 
                queryPageBean.getPageNum(), 
                queryPageBean.getPageSize());
        
        // 尝试从缓存获取
        PageResult<OrderSetting> pageResult = cacheService.get(cacheKey);
        if (pageResult != null) {
            return pageResult;
        }
        
        // 创建分页对象
        Page<OrderSetting> page = new Page<>(queryPageBean.getPageNum(), queryPageBean.getPageSize());
        
        // 构建查询条件
        QueryWrapper<OrderSetting> queryWrapper = new QueryWrapper<>();
        
        // 如果有查询字符串，添加模糊查询条件
        if (queryPageBean.getQueryString() != null && !queryPageBean.getQueryString().isEmpty()) {
            queryWrapper.like("orderDate", queryPageBean.getQueryString());
        }
        
        // 执行分页查询
        Page<OrderSetting> page1 = baseMapper.selectPage(page, queryWrapper);
        
        // 构建分页结果
        pageResult = new PageResult<>(page1.getTotal(), page1.getRecords());
        
        // 缓存结果，设置过期时间为5分钟
        cacheService.set(cacheKey, pageResult, 5, TimeUnit.MINUTES);
        
        return pageResult;
    }
    
    /**
     * 新增预约设置
     * @param orderSetting 预约设置信息
     */
    @Override
    public void add(OrderSetting orderSetting) {
        baseMapper.insert(orderSetting);
        // 清除相关缓存
        clearOrderSettingCache();
    }
    
    /**
     * 更新预约设置
     * @param orderSetting 预约设置信息
     */
    @Override
    public void update(OrderSetting orderSetting) {
        baseMapper.updateById(orderSetting);
        // 清除相关缓存
        cacheService.delete(CacheKeyGenerator.generateKey("ordersetting", orderSetting.getId()));
        clearOrderSettingCache();
    }
    
    /**
     * 根据ID删除预约设置
     * @param id 预约设置ID
     */
    @Override
    public void deleteById(Integer id) {
        baseMapper.deleteById(id);
        // 清除相关缓存
        cacheService.delete(CacheKeyGenerator.generateKey("ordersetting", id));
        clearOrderSettingCache();
    }
    
    /**
     * 根据ID查询预约设置
     * @param id 预约设置ID
     * @return 预约设置信息
     */
    @Override
    public OrderSetting getById(Integer id) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("ordersetting", id);
        
        // 尝试从缓存获取
        OrderSetting orderSetting = cacheService.get(cacheKey);
        if (orderSetting != null) {
            return orderSetting;
        }
        
        // 从数据库查询
        orderSetting = baseMapper.selectById(id);
        
        // 缓存结果，设置过期时间为10分钟
        if (orderSetting != null) {
            cacheService.set(cacheKey, orderSetting, 10, TimeUnit.MINUTES);
        }
        
        return orderSetting;
    }
    
    /**
     * 查询所有预约设置
     * @return 预约设置列表
     */
    @Override
    public List<OrderSetting> findAll() {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("ordersetting", "all");
        
        // 尝试从缓存获取
        List<OrderSetting> orderSettings = cacheService.get(cacheKey);
        if (orderSettings != null) {
            return orderSettings;
        }
        
        // 从数据库查询
        orderSettings = baseMapper.selectList(null);
        
        // 缓存结果，设置过期时间为10分钟
        if (orderSettings != null) {
            cacheService.set(cacheKey, orderSettings, 10, TimeUnit.MINUTES);
        }
        
        return orderSettings;
    }
    
    /**
     * 批量添加预约设置
     * @param orderSettings 预约设置列表
     */
    @Override
    public void batchAdd(List<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            baseMapper.insert(orderSetting);
        }
        // 清除相关缓存
        clearOrderSettingCache();
    }
    
    /**
     * 按日期范围查询预约设置
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 预约设置列表
     */
    @Override
    public List<OrderSetting> findByDateRange(String startDate, String endDate) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("ordersetting", "range", startDate, endDate);
        
        // 尝试从缓存获取
        List<OrderSetting> orderSettings = cacheService.get(cacheKey);
        if (orderSettings != null) {
            return orderSettings;
        }
        
        // 构建查询条件
        QueryWrapper<OrderSetting> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("orderDate", startDate).le("orderDate", endDate);
        
        // 从数据库查询
        orderSettings = baseMapper.selectList(queryWrapper);
        
        // 缓存结果，设置过期时间为10分钟
        if (orderSettings != null) {
            cacheService.set(cacheKey, orderSettings, 10, TimeUnit.MINUTES);
        }
        
        return orderSettings;
    }
    
    /**
     * 清除预约设置相关缓存
     */
    private void clearOrderSettingCache() {
        // 清除预约设置列表缓存
        cacheService.delete(CacheKeyGenerator.generateKey("ordersetting", "all"));
        // 清除所有与预约设置相关的缓存（实际生产环境中，建议使用更精确的缓存键管理）
        cacheService.deletePattern("ordersetting:*");
    }
}
