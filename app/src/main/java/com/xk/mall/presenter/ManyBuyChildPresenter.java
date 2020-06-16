package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.impl.ManyBuyChildViewImpl;

/**
 * ClassName ManyBuyChildPresenter
 * Description 多买多折子页面请求Presenter
 * Author 卿凯
 * Date 2019/7/30/030
 * Version V1.0
 */
public class ManyBuyChildPresenter extends BasePresenter<ManyBuyChildViewImpl> {

    public ManyBuyChildPresenter(ManyBuyChildViewImpl baseView) {
        super(baseView);
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
}
