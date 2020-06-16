package com.xk.mall.interfaces;

import com.xk.mall.model.entity.OrderStateBean;

/**
 * @ClassName: CancelOrderListener
 * @Description: 确认收货订单接口
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public interface SureOrderListener {
    void sureOrder(OrderStateBean.ResultBean item, int orderType);
}
