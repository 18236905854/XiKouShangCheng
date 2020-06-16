package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.WuGPageBean;
import com.xk.mall.model.impl.WuG2MainImpl;
import com.xk.mall.model.request.ShareRequestBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: WuG2MainPresenter
 * @Description: 新版吾G页面请求Presenter
 * @Author: 卿凯
 * @Date: 2019/9/20/020 10:01
 * @Version: 1.0
 */
public class WuG2MainPresenter extends BasePresenter<WuG2MainImpl> {
    public WuG2MainPresenter(WuG2MainImpl baseView) {
        super(baseView);
    }

    /**
     * 查询吾G版块banner信息
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
     * 获取吾G商品数据接口
     * @param page 页数
     * @param limit 每页的条数
     * @param sort 排序字段，1：时间，2：价格
     * @param sortDirect 排序方向，1：升序，2：降序
     */
    public void getWuGData(int page, int limit, int sort, int sortDirect){
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("limit", limit);
        if(sort != 1){
            params.put("sort", sort);
            params.put("sortDirect", sortDirect);
        }
        addDisposable(apiServer.getWuGData(params), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetWuGDataSuccess((BaseModel<WuGPageBean>) o);
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
