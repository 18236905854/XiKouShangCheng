package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.ActivityRoundBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ZeroCurrentPriceBean;
import com.xk.mall.model.entity.ZeroGoodsBean;

import java.util.List;

/**
 * ClassName ZeroViewImpl
 * Description 0元拍页面view的接口
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public interface ZeroViewImpl extends BaseViewListener {
    void onRoundSuccess(BaseModel<List<ActivityRoundBean>> baseModel);

    void onGetGoodsByRoundIdSuccess(BaseModel<List<ZeroGoodsBean>> baseModel);

    void onGetGoodsCurrentPrice(BaseModel<List<ZeroCurrentPriceBean>> baseModel);
    
    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);

    //获取版块信息成功的回调
    void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model);
    //获取版块下的商品成功
    void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model);
}
