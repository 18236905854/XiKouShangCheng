package com.xk.mall.model.request;

/**
 * @ClassName: RemindRequestBody
 * @Description: 提醒发货请求体
 * @Author: 卿凯
 * @Date: 2019/8/21/021 14:51
 * @Version: 1.0
 */
public class RemindRequestBody {

    /**
     * orderNo :
     * orderType : 0
     */

    private String orderNo;
    private int orderType;

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
