package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.SettlementMxChildBean;

/**
 * ClassName SettlementDetailViewImpl
 * Description 待结算明细详情
 * Author 卿凯
 * Date 2019/8/2/002
 * Version V1.0
 */
public interface SettlementDetailViewImpl extends BaseViewListener {
    //获取详情成功回调
    void onGetDetailSuccess(BaseModel<SettlementMxChildBean> mxChildBeanBaseModel);
}
