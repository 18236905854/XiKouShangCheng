package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AttenShopListBean;
import com.xk.mall.model.impl.AttentionShopViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;

/**
 * ClassName AttentionPresenter
 * Description 我的关注P层接口
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class AttentionShopPresenter extends BasePresenter<AttentionShopViewImpl> {
    public AttentionShopPresenter(AttentionShopViewImpl baseView) {
        super(baseView);
    }
    /**
     * 调用接口获取关注店铺数据
     * @param userId
     */
    public void getAttenShopListData(String userId,int page,int limit){
        addDisposable(apiServer.getAttenShopListDataByID(userId,page,limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onAttentionGetData((BaseModel<AttenShopListBean>) o);
            }
            @Override
            public void onError(String msg) {

            }
        });
    }


    /**
     * 取消 关注
     */
    public void cancelShop(AttentionRequestBody requestBody){
        addDisposable(apiServer.addAttentionShop(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.cancelAttentionSuccess((BaseModel)o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
