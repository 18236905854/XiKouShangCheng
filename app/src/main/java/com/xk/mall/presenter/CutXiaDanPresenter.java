package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ConfirmCutOrderBean;
import com.xk.mall.model.entity.ConfirmGroupOrderBean;
import com.xk.mall.model.impl.CutXiaDanViewImpl;
import com.xk.mall.model.impl.GroupGoodsViewImpl;
import com.xk.mall.model.request.CutOrderRequestBody;
import com.xk.mall.model.request.GroupOrderRequestBody;

import java.util.List;

/**
 *  喜立得 presenter
 */
public class CutXiaDanPresenter extends BasePresenter<CutXiaDanViewImpl> {
    public CutXiaDanPresenter(CutXiaDanViewImpl baseView) {
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

    public void onSubmitOrder(CutOrderRequestBody requestBody){
        addDisposable(apiServer.createCutOrder(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onSubmitOrderSuc((BaseModel<ConfirmCutOrderBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
