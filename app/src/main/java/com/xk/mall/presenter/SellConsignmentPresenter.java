package com.xk.mall.presenter;

import android.text.TextUtils;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OrderPageBean;
import com.xk.mall.model.impl.SellConsignmentViewImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName SellConsignmentPresenter
 * Description 我是卖家 寄卖订单请求Presenter
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public class SellConsignmentPresenter extends BasePresenter<SellConsignmentViewImpl> {
    public SellConsignmentPresenter(SellConsignmentViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取寄卖订单列表
     * @param userAccount 我的账号，手机号
     */
    public void getSellConsignmentOrderList(String userAccount,String searchName, int state, String orderAmountL,
                                            String orderAmountR, int page, int limit){

        addDisposable(apiServer.getSellConsignmentOrderList(userAccount, searchName, state, orderAmountL, orderAmountR, page, limit),
                new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetSellConsignmentOrderListSuccess((BaseModel<OrderPageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 获取寄卖订单列表
     * @param userAccount 我的账号，手机号
     */
    public void getSellConsignmentSearchOrderList(String userAccount,String searchName, int state, int createTimeFlag, String orderAmountL,
                                            String orderAmountR, int page, int limit){
        Map<String, Object> params = new HashMap<>();
        params.put("consignmentAccount", userAccount);
        params.put("page", page);
        params.put("limit", limit);
        if(!TextUtils.isEmpty(searchName)){
            params.put("searchName", searchName);
        }
        if(createTimeFlag != 0){
            params.put("createTimeFlag", createTimeFlag);
        }
        if(!TextUtils.isEmpty(orderAmountL)){
            params.put("orderAmountL", orderAmountL);
        }
        if(!TextUtils.isEmpty(orderAmountR)){
            params.put("orderAmountR", orderAmountR);
        }
        addDisposable(apiServer.getSellConsignmentOrderList(params),
                new BaseObserver(baseView) {
                    @Override
                    public void onSuccess(Object o) {
                        baseView.onGetSellConsignmentOrderListSuccess((BaseModel<OrderPageBean>) o);
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
    }
}
