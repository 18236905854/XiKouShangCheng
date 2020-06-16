package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.model.entity.ActivityBean;
import com.xk.mall.model.entity.GlobalBuyerBean;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * ClassName ActivityGlobalBuyerAdapter
 * Description 活动首页全球买手adapter
 * Author 卿凯
 * Date 2019/6/22/022
 * Version V1.0
 */
public class ActivityGlobalBuyerAdapter extends CommonAdapter<ActivityBean.GlobalBuyerHomeActivityVoBean.GlobalBuyerCommodityListBean> {
    public ActivityGlobalBuyerAdapter(Context context, int layoutId, List<ActivityBean.GlobalBuyerHomeActivityVoBean.GlobalBuyerCommodityListBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public int getItemCount() {
        return getDatas().size() > 4 ? 4 : getDatas().size();
    }

    @Override
    protected void convert(ViewHolder holder, ActivityBean.GlobalBuyerHomeActivityVoBean.GlobalBuyerCommodityListBean globalBuyerBean, int position) {
        ImageView ivLogo = holder.getView(R.id.iv_global_logo);
        GlideUtil.showRadius(mContext, globalBuyerBean.getGoodsImageUrl(), 2, ivLogo);
        holder.setText(R.id.tv_global_name, globalBuyerBean.getCommodityName());
        holder.setText(R.id.tv_global_now_price, "" + PriceUtil.dividePrice(globalBuyerBean.getCommodityPrice()));
        TextView tvLinePrice = holder.getView(R.id.tv_global_real_price);
        tvLinePrice.setText(mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(globalBuyerBean.getSalePrice()));
        tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.setText(R.id.tv_global_coupon, "" + PriceUtil.dividePrice(globalBuyerBean.getCouponValue()));
    }
}
