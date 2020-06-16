package com.xk.mall.model.entity;

/**
 * @ClassName: WuGOrderMoneyBean
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class WuGOrderMoneyBean {

    /**
     * balance : 0
     * limitAmount : 0
     */

    private int balance;//用户剩余额度
    private int limitAmount;//每月限制额度
    private String expirationTime;//下次刷新时间

    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(int limitAmount) {
        this.limitAmount = limitAmount;
    }
}
