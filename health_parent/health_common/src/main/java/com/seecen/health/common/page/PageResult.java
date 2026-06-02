package com.seecen.health.common.page;

import java.util.List;

/**
 * 分页结果类
 * 用于封装分页查询的结果数据
 */
public class PageResult<T> {

    /**
     * 总记录数
     */
    private long total;

    /**
     * 数据列表
     */
    private List<T> rows;

    /**
     * 无参构造方法
     */
    public PageResult() {
    }

    /**
     * 构造方法
     * @param total 总记录数
     * @param rows 数据列表
     */
    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    /**
     * 获取总记录数
     * @return 总记录数
     */
    public long getTotal() {
        return total;
    }

    /**
     * 设置总记录数
     * @param total 总记录数
     */
    public void setTotal(long total) {
        this.total = total;
    }

    /**
     * 获取数据列表
     * @return 数据列表
     */
    public List<T> getRows() {
        return rows;
    }

    /**
     * 设置数据列表
     * @param rows 数据列表
     */
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
