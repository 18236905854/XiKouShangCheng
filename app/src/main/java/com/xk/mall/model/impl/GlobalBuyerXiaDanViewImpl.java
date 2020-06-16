package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.GlobalBuyerOrderResultBean;

import java.util.List;

/**
 * ClassName GlobalBuyerXiaDanViewImpl
 * Description 全球买手下单view接口
 * Author 卿凯
 * Date 2019/7/13/013
 * Version V1.0
 */
public interface GlobalBuyerXiaDanViewImpl extends BaseViewListener {
    //获取地址列表成功的回调
    void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList);
    //下单成功的回调
    void onOrderSuccess(BaseModel<GlobalBuyerOrderResultBean> model);
    //获取是否可以寄卖到吾G内容
    void onGetOutSellContent(BaseModel<String> model);
}
