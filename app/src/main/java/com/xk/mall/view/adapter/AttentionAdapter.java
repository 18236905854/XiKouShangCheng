package com.xk.mall.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.model.entity.AttentionBean;
import com.xk.mall.model.entity.DesignerBean;
import com.xk.mall.utils.GlideUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * ClassName AttentionAdapter
 * Description 我的关注页面adapter
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class AttentionAdapter extends CommonAdapter<DesignerBean.ResultBean> {
    private RvButtonListener rvButtonListener;

    public void setRvButtonListener(RvButtonListener rvButtonListener) {
        this.rvButtonListener = rvButtonListener;
    }

    public AttentionAdapter(Context context, int layoutId, List<DesignerBean.ResultBean> datas) {
        super(context, layoutId, datas);
    }



    @Override
    protected void convert(ViewHolder holder, DesignerBean.ResultBean attentionBean, int position) {
        holder.setText(R.id.tv_attention_item_name, attentionBean.getPageName());
        holder.setText(R.id.tv_attention_item_time, attentionBean.getCreateTimeDifference());
        ImageView ivHead = holder.getView(R.id.iv_attention_item_icon);
        GlideUtil.show(mContext, attentionBean.getHeadUrl(), ivHead);
        //取消关注
        holder.getView(R.id.tv_attention_item_cancel).setOnClickListener(v -> {
            rvButtonListener.onItemClick(v,position);

        });
    }

}
