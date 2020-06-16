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
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.TransferResultBean;
import com.xk.mall.model.impl.TransferNextViewImpl;
import com.xk.mall.model.request.TransferRequestBody;
import com.xk.mall.presenter.TransferNextPresenter;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.StringUtils;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.ClearEditText;
import com.xk.mall.view.widget.CommomDialog;
import com.xk.mall.view.widget.CommonPopupWindow;
import com.xk.mall.view.widget.pswkeyboard.widget.PopEnterPassword;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 转账下一步页面
 */
public class TransferNextActivity extends BaseActivity<TransferNextPresenter> implements TransferNextViewImpl,CommonPopupWindow.ViewInterface {
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
    @BindView(R.id.et_transfer_remarks)
    ClearEditText etTransferRemarks;//备注
    private CommonPopupWindow popupWindow;
    private boolean isTransfer;//是否转账
    private int myYuEr;//用户转账余额
    private String phone;
    private String logo;
    private String name;
    private String transferUserId;//转账的用户ID

    @Override
    protected TransferNextPresenter createPresenter() {
        return new TransferNextPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer_next;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("转账");
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
        GlideUtil.showCircleLoading(mContext, logo, ivTransferLogo);
        tvTransferName.setText(name);
        tvTransferPhone.setText(phone);
        tvTransferTotalMoney.setText("可转账余额 ¥" + PriceUtil.dividePrice(myYuEr));

        //限定只能输入两位小数
        etTransferMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //控制两位小数“num”即为要控制的位数
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + (2 + 1));
                        etTransferMoney.setText(s);
                        etTransferMoney.setSelection(s.length());
                    }
                }
                //限制只能输入一次小数点

                if (etTransferMoney.getText().toString().indexOf(".") >= 0) {
                    if (etTransferMoney.getText().toString().indexOf(".", etTransferMoney.getText().toString().indexOf(".") + 1) > 0) {
                        etTransferMoney.setText(etTransferMoney.getText().toString().substring(0, etTransferMoney.getText().toString().length() - 1));
                        etTransferMoney.setSelection(etTransferMoney.getText().toString().length());
                    }
                }

                //第一次输入为点的时候
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    etTransferMoney.setText(s);
                    etTransferMoney.setSelection(2);

                }

                //个位数为0的时候
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        etTransferMoney.setText(s.subSequence(0, 1));
                        etTransferMoney.setSelection(1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>=1){
                    //判断单笔金额，输入的金额不能大于单笔金额并且不能大于总余额，且不能小于最低提现金额
                    double withDrawMoney = Double.parseDouble(etTransferMoney.getText().toString());
                    if(withDrawMoney == 0){
                        btnTransferNext.setEnabled(false);
                    }else {
                        int sub = myYuEr - Double.valueOf(withDrawMoney * 100).intValue();
                        if(sub < 0){
                            btnTransferNext.setEnabled(false);
                            ToastUtils.showShortToast(mContext, "转账金额不能大于可转账金额");
                        }else if(sub > myYuEr){
                            btnTransferNext.setEnabled(false);
                            ToastUtils.showShortToast(mContext, "转账金额不能小于0");
                        }else {
                            btnTransferNext.setEnabled(true);
                        }
                    }
                }else {
                    btnTransferNext.setEnabled(false);
                }
            }
        });
    }

    @OnClick(R.id.btn_transfer_next)
    public void clickView(){
        KeyboardUtils.hideSoftInput(this);
        //弹出输入密码
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
            TransferRequestBody requestBody = new TransferRequestBody();
            requestBody.setFromUserId(MyApplication.userId);
            requestBody.setPassword(EncodeUtils.base64Encode2String(result));
            requestBody.setToUserId(transferUserId);
            if(!XiKouUtils.isNullOrEmpty(etTransferRemarks.getText().toString().trim())){
                requestBody.setRemarks(etTransferRemarks.getText().toString().trim());
            }
            int amount = Double.valueOf(Double.parseDouble(etTransferMoney.getText().toString()) * 100).intValue();
            requestBody.setAmount(amount);
            mPresenter.transfer(requestBody);
        });

        //重置密码
        popEnterPassword.setOnSetPwdListenner(() -> {
            Intent intent = new Intent(mContext, SetPayPwdOneActivity.class);
            startActivityForResult(intent, 1000);
        });
    }

    private void showMonthPopup() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(TransferNextActivity.this).inflate(R.layout.popup_transfer_sure, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_transfer_sure)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
//                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onTransferSuccess(BaseModel<TransferResultBean> model) {
        //转账成功跳转页面
        if(model != null && model.getData() != null){
            //跳转成功
            Intent intent = new Intent(mContext, TransferResultActivity.class);
            intent.putExtra(TransferResultActivity.WITH_DRAW_ID, model.getData().getId());
            intent.putExtra(TransferResultActivity.WITH_DRAW_KEY, model.getData().getRefkey());
            ActivityUtils.startActivity(intent);
        }else {
            //跳转失败
            Intent intent = new Intent(mContext, TransferResultActivity.class);
            ActivityUtils.startActivity(intent);
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        TextView tvMoney = view.findViewById(R.id.tv_transfer_money);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        ImageView imgClose = view.findViewById(R.id.img_close);
        tvMoney.setText("¥ " + etTransferMoney.getText().toString().trim());
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
            Intent intent = new Intent(mContext, TransferResultActivity.class);
            ActivityUtils.startActivity(intent);
        }else {
            ToastUtils.showShortToast(mContext, model.getMsg());
        }
    }
}
