package com.xk.mall.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.FightGroupBean;
import com.xk.mall.model.entity.ZeroGoodsBean;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * 0 元拍 正在热拍 adapter
 */
public class ZeroHotGridAdapter extends RecyclerView.Adapter<ZeroHotGridAdapter.MyHolder> {
    private static final String TAG = "ZeroHotGridAdapter";
    private Context mContext;
    private List<ZeroGoodsBean> listData;//商品
    private RvListener rvListener;

    public void setRvListener(RvListener rvListener) {
        this.rvListener = rvListener;
    }

    public ZeroHotGridAdapter(Context context, List<ZeroGoodsBean> listData) {
        this.mContext = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.zero_hot_grid_item, null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ZeroGoodsBean zeroGoodsBean = listData.get(position);
        GlideUtil.show(mContext,zeroGoodsBean.getGoodsImageUrl(),holder.ivGoodsUrl);
        holder.tvGoodsName.setText(zeroGoodsBean.getCommodityName());
//        holder.tvPrice.setText(PriceUtil.dividePrice(zeroGoodsBean.getSalePrice()));

//        holder.refreshTime(zeroGoodsBean.getCloseTime() - System.currentTimeMillis());
        int status=zeroGoodsBean.getStatus();
        Log.e(TAG, "onBindViewHolder: "+status );
        if(status==1){//1:未开始，2:已开始，3:已结束，4:已取消, 5:已流拍
            holder.countdownView.setVisibility(View.GONE);
            holder.tvState.setVisibility(View.VISIBLE);
            holder.tv_end.setVisibility(View.GONE);
            holder.tvState.setText("未开始");
            holder.btn_zero_hot.setText("未开始");
            holder.btn_zero_hot.setEnabled(false);
            holder.btn_zero_hot.setBackgroundResource(R.drawable.bg_zero_hot_disable);
        }else if(status == 3){
            holder.countdownView.setVisibility(View.GONE);
            holder.tvState.setVisibility(View.VISIBLE);
            holder.tvState.setText("已结束");
            holder.tv_end.setVisibility(View.GONE);
            holder.btn_zero_hot.setText("已结束");
            holder.btn_zero_hot.setEnabled(false);
            holder.btn_zero_hot.setBackgroundResource(R.drawable.bg_zero_hot_disable);
        }else if(status==2){
            holder.countdownView.setVisibility(View.VISIBLE);
            holder.tvState.setVisibility(View.GONE);
            holder.tv_end.setVisibility(View.VISIBLE);
            holder.btn_zero_hot.setText("抢拍");
            holder.btn_zero_hot.setEnabled(true);
            holder.btn_zero_hot.setBackgroundResource(R.drawable.bg_zero_hot_enable);
        }else if(status == 5){
            holder.countdownView.setVisibility(View.GONE);
            holder.tvState.setVisibility(View.VISIBLE);
            holder.tvState.setText("已流拍");
            holder.tv_end.setVisibility(View.GONE);
            holder.btn_zero_hot.setText("已流拍");
            holder.btn_zero_hot.setEnabled(false);
            holder.btn_zero_hot.setBackgroundResource(R.drawable.bg_zero_hot_disable);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvListener.onItemClick(1,position);
            }
        });
        holder.refreshTime(zeroGoodsBean.getCloseTime() *1000);
        if(zeroGoodsBean.getStock() <= 0){
            holder.iv_goods_empty.setVisibility(View.VISIBLE);
        }else {
            holder.iv_goods_empty.setVisibility(View.GONE);
        }
    }

    /**
     * 以下两个接口代替 activity.onStart() 和 activity.onStop(), 控制 timer 的开关
     */
    @Override
    public void onViewAttachedToWindow(MyHolder holder) {
        int pos = holder.getAdapterPosition();

        ZeroGoodsBean itemInfo = listData.get(pos);

//        holder.refreshTime(itemInfo.getCloseTime() - System.currentTimeMillis());
        holder.refreshTime(itemInfo.getCloseTime()*1000);
    }

    @Override
    public void onViewDetachedFromWindow(MyHolder holder) {

        holder.getCvCountdownView().stop();
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{
         ImageView ivGoodsUrl, iv_goods_empty;
         TextView tvGoodsName;
         CountdownView countdownView;
         TextView tvPrice;
          TextView btn_zero_hot;
          TextView tvState;
          TextView tv_end;//距离结束 文字


        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ivGoodsUrl = itemView.findViewById(R.id.iv_zero_grid_logo);
            iv_goods_empty = itemView.findViewById(R.id.iv_goods_empty);
            tvGoodsName = itemView.findViewById(R.id.tv_zero_grid_name);
            tvState = itemView.findViewById(R.id.tv_state);
            countdownView = itemView.findViewById(R.id.count_zero_hot_grid);
            tvPrice = itemView.findViewById(R.id.tv_zero_hot_price);
            btn_zero_hot = itemView.findViewById(R.id.btn_zero_hot);
            tv_end = itemView.findViewById(R.id.tv_end);
        }



        public CountdownView getCvCountdownView() {
            return countdownView;
        }

        public void refreshTime(long leftTime) {
            if (leftTime > 0) {
                countdownView.start(leftTime);
            } else {
                countdownView.stop();
                countdownView.allShowZero();
            }
        }
    }
}
