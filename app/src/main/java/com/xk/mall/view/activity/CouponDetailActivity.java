package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.kennyc.view.MultiStateView;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.CouponDetailBean;
import com.xk.mall.model.entity.CouponDetailRecordBean;
import com.xk.mall.model.eventbean.UpdateCouponEvent;
import com.xk.mall.model.impl.CouponDetailViewImpl;
import com.xk.mall.presenter.CouponDetailPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.CouponUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.CommomDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName CouponDetailActivity
 * Description 优惠券详情页面
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class CouponDetailActivity extends BaseActivity<CouponDetailPresenter> implements CouponDetailViewImpl {
    @BindView(R.id.tv_coupon_item_money)
    TextView tvCouponItemMoney;//使用金额
    @BindView(R.id.tv_coupon_item_sum_money)
    TextView tvCouponItemSumMoney;//总金额
    @BindView(R.id.tv_coupon_item_time)
    TextView tvCouponItemTime;//时间
    @BindView(R.id.rv_coupon_detail)
    RecyclerView rvCouponDetail;
    @BindView(R.id.state_view_coupon_record)
    MultiStateView multiStateView;
    @BindView(R.id.tv_empty_text)
    TextView tvEmpty;//为空时显示的文字
    @BindView(R.id.tv_coupon_give_away)
    TextView tvCouponGiveAway;//转赠按钮

    /**优惠券页面传递过来的优惠券ID的Key*/
    public static String COUPON_ID = "coupon_id";
    public static String COUPON_TYPE = "coupon_type";//优惠券类型，可用，已用
    public static String OUT_BALANCE = "out_balance";//可转出金额
    private int couponBalance = 0;//优惠券余额
    private int feeRates;//转赠优惠券费率
    private String couponId;//优惠券ID
    private int transferOutBalance;//可转出金额

    @Override
    protected CouponDetailPresenter createPresenter() {
        return new CouponDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle(getResources().getString(R.string.coupon_detail_title));

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        couponId = intent.getStringExtra(COUPON_ID);
        int type = intent.getIntExtra(COUPON_TYPE, 1);
        transferOutBalance = intent.getIntExtra(OUT_BALANCE, 0);
//        if(type == 0){//可用显示转赠
//            tvCouponGiveAway.setVisibility(View.VISIBLE);
//        }else {
            tvCouponGiveAway.setVisibility(View.GONE);
//        }
        mPresenter.getCouponDetail(couponId);
    }

    @OnClick(R.id.tv_coupon_give_away)
    public void clickGiveAway(){//点击转赠按钮
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
        intent.putExtra(TransferCouponActivity.BALANCE, transferOutBalance);
        intent.putExtra(TransferCouponActivity.FEE_RATES, feeRates);
        intent.putExtra(TransferCouponActivity.COUPON_ID, couponId);
        ActivityUtils.startActivity(intent);
    }

    /**
     * 获取优惠券使用明细成功
     */
    @Override
    public void onCouponDetailSuccess(BaseModel<CouponDetailBean> model) {
        if(model.getData() != null){
            bindCouponDetail(model.getData());
        }
    }

    //转赠成功之后发送消息，更新优惠券数据
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCoupon(UpdateCouponEvent updateCouponEvent){
        mPresenter.getCouponDetail(couponId);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    /**
     * 绑定数据
     */
    private void bindCouponDetail(CouponDetailBean couponDetailBean){
        tvCouponItemMoney.setText("" + PriceUtil.divideCoupon(couponDetailBean.balance));
        couponBalance = couponDetailBean.balance;
        feeRates = couponDetailBean.feeRates;
        tvCouponItemSumMoney.setText("" + PriceUtil.divideCoupon(couponDetailBean.total));
        String startTime = TimeUtils.date2String(TimeUtils.string2Date(couponDetailBean.startTime), new SimpleDateFormat("yyyy/MM/dd"));
        String endTime = TimeUtils.date2String(TimeUtils.string2Date(couponDetailBean.endTime), new SimpleDateFormat("yyyy/MM/dd"));
        tvCouponItemTime.setText(startTime + " -- " + endTime);

        if(couponDetailBean.recordModelList != null && couponDetailBean.recordModelList.size() != 0){
            //设置adapter
            multiStateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            CouponDetailAdapter detailAdapter = new CouponDetailAdapter(mContext, R.layout.item_redbag_detail, couponDetailBean.recordModelList);
            rvCouponDetail.setLayoutManager(new LinearLayoutManager(mContext));
            rvCouponDetail.setAdapter(detailAdapter);
        }else {
            tvEmpty.setText("没有使用记录");
            multiStateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    /**
     * 使用记录的adapter
     */
    class CouponDetailAdapter extends CommonAdapter<CouponDetailRecordBean>{

        public CouponDetailAdapter(Context context, int layoutId, List<CouponDetailRecordBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, CouponDetailRecordBean couponDetailRecordBean, int position) {
            holder.setImageResource(R.id.img_status, R.drawable.coupon_detail_icon);
            holder.setText(R.id.tv_title, "订单号 " + couponDetailRecordBean.primaryKey);//订单号
            String activity = CouponUtil.getActivityNameByMouldId(couponDetailRecordBean.moduleId);
            holder.setText(R.id.tv_content, activity);//全球买手，活动
            if(!XiKouUtils.isNullOrEmpty(couponDetailRecordBean.transferToUser)){
                holder.setText(R.id.tv_content, "转赠(" + couponDetailRecordBean.transferToUser + ")");//全球买手，活动
                holder.setText(R.id.tv_handling_fee, "服务费" + PriceUtil.divideCoupon(couponDetailRecordBean.transferFee));
            }
            holder.setText(R.id.tv_time, couponDetailRecordBean.createTime.replaceAll("-", "/"));//全球买手，活动
            holder.setText(R.id.tv_money, "-" + PriceUtil.divideCoupon(couponDetailRecordBean.cost));//全球买手，活动

        }
    }
}
