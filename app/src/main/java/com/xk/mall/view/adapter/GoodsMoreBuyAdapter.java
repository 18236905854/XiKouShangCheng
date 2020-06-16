package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.HomeDataBean;

import java.util.List;

/**
 * 多买多得 商品 adapter
 */
public class GoodsMoreBuyAdapter extends  RvAdapter<HomeDataBean.HomeGoodsBean>{

    private Context mContext;
    public GoodsMoreBuyAdapter(Context context, List<HomeDataBean.HomeGoodsBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_home_twochild;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new GoodsOneHolder(view,viewType,listener);
    }

    private class GoodsOneHolder extends RvHolder<HomeDataBean.HomeGoodsBean>{
        private ImageView img_goods;
        private TextView  tv_vip_discout;
        private TextView  tv_goods_name,tv_hot_sale_num,tv_price,tv_line_price;

        public GoodsOneHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            img_goods=itemView.findViewById(R.id.img_goods);
            tv_vip_discout=itemView.findViewById(R.id.tv_vip_discout);
            tv_goods_name=itemView.findViewById(R.id.tv_goods_name);
            tv_hot_sale_num=itemView.findViewById(R.id.tv_hot_sale_num);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_line_price=itemView.findViewById(R.id.tv_line_price);

        }

        @Override
        public void bindHolder(HomeDataBean.HomeGoodsBean bean, int position) {
            Glide.with(mContext).load(bean.getImage()).into(img_goods);
            tv_vip_discout.setText("会员"+bean.getVipDiscount()+"折");
            tv_goods_name.setText(bean.getName());
            tv_hot_sale_num.setText("热销"+bean.getHotSellNum()+"件");
            tv_price.setText(bean.getPrice());
            tv_line_price.setText("¥"+bean.getLinePrice());
            //中划线
            tv_line_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        }
    }


}
