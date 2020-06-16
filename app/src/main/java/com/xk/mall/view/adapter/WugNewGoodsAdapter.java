package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.WuGGoodsBean;
import com.xk.mall.utils.PriceUtil;

import java.util.List;

/**
 * 吾G购  adapter //添加foot 我是有底线的
 */
public class WugNewGoodsAdapter extends  RvAdapter<WuGGoodsBean>{
    private Context mContext;
    private int footCount=1;//尾部个数
    private static final int FOOT_TYPE=6;

    private boolean isFoot(int position) {
        return footCount!=0&&(position>=getBodySize());
    }

    private int getBodySize() {
        return list.size();
    }

    public WugNewGoodsAdapter(Context context, List<WuGGoodsBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return list.size()+footCount;
    }

    @Override
    protected int getLayoutId(int viewType) {
        if(viewType==FOOT_TYPE){
            return  R.layout.rl_bottom_xian;
        }else{
            return R.layout.item_wug_goods;
        }

    }

    //布局类型
    @Override
    public int getItemViewType(int position) {
        if(isFoot(position)){
            return FOOT_TYPE;
        }else{
            return 0;
        }
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new GoodsOneHolder(view,viewType,listener);
    }

    private class GoodsOneHolder extends RvHolder<WuGGoodsBean>{
        private ImageView iv_goods, iv_goods_empty;
        private TextView tv_coupom, tv_goods_name,tv_price,tv_line_price,tv_grab_buy;

        public GoodsOneHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            iv_goods=itemView.findViewById(R.id.iv_goods);
            iv_goods_empty = itemView.findViewById(R.id.iv_goods_empty);
            tv_coupom=itemView.findViewById(R.id.tv_coupom);
            tv_goods_name=itemView.findViewById(R.id.tv_goods_name);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_line_price=itemView.findViewById(R.id.tv_line_price);
            tv_grab_buy=itemView.findViewById(R.id.tv_grab_buy);

        }

        @Override
        public void bindHolder(WuGGoodsBean bean, int position) {
            Glide.with(mContext).load(bean.getGoodsImageUrl()).into(iv_goods);
            tv_goods_name.setText(bean.getCommodityName());
            tv_price.setText(String.valueOf(PriceUtil.dividePrice(bean.getSalePrice())));
            tv_line_price.setText(mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(bean.getMarketPrice()));
            //中划线
            tv_line_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_coupom.setText("赠券"+ PriceUtil.dividePrice(bean.getCouponValue()));
            if(bean.getStock() <= 0){
                tv_grab_buy.setText("已售罄");
                tv_grab_buy.setEnabled(false);
                iv_goods_empty.setVisibility(View.VISIBLE);
            }else {
                tv_grab_buy.setText("立即抢购");
                tv_grab_buy.setEnabled(true);
                iv_goods_empty.setVisibility(View.GONE);
            }
        }
    }


}
