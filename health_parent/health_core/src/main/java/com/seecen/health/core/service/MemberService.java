package com.seecen.health.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seecen.health.common.entity.Member;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;

import java.util.List;

/**
 * 会员服务接口
 * 定义会员相关的业务逻辑规范
 */
public interface MemberService extends IService<Member> {
    
    /**
     * 会员分页查询
     * @param queryPageBean 查询条件，包含页码、每页大小、查询字符串
     * @return 分页结果，包含总记录数和当前页数据
     */
    PageResult<Member> pageQuery(QueryPageBean queryPageBean);
    
    /**
     * 新增会员
     * @param member 会员信息
     */
    void add(Member member);
    
    /**
     * 更新会员
     * @param member 会员信息
     */
    void update(Member member);
    
    /**
     * 根据ID删除会员
     * @param id 会员ID
     */
    void deleteById(Integer id);
    
    /**
     * 根据ID查询会员
     * @param id 会员ID
     * @return 会员信息
     */
    Member getById(Integer id);
    
    /**
     * 查询所有会员
     * @return 会员列表
     */
    List<Member> findAll();
}
