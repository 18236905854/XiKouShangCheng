package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.CutOrderBean;
import com.xk.mall.model.entity.FightGroupOrderBean;
import com.xk.mall.model.entity.ManyBuyOrderBean;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.entity.ZeroOrderBean;

/**
 * ClassName OrderFilterViewImpl
 * Description 订单过滤页面view接口
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public interface OrderFilterViewImpl extends BaseViewListener {
    //获取搜索后的全球买手和吾G订单成功回调
    void onGetFiltedOrderListSuccess(BaseModel<OrderPageBean> model);
    //获取搜索后的多买多折订单成功回调
    void onGetFiltedManyBuyOrderListSuccess(BaseModel<ManyBuyOrderBean> model);
    //获取搜索后的0元拍订单成功回调
    void onGetFiltedZeroOrderListSuccess(BaseModel<ZeroOrderBean> model);
    //获取搜索后的喜立得订单成功回调
    void onGetFiltedCutOrderListSuccess(BaseModel<CutOrderBean> model);
    //获取搜索后的定制拼团订单成功回调
    void onGetFiltedFightGroupOrderListSuccess(BaseModel<FightGroupOrderBean> model);
}
