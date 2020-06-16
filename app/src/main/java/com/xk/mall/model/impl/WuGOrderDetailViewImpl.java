package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.GlobalBuyerOrderDetailBean;

/**
 * ClassName WuGOrderDetailViewImpl
 * Description 吾G订单详情页面view接口
 * Author 卿凯
 * Date 2019/7/17/017
 * Version V1.0
 */
public interface WuGOrderDetailViewImpl extends BaseViewListener {
    //获取订单详情成功回调
    void onGetOrderDetailSuccess(BaseModel<GlobalBuyerOrderDetailBean> model);
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
