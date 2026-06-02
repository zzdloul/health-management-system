package com.seecen.health.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seecen.health.common.constant.MessageConstant;
import com.seecen.health.common.entity.CheckItem;
import com.seecen.health.common.exception.BusinessException;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.core.mapper.CheckItemMapper;
import com.seecen.health.core.service.CheckItemService;
import com.seecen.health.store.service.CacheService;
import com.seecen.health.store.utils.CacheKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 检查项服务实现类
 * 实现CheckItemService接口，提供检查项相关的业务逻辑
 */
@Service
public class CheckItemServiceImpl extends ServiceImpl<CheckItemMapper, CheckItem> implements CheckItemService {
    
    @Autowired
    private CheckItemMapper checkItemMapper;
    
    @Autowired
    private CacheService cacheService;
    
    /**
     * 检查项分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    @Override
    public PageResult<CheckItem> pageQuery(QueryPageBean queryPageBean) {
        PageResult<CheckItem> pageResult = null;
        
        try {
            // 生成缓存键
            String cacheKey = CacheKeyGenerator.generateListKey("checkitem", 
                    queryPageBean.getQueryString() != null ? queryPageBean.getQueryString() : "", 
                    queryPageBean.getPageNum(), 
                    queryPageBean.getPageSize());
            
            // 尝试从缓存获取
            pageResult = cacheService.get(cacheKey);
            if (pageResult != null) {
                return pageResult;
            }
        } catch (Exception e) {
            // 缓存不可用时，直接从数据库查询
            e.printStackTrace();
        }
        
        // 创建分页对象
        Page<CheckItem> page = new Page<>(queryPageBean.getPageNum(), queryPageBean.getPageSize());
        
        // 构建查询条件
        QueryWrapper<CheckItem> queryWrapper = new QueryWrapper<>();
        
        // 如果有查询字符串，添加模糊查询条件
        if (queryPageBean.getQueryString() != null && !queryPageBean.getQueryString().isEmpty()) {
            queryWrapper.like("code", queryPageBean.getQueryString())
                    .or().like("name", queryPageBean.getQueryString())
                    .or().like("remark", queryPageBean.getQueryString());
        }
        
        // 执行分页查询
        Page<CheckItem> page1 = baseMapper.selectPage(page, queryWrapper);
        
        // 构建分页结果
        pageResult = new PageResult<>(page1.getTotal(), page1.getRecords());
        
        try {
            // 缓存结果，设置过期时间为5分钟
            String cacheKey = CacheKeyGenerator.generateListKey("checkitem", 
                    queryPageBean.getQueryString() != null ? queryPageBean.getQueryString() : "", 
                    queryPageBean.getPageNum(), 
                    queryPageBean.getPageSize());
            cacheService.set(cacheKey, pageResult, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            // 缓存失败不影响业务逻辑
            e.printStackTrace();
        }
        
        return pageResult;
    }

    /**
     * 新增检查项
     * @param checkItem 检查项信息
     */
    @Override
    public void add(CheckItem checkItem) {
        baseMapper.insert(checkItem);
        // 清除相关缓存
        clearCheckItemCache();
    }
    
    /**
     * 根据ID删除检查项
     * @param id 检查项ID
     * @throws BusinessException 如果检查项已关联检查组或删除失败
     */
    @Transactional
    @Override
    public void deleteById(Integer id) {
        // 1.验证当前检查项是否关联了检查组
        int count = checkItemMapper.countByCheckItemId(id);
        if (count > 0) {
            throw new BusinessException(MessageConstant.CHECKITEM_HAS_ASSOCIATION);
        }
        
        // 2.没有关联检查组，可以删除
        int i = baseMapper.deleteById(id);
        if (i == 0) {
            // 手动抛出自定义异常
            throw new BusinessException(MessageConstant.DELETE_CHECKITEM_FAIL);
        }
        
        // 清除相关缓存
        cacheService.delete(CacheKeyGenerator.generateCheckItemKey(id));
        clearCheckItemCache();
    }

    /**
     * 更新检查项
     * @param checkItem 检查项信息
     */
    @Override
    public void update(CheckItem checkItem) {
        baseMapper.updateById(checkItem);
        // 清除相关缓存
        cacheService.delete(CacheKeyGenerator.generateCheckItemKey(checkItem.getId()));
        clearCheckItemCache();
    }

    /**
     * 查询所有检查项
     * @return 检查项列表
     */
    @Override
    public List<CheckItem> findAll() {
        List<CheckItem> checkItems = null;
        
        try {
            // 生成缓存键
            String cacheKey = CacheKeyGenerator.generateKey("checkitem", "all");
            
            // 尝试从缓存获取
            checkItems = cacheService.get(cacheKey);
            if (checkItems != null) {
                return checkItems;
            }
        } catch (Exception e) {
            // 缓存不可用时，直接从数据库查询
            e.printStackTrace();
        }
        
        // 从数据库查询
        checkItems = baseMapper.selectList(null);
        
        try {
            // 缓存结果，设置过期时间为10分钟
            String cacheKey = CacheKeyGenerator.generateKey("checkitem", "all");
            cacheService.set(cacheKey, checkItems, 10, TimeUnit.MINUTES);
        } catch (Exception e) {
            // 缓存失败不影响业务逻辑
            e.printStackTrace();
        }
        
        return checkItems;
    }
    
    /**
     * 清除检查项相关缓存
     */
    private void clearCheckItemCache() {
        // 这里可以使用Redis的keys命令清除所有检查项相关缓存
        // 实际生产环境中，建议使用更精确的缓存键管理
    }

}
