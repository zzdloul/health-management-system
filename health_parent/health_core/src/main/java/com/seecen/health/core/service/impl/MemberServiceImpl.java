package com.seecen.health.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seecen.health.common.entity.Member;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.core.mapper.MemberMapper;
import com.seecen.health.core.service.MemberService;
import com.seecen.health.store.service.CacheService;
import com.seecen.health.store.utils.CacheKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 会员服务实现类
 * 实现MemberService接口，提供会员相关的业务逻辑
 * 包含缓存支持，提升系统性能
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
    
    @Autowired
    private MemberMapper memberMapper;
    
    @Autowired(required = false)
    private CacheService cacheService;
    
    /**
     * 会员分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    @Override
    public PageResult<Member> pageQuery(QueryPageBean queryPageBean) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateListKey("member", 
                queryPageBean.getQueryString() != null ? queryPageBean.getQueryString() : "", 
                queryPageBean.getPageNum(), 
                queryPageBean.getPageSize());
        
        // 尝试从缓存获取
        PageResult<Member> pageResult = null;
        if (cacheService != null) {
            pageResult = cacheService.get(cacheKey);
            if (pageResult != null) {
                return pageResult;
            }
        }
        
        // 创建分页对象
        Page<Member> page = new Page<>(queryPageBean.getPageNum(), queryPageBean.getPageSize());
        
        // 构建查询条件
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        
        // 如果有查询字符串，添加模糊查询条件
        if (queryPageBean.getQueryString() != null && !queryPageBean.getQueryString().isEmpty()) {
            queryWrapper.like("name", queryPageBean.getQueryString())
                    .or().like("phoneNumber", queryPageBean.getQueryString())
                    .or().like("idCard", queryPageBean.getQueryString());
        }
        
        // 执行分页查询
        Page<Member> page1 = baseMapper.selectPage(page, queryWrapper);
        
        // 构建分页结果
        pageResult = new PageResult<>(page1.getTotal(), page1.getRecords());
        
        // 缓存结果，设置过期时间为5分钟
        if (cacheService != null) {
            cacheService.set(cacheKey, pageResult, 5, TimeUnit.MINUTES);
        }
        
        return pageResult;
    }
    
    /**
     * 新增会员
     * @param member 会员信息
     */
    @Override
    public void add(Member member) {
        baseMapper.insert(member);
        // 清除相关缓存
        if (cacheService != null) {
            clearMemberCache();
        }
    }
    
    /**
     * 更新会员
     * @param member 会员信息
     */
    @Override
    public void update(Member member) {
        baseMapper.updateById(member);
        // 清除相关缓存
        if (cacheService != null) {
            cacheService.delete(CacheKeyGenerator.generateKey("member", member.getId()));
            clearMemberCache();
        }
    }
    
    /**
     * 根据ID删除会员
     * @param id 会员ID
     */
    @Override
    public void deleteById(Integer id) {
        baseMapper.deleteById(id);
        // 清除相关缓存
        if (cacheService != null) {
            cacheService.delete(CacheKeyGenerator.generateKey("member", id));
            clearMemberCache();
        }
    }
    
    /**
     * 根据ID查询会员
     * @param id 会员ID
     * @return 会员信息
     */
    @Override
    public Member getById(Integer id) {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("member", id);
        
        // 尝试从缓存获取
        Member member = null;
        if (cacheService != null) {
            member = cacheService.get(cacheKey);
            if (member != null) {
                return member;
            }
        }
        
        // 从数据库查询
        member = baseMapper.selectById(id);
        
        // 缓存结果，设置过期时间为10分钟
        if (cacheService != null && member != null) {
            cacheService.set(cacheKey, member, 10, TimeUnit.MINUTES);
        }
        
        return member;
    }
    
    /**
     * 查询所有会员
     * @return 会员列表
     */
    @Override
    public List<Member> findAll() {
        // 生成缓存键
        String cacheKey = CacheKeyGenerator.generateKey("member", "all");
        
        // 尝试从缓存获取
        List<Member> members = null;
        if (cacheService != null) {
            members = cacheService.get(cacheKey);
            if (members != null) {
                return members;
            }
        }
        
        // 从数据库查询
        members = baseMapper.selectList(null);
        
        // 缓存结果，设置过期时间为10分钟
        if (cacheService != null && members != null) {
            cacheService.set(cacheKey, members, 10, TimeUnit.MINUTES);
        }
        
        return members;
    }
    
    /**
     * 清除会员相关缓存
     */
    private void clearMemberCache() {
        // 清除会员列表缓存
        if (cacheService != null) {
            cacheService.delete(CacheKeyGenerator.generateKey("member", "all"));
            // 实际生产环境中，建议使用更精确的缓存键管理
        }
    }
}
