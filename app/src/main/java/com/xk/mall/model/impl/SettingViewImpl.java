package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressServerBean;

import java.util.List;

/**
 * @ClassName: SettingViewImpl
 * @Description: 设置页面接口
 * @Author: 卿凯
 * @Date: 2019/9/18/018 11:29
 * @Version: 1.0
 */
public interface SettingViewImpl extends BaseViewListener {
    void getAddressSuccess(BaseModel<List<AddressServerBean>> listBaseModel);
}
