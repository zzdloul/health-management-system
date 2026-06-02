package com.seecen.health.api.controller;

import com.seecen.health.common.entity.OrderSetting;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.common.result.Result;
import com.seecen.health.core.service.OrderSettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预约设置控制器
 * 处理预约设置相关的HTTP请求
 */
@RestController
@RequestMapping("/ordersettings")
@Tag(name = "预约设置管理", description = "预约设置相关的接口")
public class OrderSettingController {
    
    @Autowired
    private OrderSettingService orderSettingService;
    
    /**
     * 预约设置分页查询
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param queryString 查询字符串
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "预约设置分页查询", description = "根据查询条件分页获取预约设置列表")
    public Result<PageResult<OrderSetting>> pageQuery(@RequestParam(defaultValue = "1") Integer pageNum, 
                                             @RequestParam(defaultValue = "10") Integer pageSize, 
                                             @RequestParam(required = false) String queryString) {
        QueryPageBean queryPageBean = new QueryPageBean();
        queryPageBean.setPageNum(pageNum);
        queryPageBean.setPageSize(pageSize);
        queryPageBean.setQueryString(queryString);
        PageResult<OrderSetting> result = orderSettingService.pageQuery(queryPageBean);
        return Result.success("查询成功", result);
    }
    
    /**
     * 新增预约设置
     * @param orderSetting 预约设置信息
     * @return 操作结果
     */
    @PostMapping
    @Operation(summary = "新增预约设置", description = "添加新的预约设置信息")
    public Result<String> add(@RequestBody OrderSetting orderSetting) {
        orderSettingService.add(orderSetting);
        return Result.success("新增预约设置成功");
    }
    
    /**
     * 更新预约设置
     * @param orderSetting 预约设置信息
     * @return 操作结果
     */
    @PutMapping
    @Operation(summary = "更新预约设置", description = "更新预约设置信息")
    public Result<String> update(@RequestBody OrderSetting orderSetting) {
        orderSettingService.update(orderSetting);
        return Result.success("更新预约设置成功");
    }
    
    /**
     * 删除预约设置
     * @param id 预约设置ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除预约设置", description = "根据ID删除预约设置")
    public Result<String> deleteById(@PathVariable Integer id) {
        orderSettingService.deleteById(id);
        return Result.success("删除预约设置成功");
    }
    
    /**
     * 根据ID查询预约设置
     * @param id 预约设置ID
     * @return 预约设置信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询预约设置详情", description = "根据ID查询预约设置详细信息")
    public Result<OrderSetting> getById(@PathVariable Integer id) {
        OrderSetting orderSetting = orderSettingService.getById(id);
        return Result.success("查询成功", orderSetting);
    }
    
    /**
     * 查询所有预约设置
     * @return 预约设置列表
     */
    @GetMapping("/all")
    @Operation(summary = "查询所有预约设置", description = "获取所有预约设置列表")
    public Result<List<OrderSetting>> findAll() {
        List<OrderSetting> orderSettings = orderSettingService.findAll();
        return Result.success("查询成功", orderSettings);
    }
    
    /**
     * 批量添加预约设置
     * @param orderSettings 预约设置列表
     * @return 操作结果
     */
    @PostMapping("/batch")
    @Operation(summary = "批量添加预约设置", description = "批量添加预约设置信息")
    public Result<String> batchAdd(@RequestBody List<OrderSetting> orderSettings) {
        orderSettingService.batchAdd(orderSettings);
        return Result.success("批量添加预约设置成功");
    }
    
    /**
     * 按日期范围查询预约设置
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 预约设置列表
     */
    @GetMapping("/dateRange")
    @Operation(summary = "按日期范围查询预约设置", description = "根据日期范围查询预约设置列表")
    public Result<List<OrderSetting>> findByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        List<OrderSetting> orderSettings = orderSettingService.findByDateRange(startDate, endDate);
        return Result.success("查询成功", orderSettings);
    }
}
