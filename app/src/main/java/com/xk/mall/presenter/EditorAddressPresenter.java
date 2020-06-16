package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.impl.EditorAddressViewImpl;
import com.xk.mall.model.request.UserAddressRequestBody;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName EditorAddressPresenter
 * Description 编辑和新增地址的请求Presenter
 * Author Kay
 * Date 2019/7/6/006
 * Version V1.0
 */
public class EditorAddressPresenter extends BasePresenter<EditorAddressViewImpl> {
    public EditorAddressPresenter(EditorAddressViewImpl baseView) {
        super(baseView);
    }

    /**
     * 修改地址
     */
    public void saveAddress(String addressId, UserAddressRequestBody addressRequestBody){
        addDisposable(apiServer.updateUserAddress(addressId, addressRequestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onUpdateAddressSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 新增用户地址
     */
    public void addAddress(UserAddressRequestBody requestBody){
        addDisposable(apiServer.insertUserAddress(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onAddAddressSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 根据条件查询行政区域
     * @param level 等级
     */
    public void getServerAddress(int level){
//        apiServer.queryAreaByIeve(level).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(listBaseModel -> baseView.getAddressSuccess(listBaseModel));

        addDisposable(apiServer.queryAreaByIeve(level), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.getAddressSuccess((BaseModel<List<AddressServerBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
