package com.xk.mall.presenter;

import com.xk.mall.MyApplication;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.PaySwitchBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.WuGGoodsDetailBean;
import com.xk.mall.model.impl.WuGGoodsDetailViewImpl;
import com.xk.mall.model.request.ShareRequestBody;
import com.xk.mall.utils.XiKouUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName WuGGoodsDetailPresenter
 * Description 吾G商品详情请求Presenter
 * Author 卿凯
 * Date 2019/7/12/012
 * Version V1.0
 */
public class WuGGoodsDetailPresenter extends BasePresenter<WuGGoodsDetailViewImpl> {
    public WuGGoodsDetailPresenter(WuGGoodsDetailViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取吾G商品详情
     */
    public void getWuGGoodsDetail(String activityId,int activityType, String userId){
        getPaySwitch();
        addDisposable(apiServer.getWuGGoodsDetailByGoodsId(activityId, activityType, userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetWuGGoodsDetailSuccess((BaseModel<GlobalBuyerGoodsDetailBean>) o);
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

}
