package com.xk.mall.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.flyco.tablayout.SlidingTabLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.view.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName RankFragment
 * @Description 首页热排行页面
 * @Author 卿凯
 * @Date 2019/8/17/004
 * @Version V1.0
 */
public class RankFragment extends BaseFragment {

    @BindView(R.id.tablayout)
    SlidingTabLayout tablayout;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    private String [] titles={"爆品榜","热推榜","喜赚榜"};

    public static RankFragment getInstance() {
        RankFragment sf = new RankFragment();
        return sf;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_ranking;
    }

    @Override
    protected void initData() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f)
                .init();
        ImmersionBar.with(this)
                .titleBar(myToolbar) //可以为任意view，如果是自定义xml实现标题栏的话，标题栏根节点不能为RelativeLayout或者ConstraintLayout，以及其子类
                .init();
    }

    @Override
    protected void loadLazyData() {
        List<Fragment> fragmentList=new ArrayList<>();
        List<String> listTitle = Arrays.asList(titles);

        fragmentList.add(HotRankFragment.getInstance(1));
        fragmentList.add(HotRankFragment.getInstance(2));
        fragmentList.add(HotRankFragment.getInstance(3));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(),fragmentList,listTitle);
        viewPage.setAdapter(viewPagerAdapter);
        viewPage.setOffscreenPageLimit(listTitle.size());
        tablayout.setViewPager(viewPage);
        tablayout.setCurrentTab(0);
    }
}
