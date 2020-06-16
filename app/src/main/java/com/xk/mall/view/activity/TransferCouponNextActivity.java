package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.TransferResultBean;
import com.xk.mall.model.eventbean.UpdateCouponEvent;
import com.xk.mall.model.impl.TransferCouponNextViewImpl;
import com.xk.mall.model.impl.TransferNextViewImpl;
import com.xk.mall.model.request.TransferCouponRequestBody;
import com.xk.mall.model.request.TransferRequestBody;
import com.xk.mall.presenter.TransferCouponNextPresenter;
import com.xk.mall.presenter.TransferNextPresenter;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.ClearEditText;
import com.xk.mall.view.widget.CommonPopupWindow;
import com.xk.mall.view.widget.pswkeyboard.widget.PopEnterPassword;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 转账下一步页面
 */
public class TransferCouponNextActivity extends BaseActivity<TransferCouponNextPresenter> implements TransferCouponNextViewImpl,CommonPopupWindow.ViewInterface {
    /**传递过来的用户可转账金额的key*/
    public static String BALANCE = "my_balance";
    /**传递过来的用户头像的key*/
    public static String USER_LOGO = "user_logo";
    /**传递过来的用户名称的key*/
    public static String USER_NAME = "user_name";
    /**传递过来的用户手机号的key*/
    public static String USER_PHONE = "user_phone";
    /**传递过来的用户ID的key*/
    public static String USER_ID = "user_id";
    public static String FEE_RATES = "fee_rates";//费率
    public static String COUPON_ID = "coupon_id";//优惠券ID
    @BindView(R.id.iv_transfer_logo)
    ImageView ivTransferLogo;//用户头像
    @BindView(R.id.tv_transfer_name)
    TextView tvTransferName;//用户名称
    @BindView(R.id.tv_transfer_phone)
    TextView tvTransferPhone;//用户手机号
    @BindView(R.id.tv_transfer_total_money)
    TextView tvTransferTotalMoney;//可转账余额
    @BindView(R.id.et_transfer_money)
    ClearEditText etTransferMoney;//转账金额
    @BindView(R.id.btn_transfer_next)
    Button btnTransferNext;//确认转账
    @BindView(R.id.tv_handling_fee)
    TextView tvHandlingFee;//手续费
    @BindView(R.id.tv_handling_rate)
    TextView tvHandlingRate;//费率
    private boolean isTransfer;//是否转账
    private int myYuEr;//单张优惠券余额
    private String couponId;//优惠券ID
    private int feeRates;//优惠券费率
    private String phone;
    private String logo;
    private String name;
    private String transferUserId;//转账的用户ID
    private int newFate;//手续费
    private int outAmount;//本次消耗的优惠券总值
    private CommonPopupWindow popupWindow;

    @Override
    protected TransferCouponNextPresenter createPresenter() {
        return new TransferCouponNextPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer_coupon_next;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("转赠优惠券");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(this);
        myYuEr = getIntent().getIntExtra(BALANCE, 0);
        phone = getIntent().getStringExtra(USER_PHONE);
        logo = getIntent().getStringExtra(USER_LOGO);
        name = getIntent().getStringExtra(USER_NAME);
        transferUserId = getIntent().getStringExtra(USER_ID);
        couponId = getIntent().getStringExtra(COUPON_ID);
        feeRates = getIntent().getIntExtra(FEE_RATES, 0);
        GlideUtil.showCircleLoading(mContext, logo, ivTransferLogo);
        tvTransferName.setText(name);
        tvTransferPhone.setText(phone);
        tvHandlingRate.setText("费率:" + feeRates + "%");
        tvTransferTotalMoney.setText("本券可转赠 " + PriceUtil.divideCoupon(myYuEr));

        etTransferMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(XiKouUtils.isNullOrEmpty(s.toString().trim())){
                    tvHandlingFee.setVisibility(View.GONE);
                    btnTransferNext.setEnabled(false);
                }else {
                    //根据费率来计算
                    calculationLast(s.toString().trim());
                }
            }
        });

    }

    /**
     * 计算最后值
     * @param content  用户输入的值
     */
    private void calculationLast(String content){
        int userInCoupon = Integer.parseInt(content);
        LogUtils.e("剩余:" + myYuEr);
        int newMyYuEr = Integer.parseInt(PriceUtil.divideCoupon(myYuEr));
        if(userInCoupon == 0){
            ToastUtils.showShortToast(mContext, "不能转赠0");
            btnTransferNext.setEnabled(false);
        }else if(userInCoupon > newMyYuEr){
            ToastUtils.showShortToast(mContext, "转赠优惠券面值不能大于可转赠优惠券面值");
            btnTransferNext.setEnabled(false);
        }
//        else if(userInCoupon * 100 == myYuEr){//向上取整当全部取出时，需要计算
//            Double newTotalAmount = Math.floor(myYuEr * 100.0/(100-feeRates));
//            Double realCoupon = Math.ceil(newTotalAmount/100.0);//没有扣除手续费的优惠券实际值
//            LogUtils.e("优惠券实际总值:" + realCoupon.intValue());
//            newFate = realCoupon.intValue() - userInCoupon;
//            tvHandlingFee.setText("手续费:" + newFate + "券");
//            btnTransferNext.setEnabled(true);
//        }
        else {
            tvHandlingFee.setVisibility(View.VISIBLE);
            //计算手续费
            double result = userInCoupon * feeRates / 100.0;
            result = Math.ceil(result);
            newFate = Double.valueOf(result).intValue();
            tvHandlingFee.setText("服务费:" + newFate + "券");
            btnTransferNext.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_transfer_next)
    public void clickView(){
        KeyboardUtils.hideSoftInput(this);
        int userInCoupon = Integer.parseInt(etTransferMoney.getText().toString().trim());
        if(userInCoupon > myYuEr){
            ToastUtils.showShortToast(mContext, "转赠优惠券面值不能大于可转赠优惠券面值");
            btnTransferNext.setEnabled(false);
            return;
        }
        showMonthPopup();
    }

    /**
     * 密码输入弹窗
     */
    private void showPwdDialog() {
        KeyboardUtils.hideSoftInput(this);
        PopEnterPassword popEnterPassword = new PopEnterPassword(this);
        // 显示窗口
        popEnterPassword.showAtLocation(this.findViewById(R.id.layoutContent),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

        popEnterPassword.setOnFinishInputListener(input -> {
            popEnterPassword.dismiss();
            String key = "xikouxikouxikoux";
            byte[] result = EncryptUtils.encryptAES(input.getBytes(), key.getBytes(), "AES/CBC/PKCS5Padding", key.getBytes());
            TransferCouponRequestBody requestBody = new TransferCouponRequestBody();
            requestBody.setPayPassword(EncodeUtils.base64Encode2String(result));
            requestBody.setToUserId(transferUserId);
            requestBody.setCouponId(couponId);
            requestBody.setFee(newFate * 100);
            int amount = Double.valueOf(Double.parseDouble(etTransferMoney.getText().toString()) * 100).intValue();
            requestBody.setOutAmount(amount + newFate * 100);
            outAmount = amount + newFate * 100;
            requestBody.setReceivedAmount(amount);
            mPresenter.transferCoupon(requestBody);
        });

        //重置密码
        popEnterPassword.setOnSetPwdListenner(() -> {
            Intent intent = new Intent(mContext, SetPayPwdOneActivity.class);
            startActivityForResult(intent, 1000);
        });
    }

    private void showMonthPopup() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(mContext).inflate(R.layout.popup_transfer_coupon_sure, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_transfer_coupon_sure)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
//                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onTransferSuccess(BaseModel model) {
        //转账成功跳转页面
        if(model != null && model.getData() != null){
            //跳转成功
            //转赠成功之后需要发消息更新之前的优惠券
            UpdateCouponEvent updateCouponEvent = new UpdateCouponEvent();
            updateCouponEvent.setCouponId(couponId);
            Double newTotalAmount = Math.ceil(myYuEr * 100.0/(100-feeRates));
            updateCouponEvent.setAmount(newTotalAmount.intValue() - outAmount);
            EventBus.getDefault().post(updateCouponEvent);
            Intent intent = new Intent(mContext, TransferCouponResultActivity.class);
            intent.putExtra(TransferCouponResultActivity.TRANSFER_STATE, true);
            ActivityUtils.startActivity(intent);
        }else {
            //跳转失败
            Intent intent = new Intent(mContext, TransferCouponResultActivity.class);
            ActivityUtils.startActivity(intent);
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        int amount = Double.valueOf(Double.parseDouble(etTransferMoney.getText().toString())).intValue();
        int totalAmount = amount + newFate;
        TextView tvMoney = view.findViewById(R.id.tv_transfer_money);
        TextView tvAmount = view.findViewById(R.id.tv_coupon_amount);
        TextView tvFee = view.findViewById(R.id.tv_coupon_fee);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        ImageView imgClose = view.findViewById(R.id.img_close);
        tvMoney.setText(totalAmount + "券");
        tvFee.setText(newFate + "券");
        tvAmount.setText(etTransferMoney.getText().toString().trim() + "券");
        imgClose.setOnClickListener(v -> {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        });

        //筛选确认
        btnConfirm.setOnClickListener(v -> {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
            showPwdDialog();
        });
    }

    @Override
    public void onErrorCode(BaseModel model) {
        if(isTransfer){
            super.onErrorCode(model);
            //跳转失败
            Intent intent = new Intent(mContext, TransferCouponResultActivity.class);
            ActivityUtils.startActivity(intent);
        }else {
            ToastUtils.showShortToast(mContext, model.getMsg());
        }
    }
}
