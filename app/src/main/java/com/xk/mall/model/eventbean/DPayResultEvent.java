package com.xk.mall.model.eventbean;

/**
 * @ClassName: DPayResultEvent
 * @Description: 代付订单金额对象
 * @Author: 卿凯
 * @Date: 2019/9/9/009 11:43
 * @Version: 1.0
 */
public class DPayResultEvent {
    private int hasPaid;//已支付金额
    private int notPaid;//未支付金额

    public DPayResultEvent(int hasPaid, int notPaid) {
        this.hasPaid = hasPaid;
        this.notPaid = notPaid;
    }

    public int getHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(int hasPaid) {
        this.hasPaid = hasPaid;
    }

    public int getNotPaid() {
        return notPaid;
    }

    public void setNotPaid(int notPaid) {
        this.notPaid = notPaid;
    }
}
