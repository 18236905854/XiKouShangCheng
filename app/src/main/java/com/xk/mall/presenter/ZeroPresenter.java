package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.ActivityRoundBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.ZeroCurrentPriceBean;
import com.xk.mall.model.entity.ZeroGoodsBean;
import com.xk.mall.model.impl.ZeroViewImpl;
import com.xk.mall.model.request.ShareRequestBody;

import java.util.List;

/**
 * ClassName ZeroPresenter
 * Description 0元竞拍页面请求数据的Presenter
 * Author 卿凯
 * Date 2019/7/5/005
 * Version V1.0
 */
public class ZeroPresenter extends BasePresenter<ZeroViewImpl> {
    public ZeroPresenter(ZeroViewImpl baseView) {
        super(baseView);
    }

    //获取活动轮次
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

    /**
     * 根据轮次ID获取该轮次下的活动商品
     * @param roundId  轮次ID
     */
    public void getGoodsByRoundId(String roundId){
        addDisposable(apiServer.getGoodsByRoundId(roundId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetGoodsByRoundIdSuccess((BaseModel<List<ZeroGoodsBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }


    /**
     * 根据轮次ID获取该轮次下的活动商品 当前竞拍价 剩余时间
     * @param roundId  轮次ID
     */
    public void getGoodsCurrentPriceByRoundId(String roundId){
        addDisposable(apiServer.getZeroFragGoodsInfo(roundId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetGoodsCurrentPrice((BaseModel<List<ZeroCurrentPriceBean>>) o);
            }

            @Override
            public void onError(String msg) {

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

    //根据活动轮次ID获取活动商品竞拍情况
    public void getGoodsInfos(String activityId){
        addDisposable(apiServer.getGoodsInfo(activityId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {

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
