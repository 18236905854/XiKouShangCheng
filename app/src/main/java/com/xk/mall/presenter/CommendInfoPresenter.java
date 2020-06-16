package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CommendInfoBean;
import com.xk.mall.model.impl.CommendInfoViewImpl;

/**
 * ClassName CommendInfoPresenter
 * Description 推荐人信息请求Presenter
 * Author 卿凯
 * Date 2019/7/26/026
 * Version V1.0
 */
public class CommendInfoPresenter extends BasePresenter<CommendInfoViewImpl> {
    public CommendInfoPresenter(CommendInfoViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取推荐人信息
     */
    public void getCommendInfo(String userId){
        addDisposable(apiServer.getCommendInfo(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetCommendInfoSuccess((BaseModel<CommendInfoBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
