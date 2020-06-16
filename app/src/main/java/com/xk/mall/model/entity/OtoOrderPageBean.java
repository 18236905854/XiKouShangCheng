package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName OtoOrderPageBean
 * Description OTO联盟订单分页Bean
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public class OtoOrderPageBean {
    private int pageCount;
    private int totalCount;
    private List<OtoOrderBean> result;

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

    public List<OtoOrderBean> getResult() {
        return result;
    }

    public void setResult(List<OtoOrderBean> result) {
        this.result = result;
    }
}
