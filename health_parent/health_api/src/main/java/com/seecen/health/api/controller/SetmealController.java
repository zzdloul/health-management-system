package com.seecen.health.api.controller;

import com.seecen.health.api.request.RequestParams;
import com.seecen.health.common.constant.MessageConstant;
import com.seecen.health.common.entity.Setmeal;
import com.seecen.health.common.exception.BusinessException;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.common.result.Result;
import com.seecen.health.core.service.SetmealService;
import com.seecen.health.store.utils.OssUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * 套餐管理控制器
 * 提供套餐的增删改查操作接口
 */
@RestController
@RequestMapping("/setmeals")
@Tag(name = "检查套餐管理", description = "检查套餐增删改查操作")
@CrossOrigin
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
    
    @Autowired(required = false)
    private OssUtil ossUtil;

    /**
     * 套餐分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果
     */
    @PostMapping("/page")
    @Operation(summary = "检查套餐分页查询", description = "根据条件分页查询检查套餐列表")
    public Result<PageResult<Setmeal>> page(@RequestBody QueryPageBean queryPageBean){
        PageResult<Setmeal> pageResult = setmealService.pageQuery(queryPageBean);
        return Result.success(MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);
    }

    /**
     * 新增或更新套餐
     * @param requestParams 包含套餐信息和关联的检查组ID列表
     * @return 操作结果
     */
    @PutMapping
    @Operation(summary = "新增和更新检查套餐", description = "新增和更新检查套餐信息及关联关系")
    public Result<?> addOrUpdate(@RequestBody RequestParams<Setmeal> requestParams){
        String message = setmealService.addOrUpdate(requestParams.getData(), requestParams.getIds());
        return Result.success(message);
    }

    /**
     * 删除套餐
     * @param id 套餐ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除检查套餐", description = "根据ID删除检查套餐")
    public Result<?> delete(@PathVariable Integer id){
        setmealService.deleteById(id);
        return Result.success(MessageConstant.DELETE_SETMEAL_SUCCESS);
    }
    
    /**
     * 根据ID查询套餐
     * @param id 套餐ID
     * @return 套餐信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询检查套餐", description = "根据ID查询检查套餐详情")
    public Result<Setmeal> getById(@PathVariable Integer id){
        return Result.success(MessageConstant.QUERY_SETMEAL_SUCCESS, setmealService.getById(id));
    }

    /**
     * 根据套餐ID查询关联的检查组ID列表
     * @param id 套餐ID
     * @return 检查组ID列表
     */
    @GetMapping("/checkGroupIds/{id}")
    @Operation(summary = "查询套餐关联的检查组ID", description = "获取当前检查套餐关联的所有检查组ID")
    public Result<List<Integer>> findCheckGroupIds(@PathVariable Integer id){
        return Result.success(MessageConstant.QUERY_SETMEAL_SUCCESS, setmealService.findCheckGroupIdsBySetmealId(id));
    }

    /**
     * 查询所有套餐
     * @return 套餐列表
     */
    @GetMapping("/all")
    @Operation(summary = "查询所有检查套餐", description = "获取所有检查套餐列表")
    public Result<List<Setmeal>> findAll(){
        return Result.success(MessageConstant.QUERY_SETMEAL_SUCCESS, setmealService.findAll());
    }
    
    /**
     * 图片上传
     * @param file 图片文件
     * @return 上传结果和图片URL
     */
    @PostMapping("/upload")
    @Operation(summary = "上传套餐图片", description = "上传套餐图片到OSS")
    public Result<String> upload(@RequestParam("file") MultipartFile file){
        try {
            if (ossUtil == null) {
                // 如果OssUtil不可用，返回模拟的图片URL
                return Result.success(MessageConstant.UPLOAD_SUCCESS, "/api/upload/setmeal-images/" + file.getOriginalFilename());
            }
            String imgUrl = ossUtil.uploadSetmealImage(file);
            return Result.success(MessageConstant.UPLOAD_SUCCESS, imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(MessageConstant.UPLOAD_FAIL);
        }
    }
    
    /**
     * 更新套餐状态
     * @param id 套餐ID
     * @param status 套餐状态
     * @return 操作结果
     */
    @PutMapping("/status/{id}")
    @Operation(summary = "更新套餐状态", description = "根据ID更新套餐状态")
    public Result<String> updateStatus(@PathVariable Integer id, @RequestParam Integer status) {
        setmealService.updateStatus(id, status);
        return Result.success("更新套餐状态成功");
    }
    
    /**
     * 更新套餐排序
     * @param id 套餐ID
     * @param sort 套餐排序
     * @return 操作结果
     */
    @PutMapping("/sort/{id}")
    @Operation(summary = "更新套餐排序", description = "根据ID更新套餐排序")
    public Result<String> updateSort(@PathVariable Integer id, @RequestParam Integer sort) {
        setmealService.updateSort(id, sort);
        return Result.success("更新套餐排序成功");
    }

}


