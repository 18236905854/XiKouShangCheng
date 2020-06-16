package com.xk.mall.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.model.entity.DesignerBean;
import com.xk.mall.model.impl.AttentionViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;
import com.xk.mall.presenter.AttentionPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.activity.DesignerActivity;
import com.xk.mall.view.adapter.AttentionAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ClassName AttentionFragment
 * Description 设计师 关注页面
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public class AttentionFragment extends BaseFragment<AttentionPresenter> implements AttentionViewImpl {
    private static final String TAG = "AttentionFragment";
    @BindView(R.id.refresh_attention)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.state_view_attention)
    MultiStateView llAttention;//当前页面的父布局
    @BindView(R.id.rv_attention)
    RecyclerView rvAttention;


    private List<DesignerBean.ResultBean> listData;
    private AttentionAdapter attentionAdapter;
    private int page = 1;//当前页面
    private int limit = Constant.limit;//当前页面的条数
    private int cancelPos = 0;//取消关注的位置
    private boolean hasMore = true;//是否有更多

    public static AttentionFragment getInstance() {
        AttentionFragment fragment = new AttentionFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attention;
    }

    @Override
    protected void initData() {

    }



    @Override
    protected AttentionPresenter createPresenter() {
        return new AttentionPresenter(this);
    }

    @Override
    protected void loadLazyData() {
        rvAttention.setLayoutManager(new LinearLayoutManager(mContext));
        listData = new ArrayList<>();
        attentionAdapter = new AttentionAdapter(mContext, R.layout.attention_item, listData);
        rvAttention.setAdapter(attentionAdapter);
        getData();
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            listData.clear();
            page = 1;
            getData();
        });

        mRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
            if (hasMore) {
                page = page + 1;
                getData();
            } else {
                mRefreshLayout.finishLoadMore();
//                ToastUtils.showShortToast(getActivity(), "没有更多数据了");
            }
        });

        attentionAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //跳转设计师主页
                Intent intent = new Intent(mContext, DesignerActivity.class);
                intent.putExtra(DesignerActivity.DESIGNER_ID, listData.get(position).getId());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        attentionAdapter.setRvButtonListener(new RvButtonListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.e(TAG, "onItemClick: " + position);
                cancelPos = position;
                AttentionRequestBody requestBody = new AttentionRequestBody();
                requestBody.setUserId(MyApplication.userId);
                requestBody.setDesignerId(listData.get(position).getId());
                requestBody.setOperationType(Constant.CANCEL);
                mPresenter.addAndCancelAttention(requestBody);
            }
        });
    }

    private void getData() {
        mPresenter.getAttentionDesignerList(MyApplication.userId, page, limit);

    }


    /**
     * 取消关注成功的回调
     */
    @Override
    public void cancelAttentionSuccess(BaseModel baseModel) {
        listData.remove(cancelPos);
        attentionAdapter.notifyItemRemoved(cancelPos);
        attentionAdapter.notifyDataSetChanged();
        if(listData.size()==0){
            Logger.e("显示空布局");
            llAttention.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
        ToastUtils.showShortToast(getActivity(), "取消关注成功");
    }

    /**
     * 获取我的关注List成功的回调
     */
    @Override
    public void onAttentionSuccess(BaseModel<DesignerBean> entity) {
//        listData.addAll(entity.getData().getResult());
        Log.e(TAG, "onAttentionSuccess: " + entity.getData().getResult().size());
        if(page == 1){
            listData.clear();
            if(entity.getData().getResult() == null || entity.getData().getResult().size() == 0){
                llAttention.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }else {
                listData.addAll(entity.getData().getResult());
            }
        }else {
            listData.addAll(entity.getData().getResult());
        }

        if(entity.getData().getResult().size() < limit){//当返回的数据小于请求的条数时，说明没有更多数据了
            hasMore = false;
        }
        attentionAdapter.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }

}
