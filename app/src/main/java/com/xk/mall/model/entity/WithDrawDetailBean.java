package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * 提现明细
 */
public class WithDrawDetailBean implements Serializable {
    /**
     * cashAmount : 50000
     * amount : 500
     * cashCommission : 49500
     * cashType : T+0
     * state : 1
     * cashContent : null
     * cashTime : 2019-09-24 10:17:09
     * updateTime : 2019-09-24 10:17:09
     */

    private int cashAmount;//提现金额
    private int amount;//实际到账金额
    private int cashCommission;//提现手续费
    private String cashType;//到账类型
    private int state;//状态提现工单状态:1：审批中 2：审批通过 3：支付成功 4：废止 5：驳回 6：支付失败
    private String cashContent;//失败原因
    private String cashTime;//申请提现时间
    private String updateTime;//更新时间
    private String cashBankCard;//提现卡号
    private int rate;//手续费

    public String getCashBankCard() {
        return cashBankCard;
    }

    public void setCashBankCard(String cashBankCard) {
        this.cashBankCard = cashBankCard;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(int cashAmount) {
        this.cashAmount = cashAmount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCashCommission() {
        return cashCommission;
    }

    public void setCashCommission(int cashCommission) {
        this.cashCommission = cashCommission;
    }

    public String getCashType() {
        return cashType;
    }

    public void setCashType(String cashType) {
        this.cashType = cashType;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCashContent() {
        return cashContent;
    }

    public void setCashContent(String cashContent) {
        this.cashContent = cashContent;
    }

    public String getCashTime() {
        return cashTime;
    }

    public void setCashTime(String cashTime) {
        this.cashTime = cashTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
