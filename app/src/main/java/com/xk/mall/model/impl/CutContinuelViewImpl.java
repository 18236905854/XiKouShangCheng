package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.CutGoodsDetailBean;
import com.xk.mall.model.entity.CutSuccessBean;

import java.util.List;

/**
 *
 * Description 定制拼团 view接口
 *
 */
public interface CutContinuelViewImpl extends BaseViewListener {
    //获取商品详情成功
    void onGoodsContinueSuccess(BaseModel<CutSuccessBean> model);
    //分享回调
    void onShareCallback(BaseModel model);
}
