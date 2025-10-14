package com.lmh.blog_project.common;

import java.io.Serializable;
import java.util.List;

public class Paging<D> implements Serializable {
    private static final long serialVersionUID = -1438830917101814899L;

    //当前所处的页数
    private Integer pageNum;

    //每页数量
    private Integer pageSize = 10;

    //总页数
    private Integer totalPage;

    //总记录数（总结果数）
    private Long totalCount;

    //当前页面的集合数据
    private List<D> data;

    public Paging() {}

    public Paging(Integer pageNum, Integer pageSize, Integer totalPage, Long totalCount, List<D> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.data = data;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<D> getData() {
        return data;
    }

    public void setData(List<D> data) {
        this.data = data;
    }
}
