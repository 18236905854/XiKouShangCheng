package com.xk.mall.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.model.entity.CutBean;
import com.xk.mall.model.entity.CutServerBean;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.activity.CutContinueActivity;
import com.xk.mall.view.activity.CutGoodsDetailActivity;
import com.xk.mall.view.activity.CutSuccessActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * ClassName CutRecommendAdapter
 * Description 喜立得页面为您推荐adapter
 * Author 卿凯
 * Date 2019/6/24/024
 * Version V1.0
 */
public class CutRecommendAdapter extends CommonAdapter<CutServerBean> {
    public CutRecommendAdapter(Context context, int layoutId, List<CutServerBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CutServerBean cutRecommendBean, int position) {
        ImageView ivLogo = holder.getView(R.id.iv_cut_recommend_logo);//图片
        TextView tvRealPrice = holder.getView(R.id.tv_cut_real_price);
        tvRealPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.setText(R.id.tv_cut_name, cutRecommendBean.getCommodityName());
        holder.setText(R.id.tv_cut_success_man, cutRecommendBean.getSalesNumber() + "人砍价成功");
        holder.setText(R.id.tv_cut_man, cutRecommendBean.getBargainNumber() + "人助力");
        holder.setText(R.id.tv_cut_now_price, mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(cutRecommendBean.getSalePrice()));
        holder.setText(R.id.tv_cut_real_price, mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(cutRecommendBean.getMarketPrice()));

        TextView btnCut = holder.getView(R.id.btn_cut);
        GlideUtil.show(mContext, cutRecommendBean.getGoodsImageUrl(), ivLogo);
        btnCut.setText("我要砍价");
//        if(cutRecommendBean.type == 0){
//            btnCut.setText("我要砍价");
//            ivState.setVisibility(View.GONE);
//            rlEndTime.setVisibility(View.GONE);
//        }else if(cutRecommendBean.type == 1){
//            btnCut.setText("砍价成功");
//            ivState.setVisibility(View.VISIBLE);
//            ivState.setImageResource(R.drawable.ic_cut_success);
//            rlEndTime.setVisibility(View.GONE);
//        }else if(cutRecommendBean.type == 2){
//            btnCut.setText("继续砍价");
//            ivState.setVisibility(View.VISIBLE);
//            ivState.setImageResource(R.drawable.ic_cut_cutting);
//            countdownView.setVisibility(View.VISIBLE);
//            rlEndTime.setVisibility(View.VISIBLE);
//        }
    }
}
