package com.xk.mall.model.entity;

/**
 * @ClassName: WithDrawCountBean
 * @Description: 提现月统计对象
 * @Author: 卿凯
 * @Date: 2019/9/25/025 8:57
 * @Version: 1.0
 */
public class WithDrawCountBean {

    /**
     * haveCashAmount : 0
     * onWayAmount : 0
     * userId :
     */

    private int haveCashAmount;
    private int onWayAmount;
    private String userId;

    public int getHaveCashAmount() {
        return haveCashAmount;
    }

    public void setHaveCashAmount(int haveCashAmount) {
        this.haveCashAmount = haveCashAmount;
    }

    public int getOnWayAmount() {
        return onWayAmount;
    }

    public void setOnWayAmount(int onWayAmount) {
        this.onWayAmount = onWayAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
