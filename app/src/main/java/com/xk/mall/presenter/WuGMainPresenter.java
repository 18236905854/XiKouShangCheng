package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.HomeBean;
import com.xk.mall.model.entity.HomeMessageBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.WuGConfigBean;
import com.xk.mall.model.entity.WuGOrderMoneyBean;
import com.xk.mall.model.impl.HomeDataImpl;
import com.xk.mall.model.impl.WuGMainImpl;
import com.xk.mall.model.request.ShareRequestBody;

import java.util.List;

/**
 * 吾G购 Presenter
 */
public class WuGMainPresenter extends BasePresenter<WuGMainImpl> {
    private static final String TAG = "WuGMainPresenter";

    public WuGMainPresenter(WuGMainImpl baseView) {
        super(baseView);
    }

    /***
     * 获取 banner
        @param moudle  1: 首页 2: 活动首页 3: 吾G频道
      * @param position 位置(1： 头部BANNER 2： 中部BANNER 3: 活动主图)
     */
//    public void getBannnerData(int moudle,int position){
//        addDisposable(apiServer.getBannerList(moudle,position), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(Object o) {
//                baseView.onGetBannerSuc((BaseModel<List< BannerBean >>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//                if (baseView != null) {
//                    baseView.showError(msg);
//                }
//            }
//        });
//    }

    /**
     * 查询活动版块信息
     * @param activityType 活动类型
     */
    public void getActiveSectionData(int activityType){
        addDisposable(apiServer.getActiveSectionData(activityType), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetActiveSectionSuccess((BaseModel<ActiveSectionBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 获取吾G分区开关和场次
     */
    public void getScheduleConfig(){
        addDisposable(apiServer.getWuGSchedule(), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetScheduleConfigSuccess((BaseModel<WuGConfigBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 根据版块ID获取该版块下的商品列表
     * @param categoryId 版块ID
     * @param activityType 活动类型
     * @param page 页数
     * @param limit 每页的条数
     */
    public void getActiveSectionGoods(String categoryId, int activityType,String userId, int page, int limit){
        addDisposable(apiServer.getActiveSectionGoods(categoryId, activityType,userId, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetActiveSectionGoodsSuccess((BaseModel<ActiveSectionGoodsPageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 获取分享内容
     */
    public void getShareContent(String userId, String activityId, int type){
        ShareRequestBody shareRequestBody = new ShareRequestBody();
        shareRequestBody.setShareUserId(userId);
        shareRequestBody.setActivityId(activityId);
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

    /**
     * 获取吾G订单额度
     */
    public void getTotalMoney(String userId){
        addDisposable(apiServer.getWuGOrderMoney(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetMoneySuccess((BaseModel<WuGOrderMoneyBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
