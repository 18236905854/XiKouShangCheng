package com.xk.mall.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.view.adapter.ViewPagerAdapter;
import com.xk.mall.view.fragment.HotRankFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName HotRankingActivity
 * Description 热排行
 * Author
 * Date
 * Version V1.0
 */
public class HotRankingActivity extends BaseActivity {
    private static final String TAG = "HotRankingActivity";
    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    private String [] titles={"爆品榜","热推榜","喜赚榜"};

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hot_ranking;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
       myToolbar.setNavigationOnClickListener(v -> {
           finish();
       });
    }

    @Override
    protected void initData() {
        List<Fragment> fragmentList=new ArrayList<>();
        List<String> listTitle = Arrays.asList(titles);

        fragmentList.add(HotRankFragment.getInstance(1));
        fragmentList.add(HotRankFragment.getInstance(2));
        fragmentList.add(HotRankFragment.getInstance(3));

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragmentList,listTitle);
        viewPage.setAdapter(viewPagerAdapter);
        viewPage.setOffscreenPageLimit(listTitle.size());
        tablayout.setViewPager(viewPage);
        tablayout.setCurrentTab(0);
    }

}
