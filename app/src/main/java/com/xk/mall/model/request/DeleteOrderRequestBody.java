package com.xk.mall.model.request;

/**
 * ClassName DeleteOrderRequestBody
 * Description 删除订单请求体
 * Author Kay
 * Date 2019/7/16/016
 * Version V1.0
 */
public class DeleteOrderRequestBody {

    /**
     * orderNo :
     * orderType : 0
     */

    private String orderNo;//订单号
    private int orderType;//订单类型;1-0元竞拍订单;2-多买多折订单;3-喜立得订单;4-吾G订单;5-全球买手订单;6-定制拼团订单

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
}
