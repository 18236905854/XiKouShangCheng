package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName ManyBuyOrderAdapterBean
 * Description 多买多折订单放在adapter中的bean
 * Author 卿凯
 * Date 2019/7/18/018
 * Version V1.0
 */
public class ManyBuyOrderAdapterBean {
    private int state = 0;//状态 1:待付款  2:已付款
    private String tradeNo;//主订单号
    private List<ManyBuyOrderBean.ResultBean> manyBuyOrderBeanList;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<ManyBuyOrderBean.ResultBean> getManyBuyOrderBeanList() {
        return manyBuyOrderBeanList;
    }

    public void setManyBuyOrderBeanList(List<ManyBuyOrderBean.ResultBean> manyBuyOrderBeanList) {
        this.manyBuyOrderBeanList = manyBuyOrderBeanList;
    }
}
