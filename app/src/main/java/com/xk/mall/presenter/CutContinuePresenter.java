package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CutSuccessBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.impl.CutContinuelViewImpl;

/**
 *
 * 喜立得商品详情页面的presenter
 *
 */
public class CutContinuePresenter extends BasePresenter<CutContinuelViewImpl> {
    public CutContinuePresenter(CutContinuelViewImpl baseView) {
        super(baseView);
    }


    /**
     *
     * 获取砍价信息
     * @param cutId   砍价商品id  列表 主键
     */
    public void goCutContinue(String userId,String activityId,String cutId){
        addDisposable(apiServer.getCutContinute(userId,activityId,cutId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGoodsContinueSuccess((BaseModel<CutSuccessBean>) o);
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
