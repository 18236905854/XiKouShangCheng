package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.OrderPageBean;

/**
 * @ClassName: PayBackViewImpl
 * @Description: 退款退货订单列表View接口
 * @Author: 卿凯
 * @Date: 2019/12/9/009 19:50
 * @Version: 1.0
 */
public interface PayBackViewImpl extends BaseViewListener {
    //获取退款列表成功
    void onGetPayBackListSuccess(BaseModel<OrderPageBean> model);
    //取消退款成功
    void cancelSalesReturnSuccess(BaseModel baseModel);
}
