package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;

/**
 * @ClassName: EnterLogisticsViewImpl
 * @Description: 填写物流页面接口
 * @Author: 卿凯
 * @Date: 2019/12/10/010 14:29
 * @Version: 1.0
 */
public interface EnterLogisticsViewImpl extends BaseViewListener {
    //提交成功
    void onPostSuccess(BaseModel baseModel);
}
