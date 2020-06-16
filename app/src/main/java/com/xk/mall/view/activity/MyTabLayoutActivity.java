package com.xk.mall.view.activity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.view.adapter.ManyBuyViewPagerAdapter;
import com.xk.mall.view.fragment.MyTabFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTabLayoutActivity extends BaseActivity {


    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private String [] titles={ "10:00","11:00","12:00","13:00","14:00","15:00"};
    private String [] childTitles={"已结束","已开启","未开启","未开启","未开启","未开启"};
    private List<String> listTitle=new ArrayList<>();
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_tablayout;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        initTabLayout();
    }

    private void initTabLayout(){
        List<Fragment>  listFragments=new ArrayList<>();
        for(int i=0;i<titles.length;i++){
            listTitle.add(titles[i]);
            listFragments.add(MyTabFragment.getInstance(i));
        }

        ManyBuyViewPagerAdapter manyBuyViewPagerAdapter = new ManyBuyViewPagerAdapter(getSupportFragmentManager(), listFragments, listTitle);
        viewPager.setAdapter(manyBuyViewPagerAdapter);
        viewPager.setOffscreenPageLimit(titles.length);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

    }

    private void setupTabIcons() {
        //去除点击黑色阴影效果
        tabLayout.setTabRippleColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        for(int i=0;i<titles.length;i++){
            TabLayout.Tab tabAt = tabLayout.getTabAt(i);
            tabLayout.getTabAt(i).setCustomView(getTabView(i));
            if(i==0){
                // 设置第一个tab的TextView是被选择的样式
                tabAt.getCustomView().findViewById(R.id.tv_title).setSelected(true);//第一个tab被选中
                tabAt.getCustomView().findViewById(R.id.tv_child_title).setSelected(true);//第一个tab被选中
                ((TextView)tabAt.getCustomView().findViewById(R.id.tv_title)).setTextColor(Color.parseColor("#444444"));
                ((TextView)tabAt.getCustomView().findViewById(R.id.tv_child_title)).setTextColor(Color.parseColor("#444444"));
                ((View)tabAt.getCustomView().findViewById(R.id.tab_item_indicator)).setVisibility(View.VISIBLE);
            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                updateTabView(tab,true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                updateTabView(tab,false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    /**
     *  用来改变tabLayout选中后的字体大小及颜色
     * @param tab
     * @param isSelect
     */
    private void updateTabView(TabLayout.Tab tab, boolean isSelect) {
        //找到自定义视图的控件ID
        TextView tvTtile = (TextView) tab.getCustomView().findViewById(R.id.tv_title);
        TextView tvChildTitle = (TextView) tab.getCustomView().findViewById(R.id.tv_child_title);
        View viewIndicator =  tab.getCustomView().findViewById(R.id.tab_item_indicator);
        if(isSelect) {
            //设置标签选中
            tvTtile.setSelected(true);
            tvChildTitle.setSelected(true);
//           tvTtile .setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tvTtile.setTextColor(Color.parseColor("#444444"));
            tvChildTitle.setTextColor(Color.parseColor("#444444"));
            viewIndicator.setVisibility(View.VISIBLE);

        }else{
            //设置标签取消选中
            tvTtile.setSelected(false);
            tvChildTitle.setSelected(false);
            //设置不为加粗
//            tvTtile.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tvTtile.setTextColor(Color.parseColor("#CCCCCC"));
            tvChildTitle.setTextColor(Color.parseColor("#CCCCCC"));
            viewIndicator.setVisibility(View.INVISIBLE);
        }
    }

    public View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.item_my_tablayout, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvChilTitle = view.findViewById(R.id.tv_child_title);
        tvTitle.setText(titles[position]);
        tvChilTitle.setText(childTitles[position]);
        return view;
    }
}
