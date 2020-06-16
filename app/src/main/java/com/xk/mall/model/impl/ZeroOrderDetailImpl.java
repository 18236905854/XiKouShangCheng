package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.GroupOrderDetailBean;
import com.xk.mall.model.entity.ZeroOrderDetailBean;

/**
 * 0元拍 订单详情 实现类
 */
public interface ZeroOrderDetailImpl extends BaseViewListener {
    /**
     * 获取订单详情成功
     * @param model
     */
    void onGetOrderDetailSuc(BaseModel<ZeroOrderDetailBean> model);


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

    //新增或修改收货地址
    void onInsertOrUpdateAddressSuccess(BaseModel model);
    //校验地址是否超出范围
    void onVerifyUserAddressInfo(BaseModel<Boolean> model);

}
