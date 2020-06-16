package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.impl.GroupMainViewImpl;
import com.xk.mall.model.request.ShareRequestBody;

/**
 * ClassName GroupMainPresenter
 * Description 定制拼团页面
 * Author Kay
 * Date 2019/7/24/024
 * Version V1.0
 */
public class GroupMainPresenter extends BasePresenter<GroupMainViewImpl>{
    public GroupMainPresenter(GroupMainViewImpl baseView) {
        super(baseView);
    }

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
}
