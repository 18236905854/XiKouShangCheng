package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressServerBean;

import java.util.List;

/**
 * ClassName EditorAddressViewImpl
 * Description 编辑和新增地址的view接口
 * Author 卿凯
 * Date 2019/7/6/006
 * Version V1.0
 */
public interface EditorAddressViewImpl extends BaseViewListener {
    //更新用户地址成功
    void onUpdateAddressSuccess(BaseModel baseModel);
    //新增用户地址成功
    void onAddAddressSuccess(BaseModel baseModel);
    //获取地址成功
    void getAddressSuccess(BaseModel<List<AddressServerBean>> listBaseModel);
}
