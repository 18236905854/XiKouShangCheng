package com.xk.mall.model.request;

/**
 * @ClassName: TransferCouponRequestBody
 * @Description: 转赠优惠券请求体
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class TransferCouponRequestBody {

    /**
     * couponId :
     * fee : 0
     * outAmount : 0
     * payPassword :
     * receivedAmount : 0
     * toUserId :
     */

    private String couponId;//转出券ID
    private int fee;//手续费
    private int outAmount;//支出券金额，扩大100倍，以分为单位
    private String payPassword;//支付密码
    private int receivedAmount;//到账券金额，扩大100倍，以分为单位
    private String toUserId;//收款用户ID

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getOutAmount() {
        return outAmount;
    }

    public void setOutAmount(int outAmount) {
        this.outAmount = outAmount;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public int getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(int receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
}
