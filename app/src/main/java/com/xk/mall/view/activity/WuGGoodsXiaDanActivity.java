package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.AddressServerBean;
import com.xk.mall.model.entity.WuGOrderBean;
import com.xk.mall.model.entity.WuGOrderResultBean;
import com.xk.mall.model.impl.WuGXiaDanViewImpl;
import com.xk.mall.presenter.WuGXiaDanPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.widget.PosterTipDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName WuGGoodsXiaDanActivity
 * Description 吾G 商品下单
 * Author
 * Date
 * Version
 */
public class WuGGoodsXiaDanActivity extends BaseActivity<WuGXiaDanPresenter> implements WuGXiaDanViewImpl {
    @BindView(R.id.tv_add_address)
    TextView tvAddAddress;//请添加收货地址
    @BindView(R.id.rl_wug_xiadan_no_adress)
    RelativeLayout rlWugXiadanNoAdress;//没有收货地址布局
    @BindView(R.id.tv_wug_xiadan_receiver_name)
    TextView tvWugXiadanReceiverName;//收货人姓名
    @BindView(R.id.tv_wug_xiadan_receiver_phone)
    TextView tvWugXiadanReceiverPhone;//收货人电话
    @BindView(R.id.tv_wug_xiadan_receiver_address)
    TextView tvWugXiadanReceiverAddress;//收货人地址
    @BindView(R.id.rl_wug_xiadan_address)
    RelativeLayout rlWugXiadanAddress;//收货人地址布局，点击选择地址
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;//总金额
    @BindView(R.id.btn_confirm_order)
    Button btnConfirmOrder;//确认下单
    @BindView(R.id.tv_wug_xiadan_sku)
    TextView tvWugXiadanSku;//sku
    @BindView(R.id.tv_wug_xiadan_num)
    TextView tvWugXiadanNum;//商品数量
    @BindView(R.id.tv_many_goods_xiadan_post)
    TextView tvManyGoodsXiadanPost;//邮费
    @BindView(R.id.tv_many_goods_xiadan_coupon)
    TextView tvCoupon;//赠送优惠券金额
    @BindView(R.id.tv_wug_xiadan_price)
    TextView tvWugXiadanPrice;//商品价格
    @BindView(R.id.tv_wug_xiadan_name)
    TextView tvWugXiadanName;//商品名称
    @BindView(R.id.iv_wug_xiadan_logo)
    ImageView ivWugXiadanLogo;
    @BindView(R.id.rl_wug_xiadan_other_pay)
    LinearLayout rlOtherPay;//请人代付
    @BindView(R.id.cb_wug_goods_xiadan)
    CheckBox cbOtherPay;
    @BindView(R.id.cb_wug_goods_xiadan_two)
    CheckBox cbFriendPay;//请朋友代付
    @BindView(R.id.tv_wug_xiadan_friend_phone)
    TextView tvFriendPhone;//朋友的手机号
    @BindView(R.id.rl_coupon)
    RelativeLayout rl_coupon;//优惠券布局
    @BindView(R.id.tv_wug_goods_xiadan_remarks)
    TextView tvRemarks;
    String remarks;//备注内容
    private boolean isShowOtherPay = true;//是否显示代付，目前只有全球买手和吾G购可以显示，定制拼团下单过来之后需要隐藏
    private boolean isOtherPay = false;//是否合伙人代付
    private boolean isFriendPay = false;//是否朋友代付

    private double totalPrice;
    /**传递过来的商品总价的key*/
    public static final String TOTAL_PRICE = "total_price";
    /**传递过来的对象的key**/
    public static final String XIA_DAN_DATA = "xia_dan_data";
    /**传递过来的邮费的key**/
    public static final String XIA_DAN_POSTAGE = "xia_dan_postage";
    /**传递过来的优惠券的key**/
    public static final String XIA_DAN_COUPON = "xia_dan_coupon";
    private AddressBean defaultAddress;
    private WuGOrderBean wuGOrderBean;
    private int coupon;//赠送的优惠券额度
    private int activityType;//订单类型
    @BindView(R.id.ll_address_out_range)
    LinearLayout llAddressOutRange;//下单地址超出范围view
    private boolean isOutRange;//当前地址是否超出配送范围

    @Override
    protected WuGXiaDanPresenter createPresenter() {
        return new WuGXiaDanPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wug_goods_xiadan;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setText("确认订单");
    }

    @Override
    protected void initData() {
        mPresenter.getAddress(MyApplication.userId);
        int totalPrice = getIntent().getIntExtra(TOTAL_PRICE, 0);
        wuGOrderBean = (WuGOrderBean) getIntent().getSerializableExtra(XIA_DAN_DATA);
        int postage = getIntent().getIntExtra(XIA_DAN_POSTAGE, 0);
        coupon = getIntent().getIntExtra(XIA_DAN_COUPON, 0);
        if(postage == 0){
            //包邮
            tvManyGoodsXiadanPost.setText("全国包邮");
        }else {
            tvManyGoodsXiadanPost.setText("邮费: " + PriceUtil.dividePrice(postage) + "元");
        }
        if(coupon == 0){
            activityType = ActivityType.ACTIVITY_NEW_PERSON;
            rl_coupon.setVisibility(View.GONE);
            rlOtherPay.setVisibility(View.GONE);
        }else {
            activityType = ActivityType.ACTIVITY_WUG;
            rl_coupon.setVisibility(View.VISIBLE);
            rlOtherPay.setVisibility(View.VISIBLE);
            tvCoupon.setText(PriceUtil.divideCoupon(coupon));
        }
        tvTotalMoney.setText(PriceUtil.dividePrice(totalPrice + postage));
        GlideUtil.showRadius(mContext, wuGOrderBean.getGoodsImageUrl(), 2, ivWugXiadanLogo);
        tvWugXiadanName.setText(wuGOrderBean.getGoodsName());
        tvWugXiadanNum.setText("x" + wuGOrderBean.getCommodityQuantity());
        tvWugXiadanSku.setText(wuGOrderBean.getCommodityModel() + " " +wuGOrderBean.getCommoditySpec());
        tvWugXiadanPrice.setText(String.valueOf(PriceUtil.dividePrice(wuGOrderBean.getUnitPrice())));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 101 && data != null){
            remarks = data.getStringExtra("remarks_content");
            tvRemarks.setText(remarks);
        }else if(resultCode == RESULT_OK && requestCode == 102 && data != null){
            tvFriendPhone.setText(data.getStringExtra("phone"));
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    /**
     * eventBus接收回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddress(AddressBean addressBean) {
        Logger.e("接收到地址"+addressBean.address);
        rlWugXiadanNoAdress.setVisibility(View.GONE);
        rlWugXiadanAddress.setVisibility(View.VISIBLE);
        if(addressBean != null){
            defaultAddress = addressBean;
            tvWugXiadanReceiverAddress.setText(XiKouUtils.getAddress(mContext,addressBean));
            tvWugXiadanReceiverName.setText(addressBean.consigneeName);
            tvWugXiadanReceiverPhone.setText(addressBean.consigneeMobile);
            isOutRange = !defaultAddress.outRange;
            if(isOutRange){
                llAddressOutRange.setVisibility(View.VISIBLE);
            }else {
                llAddressOutRange.setVisibility(View.GONE);
            }
        }

    }

    @OnClick({R.id.btn_confirm_order, R.id.cb_wug_goods_xiadan, R.id.cb_wug_goods_xiadan_two,R.id.rl_wug_xiadan_address, R.id.rl_wug_xiadan_no_adress,
            R.id.rl_wug_xiadan_remarks, R.id.ll_address_out_range, R.id.rl_wug_xiadan_other_pay_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_order://确认订单
                if(isFriendPay){//如果是请好友代付，需要判断是否填写了手机号
                    String phone = tvFriendPhone.getText().toString().trim();
                    if(XiKouUtils.isNullOrEmpty(phone)){
                        ToastUtils.showShortToast(mContext, "请输入为您代付的喜扣好友");
                        return;
                    }else {
                        orderFriend();
                    }
                }else {
                    order();
                }
                break;

            case R.id.cb_wug_goods_xiadan:
                if(isOtherPay){
                    cbOtherPay.setChecked(false);
                    isOtherPay = false;
                }else {
                    cbOtherPay.setChecked(true);
                    isOtherPay = true;
                    isFriendPay = false;
                    tvFriendPhone.setText("");
                    cbFriendPay.setChecked(false);
                }
                break;
            case R.id.cb_wug_goods_xiadan_two:
                if(isFriendPay){
                    cbFriendPay.setChecked(false);
                    tvFriendPhone.setText("");
                    isFriendPay = false;
                }else {
                    cbFriendPay.setChecked(true);
                    isFriendPay = true;
                    cbOtherPay.setChecked(false);
                    isOtherPay = false;
                }
                break;

            case R.id.rl_wug_xiadan_address:
            case R.id.rl_wug_xiadan_no_adress://添加地址
                Intent toAddress = new Intent(mContext, AddressActivity.class);
                toAddress.putExtra("is_xiadan",true);
                startActivity(toAddress);
                break;

            case R.id.rl_wug_xiadan_remarks:
                remarks = tvRemarks.getText().toString().trim();
                Intent intent = new Intent(mContext, RemarksActivity.class);
                intent.putExtra("remarks_content", remarks);
                startActivityForResult(intent, 101);
                break;

            case R.id.ll_address_out_range:
                //显示对话框
                new PosterTipDialog(mContext).show();
                break;

            case R.id.rl_wug_xiadan_other_pay_two:
                //点击跳转页面查询用户数据
                if(isFriendPay){
                    Intent intent1 = new Intent(mContext, SearchFriendActivity.class);
                    intent1.putExtra("phone", tvFriendPhone.getText().toString());
                    startActivityForResult(intent1, 102);
                }
                break;
        }
    }


    /**
     * 确认下单
     */
    private void orderFriend(){
        if(defaultAddress == null || TextUtils.isEmpty(defaultAddress.id)){
            ToastUtils.showShortToast(mContext, "请添加收货地址");
            return;
        }
        if(isOutRange){
            ToastUtils.showShortToast(mContext, getResources().getString(R.string.address_out_range));
            return;
        }
        wuGOrderBean.setReceiptAddressRef(defaultAddress.id);
        wuGOrderBean.setInsteadPay(isFriendPay ? 1 : 0);
        wuGOrderBean.setPayPhone(tvFriendPhone.getText().toString());
        if(remarks != null){
            wuGOrderBean.setRemarks(remarks);
        }
        mPresenter.order(activityType, wuGOrderBean);
    }

    /**
     * 确认下单
     */
    private void order(){
        if(defaultAddress == null || TextUtils.isEmpty(defaultAddress.id)){
            ToastUtils.showShortToast(mContext, "请添加收货地址");
            return;
        }
        if(isOutRange){
            ToastUtils.showShortToast(mContext, getResources().getString(R.string.address_out_range));
            return;
        }
        wuGOrderBean.setReceiptAddressRef(defaultAddress.id);
        wuGOrderBean.setInsteadPay(isOtherPay ? 1 : 0);
        wuGOrderBean.setPayPhone("");//合伙人代付或者直接下单没有手机号
        if(remarks != null){
            wuGOrderBean.setRemarks(remarks);
        }
        mPresenter.order(activityType, wuGOrderBean);
    }

    @Override
    public void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList) {
        if(addressBeanList.getData() != null && addressBeanList.getData().size() != 0){
            rlWugXiadanAddress.setVisibility(View.VISIBLE);
            rlWugXiadanNoAdress.setVisibility(View.GONE);
            bindAddress(addressBeanList.getData());
        }else {
            rlWugXiadanNoAdress.setVisibility(View.VISIBLE);
            rlWugXiadanAddress.setVisibility(View.GONE);
        }
    }

    @Override
    public void onOrderSuccess(BaseModel<WuGOrderResultBean> model) {
        if(isOtherPay){//选择代付
//            Intent intent = new Intent(mContext, OtherPayActivity.class);
//            intent.putExtra(OtherPayActivity.GOODS_NAME, model.getData().getGoods().getGoodsName());
//            intent.putExtra(OtherPayActivity.TOTAL_PRICE, model.getData().getPayAmount());
//            intent.putExtra(OtherPayActivity.ORDER_NUMBER, model.getData().getOrderNo());
//            intent.putExtra(OtherPayActivity.PAYMENT_ID, model.getData().getInsteadPaymentId());
//            intent.putExtra(OtherPayActivity.ORDER_TYPE, OrderType.WUG_TYPE);
//            ActivityUtils.startActivity(intent);
            ToastUtils.showShortToast(mContext, "发起代付成功");
            finish();
        }else if(isFriendPay){
            ToastUtils.showShortToast(mContext, "发起代付成功");
            finish();
        }else {
            //跳转支付页面
            Intent intent = new Intent(mContext, PayOrderActivity.class);
            intent.putExtra(PayOrderActivity.GOODS_NAME, model.getData().getGoods().getGoodsName());
            intent.putExtra(PayOrderActivity.TOTAL_PRICE, model.getData().getPayAmount());
            intent.putExtra(PayOrderActivity.ORDER_NUMBER, model.getData().getOrderNo());
            intent.putExtra(PayOrderActivity.COUPON_VALUE, coupon);
            if(activityType == ActivityType.ACTIVITY_WUG){
                intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, true);
                intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.WUG_TYPE);
            }else if(activityType == ActivityType.ACTIVITY_NEW_PERSON){
                intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
                intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.NEW_PERSON_TYPE);
            }
            ActivityUtils.startActivity(intent);
        }
        finish();
    }


    @Override
    public void onErrorCode(BaseModel model) {
        ToastUtils.showShortToast(mContext, model.getMsg());
    }

    /**
     * 获取地址成功之后的绑定地址
     */
    private void bindAddress(List<AddressBean> result) {
        for(AddressBean addressBean : result){
            if(addressBean.defaultId == 1){//显示默认地址
                defaultAddress = addressBean;
                tvWugXiadanReceiverName.setText(addressBean.consigneeName);
                tvWugXiadanReceiverPhone.setText(addressBean.consigneeMobile);
                tvWugXiadanReceiverAddress.setText(XiKouUtils.getAddress(mContext,defaultAddress));
            }
        }

        if(defaultAddress == null && result.size() != 0){
            defaultAddress = result.get(0);
            tvWugXiadanReceiverName.setText(defaultAddress.consigneeName);
            tvWugXiadanReceiverPhone.setText(defaultAddress.consigneeMobile);
            tvWugXiadanReceiverAddress.setText(XiKouUtils.getAddress(mContext,defaultAddress));
        }
        if(defaultAddress != null && !defaultAddress.outRange){
            //显示超出配送范围
            isOutRange = true;
            llAddressOutRange.setVisibility(View.VISIBLE);
        }

    }
}
