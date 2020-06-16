package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.LogisticsResultBean;

/**
 * @ClassName: LogisticsViewImpl
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/9/10/010 19:44
 * @Version: 1.0
 */
public interface LogisticsViewImpl extends BaseViewListener {
    //获取物流信息接口
    void onGetLogisticsSuccess(BaseModel<LogisticsResultBean> beanBaseModel);
}
