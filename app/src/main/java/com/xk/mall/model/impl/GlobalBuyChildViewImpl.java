package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.GlobalBuyerServerBean;

/**
 * ClassName ManyBuyChildViewImpl
 * Description 全球买手子页面接口
 * Author 卿凯
 * Date 2019/7/30/030
 * Version V1.0
 */
public interface GlobalBuyChildViewImpl extends BaseViewListener {
    //获取版块下的商品成功
    void onGetActiveSectionGoodsSuccess(BaseModel< ActiveSectionGoodsPageBean> model);
}
