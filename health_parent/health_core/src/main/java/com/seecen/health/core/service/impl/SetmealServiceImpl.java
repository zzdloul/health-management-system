package com.seecen.health.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seecen.health.common.constant.MessageConstant;
import com.seecen.health.common.entity.Setmeal;
import com.seecen.health.common.exception.BusinessException;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.core.mapper.SetmealMapper;
import com.seecen.health.core.service.SetmealService;
import com.seecen.health.store.service.CacheService;
import com.seecen.health.store.utils.CacheKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 套餐服务实现类
 * 实现SetmealService接口，提供套餐相关的业务逻辑
 * 包含缓存支持，提升系统性能
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    
    @Autowired
    private CacheService cacheService;

    /**
     * 套餐分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    @Override
    public PageResult<Setmeal> pageQuery(QueryPageBean queryPageBean) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateListKey("setmeal", 
                queryPageBean.getQueryString() != null ? queryPageBean.getQueryString() : "", 
                queryPageBean.getPageNum(), 
                queryPageBean.getPageSize());
        
        // 尝试从缓存获取
        PageResult<Setmeal> result = null;
        if (cacheService != null) {
            result = cacheService.get(cacheKey);
            if (result != null) {
                return result;
            }
        }
        
        // 创建分页对象
        Page<Setmeal> page = new Page<>(queryPageBean.getPageNum(), queryPageBean.getPageSize());
        
        // 构建查询条件
        QueryWrapper<Setmeal> queryWrapper = new QueryWrapper<>();
        
        // 如果有查询字符串，添加模糊查询条件
        if (queryPageBean.getQueryString() != null && !queryPageBean.getQueryString().isEmpty()) {
            queryWrapper.like("helpCode", queryPageBean.getQueryString())
                    .or().like("name", queryPageBean.getQueryString())
                    .or().like("remark", queryPageBean.getQueryString());
        }

        // 执行分页查询
        Page page1 = baseMapper.selectPage(page, queryWrapper);

        // 构建分页结果
        result = new PageResult<>(page1.getTotal(), page1.getRecords());
        
        // 缓存结果，设置过期时间为5分钟
        if (cacheService != null) {
            cacheService.set(cacheKey, result, 5, TimeUnit.MINUTES);
        }
        
        return result;
    }

    /**
     * 新增或更新套餐
     * @param setmeal 套餐信息
     * @param checkGroupIds 关联的检查组ID列表
     * @return 操作结果消息
     */
    @Transactional
    @Override
    public String addOrUpdate(Setmeal setmeal, List<Integer> checkGroupIds) {
        String message = MessageConstant.EDIT_SETMEAL_SUCCESS;
        
        if (setmeal.getId() == null) {
            // 新增套餐
            baseMapper.insert(setmeal);
            message = MessageConstant.ADD_SETMEAL_SUCCESS;
        } else {
            // 更新套餐
            baseMapper.updateById(setmeal);
            // 删除旧的关联关系
            setmealMapper.deleteSetmealCheckGroup(setmeal.getId());
            
            // 清除缓存
            if (cacheService != null) {
                String setmealCacheKey = CacheKeyGenerator.generateSetmealKey(setmeal.getId());
                cacheService.delete(setmealCacheKey);
            }
        }
        
        // 处理新的关联关系
        if (checkGroupIds != null && !checkGroupIds.isEmpty()) {
            setmealMapper.batchInsertSetmealCheckGroup(setmeal.getId(), checkGroupIds);
        }
        
        // 清除列表缓存
        if (cacheService != null) {
            cacheService.delete(CacheKeyGenerator.generateKey("setmeal", "list"));
        }
        
        return message;
    }

    /**
     * 根据ID删除套餐
     * @param setmealId 套餐ID
     * @throws BusinessException 如果套餐已关联订单或删除失败
     */
    @Transactional
    @Override
    public void deleteById(Integer setmealId) {
        // 检查套餐是否关联订单
        int count = setmealMapper.countBySetmealId(setmealId);
        if (count > 0) {
            throw new BusinessException(MessageConstant.SETMEAL_HAS_ASSOCIATION);
        }

        // 删除套餐和检查组的关联关系
        setmealMapper.deleteSetmealCheckGroup(setmealId);

        // 删除套餐数据
        int i = baseMapper.deleteById(setmealId);
        if (i == 0) {
            throw new BusinessException(MessageConstant.DELETE_SETMEAL_FAIL);
        }
        
        // 清除缓存
        if (cacheService != null) {
            String setmealCacheKey = CacheKeyGenerator.generateSetmealKey(setmealId);
            cacheService.delete(setmealCacheKey);
            
            // 清除列表缓存
            cacheService.delete(CacheKeyGenerator.generateKey("setmeal", "list"));
        }
    }

    /**
     * 根据ID查询套餐
     * @param setmealId 套餐ID
     * @return 套餐信息
     */
    @Override
    public Setmeal getById(Integer setmealId) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateSetmealKey(setmealId);
        
        // 尝试从缓存获取
        Setmeal setmeal = null;
        if (cacheService != null) {
            setmeal = cacheService.get(cacheKey);
            if (setmeal != null) {
                return setmeal;
            }
        }
        
        // 从数据库查询
        setmeal = baseMapper.selectById(setmealId);
        
        // 缓存结果，设置过期时间为10分钟
        if (setmeal != null && cacheService != null) {
            cacheService.set(cacheKey, setmeal, 10, TimeUnit.MINUTES);
        }
        
        return setmeal;
    }

    /**
     * 根据套餐ID查询关联的检查组ID列表
     * @param setmealId 套餐ID
     * @return 检查组ID列表
     */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("setmeal", "checkgroups", setmealId);
        
        // 尝试从缓存获取
        List<Integer> checkGroupIds = null;
        if (cacheService != null) {
            checkGroupIds = cacheService.get(cacheKey);
            if (checkGroupIds != null) {
                return checkGroupIds;
            }
        }
        
        // 从数据库查询
        checkGroupIds = setmealMapper.findCheckGroupIdsBySetmealId(setmealId);
        
        // 缓存结果，设置过期时间为10分钟
        if (checkGroupIds != null && cacheService != null) {
            cacheService.set(cacheKey, checkGroupIds, 10, TimeUnit.MINUTES);
        }
        
        return checkGroupIds;
    }

    /**
     * 查询所有套餐
     * @return 套餐列表
     */
    @Override
    public List<Setmeal> findAll() {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("setmeal", "list");
        
        // 尝试从缓存获取
        List<Setmeal> setmeals = null;
        if (cacheService != null) {
            setmeals = cacheService.get(cacheKey);
            if (setmeals != null) {
                return setmeals;
            }
        }
        
        // 从数据库查询
        setmeals = baseMapper.selectList(null);
        
        // 缓存结果，设置过期时间为5分钟
        if (setmeals != null && cacheService != null) {
            cacheService.set(cacheKey, setmeals, 5, TimeUnit.MINUTES);
        }
        
        return setmeals;
    }
    
    /**
     * 更新套餐状态
     * @param id 套餐ID
     * @param status 套餐状态
     */
    @Override
    public void updateStatus(Integer id, Integer status) {
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        baseMapper.updateById(setmeal);
        // 清除相关缓存
        if (cacheService != null) {
            String setmealCacheKey = CacheKeyGenerator.generateSetmealKey(id);
            cacheService.delete(setmealCacheKey);
            cacheService.delete(CacheKeyGenerator.generateKey("setmeal", "list"));
        }
    }
    
    /**
     * 更新套餐排序
     * @param id 套餐ID
     * @param sort 套餐排序
     */
    @Override
    public void updateSort(Integer id, Integer sort) {
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setSort(sort);
        baseMapper.updateById(setmeal);
        // 清除相关缓存
        if (cacheService != null) {
            String setmealCacheKey = CacheKeyGenerator.generateSetmealKey(id);
            cacheService.delete(setmealCacheKey);
            cacheService.delete(CacheKeyGenerator.generateKey("setmeal", "list"));
        }
    }

}