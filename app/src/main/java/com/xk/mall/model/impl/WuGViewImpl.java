package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;

/**
 * ClassName WuGViewImpl
 * Description 吾G页面view接口
 * Author 卿凯
 * Date 2019/7/12/012
 * Version V1.0
 */
public interface WuGViewImpl extends BaseViewListener {
    //获取吾G列表成功的回调
//    void onGetDataSuccess(BaseModel<WuGPageBean> model);
    //获取版块下的商品成功
    void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model);
}
