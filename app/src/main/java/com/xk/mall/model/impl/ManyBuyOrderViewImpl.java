package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ManyBuyOrderBean;
import com.xk.mall.model.entity.OrderPageBean;

/**
 * ClassName ManyBuyOrderViewImpl
 * Description 多买多折订单列表页面view接口
 * Author 卿凯
 * Date 2019/7/17/017
 * Version V1.0
 */
public interface ManyBuyOrderViewImpl extends BaseViewListener {
    //获取多买多折列表成功回调
    void onGetManyBuyOrderListSuccess(BaseModel<ManyBuyOrderBean> model);
    //取消订单接口回调
    void onCancelOrderSuccess(BaseModel<String> model);
    //提醒发货成功
    void onRemindShipSuccess(BaseModel model);
    //延长发货成功
    void onExtendTheTimeSuccess(BaseModel model);
    //确认收货接口回调
    void onCompleteOrderSuccess(BaseModel<String> model);
    //删除订单成功的回调
    void onDeleteSuccess(BaseModel<String> model);
}
