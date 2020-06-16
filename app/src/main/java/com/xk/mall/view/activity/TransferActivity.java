package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.TransferInfoBean;
import com.xk.mall.model.impl.TransferViewImpl;
import com.xk.mall.presenter.TransferPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.ClearEditText;
import com.xk.mall.view.widget.CommomDialog;
import com.xk.mall.view.widget.OrderTipDialog;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * 转账页面
 */
public class TransferActivity extends BaseActivity<TransferPresenter> implements TransferViewImpl {
    public static String BALANCE = "my_balance";
    @BindView(R.id.et_transfer_phone)
    ClearEditText etTransferPhone;//电话号码输入框
    @BindView(R.id.iv_transfer_logo)
    ImageView ivTransferLogo;//用户头像
    @BindView(R.id.tv_transfer_name)
    TextView tvTransferName;//用户名称
    @BindView(R.id.ll_transfer_info)
    LinearLayout llTransferInfo;//用户信息布局
    @BindView(R.id.btn_transfer_next)
    Button btnTransferNext;//下一步

    private int balance;//用户可转账金额
    private String phone = "";//用户手机号
    private String logo;//用户头像
    private String name;//用户名称
    private String transferId;//用户ID

    @Override
    protected TransferPresenter createPresenter() {
        return new TransferPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transfer;
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
        balance = getIntent().getIntExtra(BALANCE, 0);
        btnTransferNext.setEnabled(false);
        etTransferPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                llTransferInfo.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                phone = editable.toString().trim();
                if(XiKouUtils.isNullOrEmpty(phone)){
                    llTransferInfo.setVisibility(View.GONE);
                    btnTransferNext.setEnabled(false);
                }else {
                    btnTransferNext.setEnabled(true);
                }
            }
        });
    }


    @OnClick(R.id.btn_transfer_next)
    public void onClickView(){
        if(llTransferInfo.getVisibility() == View.GONE){
            String userPhone = SPUtils.getInstance().getString(Constant.USER_MOBILE);
            if(!XiKouUtils.isNullOrEmpty(phone) && phone.equals(userPhone)){
                ToastUtils.showShortToast(mContext, "不能给自己转账哦");
            }else {
                mPresenter.getTransferInfoByPhone(phone);
            }
        }else {
            Intent intent = new Intent(mContext, TransferNextActivity.class);
            intent.putExtra(TransferNextActivity.BALANCE, balance);
            intent.putExtra(TransferNextActivity.USER_NAME, name);
            intent.putExtra(TransferNextActivity.USER_PHONE, phone);
            intent.putExtra(TransferNextActivity.USER_LOGO, logo);
            intent.putExtra(TransferNextActivity.USER_ID, transferId);
            ActivityUtils.startActivity(intent);
        }
    }

    @Override
    public void onGetDataByPhone(BaseModel<TransferInfoBean> model) {
        if(model.getData() != null){
            //绑定数据
            btnTransferNext.setEnabled(true);
            llTransferInfo.setVisibility(View.VISIBLE);
            GlideUtil.showCircleLoading(mContext, model.getData().getHeadUrl(), ivTransferLogo);
            tvTransferName.setText(model.getData().getNickName());
            phone = model.getData().getMobile();
            name = model.getData().getNickName();
            logo = model.getData().getHeadUrl();
            transferId = model.getData().getId();
        }else {
            //显示用户不存在
            new CommomDialog(mContext, R.style.mydialog, model.getMsg(), (dialog, confirm) -> {
                if (confirm) {
                    dialog.dismiss();
                }
            }).show();
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        new CommomDialog(mContext, R.style.mydialog, model.getMsg(), (dialog, confirm) -> {
            if (confirm) {
                dialog.dismiss();
            }
        }).show();
    }
}
