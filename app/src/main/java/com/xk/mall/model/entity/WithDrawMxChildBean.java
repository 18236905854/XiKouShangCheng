package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * @ClassName: WithDrawMxChildBean
 * @Description: 提现明细对象Bean
 * @Author: 卿凯
 * @Date: 2019/9/24/024 20:31
 * @Version: 1.0
 */
public class WithDrawMxChildBean implements Serializable {


    /**
     * cashAmount : 0
     * cashBankCard :
     * cashId :
     * cashTime :
     * state : 0
     * updateTime :
     */

    private int cashAmount; //提现金额
    private String cashBankCard;//提现到账银行卡号
    private String cashId;//提现主键id
    private String cashTime;//申请提现时间
    private int state;//提现工单状态:1：审批中 2：审批通过 3：支付成功 4：废止 5：驳回 6：支付失败
    private String updateTime;//更新时间

    public int getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(int cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getCashBankCard() {
        return cashBankCard;
    }

    public void setCashBankCard(String cashBankCard) {
        this.cashBankCard = cashBankCard;
    }

    public String getCashId() {
        return cashId;
    }

    public void setCashId(String cashId) {
        this.cashId = cashId;
    }

    public String getCashTime() {
        return cashTime;
    }

    public void setCashTime(String cashTime) {
        this.cashTime = cashTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
