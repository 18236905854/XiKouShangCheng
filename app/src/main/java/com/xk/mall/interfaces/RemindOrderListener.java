package com.xk.mall.interfaces;

import com.xk.mall.model.entity.OrderStateBean;

/**
 * @ClassName: DeleteOrderListener
 * @Description: 提醒发货订单接口
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public interface RemindOrderListener {
    void remindOrder(OrderStateBean.ResultBean item, int orderType);
}
