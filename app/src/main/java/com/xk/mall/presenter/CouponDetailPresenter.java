package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CouponDetailBean;
import com.xk.mall.model.impl.CouponDetailViewImpl;

/**
 * ClassName CouponDetailPresenter
 * Description 优惠券详情Presenter
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class CouponDetailPresenter extends BasePresenter<CouponDetailViewImpl> {
    public CouponDetailPresenter(CouponDetailViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据优惠券ID获取优惠券使用详情
     */
    public void getCouponDetail(String couponId){
        addDisposable(apiServer.queryCouponRecordByCouponId(couponId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onCouponDetailSuccess((BaseModel<CouponDetailBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
