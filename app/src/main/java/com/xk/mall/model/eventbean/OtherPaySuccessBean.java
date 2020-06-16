package com.xk.mall.model.eventbean;

/**
 * @ClassName: OtherPaySuccessBean
 * @Description: 申请代付成功发送消息Bean
 * @Author: 卿凯
 * @Date: 2019/8/31/031 12:24
 * @Version: 1.0
 */
public class OtherPaySuccessBean {
    private String orderNo;//订单号
    private int orderType;//订单类型

    public OtherPaySuccessBean(String orderNo, int orderType) {
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
