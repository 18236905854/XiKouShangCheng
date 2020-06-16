package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.GlobalBuyerChildPageBean;
import com.xk.mall.model.impl.GlobalBuyerChildViewImpl;
import com.xk.mall.presenter.GlobalBuyerChildPresenter;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.adapter.RvAdapter;
import com.xk.mall.view.adapter.RvHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName GlobalBuyerChildActivity
 * Description 全球买手商品列表子页面
 * Author 卿凯
 * Date 2019/6/27/027
 * Version V1.0
 */
public class GlobalBuyerChildActivity extends BaseActivity<GlobalBuyerChildPresenter> implements GlobalBuyerChildViewImpl {
    @BindView(R.id.tv_global_buyer_child_composite)
    TextView tvGlobalBuyerChildComposite;//综合
    @BindView(R.id.tv_global_buyer_child_price)
    TextView tvGlobalBuyerChildPrice;//价格
    @BindView(R.id.iv_near_order_first)
    ImageView ivNearFirst;//价格
    @BindView(R.id.tv_global_buyer_child_new)
    TextView tvGlobalBuyerChildNew;//上新
    @BindView(R.id.iv_near_order_center)
    ImageView ivNearCenter;//上新
    @BindView(R.id.tv_global_buyer_child_volume)
    TextView tvGlobalBuyerChildVolume;//销量
    @BindView(R.id.iv_near_order_last)
    ImageView ivNearLast;//销量
    @BindView(R.id.ll_global_buyer_filter_price)
    LinearLayout llGlobalBuyerFilterPrice;//价格排序
    @BindView(R.id.ll_global_buyer_filter_new)
    LinearLayout llGlobalBuyerFilterNew;//上新排序
    @BindView(R.id.ll_global_buyer_filter_sell)
    LinearLayout llGlobalBuyerFilterSell;//销售排序
    @BindView(R.id.rv_global_buyer_child)
    RecyclerView rvGlobalBuyerChild;//列表
    @BindView(R.id.refresh_child_global)
    SmartRefreshLayout smartRefreshLayout;

    public static String TITLE = "title";
    public static String ACTIVITY_ID = "activity_id";
    public static String CATEGORY_ID = "category_id";
    private String activityId = "";//传递过来的活动ID
    private String categoryId = "";//传递过来的分类ID
    private int sortType = 0;// 1:价格 2:上新 3:销量
    private int sortMode = 0;// 排序方式 1:升序 2:倒序
    private int page = 1;// 当前页数
    private int limit = 10;//每页限制的数量
    private List<GlobalBuyerChildPageBean.GlobalBuyerChildGoodsBean> buyerBeanList;
    private GlobalBuyerChildAdapter2 buyerChildAdapter;
    private boolean isHasMore = true;//是否还有更多

    @Override
    protected GlobalBuyerChildPresenter createPresenter() {
        return new GlobalBuyerChildPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_global_buyer_child;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String title = intent.getStringExtra(TITLE);
        tvGlobalBuyerChildComposite.setSelected(true);
        activityId = intent.getStringExtra(ACTIVITY_ID);
        categoryId = intent.getStringExtra(CATEGORY_ID);
        setTitle(title);
        buyerBeanList = new ArrayList<>();
        mPresenter.getGloBuyerChildData(activityId, categoryId, sortType, sortMode, page, limit);


//        rvGlobalBuyerChild.setLayoutManager(new LinearLayoutManager(mContext));
//
//        buyerChildAdapter = new GlobalBuyerChildAdapter(mContext, R.layout.item_global_buyer_child_goods, buyerBeanList);
//        rvGlobalBuyerChild.setAdapter(buyerChildAdapter);
//
//        buyerChildAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                //点击跳转全球买手商品详情
//                //点击进入商品详情页面
//                if(position >= 0 && position < buyerBeanList.size()){
//                    Intent intent = new Intent(mContext, GlobalBuyerGoodsDetailActivity.class);
//                    intent.putExtra(GlobalBuyerGoodsDetailActivity.ACTIVITY_GOODS_ID, buyerBeanList.get(position).getId());
//                    ActivityUtils.startActivity(intent);
//                }
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                return false;
//            }
//        });


        rvGlobalBuyerChild.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvGlobalBuyerChild.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));

        buyerChildAdapter = new GlobalBuyerChildAdapter2(mContext, buyerBeanList, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                //点击跳转全球买手商品详情
                //点击进入商品详情页面
                if(position >= 0 && position < buyerBeanList.size()){
                    Intent intent = new Intent(mContext, GlobalBuyerGoodsDetailActivity.class);
                    intent.putExtra(GlobalBuyerGoodsDetailActivity.ACTIVITY_GOODS_ID, buyerBeanList.get(position).getId());
                    ActivityUtils.startActivity(intent);
                }
            }
        });
        rvGlobalBuyerChild.setAdapter(buyerChildAdapter);







        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getGloBuyerChildData(activityId, categoryId, sortType, sortMode, page, limit);
        });

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if(isHasMore){
                page = page + 1;
                mPresenter.getGloBuyerChildData(activityId, categoryId, sortType, sortMode, page, limit);
            }else {
                showToast("没有更多数据了");
                smartRefreshLayout.finishLoadMore();
                smartRefreshLayout.setEnableAutoLoadMore(false);
            }
        });
    }

    @OnClick({R.id.tv_global_buyer_child_composite, R.id.ll_global_buyer_filter_price, R.id.ll_global_buyer_filter_new,
            R.id.ll_global_buyer_filter_sell})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.tv_global_buyer_child_composite://综合
                changeOrder(0);
                break;

            case R.id.ll_global_buyer_filter_price://价格
                sortMode = sortMode == 1? 2 : 1;
                changeOrder(1);
                break;

            case R.id.ll_global_buyer_filter_new://上新
                sortMode = sortMode == 1? 2 : 1;
                changeOrder(2);
                break;

            case R.id.ll_global_buyer_filter_sell://销量
                sortMode = sortMode == 1? 2 : 1;
                changeOrder(3);
                break;
        }
    }

    /**
     * 改变排序
     */
    private void changeOrder(int position){
        page = 1;
        sortType = position;
        if(position == 0){
            sortMode = 0;
            tvGlobalBuyerChildComposite.setSelected(true);
            tvGlobalBuyerChildPrice.setSelected(false);
            tvGlobalBuyerChildNew.setSelected(false);
            tvGlobalBuyerChildVolume.setSelected(false);
            ivNearFirst.setImageResource(R.drawable.near_order_default);
            ivNearCenter.setImageResource(R.drawable.near_order_default);
            ivNearLast.setImageResource(R.drawable.near_order_default);
        }else if(position == 1){
            tvGlobalBuyerChildComposite.setSelected(false);
            tvGlobalBuyerChildPrice.setSelected(true);
            tvGlobalBuyerChildNew.setSelected(false);
            tvGlobalBuyerChildVolume.setSelected(false);
            if(sortMode == 1){
                ivNearFirst.setImageResource(R.drawable.near_order_up);
            }else {
                ivNearFirst.setImageResource(R.drawable.near_order_down);
            }
            ivNearCenter.setImageResource(R.drawable.near_order_default);
            ivNearLast.setImageResource(R.drawable.near_order_default);
        }else if(position == 2){
            tvGlobalBuyerChildComposite.setSelected(false);
            tvGlobalBuyerChildPrice.setSelected(false);
            tvGlobalBuyerChildNew.setSelected(true);
            tvGlobalBuyerChildVolume.setSelected(false);
            if(sortMode == 1){
                ivNearCenter.setImageResource(R.drawable.near_order_up);
            }else {
                ivNearCenter.setImageResource(R.drawable.near_order_down);
            }
            ivNearFirst.setImageResource(R.drawable.near_order_default);
            ivNearLast.setImageResource(R.drawable.near_order_default);
        }else if(position == 3){
            tvGlobalBuyerChildComposite.setSelected(false);
            tvGlobalBuyerChildPrice.setSelected(false);
            tvGlobalBuyerChildNew.setSelected(false);
            tvGlobalBuyerChildVolume.setSelected(true);
            if(sortMode == 1){
                ivNearLast.setImageResource(R.drawable.near_order_up);
            }else {
                ivNearLast.setImageResource(R.drawable.near_order_down);
            }
            ivNearFirst.setImageResource(R.drawable.near_order_default);
            ivNearCenter.setImageResource(R.drawable.near_order_default);
        }
        mPresenter.getGloBuyerChildData(activityId, categoryId, sortType, sortMode, page, limit);
    }

    @Override
    public void onGetDataSuccess(BaseModel<GlobalBuyerChildPageBean> model) {
        smartRefreshLayout.finishLoadMore();
        smartRefreshLayout.finishRefresh();
        if(page == 1){
            buyerBeanList.clear();
        }
        if(model.getData().totalCount < limit){
            isHasMore = false;
        }else {
            isHasMore = true;
        }
        buyerBeanList.addAll(model.getData().result);
        buyerChildAdapter.notifyDataSetChanged();
        smartRefreshLayout.setEnableLoadMore(isHasMore);
    }


    public class GlobalBuyerChildAdapter2 extends RvAdapter<GlobalBuyerChildPageBean.GlobalBuyerChildGoodsBean> {
        private static final String TAG = "GlobalBuyerChildAdapter2";
        private Context mContext;

        public GlobalBuyerChildAdapter2(Context context, List<GlobalBuyerChildPageBean.GlobalBuyerChildGoodsBean> list, RvListener listener) {
            super(context, list, listener);
            this.mContext = context;

        }

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
            return new GlobalBuyerChildAdapter2.GoodsOneHolder(view, viewType, listener);
        }

        private class GoodsOneHolder extends RvHolder<GlobalBuyerChildPageBean.GlobalBuyerChildGoodsBean> {
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
            public void bindHolder(GlobalBuyerChildPageBean.GlobalBuyerChildGoodsBean bean, int position) {
                GlideUtil.show(mContext, bean.getGoodsImageUrl(), img_wug_goods);
                tv_goodsName.setText(bean.getCommodityName());
                tv_global_coupon.setText(PriceUtil.divideCoupon(bean.getCouponValue()));
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

//    class GlobalBuyerChildAdapter extends CommonAdapter<GlobalBuyerChildPageBean.GlobalBuyerChildGoodsBean>{
//
//        public GlobalBuyerChildAdapter(Context context, int layoutId, List<GlobalBuyerChildPageBean.GlobalBuyerChildGoodsBean> datas) {
//            super(context, layoutId, datas);
//        }
//
//        @Override
//        protected void convert(ViewHolder holder, GlobalBuyerChildPageBean.GlobalBuyerChildGoodsBean globalBuyerBean, int position) {
//            ImageView ivLogo = holder.getView(R.id.iv_global_child_logo);
//            GlideUtil.showRadius(mContext, globalBuyerBean.getGoodsImageUrl(),2, ivLogo);
//            holder.setText(R.id.tv_global_child_goods_name, globalBuyerBean.getCommodityName());
//            holder.setText(R.id.tv_global_child_price, "" + PriceUtil.dividePrice(globalBuyerBean.getCommodityPrice()));
//            TextView tvLinePrice = holder.getView(R.id.tv_global_child_line_price);
//            tvLinePrice.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(globalBuyerBean.getSalePrice()));
//            tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//            holder.setText(R.id.tv_global_child_coupon, "" + PriceUtil.divideCoupon(globalBuyerBean.getCouponValue()));
//            TextView tvBuy = holder.getView(R.id.tv_buy);
//            if(globalBuyerBean.getStock() <= 0){
//                tvBuy.setText("已售罄");
//                tvBuy.setEnabled(false);
//                tvBuy.setBackgroundResource(R.drawable.bg_register_btn_selector);
//                holder.setVisible(R.id.iv_goods_empty, true);
//            }else {
//                tvBuy.setText("立即抢购");
//                tvBuy.setEnabled(true);
//                tvBuy.setBackgroundResource(R.drawable.bg_many_share_money);
//                holder.setVisible(R.id.iv_goods_empty, false);
//            }
//        }
//    }

}
