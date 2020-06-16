package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.impl.WuGViewImpl;
import com.xk.mall.utils.XiKouUtils;

/**
 * ClassName WuGPresenter
 * Description 吾G请求Presenter
 * Author 卿凯
 * Date 2019/7/12/012
 * Version V1.0
 */
public class WuGPresenter extends BasePresenter<WuGViewImpl> {

    public WuGPresenter(WuGViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据版块ID获取该版块下的商品列表
     * @param categoryId 版块ID
     * @param activityType 活动类型
     * @param page 页数
     * @param limit 每页的条数
     */
    public void getActiveSectionGoods(boolean isScheduleOpen, String scheduleId, String categoryId, int activityType,String userId, int page, int limit){
        addDisposable(apiServer.getWuGActiveSectionGoods(scheduleId, categoryId, activityType,userId, page, limit), new BaseObserver(baseView) {
                @Override
                public void onSuccess(Object o) {
                    baseView.onGetActiveSectionGoodsSuccess((BaseModel<ActiveSectionGoodsPageBean>) o);
                }

                @Override
                public void onError(String msg) {

                }
            });
    }

//    /**
//     * 获取吾G列表
//     */
//    public void getWuGData(int page, int limit){
//        addDisposable(apiServer.getWuGData(page, limit), new BaseObserver(baseView) {
//            @Override
//            public void onSuccess(Object o) {
//                baseView.onGetDataSuccess((BaseModel<WuGPageBean>) o);
//            }
//
//            @Override
//            public void onError(String msg) {
//
//            }
//        });
//    }
}
