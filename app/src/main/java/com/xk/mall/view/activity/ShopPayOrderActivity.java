package com.xk.mall.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.pingplusplus.android.Pingpp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseContent;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.AliPayResultBean;
import com.xk.mall.model.entity.PayPingResultBean;
import com.xk.mall.model.entity.PayResultBean;
import com.xk.mall.model.impl.PayViewImpl;
import com.xk.mall.presenter.PayPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PayResult;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.WXPayUtils;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.pswkeyboard.widget.PopEnterPassword;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName PayOrderActivity
 * Description 支付订单
 * Author
 * Date
 * Version
 */
public class ShopPayOrderActivity extends BaseActivity<PayPresenter> implements PayViewImpl {


    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;//订单号
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;//订单金额
    @BindView(R.id.tv_gift_coupon)
    TextView tvGiftCoupon;//优惠券
    @BindView(R.id.ck_pay_red)
    CheckBox ckPayRed;
    @BindView(R.id.rl_pay_red)
    RelativeLayout rlPayRed;
    @BindView(R.id.ck_pay_wechat)
    CheckBox ckPayWechat;
    @BindView(R.id.rl_pay_wechat)
    RelativeLayout rlPayWechat;
    @BindView(R.id.ck_pay_zfb)
    CheckBox ckPayZfb;
    @BindView(R.id.rl_pay_zfb)
    RelativeLayout rlPayZfb;
    @BindView(R.id.ck_pay_bank)
    CheckBox ckPayBank;
    @BindView(R.id.rl_pay_bank)
    RelativeLayout rlPayBank;
    @BindView(R.id.ck_pay_friend)
    CheckBox ckPayFriend;
    @BindView(R.id.rl_pay_friend)
    RelativeLayout rlPayFriend;
    @BindView(R.id.btn_pay_order)
    Button btnPayOrder;//支付按钮
    @BindView(R.id.rl_goods_name)
    RelativeLayout rlPayGoodsName;//商品名
    @BindView(R.id.rl_goods_coupon)
    RelativeLayout rlPayGoodsCoupon;//优惠券
    @BindView(R.id.tv_real_money)
    TextView tvRealMoney;//实际支付金额

    /**intent传递过来的订单金额的key*/
    public static String TOTAL_PRICE = "total_price";
    /**intent传递过来的实付金额的key*/
    public static String REAL_PRICE = "real_price";
    /**intent传递过来的订单优惠券的key*/
    public static String COUPON_VALUE = "coupon_value";
    /**intent传递过来的订单号的key*/
    public static String OTO_NO = "oto_no";
    /**intent传递过来的订单类型的key*/
    public static String ORDER_TYPE = "order_type";
    IWXAPI api;
    private String orderNo;//订单号
    private int realPrice;//支付金额
    private int coupon;//优惠券金额
    private int totalPrice;//总计金额

    private static final int SDK_PAY_FLAG = 1;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            if(msg.what == SDK_PAY_FLAG){
                @SuppressWarnings("unchecked")
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                /**
                 * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为9000则代表支付成功
                if (TextUtils.equals(resultStatus, "9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Logger.e("支付成功回调");
                    Intent intent = new Intent(mContext, PayOrderResultActivity.class);
                    intent.putExtra(PayOrderResultActivity.PAY_STATUS, true);
                    intent.putExtra(PayOrderResultActivity.PAY_COUPON,coupon);
                    intent.putExtra(PayOrderResultActivity.PAY_MOUNT,totalPrice);
                    ActivityUtils.startActivity(intent);
                    finish();
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Logger.e("支付失败回调");
                    Intent intent = new Intent(mContext, PayOrderResultActivity.class);
                    intent.putExtra(PayOrderResultActivity.PAY_STATUS, false);
                    ActivityUtils.startActivity(intent);
                    finish();
                }
            }
        }
    };

    @Override
    protected PayPresenter createPresenter() {
        return new PayPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_pay_order;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setText("支付订单");
    }

    @Override
    protected void initData() {
        ckPayRed.setChecked(true);
        api = WXAPIFactory.createWXAPI(this, null);
        // 将应用的appId注册到微信
        api.registerApp(BaseContent.UM_WECHAT_KEY);
        realPrice = getIntent().getIntExtra(REAL_PRICE, 0);
        totalPrice = getIntent().getIntExtra(TOTAL_PRICE, 0);
        coupon = getIntent().getIntExtra(COUPON_VALUE, 0);
        orderNo = getIntent().getStringExtra(OTO_NO);
        tvOrderNumber.setText(orderNo);
        tvGiftCoupon.setText(PriceUtil.divideCoupon(coupon));
        tvOrderPrice.setText(String.valueOf(PriceUtil.dividePrice(totalPrice)));
        tvRealMoney.setText(String.valueOf(PriceUtil.dividePrice(realPrice)));
        btnPayOrder.setText("支付金额 ¥" + PriceUtil.dividePrice(realPrice));
    }

    @Override
    public void onBackPressed() {
        //显示对话框
        new OrderTipDialog(mContext, R.style.mydialog, "提示", "确定退出支付吗?", "确定", (dialog, confirm) -> {
            if(confirm){
                //确定退出
                finish();
            }
        }).show();
    }

    @OnClick({R.id.rl_pay_red, R.id.rl_pay_wechat, R.id.rl_pay_zfb, R.id.rl_pay_bank, R.id.rl_pay_friend, R.id.btn_pay_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_pay_red:
                switchCkBox(rlPayRed);
                break;
            case R.id.rl_pay_wechat:
                switchCkBox(rlPayWechat);
                break;
            case R.id.rl_pay_zfb:
                switchCkBox(rlPayZfb);
                break;
            case R.id.rl_pay_bank:
                switchCkBox(rlPayBank);
                break;
            case R.id.rl_pay_friend:
                switchCkBox(rlPayFriend);
                break;
            case R.id.btn_pay_order:
                if (ckPayRed.isChecked()) {//红包支付
                    showPassWordDialog();
                }else if(ckPayWechat.isChecked()){
                    //微信支付
                    mPresenter.pay(orderNo, MyApplication.userId, OrderType.OTO_TYPE, realPrice, MyApplication.switchBean.isPingxx());
                }else if(ckPayZfb.isChecked()){
                    //支付宝支付
                    mPresenter.goAliPay(orderNo, OrderType.OTO_TYPE, MyApplication.userId, MyApplication.switchBean.isPingxx());
                }
                break;
        }
    }

    private void showPassWordDialog() {
//        PayInputPwdDialog  payInputPwdDialog=new PayInputPwdDialog(mContext);
//        payInputPwdDialog.show();
        PopEnterPassword popEnterPassword = new PopEnterPassword(this);
        // 显示窗口
        popEnterPassword.showAtLocation(this.findViewById(R.id.layoutContent),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

        popEnterPassword.setOnFinishInputListener(new PopEnterPassword.OnFinishInputListener() {
            @Override
            public void onFinish(String input) {
                mPresenter.goRedPay(orderNo, OrderType.OTO_TYPE, input, realPrice, MyApplication.userId);
                popEnterPassword.dismiss();
//                ToastUtils.showShortToast(mContext,"支付成功 ：密碼"+input);
            }
        });

        //重置密码
        popEnterPassword.setOnSetPwdListenner(new PopEnterPassword.OnSetPwdListenner() {
            @Override
            public void OnSetPwd() {
                Intent intent=new Intent(ShopPayOrderActivity.this, SetPayPwdOneActivity.class);
                startActivityForResult(intent,1000);
            }
        });



    }

    private void switchCkBox(View view) {
        switch (view.getId()) {
            case R.id.rl_pay_red:
                ckPayRed.setChecked(true);
                ckPayWechat.setChecked(false);
                ckPayZfb.setChecked(false);
                ckPayBank.setChecked(false);
                ckPayFriend.setChecked(false);
                break;
            case R.id.rl_pay_wechat:
                ckPayRed.setChecked(false);
                ckPayWechat.setChecked(true);
                ckPayZfb.setChecked(false);
                ckPayBank.setChecked(false);
                ckPayFriend.setChecked(false);
                break;
            case R.id.rl_pay_zfb:
                ckPayRed.setChecked(false);
                ckPayWechat.setChecked(false);
                ckPayZfb.setChecked(true);
                ckPayBank.setChecked(false);
                ckPayFriend.setChecked(false);
                break;
            case R.id.rl_pay_bank:
                ckPayRed.setChecked(false);
                ckPayWechat.setChecked(false);
                ckPayZfb.setChecked(false);
                ckPayBank.setChecked(true);
                ckPayFriend.setChecked(false);
                break;
            case R.id.rl_pay_friend:
                ckPayRed.setChecked(false);
                ckPayWechat.setChecked(false);
                ckPayZfb.setChecked(false);
                ckPayBank.setChecked(false);
                ckPayFriend.setChecked(true);
                break;
        }
    }

    @Override
    public void onWXPaySuccess(BaseModel<PayResultBean> model) {
        if(model.getData() != null){
            PayResultBean payResultBean = model.getData();
            pay(payResultBean);
        }else {
            ToastUtils.showShortToast(mContext, "获取微信支付数据出错");
        }
    }

    @Override
    public void onGetAliPayDataSuccess(BaseModel<PayResultBean> model) {
        if(model.getData() != null){
            PayResultBean payResultBean = model.getData();
            if(!XiKouUtils.isNullOrEmpty(payResultBean.getAliPay())){
                goAliPay(payResultBean.getAliPay());
            }else {
                ToastUtils.showShortToast(mContext, "获取支付宝支付数据出错");
            }
        }
    }

    @Override
    public void onPingWXPaySuccess(BaseModel<PayPingResultBean> model) {
        if(model.getData() != null){
            Pingpp.createPayment(this, model.getData().getResponseBody());
        }else {
            ToastUtils.showShortToast(mContext, "获取微信支付数据出错");
        }
    }

    @Override
    public void onGetPingAliPayDataSuccess(BaseModel<PayPingResultBean> model) {
        if(model.getData() != null){
            Pingpp.createPayment(this, model.getData().getResponseBody());
        }else {
            ToastUtils.showShortToast(mContext, "获取支付宝支付数据出错");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                /* 处理返回值
                 * "success" - payment succeed
                 * "fail"    - payment failed
                 * "cancel"  - user canceld
                 * "invalid" - payment plugin not installed
                 */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                LogUtils.e("返回结果" + result);
                LogUtils.e("返回结果" + errorMsg == null ? "" : errorMsg);
                LogUtils.e("返回结果" + extraMsg == null ? "" : extraMsg);
                if(!XiKouUtils.isNullOrEmpty(result)){
                    if("success".equals(result)){//支付成功
                        goSuccess();
                    }else if("fail".equals(result)){//支付失败
                        goFail();
                    }else if("cancel".equals(result)){
                        ToastUtils.showShortToast(mContext,"已取消");
                    }
                }
            }
        }
    }

    //跳转失败页面
    private void goFail(){
        finish();
        Intent intent=new Intent(ShopPayOrderActivity.this, PayOrderResultActivity.class);
        intent.putExtra(PayOrderResultActivity.PAY_STATUS,false);
        intent.putExtra(PayOrderResultActivity.PAY_ORDER_NO,orderNo);
        intent.putExtra(PayOrderResultActivity.PAY_ORDER_TYPE,OrderType.OTO_TYPE);
        startActivity(intent);
    }

    //跳转成功页面
    private void goSuccess(){
        ToastUtils.showShortToast(mContext,"支付成功");
        finish();
        Intent intent=new Intent(ShopPayOrderActivity.this, PayOrderResultActivity.class);
        intent.putExtra(PayOrderResultActivity.PAY_STATUS,true);
        intent.putExtra(PayOrderResultActivity.PAY_ORDER_NO,orderNo);
        intent.putExtra(PayOrderResultActivity.PAY_ORDER_TYPE,OrderType.OTO_TYPE);
        intent.putExtra(PayOrderResultActivity.PAY_COUPON,coupon);
        intent.putExtra(PayOrderResultActivity.PAY_MOUNT,realPrice);
        startActivity(intent);
    }

    @Override
    public void onRedPaySuccess(BaseModel model) {
        //跳转支付成功页面
        finish();
        Intent intent=new Intent(ShopPayOrderActivity.this, PayOrderResultActivity.class);
        intent.putExtra(PayOrderResultActivity.PAY_STATUS,true);
        intent.putExtra(PayOrderResultActivity.PAY_ORDER_NO,orderNo);
        intent.putExtra(PayOrderResultActivity.PAY_ORDER_TYPE,OrderType.OTO_TYPE);
        intent.putExtra(PayOrderResultActivity.PAY_COUPON,coupon);
        intent.putExtra(PayOrderResultActivity.PAY_MOUNT,realPrice);
        startActivity(intent);
    }

    @Override
    public void onErrorCode(BaseModel model) {
        //跳转支付成功页面
        finish();
        Intent intent=new Intent(ShopPayOrderActivity.this, PayOrderResultActivity.class);
        intent.putExtra(PayOrderResultActivity.PAY_STATUS,false);
        intent.putExtra(PayOrderResultActivity.PAY_ORDER_NO,orderNo);
        intent.putExtra(PayOrderResultActivity.PAY_ORDER_TYPE,OrderType.OTO_TYPE);
        startActivity(intent);
    }

    private void pay(PayResultBean builder){
        PayReq request = new PayReq();
        request.appId = builder.getAppid();//wx33bda4593214121a
        request.nonceStr = builder.getNoncestr();
        request.packageValue = builder.getPackageX();
        request.partnerId = builder.getPartnerid();
        request.prepayId = builder.getPrepayid();
        request.sign = builder.getSign();
        request.timeStamp = builder.getTimestamp();
        api.registerApp(builder.getAppid());
        boolean isRight = api.sendReq(request);
        SPUtils.getInstance().put(Constant.PAY_ORDER_NO,orderNo);
        SPUtils.getInstance().put(Constant.PAY_ORDER_TPYE,OrderType.OTO_TYPE);
        SPUtils.getInstance().put(Constant.PAY_ORDER_COUPON,coupon);
        SPUtils.getInstance().put(Constant.PAY_ORDER_MOUNT,totalPrice);
        Logger.e("" + isRight);
    }

    /**
     * 支付宝支付
     * @param orderInfo 服务端返回的订单信息
     */
    private void goAliPay(String orderInfo){
        final Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(ShopPayOrderActivity.this);
            Map<String, String> result = alipay.payV2(orderInfo, true);
            Log.i("msp", result.toString());

            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            mHandler.sendMessage(msg);
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
