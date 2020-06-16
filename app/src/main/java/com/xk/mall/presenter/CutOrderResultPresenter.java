package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CutRedBean;
import com.xk.mall.model.impl.CutOrderResultViewImpl;

import java.util.List;

/**
 * @ClassName: CutOrderResultPresenter
 * @Description: 砍价支付完成之后的请求Presenter
 * @Author: 卿凯
 * @Version: ${VERSION}
 */
public class CutOrderResultPresenter extends BasePresenter<CutOrderResultViewImpl> {
    public CutOrderResultPresenter(CutOrderResultViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取砍价红包列表
     * @param id 砍价进度ID
     */
    public void getCutRedList(String id){
        addDisposable(apiServer.getCutRedList(id), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetCutRedSuccess((BaseModel<List<CutRedBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
