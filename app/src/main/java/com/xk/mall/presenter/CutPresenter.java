package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.CutPageBean;
import com.xk.mall.model.entity.CutShangJiaBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.impl.CutViewImpl;
import com.xk.mall.model.request.ShareRequestBody;

/**
 * ClassName CutPresenter
 * Description 喜立得页面请求Presenter
 * Author 卿凯
 * Date 2019/7/11/011
 * Version V1.0
 */
public class CutPresenter extends BasePresenter<CutViewImpl> {
    public CutPresenter(CutViewImpl baseView) {
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
     * 根据版块ID获取该版块下的商品列表
     * @param categoryId 版块ID
     * @param activityType 活动类型
     * @param page 页数
     * @param limit 每页的条数
     */
    public void getActiveSectionGoods(String categoryId, int activityType, String userId,int page, int limit){
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
     * 根据版块ID获取该版块下的商品列表
     * @param categoryId 版块ID
     * @param activityType 活动类型
     * @param page 页数
     * @param limit 每页的条数
     */
    public void getActiveSectionTwoGoods(String categoryId, int activityType, String userId,int page, int limit){
        addDisposable(apiServer.getActiveSectionGoods(categoryId, activityType, userId,page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetActiveSectionTwoGoodsSuccess((BaseModel<ActiveSectionGoodsPageBean>) o);
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
