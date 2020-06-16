package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CommunityFlowBean;
import com.xk.mall.model.impl.CommunityFlowViewImpl;

/**
 * ClassName CommunityFlowPresenter
 * Description 社交流量页面请求Presenter
 * Author 卿凯
 * Date 2019/8/2/002
 * Version V1.0
 */
public class CommunityFlowPresenter extends BasePresenter<CommunityFlowViewImpl> {
    public CommunityFlowPresenter(CommunityFlowViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取社交流量接口
     */
    public void getCommunity(String userId){
        addDisposable(apiServer.getCommunity(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetCommunitySuccess((BaseModel<CommunityFlowBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
