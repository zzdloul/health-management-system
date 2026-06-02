package com.seecen.health.api.controller;

import com.seecen.health.api.request.RequestParams;
import com.seecen.health.common.constant.MessageConstant;
import com.seecen.health.common.entity.CheckGroup;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.common.result.Result;
import com.seecen.health.core.service.CheckGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 检查组管理控制器
 * 提供检查组的增删改查操作接口
 */
@RestController
@RequestMapping("/checkGroups")
@Tag(name="检查组管理", description = "检查组增删改查操作")
public class CheckGroupController {
    
    @Autowired
    private CheckGroupService checkGroupService;
    
    /**
     * 检查组分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果
     */
    @PostMapping("/page")
    @Operation(summary = "检查组分页查询", description = "根据条件分页查询检查组")
    public Result<PageResult<CheckGroup>> page(@RequestBody QueryPageBean queryPageBean){
        PageResult<CheckGroup> pageResult = checkGroupService.pageQuery(queryPageBean);
        return Result.success(MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
    }

    /**
     * 新增或更新检查组
     * @param requestParams 包含检查组信息和关联的检查项ID列表
     * @return 操作结果
     */
    @PutMapping
    @Operation(summary = "新增或更新检查组", description = "新增或更新检查组信息及关联关系")
    public Result<?> addorupdate(@RequestBody RequestParams<CheckGroup> requestParams){
        String message = checkGroupService.addorupdate(requestParams.getData(), requestParams.getIds());
        return Result.success(message);
    }
    
    /**
     * 删除检查组
     * @param id 检查组ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除检查组", description = "根据ID删除检查组")
    public Result<?> delete(@PathVariable Integer id){
        checkGroupService.deleteById(id);
        return Result.success(MessageConstant.DELETE_CHECKGROUP_SUCCESS);
    }
    
    /**
     * 根据ID查询检查组
     * @param id 检查组ID
     * @return 检查组信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询检查组", description = "根据ID查询检查组详情")
    public Result<CheckGroup> getById(@PathVariable Integer id){
        return Result.success(MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupService.getById(id));
    }

    /**
     * 根据检查组ID查询关联的检查项ID列表
     * @param id 检查组ID
     * @return 检查项ID列表
     */
    @GetMapping("/checkItemIds/{id}")
    @Operation(summary = "查询检查组关联的检查项ID", description = "获取当前检查组关联的所有检查项的ID")
    public Result<List<Integer>> findCheckItemIds(@PathVariable Integer id){
        return Result.success(MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupService.findCheckItemIdsByCheckGroupId(id));
    }
    
    /**
     * 查询所有检查组
     * @return 检查组列表
     */
    @GetMapping("/all")
    @Operation(summary = "查询所有检查组", description = "获取所有检查组列表")
    public Result<List<CheckGroup>> findAll(){
        return Result.success(MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroupService.findAll());
    }
}
