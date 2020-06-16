package com.xk.mall.interfaces;

import com.xk.mall.model.entity.OrderStateBean;

/**
 * @ClassName: CancelOrderListener
 * @Description: 取消订单接口
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public interface ModifyOrderStateListener {
    //shareModel 寄卖分享类型  ShareModel类中说明
    void modifyOrderState(OrderStateBean.ResultBean item, int orderType, int type, int shareModel);//type表示1 发货还是2 寄卖
}
