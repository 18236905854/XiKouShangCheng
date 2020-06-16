package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.PayOrderRequestBean;
import com.xk.mall.model.entity.PayPingResultBean;
import com.xk.mall.model.entity.PayResultBean;
import com.xk.mall.model.impl.PayViewImpl;

/**
 * ClassName PayPresenter
 * Description 支付请求Presenter
 * Author 卿凯
 * Date 2019/7/20/020
 * Version V1.0
 */
public class PayPresenter extends BasePresenter<PayViewImpl> {
    public PayPresenter(PayViewImpl baseView) {
        super(baseView);
    }

    /**
     * 调用微信支付
     * @param orderNo 订单号
     * @param userId 用户ID
     * @param orderType 订单类型
     * @param payAmount 订单金额
     */
    public void pay(String orderNo, String userId, int orderType, int payAmount, boolean isPing){
        PayOrderRequestBean requestBean = new PayOrderRequestBean();
        requestBean.setClientType("android");
        requestBean.setOrderNo(orderNo);
        requestBean.setOsType(1);
        requestBean.setOrderType(orderType);
        requestBean.setPaymentAmount(payAmount);
        requestBean.setPayType(3);
        requestBean.setPayWay(1);
        requestBean.setTimestamp(251151111);
        requestBean.setUserId(userId);
        if(!isPing){//使用原生
            addDisposable(apiServer.wxPay(requestBean), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onWXPaySuccess((BaseModel<PayResultBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }else {
            addDisposable(apiServer.wxPayPing(requestBean), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onPingWXPaySuccess((BaseModel<PayPingResultBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }

    /**
     * 支付宝支付
     */
    public void goAliPay(String orderNo, int orderType, String userId, boolean isPing){
        PayOrderRequestBean requestBean = new PayOrderRequestBean();
        requestBean.setClientType("android");
        requestBean.setOrderNo(orderNo);
        requestBean.setOsType(1);
        requestBean.setOrderType(orderType);
        requestBean.setPayType(3);
        requestBean.setPayWay(2);//支付方式;1-微信;2-支付宝;3-银行卡;4-优惠券;5-红包
        requestBean.setTimestamp(251151111);
        requestBean.setUserId(userId);
        if(!isPing){
            addDisposable(apiServer.aliPay(requestBean), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onGetAliPayDataSuccess((BaseModel<PayResultBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }else {
            addDisposable(apiServer.aliPayPing(requestBean), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onGetPingAliPayDataSuccess((BaseModel<PayPingResultBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
        }
    }

    /**
     * 红包支付
     */
    public void goRedPay(String orderNo, int orderType, String payPassword, int payMount, String userId){
        PayOrderRequestBean requestBean = new PayOrderRequestBean();
        requestBean.setClientType("android");
        requestBean.setOrderNo(orderNo);
        requestBean.setPayPassword(payPassword);
        requestBean.setPaymentAmount(payMount);
        requestBean.setOsType(1);
        requestBean.setOrderType(orderType);
        requestBean.setPayType(3);
        requestBean.setPayWay(5);//支付方式;1-微信;2-支付宝;3-银行卡;4-优惠券;5-红包
        requestBean.setUserId(userId);
        addDisposable(apiServer.redPay(requestBean), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onRedPaySuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
