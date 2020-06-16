package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.BaoPinGoodsBean;
import com.xk.mall.model.entity.HomeDataBean;
import com.xk.mall.model.entity.HotGoodsBean;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;

import java.util.List;

/**
 * 热排行 adapter
 */
public class HotRankAdapter extends  RvAdapter<BaoPinGoodsBean>{
    private Context mContext;
    private int rankType;//1 爆品  2 热推  3 喜赚
    public HotRankAdapter(int rankType,Context context, List<BaoPinGoodsBean> list, RvListener listener) {
        super(context, list, listener);
        this.rankType=rankType;
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_hotgoods_rank;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new GoodsOneHolder(view,viewType,listener);
    }

    private class GoodsOneHolder extends RvHolder<BaoPinGoodsBean>{
        private ImageView iv_goods;
        private TextView tv_rank_num, tv_goods_name,tv_price,tv_line_price,tv_forward_num;
        private LinearLayout ll_coupon;
        private RelativeLayout rl_rank_num;
        private TextView tv_discount_and_people,tv_coupon;

        public GoodsOneHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            iv_goods=itemView.findViewById(R.id.iv_goods);
            tv_rank_num=itemView.findViewById(R.id.tv_rank_num);
            tv_goods_name=itemView.findViewById(R.id.tv_goods_name);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_line_price=itemView.findViewById(R.id.tv_line_price);
            tv_forward_num=itemView.findViewById(R.id.tv_forward_num);
            ll_coupon=itemView.findViewById(R.id.ll_coupon);
            rl_rank_num = itemView.findViewById(R.id.rl_rank_num);
            tv_discount_and_people=itemView.findViewById(R.id.tv_discount_and_people);
            tv_coupon=itemView.findViewById(R.id.tv_coupon);

        }

        @Override
        public void bindHolder(BaoPinGoodsBean bean, int position) {
            if(rankType==1){
                tv_discount_and_people.setVisibility(View.VISIBLE);
                ll_coupon.setVisibility(View.GONE);
                tv_discount_and_people.setText("封顶"+PriceUtil.changeDoubleToStr(bean.getRateOne())+"折");
                tv_forward_num.setText("转发"+bean.getShareCount()+"+");
                tv_price.setText(""+PriceUtil.dividePrice(bean.getCommodityPriceOne()));
                tv_line_price.setText("¥"+PriceUtil.dividePrice(bean.getSalePrice()));
            }else if(rankType==2){
                tv_discount_and_people.setVisibility(View.VISIBLE);
                ll_coupon.setVisibility(View.GONE);
                tv_discount_and_people.setText(bean.getBargainNumber()+"人助力");
                tv_forward_num.setText("转发"+bean.getShareCount()+"+");
                tv_price.setText(""+PriceUtil.dividePrice(bean.getCommodityPrice()));
                tv_line_price.setText("¥"+PriceUtil.dividePrice(bean.getSalePrice()));
            }else{//喜赚榜
                tv_discount_and_people.setVisibility(View.GONE);
                ll_coupon.setVisibility(View.VISIBLE);
                if(bean.getCouponAmount()==0){
                    tv_coupon.setText("0");
                }else{
                    tv_coupon.setText(PriceUtil.divideCoupon(bean.getCouponAmount()));
                }
                tv_forward_num.setText("省钱¥"+PriceUtil.dividePrice(bean.getSaveMoney()));
                tv_price.setText(PriceUtil.dividePrice(bean.getCommodityPrice()));
                tv_line_price.setText("¥"+PriceUtil.dividePrice(bean.getSalePrice()));
            }

            GlideUtil.show(mContext,bean.getGoodsImageUrl(),iv_goods);
            tv_goods_name.setText(bean.getCommodityName());
            //中划线
            tv_line_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tv_rank_num.setText(String.valueOf(position+1));
//            tv_forward_num.setText("转发"+bean.getForWard()+"+");
            if(position==0){
                rl_rank_num.setBackgroundResource(R.mipmap.ic_rank_one);
            }else if(position==1){
                rl_rank_num.setBackgroundResource(R.mipmap.ic_rank_two);
            }else if(position==2){
                rl_rank_num.setBackgroundResource(R.mipmap.ic_rank_three);
            }else{
                rl_rank_num.setBackgroundResource(R.mipmap.ic_rank_normal);
            }
        }
    }


}
