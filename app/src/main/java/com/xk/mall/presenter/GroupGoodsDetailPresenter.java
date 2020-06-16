package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.FightGroupGoodsBean;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.impl.FightGroupGoodsDetailViewImpl;
import com.xk.mall.model.request.ShareRequestBody;

/**
 *
 * Description 定制拼团商品详情页面的presenter
 *
 */
public class GroupGoodsDetailPresenter extends BasePresenter<FightGroupGoodsDetailViewImpl> {
    public GroupGoodsDetailPresenter(FightGroupGoodsDetailViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据商品ID获取商品详情
     * @param activityGoodsId   活动商品id
     * @param  activityType 活动类型  1 （买一赠2(吾G)） 2 （全球买手）  3 （0元竞拍）  4（多买多折） 5（喜立得 红包）6（定制拼团）
     */
    public void getGoodsDetailByGoodsId(String activityGoodsId, int activityType, String userId){
        addDisposable(apiServer.getGroupGoodsDetail(activityGoodsId,activityType, userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGoodsDetailSuccess((BaseModel<GlobalBuyerGoodsDetailBean>) o);
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
