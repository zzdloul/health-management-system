package com.seecen.health.api.controller;

import com.seecen.health.common.entity.Member;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;
import com.seecen.health.common.result.Result;
import com.seecen.health.core.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员控制器
 * 处理会员相关的HTTP请求
 */
@RestController
@RequestMapping("/members")
@Tag(name = "会员管理", description = "会员相关的接口")
public class MemberController {
    
    @Autowired
    private MemberService memberService;
    
    /**
     * 会员分页查询
     * @param queryPageBean 查询条件
     * @return 分页结果
     */
    @PostMapping("/pageQuery")
    @Operation(summary = "会员分页查询", description = "根据查询条件分页获取会员列表")
    public Result<PageResult<Member>> pageQuery(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Member> result = memberService.pageQuery(queryPageBean);
        return Result.success("查询成功", result);
    }
    
    /**
     * 新增会员
     * @param member 会员信息
     * @return 操作结果
     */
    @PostMapping
    @Operation(summary = "新增会员", description = "添加新的会员信息")
    public Result<String> add(@RequestBody Member member) {
        memberService.add(member);
        return Result.success("新增会员成功");
    }
    
    /**
     * 更新会员
     * @param member 会员信息
     * @return 操作结果
     */
    @PutMapping
    @Operation(summary = "更新会员", description = "更新会员信息")
    public Result<String> update(@RequestBody Member member) {
        memberService.update(member);
        return Result.success("更新会员成功");
    }
    
    /**
     * 删除会员
     * @param id 会员ID
     * @return 操作结果
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除会员", description = "根据ID删除会员")
    public Result<String> deleteById(@PathVariable Integer id) {
        memberService.deleteById(id);
        return Result.success("删除会员成功");
    }
    
    /**
     * 根据ID查询会员
     * @param id 会员ID
     * @return 会员信息
     */
    @GetMapping("/{id}")
    @Operation(summary = "查询会员详情", description = "根据ID查询会员详细信息")
    public Result<Member> getById(@PathVariable Integer id) {
        Member member = memberService.getById(id);
        return Result.success("查询成功", member);
    }
    
    /**
     * 会员分页查询（GET请求）
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @param queryString 查询字符串
     * @return 分页结果
     */
    @GetMapping
    @Operation(summary = "会员分页查询", description = "根据查询条件分页获取会员列表")
    public Result<PageResult<Member>> pageQuery(@RequestParam(defaultValue = "1") Integer pageNum, 
                                             @RequestParam(defaultValue = "10") Integer pageSize, 
                                             @RequestParam(required = false) String queryString) {
        QueryPageBean queryPageBean = new QueryPageBean();
        queryPageBean.setPageNum(pageNum);
        queryPageBean.setPageSize(pageSize);
        queryPageBean.setQueryString(queryString);
        PageResult<Member> result = memberService.pageQuery(queryPageBean);
        return Result.success("查询成功", result);
    }
    
    /**
     * 查询所有会员
     * @return 会员列表
     */
    @GetMapping("/all")
    @Operation(summary = "查询所有会员", description = "获取所有会员列表")
    public Result<List<Member>> findAll() {
        List<Member> members = memberService.findAll();
        return Result.success("查询成功", members);
    }
}
