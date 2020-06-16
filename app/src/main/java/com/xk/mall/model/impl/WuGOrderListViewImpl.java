package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.WuGOrderMoneyBean;

/**
 * @ClassName: WuGOrderListViewImpl
 * @Description: 吾G订单列表页面view接口
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public interface WuGOrderListViewImpl extends BaseViewListener {
    //获取吾G订单额度成功
    void onGetMoneySuccess(BaseModel<WuGOrderMoneyBean> moneyBeanBaseModel);
}
