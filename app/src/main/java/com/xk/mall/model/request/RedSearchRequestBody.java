package com.xk.mall.model.request;

/**
 * 红包明细查询请求体
 */
public class RedSearchRequestBody {
    private String userId;
    private String searchTime;
    private int businessType;
    private int page;
    private int limit;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSearchTime() {
        return searchTime;
    }

    public void setSearchTime(String searchTime) {
        this.searchTime = searchTime;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
