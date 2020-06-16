package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.OrderPageBean;

/**
 * ClassName SellConsignmentViewImpl
 * Description 我是卖家 寄卖订单view接口
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public interface SellConsignmentViewImpl extends BaseViewListener {
    //获取寄卖订单成功的回调
    void onGetSellConsignmentOrderListSuccess(BaseModel<OrderPageBean> model);
}
