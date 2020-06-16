package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.TransferResultBean;

/**
 * @ClassName: TransferNextViewImpl
 * @Description: 转账页面view接口
 * @Author: 卿凯
 * @Date: 2019/10/20/020 18:32
 * @Version: 1.0
 */
public interface TransferNextViewImpl extends BaseViewListener {
    //转账成功
    void onTransferSuccess(BaseModel<TransferResultBean> model);

}
