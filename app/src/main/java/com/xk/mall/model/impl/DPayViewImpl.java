package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.DPayResultPageBean;

/**
 * @ClassName: DPayViewImpl
 * @Description: 代付页面接口
 * @Author: 卿凯
 * @Date: 2019/8/30/030 19:03
 * @Version: 1.0
 */
public interface DPayViewImpl extends BaseViewListener {
    //请求成功
    void onGetDataSuccess(BaseModel<DPayResultPageBean> model);
}
