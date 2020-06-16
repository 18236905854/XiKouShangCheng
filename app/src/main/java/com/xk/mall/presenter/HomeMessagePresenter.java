package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.HomeMessageBean;
import com.xk.mall.model.impl.HomeMessageViewImpl;

/**
 * ClassName HomeMessagePresenter
 * Description 消息中心的Presenter
 * Author 卿凯
 * Date 2019/7/3/003
 * Version V1.0
 */
public class HomeMessagePresenter extends BasePresenter<HomeMessageViewImpl> {
    public HomeMessagePresenter(HomeMessageViewImpl baseView) {
        super(baseView);
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
}
