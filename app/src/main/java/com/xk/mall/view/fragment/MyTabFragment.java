package com.xk.mall.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public class MyTabFragment extends BaseFragment {

    private static final String TYPE ="type" ;
    @BindView(R.id.tv_position)
    TextView tvPosition;

    private int rankType;
    //回调用来接收参数
    public static MyTabFragment getInstance(int id) {
        MyTabFragment fragment = new MyTabFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        rankType = arguments.getInt(TYPE);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void loadLazyData() {
        tvPosition.setText("第几个"+rankType);
    }

}
