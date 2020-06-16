package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.HomeBean;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;

import java.util.List;

/**
 * 首页 全球买手 商品 adapter
 */
public class HomeGlobalAdapter extends RvAdapter<HomeBean.HomePageGlobalBuyerActivityModelBean.HomeGlobalBuyerCommodityModelsBean> {
    private static final String TAG = "HomeGlobalAdapter";
    private Context mContext;

    public HomeGlobalAdapter(Context context, List<HomeBean.HomePageGlobalBuyerActivityModelBean.HomeGlobalBuyerCommodityModelsBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext = context;

    }

//    @Override
//    public int getItemCount() {
//        return list.size() > 4 ? 4 : list.size();
//    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_index_global;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new GoodsOneHolder(view, viewType, listener);
    }

    private class GoodsOneHolder extends RvHolder<HomeBean.HomePageGlobalBuyerActivityModelBean.HomeGlobalBuyerCommodityModelsBean> {
        private ImageView img_wug_goods;
        private TextView tv_global_coupon;
        private TextView tv_goodsName, tv_sale_price, tv_line_price;

        public GoodsOneHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            img_wug_goods = itemView.findViewById(R.id.img_wug_goods);
            tv_global_coupon = itemView.findViewById(R.id.tv_global_coupon);
            tv_goodsName = itemView.findViewById(R.id.tv_goodsName);
            tv_sale_price = itemView.findViewById(R.id.tv_sale_price);
            tv_line_price = itemView.findViewById(R.id.tv_line_price);

        }

        @Override
        public void bindHolder(HomeBean.HomePageGlobalBuyerActivityModelBean.HomeGlobalBuyerCommodityModelsBean bean, int position) {
            GlideUtil.show(mContext, bean.getGoodsImageUrl(), img_wug_goods);
            tv_goodsName.setText(bean.getCommodityName());
            tv_global_coupon.setText(PriceUtil.divideCoupon(bean.getDeductionCouponAmount()));
            tv_sale_price.setText(PriceUtil.dividePrice(bean.getCommodityPrice()));
            tv_line_price.setText(new StringBuilder().append("¥").append(PriceUtil.dividePrice(bean.getSalePrice())));
            //中划线
            tv_line_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            //设置图片为正方形
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) img_wug_goods.getLayoutParams();
            int deviceWidth = DensityUtils.getDeviceWidth(mContext);
//            Log.e(TAG, "bindHolder: " + deviceWidth);
            layoutParams.height = (deviceWidth - DensityUtils.dp2px(mContext, 35)) / 2;
            img_wug_goods.setLayoutParams(layoutParams);
        }
    }


}
