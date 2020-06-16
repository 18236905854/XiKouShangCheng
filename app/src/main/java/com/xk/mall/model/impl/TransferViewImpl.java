package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.TransferInfoBean;

/**
 * @ClassName: TransferViewImpl
 * @Description: 转账页面view接口
 * @Author: 卿凯
 * @Date: 2019/10/20/020 15:34
 * @Version: 1.0
 */
public interface TransferViewImpl extends BaseViewListener {
    //根据手机号码查看转账信息
    void onGetDataByPhone(BaseModel<TransferInfoBean> model);
}
