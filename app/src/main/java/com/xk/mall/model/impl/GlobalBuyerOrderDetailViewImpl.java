package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.GlobalBuyerOrderDetailBean;
import com.xk.mall.model.entity.ShareBean;

/**
 * ClassName GlobalBuyerOrderDetailViewImpl
 * Description 全球买手订单详情页面view接口
 * Author 卿凯
 * Date 2019/7/15/015
 * Version V1.0
 */
public interface GlobalBuyerOrderDetailViewImpl extends BaseViewListener {
    //获取订单详情成的回调
    void onGetOrderDetailSuccess(BaseModel<GlobalBuyerOrderDetailBean> model);
    //取消订单接口回调
    void onCancelOrderSuccess(BaseModel<String> model);
    //确认收货接口回调
    void onCompleteOrderSuccess(BaseModel<String> model);

    //提醒发货成功
    void onRemindShipSuccess(BaseModel model);
    //延长发货成功
    void onExtendTheTimeSuccess(BaseModel model);
    //删除订单成功的回调
    void onDeleteSuccess(BaseModel<String> model);
    //修改全收买手订单处理方式成功的回调
    void onModifyOrderTypeSuccess(BaseModel<ShareBean> model);
}
