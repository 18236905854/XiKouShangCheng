package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.GoodsServerDetailBean;
import com.xk.mall.model.entity.HuoDongGoodsBean;
import com.xk.mall.model.entity.ManyCartsBean;
import com.xk.mall.model.entity.ShareBean;

import java.util.List;

/**
 * ClassName ManyGoodsDetailViewImpl
 * Description 多买多折商品详情页面的view接口
 * Author 卿凯
 * Date 2019/7/7/007
 * Version V1.0
 */
public interface ManyGoodsDetailViewImpl extends BaseViewListener {
    void onGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> model);

    void onAddCartSuce(BaseModel model);

    //获取购物车数量成功
    void onGetCartDataSuc(BaseModel<List<ManyCartsBean>> baseModel);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
