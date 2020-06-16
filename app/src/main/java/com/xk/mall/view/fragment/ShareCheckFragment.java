package com.xk.mall.view.fragment;

import android.support.v7.widget.RecyclerView;

import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;

import butterknife.BindView;

/**
 * @ClassName: ShareCheckFragment
 * @Description: 分享审核子页面
 * @Author: 卿凯
 * @Date: 2019/12/7/007 16:37
 * @Version: 1.0
 */
public class ShareCheckFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recycler_view;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadLazyData() {

    }
}
