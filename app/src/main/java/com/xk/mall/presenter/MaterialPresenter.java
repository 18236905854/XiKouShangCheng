package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.MaterialBean;
import com.xk.mall.model.entity.ResultPageBean;
import com.xk.mall.model.impl.MaterialViewImpl;

/**
 * @ClassName: MaterialPresenter
 * @Description: 喜扣素材页面请求类
 * @Author: 卿凯
 * @Date: 2019/12/9/009 16:31
 * @Version: 1.0
 */
public class MaterialPresenter extends BasePresenter<MaterialViewImpl> {

    public MaterialPresenter(MaterialViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取素材接口
     */
    public void getMaterial(int page, int limit){
        addDisposable(apiServer.getMaterial(page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetMaterialSuccess((BaseModel<ResultPageBean<MaterialBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
