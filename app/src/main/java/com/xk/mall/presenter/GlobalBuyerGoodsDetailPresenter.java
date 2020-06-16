package com.xk.mall.presenter;

import com.xk.mall.MyApplication;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CouponTotalBean;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.HuoDongGoodsBean;
import com.xk.mall.model.entity.PaySwitchBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.impl.GlobalBuyerGoodsDetailViewImpl;
import com.xk.mall.model.request.ShareRequestBody;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName GlobalBuyerGoodsDetailPresenter
 * Description 全球买手商品详情页面的presenter
 * Author 卿凯
 * Date 2019/7/7/007
 * Version V1.0
 */
public class GlobalBuyerGoodsDetailPresenter extends BasePresenter<GlobalBuyerGoodsDetailViewImpl> {
    public GlobalBuyerGoodsDetailPresenter(GlobalBuyerGoodsDetailViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据商品ID获取商品详情
     */
    public void getGoodsDetailByGoodsId(String activityGoodsId, int activityType, String userId){
        getPaySwitch();
        addDisposable(apiServer.getGlobalBuyerGoodsDetailByGoodsId(activityGoodsId, activityType, userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGoodsDetailSuccess((BaseModel<GlobalBuyerGoodsDetailBean>) o);
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
     * 获取分享内容
     */
    public void getShareContent(String userId, ShareRequestBody.ActivityGoodsConditionBean activityGoodsConditionBean, int type){
        ShareRequestBody shareRequestBody = new ShareRequestBody();
        shareRequestBody.setShareUserId(userId);
        shareRequestBody.setActivityGoodsCondition(activityGoodsConditionBean);
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
    public void shareSuccessCallback(String userId, int sharePos, ShareSuccessBean.ActivityGoodsConditionBean goodsConditionBean){
        ShareSuccessBean shareSuccessBean = new ShareSuccessBean();
        shareSuccessBean.setPopularizeUserId(userId);
        shareSuccessBean.setPopularizeId(String.valueOf(sharePos));
        shareSuccessBean.setState(1);
        shareSuccessBean.setActivityGoodsCondition(goodsConditionBean);
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
