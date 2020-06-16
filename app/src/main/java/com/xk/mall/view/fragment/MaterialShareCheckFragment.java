package com.xk.mall.view.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ResultPageBean;
import com.xk.mall.model.entity.ShareCheckBean;
import com.xk.mall.model.impl.MaterialShareCheckImpl;
import com.xk.mall.presenter.MaterialShareCheckPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.adapter.MaterialShareCheckAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 喜口素材 fragment
 */
public class MaterialShareCheckFragment extends BaseFragment<MaterialShareCheckPresenter> implements MaterialShareCheckImpl {
    private static final String TAG = "MaterialShareCheckFragment";
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.state_view_order)
    MultiStateView multiStateView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private String dataId = "";//用来请求数据的ID
    private int page = 1;
    private List<ShareCheckBean> shareCheckBeans;
    private MaterialShareCheckAdapter shareCheckAdapter;

    public static MaterialShareCheckFragment getInstance(String id) {
        MaterialShareCheckFragment fragment = new MaterialShareCheckFragment();
        fragment.dataId = id;
        return fragment;
    }

    @Override
    protected MaterialShareCheckPresenter createPresenter() {
        return new MaterialShareCheckPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_material;
    }

    @Override
    protected void initData() {
        View view = multiStateView.getView(MultiStateView.VIEW_STATE_EMPTY);
        TextView tvEmptyText = view.findViewById(R.id.tv_empty_text);
        tvEmptyText.setText("暂无上传记录");
    }

    @Override
    protected void loadLazyData() {
        String phone = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        String nickName = SPUtils.getInstance().getString(Constant.NICK_NAME);
        shareCheckBeans = new ArrayList<>();
        shareCheckAdapter = new MaterialShareCheckAdapter(mContext, R.layout.item_share_check, shareCheckBeans);
        recycleView.setLayoutManager(new LinearLayoutManager(mContext));
        recycleView.setAdapter(shareCheckAdapter);
        mPresenter.getData(phone, nickName, dataId, page, Constant.limit);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getData(phone, nickName, dataId, page, Constant.limit);
        });
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getData(phone, nickName, dataId, page, Constant.limit);
        });
    }

    @Override
    public void onGetDataSuccess(BaseModel<ResultPageBean<ShareCheckBean>> model) {
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
        if(model != null && model.getData() != null && model.getData().getResult() != null){
            if(page == 1){
                shareCheckBeans.clear();
            }
            shareCheckBeans.addAll(model.getData().getResult());
            shareCheckAdapter.notifyDataSetChanged();
            if(model.getData().getResult().size() < Constant.limit){
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
