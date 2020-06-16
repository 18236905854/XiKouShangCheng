package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AliPayResultBean;

/**
 * ClassName AliPayViewImpl
 * Description 支付宝支付view接口
 * Author 卿凯
 * Date 2019/7/23/023
 * Version V1.0
 */
public interface AliPayViewImpl extends BaseViewListener {
    void onGetAliPayDataSuccess(BaseModel<AliPayResultBean> model);
}
