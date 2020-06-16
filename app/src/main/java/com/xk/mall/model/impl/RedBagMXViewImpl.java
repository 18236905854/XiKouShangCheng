package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.MonthAccountBean;
import com.xk.mall.model.entity.RealNameInfoBean;
import com.xk.mall.model.entity.RedBagMxBean;
import com.xk.mall.model.entity.RedChooseBean;


/**
 *
 * Description 红包明细view接口
 */
public interface RedBagMXViewImpl extends BaseViewListener {

    //获取列表
    void onGetListDataSuc(BaseModel<RedBagMxBean> model);
    //查询月收支统计
    void onGetMonthAccountSuccess(BaseModel<MonthAccountBean> model);
    //帐户操作类型查询接口
    void onGetAccountTypeSuccess(BaseModel<RedChooseBean> model);

}
