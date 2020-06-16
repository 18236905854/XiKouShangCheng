package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ResultPageBean;
import com.xk.mall.model.entity.ShareCheckBean;
import com.xk.mall.model.impl.MaterialShareCheckImpl;

/**
 * @ClassName: MaterialShareCheckPresenter
 * @Description: 分享审核页面的请求类
 * @Author: 卿凯
 * @Date: 2019/12/8/008 14:40
 * @Version: 1.0
 */
public class MaterialShareCheckPresenter extends BasePresenter<MaterialShareCheckImpl> {

    public MaterialShareCheckPresenter(MaterialShareCheckImpl baseView) {
        super(baseView);
    }

    /**
     *
     * @param phone  用户手机
     * @param userName 用户名字
     * @param state  图片审核状态 none: 待审核(申请中)，pass: 审核通过，failed: 审核不通过
     * @param page 页数
     * @param limit 每页条数
     */
    public void getData(String phone, String userName, String state, int page, int limit){
        addDisposable(apiServer.getShareList(phone, userName, state, page, limit), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetDataSuccess((BaseModel<ResultPageBean<ShareCheckBean>>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
