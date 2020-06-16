package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.impl.SetPwdViewImpl;
import com.xk.mall.presenter.SetPwdPresenter;
import com.xk.mall.utils.Constant;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ClassName SetPayPwdOneActivity
 * Description 设置支付密码 one
 * Author
 * Date
 * Version
 */
public class SetPayPwdOneActivity extends BaseActivity<SetPwdPresenter> implements SetPwdViewImpl {
    private static final String TAG = "SetPayPwdOneActivity";
    @BindView(R.id.et_code)
    EditText editTextCode;
    @BindView(R.id.btn_register_code)
    Button btnGetCode;//获取验证码按钮
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_next)
    Button btnNext;

    private int count = 59;//计时器初始值
    private String phone;

    @Override
    protected SetPwdPresenter createPresenter() {
        return new SetPwdPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_paypwd;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setText("支付密码");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(SetPayPwdOneActivity.this);
        phone= SPUtils.getInstance().getString(Constant.USER_MOBILE,"");
        tvPhone.setText(phone);
        editTextCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String code = s.toString();
                if(TextUtils.isEmpty(code)){
                    btnNext.setEnabled(false);
                }else {
                    btnNext.setEnabled(true);
                }
            }
        });
    }

    @OnClick({R.id.btn_register_code, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register_code://获取验证码
                if(!TextUtils.isEmpty(phone)){
                    getCode();
                }
                break;
            case R.id.btn_next://下一步
                mPresenter.getValidCodeNext(editTextCode.getText().toString(),phone);
                break;
        }
    }


    /**
     * 获取验证码的方法  测试
     */
    private void getCode(){
            Logger.e("开始倒计时");
            mPresenter.getValidCode(tvPhone.getText().toString());

            //调用获取验证码接口，显示倒计时
            Observable.interval(0, 1, TimeUnit.SECONDS) //0延迟  每隔1秒触发
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                    .take(count + 1) //设置循环次数
                    .map(aLong -> count - aLong) //从60-1
                    .doOnSubscribe(disposable -> btnGetCode.setClickable(false))
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Long aLong) {
                            btnGetCode.setText(aLong + "s 后重发");
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            btnGetCode.setText(getResources().getString(R.string.login_get_code));
                            btnGetCode.setClickable(true);
                            count = 59;
                        }
                    });
        }

      //获取验证码成功
    @Override
    public void onGetValidCodeSuccess(BaseModel loginBean) {

    }



    //校验验证码
    @Override
    public void onCodeNextSucess(BaseModel<Boolean> entity) {
        Log.e(TAG, "onCodeNextSucess: " );
        if(entity!=null){
            boolean isSucc=entity.getData();
            if(isSucc){
                Intent intent=new Intent(SetPayPwdOneActivity.this, SetPayPwdTwoActivity.class);
                intent.putExtra("phone_num",phone);
                String code = editTextCode.getText().toString();
                intent.putExtra("inviteCode", code);
                startActivity(intent);
            }else {
                if(!TextUtils.isEmpty(entity.getMsg())){
                    ToastUtils.showShortToast(mContext, entity.getMsg());
                }else {
                    ToastUtils.showShortToast(mContext, "验证码有误");
                }
            }
        }
    }

    @Override
    public void onSavePayPwdSuc(BaseModel o) {

    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext,model.getMsg());
    }
}
