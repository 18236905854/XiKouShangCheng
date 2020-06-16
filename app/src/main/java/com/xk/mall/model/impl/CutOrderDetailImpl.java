package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CutOrderDetailBean;
import com.xk.mall.model.entity.ZeroOrderDetailBean;

/**
 * 喜立得 订单详情 实现类
 */
public interface CutOrderDetailImpl extends BaseViewListener {
    /**
     * 获取订单详情成功
     * @param model
     */
    void onGetOrderDetailSuc(BaseModel<CutOrderDetailBean> model);


    /**
     * 取消订单
     * @param model
     */
    void onGetCancelOrderSuc(BaseModel<String> model);

    //提醒发货成功
    void onRemindShipSuccess(BaseModel model);
    //延长发货成功
    void onExtendTheTimeSuccess(BaseModel model);

    //确认收货接口回调
    void onCompleteOrderSuccess(BaseModel<String> model);

    /**
     * 删除订单
     * @param model
     */
    void onGetDeleteOrderSuc(BaseModel<String> model);

}
