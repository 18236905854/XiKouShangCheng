package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.WuGOrderResultBean;

import java.util.List;

/**
 * ClassName WuGXiaDanViewImpl
 * Description 吾G下单页面view接口
 * Author 卿凯
 * Date 2019/7/15/015
 * Version V1.0
 */
public interface WuGXiaDanViewImpl extends BaseViewListener {
    //获取地址列表成功的回调
    void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList);
    //下单成功的回调
    void onOrderSuccess(BaseModel<WuGOrderResultBean> model);
}
