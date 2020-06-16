package com.xk.mall.presenter;

import com.xk.mall.MyApplication;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.HuoDongGoodsBean;
import com.xk.mall.model.entity.PaySwitchBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.ZeroAuctionBean;
import com.xk.mall.model.entity.ZeroGoodsDetailBean;
import com.xk.mall.model.impl.ZeroDetailViewImpl;
import com.xk.mall.model.request.ShareRequestBody;
import com.xk.mall.model.request.ZeroGivePriceRequestBody;
import com.xk.mall.utils.XiKouUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName ZeroDetailPresenter
 * Description 0元拍详情页面Presenter
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public class ZeroDetailPresenter extends BasePresenter<ZeroDetailViewImpl> {
    public ZeroDetailPresenter(ZeroDetailViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据商品ID获取商品竞拍详情
     * @param goodsId  活动商品id
     * @param userId   用户id
     */
    public void getGoodsLunXu(String goodsId,String userId){
        if(XiKouUtils.isNullOrEmpty(userId)){
            userId = "null";//由于接口问题，当没有登录会报404，所以必须上传一个值来获取数据
        }
        addDisposable(apiServer.getZeroGoodsAuctionByGoodsId(goodsId,userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetZeroGoodsLunXunSuc((BaseModel<ZeroAuctionBean>) o);
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
     * 获取0元拍商品详情
     */
    public void getGoodsDetail(String activityId, int activityType,  String userId){
        getPaySwitch();
        addDisposable(apiServer.getZeroGoodsDetailByGoodsId(activityId,activityType,userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetZeroGoodsDetailSuccess((BaseModel<GlobalBuyerGoodsDetailBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 竞拍出价
     */
    public void offer(ZeroGivePriceRequestBody rRequestBody){
        addDisposable(apiServer.givePrice(rRequestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGivePriceSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

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
