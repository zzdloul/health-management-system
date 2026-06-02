package com.seecen.health.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.seecen.health.common.entity.Assessment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 健康评估数据访问接口
 * 继承自MyBatis-Plus的BaseMapper，提供基础的CRUD操作
 */
public interface AssessmentMapper extends BaseMapper<Assessment> {
    
    /**
     * 根据会员ID查询评估历史
     * @param memberId 会员ID
     * @return 评估列表
     */
    List<Assessment> findByMemberId(@Param("memberId") Integer memberId);
    
}