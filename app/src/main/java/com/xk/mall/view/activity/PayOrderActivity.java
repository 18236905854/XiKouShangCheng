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
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
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
import com.xk.mall.model.entity.PayPingResultBean;
import com.xk.mall.model.entity.PayResultBean;
import com.xk.mall.model.impl.PayViewImpl;
import com.xk.mall.presenter.PayPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PayResult;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.pswkeyboard.widget.PopEnterPassword;

import org.json.JSONException;
import org.json.JSONObject;

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
public class PayOrderActivity extends BaseActivity<PayPresenter> implements PayViewImpl {
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_gift_coupon)
    TextView tvGiftCoupon;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;//根据订单类型显示赠送优惠券还是消耗优惠券
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
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.btn_pay_order)
    Button btnPayOrder;
    @BindView(R.id.rl_goods_name)
    RelativeLayout rlPayGoodsName;//商品名
    @BindView(R.id.rl_goods_coupon)
    RelativeLayout rlPayGoodsCoupon;//优惠券
    /**intent传递过来的订单类型的key*/
    public static final String ORDER_TYPE = "order_type";
    /**intent传递过来的商品名称的key*/
    public static final String GOODS_NAME = "goods_name";
    /**intent传递过来的订单总价的key*/
    public static final String TOTAL_PRICE = "total_price";
    /**intent传递过来的订单号的key*/
    public static final String ORDER_NUMBER = "order_number";
    /**intent传递过来的优惠券的key*/
    public static final String COUPON_VALUE = "coupon_value";
    /**intent传递过来的是否显示优惠券布局的key*/
    public static final String IS_SHOW_COUPON = "is_show_coupon";
    /**intent传递过来的活动类型的key*/
    public static final String ACTIVITY_TYPE = "activity_type";
    /**intent传递过来的是否砍价*/
    public static final String IS_CUT = "is_cut";//是否是砍价
    /**intent传递过来的砍价id*/
    public static final String CUT_ID = "cut_id";//砍价ID

    private String order_number = "";//订单号
    private boolean isShowCoupon = false;
    private int totalPrice;//订单金额
    private int orderType = 0;//订单类型
    private int couponValue = 0;//优惠券金额
    private boolean isCut;//是否砍价
    private String cutId;//砍价ID
//    private boolean isStop;//是否进入关闭
    private PopEnterPassword popEnterPassword;//密码弹窗
    IWXAPI api;
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
                    if(isCut){
                        Intent intent=new Intent(PayOrderActivity.this,CutOrderResultActivity.class);
                        intent.putExtra(CutOrderResultActivity.PAY_STATUS,true);
                        intent.putExtra(CutOrderResultActivity.CUT_ID,cutId);
                        intent.putExtra(CutOrderResultActivity.PAY_ORDER_NO,order_number);
                        intent.putExtra(CutOrderResultActivity.PAY_ORDER_TYPE,orderType);
                        intent.putExtra(CutOrderResultActivity.PAY_COUPON,couponValue);
                        intent.putExtra(CutOrderResultActivity.PAY_MOUNT,totalPrice);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(mContext, PayOrderResultActivity.class);
                        intent.putExtra(PayOrderResultActivity.PAY_STATUS, true);
                        intent.putExtra(PayOrderResultActivity.PAY_ORDER_NO,order_number);
                        intent.putExtra(PayOrderResultActivity.PAY_ORDER_TYPE,orderType);
                        intent.putExtra(PayOrderResultActivity.PAY_COUPON,couponValue);
                        intent.putExtra(PayOrderResultActivity.PAY_MOUNT,totalPrice);
                        ActivityUtils.startActivity(intent);
                        finish();
                    }
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
        return R.layout.activity_pay_order;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setText("支付订单");
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(this);
        ckPayRed.setChecked(true);
        String name = getIntent().getStringExtra(GOODS_NAME);
        totalPrice = getIntent().getIntExtra(TOTAL_PRICE, 0);
        cutId = getIntent().getStringExtra(CUT_ID);
        orderType = getIntent().getIntExtra(ORDER_TYPE, -1);
        order_number = getIntent().getStringExtra(ORDER_NUMBER);
        couponValue = getIntent().getIntExtra(COUPON_VALUE, 0);
        isCut = getIntent().getBooleanExtra(IS_CUT, false);
        int activityType = getIntent().getIntExtra(ACTIVITY_TYPE, 0);
        if(activityType != 0){
            if(activityType == ActivityType.ACTIVITY_WUG){
                tvCoupon.setText("赠送优惠券");
            }else if(activityType == ActivityType.ACTIVITY_GLOBAL_BUYER){
                tvCoupon.setText("消耗优惠券");
            }
        }else {
            if(orderType == OrderType.WUG_TYPE){
                tvCoupon.setText("赠送优惠券");
            }else if(orderType == OrderType.GLOBAL_TYPE){
                tvCoupon.setText("消耗优惠券");
            }else if(orderType == OrderType.NEW_PERSON_TYPE){

            }
        }
        if(order_number != null){
            tvOrderNumber.setText(order_number);
        }
        tvGiftCoupon.setText(PriceUtil.divideCoupon(couponValue));
        isShowCoupon = getIntent().getBooleanExtra(IS_SHOW_COUPON, true);
        if(null == name || TextUtils.isEmpty(name)){
            rlPayGoodsName.setVisibility(View.GONE);
        }else {
            rlPayGoodsName.setVisibility(View.VISIBLE);
            tvGoodsName.setText(name);
        }
        tvOrderPrice.setText(String.valueOf(PriceUtil.dividePrice(totalPrice)));
        btnPayOrder.setText("支付金额 ¥" + PriceUtil.dividePrice(totalPrice));
        if(isShowCoupon){
            rlPayGoodsCoupon.setVisibility(View.VISIBLE);
        }else {
            rlPayGoodsCoupon.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(api != null){
            api.unregisterApp();
            api.detach();
            api = null;
        }
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
        }
        MyApplication.getInstance().removeActivity(this);
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

    @Override
    public boolean isSupportSwipeBack() {
        return false;
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
                    api = WXAPIFactory.createWXAPI(this, null);
                    // 将应用的appId注册到微信
                    api.registerApp(BaseContent.UM_WECHAT_KEY);
                    mPresenter.pay(order_number, MyApplication.userId, orderType, totalPrice,MyApplication.switchBean.isPingxx());
                }else if(ckPayZfb.isChecked()){
                    //支付宝支付
                    mPresenter.goAliPay(order_number, orderType, MyApplication.userId, MyApplication.switchBean.isPingxx());
                }
                break;
        }
    }

    private void showPassWordDialog() {
//        PayInputPwdDialog  payInputPwdDialog=new PayInputPwdDialog(mContext);
//        payInputPwdDialog.show();
        //判断用户是否设置支付密码，没设置跳转设置页面
        int isSetPwd = SPUtils.getInstance().getInt(Constant.USER_SET_PWD, 0);
        int isRealName = SPUtils.getInstance().getInt(Constant.USER_REAL_NAME, 0);
        if(isRealName != 1){
            ToastUtils.showShortToast(mContext, "请先实名认证");
            ActivityUtils.startActivity(RealNameActivity.class);
            return;
        }
        if(isSetPwd != 1){
            //跳转设置支付密码页面
            ToastUtils.showShortToast(mContext, "请设置支付密码");
            ActivityUtils.startActivity(SetPayPwdOneActivity.class);
            return;
        }

         popEnterPassword = new PopEnterPassword(this);
        // 显示窗口
        popEnterPassword.showAtLocation(this.findViewById(R.id.layoutContent),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

        popEnterPassword.setOnFinishInputListener(new PopEnterPassword.OnFinishInputListener() {
            @Override
            public void onFinish(String input) {
                String key = "xikouxikouxikoux";
                byte[] result = EncryptUtils.encryptAES(input.getBytes(), key.getBytes(), "AES/CBC/PKCS5Padding", key.getBytes());
                mPresenter.goRedPay(order_number, orderType, EncodeUtils.base64Encode2String(result), totalPrice, MyApplication.userId);
                popEnterPassword.dismiss();
//                ToastUtils.showShortToast(mContext,"支付成功 ：密碼"+input);
            }
        });

        //重置密码
        popEnterPassword.setOnSetPwdListenner(new PopEnterPassword.OnSetPwdListenner() {
            @Override
            public void OnSetPwd() {
                Intent intent=new Intent(PayOrderActivity.this, SetPayPwdOneActivity.class);
                startActivityForResult(intent,1000);
                popEnterPassword.dismiss();
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
            //将Ping++的数据整理成微信原生需要的数据去发送请求
            try {
                LogUtils.e("将Ping++的数据整理成微信原生数据发送支付请求");
                parsePingxxData(model.getData().getResponseBody());
            } catch (JSONException e) {
                ToastUtils.showShortToast(mContext, "获取微信支付数据出错");
                e.printStackTrace();
            }
//            Pingpp.createPayment(this, model.getData().getResponseBody());
        }else {
            ToastUtils.showShortToast(mContext, "获取微信支付数据出错");
        }
    }

    /**
     * 将ping++接口返回的数据整理的微信原生的数据并发送支付请求
     * @param charge ping++的charge对象字符串
     */
    private void parsePingxxData(String charge) throws JSONException {
        PayResultBean payResultBean = new PayResultBean();
        JSONObject jsonObject = new JSONObject(charge);
        JSONObject credential = jsonObject.getJSONObject("credential");
        JSONObject wxObject = credential.getJSONObject("wx");
        String appID = wxObject.getString("appId");
        String partnerId = wxObject.getString("partnerId");
        String prepayId = wxObject.getString("prepayId");
        String nonceStr = wxObject.getString("nonceStr");
        String packageValue = wxObject.getString("packageValue");
        String sign = wxObject.getString("sign");
        long timeStamp = wxObject.getLong("timeStamp");
        LogUtils.e("解析Ping++数据");
        payResultBean.setAppid(appID);
        payResultBean.setNoncestr(nonceStr);
        payResultBean.setPackageX(packageValue);
        payResultBean.setPartnerid(partnerId);
        payResultBean.setPrepayid(prepayId);
        payResultBean.setSign(sign);
        payResultBean.setTimestamp("" + timeStamp);
        LogUtils.e("发送请求");
        pay(payResultBean);
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
    public void onRedPaySuccess(BaseModel model) {
        //跳转支付成功页面
        finish();
        if(isCut){
            Intent intent=new Intent(PayOrderActivity.this,CutOrderResultActivity.class);
            intent.putExtra(CutOrderResultActivity.PAY_STATUS,true);
            intent.putExtra(CutOrderResultActivity.CUT_ID,cutId);
            intent.putExtra(CutOrderResultActivity.PAY_ORDER_NO,order_number);
            intent.putExtra(CutOrderResultActivity.PAY_ORDER_TYPE,orderType);
            intent.putExtra(CutOrderResultActivity.PAY_COUPON,couponValue);
            intent.putExtra(CutOrderResultActivity.PAY_MOUNT,totalPrice);
            startActivity(intent);
        }else {
            Intent intent=new Intent(PayOrderActivity.this,PayOrderResultActivity.class);
            intent.putExtra(PayOrderResultActivity.PAY_STATUS,true);
            intent.putExtra(PayOrderResultActivity.PAY_ORDER_NO,order_number);
            intent.putExtra(PayOrderResultActivity.PAY_ORDER_TYPE,orderType);
            intent.putExtra(PayOrderResultActivity.PAY_COUPON,couponValue);
            intent.putExtra(PayOrderResultActivity.PAY_MOUNT,totalPrice);
            startActivity(intent);
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext, model.getMsg());
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
        ToastUtils.showShortToast(mContext,"支付失败...");
        Intent intent = new Intent(mContext, PayOrderResultActivity.class);
        intent.putExtra(PayOrderResultActivity.PAY_STATUS,false);
        startActivity(intent);
        finish();
    }

    //跳转成功页面
    private void goSuccess(){
        ToastUtils.showShortToast(mContext,"支付成功");
        //订单支付
        if(!TextUtils.isEmpty(order_number)){
            SPUtils.getInstance().remove(Constant.PAY_ORDER_NO);
            SPUtils.getInstance().remove(Constant.PAY_ORDER_CUT);
            SPUtils.getInstance().remove(Constant.PAY_ORDER_TPYE);
            SPUtils.getInstance().remove(Constant.PAY_ORDER_COUPON);
            SPUtils.getInstance().remove(Constant.PAY_ORDER_MOUNT);
            if(!XiKouUtils.isNullOrEmpty(cutId)){
                Intent intent=new Intent(mContext, CutOrderResultActivity.class);
                intent.putExtra(CutOrderResultActivity.PAY_STATUS,true);
                intent.putExtra(CutOrderResultActivity.CUT_ID,cutId);
                intent.putExtra(CutOrderResultActivity.PAY_ORDER_NO,order_number);
                intent.putExtra(CutOrderResultActivity.PAY_ORDER_TYPE,orderType);
                intent.putExtra(CutOrderResultActivity.PAY_COUPON,couponValue);
                intent.putExtra(CutOrderResultActivity.PAY_MOUNT,totalPrice);
                startActivity(intent);
            }else {
                Intent intent = new Intent(mContext, PayOrderResultActivity.class);
                intent.putExtra(PayOrderResultActivity.PAY_STATUS,true);
                intent.putExtra(PayOrderResultActivity.PAY_ORDER_NO,order_number);
                intent.putExtra(PayOrderResultActivity.PAY_ORDER_TYPE,orderType);
                intent.putExtra(PayOrderResultActivity.PAY_COUPON,couponValue);
                intent.putExtra(PayOrderResultActivity.PAY_MOUNT,totalPrice);
                startActivity(intent);
            }
            MyApplication.getInstance().closeActivity();
            return;
        }
    }

    //
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if(isStop){
//            //根据订单号查询订单是否支付成功，成功或失败跳转页面
//
//            isStop = false;
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        isStop = true;
//    }

    /**
     * 支付宝支付
     * @param orderInfo 服务端返回的订单信息
     */
    private void goAliPay(String orderInfo){
        final Runnable payRunnable = () -> {
            PayTask alipay = new PayTask(PayOrderActivity.this);
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

    private void pay(PayResultBean builder){
        if(builder == null){
            ToastUtils.showShortToast(mContext, "获取数据有误");
            return;
        }
        LogUtils.e("生成的数据:" + builder.toString());
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
        //保存支付的订单号-及订单类型
        SPUtils.getInstance().put(Constant.PAY_ORDER_NO,order_number);
        if(isCut){
            SPUtils.getInstance().put(Constant.PAY_ORDER_CUT,isCut);
        }
        SPUtils.getInstance().put(Constant.PAY_ORDER_TPYE,orderType);
        SPUtils.getInstance().put(Constant.PAY_ORDER_COUPON,couponValue);
        SPUtils.getInstance().put(Constant.PAY_ORDER_MOUNT,totalPrice);
        Logger.e("" + isRight);
    }
}
