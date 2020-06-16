package com.xk.mall.presenter;

import com.xk.mall.MyApplication;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.GlobalBuyerOrderBean;
import com.xk.mall.model.entity.GlobalBuyerOrderResultBean;
import com.xk.mall.model.impl.GlobalBuyerXiaDanViewImpl;
import com.xk.mall.model.request.GetOutSellRequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName GlobalBuyerXiaDanPresenter
 * Description 全球买手下单页面的Presenter
 * Author Kay
 * Date 2019/7/13/013
 * Version V1.0
 */
public class GlobalBuyerXiaDanPresenter extends BasePresenter<GlobalBuyerXiaDanViewImpl> {
    public GlobalBuyerXiaDanPresenter(GlobalBuyerXiaDanViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据用户ID获取地址列表
     * @param userId
     */
    public void getAddress(String userId){
        addDisposable(apiServer.getUserAddressList(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetAddressListSuc((BaseModel<List<AddressBean>>) o);
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
     * 下单
     */
    public void order(GlobalBuyerOrderBean globalBuyerOrderBean){
        addDisposable(apiServer.orderGlobalBuyer(globalBuyerOrderBean), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onOrderSuccess((BaseModel<GlobalBuyerOrderResultBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 查询该商品是否可以寄卖到吾G文字内容
     * @param commodityId  商品skuID
     * @param userId 用户ID
     */
    public void getOutSellContent(String commodityId, String userId){
        addDisposable(apiServer.getOutSellContent(userId, commodityId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetOutSellContent((BaseModel<String>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
