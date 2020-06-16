package com.xk.mall.view.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.HomeBean;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;

import java.util.List;

/**
 * 首页喜立得 adapter
 */
public class HomeKanLiDeAdapter extends  RvAdapter<HomeBean.HomeBargainActivityModelBean.HomeBargainCommodityModelsBean>{
    private Context mContext;

    private int footCount=1;//尾部个数
    private static final int FOOT_TYPE=6;

    private boolean isFoot(int position) {
        return footCount!=0&&(position>=getBodySize());
    }

    private int getBodySize() {
        return list.size();
    }

    public HomeKanLiDeAdapter(Context context, List<HomeBean.HomeBargainActivityModelBean.HomeBargainCommodityModelsBean> list, RvListener listener) {
        super(context, list, listener);
        this.mContext=context;
    }

    @Override
    public int getItemCount() {
        if(list .size() >=4){
            return list.size()+footCount;
        }else {
            return list.size();
        }
    }

    @Override
    protected int getLayoutId(int viewType) {
        if(viewType==FOOT_TYPE){
            return  R.layout.item_index_wug_more;
        }else{
            return R.layout.item_index_kanlide2;
        }

    }

    //布局类型
    @Override
    public int getItemViewType(int position) {
        if(isFoot(position) && list.size() >= 4){
            return FOOT_TYPE;
        }else{
            return 0;
        }
    }

//    @Override
//    protected int getLayoutId(int viewType) {
//        return R.layout.item_index_kanlide2;
//    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new HomeWuGGoodsHolder(view,viewType,listener);
    }

    private class HomeWuGGoodsHolder extends RvHolder<HomeBean.HomeBargainActivityModelBean.HomeBargainCommodityModelsBean>{
        private ImageView img_wug_goods;
        private TextView  tv_goodsName,tv_sale_price,tv_line_price;
        private View view;

        public HomeWuGGoodsHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);

            img_wug_goods=itemView.findViewById(R.id.img_wug_goods);
            tv_goodsName=itemView.findViewById(R.id.tv_goodsName);
            tv_sale_price=itemView.findViewById(R.id.tv_sale_price);
            tv_line_price=itemView.findViewById(R.id.tv_line_price);
            view=itemView.findViewById(R.id.view);

        }

        @Override
        public void bindHolder(HomeBean.HomeBargainActivityModelBean.HomeBargainCommodityModelsBean bean, int position) {
            GlideUtil.show(mContext,bean.getGoodsImageUrl(),img_wug_goods);
            tv_goodsName.setText(bean.getCommodityName());
            tv_sale_price.setText(PriceUtil.dividePrice(bean.getCommodityPrice()));
            tv_line_price.setText(new StringBuilder("¥").append(PriceUtil.dividePrice(bean.getSalePrice())));
            tv_line_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//            int width=mContext.getResources().getDisplayMetrics().widthPixels- DensityUtils.dp2px(mContext,30);
            int width=mContext.getResources().getDisplayMetrics().widthPixels;
//                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (width / 2.5f),
//                        ViewGroup.LayoutParams.MATCH_PARENT);
//                itemView.setLayoutParams(params);
            if(position==0){
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) itemView.getLayoutParams();
                layoutParams.setMargins(DensityUtils.dp2px(mContext,15),0,0,0);
                itemView.setLayoutParams(layoutParams);
            }


//            if(position==list.size()-1){//最后一个
//                view.setVisibility(View.INVISIBLE);
//            }else{
//                view.setVisibility(View.VISIBLE);
//            }
        }
    }


}
