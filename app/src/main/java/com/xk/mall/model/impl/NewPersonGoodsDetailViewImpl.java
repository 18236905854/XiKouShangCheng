package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.ShareBean;

/**
 * @ClassName: NewPersonGoodsDetailViewImpl
 * @Description: 新人专区商品详情view接口
 * @Author: 卿凯
 * @Date: 2019/9/5/005 9:48
 * @Version: 1.0
 */
public interface NewPersonGoodsDetailViewImpl extends BaseViewListener {
    //获取商品详情成功的回调
    void onGetNewGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> model);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
