package com.xk.mall.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.model.entity.AttenShopListBean;
import com.xk.mall.model.entity.DesignerBean;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.view.activity.ShopActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 *
 * 我的关注店铺adapter
 */
public class AttentionShopAdapter extends CommonAdapter<AttenShopListBean.ResultBean> {
    private RvButtonListener rvButtonListener;

    public AttentionShopAdapter(Context context, int layoutId, List<AttenShopListBean.ResultBean> datas) {
        super(context, layoutId, datas);
    }

    public void setRvButtonListener(RvButtonListener rvButtonListener) {
        this.rvButtonListener = rvButtonListener;
    }

    @Override
    protected void convert(ViewHolder holder, AttenShopListBean.ResultBean attentionBean, int position) {
        holder.setText(R.id.tv_attention_item_name, attentionBean.getShopName());
        holder.setText(R.id.tv_attention_item_time, attentionBean.getFollowTime());
        ImageView ivHead = holder.getView(R.id.iv_attention_item_icon);
        GlideUtil.show(mContext, attentionBean.getShopImageUrl(), ivHead);
        ivHead.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ShopActivity.class);
            intent.putExtra(ShopActivity.SHOP_ID, attentionBean.getId());
            ActivityUtils.startActivity(intent);
        });
        //取消关注
        holder.getView(R.id.tv_attention_item_cancel).setOnClickListener(v -> {
            rvButtonListener.onItemClick(v,position);
        });
    }

}
