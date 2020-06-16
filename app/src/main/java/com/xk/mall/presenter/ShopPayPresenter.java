package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OTOOrderRequestBean;
import com.xk.mall.model.entity.OTOOrderResultBean;
import com.xk.mall.model.impl.ShopPayViewImpl;

/**
 * ClassName ShopPayPresenter
 * Description 店铺收银台页面请求Presenter
 * Author 卿凯
 * Date 2019/7/11/011
 * Version V1.0
 */
public class ShopPayPresenter extends BasePresenter<ShopPayViewImpl> {

    public ShopPayPresenter(ShopPayViewImpl baseView) {
        super(baseView);
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

    /**
     * OTO下单
     */
    public void orderOto(OTOOrderRequestBean otoOrderRequestBean){
        addDisposable(apiServer.orderOto(otoOrderRequestBean), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onOrderSuccess((BaseModel<OTOOrderResultBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
