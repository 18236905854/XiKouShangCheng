package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CustomGuanBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.impl.DesignerViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;
import com.xk.mall.model.request.ShareRequestBody;

import java.util.List;

/**
 * ClassName CustomPresenter
 * Description 设计师主页页面请求的presenter
 * Author 卿凯
 * Date 2019/6/20/020
 * Version V1.0
 */
public class DesignerPresenter extends BasePresenter<DesignerViewImpl> {
    private static final String TAG = "DesignerPresenter";
    public DesignerPresenter(DesignerViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据ID获取设计师主页信息，包含
     */
    public void getDesignerUserInfoById(String designerId,String userId){
        addDisposable(apiServer.getDesignerUserInfo(designerId,userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetDesignerInfoSuc((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }

        });
    }

    /**
     * 获取设计师作品集合
     */
    public void getDesignerWorkList(String designerId, String userId,int page,int limit){
        addDisposable(apiServer.getDesignerWorks(designerId,userId,page,limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetCustomSuc((BaseModel<CustomGuanBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 添加关注
     */
    public void addAttentionDesigner(AttentionRequestBody requestBody){
        addDisposable(apiServer.addAttentionDesigner(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onAttentionDesignerSuc((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 取消关注
     * @param requestBody
     */
    public void cancelAttentionDesigner(AttentionRequestBody requestBody){
        addDisposable(apiServer.addAttentionDesigner(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onCancelDegisnerSuc((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 获取分享内容
     */
    public void getShareContent(String userId, String designerId, int type){
        ShareRequestBody shareRequestBody = new ShareRequestBody();
        shareRequestBody.setShareUserId(userId);
        shareRequestBody.setDesignerId(designerId);
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
