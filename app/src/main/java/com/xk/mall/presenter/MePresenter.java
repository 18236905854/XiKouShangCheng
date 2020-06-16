package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.UserServerBean;
import com.xk.mall.model.impl.MeViewImpl;

/**
 * ClassName MePresenter
 * Description 我的页面的presenter
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public class MePresenter extends BasePresenter<MeViewImpl> {

    public MePresenter(MeViewImpl baseView) {
        super(baseView);
    }

    //根据用户ID获取用户信息接口
    public void getUserInfoById(String userId){
        addDisposable(apiServer.getMeUserInfoById(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetUserInfoSuccess((BaseModel<UserServerBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 根据用户ID获取用户可使用的优惠券的总金额
     * @param userId  用户ID
     */
    public void getCouponSum(String userId){
        addDisposable(apiServer.getCouponSum(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetCouponSumSuccess((BaseModel<Integer>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
