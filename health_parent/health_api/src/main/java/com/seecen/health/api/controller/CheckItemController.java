package com.seecen.health.api.controller;

import com.seecen.health.common.constant.MessageConstant;
import com.seecen.health.common.entity.CheckItem;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.common.result.Result;
import com.seecen.health.core.service.CheckItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查项管理控制器
 * 提供检查项的增删改查操作接口
 */
@RestController
@RequestMapping("/checkItems")
@Tag(name="检查项管理", description = "检查项增删改查操作")
public class CheckItemController {
    
    @Autowired
    private CheckItemService checkItemService;
    
    /**
     * 检查项分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果
     */
    @PostMapping("/page")
    @Operation(summary = "检查项分页查询", description = "根据条件分页查询检查项")
    public Result<PageResult<CheckItem>> page(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckItem> pageResult = checkItemService.pageQuery(queryPageBean);
        return Result.success(MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
    }
    
    /**
     * 新增检查项
     * @param checkItem 检查项信息
     * @return 操作结果
     */
    @PostMapping
    @Operation(summary = "新增检查项", description = "创建新的检查项")
    public Result<?> add(@RequestBody CheckItem checkItem){
        checkItemService.add(checkItem);
        return Result.success(MessageConstant.ADD_CHECKITEM_SUCCESS);
    }

    /**
     * 删除检查项
     * @param id 检查项ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除检查项", description = "根据ID删除检查项")
    public Result<?> delete(@PathVariable Integer id){
        checkItemService.deleteById(id);
        return Result.success(MessageConstant.DELETE_CHECKITEM_SUCCESS);
    }
    
    /**
     * 更新检查项
     * @param checkItem 检查项信息
     * @return 操作结果
     */
    @PutMapping
    @Operation(summary = "更新检查项", description = "更新检查项信息")
    public Result<?> update(@RequestBody CheckItem checkItem){
        checkItemService.update(checkItem);
        return Result.success(MessageConstant.EDIT_CHECKITEM_SUCCESS);
    }
    
    /**
     * 查询所有检查项
     * @return 检查项列表
     */
    @GetMapping("/all")
    @Operation(summary = "查询所有检查项", description = "获取所有检查项列表")
    public Result<List<CheckItem>> findAll(){
        return Result.success(MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemService.findAll());
    }
}
