package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ManyBuyOrderResultBean;

import java.util.List;

/**
 * ClassName ManyGoodsXiaDanViewImpl
 * Description 多买多折下单页面的view接口
 * Author 卿凯
 * Date 2019/7/16/016
 * Version V1.0
 */
public interface ManyGoodsXiaDanViewImpl extends BaseViewListener {
    //获取地址列表成功的回调
    void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList);
    //下单成功的回调
    void onOrderSuccess(BaseModel<ManyBuyOrderResultBean> model);
}
