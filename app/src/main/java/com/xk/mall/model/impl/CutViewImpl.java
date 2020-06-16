package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.CutPageBean;
import com.xk.mall.model.entity.CutShangJiaBean;
import com.xk.mall.model.entity.ShareBean;


/**
 * ClassName CutViewImpl
 * Description 喜立得主页view接口
 * Author 卿凯
 * Date 2019/7/11/011
 * Version V1.0
 */
public interface CutViewImpl extends BaseViewListener {
    //获取版块信息成功的回调
    void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model);
    //获取版块下的商品成功
    void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model);
    //获取版块下的商品成功
    void onGetActiveSectionTwoGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
}
