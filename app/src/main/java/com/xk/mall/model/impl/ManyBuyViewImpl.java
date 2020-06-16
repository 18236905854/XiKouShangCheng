package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.ManyCartsBean;
import com.xk.mall.model.entity.ManyPageBean;
import com.xk.mall.model.entity.ShareBean;

import java.util.List;

/**
 * ClassName ManyBuyViewImpl
 * Description 多买多折页面的view接口
 * Author 卿凯
 * Date 2019/7/7/007
 * Version V1.0
 */
public interface ManyBuyViewImpl extends BaseViewListener {
    //获取数据成功的回调
//    void onGetDataSuccess(BaseModel<ManyPageBean> model);

    //获取版块信息成功的回调
    void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model);
    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //获取购物车数量成功
    void onGetCartDataSuc(BaseModel<List<ManyCartsBean>> baseModel);

    //分享回调
    void onShareCallback(BaseModel model);
    //获取版块下的商品成功
    void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model);
}
