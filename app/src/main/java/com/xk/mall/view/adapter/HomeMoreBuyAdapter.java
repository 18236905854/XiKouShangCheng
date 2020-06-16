package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.HomeBean;
import com.xk.mall.model.entity.HomeDataBean;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ScreenShotUtils;

import java.util.List;

/**
 * 首页 多买多得 商品 adapter
 */
public class HomeMoreBuyAdapter extends  RvAdapter<HomeBean.HomeDiscountActivityModelBean.HomeDiscountCommodityModelsBean>{
    private static final String TAG = "HomeMoreBuyAdapter";
    private Context mContext;
    public HomeMoreBuyAdapter(Context context, List<HomeBean.HomeDiscountActivityModelBean.HomeDiscountCommodityModelsBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;

    }

//    @Override
//    public int getItemCount() {
//        return list.size()>4 ? 4: list.size() ;
//    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_home_twochild;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new GoodsOneHolder(view,viewType,listener);
    }

    private class GoodsOneHolder extends RvHolder<HomeBean.HomeDiscountActivityModelBean.HomeDiscountCommodityModelsBean>{
        private ImageView img_goods;
        private TextView  tv_vip_discout;
        private TextView  tv_goods_name,tv_hot_sale_num,tv_price,tv_line_price;
        private TextView tv_bottom_price;//最低

        public GoodsOneHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            img_goods=itemView.findViewById(R.id.img_goods);
            tv_vip_discout=itemView.findViewById(R.id.tv_vip_discout);
            tv_goods_name=itemView.findViewById(R.id.tv_goods_name);
            tv_hot_sale_num=itemView.findViewById(R.id.tv_hot_sale_num);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_line_price=itemView.findViewById(R.id.tv_line_price);
            tv_bottom_price=itemView.findViewById(R.id.tv_bottom_price);
        }

        @Override
        public void bindHolder(HomeBean.HomeDiscountActivityModelBean.HomeDiscountCommodityModelsBean bean, int position) {
            GlideUtil.show(mContext,bean.getGoodsImageUrl(), img_goods);
            tv_vip_discout.setText(new StringBuilder().append("会员").append(bean.getDiscount()).append("折"));
            tv_goods_name.setText(bean.getCommodityName());
            tv_bottom_price.setText(new StringBuilder().append("封顶").append(PriceUtil.changeDoubleToStr(bean.getRateOne())).append("折"));
            tv_hot_sale_num.setText(new StringBuilder().append("分享赚").append(PriceUtil.dividePrice(bean.getShareMoney())));
            tv_price.setText(String.valueOf(PriceUtil.dividePrice(bean.getCommodityPriceOne())));
            tv_line_price.setText(new StringBuilder().append("¥").append(PriceUtil.dividePrice(bean.getSalePrice())));
            //中划线
            tv_line_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //设置图片为正方形
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) img_goods.getLayoutParams();
            int deviceWidth = DensityUtils.getDeviceWidth(mContext);
            layoutParams.height= (deviceWidth - DensityUtils.dp2px(mContext,35)) / 2  ;
            img_goods.setLayoutParams(layoutParams);
        }
    }


}
