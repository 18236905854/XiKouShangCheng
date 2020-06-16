package com.xk.mall.presenter;


import com.xk.mall.MyApplication;
import com.xk.mall.api.ApiServer;
import com.xk.mall.base.BaseContent;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.entity.PaySwitchBean;
import com.xk.mall.model.entity.UpdateAppBean;
import com.xk.mall.model.impl.MainViewImpl;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * File descripition:
 *
 */

public class MainPresenter extends BasePresenter<MainViewImpl> {
    public MainPresenter(MainViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据条件查询行政区域
     * @param level 等级
     */
    public void getServerAddress(int level){
        apiServer.queryAreaByIeve(level).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBaseModel -> baseView.getAddressSuccess(listBaseModel));

//        addDisposable(apiServer.queryAreaByIeve(level), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(Object o) {
//                baseView.getAddressSuccess((BaseModel<List<AddressServerBean>>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//
//            }
//        });
    }

    /**
     * 在处理刷新token
     */
    public void refreshToken(String refreshToken){
        addDisposable(apiServer.refreshToken(refreshToken), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onRefreshTokenSuccess((BaseModel<LoginBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 获取支付开关
     */
    public void getPaySwitch(){
        apiServer.getPaySwitch().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<PaySwitchBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseModel<PaySwitchBean> paySwitchBeanBaseModel) {
                        if(paySwitchBeanBaseModel.getData() != null){
                            MyApplication.switchBean = paySwitchBeanBaseModel.getData();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 检查更新
     */
    public void checkUpdate(){
        addDisposable(apiServer.checkVersion(), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.checkVersionSuccess((BaseModel<UpdateAppBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
