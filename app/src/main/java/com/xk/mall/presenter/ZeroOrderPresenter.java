package com.xk.mall.presenter;

import android.text.TextUtils;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.FightGroupOrderBean;
import com.xk.mall.model.entity.OrderStateBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ZeroOrderBean;
import com.xk.mall.model.impl.FightGroupOrderImpl;
import com.xk.mall.model.impl.ZeroOrderImpl;
import com.xk.mall.model.request.CancelOrderRequestBody;
import com.xk.mall.model.request.DeleteOrderRequestBody;
import com.xk.mall.model.request.ModifyOrderTypeRequestBody;
import com.xk.mall.model.request.RemindRequestBody;

import java.util.HashMap;
import java.util.Map;

import retrofit2.http.Query;

/**
 * 定制拼团 Presenter
 */
public class ZeroOrderPresenter extends BasePresenter<ZeroOrderImpl> {
    private static final String TAG = "GroupOrderPresenter";

    public ZeroOrderPresenter(ZeroOrderImpl baseView) {
        super(baseView);
    }

    /**
     * 获取所有订单消息
     * @param searchName  商品名称/商品编号/订单编号
     * @param buyerAccount 买家账号
     * @param state
     * @param createTimeFlag   创建时间类型（1:一个月，2:三个月）
     * @param orderAmountL    订单金额左区间
     * @param orderAmountR    订单金额右区间
     * @param page
     * @param limit
     */
    public void getAllOrderList(String searchName, String buyerAccount, int state, int createTimeFlag, int orderAmountL, int orderAmountR, int page, int limit) {
        Map<String, Object> params = new HashMap<>();
        if(!TextUtils.isEmpty(searchName)){
            params.put("searchName", searchName);
        }
        if(createTimeFlag != 0){
            params.put("createTimeFlag", createTimeFlag);
        }
        if(orderAmountL != 0 || orderAmountR != 0){
            params.put("orderAmountL", orderAmountL);
            params.put("orderAmountR", orderAmountR);
        }
        params.put("buyerAccount", buyerAccount);
        params.put("page", page);
        params.put("state", state);
        params.put("limit", limit);
        addDisposable(apiServer.queryOrderListByState(params), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetOrderListDataSuc((BaseModel<OrderStateBean>) o);
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
    public void cancelOrder(CancelOrderRequestBody requestBody) {
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

    //删除订单
    public void deleteOrder(DeleteOrderRequestBody requestBody) {
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

    /**
     * 修改全球买手订单处理方式
     * @param userId 用户ID
     * @param orderNo 订单号
     * @param type 处理方式;1-自提;2-寄卖
     * @param shareModel 分享模式: 1-分享给好友;2-寄卖;3-两者都有
     */
    public void modifyOrderType(String userId, String orderNo, int shareModel, int type){
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
}
