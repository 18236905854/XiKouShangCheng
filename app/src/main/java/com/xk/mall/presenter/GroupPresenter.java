package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.impl.FightGroupViewImpl;

/**
 * ClassName GroupPresenter
 * Description 定制拼团页面请求Presenter
 * Author 卿凯
 * Date 2019/7/9/009
 * Version V1.0
 */
public class GroupPresenter extends BasePresenter<FightGroupViewImpl> {

    public GroupPresenter(FightGroupViewImpl baseView) {
        super(baseView);
    }

    public void getFightGroupData(String categoryId,int activityType,String userId,int page, int limit){
        addDisposable(apiServer.getActiveSectionGoods(categoryId, activityType,userId,page,limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetFightGroupDataSuccess((BaseModel<ActiveSectionGoodsPageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
