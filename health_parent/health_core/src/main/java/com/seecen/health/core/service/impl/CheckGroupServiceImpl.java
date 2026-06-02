package com.seecen.health.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seecen.health.common.constant.MessageConstant;
import com.seecen.health.common.entity.CheckGroup;
import com.seecen.health.common.exception.BusinessException;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.core.mapper.CheckGroupMapper;
import com.seecen.health.core.service.CheckGroupService;
import com.seecen.health.store.service.CacheService;
import com.seecen.health.store.utils.CacheKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 检查组服务实现类
 * 实现CheckGroupService接口，提供检查组相关的业务逻辑
 */
@Service
public class CheckGroupServiceImpl extends ServiceImpl<CheckGroupMapper, CheckGroup> implements CheckGroupService {
    
    @Autowired
    private CheckGroupMapper checkGroupMapper;
    
    @Autowired
    private CacheService cacheService;

    /**
     * 检查组分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    @Override
    public PageResult<CheckGroup> pageQuery(QueryPageBean queryPageBean) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateListKey("checkgroup", 
                queryPageBean.getQueryString() != null ? queryPageBean.getQueryString() : "", 
                queryPageBean.getPageNum(), 
                queryPageBean.getPageSize());
        
        // 尝试从缓存获取
        PageResult<CheckGroup> pageResult = cacheService.get(cacheKey);
        if (pageResult != null) {
            return pageResult;
        }
        
        // 1、创建分页对象
        Page<CheckGroup> page = new Page<>(queryPageBean.getPageNum(), queryPageBean.getPageSize());

        // 2、构建查询条件
        QueryWrapper<CheckGroup> queryWrapper = new QueryWrapper<>();
        
        // 如果有查询字符串，添加模糊查询条件
        if (queryPageBean.getQueryString() != null && !queryPageBean.getQueryString().isEmpty()) {
            queryWrapper.like("helpCode", queryPageBean.getQueryString())
                    .or().like("name", queryPageBean.getQueryString())
                    .or().like("remark", queryPageBean.getQueryString());
        }

        // 3、执行分页查询
        Page page1 = baseMapper.selectPage(page, queryWrapper);

        // 构建分页结果
        pageResult = new PageResult<>(page1.getTotal(), page1.getRecords());
        
        // 缓存结果，设置过期时间为5分钟
        cacheService.set(cacheKey, pageResult, 5, TimeUnit.MINUTES);

        return pageResult;
    }

    /**
     * 新增或更新检查组
     * @param checkGroup 检查组信息
     * @param checkItemIds 关联的检查项ID列表
     * @return 操作结果消息
     */
    @Transactional
    @Override
    public String addorupdate(CheckGroup checkGroup, List<Integer> checkItemIds) {
        String message = MessageConstant.EDIT_CHECKGROUP_SUCCESS;
        
        if (checkGroup.getId() == null) {
            // 新增检查组
            baseMapper.insert(checkGroup);
            message = MessageConstant.ADD_CHECKGROUP_SUCCESS;
        } else {
            // 更新检查组
            baseMapper.updateById(checkGroup);

            // 删除旧的关联关系
            checkGroupMapper.deleteCheckGroupCheckItem(checkGroup.getId());
            
            // 清除相关缓存
            cacheService.delete(CacheKeyGenerator.generateCheckGroupKey(checkGroup.getId()));
        }

        // 处理新的关联关系
        if (checkItemIds != null && !checkItemIds.isEmpty()) {
            checkGroupMapper.batchInsertCheckGroupCheckItem(checkGroup.getId(), checkItemIds);
        }
        
        // 清除相关缓存
        clearCheckGroupCache();
        
        return message;
    }

    /**
     * 根据ID删除检查组
     * @param checkGroupId 检查组ID
     * @throws BusinessException 如果检查组已被套餐使用或删除失败
     */
    @Transactional
    @Override
    public void deleteById(Integer checkGroupId) {
        // 检查当前的检查组是否被在套餐中使用
        int count = checkGroupMapper.countCheckGroupId(checkGroupId);
        if (count > 0) {
            throw new BusinessException(MessageConstant.CHECKGROUP_HAS_ASSOCIATION);
        }

        // 删除检查组和检查项的关联关系
        checkGroupMapper.deleteCheckGroupCheckItem(checkGroupId);
        
        // 删除检查组数据
        int i = baseMapper.deleteById(checkGroupId);
        if (i == 0) {
            throw new BusinessException(MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
        
        // 清除相关缓存
        cacheService.delete(CacheKeyGenerator.generateCheckGroupKey(checkGroupId));
        clearCheckGroupCache();
    }

    /**
     * 根据ID查询检查组
     * @param checkGroupId 检查组ID
     * @return 检查组信息
     */
    @Override
    public CheckGroup getById(Integer checkGroupId) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateCheckGroupKey(checkGroupId);
        
        // 尝试从缓存获取
        CheckGroup checkGroup = cacheService.get(cacheKey);
        if (checkGroup != null) {
            return checkGroup;
        }
        
        // 从数据库查询
        checkGroup = baseMapper.selectById(checkGroupId);
        
        // 缓存结果，设置过期时间为10分钟
        if (checkGroup != null) {
            cacheService.set(cacheKey, checkGroup, 10, TimeUnit.MINUTES);
        }
        
        return checkGroup;
    }

    /**
     * 根据检查组ID查询关联的检查项ID列表
     * @param checkGroupId 检查组ID
     * @return 检查项ID列表
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("checkgroup", "items", checkGroupId);
        
        // 尝试从缓存获取
        List<Integer> checkItemIds = cacheService.get(cacheKey);
        if (checkItemIds != null) {
            return checkItemIds;
        }
        
        // 从数据库查询
        checkItemIds = checkGroupMapper.findCheckItemIdsByCheckGroupId(checkGroupId);
        
        // 缓存结果，设置过期时间为10分钟
        cacheService.set(cacheKey, checkItemIds, 10, TimeUnit.MINUTES);
        
        return checkItemIds;
    }

    /**
     * 查询所有检查组
     * @return 检查组列表
     */
    @Override
    public List<CheckGroup> findAll() {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("checkgroup", "all");
        
        // 尝试从缓存获取
        List<CheckGroup> checkGroups = cacheService.get(cacheKey);
        if (checkGroups != null) {
            return checkGroups;
        }
        
        // 从数据库查询
        checkGroups = baseMapper.selectList(null);
        
        // 缓存结果，设置过期时间为10分钟
        cacheService.set(cacheKey, checkGroups, 10, TimeUnit.MINUTES);
        
        return checkGroups;
    }
    
    /**
     * 清除检查组相关缓存
     */
    private void clearCheckGroupCache() {
        // 这里可以使用Redis的keys命令清除所有检查组相关缓存
        // 实际生产环境中，建议使用更精确的缓存键管理
    }
}
