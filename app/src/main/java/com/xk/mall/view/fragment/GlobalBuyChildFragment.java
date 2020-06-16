package com.xk.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.kennyc.view.MultiStateView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.GlobalBuyerServerBean;
import com.xk.mall.model.entity.NearAddressBean;
import com.xk.mall.model.eventbean.LocationPointBean;
import com.xk.mall.model.impl.GlobalBuyChildViewImpl;
import com.xk.mall.model.impl.ManyBuyChildViewImpl;
import com.xk.mall.model.impl.NearChildViewImpl;
import com.xk.mall.presenter.GlobalBuyChildPresenter;
import com.xk.mall.presenter.ManyBuyChildPresenter;
import com.xk.mall.presenter.NearChildPresenter;
import com.xk.mall.promptdialog.PromptDialog;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.EventBusMessage;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.SpacesItemDecoration;
import com.xk.mall.view.activity.GlobalBuyerActivity;
import com.xk.mall.view.activity.GlobalBuyerChildActivity;
import com.xk.mall.view.activity.GlobalBuyerGoodsDetailActivity;
import com.xk.mall.view.activity.ManyGoodsDetailActivity;
import com.xk.mall.view.activity.ShopActivity;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * ClassName NearFragment
 * Description 全球买手商品种类对应的商品条目显示View
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public class GlobalBuyChildFragment extends BaseFragment<GlobalBuyChildPresenter> implements GlobalBuyChildViewImpl {
    @BindView(R.id.rv_global_buy_child)
    RecyclerView rvGlobalBuyChild;
    @BindView(R.id.refresh_global)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.multi_view_global)
    MultiStateView multiStateView;
    @BindView(R.id.rl_more_goods)
    RelativeLayout rlMoreGoods;
    private List< ActiveSectionGoodsBean> result;

    private String categoryId = "";//请求数据的分类ID
    private String categoryName="";//请求数据的分类名称
    private int page = 1;
    private GlobalBuyChildFragment.GlobalBuyChildAdapter globalBuyChildAdapter;

    public static GlobalBuyChildFragment getInstance(String id,String categoryName) {
        GlobalBuyChildFragment fragment = new GlobalBuyChildFragment();
        fragment.categoryId = id;
        fragment.categoryName=categoryName;
        return fragment;
    }

    @Override
    protected GlobalBuyChildPresenter createPresenter() {
        return new GlobalBuyChildPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_global_buy_child;
    }

    @Override
    protected void initData() {
        rlMoreGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, GlobalBuyerChildActivity.class);
                intent.putExtra(GlobalBuyerChildActivity.ACTIVITY_ID, ActivityType.ACTIVITY_GLOBAL_BUYER);
                intent.putExtra(GlobalBuyerChildActivity.CATEGORY_ID, categoryId);
                intent.putExtra(GlobalBuyerChildActivity.TITLE, categoryName);
                ActivityUtils.startActivity(intent);
            }
        });
    }


    @Override
    protected void loadLazyData() {
        mPresenter.getActiveSectionGoods(categoryId, ActivityType.ACTIVITY_GLOBAL_BUYER,MyApplication.userId, page, Constant.limit);
        refreshLayout.setEnableRefresh(false);
        result = new ArrayList<>();
        globalBuyChildAdapter = new GlobalBuyChildFragment.GlobalBuyChildAdapter(mContext, R.layout.global_buy_grid_item, result);
        rvGlobalBuyChild.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvGlobalBuyChild.addItemDecoration(new GridSpacingItemDecoration(2, ConvertUtils.dp2px(13), false));
        rvGlobalBuyChild.setAdapter(globalBuyChildAdapter);
        globalBuyChildAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //点击进入全球买手商品详情页面
                ActiveSectionGoodsBean sectionGoodsBean = result.get(position);
                Intent intent = new Intent(mContext, GlobalBuyerGoodsDetailActivity.class);
                intent.putExtra(GlobalBuyerGoodsDetailActivity.ACTIVITY_GOODS_ID,sectionGoodsBean.getActivityGoodsId());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
/*        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getActiveSectionGoods(categoryId, ActivityType.ACTIVITY_GLOBAL_BUYER, MyApplication.userId, page, Constant.limit);
        });*/
    }

    @Override
    public void onGetActiveSectionGoodsSuccess(BaseModel< ActiveSectionGoodsPageBean> model) {
        if(model.getData() != null){
            if(model.getData().getResult() != null && model.getData().getResult().size() != 0){
                result.addAll(model.getData().getResult());
                globalBuyChildAdapter.notifyDataSetChanged();
                String activityId = model.getData().getResult().get(0).getActivityId();
                EventBus.getDefault().post(new EventBusMessage(activityId));
            }

            if(page == 1 && model.getData().getResult().size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    class GlobalBuyChildAdapter extends CommonAdapter< ActiveSectionGoodsBean> {

        public GlobalBuyChildAdapter(Context context, int layoutId, List< ActiveSectionGoodsBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ActiveSectionGoodsBean globalBuyerBean, int position) {
            ImageView ivLogo = holder.getView(R.id.iv_global_logo);
            GlideUtil.showRadius(mContext, globalBuyerBean.getGoodsImageUrl(), 2, ivLogo);
            holder.setText(R.id.tv_global_name, globalBuyerBean.getCommodityName());
            holder.setText(R.id.tv_global_now_price, PriceUtil.dividePrice(globalBuyerBean.getCommodityPrice()));
            holder.setText(R.id.tv_global_real_price, mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(globalBuyerBean.getSalePrice()));
            TextView tvLinePrice = holder.getView(R.id.tv_global_real_price);
            tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.setText(R.id.tv_global_coupon, PriceUtil.divideCoupon(globalBuyerBean.getCouponValue()));

            //设置图片为正方形
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ivLogo.getLayoutParams();
            int deviceWidth = DensityUtils.getDeviceWidth(mContext);
            layoutParams.height= (deviceWidth - DensityUtils.dp2px(mContext,35)) / 2  ;
            ivLogo.setLayoutParams(layoutParams);
            if(globalBuyerBean.getStock() <= 0){
                holder.setVisible(R.id.iv_goods_empty, true);
            }else {
                holder.setVisible(R.id.iv_goods_empty, false);
            }
        }
    }


}
