package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.ZeroAuctionBean;
import com.xk.mall.model.entity.ZeroGoodsDetailBean;

import java.util.List;

/**
 * ClassName ZeroGivePriceRecordImpl
 * Description 0元拍砍价记录view接口
 * Author 卿凯
 * Date 2019/7/6/006
 * Version V1.0
 */
public interface ZeroGivePriceRecordImpl extends BaseViewListener {
    //获取出价记录成功
    void onGetRecordSuccess(BaseModel<List<ZeroAuctionBean.RecordListBean>> listBaseModel);
}
