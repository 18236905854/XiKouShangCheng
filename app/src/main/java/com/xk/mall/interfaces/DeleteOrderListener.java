package com.xk.mall.interfaces;

import com.xk.mall.model.entity.OrderStateBean;

/**
 * @ClassName: DeleteOrderListener
 * @Description: 删除订单接口
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public interface DeleteOrderListener {
    void deleteOrder(OrderStateBean.ResultBean item, int orderType);
}
