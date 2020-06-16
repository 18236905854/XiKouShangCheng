package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.DesignerBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShopBean;

import java.util.List;

/**
 * Description 店铺view接口
 */
public interface ShopViewImpl extends BaseViewListener {

    void onGetShopDataSuc(BaseModel<ShopBean> shopBean);
    //关注 成功
    void onAttentionShopSuc(BaseModel baseModel);

    //取消关注
    void onCancelShopSuc(BaseModel baseModel);
    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
