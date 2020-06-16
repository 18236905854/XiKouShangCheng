package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AliPayResultBean;
import com.xk.mall.model.entity.PromotionShareBean;

/**
 *
 * 我的推广view接口
 *
 *
 *
 */
public interface MyPromotionViewImpl extends BaseViewListener {
    void onGetPromotionSuc(BaseModel<PromotionShareBean> model);
}
