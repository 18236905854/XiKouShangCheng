package com.xk.mall.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.CutShangJiaBean;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * ClassName CutNewAdapter
 * Description 喜立得最新上架adapter
 * Author 卿凯
 * Date 2019/6/24/024
 * Version V1.0
 */
public class CutNewAdapter extends CommonAdapter<ActiveSectionGoodsBean> {
    public CutNewAdapter(Context context, int layoutId, List<ActiveSectionGoodsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ActiveSectionGoodsBean cutRecommendBean, int position) {
        ImageView ivLogo = holder.getView(R.id.iv_cut_new_logo);
        TextView  tvDiscount=holder.getView(R.id.tv_cut_new_discount);

        GlideUtil.show(mContext,cutRecommendBean.getGoodsImageUrl(),ivLogo);
        if(cutRecommendBean.getDiscount() == 0){
            tvDiscount.setVisibility(View.GONE);
        }else {
            tvDiscount.setVisibility(View.VISIBLE);
            tvDiscount.setText(cutRecommendBean.getDiscount() /10 + "折起");
        }

        int width=mContext.getResources().getDisplayMetrics().widthPixels;
        int result=width - DensityUtils.dp2px(mContext,23);
        //评分3等分
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (result / 3f),
//                ViewGroup.LayoutParams.MATCH_PARENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (result / 3f),
                (int) (result / 3f));
        holder.itemView.setLayoutParams(params);
        holder.itemView.setPadding(0,0,DensityUtils.dp2px(mContext,7),0);
    }
}
