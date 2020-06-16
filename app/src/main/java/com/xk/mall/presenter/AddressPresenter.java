package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.impl.AddressViewImpl;

import java.util.List;

/**
 * ClassName AddressPresenter
 * Description 我的地址的presenter
 * Author Kay
 * Date 2019/6/10/010
 * Version V1.0
 */
public class AddressPresenter extends BasePresenter<AddressViewImpl> {
    public AddressPresenter(AddressViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据用户ID获取用户地址列表
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
     * 根据地址ID删除地址信息
     */
    public void deleteAddress(String addressID){
        addDisposable(apiServer.deleteAddress(addressID), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onDeleteAddressSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
