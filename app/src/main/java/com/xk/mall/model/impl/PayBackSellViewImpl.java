package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.OrderPageBean;

/**
 * @ClassName: PayBackSellViewImpl
 * @Description: 我是卖家退款售后页面接口
 * @Author: 卿凯
 * @Date: 2019/12/10/010 10:18
 * @Version: 1.0
 */
public interface PayBackSellViewImpl extends BaseViewListener {
    //获取退款列表成功
    void onGetPayBackSellListSuccess(BaseModel<OrderPageBean> model);

}
