package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * ClassName CouponBean
 * Description 我的优惠券的Bean
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class CouponBean implements Serializable {
    public int balance = 0;//优惠券余额
    public int couponType = 0;//优惠券类型 1 通用券 (全球买手，0元竞拍，oto)
    public String startTime = "";//优惠券可以使用时间
    public String endTime = "";//优惠券结束使用时间
    public String id = "";//优惠券ID
    public int total = 0;//优惠券总额
    public int useable = 0;//可使用优惠券类型：1：已生效，2：未生效
    public String transferFromUser = "";//获赠用户昵称，非用户转入券为空
    public int transferOutBalance;//可转出金额

}
