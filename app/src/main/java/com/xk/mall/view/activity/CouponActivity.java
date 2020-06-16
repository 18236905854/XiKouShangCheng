package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CouponTotalBean;
import com.xk.mall.model.eventbean.UpdateCouponEvent;
import com.xk.mall.model.impl.CouponMainViewImpl;
import com.xk.mall.presenter.CouponMainPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.adapter.CouponViewPagerAdapter;
import com.xk.mall.view.fragment.CouponFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName CouponActivity
 * Description 我的优惠券页面
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class CouponActivity extends BaseActivity<CouponMainPresenter> implements CouponMainViewImpl {
    @BindView(R.id.tab_coupon)
    SlidingTabLayout tabCoupon;
    @BindView(R.id.vp_coupon)
    ViewPager vpCoupon;
    @BindView(R.id.rl_coupon_total)
    RelativeLayout rlCouponTotal;//优惠券布局
    @BindView(R.id.tv_coupon_last)
    TextView tvCouponTotal;//优惠券可用余额
    @BindView(R.id.tv_coupon_universal_amount)
    TextView tvUniversalAmount;//通用寄卖额度
    public int feeRates = 0;//优惠券费率

    @Override
    protected CouponMainPresenter createPresenter() {
        return new CouponMainPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("我的优惠券");
        setRightDrawable(R.drawable.ic_coupon_question);
        setOnRightIconClickListener(v -> {
            //跳转使用说明
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(Constant.INTENT_URL, Constant.couponProblem);
            intent.putExtra(Constant.INTENT_TITLE, "使用说明");
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        mPresenter.getCouponTotal(MyApplication.userId);
        List<String> titles = new ArrayList<>();
        titles.add("可使用");
        titles.add("已使用");
        titles.add("已失效");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(CouponFragment.getInstance(0));
        fragments.add(CouponFragment.getInstance(1));
        fragments.add(CouponFragment.getInstance(2));

        CouponViewPagerAdapter couponViewPagerAdapter = new CouponViewPagerAdapter(getSupportFragmentManager(),
                fragments, titles);
        vpCoupon.setAdapter(couponViewPagerAdapter);
        vpCoupon.setOffscreenPageLimit(titles.size());
        tabCoupon.setViewPager(vpCoupon);
        tabCoupon.setCurrentTab(0);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    //转赠成功之后发送消息，更新优惠券数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCoupon(UpdateCouponEvent updateCouponEvent){
        mPresenter.getCouponTotal(MyApplication.userId);
    }

    @Override
    public void onGetDataSuccess(BaseModel<CouponTotalBean> model) {
        if(model != null && model.getData() != null){
            rlCouponTotal.setVisibility(View.VISIBLE);
            feeRates = model.getData().getFeeRates();
//            tvCouponTotal.setText("优惠券总额：" + PriceUtil.divideCoupon(model.getData().getCouponSumNum()) + "  " +
//                    "可用优惠券：" + PriceUtil.divideCoupon(model.getData().getCouponUsableSumNum()));
            tvCouponTotal.setText(PriceUtil.divideCoupon(model.getData().getCouponSumNum()) + "券");
            tvUniversalAmount.setText(PriceUtil.divideCoupon(model.getData().getBuyGiftAmount()) + "券");
        }else {
            rlCouponTotal.setVisibility(View.GONE);
        }
    }
}
