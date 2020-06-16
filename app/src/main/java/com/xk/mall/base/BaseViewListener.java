package com.xk.mall.base;

import com.xk.mall.model.entity.LoginBean;

/**
 * File descripition:   基本回调 可自定义添加所需回调
 */

public interface BaseViewListener {
    /**
     * 显示dialog
     */
    void showLoading();
    /**
     * 隐藏 dialog
     */

    void hideLoading();
    /**
     * 显示错误信息
     *
     * @param msg
     */
    void showError(String msg);
    /**
     * 错误码
     */
    void onErrorCode(BaseModel model);
    /***
     * 放在基类里面处理刷新token成功
     */
    void onRefreshTokenSuccess(BaseModel<LoginBean> loginBean);
}
