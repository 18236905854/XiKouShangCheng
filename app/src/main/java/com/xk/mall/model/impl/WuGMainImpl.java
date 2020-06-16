package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.HomeBean;
import com.xk.mall.model.entity.HomeMessageBean;
import com.xk.mall.model.entity.ResultPageBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.WuGConfigBean;
import com.xk.mall.model.entity.WuGOrderMoneyBean;

import java.util.List;


/**
 * 吾G 首页 实现类
 */
public interface WuGMainImpl extends BaseViewListener {
    /**
     * 获取轮播图成功
     * @param model
     */
//    void onGetBannerSuc(BaseModel<List<BannerBean>> model);

    //获取版块信息成功的回调
    void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model);
    //获取版块下的商品成功
    void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model);

    //获取场次
    void onGetScheduleConfigSuccess(BaseModel<WuGConfigBean> model);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);
    //获取吾G订单额度成功
    void onGetMoneySuccess(BaseModel<WuGOrderMoneyBean> moneyBeanBaseModel);
}
