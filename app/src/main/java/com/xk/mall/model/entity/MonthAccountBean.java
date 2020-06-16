package com.xk.mall.model.entity;

/**
 * @ClassName: MonthAccountBean
 * @Description: 钱包明细月收入统计
 * @Author: 卿凯
 * @Date: 2019/9/21/021 16:49
 * @Version: 1.0
 */
public class MonthAccountBean {

    /**
     * expenditureAmount : 0 月支出金额
     * incomeAmount : 0 月收入金额
     * userId :
     */

    private int expenditureAmount;
    private int incomeAmount;
    private String userId;

    public int getExpenditureAmount() {
        return expenditureAmount;
    }

    public void setExpenditureAmount(int expenditureAmount) {
        this.expenditureAmount = expenditureAmount;
    }

    public int getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(int incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
