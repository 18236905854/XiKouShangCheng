package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ConfirmCutOrderBean;
import com.xk.mall.model.entity.ConfirmGroupOrderBean;

import java.util.List;

/**
 * 喜立得 下单  view
 */
public interface CutXiaDanViewImpl extends BaseViewListener {
    void onSubmitOrderSuc(BaseModel<ConfirmCutOrderBean> baseModel);
    void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList);
}
