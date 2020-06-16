package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.orhanobut.logger.Logger;
import com.xk.mall.R;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.FightGroupBean;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * ClassName FightGroupAdapter
 * Description 定制拼团的adapter
 * Author 卿凯
 * Date 2019/6/20/020
 * Version V1.0
 */
public class FightGroupAdapter extends CommonAdapter<ActiveSectionGoodsBean> {

    public FightGroupAdapter(Context context, int layoutId, List<ActiveSectionGoodsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ActiveSectionGoodsBean fightGroupBean, int position) {
        holder.setText(R.id.tv_fight_group_name, fightGroupBean.getCommodityName());
        holder.setText(R.id.tv_commodity_price, "" + PriceUtil.dividePrice(fightGroupBean.getCommodityPrice()));
        TextView tvGroupPrice = holder.getView(R.id.tv_fight_group_price);
        tvGroupPrice.setText(mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(fightGroupBean.getSalePrice()));
        tvGroupPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        int lastNum = fightGroupBean.getTargetNumber() - fightGroupBean.getCurrentFightGroupNum();
        holder.setText(R.id.tv_fight_group_last, "仅剩" + lastNum + "件");
        CountdownView countdownView = holder.getView(R.id.count_down_fight_group);
//        countdownView.start(fightGroupBean.getEndTime());
//        refreshTime(countdownView, fightGroupBean.getEndTime() - System.currentTimeMillis());
        ProgressBar pbFightGroup = holder.getView(R.id.pb_fight_group);
        int progress = 0;
        if(fightGroupBean.getTargetNumber() != 0){
            progress = (fightGroupBean.getTargetNumber() - fightGroupBean.getCurrentFightGroupNum()) * 100 / fightGroupBean.getTargetNumber();
        }
        pbFightGroup.setProgress(progress);
        ImageView ivLogo = holder.getView(R.id.iv_fight_group_logo);
        GlideUtil.showRadius(mContext, fightGroupBean.getGoodsImageUrl(), 2, ivLogo);
        TextView tvAuthen = holder.getView(R.id.tv_fight_group_authen);
//        if(fightGroupBean.getAuthen() == 1){
//            tvAuthen.setVisibility(View.VISIBLE);
//        }else {
//            tvAuthen.setVisibility(View.GONE);
//        }
        Button button = holder.getView(R.id.btn_fight_group);
//        if(fightGroupBean.getEndTime() == 0){
//            button.setText("拼团结束");
//            button.setBackgroundResource(R.drawable.bg_btn_fight_group_disbale);
//        }else {
            button.setText("马上抢");
            button.setBackgroundResource(R.drawable.bg_btn_fight_group);
//        }
    }

    public void refreshTime(CountdownView countdownView, long leftTime) {
        if (leftTime > 0) {
            countdownView.start(leftTime);
        } else {
            countdownView.stop();
            countdownView.allShowZero();
        }
    }
}
