package com.xk.mall.model.eventbean;

/**
 * @ClassName: PayBackEvent
 * @Description: 退款成功事件
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class PayBackEvent {
    private String orderNo;//订单号

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
