package com.xk.mall.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.umeng.commonsdk.debug.W;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.WithDrawRequestBean;
import com.xk.mall.model.entity.WithdrawAccountBean;
import com.xk.mall.model.eventbean.WithdrawSuccessBean;
import com.xk.mall.model.impl.MyWithdrawViewImpl;
import com.xk.mall.presenter.MyWithdrawPresenter;
import com.xk.mall.utils.CalculateUtils;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.widget.ClearEditText;
import com.xk.mall.view.widget.CommonPopupWindow;
import com.xk.mall.view.widget.WithDrawMaxDialog;
import com.xk.mall.view.widget.pswkeyboard.widget.PopEnterPassword;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的提现
 */
public class MyWithdrawActivity extends BaseActivity<MyWithdrawPresenter> implements CommonPopupWindow.ViewInterface, MyWithdrawViewImpl {
    private static final String TAG = "MyWithdrawActivity";
    public static final String MY_YU_ER = "my_yu_er";
    public static final String MY_RATE = "my_rate";
    public static final String MIN_MONEY = "min_money";
    public static final String MAX_MONEY = "max_money";
    public static final String CENTER_MONEY = "center_money";
    public static final String CASH_TIME = "cash_time";
    public static final int REQUEST_CODE = 1000;

//    @BindView(R.id.my_toolbar)
//    Toolbar myToolbar;
    @BindView(R.id.rl_withdraw_type)
    RelativeLayout rlWithdrawType;//提现类型
    @BindView(R.id.iv_account_type)
    ImageView ivAccountType;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.rl_switch_account)
    RelativeLayout rlSwitchAccount;
    @BindView(R.id.edit_with_money)
    ClearEditText editWithMoney;
    @BindView(R.id.tv_yuer)
    TextView tvYuer;
    @BindView(R.id.btn_withdraw)
    Button btnWithdraw;
    @BindView(R.id.tv_withdraw_handling)
    TextView tvHandling;//手续费
    @BindView(R.id.tv_withdraw_real)
    TextView tvReal;//实际到账金额
    @BindView(R.id.tv_withdraw_type)
    TextView tvWithdrawType;//提现类型
    @BindView(R.id.tv_rule)
    TextView tvRule;//提现规则
    private String arrivalTime;//到账时间
    private int handlingMoney;//手续费
    private CommonPopupWindow popupWindow;
    private int myYuEr;//我的余额
    private int rate;//手续费率
    private int minMoney;//最低提现金额
    private int maxMoney;//最高提现金额
    private int centerMoney;//中间金额
    private int time;//到账时间
    private String cardNo;//银行卡号
    private String cardId;//银行卡ID
    private double rateMoney;//手续费
    private double realMoney;//实际到账金额
    private double withdrawMoney;//提现金额
    private boolean isWithDraw;//是否提现

    @Override
    protected MyWithdrawPresenter createPresenter() {
        return new MyWithdrawPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_withdraw;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarColor(R.color.color_title).titleBar(toolbar).init();
        setTitle("我的提现");
        setTitleTextColor(Color.WHITE);
        setLeftDrawable(R.drawable.ic_back_white);
        toolbar.setBackgroundColor(getResources().getColor(R.color.color_title));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data !=null ){
            if(requestCode==REQUEST_CODE){
                WithdrawAccountBean bean = (WithdrawAccountBean) data.getSerializableExtra("accout_entity");
                Log.e(TAG, "onActivityResult: "+bean.getBankName() );
                if(bean.getAccount().length() > 4){
                    tvAccountName.setText(bean.getBankName() + "(" + bean.getAccount().substring(bean.getAccount().length() - 4) + ")");
                }else {
                    tvAccountName.setText(bean.getBankName() + "(" + bean.getAccount() + ")");
                }
                cardNo = bean.getAccount();
                cardId = bean.getId();
                bindIcon(bean, ivAccountType);
            }
        }
    }

    @Override
    protected void initData() {
        myYuEr=getIntent().getIntExtra(MY_YU_ER,0);
        Log.e(TAG, "initData: "+myYuEr );
        tvYuer.setText("余额 ¥"+ PriceUtil.dividePrice(myYuEr));
        rate = getIntent().getIntExtra(MY_RATE, 0);
        minMoney = getIntent().getIntExtra(MIN_MONEY, 0);
        maxMoney = getIntent().getIntExtra(MAX_MONEY, 0);
        centerMoney = getIntent().getIntExtra(CENTER_MONEY, 0);
        time = getIntent().getIntExtra(CASH_TIME, 0);
        clearMoney();
        if(myYuEr == 0){
            editWithMoney.setEnabled(false);
        }
        initEvent();
        tvWithdrawType.setText("即时到账(" + rate + "%)");
        mPresenter.getAccountList(MyApplication.userId);
        String result = String.format(getResources().getString(R.string.with_draw_rule),
                PriceUtil.dividePrice(minMoney), PriceUtil.dividePrice(maxMoney),
                PriceUtil.dividePrice(centerMoney), time);
        tvRule.setText(result);
        if(minMoney == 0 && maxMoney == 0){
            tvRule.setVisibility(View.GONE);
        }else {
            tvRule.setVisibility(View.VISIBLE);
        }
    }

    private void clearMoney(){
        tvHandling.setText("手续费：0.00元");
        tvReal.setText("实际到账：0.00元");
    }

    private void initEvent(){
        editWithMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //控制两位小数“num”即为要控制的位数
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + (2 + 1));
                        editWithMoney.setText(s);
                        editWithMoney.setSelection(s.length());
                    }
                }
                //限制只能输入一次小数点

                if (editWithMoney.getText().toString().indexOf(".") >= 0) {
                    if (editWithMoney.getText().toString().indexOf(".", editWithMoney.getText().toString().indexOf(".") + 1) > 0) {
                        editWithMoney.setText(editWithMoney.getText().toString().substring(0, editWithMoney.getText().toString().length() - 1));
                        editWithMoney.setSelection(editWithMoney.getText().toString().length());
                    }
                }

                //第一次输入为点的时候
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editWithMoney.setText(s);
                    editWithMoney.setSelection(2);

                }

                //个位数为0的时候
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editWithMoney.setText(s.subSequence(0, 1));
                        editWithMoney.setSelection(1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>=1){
                    //判断单笔金额，输入的金额不能大于单笔金额并且不能大于总余额，且不能小于最低提现金额
                    double withDrawMoney = Double.parseDouble(editWithMoney.getText().toString());
                    int sub = myYuEr - Double.valueOf(withDrawMoney * 100).intValue();
                    if(sub < 0){
                        btnWithdraw.setEnabled(false);
                        ToastUtils.showShortToast(mContext, "提现金额不能大于余额");
                    }else if(minMoney != 0 && sub < minMoney){
                        btnWithdraw.setEnabled(false);
                        ToastUtils.showShortToast(mContext, "提现金额不能小于最低提现金额");
                    }else if(maxMoney != 0 && sub > maxMoney){
                        btnWithdraw.setEnabled(false);
                        ToastUtils.showShortToast(mContext, "提现金额不能大于最高提现金额");
                    }else {
                        btnWithdraw.setEnabled(true);
                        //计算手续费和实际到账金额
                        calculateMoney(withDrawMoney);
                    }
                }else {
                    clearMoney();
                }
            }
        });
    }

    //计算金额
    private void calculateMoney(double withDrawMoney) {
        this.withdrawMoney = withDrawMoney;
        realMoney = CalculateUtils.multiplyHalfUpDouble(withDrawMoney, 1d-CalculateUtils.subtractDouble((double) rate, 100.0));
        tvReal.setText("实际到账：" + realMoney + "元");
        rateMoney = CalculateUtils.divideDouble(withDrawMoney, realMoney);
        tvHandling.setText("手续费：" + rateMoney + "元");
    }


    @OnClick({R.id.rl_switch_account, R.id.btn_withdraw, R.id.rl_withdraw_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_switch_account://切换账号
                Intent intent=new Intent(MyWithdrawActivity.this, WithdrawAccountActivity.class);
                intent.putExtra("isSelectAccount", true);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.btn_withdraw://提现
                if(TextUtils.isEmpty(tvAccountName.getText().toString())){
                    ToastUtils.showShortToast(mContext,"请选择收款账号");
                    return;
                }
                showPwdDialog();
                break;
            case R.id.rl_withdraw_type:
//                showSelectFee();
                break;
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    //删除银行卡信息的消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void removeBank(WithdrawAccountBean bean){
        if(bean != null && bean.getId().equals(cardId)){
            //清空银行卡信息
            ivAccountType.setImageResource(R.mipmap.ic_bank_moren);
            tvAccountName.setText("");
        }
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
            mPresenter.verifyPayPwd(MyApplication.userId, EncodeUtils.base64Encode2String(result));
        });

        //重置密码
        popEnterPassword.setOnSetPwdListenner(() -> {
            Intent intent = new Intent(mContext, SetPayPwdOneActivity.class);
            startActivityForResult(intent, 1000);
        });
    }

    //去提现
    private void goWithdraw(){
        isWithDraw = true;
        WithDrawRequestBean requestBean = new WithDrawRequestBean();
        requestBean.setAmount(CalculateUtils.multiplyDouble(realMoney, 100.0).intValue());
        requestBean.setCashAmount(CalculateUtils.multiplyDouble(withdrawMoney, 100.0).intValue());
        requestBean.setCashCommission(CalculateUtils.multiplyDouble(rateMoney, 100.0).intValue());
        requestBean.setCashBankCard(cardNo);
        requestBean.setUserId(MyApplication.userId);
        requestBean.setUserType(5);
        requestBean.setCashType("即时到账");
        mPresenter.goWithdraw(requestBean);
    }

    /**
     * 显示提现次数到达上限对话框
     */
    private void showNoTime(){
        //提现达到上限弹窗
        new WithDrawMaxDialog(mContext, R.style.mydialog, (dialog, confirm) -> {
            if (confirm) {
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 选择手续费 弹窗 选项
     */
    private void showSelectFee() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.popup_withdraw_fee, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_withdraw_fee)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId){
            case R.layout.popup_withdraw_fee:
                RelativeLayout rl_jishi=view.findViewById(R.id.rl_jishi);
                RelativeLayout rl_three_day=view.findViewById(R.id.rl_three_day);
                RelativeLayout rl_seven_day=view.findViewById(R.id.rl_seven_day);

                CheckBox checkbox_jishi=view.findViewById(R.id.checkbox_jishi);
                CheckBox checkbox_three=view.findViewById(R.id.checkbox_three);
                CheckBox checkbox_seven=view.findViewById(R.id.checkbox_seven);
                ImageView imgClose=view.findViewById(R.id.img_close);
                Button btnConfirm=view.findViewById(R.id.btn_confirm);
                imgClose.setOnClickListener(v -> {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                });

                //确认提现
                btnConfirm.setOnClickListener(v -> {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    //将提现类型和手续费回传
                });
                rl_jishi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkbox_jishi.setChecked(true);
                        checkbox_three.setChecked(false);
                        checkbox_seven.setChecked(false);
                    }
                });

                rl_three_day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkbox_jishi.setChecked(false);
                        checkbox_three.setChecked(true);
                        checkbox_seven.setChecked(false);
                    }
                });

                rl_seven_day.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkbox_jishi.setChecked(false);
                        checkbox_three.setChecked(false);
                        checkbox_seven.setChecked(true);
                    }
                });
                break;
        }
    }

    @Override
    public void onGetAccountSuccess(BaseModel<List<WithdrawAccountBean>> model) {
        if(model != null && model.getData() != null && model.getData().size() != 0){
            //获取第一个银行卡数据并显示
            WithdrawAccountBean bean = model.getData().get(0);
            cardNo = model.getData().get(0).getAccount();
            cardId = model.getData().get(0).getId();
            if(bean.getAccount().length() > 4){
                tvAccountName.setText(bean.getBankName() + "(" + bean.getAccount().substring(bean.getAccount().length() - 4) + ")");
            }else {
                tvAccountName.setText(bean.getBankName() + "(" + bean.getAccount() + ")");
            }
            bindIcon(bean, ivAccountType);
        }
    }

    /**
     * 绑定图标
     */
    private void bindIcon(WithdrawAccountBean bean, ImageView imageView){
        if(bean.getImage() != null && !TextUtils.isEmpty(bean.getImage())){
            GlideUtil.show(mContext, bean.getImage(), imageView);
        }else {
            if(bean.getBankName().contains("建设")){
                ivAccountType.setImageResource(R.mipmap.ic_bank_jianhang);
            }else if(bean.getBankName().contains("光大")){
                ivAccountType.setImageResource(R.mipmap.ic_bank_gda);
            }else if(bean.getBankName().contains("交通")){
                ivAccountType.setImageResource(R.mipmap.ic_bank_jiaotong);
            }else if(bean.getBankName().contains("农业")){
                ivAccountType.setImageResource(R.mipmap.ic_bank_nongye);
            }else if(bean.getBankName().contains("浦发")){
                ivAccountType.setImageResource(R.mipmap.ic_bank_pufa);
            }else if(bean.getBankName().contains("邮政")){
                ivAccountType.setImageResource(R.mipmap.ic_bank_youzhen);
            }else if(bean.getBankName().contains("中国")){
                ivAccountType.setImageResource(R.mipmap.ic_bank_china);
            }else if(bean.getBankName().contains("招商")){
                ivAccountType.setImageResource(R.mipmap.ic_bank_zhaoshang);
            }else if(bean.getBankName().contains("工商")){
                ivAccountType.setImageResource(R.mipmap.ic_bank_gs);
            }else {
                ivAccountType.setImageResource(R.mipmap.ic_bank_moren);
            }
        }
    }

    @Override
    public void onWithdrawSuccess(BaseModel<String> model) {
        if(model != null && model.getData() != null){
            //跳转提现申请成功
            Intent intent = new Intent(mContext, WithDrawResultActivity.class);
            intent.putExtra(WithDrawResultActivity.REAL_MONEY, realMoney);
            intent.putExtra(WithDrawResultActivity.RATE_MONEY, rateMoney);
            intent.putExtra(WithDrawResultActivity.WITH_DRAW_ID, model.getData());
            ActivityUtils.startActivity(intent);
            finish();
            //发送消息重新获取数据
            EventBus.getDefault().post(new WithdrawSuccessBean());
        }
    }

    @Override
    public void onGetVerityPayPwdSuc(BaseModel<String> model) {
        goWithdraw();
    }

    @Override
    public void onErrorCode(BaseModel model) {
        if(isWithDraw){
            super.onErrorCode(model);
            finish();
            //跳转提现失败
            Intent intent = new Intent(mContext, WithDrawResultActivity.class);
            ActivityUtils.startActivity(intent);
        }else {
            ToastUtils.showShortToast(mContext, model.getMsg());
        }
    }
}
