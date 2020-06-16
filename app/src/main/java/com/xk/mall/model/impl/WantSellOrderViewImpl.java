package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.entity.ShareBean;

/**
 * ClassName WantSellOrderViewImpl
 * Description 我要寄卖订单列表
 * Author 卿凯
 * Date 2019/7/17/017
 * Version V1.0
 */
public interface WantSellOrderViewImpl extends BaseViewListener {
    //获取我要寄卖订单列表成功
    void onGetWantSellOrderListSuccess(BaseModel<OrderPageBean> model);
    //修改全收买手订单处理方式成功的回调
    void onModifyOrderTypeSuccess(BaseModel<ShareBean> model);
}
