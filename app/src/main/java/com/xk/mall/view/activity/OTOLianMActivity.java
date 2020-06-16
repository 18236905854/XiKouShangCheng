package com.xk.mall.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OtoOrderBean;
import com.xk.mall.model.entity.OtoOrderPageBean;
import com.xk.mall.model.impl.OTOOrderViewImpl;
import com.xk.mall.presenter.OTOOrderPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.adapter.OtoLianMAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName OTOLianMActivity
 * Description  OTO 联盟 页面
 * Author
 * Date
 * Version
 */
public class OTOLianMActivity extends BaseActivity<OTOOrderPresenter> implements OTOOrderViewImpl {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.layoutContent)
    LinearLayout layoutContent;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;
    private List<OtoOrderBean> listData;
    private OtoLianMAdapter otoLianMAdapter;
    private int page = 1;
    private int limit = 10;

    @Override
    protected OTOOrderPresenter createPresenter() {
        return new OTOOrderPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_oto_lianm;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setText("O2O联盟");
    }

    @Override
    protected void initData() {
        mPresenter.getOTOOrderList(SPUtils.getInstance().getString(Constant.USER_MOBILE), page, limit);
        listData = new ArrayList<>();
        otoLianMAdapter = new OtoLianMAdapter(mContext,R.layout.item_oto_lianmen
                ,listData);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(otoLianMAdapter);
        otoLianMAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                Log.e(TAG, "onItemClick: "+position );
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getOTOOrderList(SPUtils.getInstance().getString(Constant.USER_MOBILE), page, limit);
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getOTOOrderList(SPUtils.getInstance().getString(Constant.USER_MOBILE), page, limit);
        });
    }


    @Override
    public void onGetOtoOrderListSuccess(BaseModel<OtoOrderPageBean> model) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if(model.getData() != null && model.getData().getResult() != null){
            if(page == 1){
                listData.clear();
            }
            listData.addAll(model.getData().getResult());
            otoLianMAdapter.notifyDataSetChanged();
            if(model.getData().getResult().size() < limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }
            if(page == 1 && model.getData().getResult().size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }else {
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }
}
