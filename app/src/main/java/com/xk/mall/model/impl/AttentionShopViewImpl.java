package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.AttenShopListBean;
import com.xk.mall.model.entity.DesignerBean;

import java.util.List;

/**
 *
 *  我的关注店铺的view层接口
 */
public interface AttentionShopViewImpl extends BaseViewListener {
    //获取关注列表成功
    void onAttentionGetData(BaseModel<AttenShopListBean> shopListBean);
    //取消关注成功
    void cancelAttentionSuccess(BaseModel baseModel);


}
