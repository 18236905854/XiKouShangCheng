package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.OtoOrderPageBean;

/**
 * ClassName OTOOrderViewImpl
 * Description OTO联盟订单view接口
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public interface OTOOrderViewImpl extends BaseViewListener {
    //获取oto联盟订单列表
    void onGetOtoOrderListSuccess(BaseModel<OtoOrderPageBean> model);
}
