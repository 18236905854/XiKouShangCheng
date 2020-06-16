package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.SettlementMxBean;


/**
 *
 * Description 待结算view接口
 */
public interface SettlementMXViewImpl extends BaseViewListener {

    //获取列表
    void onGetListDataSuc(BaseModel<SettlementMxBean> model);

}
