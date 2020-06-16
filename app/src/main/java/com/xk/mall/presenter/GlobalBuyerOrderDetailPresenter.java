package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.GlobalBuyerOrderDetailBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.impl.GlobalBuyerOrderDetailViewImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.model.request.ModifyOrderTypeRequestBody;
import com.xk.mall.model.request.RemindRequestBody;

/**
 * ClassName GlobalBuyerOrderDetailPresenter
 * Description 全球买手订单详情页面请求Presenter
 * Author 卿凯
 * Date 2019/7/15/015
 * Version V1.0
 */
public class GlobalBuyerOrderDetailPresenter extends BasePresenter<GlobalBuyerOrderDetailViewImpl> {
    public GlobalBuyerOrderDetailPresenter(GlobalBuyerOrderDetailViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取订单详情
     * @param orderNo 订单号
     */
    public void getOrderDetail(String orderNo){
        addDisposable(apiServer.getGlobalBuyerOrderDetail(orderNo), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetOrderDetailSuccess((BaseModel<GlobalBuyerOrderDetailBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 取消订单
     * @param orderNo 订单号
     * @param orderType 订单类型 订单类型;1-0元竞拍订单;2-多买多折订单;3-喜立得订单;4-吾G订单;5-全球买手订单;6-定制拼团订单;7:OTO订单
     */
    public void cancelOrder(String orderNo, int orderType){
        CancelOrderRequestBody requestBody = new CancelOrderRequestBody();
        requestBody.setOrderNo(orderNo);
        requestBody.setOrderType(orderType);
        addDisposable(apiServer.cancelOrder(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onCancelOrderSuccess((BaseModel<String>) o);
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
     * 修改全球买手订单处理方式
     * @param userId 用户ID
     * @param orderNo 订单号
     * @param type 处理方式;1-自提;2-寄卖
     * @param shareModel 分享模式: 1-分享给好友;2-寄卖;3-两者都有
     */
    public void modifyOrderType(String userId, String orderNo, int type, int shareModel){
        ModifyOrderTypeRequestBody requestBody = new ModifyOrderTypeRequestBody();
        requestBody.setBuyerId(userId);
        requestBody.setOrderNo(orderNo);
        requestBody.setShareModel(shareModel);
        requestBody.setProcessingMethod(type);
        addDisposable(apiServer.modifyOrderType(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onModifyOrderTypeSuccess((BaseModel<ShareBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 删除订单
     * @param orderNo 订单号
     * @param orderType 订单类型
     */
    public void deleteOrder(String orderNo, int orderType){
        DeleteOrderRequestBody body = new DeleteOrderRequestBody();
        body.setOrderNo(orderNo);
        body.setOrderType(orderType);
        addDisposable(apiServer.deleteOrder(body), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onDeleteSuccess((BaseModel<String>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
