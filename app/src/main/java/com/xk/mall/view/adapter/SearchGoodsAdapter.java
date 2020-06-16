package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.HomeDataBean;
import com.xk.mall.model.entity.HomeSearchGoodsBean;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.PriceUtil;

import java.util.List;

/**
 * 搜索结果 商品 adapter
 */
public class SearchGoodsAdapter extends  RvAdapter<HomeSearchGoodsBean>{

    private Context mContext;
    DisplayMetrics displayMetrics ;
    private int width;
    public SearchGoodsAdapter(Context context, List<HomeSearchGoodsBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
        displayMetrics=DensityUtils.getDisplayMetrics(context);
        width=displayMetrics.widthPixels- DensityUtils.dp2px(context,30);//外边距2个15dp
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_search_goods;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new SearchHolder(view,viewType,listener);
    }

    private class SearchHolder extends RvHolder<HomeSearchGoodsBean>{
        private ImageView img_goods;
        private TextView  tv_goods_name,tv_price,tv_line_price, tv_activity_type;

        public SearchHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            img_goods=itemView.findViewById(R.id.img_goods);
            tv_goods_name=itemView.findViewById(R.id.tv_goods_name);
            tv_price=itemView.findViewById(R.id.tv_price);
            tv_line_price=itemView.findViewById(R.id.tv_line_price);
            tv_activity_type = itemView.findViewById(R.id.tv_activity_type);

        }

        @Override
        public void bindHolder(HomeSearchGoodsBean bean, int position) {
            Glide.with(mContext).load(bean.getImageUrl()).into(img_goods);
            //动态改变image 宽 高 为正方形
//            ViewGroup.LayoutParams layoutParams = img_goods.getLayoutParams();
//            layoutParams.width=width/2-DensityUtils.dp2px(mContext,15);
//            layoutParams.height=width/2-DensityUtils.dp2px(mContext,15);
//            img_goods.setLayoutParams(layoutParams);
            tv_activity_type.setText(ActivityType.getNameByType(bean.getActivityType()));

            tv_goods_name.setText(bean.getCommodityName());
            if(bean.getActivityType() == ActivityType.ACTIVITY_WUG){
                tv_line_price.setVisibility(View.GONE);
                tv_price.setText(PriceUtil.dividePrice(bean.getSalePrice()));
            }else {
                tv_line_price.setVisibility(View.VISIBLE);
                tv_line_price.setText("¥"+ PriceUtil.dividePrice(bean.getSalePrice()));
                //中划线
                tv_line_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                tv_price.setText(PriceUtil.dividePrice(bean.getCommodityPrice()));
            }
        }
    }


}
