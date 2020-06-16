package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.UserServerBean;

/**
 * ClassName MeViewImpl
 * Description 我的页面view的实现接口
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public interface MeViewImpl extends BaseViewListener {
    void onGetUserInfoSuccess(BaseModel<UserServerBean> baseModel);
    //获取用户优惠券总金额成功回调
    void onGetCouponSumSuccess(BaseModel<Integer> model);
}
