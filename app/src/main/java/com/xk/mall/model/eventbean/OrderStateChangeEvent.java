package com.xk.mall.model.eventbean;

/**
 * @ClassName: OrderStateChangeEvent
 * @Description: 订单状态改变事件发送对象
 * @Author: 卿凯
 * @Date: 2019/9/3/003 11:09
 * @Version: 1.0
 */
public class OrderStateChangeEvent {
    private String orderNo;//订单号
    private int orderState;//订单状态

    public OrderStateChangeEvent(String orderNo, int orderState) {
        this.orderNo = orderNo;
        this.orderState = orderState;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderState() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState = orderState;
    }
}
