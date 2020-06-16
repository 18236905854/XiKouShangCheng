package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.SettlementMxBean;
import com.xk.mall.model.entity.SettlementMxChildBean;
import com.xk.mall.model.impl.SettlementMXViewImpl;
import com.xk.mall.presenter.SettlementMXPresenter;
import com.xk.mall.view.adapter.SettlementAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 待结算中明细
 */
public class SettlementingActivity extends BaseActivity<SettlementMXPresenter> implements SettlementMXViewImpl {
    private static final String TAG = "SettlementingActivity";
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.stateView)
    MultiStateView stateView;
    List<SettlementMxChildBean> listData;
    private int page=1;
    private int limit=10;
    private SettlementAdapter redBagDetailAdapter;

    @Override
    protected SettlementMXPresenter createPresenter() {
        return new SettlementMXPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settlement_ing;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("待结算中明细");
    }

    @Override
    protected void initData() {
        mPresenter.getSettlementMXList(MyApplication.userId,page,limit);
        listData = new ArrayList<>();

        redBagDetailAdapter = new SettlementAdapter(mContext, listData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                Intent todetail = new Intent(SettlementingActivity.this, SettlementDetailActivity.class);
                todetail.putExtra(SettlementDetailActivity.RED_BAG_ID, listData.get(position).getModuleId());
                todetail.putExtra(SettlementDetailActivity.RED_BAG_KEY, listData.get(position).getRefKey());
                startActivity(todetail);
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(redBagDetailAdapter);

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getSettlementMXList(MyApplication.userId, page, limit);
        });

    }

    //获取数据成功
    @Override
    public void onGetListDataSuc(BaseModel<SettlementMxBean> model) {
        SettlementMxBean data = model.getData();
        if(data != null && data.getResult() != null){
            listData.addAll(data.getResult());
            redBagDetailAdapter.notifyDataSetChanged();
            if (page == 1 && data.getResult().size()==0){
                stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
            if(data.getResult().size() < limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }
        }else {
            stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }
}
