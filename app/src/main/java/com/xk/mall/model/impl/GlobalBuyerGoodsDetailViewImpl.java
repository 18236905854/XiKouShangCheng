package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CouponTotalBean;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.GoodsServerDetailBean;
import com.xk.mall.model.entity.HuoDongGoodsBean;
import com.xk.mall.model.entity.ShareBean;

/**
 * ClassName GlobalBuyerGoodsDetailViewImpl
 * Description 全球买手商品详情页面的view接口
 * Author 卿凯
 * Date 2019/7/7/007
 * Version V1.0
 */
public interface GlobalBuyerGoodsDetailViewImpl extends BaseViewListener {
    //获取商品详情成功
    void onGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> model);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);

    //获取优惠券成功
    void onGetDataSuccess(BaseModel<CouponTotalBean> model);
}
