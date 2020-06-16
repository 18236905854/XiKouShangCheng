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
import com.xk.mall.utils.GlideUtil;

import java.util.List;

/**
 * 吾G限时购 喜立得 商品 adapter
 */
public class GoodsOneAdapter extends  RvAdapter<HomeDataBean.HomeGoodsBean>{

    private Context mContext;
    private boolean isGift;//是否有赠送
    public GoodsOneAdapter(Context context, List<HomeDataBean.HomeGoodsBean> list,boolean isGift, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
        this.isGift=isGift;
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_home_onechild;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new GoodsOneHolder(view,viewType,listener);
    }

    private class GoodsOneHolder extends RvHolder<HomeDataBean.HomeGoodsBean>{
        private ImageView img_goods;
        private TextView  tv_goodsName,tv_price,tv_line_price,tv_gift_coupon;

        public GoodsOneHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            img_goods=itemView.findViewById(R.id.img_goods);
            tv_goodsName=itemView.findViewById(R.id.tv_goodsName);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_line_price=itemView.findViewById(R.id.tv_line_price);
            tv_gift_coupon=itemView.findViewById(R.id.tv_gift_coupon);

        }

        @Override
        public void bindHolder(HomeDataBean.HomeGoodsBean bean, int position) {
            Glide.with(mContext).load(bean.getImage()).into(img_goods);
            tv_goodsName.setText(bean.getName());
            tv_price.setText(bean.getPrice());
            if(isGift){//有赠券
                tv_line_price.setVisibility(View.GONE);
                tv_gift_coupon.setVisibility(View.VISIBLE);
                tv_gift_coupon.setText("赠券"+bean.getGiftCoupon());
            }else{
                tv_line_price.setVisibility(View.VISIBLE);
                tv_gift_coupon.setVisibility(View.GONE);
                tv_line_price.setText(bean.getLinePrice());
                //中划线
                tv_line_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }


}
