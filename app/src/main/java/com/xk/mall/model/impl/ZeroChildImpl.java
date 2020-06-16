package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ZeroCurrentPriceBean;
import com.xk.mall.model.entity.ZeroGoodsBean;

import java.util.List;

/**
 * ClassName ZeroChildImpl
 * Description 0元竞拍子页面的View接口
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public interface ZeroChildImpl extends BaseViewListener {
    void onGetGoodsByRoundIdSuccess(BaseModel<List<ZeroGoodsBean>> baseModel);

    void onGetGoodsCurrentPrice(BaseModel<List<ZeroCurrentPriceBean>> baseModel);
}
