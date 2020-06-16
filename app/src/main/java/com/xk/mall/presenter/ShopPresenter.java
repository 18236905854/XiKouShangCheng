package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.ShopBean;
import com.xk.mall.model.impl.AddressViewImpl;
import com.xk.mall.model.impl.ShopViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;
import com.xk.mall.model.request.ShareRequestBody;

import java.util.List;

/**
 * Description 店铺 presenter
 * Version V1.0
 */
public class ShopPresenter extends BasePresenter<ShopViewImpl> {
    public ShopPresenter(ShopViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取店铺详情
     * @param shopId 店铺id
     * @param userId userId
     */
    public void getShopDetail(String shopId,String userId){
        addDisposable(apiServer.getShopDetail(shopId,userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetShopDataSuc((BaseModel<ShopBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
    /**
     * 添加关注
     */
    public void addAttentionShop(AttentionRequestBody requestBody){
        addDisposable(apiServer.addAttentionShop(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onAttentionShopSuc((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 取消关注
     */
    public void cancelAttentionShop(AttentionRequestBody requestBody){
        addDisposable(apiServer.addAttentionShop(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onCancelShopSuc((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 获取分享内容
     */
    public void getShareContent(String userId, String shopId, int type){
        ShareRequestBody shareRequestBody = new ShareRequestBody();
        shareRequestBody.setShareUserId(userId);
        shareRequestBody.setShopId(shopId);
        shareRequestBody.setPopularizePosition(type);
        addDisposable(apiServer.getShareContent(shareRequestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetShareSuccess((BaseModel<ShareBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 分享回调接口
     */
    public void shareSuccessCallback(String userId, int sharePos){
        ShareSuccessBean shareSuccessBean = new ShareSuccessBean();
        shareSuccessBean.setPopularizeUserId(userId);
        shareSuccessBean.setPopularizeId(String.valueOf(sharePos));
        shareSuccessBean.setState(1);
        shareSuccessBean.setActivityGoodsCondition(new ShareSuccessBean.ActivityGoodsConditionBean());
        addDisposable(apiServer.shareCallback(shareSuccessBean), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onShareCallback((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
