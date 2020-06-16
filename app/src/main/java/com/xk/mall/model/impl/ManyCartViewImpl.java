package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ManyBuyRateBean;
import com.xk.mall.model.entity.ManyCartsBean;

import java.util.List;

/**
 * 多买多折 购物车
 */
public interface ManyCartViewImpl extends BaseViewListener {

    //获取购物车数量成功
    void onGetCartDataSuc(BaseModel<List<ManyCartsBean>> baseModel);


    //删除购物车
    void onDeleteCartSuc(BaseModel baseModel);

    //查询多买多折活动商品折扣费率
    void onGetManyRateSuccess(BaseModel<ManyBuyRateBean> model);
}
