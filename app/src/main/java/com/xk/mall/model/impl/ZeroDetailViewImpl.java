package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ZeroAuctionBean;
import com.xk.mall.model.entity.ZeroGoodsDetailBean;

/**
 * ClassName ZeroDetailViewImpl
 * Description 0元拍详情的view接口
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public interface ZeroDetailViewImpl extends BaseViewListener {
    //获取竞拍情况成功
    void onGetZeroGoodsLunXunSuc(BaseModel<ZeroAuctionBean> model);
    //获取竞拍详情成功
    void onGetZeroGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> beanBaseModel);
    //出价成功
    void onGivePriceSuccess(BaseModel model);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
