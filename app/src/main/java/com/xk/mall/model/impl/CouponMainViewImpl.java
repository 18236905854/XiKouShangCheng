package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CouponTotalBean;

/**
 * @ClassName: CouponMainViewImpl
 * @Description: 优惠券总额页面接口
 * @Author: 卿凯
 * @Date: 2019/9/29/029 9:15
 * @Version: 1.0
 */
public interface CouponMainViewImpl extends BaseViewListener {
    void onGetDataSuccess(BaseModel<CouponTotalBean> model);
}
