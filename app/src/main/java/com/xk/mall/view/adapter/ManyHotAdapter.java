package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.xk.mall.R;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.ManyBean;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * ClassName ManyHotAdapter
 * Description 多买多折今日爆款的adapter
 * Author 卿凯
 * Date 2019/6/24/024
 * Version V1.0
 */
public class ManyHotAdapter extends CommonAdapter<ActiveSectionGoodsBean> {
    public ManyHotAdapter(Context context, int layoutId, List<ActiveSectionGoodsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ActiveSectionGoodsBean manyBean, int position) {
        holder.setText(R.id.tv_many_buy_name, manyBean.getCommodityName());
        holder.setText(R.id.tv_many_buy_now_price, PriceUtil.dividePrice(manyBean.getCommodityPriceOne()));
        holder.setText(R.id.tv_many_buy_real_price, mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(manyBean.getSalePrice()));
        TextView tvLinePrice = holder.getView(R.id.tv_many_buy_real_price);
        tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//        float minRate = SPUtils.getInstance().getFloat(Constant.RATE_THREE);
//        if(minRate <= 0){
//            holder.setText(R.id.tv_many_buy_min_discount, "最低3折");
//        }else {
            holder.setText(R.id.tv_many_buy_min_discount, "封顶" + PriceUtil.changeDoubleToStr(manyBean.getRateOne()) + "折");
//        }
        ImageView ivLogo = holder.getView(R.id.iv_many_buy_logo);
        GlideUtil.show(mContext, manyBean.getGoodsImageUrl(), ivLogo);
    }

    @Override
    public int getItemCount() {
        if(getDatas().size() > 3){
            return 3;
        }
        return super.getItemCount();
    }

    /**
     * 将float转为String
     */
    private String changeFloatToString(float discount){
        double result = Float.valueOf(discount).doubleValue();
        String discountStr;
        if(result % 1.0 == 0){
            discountStr = String.valueOf((long)result);
        }else {
            discountStr = String.valueOf(result);
        }
        return  discountStr + "折";
    }
}
