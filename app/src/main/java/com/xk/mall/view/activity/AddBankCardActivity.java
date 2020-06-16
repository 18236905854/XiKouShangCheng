package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.style.cityjd.JDCityPicker;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.entity.RealNameInfoBean;
import com.xk.mall.model.impl.AddBankCardViewImpl;
import com.xk.mall.model.request.AddBankRequestBody;
import com.xk.mall.presenter.AddBankCardPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.widget.ClearEditText;
import com.xk.mall.view.widget.ContentWithSpaceEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 添加 银行卡
 */
public class AddBankCardActivity extends BaseActivity<AddBankCardPresenter> implements AddBankCardViewImpl {

    @BindView(R.id.edit_name)
    TextView tvUserName;
    @BindView(R.id.edit_cardid)
    TextView tvUserCardId;
    @BindView(R.id.edit_bankcard_num)
    ContentWithSpaceEditText editBankcardNum;
    @BindView(R.id.edit_bank_name)
    TextView editBankName;
    @BindView(R.id.tv_branch_ame)
    ClearEditText editBanchName;
    @BindView(R.id.rl_choose_bank_address)
    RelativeLayout rlChooseBankAddress;//选择银行卡地址
    @BindView(R.id.tvBankProvince)
    TextView tvBankProvice;//开户行省
    @BindView(R.id.tvBankCity)
    TextView tvBankCity;//开户行市
    @BindView(R.id.edit_phone)
    TextView tvPhone;
    @BindView(R.id.edit_code)
    ClearEditText editCode;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private int count = 59;//计时器初始值
    String bankCode;//银行卡的code
    private boolean isChooseAddress;//是否选择地址

    @Override
    protected AddBankCardPresenter createPresenter() {
        return new AddBankCardPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_bankcard;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("添加银行卡");
    }

    @Override
    protected void initData() {
        mPresenter.getRealNameInfo(MyApplication.userId);
        String phone= SPUtils.getInstance().getString(Constant.USER_MOBILE);
        tvPhone.setText(phone);
        initEvent();
    }

    @OnClick({R.id.btn_get_code, R.id.btn_submit, R.id.rl_choose_bank_address, R.id.rl_choose_bank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_get_code://获取验证码
                getCode();
                break;
            case R.id.btn_submit:
                String address = tvBankProvice.getText().toString().trim() + tvBankCity.getText().toString().trim();
                AddBankRequestBody requestBody=new AddBankRequestBody();
                String cardNum=editBankcardNum.getText().toString();
                requestBody.setAccount(cardNum.replaceAll(" ",""));
                Log.e(TAG, "onClick: "+cardNum.replaceAll(" ",""));
                if(bankCode == null || TextUtils.isEmpty(bankCode)){
                    ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                    ToastUtils.showShort("银行名称数据有误");
                    return;
                }
                requestBody.setBankCode(bankCode);
                requestBody.setBankName(editBankName.getText().toString());
                requestBody.setBranchName(editBanchName.getText().toString());
                requestBody.setBankLocation(address);
                requestBody.setUserId(MyApplication.userId);
                requestBody.setMobile(tvPhone.getText().toString());
                requestBody.setValidaCode(editCode.getText().toString());
                requestBody.setChannel(3);//账号类型：1：微信， 2：支付宝，3：银行卡，4：信用卡，5：存折
                mPresenter.saveBankCard(requestBody);
                break;

            case R.id.rl_choose_bank_address://点击选择地址
                chooseAddress();
                break;

            case R.id.rl_choose_bank://选择银行
                startActivityForResult(new Intent(mContext, ChooseBankActivity.class), 101);
                break;
        }
    }

    private void chooseAddress(){
        //点击选择地址
        JDCityPicker cityPicker = new JDCityPicker(false);
        cityPicker.init(mContext);
        cityPicker.setProvinceList(getProvinceData());
        cityPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                isChooseAddress = true;
                if(province != null){
                    tvBankProvice.setText(province.getName());
                }
                if(city != null){
                    tvBankCity.setText(city.getName());
                }else {
                    tvBankCity.setText("");
                }
            }

            @Override
            public void onCancel() {
            }
        });
        cityPicker.showCityPicker();
    }

    /**
     * 从数据库中初始化对话框的数据
     */
    private List<ProvinceBean> getProvinceData() {
        List<AddressServerBean> serverBeans = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao()
                .queryBuilder().where(AddressServerBeanDao.Properties.Level.eq(1)).build().list();
        Logger.e("取出来的地址:" + serverBeans.size());
        List<ProvinceBean> provinceBeanList = new ArrayList<>();
        for (AddressServerBean addressServerBean : serverBeans) {
            ProvinceBean provinceBean = new ProvinceBean();
            provinceBean.setName(addressServerBean.name);
            provinceBean.setId("" + addressServerBean.areaId);
            ArrayList<CityBean> cityBeans = new ArrayList<>();
            List<AddressServerBean> cityServer = MySQLiteOpenHelper.getDaoSession(mContext).getAddressServerBeanDao()
                    .queryBuilder().where(AddressServerBeanDao.Properties.ParentId.eq(addressServerBean.areaId)).build().list();
            for (AddressServerBean cityBean : cityServer) {
                CityBean cityBean1 = new CityBean();
                cityBean1.setId("" + cityBean.areaId);
                cityBean1.setName(cityBean.name);
                cityBeans.add(cityBean1);
            }
            provinceBean.setCityList(cityBeans);
            provinceBeanList.add(provinceBean);
        }
        return provinceBeanList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK && data != null){
            //设置银行名称
            String bankName = data.getStringExtra("bankName");
            bankCode = data.getStringExtra("bankCode");
            if(bankName != null && !TextUtils.isEmpty(bankName)){
                editBankName.setText(bankName);
            }
        }
    }

    private void initEvent(){
        editBankcardNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String name=s.toString();
                if(!TextUtils.isEmpty(name)
                        && !TextUtils.isEmpty(editBankName.getText().toString())
                        && !TextUtils.isEmpty(editBanchName.getText().toString())
                        && isChooseAddress
                        && !TextUtils.isEmpty(editCode.getText().toString())
                    ){
                    btnSubmit.setEnabled(true);
                }else{
                    btnSubmit.setEnabled(false);
                }
            }
        });

        editBanchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String zhiName=s.toString();
                if(!TextUtils.isEmpty(zhiName)
                        && !TextUtils.isEmpty(editBankcardNum.getText().toString())
                        && !TextUtils.isEmpty(editBankName.getText().toString())
                        && isChooseAddress
                        && !TextUtils.isEmpty(editCode.getText().toString())
                ){
                    btnSubmit.setEnabled(true);
                }else{
                    btnSubmit.setEnabled(false);
                }
            }
        });

        editCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                String code=s.toString();
                if(!TextUtils.isEmpty(code)
                        && !TextUtils.isEmpty(editBankcardNum.getText().toString())
                        && !TextUtils.isEmpty(editBankName.getText().toString())
                        &&!TextUtils.isEmpty(editBanchName.getText().toString())
                        && isChooseAddress
                ){
                    btnSubmit.setEnabled(true);
                }else{
                    btnSubmit.setEnabled(false);
                }
            }
        });
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

    /**
     * 添加银行成功
     * @param model
     */
    @Override
    public void onAddBankCardSuc(BaseModel model) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort("添加成功");
        finish();
    }

    /**
     * 查询实名认证信息成功
     * @param model
     */
    @Override
    public void onGetRealNameInfoSuc(BaseModel<RealNameInfoBean> model) {
        RealNameInfoBean realNameInfoBean = model.getData();
        if(realNameInfoBean!=null){
            tvUserName.setText(realNameInfoBean.getRealName());
            tvUserCardId.setText(realNameInfoBean.getIdCard());
        }
    }

    /**
     * 获取验证码成功
     * @param model
     */
    @Override
    public void onGetCodeSuc(BaseModel model) {
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort("发送成功");
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        ToastUtils.showShort(model.getMsg());
    }
}
