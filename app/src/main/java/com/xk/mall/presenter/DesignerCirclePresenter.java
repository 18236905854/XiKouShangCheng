package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.DesignerBean;
import com.xk.mall.model.entity.DesignerCirclBean;
import com.xk.mall.model.impl.AttentionViewImpl;
import com.xk.mall.model.impl.DesignerCircleViewImpl;
import com.xk.mall.model.impl.DesignerViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;

/**
 *
 *  设计师圈P层 接口
 *
 */
public class DesignerCirclePresenter extends BasePresenter<DesignerCircleViewImpl> {
    public DesignerCirclePresenter(DesignerCircleViewImpl baseView) {
        super(baseView);
    }

    /**
     * 调用接口获取数据
     *
     */
    public void getDesignerCircleData(int page, int limit){
        addDisposable(apiServer.getDesignerCircleData(page,limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetDegisnerCircelSuc((BaseModel<DesignerCirclBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

}
