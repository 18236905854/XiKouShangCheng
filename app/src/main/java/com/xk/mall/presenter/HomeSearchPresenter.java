package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.HomeSearchGoodsPageBean;
import com.xk.mall.model.impl.HomeSearchViewImpl;

/**
 * ClassName HomeSearchPresenter
 * Description 首页搜索请求presenter
 * Author 卿凯
 * Date 2019/8/2/002
 * Version V1.0
 */
public class HomeSearchPresenter extends BasePresenter<HomeSearchViewImpl> {
    public HomeSearchPresenter(HomeSearchViewImpl baseView) {
        super(baseView);
    }

    /**
     * 首页搜索
     */
    public void homeSearch(String commodityName, int salesNumFlag, int priceFlag, int newFlag, int page, int limit){
        addDisposable(apiServer.homeSearch(commodityName, salesNumFlag, priceFlag, newFlag, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetSearchResult((BaseModel<HomeSearchGoodsPageBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
