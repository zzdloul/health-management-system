package com.seecen.health.common.page;

/**
 * 分页查询参数类
 * 用于封装分页查询的参数
 */
public class QueryPageBean {

    /**
     * 当前页码
     */
    private Integer pageNum = 1;

    /**
     * 每页记录数
     */
    private Integer pageSize = 10;

    /**
     * 查询条件
     */
    private String queryString;

    /**
     * 无参构造方法
     */
    public QueryPageBean() {
    }

    /**
     * 构造方法
     * @param pageNum 当前页码
     * @param pageSize 每页记录数
     * @param queryString 查询条件
     */
    public QueryPageBean(Integer pageNum, Integer pageSize, String queryString) {
        this.pageNum = pageNum != null ? pageNum : 1;
        this.pageSize = pageSize != null ? pageSize : 10;
        this.queryString = queryString;
    }

    /**
     * 获取当前页码
     * @return 当前页码
     */
    public Integer getPageNum() {
        return pageNum;
    }

    /**
     * 设置当前页码
     * @param pageNum 当前页码
     */
    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum != null ? pageNum : 1;
    }

    /**
     * 获取每页记录数
     * @return 每页记录数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     * @param pageSize 每页记录数
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize != null ? pageSize : 10;
    }

    /**
     * 获取查询条件
     * @return 查询条件
     */
    public String getQueryString() {
        return queryString;
    }

    /**
     * 设置查询条件
     * @param queryString 查询条件
     */
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
