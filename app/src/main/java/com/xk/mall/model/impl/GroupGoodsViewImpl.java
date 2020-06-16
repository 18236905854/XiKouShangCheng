package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ConfirmGroupOrderBean;

import java.util.List;

/**
 * 定制拼团 商品 view
 */
public interface GroupGoodsViewImpl extends BaseViewListener {
    void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList);

    void onSubmitOrderSuc(BaseModel<ConfirmGroupOrderBean> baseModel);
}
