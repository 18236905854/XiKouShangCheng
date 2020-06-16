package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.CutGoodsDetailBean;
import com.xk.mall.model.entity.CutSuccessBean;
import com.xk.mall.model.entity.FightGroupGoodsBean;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.ShareBean;

import java.util.List;

/**
 *
 * Description 定制拼团 view接口
 *
 */
public interface CutGoodsDetailViewImpl extends BaseViewListener {
    //获取商品详情成功
    void onGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> model);
    //砍价成功的回调
    void onCutSuccess(BaseModel<CutSuccessBean> model);

    //获取地址列表成功的回调
//    void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
