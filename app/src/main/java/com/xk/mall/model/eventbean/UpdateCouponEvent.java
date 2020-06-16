package com.xk.mall.model.eventbean;

/**
 * @ClassName: UpdateCouponEvent
 * @Description: 更新优惠券发送的消息体
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class UpdateCouponEvent {
    private String couponId;//优惠券ID
    private int amount;//优惠券总值

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
