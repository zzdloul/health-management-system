package com.seecen.health.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seecen.health.common.entity.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 套餐数据访问接口
 * 继承自MyBatis-Plus的BaseMapper，提供基础的CRUD操作
 */
public interface SetmealMapper extends BaseMapper<Setmeal> {
    
    /**
     * 检查套餐是否关联订单
     * @param setmealId 套餐ID
     * @return 关联订单数
     */
    int countBySetmealId(@Param("setmealId")Integer setmealId);
    
    /**
     * 根据套餐的id查询检查组id列表
     * @param setmealId 套餐ID
     * @return 检查组ID列表
     */
    List<Integer> findCheckGroupIdsBySetmealId(@Param("setmealId")Integer setmealId);
    
    /**
     * 批量添加套餐和检查组的关联关系
     * @param setmealId 套餐ID
     * @param checkGroupIds 检查组ID列表
     */
    void batchInsertSetmealCheckGroup(@Param("setmealId") Integer setmealId, @Param("checkGroupIds") List<Integer> checkGroupIds);
    
    /**
     * 根据套餐id删除检查组关系
     * @param setmealId 套餐ID
     */
    void deleteSetmealCheckGroup(@Param("setmealId") Integer setmealId);

}
