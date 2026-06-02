package com.seecen.health.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seecen.health.common.entity.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 检查组数据访问接口
 * 继承自MyBatis-Plus的BaseMapper，提供基础的CRUD操作
 */
public interface CheckGroupMapper extends BaseMapper<CheckGroup> {
    
    /**
     * 检查检查组是否被套餐使用
     * @param checkGroupId 检查组ID
     * @return 使用次数
     */
    int countCheckGroupId(@Param("checkGroupId")Integer checkGroupId);
    
    /**
     * 根据检查组的id查询检查项id列表
     * @param checkGroupId 检查组ID
     * @return 检查项ID列表
     */
    List<Integer> findCheckItemIdsByCheckGroupId(@Param("checkGroupId")Integer checkGroupId);
    
    /**
     * 批量添加检查组和检查项的关联关系
     * @param checkGroupId 检查组ID
     * @param checkItemIds 检查项ID列表
     */
    void batchInsertCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkItemIds") List<Integer> checkItemIds);
    
    /**
     * 根据检查组的id删除检查项关联关系
     * @param checkGroupId 检查组ID
     */
    void deleteCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId);

}
