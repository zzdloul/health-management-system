package com.seecen.health.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seecen.health.common.entity.Order;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.core.mapper.OrderMapper;
import com.seecen.health.core.service.OrderService;
import com.seecen.health.store.service.CacheService;
import com.seecen.health.store.utils.CacheKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 订单服务实现类
 * 实现OrderService接口，提供订单相关的业务逻辑
 * 包含缓存支持，提升系统性能
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private CacheService cacheService;
    
    /**
     * 订单分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    @Override
    public PageResult<Order> pageQuery(QueryPageBean queryPageBean) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateListKey("order", 
                queryPageBean.getQueryString() != null ? queryPageBean.getQueryString() : "", 
                queryPageBean.getPageNum(), 
                queryPageBean.getPageSize());
        
        // 尝试从缓存获取
        PageResult<Order> pageResult = cacheService.get(cacheKey);
        if (pageResult != null) {
            return pageResult;
        }
        
        // 创建分页对象
        Page<Order> page = new Page<>(queryPageBean.getPageNum(), queryPageBean.getPageSize());
        
        // 构建查询条件
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        
        // 如果有查询字符串，添加模糊查询条件
        if (queryPageBean.getQueryString() != null && !queryPageBean.getQueryString().isEmpty()) {
            queryWrapper.like("orderDate", queryPageBean.getQueryString())
                    .or().like("orderType", queryPageBean.getQueryString())
                    .or().like("orderStatus", queryPageBean.getQueryString());
        }
        
        // 执行分页查询
        Page<Order> page1 = baseMapper.selectPage(page, queryWrapper);
        
        // 构建分页结果
        pageResult = new PageResult<>(page1.getTotal(), page1.getRecords());
        
        // 缓存结果，设置过期时间为5分钟
        cacheService.set(cacheKey, pageResult, 5, TimeUnit.MINUTES);
        
        return pageResult;
    }
    
    /**
     * 新增订单
     * @param order 订单信息
     */
    @Override
    public void add(Order order) {
        baseMapper.insert(order);
        // 清除相关缓存
        clearOrderCache();
    }
    
    /**
     * 更新订单
     * @param order 订单信息
     */
    @Override
    public void update(Order order) {
        baseMapper.updateById(order);
        // 清除相关缓存
        cacheService.delete(CacheKeyGenerator.generateKey("order", order.getId()));
        clearOrderCache();
    }
    
    /**
     * 根据ID删除订单
     * @param id 订单ID
     */
    @Override
    public void deleteById(Integer id) {
        baseMapper.deleteById(id);
        // 清除相关缓存
        cacheService.delete(CacheKeyGenerator.generateKey("order", id));
        clearOrderCache();
    }
    
    /**
     * 根据ID查询订单
     * @param id 订单ID
     * @return 订单信息
     */
    @Override
    public Order getById(Integer id) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("order", id);
        
        // 尝试从缓存获取
        Order order = cacheService.get(cacheKey);
        if (order != null) {
            return order;
        }
        
        // 从数据库查询
        order = baseMapper.selectById(id);
        
        // 缓存结果，设置过期时间为10分钟
        if (order != null) {
            cacheService.set(cacheKey, order, 10, TimeUnit.MINUTES);
        }
        
        return order;
    }
    
    /**
     * 查询所有订单
     * @return 订单列表
     */
    @Override
    public List<Order> findAll() {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("order", "all");
        
        // 尝试从缓存获取
        List<Order> orders = cacheService.get(cacheKey);
        if (orders != null) {
            return orders;
        }
        
        // 从数据库查询
        orders = baseMapper.selectList(null);
        
        // 缓存结果，设置过期时间为10分钟
        if (orders != null) {
            cacheService.set(cacheKey, orders, 10, TimeUnit.MINUTES);
        }
        
        return orders;
    }
    
    /**
     * 更新订单状态
     * @param id 订单ID
     * @param status 订单状态
     */
    @Override
    public void updateStatus(Integer id, String status) {
        Order order = new Order();
        order.setId(id);
        order.setOrderStatus(status);
        baseMapper.updateById(order);
        // 清除相关缓存
        cacheService.delete(CacheKeyGenerator.generateKey("order", id));
        clearOrderCache();
    }
    
    /**
     * 清除订单相关缓存
     */
    private void clearOrderCache() {
        // 清除订单列表缓存
        cacheService.delete(CacheKeyGenerator.generateKey("order", "all"));
        // 实际生产环境中，建议使用更精确的缓存键管理
    }
}
