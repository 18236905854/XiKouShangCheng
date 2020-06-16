package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.GlobalBuyerOrderDetailBean;
import com.xk.mall.model.entity.SellOrderDetailBean;
import com.xk.mall.model.impl.SellOrderDetailViewImpl;

/**
 * @ClassName: SellOrderDetailPresenter
 * @Description: 寄卖订单详情请求类
 * @Author: 卿凯
 * @Date: 2019/10/16/016 10:01
 * @Version: 1.0
 */
public class SellOrderDetailPresenter extends BasePresenter<SellOrderDetailViewImpl> {
    public SellOrderDetailPresenter(SellOrderDetailViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取订单详情
     * @param orderNo 订单号
     */
    public void getOrderDetail(String orderNo){
        addDisposable(apiServer.getMySellOrderDetail(orderNo), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetOrderDetailSuccess((BaseModel<SellOrderDetailBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
