package com.seecen.health.api.controller;

import com.seecen.health.common.entity.Order;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.common.result.Result;
import com.seecen.health.core.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单控制器
 * 处理订单相关的HTTP请求
 */
@RestController
@RequestMapping("/orders")
@Tag(name = "订单管理", description = "订单相关的接口")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    /**
     * 订单分页查询
     * @param queryPageBean 查询条件
     * @return 分页结果
     */
    @PostMapping("/pageQuery")
    @Operation(summary = "订单分页查询", description = "根据查询条件分页获取订单列表")
    public Result<PageResult<Order>> pageQuery(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Order> result = orderService.pageQuery(queryPageBean);
        return Result.success("查询成功", result);
    }
    
    /**
     * 订单分页查询（GET请求）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param queryString 查询字符串
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "订单分页查询", description = "根据查询条件分页获取订单列表")
    public Result<PageResult<Order>> pageQuery(@RequestParam(defaultValue = "1") Integer pageNum, 
                                             @RequestParam(defaultValue = "10") Integer pageSize, 
                                             @RequestParam(required = false) String queryString) {
        QueryPageBean queryPageBean = new QueryPageBean();
        queryPageBean.setPageNum(pageNum);
        queryPageBean.setPageSize(pageSize);
        queryPageBean.setQueryString(queryString);
        PageResult<Order> result = orderService.pageQuery(queryPageBean);
        return Result.success("查询成功", result);
    }
    
    /**
     * 新增订单
     * @param order 订单信息
     * @return 操作结果
     */
    @PostMapping
    @Operation(summary = "新增订单", description = "添加新的订单信息")
    public Result<String> add(@RequestBody Order order) {
        orderService.add(order);
        return Result.success("新增订单成功");
    }
    
    /**
     * 更新订单
     * @param order 订单信息
     * @return 操作结果
     */
    @PutMapping
    @Operation(summary = "更新订单", description = "更新订单信息")
    public Result<String> update(@RequestBody Order order) {
        orderService.update(order);
        return Result.success("更新订单成功");
    }
    
    /**
     * 删除订单
     * @param id 订单ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除订单", description = "根据ID删除订单")
    public Result<String> deleteById(@PathVariable Integer id) {
        orderService.deleteById(id);
        return Result.success("删除订单成功");
    }
    
    /**
     * 根据ID查询订单
     * @param id 订单ID
     * @return 订单信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询订单详情", description = "根据ID查询订单详细信息")
    public Result<Order> getById(@PathVariable Integer id) {
        Order order = orderService.getById(id);
        return Result.success("查询成功", order);
    }
    
    /**
     * 查询所有订单
     * @return 订单列表
     */
    @GetMapping("/all")
    @Operation(summary = "查询所有订单", description = "获取所有订单列表")
    public Result<List<Order>> findAll() {
        List<Order> orders = orderService.findAll();
        return Result.success("查询成功", orders);
    }
    
    /**
     * 更新订单状态
     * @param id 订单ID
     * @param status 订单状态
     * @return 操作结果
     */
    @PutMapping("/status/{id}")
    @Operation(summary = "更新订单状态", description = "根据ID更新订单状态")
    public Result<String> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        orderService.updateStatus(id, status);
        return Result.success("更新订单状态成功");
    }
}
