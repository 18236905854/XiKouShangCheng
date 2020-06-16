package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.PayPingResultBean;
import com.xk.mall.model.entity.PayResultBean;

/**
 * ClassName PayViewImpl
 * Description 微信支付view接口
 * Author 卿凯
 * Date 2019/7/20/020
 * Version V1.0
 */
public interface PayViewImpl extends BaseViewListener {
    //微信支付下单成功回调
    void onWXPaySuccess(BaseModel<PayResultBean> model);

    //支付宝下单成功回调
    void onGetAliPayDataSuccess(BaseModel<PayResultBean> model);

    //ping微信支付下单成功回调
    void onPingWXPaySuccess(BaseModel<PayPingResultBean> model);

    //ping++支付宝下单成功回调
    void onGetPingAliPayDataSuccess(BaseModel<PayPingResultBean> model);

    //红包支付成功回调
    void onRedPaySuccess(BaseModel model);
}
