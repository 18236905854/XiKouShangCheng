package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.WuGGoodsDetailBean;

/**
 * ClassName WuGGoodsDetailViewImpl
 * Description 吾G商品详情请求Presenter
 * Author 卿凯
 * Date 2019/7/12/012
 * Version V1.0
 */
public interface WuGGoodsDetailViewImpl extends BaseViewListener {
    //获取商品详情成功的回调
    void onGetWuGGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> model);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
