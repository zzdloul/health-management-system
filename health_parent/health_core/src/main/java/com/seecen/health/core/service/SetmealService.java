package com.seecen.health.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seecen.health.common.entity.CheckGroup;
import com.seecen.health.common.entity.Setmeal;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;

import java.util.List;

/**
 * 检查套餐服务接口：定义 检查套餐业务规范
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * 分页查询
     */
    PageResult<Setmeal> pageQuery(QueryPageBean queryPageBean);
    /**
     * 新增或更新检查组
     * 1、先更新检查组的基础信息
     * 2、删除旧的关系
     * 3、更新检查组和检查项的关系列表
     * @param setmeal
     * @param checkGroupIds
     */
    String addOrUpdate(Setmeal setmeal,List<Integer> checkGroupIds);

    //删除检查套餐
    void deleteById(Integer setmealId);

    //根据套餐id查询检查组
    Setmeal getById(Integer setmealId);

    //根据检查套餐id查询 检查组id列表
    List<Integer> findCheckGroupIdsBySetmealId(Integer SetmealId);

    /**
     * 查询所有检查套餐
     * @return
     */
    List<Setmeal> findAll();
    
    /**
     * 更新套餐状态
     * @param id 套餐ID
     * @param status 套餐状态
     */
    void updateStatus(Integer id, Integer status);
    
    /**
     * 更新套餐排序
     * @param id 套餐ID
     * @param sort 套餐排序
     */
    void updateSort(Integer id, Integer sort);
}