package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ConfirmGroupOrderBean;
import com.xk.mall.model.entity.ConfirmZeroOrderBean;

import java.util.List;

/**
 * 0元拍下单 商品 view
 */
public interface ZeroXiaDanViewImpl extends BaseViewListener {

    void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList);

    void onSubmitOrderSuc(BaseModel<ConfirmZeroOrderBean> baseModel);
}
