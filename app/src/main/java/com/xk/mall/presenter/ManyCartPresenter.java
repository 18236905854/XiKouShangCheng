package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ManyBuyRateBean;
import com.xk.mall.model.entity.ManyCartsBean;
import com.xk.mall.model.impl.ManyCartViewImpl;
import com.xk.mall.model.request.ModifyBuyerNumInBuyerCartRequestBody;

import java.util.List;

/**
 * 多买多折购物车 Presenter
 */
public class ManyCartPresenter extends BasePresenter<ManyCartViewImpl> {
    private static final String TAG = "ManyCartPresenter";

    public ManyCartPresenter(ManyCartViewImpl baseView) {
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

    /**
     * 更新购物车商品数量
     */
    public void updataNum(String cartId, int num){
        ModifyBuyerNumInBuyerCartRequestBody modifyBuyerNumInBuyerCartRequestBody = new ModifyBuyerNumInBuyerCartRequestBody();
        modifyBuyerNumInBuyerCartRequestBody.setBuyerCartId(cartId);
        modifyBuyerNumInBuyerCartRequestBody.setBuyerNumber(num);
        addDisposable(apiServer.modifyBuyerNumInBuyerCart(modifyBuyerNumInBuyerCartRequestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
            }

            @Override
            public void onError(String msg) {

            }
        });
    }


    /**
     * 查询多买多折商品折扣费率
     * @param activityId 活动ID
     * @param commodityId 商品sku ID
     */
    public void getManyBuyRate(String activityId, String commodityId){
        addDisposable(apiServer.getManyBuyRate(activityId, commodityId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetManyRateSuccess((BaseModel<ManyBuyRateBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
