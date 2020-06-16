package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;

/**
 * ClassName ManyBuyChildViewImpl
 * Description 多买多折子页面接口
 * Author 卿凯
 * Date 2019/7/30/030
 * Version V1.0
 */
public interface ManyBuyChildViewImpl extends BaseViewListener {
    //获取版块下的商品成功
    void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model);
}
