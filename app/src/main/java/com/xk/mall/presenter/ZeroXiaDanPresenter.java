package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ConfirmZeroOrderBean;
import com.xk.mall.model.impl.ZeroXiaDanViewImpl;
import com.xk.mall.model.request.ZeroOrderRequestBody;

import java.util.List;

/**
 *  定制拼团 presenter
 */
public class ZeroXiaDanPresenter extends BasePresenter<ZeroXiaDanViewImpl> {
    public ZeroXiaDanPresenter(ZeroXiaDanViewImpl baseView) {
        super(baseView);
    }

    public void getAddressList(String userId){
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

    public void onSubmitOrder(ZeroOrderRequestBody requestBody){
        addDisposable(apiServer.createZeroOrder(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onSubmitOrderSuc((BaseModel<ConfirmZeroOrderBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
