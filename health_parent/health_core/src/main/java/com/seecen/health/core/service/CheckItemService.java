package com.seecen.health.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.seecen.health.common.entity.CheckItem;
import com.seecen.health.common.page.PageResult;
import com.seecen.health.common.page.QueryPageBean;

import java.util.List;

public interface CheckItemService extends IService<CheckItem> {
/*queryPageBean前端给后端的分页查询条件
* 响应给前端*/
    PageResult<CheckItem> pageQuery (QueryPageBean queryPageBean);

//    新增检查项
    void add(CheckItem checkItem);
//    根据id删除检查项
    void deleteById(Integer id);

    void update(CheckItem checkItem);
    List<CheckItem> findAll();
}
