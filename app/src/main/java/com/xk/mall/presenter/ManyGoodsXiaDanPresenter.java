package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ManyBuyOrderRequestBean;
import com.xk.mall.model.entity.ManyBuyOrderResultBean;
import com.xk.mall.model.impl.ManyGoodsXiaDanViewImpl;

import java.util.List;

/**
 * ClassName ManyGoodsXiaDanPresenter
 * Description 多买多折确认订单页面的Presenter
 * Author 卿凯
 * Date 2019/7/16/016
 * Version V1.0
 */
public class ManyGoodsXiaDanPresenter extends BasePresenter<ManyGoodsXiaDanViewImpl> {

    public ManyGoodsXiaDanPresenter(ManyGoodsXiaDanViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据用户ID获取地址列表
     * @param userId 用户ID
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
    public void order(ManyBuyOrderRequestBean wuGOrderBean){
        addDisposable(apiServer.orderManyBuy(wuGOrderBean), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onOrderSuccess((BaseModel<ManyBuyOrderResultBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
