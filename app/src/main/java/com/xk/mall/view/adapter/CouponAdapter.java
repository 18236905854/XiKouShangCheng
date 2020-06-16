package com.xk.mall.view.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.TimeUtils;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.model.entity.CouponBean;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * ClassName CouponAdapter
 * Description 优惠券Adapter
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class CouponAdapter extends CommonAdapter<CouponBean> {
    private int type = 0;//类型  1:已使用  2:已失效
    private RvButtonListener rvButtonListener;

    public CouponAdapter(Context context, int layoutId, List<CouponBean> datas) {
        super(context, layoutId, datas);
    }

    public void setRvButtonListener(RvButtonListener rvButtonListener) {
        this.rvButtonListener = rvButtonListener;
    }

    /**
     * 设置类型，判断是已使用还是已失效
     */
    public void setType(int type){
        this.type = type;
    }

    @Override
    protected void convert(ViewHolder holder, CouponBean couponBean, int position) {
        holder.setVisible(R.id.tv_coupon_item_detail, false);

        holder.setText(R.id.tv_coupon_item_money, "" + PriceUtil.divideCoupon(couponBean.balance));
        holder.setText(R.id.tv_coupon_item_sum_money, "" + PriceUtil.divideCoupon(couponBean.total));
        String startTime = TimeUtils.date2String( TimeUtils.string2Date(couponBean.startTime), new SimpleDateFormat("yyyy/MM/dd"));
        String endTime = TimeUtils.date2String(TimeUtils.string2Date(couponBean.endTime), new SimpleDateFormat("yyyy/MM/dd"));
        holder.setText(R.id.tv_coupon_item_time, startTime + " -- " + endTime);
        if(!XiKouUtils.isNullOrEmpty(couponBean.transferFromUser)){
            holder.setText(R.id.tv_coupon_transfer, "来源: " + couponBean.transferFromUser + " 转赠");
        }else {
            holder.setVisible(R.id.tv_coupon_transfer, false);
        }
        RelativeLayout rlCouponBg = holder.getView(R.id.rl_coupon_bg);
        if (type == 0){
            if(couponBean.useable == 1){
                holder.setText(R.id.tv_coupon_item_detail, "转赠");
                holder.setVisible(R.id.iv_coupon_active, false);
                holder.setVisible(R.id.tv_coupon_item_detail, true);
            }else {
                holder.setVisible(R.id.iv_coupon_active, true);
                holder.setVisible(R.id.tv_coupon_item_detail, false);
            }
        }else if(type == 1){
            rlCouponBg.setBackgroundResource(R.drawable.bg_lose_coupon);
            holder.setText(R.id.tv_coupon_item_detail, "已使用");
        }else {
            rlCouponBg.setBackgroundResource(R.drawable.bg_fail_coupon);
            holder.setText(R.id.tv_coupon_item_detail, "已失效");
        }
        if(couponBean.couponType == 1){//通用券
            holder.setText(R.id.tv_coupon_rang, "适用范围:全球买手、0元抢、O2O");
        }

        holder.setOnClickListener(R.id.tv_coupon_item_detail, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击跳转转赠按钮
                if(rvButtonListener != null){
                    rvButtonListener.onItemClick(v, position);
                }
            }
        });
    }
}
