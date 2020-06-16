package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.PayBackDetailBean;

/**
 * @ClassName: PayBackDetailViewImpl
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/12/9/009 22:01
 * @Version: 1.0
 */
public interface PayBackDetailViewImpl extends BaseViewListener {
    //获取数据成功
    void onGetDetailSuccess(BaseModel<PayBackDetailBean> backBeanBaseModel);
    //取消退款成功
    void cancelSalesReturnSuccess(BaseModel baseModel);
}
