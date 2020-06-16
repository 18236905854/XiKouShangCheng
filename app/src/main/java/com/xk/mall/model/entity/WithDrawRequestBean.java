package com.xk.mall.model.entity;

/**
 * @ClassName: WithDrawRequestBean
 * @Description: 请求提现请求类型
 * @Author: 卿凯
 * @Date: 2019/9/23/023 19:43
 * @Version: 1.0
 */
public class WithDrawRequestBean {


    /**
     * amount : 0
     * cashAmount : 0
     * cashBankCard :
     * cashCommission : 0
     * cashTime :
     * cashType :
     * frozen : 0
     * id :
     * orderNo :
     * remarks :
     * state : 0
     * updateTime :
     * userId :
     * userType : 0
     */

    private int amount;//实际到账金额
    private int cashAmount;//提现金额
    private String cashBankCard;//提现到账银行卡号
    private int cashCommission;//提现手续费
    private String cashType;//到账类型：T+0; T+3; T+7
    private String userId;//用户ID
    private int userType;//用户类型

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
