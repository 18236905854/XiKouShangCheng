package com.xk.mall.view.adapter;

import android.content.Context;

import com.xk.mall.R;
import com.xk.mall.model.entity.UserItem;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * @ClassName MeAdapter
 * @Description 我的页面中的adapter
 * @Author 卿凯
 * @Date 2019/6/4/004
 * @Version V1.0
 */
public class MeAdapter extends CommonAdapter<UserItem> {
    public MeAdapter(Context context, int layoutId, List<UserItem> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, UserItem userItem, int position) {
        holder.setText(R.id.tv_user_vip_item_name, userItem.title);
        holder.setImageResource(R.id.iv_user_vip_item_icon, userItem.resourceId);
    }
}
