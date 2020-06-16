package com.xk.mall.model.entity;

import java.util.List;

/**
 * 结算明细bean
 */
public class SettlementMxBean {
    /**
     * result : []
     * totalCount : 0
     * pageCount : 0
     */

    private int totalCount;
    private int pageCount;
    private List<SettlementMxChildBean> result;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public List<SettlementMxChildBean> getResult() {
        return result;
    }

    public void setResult(List<SettlementMxChildBean> result) {
        this.result = result;
    }
}
