package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ManyCartsBean;
import com.xk.mall.model.impl.ManyCartViewImpl;

import java.util.List;

/**
 * 吾G购  Presenter
 */
public class WuGBuyPresenter extends BasePresenter<ManyCartViewImpl> {
    private static final String TAG = "WuGBuyPresenter";

    public WuGBuyPresenter(ManyCartViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据ID 获取购物车数据
     */
    public void getCartData(String userId){
        addDisposable(apiServer.getCartListByUserId(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetCartDataSuc((BaseModel<List< ManyCartsBean >>) o);
            }

            @Override
            public void onError(String msg) {

            }

        });
    }

    /**
     * 根据id 删除 购物车
     */
    public void deleteCartData(String id){
        addDisposable(apiServer.deleteCartData(id), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onDeleteCartSuc((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }

        });
    }

}
