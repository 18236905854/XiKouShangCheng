package com.xk.mall.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.eventbean.RefreshWuGEvent;
import com.xk.mall.model.impl.WuGViewImpl;
import com.xk.mall.presenter.WuGPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.EventBusMessage;
import com.xk.mall.utils.SpacesItemDecoration;
import com.xk.mall.view.activity.WuGGoodsDetailActivity;
import com.xk.mall.view.adapter.WugGoodesAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 吾G 商品 fragment
 */
public class WuGGoodsFragment extends BaseFragment<WuGPresenter> implements WuGViewImpl {
    private static final String TAG = "WuGGoodsFragment";

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refresh_wug)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.rl_wug_empty)
    RelativeLayout rlEmpty;//空布局
    private boolean isHasMore = true;
    private int page = 1;
    private int limit = 10;

    private String dataId = "";//用来请求数据的ID
    private String scheduleId = "";//场次ID
    private List<ActiveSectionGoodsBean> hotGoodsBeanList;
    private WugGoodesAdapter hotRankAdapter;
    private String activityId;
    boolean isStart;//是否开始
    boolean isScheduleOpen;//场次开光是否打开

    public static WuGGoodsFragment getInstance(String id, String scheduleId, boolean isStart, boolean isScheduleOpen) {
        WuGGoodsFragment fragment = new WuGGoodsFragment();
        fragment.dataId = id;
        fragment.scheduleId = scheduleId;
        fragment.isStart = isStart;
        fragment.isScheduleOpen = isScheduleOpen;
        return fragment;
    }

    @Override
    protected WuGPresenter createPresenter() {
        return new WuGPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wug_goods;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(RefreshWuGEvent event) {
        if(event.isFlag() && isFragmentVisible()){
            hotGoodsBeanList.clear();
            page =1;
            mPresenter.getActiveSectionGoods(isScheduleOpen, scheduleId, dataId, ActivityType.ACTIVITY_WUG, MyApplication.userId, page, limit);
        }
    }

    @Override
    protected void loadLazyData() {
        mPresenter.getActiveSectionGoods(isScheduleOpen, scheduleId, dataId, ActivityType.ACTIVITY_WUG, MyApplication.userId, page, limit);
        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleView.addItemDecoration(new SpacesItemDecoration(30));
        hotGoodsBeanList = new ArrayList<>();
        hotRankAdapter = new WugGoodesAdapter(mContext, hotGoodsBeanList, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if(position  < hotGoodsBeanList.size()){//防止数组越界
                    Intent intent = new Intent(mContext, WuGGoodsDetailActivity.class);
                    intent.putExtra("scheduleId", scheduleId);
                    intent.putExtra(WuGGoodsDetailActivity.ACTIVITY_GOODS_ID,hotGoodsBeanList.get(position).getActivityGoodsId());
                    startActivity(intent);
                }
            }
        });
        hotRankAdapter.setStart(isStart);
        recycleView.setAdapter(hotRankAdapter);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if(isHasMore){
                page += 1;
                mPresenter.getActiveSectionGoods(isScheduleOpen, scheduleId, dataId, ActivityType.ACTIVITY_WUG, MyApplication.userId, page, limit);
            }
        });
    }


    @Override
    public void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if(model.getData() != null && model.getData().getResult() != null && model.getData().getResult().size() != 0){
            rlEmpty.setVisibility(View.GONE);
            recycleView.setVisibility(View.VISIBLE);
            activityId = model.getData().getResult().get(0).getActivityId();
            hotGoodsBeanList.addAll(model.getData().getResult());
            hotRankAdapter.notifyDataSetChanged();
            if(model.getData().getResult() != null && model.getData().getResult().size() != 0){
                String activityId = model.getData().getResult().get(0).getActivityId();
                EventBus.getDefault().post(new EventBusMessage(activityId));
            }
            if(model.getData().getResult().size() < limit){
                isHasMore = false;
                refreshLayout.setEnableLoadMore(false);
            }else {
                isHasMore = true;
                refreshLayout.setEnableLoadMore(true);
            }
        }else {
            if(page == 1){
                rlEmpty.setVisibility(View.VISIBLE);
                recycleView.setVisibility(View.GONE);
            }
            isHasMore = false;
            refreshLayout.setEnableLoadMore(false);
        }
    }
}
