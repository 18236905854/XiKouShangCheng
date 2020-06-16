package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName RedBagBean
 * Description 我的红包页面Bean
 * Author 卿凯
 * Date 2019/6/17/017
 * Version V1.0
 */
public class RedBagBean {
    public int totalSum = 0;//用户总余额
    public int balance = 0;//用户可用余额
    public int frozen = 0;//提现审核金额
    public int onWay = 0;//冻结资金金额
    public int toCash = 0;//可提现金额
    public int rate = 0;//提现手续费率
    public int minMoney = 0;//提现最低金额
    public int maxMoney = 0;//提现最高金额
    public int centerMoney = 0;//提现中间金额
    public int arrivalTime = 0;//提现到账时间
    public int experienceBalance = 0;//体验金额度
    public List<WithdrawAccountBean> bankCardList;//银行卡列表
}
