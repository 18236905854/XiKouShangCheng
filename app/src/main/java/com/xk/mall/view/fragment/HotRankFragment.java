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
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.BaoPinGoodsBean;
import com.xk.mall.model.impl.HotRankViewImpl;
import com.xk.mall.presenter.HotRankPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.view.activity.CutGoodsDetailActivity;
import com.xk.mall.view.activity.GlobalBuyerGoodsDetailActivity;
import com.xk.mall.view.activity.ManyGoodsDetailActivity;
import com.xk.mall.view.adapter.HotRankAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 热排行
 */
public class HotRankFragment extends BaseFragment<HotRankPresenter> implements HotRankViewImpl {
    private static final String TAG = "HotRankFragment";
    private static final String RNAK_TYPE = "rank_type";
    @BindView(R.id.iv_main)
    ImageView ivMain;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.multiImageView)
    MultiStateView multiImageView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<BaoPinGoodsBean> listData = new ArrayList<>();
    private HotRankAdapter hotRankAdapter;
    private int rankType;

    //回调用来接收参数
    public static HotRankFragment getInstance(int id) {
        HotRankFragment fragment = new HotRankFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(RNAK_TYPE, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected HotRankPresenter createPresenter() {
        return new HotRankPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        rankType = arguments.getInt(RNAK_TYPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_rank;
    }

    @Override
    protected void loadLazyData() {
        if (rankType == 1) {
            mPresenter.getBaoPinData();
        } else if (rankType == 2) {
            mPresenter.getHotPushData();
        } else {
            mPresenter.getXiZuanData();
        }

        Button btnReplay = multiImageView.findViewById(R.id.btn_replay);
        btnReplay.setOnClickListener(v -> {
            if (rankType == 1) {
                mPresenter.getBaoPinData();
            } else if (rankType == 2) {
                mPresenter.getHotPushData();
            } else {
                mPresenter.getXiZuanData();
            }
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            if (rankType == 1) {
                mPresenter.getBaoPinData();
            } else if (rankType == 2) {
                mPresenter.getHotPushData();
            } else {
                mPresenter.getXiZuanData();
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleView.setNestedScrollingEnabled(false);
        hotRankAdapter = new HotRankAdapter(rankType, mContext, listData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                BaoPinGoodsBean baoPinGoodsBean = listData.get(position);
                if(rankType == 1){//多买多折  ActivityType.ACTIVITY_MANY_BUY
                    Intent intent = new Intent(mContext, ManyGoodsDetailActivity.class);
                    intent.putExtra(ManyGoodsDetailActivity.ACTIVITY_GOODS_ID, baoPinGoodsBean.getId());
                    ActivityUtils.startActivity(intent);
                }else if(rankType == 2){//喜立得  ACTIVITY_CUT
                    Intent intent = new Intent(mContext, CutGoodsDetailActivity.class);
                    intent.putExtra(CutGoodsDetailActivity.ACTIVITY_GOODS_ID, baoPinGoodsBean.getId());
                    ActivityUtils.startActivity(intent);
                }else if(rankType == 3){//全球买手--- ACTIVITY_GLOBAL_BUYER
                    Intent intent = new Intent(mContext, GlobalBuyerGoodsDetailActivity.class);
                    intent.putExtra(GlobalBuyerGoodsDetailActivity.ACTIVITY_GOODS_ID, baoPinGoodsBean.getId());
                    ActivityUtils.startActivity(intent);
                }
            }
        });
        recycleView.setAdapter(hotRankAdapter);

    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        multiImageView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    @Override
    protected void initData() {

    }


    /**
     * 获取爆品成功
     *
     * @param model
     */
    @Override
    public void onGetBaoPinSuccess(BaseModel<List<BaoPinGoodsBean>> model) {
        bindDataView(model);
    }

    /**
     * 获取热推
     *
     * @param model
     */
    @Override
    public void onGetHotPushSuccess(BaseModel<List<BaoPinGoodsBean>> model) {
        bindDataView(model);
    }

    /**
     * 获取喜赚成功
     *
     * @param model
     */
    @Override
    public void onGetXiZuanSuccess(BaseModel<List<BaoPinGoodsBean>> model) {
        bindDataView(model);
    }

    private void bindDataView(BaseModel<List<BaoPinGoodsBean>> model) {
        List<BaoPinGoodsBean> modelData = model.getData();
        refreshLayout.finishRefresh();
        listData.clear();
        listData.addAll(modelData);
        if (listData.size() == 0) {
            multiImageView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }else {
            multiImageView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            if (rankType == 1) {
                Glide.with(mContext).load(R.mipmap.bg_hot_baopin).into(ivMain);
            } else if (rankType == 2) {
                Glide.with(mContext).load(R.mipmap.bg_hot_retui).into(ivMain);
            } else {
                Glide.with(mContext).load(R.mipmap.bg_hot_xizuan).into(ivMain);
            }
        }
        hotRankAdapter.notifyDataSetChanged();
    }

}
