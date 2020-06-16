package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.CouponTotalBean;
import com.xk.mall.model.entity.GlobalBuyerServerBean;
import com.xk.mall.model.entity.ShareBean;

import java.util.List;

/**
 * ClassName GlobalBuyerViewImpl
 * Description 全球买手页面view的接口
 * Author 卿凯
 * Date 2019/7/7/007
 * Version V1.0
 */
public interface GlobalBuyerViewImpl extends BaseViewListener {
    //获取全球买手首页数据成功
    void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model);

    //获取分享信息成功
    void onGetShareSuccess(BaseModel<ShareBean> model);

    //分享回调
    void onShareCallback(BaseModel model);

    //获取版块下的商品成功
    void onGetActiveSectionGoodsSuccess(String categoryName,String categoryId,BaseModel<ActiveSectionGoodsPageBean> model);

    //获取优惠券成功
    void onGetDataSuccess(BaseModel<CouponTotalBean> model);
}
