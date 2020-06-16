package com.xk.mall.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.model.entity.FightGroupBean;

import java.util.List;

import cn.iwgang.countdownview.CountdownView;

/**
 * ClassName FightGroup2Adapter
 * Description TODO
 * Author Kay
 * Date 2019/7/12/012
 * Version V1.0
 */
public class FightGroup2Adapter extends RecyclerView.Adapter<FightGroup2Adapter.MyHolder> {

    private Context context;
    private List<FightGroupBean> listData;

    public FightGroup2Adapter(Context context, List<FightGroupBean> listData) {
        this.context = context;
        this.listData = listData;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.fight_group_item, null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int i) {
        FightGroupBean fightGroupBean = listData.get(i);
        holder.bindData(fightGroupBean);
    }

    /**
     * 以下两个接口代替 activity.onStart() 和 activity.onStop(), 控制 timer 的开关
     */
    @Override
    public void onViewAttachedToWindow(MyHolder holder) {
        int pos = holder.getAdapterPosition();

        FightGroupBean itemInfo = listData.get(pos);

        holder.refreshTime(itemInfo.getEndTime() - System.currentTimeMillis());
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

        private TextView tvName;
        private TextView tvRealPrice;
        private TextView tvLinePrice;
        private TextView tvLast;
        private TextView tvAuthen;
        private CountdownView countdownView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_fight_group_name);
            tvRealPrice = itemView.findViewById(R.id.tv_commodity_price);
            tvLinePrice = itemView.findViewById(R.id.tv_fight_group_price);
            tvLast = itemView.findViewById(R.id.tv_fight_group_last);
            countdownView = itemView.findViewById(R.id.count_down_fight_group);
        }

        public void bindData(FightGroupBean itemInfo) {
            tvName.setText(itemInfo.getCommodityName());
            tvLinePrice.setText("" + itemInfo.getCommodityPrice());
            refreshTime(itemInfo.getEndTime() - System.currentTimeMillis());
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
