package com.xk.mall.view.adapter;

import android.content.Context;

import com.xk.mall.model.entity.NearBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * ClassName NearAdapter
 * Description 附近模块的adapter
 * Author 卿凯
 * Date 2019/6/18/018
 * Version V1.0
 */
public class NearAdapter extends CommonAdapter<NearBean> {
    public NearAdapter(Context context, int layoutId, List<NearBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, NearBean nearBean, int position) {

    }
}
