package com.xk.mall.model.entity;

import java.util.List;

/**
 * @ClassName: ResultPageBean
 * @Description: 分页对象的Bean
 * @Author: 卿凯
 * @Date: 2019/10/9/009 9:17
 * @Version: 1.0
 */
public class ResultPageBean<T> {
    int pageCount;//页数
    int totalCount;//总数
    List<T> result;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
