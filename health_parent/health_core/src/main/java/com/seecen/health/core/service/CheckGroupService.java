package com.seecen.health.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seecen.health.common.entity.CheckGroup;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;

import java.util.List;

public interface CheckGroupService extends IService<CheckGroup> {
    // 分页查询
    PageResult<CheckGroup> pageQuery(QueryPageBean queryPageBean);


    /**
     * 更新检查组
     * （1）先更新检查组的基础信息
     * （2）删除旧的关系
     * （3）更新检查组和检查项的关系列表
     * @param checkGroup 检查组基础信息
     * @param checkItemIds 要关联的检查项ID集合
     */

    // 更新检查组  有id更新，无id新增
    String addorupdate(CheckGroup checkGroup,List<Integer>checkItemIds);
    // 删除检查组
    void deleteById(Integer id);

    // 根据id查询检查组
    CheckGroup getById(Integer id);

    // 根据检查组id查询 检查项id列表
// 根据检查组的id 查询 检查项 id 列表（当前检查组关联的所有检查项 id）
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);


    /**
     * 查询所有检查组
     * @return
     */
    List<CheckGroup> findAll();

}
