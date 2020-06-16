package com.xk.mall.view.adapter;

import android.content.Context;

import com.xk.mall.model.entity.GlobalBuyerServerBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @ClassName: GlobalBuyNewAdapter
 * @Description: 新版全球买手主页adapter
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class GlobalBuyNewAdapter extends CommonAdapter<GlobalBuyerServerBean> {

    public GlobalBuyNewAdapter(Context context, int layoutId, List<GlobalBuyerServerBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, GlobalBuyerServerBean globalBuyerServerBean, int position) {

    }
}
