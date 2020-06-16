package com.xk.mall.model.request;

/**
 * @ClassName: OtherPayRequestBody
 * @Description: 代付请求体
 * @Author: 卿凯
 * @Date: 2019/8/30/030 16:44
 * @Version: 1.0
 */
public class OtherPayRequestBody {

    /**
     * orderNo :
     * orderType : 0
     * userId :
     */

    private String orderNo;
    private int orderType;
    private String userId;
    private String payPhone;//代付人电话号码

    public String getPayPhone() {
        return payPhone;
    }

    public void setPayPhone(String payPhone) {
        this.payPhone = payPhone;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
