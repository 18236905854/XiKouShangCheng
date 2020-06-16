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
import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.GlobalBuyerOrderBean;
import com.xk.mall.model.entity.GlobalBuyerOrderResultBean;
import com.xk.mall.model.impl.GlobalBuyerXiaDanViewImpl;
import com.xk.mall.presenter.GlobalBuyerXiaDanPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.PosterTipDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName GlobalBuyerXiaDanActivity
 * Description 全球买手商品下单页面
 * Author 卿凯
 * Date 2019/7/13/013
 * Version V1.0
 */
public class GlobalBuyerXiaDanActivity extends BaseActivity<GlobalBuyerXiaDanPresenter> implements GlobalBuyerXiaDanViewImpl {
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
    @BindView(R.id.iv_wug_xiadan_logo)
    ImageView ivWugXiadanLogo;//商品Logo
    @BindView(R.id.tv_wug_xiadan_price)
    TextView tvWugXiadanPrice;//商品价格
    @BindView(R.id.tv_wug_xiadan_name)
    TextView tvWugXiadanName;//商品名称
    @BindView(R.id.tv_wug_xiadan_sku)
    TextView tvWugXiadanSku;//sku
    @BindView(R.id.tv_wug_xiadan_num)
    TextView tvWugXiadanNum;//商品数量
    @BindView(R.id.tv_many_goods_xiadan_post)
    TextView tvManyGoodsXiadanPost;//邮费
    @BindView(R.id.cb_wug_goods_xiadan)
    CheckBox cbWugGoodsXiadan;//请人代付选择框
    @BindView(R.id.cb_wug_goods_xiadan_two)
    CheckBox cbFriendPay;//请朋友代付
    @BindView(R.id.tv_wug_xiadan_friend_phone)
    TextView tvFriendPhone;//朋友的手机号
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;//订单金额
    @BindView(R.id.tv_many_goods_xiadan_coupon)
    TextView tvCoupon;//使用优惠券金额
    @BindView(R.id.btn_confirm_order)
    Button btnConfirmOrder;//确认下单按钮
    @BindView(R.id.ll_address_out_range)
    LinearLayout llAddressOutRange;
    /**传递过来的商品总价的key*/
    public static final String TOTAL_PRICE = "total_price";
    /**传递过来的商品单价的key*/
    public static final String UNIT_PRICE = "unit_price";
    /**传递过来的对象的key**/
    public static final String XIA_DAN_DATA = "xia_dan_data";
    /**传递过来的邮费的key**/
    public static final String XIA_DAN_POSTAGE = "xia_dan_postage";
    /**传递过来的优惠券的key**/
    public static final String XIA_DAN_COUPON = "xia_dan_coupon";
    private AddressBean defaultAddress;
    private boolean isOutRange;

    private GlobalBuyerOrderBean globalBuyerOrderBean;
    private boolean isOtherPay = false;
    private boolean isFriendPay = false;
    private int coupon;//优惠券金额

    @BindView(R.id.tv_global_goods_xiadan_remarks)
    TextView tvRemarks;
    String remarks;//备注内容
    int type = 0;//点击自己下单还是请人代付  1:自己下单  2:请人代付

    @Override
    protected GlobalBuyerXiaDanPresenter createPresenter() {
        return new GlobalBuyerXiaDanPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_global_buyer_xiadan;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("确认订单");
    }

    @Override
    protected void initData() {
        mPresenter.getAddress(MyApplication.userId);
        int totalPrice = getIntent().getIntExtra(TOTAL_PRICE, 0);
        globalBuyerOrderBean = (GlobalBuyerOrderBean) getIntent().getSerializableExtra(XIA_DAN_DATA);
        int postage = getIntent().getIntExtra(XIA_DAN_POSTAGE, 0);
        coupon = getIntent().getIntExtra(XIA_DAN_COUPON, 0);
        int unitPrice = getIntent().getIntExtra(UNIT_PRICE, 0);
        if(postage == 0){
            //包邮
            tvManyGoodsXiadanPost.setText("全国包邮");
        }else {
            tvManyGoodsXiadanPost.setText("邮费: " + PriceUtil.dividePrice(postage) + "元");
        }
        tvCoupon.setText(PriceUtil.divideCoupon(coupon));
        tvTotalMoney.setText(PriceUtil.dividePrice(totalPrice + postage));
        GlideUtil.showRadius(mContext, globalBuyerOrderBean.getGoodsImageUrl(), 2, ivWugXiadanLogo);
        tvWugXiadanName.setText(globalBuyerOrderBean.getGoodsName());
        tvWugXiadanNum.setText("x" + globalBuyerOrderBean.getCommodityQuantity());
        tvWugXiadanSku.setText(globalBuyerOrderBean.getCommodityModel() + " " +globalBuyerOrderBean.getCommoditySpec());
        tvWugXiadanPrice.setText("" + PriceUtil.dividePrice(unitPrice));

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
            isOutRange = !addressBean.outRange;
            if(isOutRange){
                llAddressOutRange.setVisibility(View.VISIBLE);
            }else {
                llAddressOutRange.setVisibility(View.GONE);
            }
        }

    }

    @OnClick({R.id.btn_confirm_order,R.id.cb_wug_goods_xiadan, R.id.rl_wug_xiadan_no_adress, R.id.rl_wug_xiadan_address,
                    R.id.rl_global_xiadan_remarks, R.id.ll_address_out_range, R.id.rl_wug_xiadan_other_pay_two, R.id.cb_wug_goods_xiadan_two})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.btn_confirm_order://确认下单
                if(isFriendPay){//如果是请好友代付，需要判断是否填写了手机号
                    String phone = tvFriendPhone.getText().toString().trim();
                    if(XiKouUtils.isNullOrEmpty(phone)){
                        ToastUtils.showShortToast(mContext, "请输入为您代付的喜扣好友");
                        return;
                    }else {
                        type = 2;
                        //请求接口查询该商品是否可以寄卖到吾G，是否限额
                        mPresenter.getOutSellContent(globalBuyerOrderBean.getCommodityId(), MyApplication.userId);
                    }
                }else {
                    type = 1;
                    //请求接口查询该商品是否可以寄卖到吾G，是否限额
                    mPresenter.getOutSellContent(globalBuyerOrderBean.getCommodityId(), MyApplication.userId);
                }
                break;

            case R.id.rl_wug_xiadan_address:
            case R.id.rl_wug_xiadan_no_adress://添加地址
                Intent toAddress = new Intent(mContext, AddressActivity.class);
                toAddress.putExtra("is_xiadan",true);
                startActivity(toAddress);
                break;

            case R.id.cb_wug_goods_xiadan:
                if(isOtherPay){
                    cbWugGoodsXiadan.setChecked(false);
                    isOtherPay = false;
                }else {
                    cbWugGoodsXiadan.setChecked(true);
                    isFriendPay = false;
                    cbFriendPay.setChecked(false);
                    isOtherPay = true;
                }
                break;

            case R.id.cb_wug_goods_xiadan_two:
                if(isFriendPay){
                    cbFriendPay.setChecked(false);
                    isFriendPay = false;
                }else {
                    cbFriendPay.setChecked(true);
                    isOtherPay = false;
                    cbWugGoodsXiadan.setChecked(false);
                    isFriendPay = true;
                }
                break;

            case R.id.rl_global_xiadan_remarks:
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
        globalBuyerOrderBean.setReceiptAddressRef(defaultAddress.id);
        globalBuyerOrderBean.setInsteadPay(isFriendPay ? 1 : 0);
        globalBuyerOrderBean.setPayPhone(tvFriendPhone.getText().toString());
        if(remarks != null){
            globalBuyerOrderBean.setRemarks(remarks);
        }
        mPresenter.order(globalBuyerOrderBean);
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
        int couponSum = SPUtils.getInstance().getInt(Constant.COUPON_SUM);
        if(couponSum < coupon){
            ToastUtils.showShortToast(mContext, "您的优惠券不够哦");
            return;
        }
        globalBuyerOrderBean.setReceiptAddressRef(defaultAddress.id);
        globalBuyerOrderBean.setInsteadPay(isOtherPay ? 1 : 0);
        if(remarks != null){
            globalBuyerOrderBean.setRemarks(remarks);
        }
        mPresenter.order(globalBuyerOrderBean);
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

    /**
     * 下单成功的回调
     */
    @Override
    public void onOrderSuccess(BaseModel<GlobalBuyerOrderResultBean> model) {
        if(isOtherPay){//选择代付
//            Intent intent = new Intent(mContext, OtherPayActivity.class);
//            intent.putExtra(OtherPayActivity.GOODS_NAME, model.getData().getGoods().getGoodsName());
//            intent.putExtra(OtherPayActivity.TOTAL_PRICE, model.getData().getPayAmount());
//            intent.putExtra(OtherPayActivity.ORDER_NUMBER, model.getData().getOrderNo());
//            intent.putExtra(OtherPayActivity.PAYMENT_ID, model.getData().getInsteadPaymentId());
//            intent.putExtra(OtherPayActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
//            ActivityUtils.startActivity(intent);
            //关闭页面提示发起代付成功
            ToastUtils.showShortToast(mContext, "发起代付成功");
            finish();
        }else if(isFriendPay){
            ToastUtils.showShortToast(mContext, "发起代付成功");
            finish();
        }else{
            //跳转支付页面
            Intent intent = new Intent(mContext, PayOrderActivity.class);
            intent.putExtra(PayOrderActivity.GOODS_NAME, model.getData().getGoods().getGoodsName());
            intent.putExtra(PayOrderActivity.TOTAL_PRICE, model.getData().getPayAmount());
            intent.putExtra(PayOrderActivity.ORDER_NUMBER, model.getData().getOrderNo());
            intent.putExtra(PayOrderActivity.COUPON_VALUE, coupon);
            intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, true);
            intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.GLOBAL_TYPE);
            ActivityUtils.startActivity(intent);
        }
        finish();
    }

    @Override
    public void onGetOutSellContent(BaseModel<String> model) {
        if(model != null){
            if(type == 1){//自己下单
                if(XiKouUtils.isNullOrEmpty(model.getData())){
                    order();
                    type = 0;
                }else {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", model.getData(),
                            "确定", (dialog, confirm) -> {
                        if(confirm){
                            order();
                            type = 0;
                        }
                    }).show();
                }
            }else if(type == 2){//请人代付
                if(XiKouUtils.isNullOrEmpty(model.getData())){
                    orderFriend();
                    type = 0;
                }else {
                    new OrderTipDialog(mContext, R.style.mydialog, "提示", model.getData(),
                            "确定", (dialog, confirm) -> {
                        if(confirm){
                            orderFriend();
                            type = 0;
                        }
                    }).show();
                }
            }
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
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
