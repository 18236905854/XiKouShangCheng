package com.xk.mall.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.DPayResultPageBean;
import com.xk.mall.model.entity.ZeroOrderTitleBean;
import com.xk.mall.model.eventbean.DPayResultEvent;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.impl.DPayViewImpl;
import com.xk.mall.presenter.DPayPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.adapter.ZeroOrderViewPagerAdapter;
import com.xk.mall.view.fragment.PayFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: DPayActivity
 * @Description: 代付页面
 * @Author: 卿凯
 * @Date: 2019/8/30/030 14:01
 * @Version: 1.0
 */
public class DPayActivity extends BaseActivity<DPayPresenter> implements DPayViewImpl {
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.tab_pay)
    SlidingTabLayout tabPay;
    @BindView(R.id.vp_pay_order)
    ViewPager vpPayOrder;
    private int page = 1;

    @Override
    protected DPayPresenter createPresenter() {
        return new DPayPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_d_pay;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("我的代付");
    }

    @Override
    protected void initData() {
        setShowDialog(false);
        String account = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        mPresenter.getData(account, 1, page, Constant.limit);
        List<ZeroOrderTitleBean> titles = new ArrayList<>();
        titles.add(new ZeroOrderTitleBean("未支付", 1));
        titles.add(new ZeroOrderTitleBean("已支付", 5));
        List<Fragment> fragments = new ArrayList<>();
        for(ZeroOrderTitleBean titleBean : titles){
            fragments.add(PayFragment.getInstance(titleBean.orderType));
        }
        ZeroOrderViewPagerAdapter viewPagerAdapter = new ZeroOrderViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        vpPayOrder.setOffscreenPageLimit(titles.size());
        vpPayOrder.setAdapter(viewPagerAdapter);
        tabPay.setViewPager(vpPayOrder);
        tabPay.setCurrentTab(0);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOtherPaySuccess(PaySuccessBean otherPaySuccessBean){
        String account = SPUtils.getInstance().getString(Constant.USER_MOBILE);
        mPresenter.getData(account, 1, page, Constant.limit);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetDPayMoney(DPayResultEvent resultEvent){
        if(resultEvent != null){
            int hasPaid = resultEvent.getHasPaid();
            int noPaid = resultEvent.getNotPaid();
            tvPayMoney.setText("您已代付金额:" + PriceUtil.dividePrice(hasPaid) + "元，还剩" + PriceUtil.dividePrice(noPaid) + "元未代付");
        }
    }

    @Override
    public void onGetDataSuccess(BaseModel<DPayResultPageBean> model) {
        if(model != null && model.getData() != null && model.getData().getResult() != null && model.getData().getResult().size() != 0){
            //绑定数据
            DPayResultPageBean.ResultBean listBean = model.getData().getResult().get(0);
            int hasPaid = listBean.getHasPaid();
            int noPaid = listBean.getNotPaid();
            tvPayMoney.setText("您已代付金额:" + PriceUtil.dividePrice(hasPaid) + "元，还剩" + PriceUtil.dividePrice(noPaid) + "元未代付");

        }else {
            //显示空布局
        }
    }
}
