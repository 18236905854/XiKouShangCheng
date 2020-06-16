package com.xk.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.ManyBean;
import com.xk.mall.model.impl.ManyBuyChildViewImpl;
import com.xk.mall.presenter.ManyBuyChildPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.SpacesItemDecoration;
import com.xk.mall.view.activity.ManyGoodsDetailActivity;
import com.xk.mall.view.adapter.ManyHotAdapter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ClassName ManyBuyChildFragment
 * Description 多买多折限时秒杀的fragment
 * Author 卿凯
 * Date 2019/6/24/024
 * Version V1.0
 */
public class ManyBuyChildFragment extends BaseFragment<ManyBuyChildPresenter> implements ManyBuyChildViewImpl {
    @BindView(R.id.rv_many_buy_child)
    RecyclerView rvManyBuyChild;
    @BindView(R.id.refresh_many)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.multi_view_many)
    MultiStateView multiStateView;
    private List<ActiveSectionGoodsBean> result;

    private String categoryId = "";//请求数据的分类ID
    private int page = 1;
    private ManyBuyChildAdapter manyBuyChildAdapter;

    public static ManyBuyChildFragment getInstance(String id) {
        ManyBuyChildFragment fragment = new ManyBuyChildFragment();
        fragment.categoryId = id;
        return fragment;
    }

    @Override
    protected ManyBuyChildPresenter createPresenter() {
        return new ManyBuyChildPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_many_buy_child;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadLazyData() {
        mPresenter.getActiveSectionGoods(categoryId, ActivityType.ACTIVITY_MANY_BUY,MyApplication.userId, page, Constant.limit);
        refreshLayout.setEnableRefresh(false);
        result = new ArrayList<>();
        manyBuyChildAdapter = new ManyBuyChildAdapter(mContext, R.layout.many_buy_grid_item, result);
        rvManyBuyChild.setLayoutManager(new LinearLayoutManager(mContext));
        rvManyBuyChild.addItemDecoration(new SpacesItemDecoration(30));
        rvManyBuyChild.setAdapter(manyBuyChildAdapter);
        manyBuyChildAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //点击进入多买多折商品详情页面
                ActiveSectionGoodsBean sectionGoodsBean = result.get(position);
                Intent intent = new Intent(mContext, ManyGoodsDetailActivity.class);
                intent.putExtra(ManyGoodsDetailActivity.ACTIVITY_GOODS_ID,sectionGoodsBean.getActivityGoodsId());
//                intent.putExtra(ManyGoodsDetailActivity.GOODS_PRICE,PriceUtil.dividePrice(sectionGoodsBean.getCommodityPrice()));
//                intent.putExtra(ManyGoodsDetailActivity.GOODS_LINE_PRICE,PriceUtil.dividePrice(sectionGoodsBean.getSalePrice()));
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getActiveSectionGoods(categoryId, ActivityType.ACTIVITY_MANY_BUY, MyApplication.userId, page, Constant.limit);
        });
    }

    @Override
    public void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model) {
        refreshLayout.finishLoadMore();
        if(model.getData() != null){
            if(model.getData().getResult() != null && model.getData().getResult().size() != 0){
                result.addAll(model.getData().getResult());
                manyBuyChildAdapter.notifyDataSetChanged();
            }

            if(page == 1 && model.getData().getResult().size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
            if(model.getData().getResult().size() < Constant.limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }
        }else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    class ManyBuyChildAdapter extends CommonAdapter<ActiveSectionGoodsBean>{

        public ManyBuyChildAdapter(Context context, int layoutId, List<ActiveSectionGoodsBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ActiveSectionGoodsBean manyBean, int position) {
            holder.setText(R.id.tv_many_buy_name, manyBean.getCommodityName());
            holder.setText(R.id.tv_many_buy_now_price, PriceUtil.dividePrice(manyBean.getCommodityPriceOne()));
            holder.setText(R.id.tv_many_buy_real_price, mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(manyBean.getSalePrice()));
            TextView tvLinePrice = holder.getView(R.id.tv_many_buy_real_price);
            tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            ImageView ivLogo = holder.getView(R.id.iv_many_buy_logo);
            holder.setText(R.id.tv_many_share_money, "分享赚" + PriceUtil.dividePrice(manyBean.getShareMoney()));
//            float minRate = SPUtils.getInstance().getFloat(Constant.RATE_THREE);
//            if(minRate <= 0){
//                holder.setText(R.id.tv_many_buy_min_discount, "最低3折");
//            }else {
                holder.setText(R.id.tv_many_buy_min_discount, "封顶" + PriceUtil.changeDoubleToStr(manyBean.getRateOne()) + "折");
//            }
            GlideUtil.showRadius(mContext, manyBean.getGoodsImageUrl(),2, ivLogo);
            if(manyBean.getStock() <= 0){
                holder.setVisible(R.id.iv_goods_empty, true);
            }else {
                holder.setVisible(R.id.iv_goods_empty, false);
            }
        }

        /**
         * 将float转为String
         */
        private String changeFloatToString(float discount){
            double result = Float.valueOf(discount).doubleValue();
            String discountStr;
            if(result % 1.0 == 0){
                discountStr = String.valueOf((long)result);
            }else {
                discountStr = String.valueOf(result);
            }
            return  discountStr + "折";
        }
    }


}
