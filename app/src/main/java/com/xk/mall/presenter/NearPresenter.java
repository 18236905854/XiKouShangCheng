package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.IndustryBean;
import com.xk.mall.model.impl.NearViewImpl;

import java.util.List;

/**
 * ClassName NearPresenter
 * Description 附近页面获取
 * Author 卿凯
 * Date 2019/7/19/019
 * Version V1.0
 */
public class NearPresenter extends BasePresenter<NearViewImpl> {
    public NearPresenter(NearViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取附近所有行业
     */
    public void getIndustry(){
        addDisposable(apiServer.getNearIndutry(), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetIndustrySuccess((BaseModel<List<IndustryBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
