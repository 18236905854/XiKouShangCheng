package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ResultPageBean;
import com.xk.mall.model.entity.ShareCheckBean;

/**
 * @ClassName: MaterialShareCheckImpl
 * @Description: 分享审核页面接口
 * @Author: 卿凯
 * @Date: 2019/12/8/008 14:42
 * @Version: 1.0
 */
public interface MaterialShareCheckImpl extends BaseViewListener {
    //获取审核数据成功
    void onGetDataSuccess(BaseModel<ResultPageBean<ShareCheckBean>> model);
}
