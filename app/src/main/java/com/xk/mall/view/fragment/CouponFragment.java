package com.xk.mall.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.model.entity.CouponBean;
import com.xk.mall.model.entity.CouponListBean;
import com.xk.mall.model.eventbean.UpdateCouponEvent;
import com.xk.mall.model.impl.CouponViewImpl;
import com.xk.mall.presenter.CouponPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.activity.CouponActivity;
import com.xk.mall.view.activity.CouponDetailActivity;
import com.xk.mall.view.activity.RealNameActivity;
import com.xk.mall.view.activity.SetPayPwdOneActivity;
import com.xk.mall.view.activity.TransferCouponActivity;
import com.xk.mall.view.adapter.CouponAdapter;
import com.xk.mall.view.widget.CommomDialog;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName CouponFragment
 * Description 优惠券Fragment
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class CouponFragment extends BaseFragment<CouponPresenter> implements CouponViewImpl {
    @BindView(R.id.rv_coupon)
    RecyclerView rvCoupon;
    @BindView(R.id.state_view_coupon)
    MultiStateView multiStateView;
    @BindView(R.id.refreshCoupon)
    SmartRefreshLayout refreshLayout;

    private int type = 0;//优惠券类型  0:可使用  1:已使用  2:已失效
    private List<CouponBean> couponBeanList;
    private CouponAdapter couponAdapter;
    private int page = 1;//当前页数
    private int feeRates = 0;//优惠券费率

    public static CouponFragment getInstance(int type) {
        CouponFragment couponFragment = new CouponFragment();
        couponFragment.type = type;
        return couponFragment;
    }

    @Override
    protected CouponPresenter createPresenter() {
        return new CouponPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_coupon;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    //转赠成功之后发送消息，更新优惠券数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCoupon(UpdateCouponEvent updateCouponEvent){
        CouponBean newCouponBean = null;
        for(int i = 0; i < couponBeanList.size(); i++){
            CouponBean couponBean = couponBeanList.get(i);
            if(couponBean.id.equals(updateCouponEvent.getCouponId())){
                couponBean.balance = updateCouponEvent.getAmount();
                newCouponBean = couponBean;
            }
        }
        if(newCouponBean != null && newCouponBean.balance <= 0){
            couponBeanList.remove(newCouponBean);
        }
        couponAdapter.notifyDataSetChanged();
    }

    @Override
    protected void loadLazyData() {
        rvCoupon.setLayoutManager(new LinearLayoutManager(mContext));
        couponBeanList = new ArrayList<>();
        if(type == 0){
            couponAdapter = new CouponAdapter(mContext, R.layout.coupon_item, couponBeanList);
            couponAdapter.setType(0);
            couponAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    //点击跳转优惠券详情
                    Intent intent = new Intent(mContext, CouponDetailActivity.class);
                    intent.putExtra(CouponDetailActivity.COUPON_ID, couponBeanList.get(position).id);
                    intent.putExtra(CouponDetailActivity.COUPON_TYPE, type);
                    intent.putExtra(CouponDetailActivity.OUT_BALANCE, couponBeanList.get(position).transferOutBalance);
                    ActivityUtils.startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }else if(type == 1){
            couponAdapter = new CouponAdapter(mContext, R.layout.lost_coupon_item, couponBeanList);
            couponAdapter.setType(1);
            couponAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    //点击跳转优惠券详情
                    Intent intent = new Intent(mContext, CouponDetailActivity.class);
                    intent.putExtra(CouponDetailActivity.COUPON_ID, couponBeanList.get(position).id);
                    ActivityUtils.startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }else if(type == 2){
            couponAdapter = new CouponAdapter(mContext, R.layout.lost_coupon_item, couponBeanList);
            couponAdapter.setType(2);
            couponAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                    //点击跳转优惠券详情
                    Intent intent = new Intent(mContext, CouponDetailActivity.class);
                    intent.putExtra(CouponDetailActivity.COUPON_ID, couponBeanList.get(position).id);
                    ActivityUtils.startActivity(intent);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }
        couponAdapter.setRvButtonListener(new RvButtonListener() {
            @Override
            public void onItemClick(View view, int position) {
                CouponActivity couponActivity = (CouponActivity) getActivity();
                if(couponActivity != null){
                    feeRates = couponActivity.feeRates;
                }
                CouponBean couponBean = couponBeanList.get(position);
                int isRealName = SPUtils.getInstance().getInt(Constant.USER_REAL_NAME, 0);
                int isSetPwd = SPUtils.getInstance().getInt(Constant.USER_SET_PWD, 0);
                if(isRealName != 1){
                    new CommomDialog(mContext, R.style.mydialog, "请先实名认证", (dialog, confirm) -> {
                        if (confirm) {
                            ActivityUtils.startActivity(RealNameActivity.class);
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }
                if(isSetPwd != 1){
                    new CommomDialog(mContext, R.style.mydialog, "请先设置支付密码", (dialog, confirm) -> {
                        if (confirm) {
                            ActivityUtils.startActivity(SetPayPwdOneActivity.class);
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }
                Intent intent = new Intent(mContext, TransferCouponActivity.class);
//                //计算手续费向上取整
//                double result = couponBean.balance * feeRates / 100.0;
//                result = Math.ceil(result);
//                int newFate = Double.valueOf(result).intValue();
//                Double fateCeil = Math.ceil(newFate / 100.0);
//                Double newDoubleBalance = Math.floor(couponBean.balance * 1.0/(1 + feeRates/100.0));
//                int newIntBalance = newDoubleBalance.intValue();
//                newIntBalance = (int) (Math.floor(newIntBalance / 100.0) * 100);
//                LogUtils.e("newFate=" + newFate + ":newBalance=" + newIntBalance);
                intent.putExtra(TransferCouponActivity.BALANCE, couponBean.transferOutBalance);
                intent.putExtra(TransferCouponActivity.FEE_RATES, feeRates);
                intent.putExtra(TransferCouponActivity.COUPON_ID, couponBean.id);
                ActivityUtils.startActivity(intent);
            }
        });
        rvCoupon.setAdapter(couponAdapter);
        getData();
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            getData();
        });

        refreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            getData();
        });
    }


    private void getData(){
        int limit = Constant.limit;
        String userID = MyApplication.userId;
        if(type == 0){
            mPresenter.getUsableCoupon(userID, page, limit);
        }else if(type == 1){
            mPresenter.getUsedCoupon(userID, page, limit);
        }else if(type == 2){
            mPresenter.getLostCoupon(userID, page, limit);
        }
    }

    @Override
    public void onCouponSuccess(BaseModel<CouponListBean> model) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if(page == 1){
            couponBeanList.clear();
            if(model.getData().result == null || model.getData().result.size() == 0){
                multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }else {
                couponBeanList.addAll(model.getData().result);
                if(model.getData().result.size() < Constant.limit){
                    refreshLayout.setEnableLoadMore(false);
                }else {
                    refreshLayout.setEnableLoadMore(true);
                }
            }
        }else {
            couponBeanList.addAll(model.getData().result);
            if(model.getData().result.size() < Constant.limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }
        }
        couponAdapter.notifyDataSetChanged();
    }


}
