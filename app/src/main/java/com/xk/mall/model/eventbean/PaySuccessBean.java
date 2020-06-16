package com.xk.mall.model.eventbean;

/**
 * @ClassName: PaySuccessBean
 * @Description: 订单支付成功发送消息Bean
 * @Author: 卿凯
 * @Date: 2019/9/2/002 19:11
 * @Version: 1.0
 */
public class PaySuccessBean {
    private String orderNo;//订单号
    private int orderType;//订单类型

    public PaySuccessBean(String orderNo, int orderType) {
        this.orderNo = orderNo;
        this.orderType = orderType;
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
}
