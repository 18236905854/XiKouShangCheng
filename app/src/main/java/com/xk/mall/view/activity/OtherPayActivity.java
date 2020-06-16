package com.xk.mall.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.OtherPayResultBean;
import com.xk.mall.model.eventbean.OtherPaySuccessBean;
import com.xk.mall.model.impl.OtherPayViewImpl;
import com.xk.mall.presenter.OtherPayPresenter;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.OrderTipDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName OtherPayActivity
 * Description 发起代付页面
 * Author 卿凯
 * Date 2019/7/1/001
 * Version V1.0
 */
public class OtherPayActivity extends BaseActivity<OtherPayPresenter> implements OtherPayViewImpl {
    @BindView(R.id.tv_other_pay_money)
    TextView tvOtherPayMoney;//代付金额
    @BindView(R.id.tv_other_pay_order)
    TextView tvOtherPayOrder;//订单号
    @BindView(R.id.tv_other_pay_goods_name)
    TextView tvOtherPayGoodsName;//商品名
    @BindView(R.id.tv_other_pay_person)
    TextView tvOtherPayPerson;//合伙人ID
    @BindView(R.id.rl_other_pay_person)
    RelativeLayout rlOtherPayPerson;
    @BindView(R.id.rl_other_pay_share)
    TextView rlOtherPayShare;//确定按钮
    @BindView(R.id.cb_wug_goods_xiadan)
    CheckBox cbPay;//合伙人代付
    @BindView(R.id.cb_wug_goods_xiadan_two)
    CheckBox cbFriendPay;//朋友代付
    @BindView(R.id.rl_wug_xiadan_other_pay_two)
    RelativeLayout rlFriendPay;//点击跳转页面
    @BindView(R.id.tv_wug_xiadan_friend_phone)
    TextView tvFriendPhone;//朋友手机号

    /**intent传递过来的商品名称的key*/
    public static final String GOODS_NAME = "goods_name";
    /**intent传递过来的订单总价的key*/
    public static final String TOTAL_PRICE = "total_price";
    /**intent传递过来的订单号的key*/
    public static final String ORDER_NUMBER = "order_number";
    /**intent传递过来的活动类型的key*/
    public static final String ORDER_TYPE = "order_type";
    /**intent传递过来的代付人ID的key*/
    public static final String PAYMENT_ID = "payment_id";
    private String orderNo;//订单号
    private int orderType;//订单类型
    private boolean isOtherPay = true;//合伙人代付
    private boolean isFriendPay;//好友代付

    @Override
    protected OtherPayPresenter createPresenter() {
        return new OtherPayPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_pay;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("发起代付");
        setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setLeftDrawable(R.drawable.ic_back_white);
        setStatusBar(Color.parseColor("#444444"));
        setDarkStatusIcon(false);//状态栏图标反色
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        int money = intent.getIntExtra(TOTAL_PRICE, 0);
        tvOtherPayMoney.setText(String.valueOf(PriceUtil.dividePrice(money)));
        orderNo = intent.getStringExtra(ORDER_NUMBER);
        orderType = intent.getIntExtra(ORDER_TYPE, 0);
        String goodsName = intent.getStringExtra(GOODS_NAME);
        String payment_id = intent.getStringExtra(PAYMENT_ID);
        if(payment_id != null){
            tvOtherPayPerson.setText(payment_id);
        }
        if(orderNo != null){
            tvOtherPayOrder.setText(orderNo);
        }
        if(goodsName != null){
            tvOtherPayGoodsName.setText(goodsName);
        }
    }

    @OnClick({R.id.rl_other_pay_share, R.id.cb_wug_goods_xiadan, R.id.cb_wug_goods_xiadan_two, R.id.rl_wug_xiadan_other_pay_two})
    public void onClickShare(View view){
        switch (view.getId()){
            case R.id.rl_other_pay_share:
                //分享到微信
                //显示对话框
                if(!isFriendPay && !isOtherPay){
                    ToastUtils.showShortToast(mContext, "请选择代付人");
                    return;
                }
                if(isOtherPay){
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "你想让合伙人帮你代付吗？", "确定", (dialog, confirm) -> {
                        if(confirm){
                            //确定申请代付
                            mPresenter.goOtherPay(orderNo, orderType, MyApplication.userId, "");
                        }
                    }).show();
                    return;
                }
                if(isFriendPay && !XiKouUtils.isNullOrEmpty(tvFriendPhone.getText().toString().trim())){
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", "你想让好友帮你代付吗？", "确定", (dialog, confirm) -> {
                        if(confirm){
                            //确定申请代付
                            mPresenter.goOtherPay(orderNo, orderType, MyApplication.userId, tvFriendPhone.getText().toString());
                        }
                    }).show();
                }else {
                    ToastUtils.showShortToast(mContext, "请输入为您代付的喜扣好友");
                }
                break;

            case R.id.cb_wug_goods_xiadan://合伙人代付
                if(isOtherPay){
                    cbPay.setChecked(false);
                    isOtherPay = false;
                }else {
                    cbPay.setChecked(true);
                    isOtherPay = true;
                    cbFriendPay.setChecked(false);
                    isFriendPay = false;
                }
                break;
            case R.id.cb_wug_goods_xiadan_two://好友代付
                if(isFriendPay){
                    cbFriendPay.setChecked(false);
                    isFriendPay = false;
                }else {
                    cbFriendPay.setChecked(true);
                    isFriendPay = true;
                    cbPay.setChecked(false);
                    isOtherPay = false;
                }
                break;

            case R.id.rl_wug_xiadan_other_pay_two:
                if(isFriendPay){
                    Intent intent1 = new Intent(mContext, SearchFriendActivity.class);
                    intent1.putExtra("phone", tvFriendPhone.getText().toString());
                    startActivityForResult(intent1, 102);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 102 && data != null){
            tvFriendPhone.setText(data.getStringExtra("phone"));
        }
    }

    @Override
    public void onBackPressed() {
        //显示对话框
        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定退出该笔订单代付", "确定", (dialog, confirm) -> {
            if(confirm){
                //确定退出
                finish();
            }
        }).show();
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext, model.getMsg());
    }

    @Override
    public void onOtherPaySuccess(BaseModel<OtherPayResultBean> beanBaseModel) {
        if(isOtherPay){
            ToastUtils.showShortToast(mContext, "已将代付请求发送给您的合伙人");
        }else if(isFriendPay){
            ToastUtils.showShortToast(mContext, "已将代付请求发送给您的好友");
        }
        EventBus.getDefault().post(new OtherPaySuccessBean(orderNo, orderType));
        new Handler().postDelayed(this::finish, 500);
    }
}
