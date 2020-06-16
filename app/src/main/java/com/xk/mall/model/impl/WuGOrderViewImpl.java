package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.OrderPageBean;

/**
 * ClassName GlobalBuyerOrderViewImpl
 * Description 全球买手订单列表页面的view接口
 * Author 卿凯
 * Date 2019/7/13/013
 * Version V1.0
 */
public interface WuGOrderViewImpl extends BaseViewListener {
    //获取全球买手订单列表成功回调
    void onGetGlobalBuyerOrderListSuccess(BaseModel<OrderPageBean> model);
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
