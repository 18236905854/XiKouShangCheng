package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.PayBackDetailBean;
import com.xk.mall.model.impl.PayBackDetailViewImpl;

/**
 * @ClassName: PayBackDetailPresenter
 * @Description: 退款订单详情请求Presenter
 * @Author: 卿凯
 * @Date: 2019/12/9/009 22:01
 * @Version: 1.0
 */
public class PayBackDetailPresenter extends BasePresenter<PayBackDetailViewImpl> {
    public PayBackDetailPresenter(PayBackDetailViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取订单详情
     * @param buyerAccount 账号
     * @param orderType  订单类型
     * @param refundOrderNo  退款订单号
     */
    public void getOrderDetail(String buyerAccount, int orderType, String refundOrderNo){
        addDisposable(apiServer.getPayBackDetail(buyerAccount, orderType, refundOrderNo), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetDetailSuccess((BaseModel<PayBackDetailBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 取消退款
     * @param refundOrderNo 退款订单
     */
    public void cancelPayBack(String refundOrderNo){
        addDisposable(apiServer.cancelSalesReturn(refundOrderNo), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.cancelSalesReturnSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
