package com.xk.mall.model.request;

/**
 * @ClassName: GetOutSellRequestBody
 * @Description: 获取用户是否可以寄卖内容
 * @Author: 卿凯
 * @Date: 2019/12/11/011 21:42
 * @Version: 1.0
 */
public class GetOutSellRequestBody {

    /**
     * commodityId :
     * userId :
     */

    private String commodityId;
    private String userId;

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
