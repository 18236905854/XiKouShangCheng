package com.xk.mall.model.request;

/**
 * @ClassName: TransferRequestBody
 * @Description: 转账请求对象
 * @Author: 卿凯
 * @Date: 2019/10/20/020 18:38
 * @Version: 1.0
 */
public class TransferRequestBody {


    /**
     * amount : 0
     * fromUserId :
     * password :
     * toUserId :
     */

    private int amount;
    private String fromUserId;
    private String payPassword;
    private String toUserId;
    private String remark;

    public String getRemarks() {
        return remark;
    }

    public void setRemarks(String remarks) {
        this.remark = remarks;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getPassword() {
        return payPassword;
    }

    public void setPassword(String password) {
        this.payPassword = password;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
}
