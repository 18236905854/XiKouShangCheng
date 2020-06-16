package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ManyCartsBean;

import java.util.List;

/**
 * 多买多折 购物车
 */
public interface WuGBuyViewImpl extends BaseViewListener {

    //获取listData 数据
    void onGetCartDataSuc(BaseModel<List<ManyCartsBean>> baseModel);



}
