package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressBean;

import java.util.List;

/**
 * ClassName AddressViewImpl
 * Description 地址管理view的接口
 * Author Kay
 * Date 2019/6/10/010
 * Version V1.0
 */
public interface AddressViewImpl extends BaseViewListener {
    //获取地址列表成功的回调
    void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList);
    //删除地址信息成功的回调
    void onDeleteAddressSuccess(BaseModel model);
}
