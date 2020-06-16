package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.WithDrawDetailBean;

/**
 * @ClassName: WithDrawDetailViewImpl
 * @Description: 提现明细详情页面接口
 * @Author: 卿凯
 * @Date: 2019/9/24/024 20:59
 * @Version: 1.0
 */
public interface WithDrawDetailViewImpl extends BaseViewListener {
    //获取数据成功
    void onGetDetailSuccess(BaseModel<WithDrawDetailBean> model);
}
