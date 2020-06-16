package com.xk.mall.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.OTOOrderRequestBean;
import com.xk.mall.model.entity.OTOOrderResultBean;
import com.xk.mall.model.impl.ShopPayViewImpl;
import com.xk.mall.presenter.ShopPayPresenter;
import com.xk.mall.utils.BigDecimalUtil;
import com.xk.mall.utils.CalculateUtils;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.widget.CouponDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName ShopPayActivity
 * Description 店铺收银台页面
 * Author 卿凯
 * Date 2019/6/18/018
 * Version V1.0
 */
public class ShopPayActivity extends BaseActivity<ShopPayPresenter> implements ShopPayViewImpl {

    @BindView(R.id.et_shop_money)
    EditText etShopMoney;//输入消费金额
    @BindView(R.id.cb_shop_pay)
    CheckBox cbShopPay;//不打折选择框
    @BindView(R.id.et_shop_not_discount_money)
    EditText etShopNotDiscountMoney;//不打折金额输入框
    @BindView(R.id.et_shop_coupon_money)
    TextView etShopCouponMoney;//优惠券节省金额
    @BindView(R.id.et_shop_pay_real_money)
    EditText etShopPayRealMoney;//实际支付金额
    @BindView(R.id.btn_shop_pay_sure)
    Button btnPaySure;//确认买单按钮
    public static String DISCOUNT_RATE = "discount_rate";//传递过来的优惠买单比例的key
    public static String MERCHANT_ID = "merchant_id";//传递过来的商家ID的key
    public static String SHOP_ID = "shop_id";//传递过来的店铺ID的key
    private String merchantId = "";
    private String shopId = "";
    private boolean isHasEnoughCoupon = false;//是否有足够的优惠券
    private int discountRate = 0;//传递过来的
    private int sumCoupon = 0;//接口取到的优惠券总金额
    private double sumMoney = 0;//订单总金额
    private double noDiscountMoney = 0;//不参与优惠金额
    private double couponMoney = 0;//抵扣优惠券金额
    private double realMoney = 0;//实付金额

    @Override
    protected ShopPayPresenter createPresenter() {
        return new ShopPayPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_pay;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String title = intent.getStringExtra(Constant.INTENT_TITLE);
        Logger.e("ShopPayActivity:" + title);
        setTitle(title);
    }

    @OnClick({R.id.btn_shop_pay_sure})
    public void onClickView(){
        //点击下单,做校验之后跳转
        String money = etShopMoney.getText().toString();
        if(TextUtils.isEmpty(money)){
           showToast("请输入消费金额");
           return;
        }

        if(!isHasEnoughCoupon){
            //显示对话框
            CouponDialog couponDialog = new CouponDialog(mContext, R.style.mydialog, (dialog, confirm) -> {
                if(confirm){
                    finish();
                }
            });
            couponDialog.show();
            couponDialog.setOnDismissListener(dialog1 -> {
                isHasEnoughCoupon = true;
            });
        }else {
            //跳转支付
            OTOOrderRequestBean otoOrderRequestBean = new OTOOrderRequestBean();
            otoOrderRequestBean.setBuyerId(MyApplication.userId);
            otoOrderRequestBean.setDiscount(discountRate);//优惠比例
            otoOrderRequestBean.setOrderSource(1);
            otoOrderRequestBean.setShopId(shopId);
            otoOrderRequestBean.setNonOfferAmount(Double.valueOf(noDiscountMoney * 100).intValue());//不参与优惠券金额
            otoOrderRequestBean.setDeductionCouponAmount(Double.valueOf(couponMoney * 100).intValue());//抵扣优惠券金额;
            otoOrderRequestBean.setMerchantId(merchantId);//商家ID
            otoOrderRequestBean.setOrderAmount(Double.valueOf(sumMoney * 100).intValue());//订单金额
            otoOrderRequestBean.setPayAmount(Double.valueOf(realMoney * 100).intValue());//实付金额
            mPresenter.orderOto(otoOrderRequestBean);
        }
    }

    @Override
    protected void initData() {
        discountRate = getIntent().getIntExtra(DISCOUNT_RATE, 0);
        merchantId = getIntent().getStringExtra(MERCHANT_ID);
        shopId = getIntent().getStringExtra(SHOP_ID);
        btnPaySure.setEnabled(false);
        mPresenter.getCouponSum(MyApplication.userId);
        etShopMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String money = s.toString();
                if(TextUtils.isEmpty(money)){
                    etShopMoney.setTextSize(13);
                    clearAll();
                }else {
                    etShopMoney.setTextSize(25);
                    calculationMoney();
                }
            }
        });

        etShopNotDiscountMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(etShopMoney.getText().toString().trim())){
                    calculationMoney();
                }
            }
        });

        cbShopPay.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                etShopNotDiscountMoney.setVisibility(View.VISIBLE);
            }else {
                etShopNotDiscountMoney.setText("");
                etShopNotDiscountMoney.setVisibility(View.GONE);
            }
        });
    }

    /**
     * 清空所有输入
     */
    private void clearAll(){
        Logger.e("清空数据");
        etShopCouponMoney.setText(getResources().getString(R.string.shop_real_pay_hint) + PriceUtil.divideCoupon(sumCoupon));
        etShopCouponMoney.setTextColor(Color.parseColor("#CCCCCC"));
        etShopNotDiscountMoney.setText("");
        etShopPayRealMoney.setHint(getResources().getString(R.string.shop_coupon_hint));
        etShopPayRealMoney.setText("");
        etShopPayRealMoney.setHintTextColor(Color.parseColor("#CCCCCC"));
    }

    /**
     * 计算实际支付金额
     */
    private void calculationMoney(){

        if(!TextUtils.isEmpty(etShopMoney.getText().toString().trim())){
            if(etShopMoney.getText().toString().trim().startsWith(".")){
                ToastUtils.showShort("输入金额有误，请重新输入");
                btnPaySure.setEnabled(false);
                return;
            }
            sumMoney = Double.parseDouble(etShopMoney.getText().toString().trim());
        }
        if(sumMoney <= 0){
            ToastUtils.setGravity(Gravity.CENTER, 0, 0);
            ToastUtils.showShort("输入金额有误，请重新输入");
            btnPaySure.setEnabled(false);
            return;
        }

        if(!TextUtils.isEmpty(etShopNotDiscountMoney.getText().toString().trim())){
            if(etShopNotDiscountMoney.getText().toString().trim().startsWith(".")){
                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                ToastUtils.showShort("输入金额有误，请重新输入");
                btnPaySure.setEnabled(false);
                return;
            }
            noDiscountMoney = Double.parseDouble(etShopNotDiscountMoney.getText().toString());
            if(sumMoney - noDiscountMoney < 0){
                ToastUtils.setGravity(Gravity.CENTER, 0, 0);
                ToastUtils.showShort("输入金额有误，请重新输入");
                btnPaySure.setEnabled(false);
                return;
            }
            couponMoney = (sumMoney - noDiscountMoney) * discountRate / 100;
        }else {
            couponMoney = sumMoney * discountRate / 100;
        }
        if(couponMoney >= sumCoupon){
            CouponDialog couponDialog = new CouponDialog(mContext, R.style.mydialog, (dialog, confirm) -> {
                dialog.dismiss();
            });
            couponDialog.show();
            isHasEnoughCoupon = false;
            return;
        }else {
            isHasEnoughCoupon = true;
        }
        couponMoney = Math.ceil(couponMoney);
        realMoney = CalculateUtils.divideDouble(sumMoney , couponMoney, 2);
        Logger.e("优惠券金额:" + couponMoney + ";实际支付金额:" + realMoney);
        etShopCouponMoney.setText(String.valueOf(Double.valueOf(couponMoney).intValue()));
        etShopCouponMoney.setTextColor(getResources().getColor(R.color.color_text));
        etShopPayRealMoney.setText(String.valueOf(realMoney));
        etShopPayRealMoney.setTextColor(getResources().getColor(R.color.color_text));
        btnPaySure.setEnabled(true);
    }

    @Override
    public void onGetCouponSumSuccess(BaseModel<Integer> model) {
        sumCoupon = model.getData();
        etShopCouponMoney.setText(getResources().getString(R.string.shop_real_pay_hint) +  PriceUtil.divideCoupon(sumCoupon));
        etShopCouponMoney.setTextColor(Color.parseColor("#CCCCCC"));
    }

    @Override
    public void onOrderSuccess(BaseModel<OTOOrderResultBean> model) {
        if(model.getData() != null){
            Intent intent = new Intent(mContext, ShopPayOrderActivity.class);
            intent.putExtra(ShopPayOrderActivity.TOTAL_PRICE, model.getData().getOrderAmount());
            intent.putExtra(ShopPayOrderActivity.REAL_PRICE, model.getData().getPayAmount());
            intent.putExtra(ShopPayOrderActivity.COUPON_VALUE, model.getData().getDeductionCouponAmount());
            intent.putExtra(ShopPayOrderActivity.OTO_NO, model.getData().getOrderNo());
            ActivityUtils.startActivity(intent);
        }
    }
}
