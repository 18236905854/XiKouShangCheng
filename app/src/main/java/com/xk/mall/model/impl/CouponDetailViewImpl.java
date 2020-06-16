package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CouponDetailBean;
import com.xk.mall.model.entity.CouponListBean;

/**
 * ClassName CouponViewImpl
 * Description 优惠券页面的实现类
 * Author 卿凯
 * Date 2019/6/17/017
 * Version V1.0
 */
public interface CouponDetailViewImpl extends BaseViewListener {
    //获取优惠券详情成功回调
    void onCouponDetailSuccess(BaseModel<CouponDetailBean> model);
}
