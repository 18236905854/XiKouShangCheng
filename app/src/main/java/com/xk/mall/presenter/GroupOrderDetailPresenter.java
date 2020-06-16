package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.FightGroupOrderBean;
import com.xk.mall.model.entity.GroupOrderDetailBean;
import com.xk.mall.model.impl.FightGroupOrderImpl;
import com.xk.mall.model.impl.GroupOrderDetailImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.model.request.RemindRequestBody;

/**
 * 定制拼团订单详情 Presenter
 */
public class GroupOrderDetailPresenter extends BasePresenter<GroupOrderDetailImpl> {
    private static final String TAG = "GroupOrderDetailPresenter";

    public GroupOrderDetailPresenter(GroupOrderDetailImpl baseView) {
        super(baseView);
    }

    /**
     * 获取订单详情
     * @param orderNo
     */
    public void getGroupOrderDetail(String orderNo){
        addDisposable(apiServer.getGroupOrderDetail(orderNo), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetOrderDetailSuc((BaseModel<GroupOrderDetailBean>) o);
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.showError(msg);
                }
            }
        });
    }

    /**
     * 提醒发货
     */
    public void remindShip(String orderNo, int orderType){
        RemindRequestBody remindRequestBody = new RemindRequestBody();
        remindRequestBody.setOrderNo(orderNo);
        remindRequestBody.setOrderType(orderType);
        addDisposable(apiServer.remindShip(remindRequestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onRemindShipSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 延长收货时间
     */
    public void extendTheTime(String orderNo, int orderType){
        RemindRequestBody remindRequestBody = new RemindRequestBody();
        remindRequestBody.setOrderNo(orderNo);
        remindRequestBody.setOrderType(orderType);
        addDisposable(apiServer.extendTheTime(remindRequestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onExtendTheTimeSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    //取消订单
    public void cancelGroupOrder(CancelOrderRequestBody requestBody){
        addDisposable(apiServer.cancelOrder(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetCancelOrderSuc((BaseModel<String>) o);
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.showError(msg);
                }
            }
        });
    }

    /**
     * 确认收货
     * @param orderNo 订单号
     * @param orderType 订单类型 订单类型;1-0元竞拍订单;2-多买多折订单;3-喜立得订单;4-吾G订单;5-全球买手订单;6-定制拼团订单;7:OTO订单
     */
    public void completeOrder(String orderNo, int orderType){
        CancelOrderRequestBody requestBody = new CancelOrderRequestBody();
        requestBody.setOrderNo(orderNo);
        requestBody.setOrderType(orderType);
        addDisposable(apiServer.completeOrder(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onCompleteOrderSuccess((BaseModel<String>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
//
//    //删除订单
    public void deleterGroupOrder(DeleteOrderRequestBody requestBody){
        addDisposable(apiServer.deleteOrder(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetDeleteOrderSuc((BaseModel<String>) o);
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.showError(msg);
                }
            }
        });
    }





}
