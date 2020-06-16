package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.OTOOrderResultBean;

/**
 * ClassName ShopPayViewImpl
 * Description 附件店铺收银台页面view接口
 * Author 卿凯
 * Date 2019/7/11/011
 * Version V1.0
 */
public interface ShopPayViewImpl extends BaseViewListener {
    //获取用户优惠券总金额成功回调
    void onGetCouponSumSuccess(BaseModel<Integer> model);
    //下单成功的回调
    void onOrderSuccess(BaseModel<OTOOrderResultBean> model);
}
