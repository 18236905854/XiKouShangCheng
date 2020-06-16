package com.xk.mall.model.entity;

import java.util.List;

/**
 * 提现明细bean
 */
public class WithDrawMxBean {
    /**
     * result : []
     * totalCount : 0
     * pageCount : 0
     */

    private int totalCount;
    private int pageCount;
    private List<WithDrawMxChildBean> result;

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

    public List<WithDrawMxChildBean> getResult() {
        return result;
    }

    public void setResult(List<WithDrawMxChildBean> result) {
        this.result = result;
    }
}
