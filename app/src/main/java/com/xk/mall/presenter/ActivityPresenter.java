package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActivityBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.impl.ActivityViewImpl;

import java.util.List;

/**
 * ClassName ActivityPresenter
 * Description 活动页面请求Presenter
 * Author 卿凯
 * Date 2019/7/18/018
 * Version V1.0
 */
public class ActivityPresenter extends BasePresenter<ActivityViewImpl>{
    public ActivityPresenter(ActivityViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取活动数据
     */
    public void getActivityData(){
        addDisposable(apiServer.getActivityData(), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetActivityDataSuccess((BaseModel<ActivityBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 获取活动数据
     */
    public void getBannerList(){
        addDisposable(apiServer.getBannerList(2,3), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetBannerSuc((BaseModel<List<BannerBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
