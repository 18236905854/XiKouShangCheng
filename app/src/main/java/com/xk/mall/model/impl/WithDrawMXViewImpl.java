package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.RedBagMxBean;
import com.xk.mall.model.entity.WithDrawCountBean;
import com.xk.mall.model.entity.WithDrawMxBean;


/**
 *
 * Description 提现明细view接口
 */
public interface WithDrawMXViewImpl extends BaseViewListener {

    //获取列表
    void onGetListDataSuc(BaseModel<WithDrawMxBean> model);
    //获取月统计数据成功
    void onGetMonthCountSuccess(BaseModel<WithDrawCountBean> model);

}
