package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.GlobalBuyerOrderDetailBean;
import com.xk.mall.model.entity.SellOrderDetailBean;

/**
 * @ClassName: SellOrderDetailViewImpl
 * @Description: 寄卖订单详情view接口
 * @Author: 卿凯
 * @Date: 2019/10/16/016 10:02
 * @Version: 1.0
 */
public interface SellOrderDetailViewImpl extends BaseViewListener {
    //获取订单详情成的回调
    void onGetOrderDetailSuccess(BaseModel<SellOrderDetailBean> model);
}
