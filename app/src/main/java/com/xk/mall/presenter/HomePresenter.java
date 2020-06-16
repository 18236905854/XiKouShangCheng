package com.xk.mall.presenter;

import android.util.Log;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.ActivityRoundBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.HomeBean;
import com.xk.mall.model.entity.HomeMessageBean;
import com.xk.mall.model.impl.HomeDataImpl;
import com.youth.banner.Banner;

import java.util.List;

/**
 * 首页 Presenter
 */
public class HomePresenter extends BasePresenter<HomeDataImpl> {
    private static final String TAG = "HomePresenter";

    public HomePresenter(HomeDataImpl baseView) {
        super(baseView);
    }

    /***
     * 获取首页 banner
        @param moudle  1: 首页 2: 活动首页 3: 吾G频道
      * @param position 位置(1： 头部BANNER 2： 中部BANNER 3: 活动主图)
     */
    public void getBannnerData(int moudle,int position){
        addDisposable(apiServer.getBannerList(moudle,position), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetHomeBannerSuc((BaseModel<List< BannerBean >>) o, position);
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.showError(msg);
                }
            }
        });
    }

    //获取活动轮次，判断是否可以进入0元拍页面
    public void getActivityType(int activityType){
        addDisposable(apiServer.getActivityType(activityType), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onRoundSuccess((BaseModel<List<ActivityRoundBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    //根据用户ID获取用户未读消息数量
    public void getUnreadMessage(String userId){
        addDisposable(apiServer.getUnreadMessage(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetUnreadMessSuc((BaseModel<HomeMessageBean>) o);
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.showError(msg);
                }
            }
        });
    }


    //获取首页数据
    public void getHomeGoodsData(){
        addDisposable(apiServer.getHomeData(), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetHomeDataSuc((BaseModel<HomeBean>) o);
            }

            @Override
            public void onError(String msg) {
                if (baseView != null) {
                    baseView.showError(msg);
                }
            }
        });
    }

    /**
     * 查询活动版块信息
     * @param activityType 活动类型
     */
    public void getActiveSectionData(int activityType){
        addDisposable(apiServer.getActiveSectionData(activityType), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetActiveSectionSuccess((BaseModel<ActiveSectionBean>) o,activityType);
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
                baseView.onGetActiveSectionGoodsSuccess((BaseModel<ActiveSectionGoodsPageBean>) o,activityType);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
