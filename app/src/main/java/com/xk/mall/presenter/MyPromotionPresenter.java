package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.AliPayResultBean;
import com.xk.mall.model.entity.PromotionShareBean;
import com.xk.mall.model.entity.UserServerBean;
import com.xk.mall.model.impl.AliPayViewImpl;
import com.xk.mall.model.impl.MyPromotionViewImpl;
import com.xk.mall.model.request.AliPayRequestBody;

/**
 *
 *  我的推广 Presenter
 *  卿凯
 *
 *
 */
public class MyPromotionPresenter extends BasePresenter<MyPromotionViewImpl> {
    public MyPromotionPresenter(MyPromotionViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取推广 数据
     */
    public void getPromotionData(){
        addDisposable(apiServer.getPromotionShare(), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetPromotionSuc((BaseModel<PromotionShareBean>) o);
            }
            @Override
            public void onError(String msg) {

            }
        });
    }

}
