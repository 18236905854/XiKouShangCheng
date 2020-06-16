package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.GlobalBuyerChildPageBean;
import com.xk.mall.model.impl.GlobalBuyerChildViewImpl;

/**
 * ClassName GlobalBuyerChildPresenter
 * Description 全球买手分类分页请求Presenter
 * Author 卿凯
 * Date 2019/7/7/007
 * Version V1.0
 */
public class GlobalBuyerChildPresenter extends BasePresenter<GlobalBuyerChildViewImpl> {
    public GlobalBuyerChildPresenter(GlobalBuyerChildViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取全球买手分类页面的数据
     * @param activityId 活动ID
     * @param categoryId 分类ID
     * @param sortType 排序类别 1:价格 2:上新 3:销量
     * @param sortMode 排序方式 1:升序 2:倒序
     * @param page 当前页数
     * @param limit 每页限制的数量
     */
    public void getGloBuyerChildData(String activityId, String categoryId, int sortType, int sortMode, int page, int limit){
        if(sortType == 0 && sortMode == 0){
            addDisposable(apiServer.getGlobalBuyerChildData(activityId, categoryId, page, limit),
                    new BaseObserver(baseView) {
                        @Override
                        public void onSuccess(Object o) {
                            baseView.onGetDataSuccess((BaseModel<GlobalBuyerChildPageBean>) o);
                        }

                        @Override
                        public void onError(String msg) {

                        }
                    });
        }else {
            addDisposable(apiServer.getGlobalBuyerChildData(activityId, categoryId, sortType, sortMode, page, limit),
                    new BaseObserver(baseView) {
                        @Override
                        public void onSuccess(Object o) {
                            baseView.onGetDataSuccess((BaseModel<GlobalBuyerChildPageBean>) o);
                        }

                        @Override
                        public void onError(String msg) {

                        }
                    });
        }
    }
}
