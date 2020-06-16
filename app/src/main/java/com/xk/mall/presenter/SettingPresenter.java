package com.xk.mall.presenter;

import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.impl.SettingViewImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @ClassName: SettingPresenter
 * @Description: 设置页面请求Presenter
 * @Author: 卿凯
 * @Date: 2019/9/18/018 11:28
 * @Version: 1.0
 */
public class SettingPresenter extends BasePresenter<SettingViewImpl>{
    public SettingPresenter(SettingViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据条件查询行政区域
     * @param level 等级
     */
    public void getServerAddress(int level){
        apiServer.queryAreaByIeve(level).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBaseModel -> {
                    if(baseView != null){
                        baseView.getAddressSuccess(listBaseModel);
                    }
                });
    }
}
