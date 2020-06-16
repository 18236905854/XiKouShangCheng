package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.GroupOrderDetailBean;
import com.xk.mall.model.entity.ZeroOrderDetailBean;
import com.xk.mall.model.impl.GroupOrderDetailImpl;
import com.xk.mall.model.impl.ZeroOrderDetailImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.model.request.InsertOrUpdateAddressRequestBody;
import com.xk.mall.model.request.RemindRequestBody;

/**
 * 0元拍 订单详情 Presenter
 */
public class ZeroOrderDetailPresenter extends BasePresenter<ZeroOrderDetailImpl> {
    private static final String TAG = "ZeroOrderDetailPresenter";

    public ZeroOrderDetailPresenter(ZeroOrderDetailImpl baseView) {
        super(baseView);
    }

    /**
     * 获取订单详情
     * @param orderNo
     */
    public void getOrderDetail(String orderNo){
        addDisposable(apiServer.getZeroOrderDetail(orderNo), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetOrderDetailSuc((BaseModel<ZeroOrderDetailBean>) o);
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.showError(msg);
                }
            }
        });
    }

    //取消订单
    public void cancelOrder(CancelOrderRequestBody requestBody){
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

//    //删除订单
    public void deleterOrder(DeleteOrderRequestBody requestBody){
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

    /**
     * 更新订单收货地址
     * @param orderNo  订单号
     * @param receiptAddressRef 收货人信息ID
     */
    public void insertOrUpdateAddress(String orderNo, String receiptAddressRef){
        InsertOrUpdateAddressRequestBody requestBody = new InsertOrUpdateAddressRequestBody();
        requestBody.setOrderNo(orderNo);
        requestBody.setReceiptAddressRef(receiptAddressRef);
        addDisposable(apiServer.insertOrUpdateAddress(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onInsertOrUpdateAddressSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 校验地址是否超出配送范围
     * @param addressRef 地址ID
     */
    public void verifyUserAddressInfo(String addressRef){
        addDisposable(apiServer.verifyUserAddressInfo(addressRef), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onVerifyUserAddressInfo((BaseModel<Boolean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
