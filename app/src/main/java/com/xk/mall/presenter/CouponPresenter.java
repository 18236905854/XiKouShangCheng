package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CouponListBean;
import com.xk.mall.model.impl.CouponViewImpl;

/**
 * ClassName CouponPresenter
 * Description 优惠券的presenter
 * Author 卿凯
 * Date 2019/6/17/017
 * Version V1.0
 */
public class CouponPresenter extends BasePresenter<CouponViewImpl> {
    public CouponPresenter(CouponViewImpl baseView) {
        super(baseView);
    }

    //根据用户ID查询可使用的优惠券
    public void getUsableCoupon(String userId, int page, int limit){
        addDisposable(apiServer.queryUsableBySelective(userId, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onCouponSuccess((BaseModel<CouponListBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    //根据用户ID查询已失效的优惠券
    public void getLostCoupon(String userId, int page, int limit){
        addDisposable(apiServer.queryLostEfficacyBySelective(userId, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onCouponSuccess((BaseModel<CouponListBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    //根据用户ID查询已使用的优惠券
    public void getUsedCoupon(String userId, int page, int limit){
        addDisposable(apiServer.queryUnusableBySelective(userId, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onCouponSuccess((BaseModel<CouponListBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
