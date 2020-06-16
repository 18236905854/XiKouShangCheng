package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.OrderPageBean;

/**
 * @ClassName: NewPersonOrderViewImpl
 * @Description: 新人专区订单列表View接口
 * @Author: 卿凯
 * @Date: 2019/9/5/005 10:35
 * @Version: 1.0
 */
public interface NewPersonOrderViewImpl extends BaseViewListener {
    //获取新人专区订单列表成功回调
    void onGetNewOrderListSuccess(BaseModel<OrderPageBean> model);
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
