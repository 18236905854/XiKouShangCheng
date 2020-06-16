package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.ActivityRoundBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.HomeBean;
import com.xk.mall.model.entity.HomeMessageBean;

import java.util.List;


/**
 * 首页 消息 实现类
 */
public interface HomeDataImpl extends BaseViewListener {
    void onGetUnreadMessSuc(BaseModel<HomeMessageBean> model);

    /**
     * 获取首页数据成功
     * @param model
     */
    void onGetHomeDataSuc(BaseModel<HomeBean> model);

    /**
     * 获取0元拍轮次成功
     */
    void onRoundSuccess(BaseModel<List<ActivityRoundBean>> baseModel);

    /**
     * 获取轮播图成功
     * @param model
     */
    void onGetHomeBannerSuc(BaseModel<List<BannerBean>> model, int position);

    //获取版块信息成功的回调
    void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model ,int activityType);

    //获取版块下的商品成功
    void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model,int activityType);


}
