package com.xk.mall.view.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;

import butterknife.BindView;

/**
 * ClassName AboutActivity
 * Description 关于页面
 * Author 卿凯
 * Date 2019/6/10/010
 * Version V1.0
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.tv_about)
    TextView tvAbout;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle(getString(R.string.about_title));
    }

    @Override
    protected void initData() {
    }
}
