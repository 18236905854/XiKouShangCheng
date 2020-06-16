package com.xk.mall.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.isseiaoki.simplecropview.util.Logger;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.AttenShopListBean;
import com.xk.mall.model.impl.AttentionShopViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;
import com.xk.mall.presenter.AttentionShopPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.activity.ShopActivity;
import com.xk.mall.view.adapter.AttentionShopAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ClassName AttentionFragment
 * Description 关注 商铺
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public class AttentionShopFragment extends BaseFragment<AttentionShopPresenter> implements AttentionShopViewImpl {
    private static final String TAG = "AttentionShopFragment";
    @BindView(R.id.refresh_attention)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.state_view_attention)
    MultiStateView llAttention;//当前页面的父布局
    @BindView(R.id.rv_attention)
    RecyclerView rvAttention;
    private List<AttenShopListBean.ResultBean> listData;
    private AttentionShopAdapter attentionShopAdapter;
    private int page = 1;//当前页面
    private int limit = Constant.limit;//当前页面的条数
    private int cancelPos = 0;//取消关注的位置
    private boolean hasMore = true;//是否有更多

    public static AttentionShopFragment getInstance() {
        AttentionShopFragment fragment = new AttentionShopFragment();
        return fragment;
    }

    @Override
    protected AttentionShopPresenter createPresenter() {
        return new AttentionShopPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attention;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadLazyData() {
        rvAttention.setLayoutManager(new LinearLayoutManager(mContext));
        listData = new ArrayList<>();
        attentionShopAdapter = new AttentionShopAdapter(mContext, R.layout.attention_item, listData);
        rvAttention.setAdapter(attentionShopAdapter);
        getData();
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData();
        });

        mRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
            if (hasMore) {
                page = page + 1;
                getData();
            } else {
                mRefreshLayout.finishLoadMore(800);
                com.orhanobut.logger.Logger.e("没有更多数据了");
//                ToastUtils.showShortToast(getActivity(), "没有更多数据了");
            }
        });

        attentionShopAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Intent intent = new Intent(mContext, ShopActivity.class);
                intent.putExtra(ShopActivity.SHOP_ID, listData.get(position).getId());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        attentionShopAdapter.setRvButtonListener((view, position) -> {
            cancelPos=position;
            AttentionRequestBody requestBody=new AttentionRequestBody();
            requestBody.setUserId(MyApplication.userId);
            requestBody.setShopId(listData.get(position).getId());
            requestBody.setOperationType(Constant.CANCEL);
            mPresenter.cancelShop(requestBody);
        });
    }


    private void getData() {
        mPresenter.getAttenShopListData(MyApplication.userId, page, limit);
    }

    @Override
    public void onAttentionGetData(BaseModel<AttenShopListBean> shopListBean) {
        if (page == 1) {
            listData.clear();
            if (shopListBean == null || shopListBean.getData().getResult().size() == 0) {
                Logger.e("显示空布局");
                llAttention.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            } else {
                listData.addAll(shopListBean.getData().getResult());
            }
        } else {
            listData.addAll(shopListBean.getData().getResult());
        }

        if (shopListBean.getData().getResult().size() < limit) {//当返回的数据小于请求的条数时，说明没有更多数据了
            hasMore = false;
        }

        attentionShopAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }

    /**
     * 取消关注成功的回调
     */
    @Override
    public void cancelAttentionSuccess(BaseModel baseModel) {
        listData.remove(cancelPos);
        attentionShopAdapter.notifyItemRemoved(cancelPos);
        attentionShopAdapter.notifyDataSetChanged();
        if(listData.size()==0){
            Logger.e("显示空布局");
            llAttention.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
        ToastUtils.showShortToast(getActivity(), "取消关注成功");
    }


    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }

}
