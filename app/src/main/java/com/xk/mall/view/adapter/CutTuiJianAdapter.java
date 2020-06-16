package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.CutServerBean;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.TimeTools;

import java.util.List;

/**
 * 喜立得 推荐商品适配
 */
public class CutTuiJianAdapter extends RecyclerView.Adapter<CutTuiJianAdapter.MyViewHolder> {
    private static final String TAG = "GrabGoodsAdapter";
    private Context mContext;
    private List<ActiveSectionGoodsBean> listData;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public CutTuiJianAdapter(Context context, List<ActiveSectionGoodsBean> listData) {
        this.listData = listData;
        this.mContext = context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cut_recommend_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        ActiveSectionGoodsBean cutRecommendBean = listData.get(position);
        //剩余时间 ---endTime=发起砍价时间+砍价有效时间

        GlideUtil.show(mContext, cutRecommendBean.getGoodsImageUrl(), holder.iv_cut_recommend_logo);
        holder.tv_cut_name.setText(cutRecommendBean.getCommodityName());
        holder.tv_cut_success_man.setText(cutRecommendBean.getBargainedNum() + "人砍价成功");
        holder.tv_zhuli_num.setText(cutRecommendBean.getBargainNumber() + "人助力");
        holder.tv_cut_now_price.setText(PriceUtil.dividePrice(cutRecommendBean.getCommodityPrice()));
        holder.tv_cut_real_price.setText(mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(cutRecommendBean.getSalePrice()));
        holder.tv_cut_real_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if(cutRecommendBean.getStock() <= 0){
            holder.iv_goods_empty.setVisibility(View.VISIBLE);
            holder.btn_cut.setText("已售罄");
            holder.btn_cut.setEnabled(false);
        }else {
            holder.btn_cut.setEnabled(true);
            holder.iv_goods_empty.setVisibility(View.GONE);
        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_cut_recommend_logo, iv_goods_empty;
        TextView tv_cut_real_price, tv_cut_name, tv_cut_success_man, tv_zhuli_num, tv_cut_now_price, btn_cut;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv_cut_recommend_logo = itemView.findViewById(R.id.iv_cut_recommend_logo);
            iv_goods_empty = itemView.findViewById(R.id.iv_goods_empty);

            tv_cut_name = itemView.findViewById(R.id.tv_cut_name);
            btn_cut = itemView.findViewById(R.id.btn_cut);
            tv_cut_success_man = itemView.findViewById(R.id.tv_cut_success_man);
            tv_zhuli_num = itemView.findViewById(R.id.tv_zhuli_num);
            tv_cut_now_price = itemView.findViewById(R.id.tv_cut_now_price);
            tv_cut_real_price = itemView.findViewById(R.id.tv_cut_real_price);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

    }

    public interface OnButtonClickListener {
        void onButtonClick(View view, int positon);
    }

}
