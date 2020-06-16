package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName CouponDetailBean
 * Description 优惠券详情Bean
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class CouponDetailBean {

    public int balance = 0;//优惠券余额
    public String startTime = "";//创建时间
    public String endTime = "";//结束时间
    public String id = "";//优惠券ID
    public int total = 0;//优惠券总额
    public int feeRates = 0;//优惠券费率
    public List<CouponDetailRecordBean> recordModelList;
}
