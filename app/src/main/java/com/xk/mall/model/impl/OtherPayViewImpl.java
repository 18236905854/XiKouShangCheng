package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.OtherPayResultBean;

/**
 * @ClassName: OtherPayViewImpl
 * @Description: 代付页面接口实现类
 * @Author: 卿凯
 * @Date: 2019/8/30/030 16:39
 * @Version: 1.0
 */
public interface OtherPayViewImpl extends BaseViewListener {

    //请求成功
    void onOtherPaySuccess(BaseModel<OtherPayResultBean> beanBaseModel);
}
