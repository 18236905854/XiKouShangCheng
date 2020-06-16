package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CouponTotalBean;
import com.xk.mall.model.impl.CouponMainViewImpl;

/**
 * @ClassName: CouponMainPresenter
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/9/29/029 9:15
 * @Version: 1.0
 */
public class CouponMainPresenter extends BasePresenter<CouponMainViewImpl> {
    public CouponMainPresenter(CouponMainViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据用户ID获取优惠券总额
     */
    public void getCouponTotal(String userId){
        addDisposable(apiServer.getCouponTotal(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetDataSuccess((BaseModel<CouponTotalBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
