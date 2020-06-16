package com.xk.mall.model.eventbean;

/**
 * ClassName RefreshOrderListEvent
 * Description 订单取消和删除消息Bean
 * Author 卿凯
 * Date 2019/7/17/017
 * Version V1.0
 */
public class RefreshOrderListEvent {
    private boolean isDelete;
    private boolean isFinish;
    private String orderNo;//订单号

    public RefreshOrderListEvent(boolean isDelete, boolean isFinish, String orderNo) {
        this.isDelete = isDelete;
        this.orderNo = orderNo;
        this.isFinish = isFinish;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
