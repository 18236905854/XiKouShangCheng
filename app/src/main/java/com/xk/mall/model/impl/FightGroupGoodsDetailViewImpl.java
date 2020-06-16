package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.FightGroupGoodsBean;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.HuoDongGoodsBean;
import com.xk.mall.model.entity.ShareBean;

/**
 *
 * Description 定制拼团 view接口
 *
 */
public interface FightGroupGoodsDetailViewImpl extends BaseViewListener {
    //获取商品详情成功
    void onGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> model);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
