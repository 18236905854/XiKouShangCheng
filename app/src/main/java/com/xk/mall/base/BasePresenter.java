package com.xk.mall.base;


import com.xk.mall.api.ApiRetrofit;
import com.xk.mall.api.ApiServer;
import com.xk.mall.model.entity.LoginBean;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
/**
 * File descripition:   创建Presenter基类
 */
public class BasePresenter<V extends BaseViewListener> {
    private CompositeDisposable compositeDisposable;
    public V baseView;
    protected ApiServer apiServer = ApiRetrofit.getInstance().getApiService();

    public BasePresenter(V baseView) {
        this.baseView = baseView;
    }
    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        removeDisposable();
    }

    /**
     * 在基类里面处理刷新token
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
     * 返回 view
     *
     * @return
     */
    public V getBaseView() {
        return baseView;
    }

    public void addDisposable(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    public void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }
}
