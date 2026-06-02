package com.seecen.health.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seecen.health.common.entity.CheckItem;
import org.apache.ibatis.annotations.Param;

/**
 * 检查项数据访问接口
 * 继承自MyBatis-Plus的BaseMapper，提供基础的CRUD操作
 */
public interface CheckItemMapper extends BaseMapper<CheckItem> {
    
    /**
     * 检查检查项是否被检查组使用
     * @param checkItemId 检查项ID
     * @return 使用次数
     */
    int countByCheckItemId(@Param("checkItemId")Integer checkItemId);
}
