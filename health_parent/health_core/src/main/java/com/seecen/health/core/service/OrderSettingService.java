package com.seecen.health.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seecen.health.common.entity.OrderSetting;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;

import java.util.List;

/**
 * 预约设置服务接口
 * 定义预约设置相关的业务逻辑规范
 */
public interface OrderSettingService extends IService<OrderSetting> {
    
    /**
     * 预约设置分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    PageResult<OrderSetting> pageQuery(QueryPageBean queryPageBean);
    
    /**
     * 新增预约设置
     * @param orderSetting 预约设置信息
     */
    void add(OrderSetting orderSetting);
    
    /**
     * 更新预约设置
     * @param orderSetting 预约设置信息
     */
    void update(OrderSetting orderSetting);
    
    /**
     * 根据ID删除预约设置
     * @param id 预约设置ID
     */
    void deleteById(Integer id);
    
    /**
     * 根据ID查询预约设置
     * @param id 预约设置ID
     * @return 预约设置信息
     */
    OrderSetting getById(Integer id);
    
    /**
     * 查询所有预约设置
     * @return 预约设置列表
     */
    List<OrderSetting> findAll();
    
    /**
     * 批量添加预约设置
     * @param orderSettings 预约设置列表
     */
    void batchAdd(List<OrderSetting> orderSettings);
    
    /**
     * 按日期范围查询预约设置
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 预约设置列表
     */
    List<OrderSetting> findByDateRange(String startDate, String endDate);
}
