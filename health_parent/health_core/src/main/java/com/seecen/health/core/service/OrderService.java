package com.seecen.health.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seecen.health.common.entity.Order;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;

import java.util.List;

/**
 * 订单服务接口
 * 定义订单相关的业务逻辑规范
 */
public interface OrderService extends IService<Order> {
    
    /**
     * 订单分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    PageResult<Order> pageQuery(QueryPageBean queryPageBean);
    
    /**
     * 新增订单
     * @param order 订单信息
     */
    void add(Order order);
    
    /**
     * 更新订单
     * @param order 订单信息
     */
    void update(Order order);
    
    /**
     * 根据ID删除订单
     * @param id 订单ID
     */
    void deleteById(Integer id);
    
    /**
     * 根据ID查询订单
     * @param id 订单ID
     * @return 订单信息
     */
    Order getById(Integer id);
    
    /**
     * 查询所有订单
     * @return 订单列表
     */
    List<Order> findAll();
    
    /**
     * 更新订单状态
     * @param id 订单ID
     * @param status 订单状态
     */
    void updateStatus(Integer id, String status);
}
