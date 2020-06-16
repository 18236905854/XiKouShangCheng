package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ManyBuyOrderRequestBean;
import com.xk.mall.model.entity.ManyBuyOrderResultBean;
import com.xk.mall.model.entity.ManyCartsBean;
import com.xk.mall.model.eventbean.OrderSuccessBean;
import com.xk.mall.model.impl.ManyGoodsXiaDanViewImpl;
import com.xk.mall.presenter.ManyGoodsXiaDanPresenter;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.widget.OrderTipDialog;
import com.xk.mall.view.widget.PosterTipDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName ManyGoodsXiaDanActivity
 * Description 多买多折 商品下单
 * Author
 * Date
 * Version
 */
public class ManyGoodsXiaDanActivity extends BaseActivity<ManyGoodsXiaDanPresenter> implements ManyGoodsXiaDanViewImpl {
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.btn_confirm_order)
    Button btnConfirmOrder;

    List<ManyCartsBean> selectList = new ArrayList<>();//购买的商品
    List<ManyCartsBean.ListBean> filterList = new ArrayList<>();
    private int totalPrice;
    private AddressBean defaultAddress;//默认地址
    private ManyXiaDanAdapter xiaDanAdapter;
    private String activityId = "";//活动ID
    private String addressId = "";//默认地址ID
    private int clickPos = -1;//点击的位置
    private AddressBean addressOne;//第一个地址
    private AddressBean addressTow;//第二个地址
    private AddressBean addressThree;//第三个地址
    private boolean isOutRange;//是否超出范围
    /**intent传递过来的总价的key*/
    public static String TOTAL_PRICE = "total_price";

    @Override
    protected ManyGoodsXiaDanPresenter createPresenter() {
        return new ManyGoodsXiaDanPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_many_goods_xiadan;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setText("确认订单");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(this);
        selectList = (List<ManyCartsBean>) getIntent().getSerializableExtra("select_data");
        totalPrice = getIntent().getIntExtra(TOTAL_PRICE, 0);
        tvTotalMoney.setText(PriceUtil.dividePrice(totalPrice));
        mPresenter.getAddress(MyApplication.userId);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        xiaDanAdapter = new ManyXiaDanAdapter(mContext, R.layout.many_goods_xia_dan_item, selectList);
        recycleView.setAdapter(xiaDanAdapter);
        for(ManyCartsBean manyCartsBean : selectList){
            for(ManyCartsBean.ListBean listBean : manyCartsBean.getList()){
                if(listBean.getPosition() != 0){
                    listBean.setMerchantId(manyCartsBean.getMerchantId());
                    filterList.add(listBean);
                }
            }
        }
    }

    @Override
    public void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList) {
        if(addressBeanList.getData() != null && addressBeanList.getData().size() != 0){
            bindAddress(addressBeanList.getData());
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
                addressId = addressBean.id;
                if(xiaDanAdapter != null){
                    xiaDanAdapter.notifyDataSetChanged();
                }
            }
        }
        if(defaultAddress == null){
            defaultAddress = result.get(0);
            addressId = defaultAddress.id;
            if(xiaDanAdapter != null){
                xiaDanAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onOrderSuccess(BaseModel<ManyBuyOrderResultBean> model) {
        Intent intent = new Intent(mContext,PayOrderActivity.class);
        intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
        intent.putExtra(PayOrderActivity.TOTAL_PRICE, totalPrice);
        String orderNo = "";
//        if(model.getData().getList().size() != 0){
//            orderNo = model.getData().getList().get(0).getOrderNo();
//        }else {
            orderNo = model.getData().getTradeNo();
//        }
        intent.putExtra(PayOrderActivity.ORDER_NUMBER, orderNo);
        intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.MANY_TYPE);
        startActivity(intent);
        finish();
        EventBus.getDefault().post(new OrderSuccessBean());
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
        if(addressBean != null && clickPos != -1){
            if(clickPos == 0){
                addressOne = addressBean;
            }else if(clickPos == 1){
                addressTow = addressBean;
            }else if(clickPos == 2){
                addressThree = addressBean;
            }
            if(defaultAddress == null || TextUtils.isEmpty(defaultAddress.id)){
                defaultAddress =addressBean;
            }
//            defaultAddress = addressBean;
            xiaDanAdapter.notifyDataSetChanged();
        }
    }

    class ManyXiaDanAdapter extends CommonAdapter<ManyCartsBean>{

        public ManyXiaDanAdapter(Context context, int layoutId, List<ManyCartsBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ManyCartsBean manyCartBean, int position) {
            holder.setText(R.id.tv_many_goods_xiadan_shop_name, manyCartBean.getMerchantName());
            RecyclerView rvChild = holder.getView(R.id.rv_many_goods_xia_dan);
            if(manyCartBean.getPostage() == 0){
                holder.setText(R.id.tv_many_goods_xiadan_post, "全国包邮");
            }else {
                holder.setText(R.id.tv_many_goods_xiadan_post, "邮费:" + manyCartBean.getPostage() + "元");
            }
            rvChild.setLayoutManager(new LinearLayoutManager(mContext));
            TextView tvName = holder.getView(R.id.tv_many_goods_xiadan_receiver_name);
            TextView tvPhone = holder.getView(R.id.tv_many_goods_xiadan_receiver_phone);
            TextView tvAddress = holder.getView(R.id.tv_many_goods_xiadan_address);
            TextView tvRemarks = holder.getView(R.id.tv_many_goods_xiadan_remarks);
            tvRemarks.setText(manyCartBean.getRemarks() == null? "" : manyCartBean.getRemarks());
            List<ManyCartsBean.ListBean> selected = new ArrayList<>();
            for(ManyCartsBean.ListBean listBean : manyCartBean.getList()){
                if(listBean.getPosition() != 0){
                    listBean.setRemarks(manyCartBean.getRemarks());
                    selected.add(listBean);
                }
            }
            ManyXiaDanChildAdapter childAdapter = new ManyXiaDanChildAdapter(mContext, R.layout.many_goods_xia_dan_child_item, selected);
            rvChild.setAdapter(childAdapter);
            //如果用户有默认地址则全部显示默认地址，如果没有，则显示新建地址
            RelativeLayout rlAddress = holder.getView(R.id.rl_many_xiadan_address);//默认地址显示
            RelativeLayout rlNoAddress = holder.getView(R.id.rl_many_goods_xiadan_no_adress);//没有默认地址，显示新建
            LinearLayout llOutRange = holder.getView(R.id.ll_address_out_range);
            llOutRange.setOnClickListener(view -> {
                //显示对话框
                new PosterTipDialog(mContext).show();
            });
            if(position == 0){
                Logger.e("位置0");
                if(addressOne != null){
                    Logger.e("位置0 + 0");
                    rlAddress.setVisibility(View.VISIBLE);
                    rlNoAddress.setVisibility(View.GONE);
                    tvName.setText(addressOne.consigneeName);
                    tvPhone.setText(addressOne.consigneeMobile);
                    tvAddress.setText(XiKouUtils.getAddress(mContext, addressOne));
                    isOutRange = !addressOne.outRange;
                    if(!addressOne.outRange){
                        llOutRange.setVisibility(View.VISIBLE);
                    }else {
                        llOutRange.setVisibility(View.GONE);
                    }
                }else if(defaultAddress != null){
                    rlAddress.setVisibility(View.VISIBLE);
                    rlNoAddress.setVisibility(View.GONE);
                    tvName.setText(defaultAddress.consigneeName);
                    tvPhone.setText(defaultAddress.consigneeMobile);
                    tvAddress.setText(XiKouUtils.getAddress(mContext, defaultAddress));
                    isOutRange = !defaultAddress.outRange;
                    if(!defaultAddress.outRange){
                        llOutRange.setVisibility(View.VISIBLE);
                    }else {
                        llOutRange.setVisibility(View.GONE);
                    }
                }else {
                    rlAddress.setVisibility(View.GONE);
                    rlNoAddress.setVisibility(View.VISIBLE);
                }
            }else if(position == 1){
                Logger.e("位置1");
                if(addressTow != null){
                    Logger.e("位置0 + 1");
                    rlAddress.setVisibility(View.VISIBLE);
                    rlNoAddress.setVisibility(View.GONE);
                    tvName.setText(addressTow.consigneeName);
                    tvPhone.setText(addressTow.consigneeMobile);
                    tvAddress.setText(XiKouUtils.getAddress(mContext, addressTow));
                    isOutRange = !addressTow.outRange;
                    if(!addressTow.outRange){
                        llOutRange.setVisibility(View.VISIBLE);
                    }else {
                        llOutRange.setVisibility(View.GONE);
                    }
                }else if(defaultAddress != null){
                    rlAddress.setVisibility(View.VISIBLE);
                    rlNoAddress.setVisibility(View.GONE);
                    tvName.setText(defaultAddress.consigneeName);
                    tvPhone.setText(defaultAddress.consigneeMobile);
                    tvAddress.setText(XiKouUtils.getAddress(mContext, defaultAddress));
                    isOutRange = !defaultAddress.outRange;
                    if(!defaultAddress.outRange){
                        llOutRange.setVisibility(View.VISIBLE);
                    }else {
                        llOutRange.setVisibility(View.GONE);
                    }
                }else {
                    rlAddress.setVisibility(View.GONE);
                    rlNoAddress.setVisibility(View.VISIBLE);
                }
            }else if(position == 2){
                Logger.e("位置2");
                if(addressThree != null){
                    Logger.e("位置0 + 2");
                    rlAddress.setVisibility(View.VISIBLE);
                    rlNoAddress.setVisibility(View.GONE);
                    tvName.setText(addressThree.consigneeName);
                    tvPhone.setText(addressThree.consigneeMobile);
                    tvAddress.setText(XiKouUtils.getAddress(mContext, addressThree));
                    isOutRange = !addressThree.outRange;
                    if(!addressThree.outRange){
                        llOutRange.setVisibility(View.VISIBLE);
                    }else {
                        llOutRange.setVisibility(View.GONE);
                    }
                }else if(defaultAddress != null){
                    rlAddress.setVisibility(View.VISIBLE);
                    rlNoAddress.setVisibility(View.GONE);
                    tvName.setText(defaultAddress.consigneeName);
                    tvPhone.setText(defaultAddress.consigneeMobile);
                    tvAddress.setText(XiKouUtils.getAddress(mContext, defaultAddress));
                    isOutRange = !defaultAddress.outRange;
                    if(!defaultAddress.outRange){
                        llOutRange.setVisibility(View.VISIBLE);
                    }else {
                        llOutRange.setVisibility(View.GONE);
                    }
                }else {
                    rlAddress.setVisibility(View.GONE);
                    rlNoAddress.setVisibility(View.VISIBLE);
                }
            }

            RelativeLayout rlRemarks = holder.getView(R.id.rl_many_xiadan_remarks);
            rlRemarks.setOnClickListener(v -> {
                clickPos = position;
                String remarks = tvRemarks.getText().toString().trim();
                Intent intent = new Intent(mContext, RemarksActivity.class);
                intent.putExtra("remarks_content", remarks);
                startActivityForResult(intent, 101);
            });

            rlAddress.setOnClickListener(v -> {
                //点击选择收货地址
                clickPos = position;
                Intent toAddress = new Intent(mContext, AddressActivity.class);
                toAddress.putExtra("is_xiadan",true);
//                toAddress.putExtra("click_pos", position);
                startActivity(toAddress);
            });

            rlNoAddress.setOnClickListener(v -> {
                //点击新建地址
                clickPos = position;
                Intent toAddress = new Intent(mContext, AddressActivity.class);
                toAddress.putExtra("is_xiadan",true);
//                toAddress.putExtra("click_pos", position);
                startActivity(toAddress);
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 101 && data != null){
            String remarks = data.getStringExtra("remarks_content");
            ManyCartsBean listBean = selectList.get(clickPos);
            if(remarks != null){
                listBean.setRemarks(remarks);
            }
            xiaDanAdapter.notifyItemChanged(clickPos);
        }
    }

    /**
     * 多买多折下单子adapter
     */
    class ManyXiaDanChildAdapter extends CommonAdapter<ManyCartsBean.ListBean>{

        public ManyXiaDanChildAdapter(Context context, int layoutId, List<ManyCartsBean.ListBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ManyCartsBean.ListBean listBean, int position) {
            holder.setText(R.id.tv_many_goods_xiadan_name, listBean.getCommodityName());
            activityId = listBean.getActivityId();
            holder.setText(R.id.tv_many_goods_xiadan_sku, listBean.getCommodityModel() + "  " + listBean.getCommoditySpec()
             + "    原价:¥" + PriceUtil.dividePrice(listBean.getSalePrice()));
            holder.setText(R.id.tv_many_goods_xiadan_price, getResources().getString(R.string.money) + PriceUtil.dividePrice(listBean.getActivityPrice()));
            holder.setText(R.id.tv_many_cart_num, "x" + listBean.getBuyerNumber());
            float discount = 0;
            if(listBean.getPosition() == 1){
                if(listBean.getBuyerNumber() == 2){
                    discount = SPUtils.getInstance().getFloat(Constant.RATE_TWO);
                    holder.setText(R.id.tv_many_cart_discount, changeFloatToString("最低", discount));
                }else if(listBean.getBuyerNumber() == 3){
                    discount = SPUtils.getInstance().getFloat(Constant.RATE_THREE);
                    holder.setText(R.id.tv_many_cart_discount, changeFloatToString("最低", discount));
                }else {
                    discount = SPUtils.getInstance().getFloat(Constant.RATE_ONE);
                    holder.setText(R.id.tv_many_cart_discount, changeFloatToString("折扣:", discount));
                }
            }else if(listBean.getPosition() == 2){
                if(listBean.getBuyerNumber() == 2){
                    discount = SPUtils.getInstance().getFloat(Constant.RATE_THREE);
                    holder.setText(R.id.tv_many_cart_discount, changeFloatToString("最低", discount));
                }else {
                    discount = SPUtils.getInstance().getFloat(Constant.RATE_TWO);
                    holder.setText(R.id.tv_many_cart_discount, changeFloatToString("折扣:", discount));
                }
            }else if(listBean.getPosition() == 3){
                discount = SPUtils.getInstance().getFloat(Constant.RATE_THREE);
                holder.setText(R.id.tv_many_cart_discount, changeFloatToString("折扣:", discount));
            }
            ImageView ivLogo = holder.getView(R.id.iv_many_goods_xiadan_logo);
            GlideUtil.showRadius(mContext, listBean.getGoodsImageUrl(), 2, ivLogo);
        }
    }

    /**
     * 将float转为String
     */
    private String changeFloatToString(String text, float discount){
        double result = Float.valueOf(discount).doubleValue();
        String discountStr;
        if(result % 1.0 == 0){
            discountStr = String.valueOf((long)result);
        }else {
            discountStr = String.valueOf(result);
        }
        return  text + discountStr + "折";
    }

    @OnClick({R.id.btn_confirm_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_order://确认订单
                showTipDialog();
                break;
        }
    }

    /**
     * 显示提示对话框
     */
    private void showTipDialog(){
        new OrderTipDialog(mContext, R.style.mydialog, (dialog, confirm) -> {
            if(confirm){
                //确定之后创建订单，下单成功之后跳转支付页面
                order();
            }
        }).show();
    }

    /**
     * 下单方法
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
        ManyBuyOrderRequestBean manyBuyOrderRequestBean = new ManyBuyOrderRequestBean();
        manyBuyOrderRequestBean.setActivityId(activityId);
        manyBuyOrderRequestBean.setBuyerId(MyApplication.userId);
        manyBuyOrderRequestBean.setOrderSource(1);
        manyBuyOrderRequestBean.setTotalAmount(totalPrice);
        List<ManyBuyOrderRequestBean.BuyMoreFoldsVoListBean> moreFoldsVoListBeans = new ArrayList<>();
        for(int i = 0; i < filterList.size(); i++){
            ManyCartsBean.ListBean listBean = filterList.get(i);
            ManyBuyOrderRequestBean.BuyMoreFoldsVoListBean moreFoldsVoListBean = new ManyBuyOrderRequestBean.BuyMoreFoldsVoListBean();
            moreFoldsVoListBean.setActivityGoodsId(listBean.getActivityGoodsId());
            moreFoldsVoListBean.setBuyerCartId(listBean.getId());
            moreFoldsVoListBean.setBuyNumber(listBean.getBuyerNumber());
            moreFoldsVoListBean.setCommodityId(listBean.getCommodityId());
            moreFoldsVoListBean.setCommodityModel(listBean.getCommodityModel());
            moreFoldsVoListBean.setCommoditySpec(listBean.getCommoditySpec());
            moreFoldsVoListBean.setDiscount(listBean.getDiscount());
            moreFoldsVoListBean.setRemarks(listBean.getRemarks());
//            moreFoldsVoListBean.setGoodsCode(listBean.get);//商品货号
            moreFoldsVoListBean.setGoodsId(listBean.getGoodsId());
            moreFoldsVoListBean.setGoodsImageUrl(listBean.getGoodsImageUrl());
            moreFoldsVoListBean.setGoodsName(listBean.getCommodityName());
            moreFoldsVoListBean.setMerchantId(listBean.getMerchantId());//商家ID
            float discount = 0;
            if(listBean.getPosition() == 1){
                discount = SPUtils.getInstance().getFloat(Constant.RATE_ONE);
            }else if(listBean.getPosition() == 2){
                discount = SPUtils.getInstance().getFloat(Constant.RATE_TWO);
            }else if(listBean.getPosition() == 3){
                discount = SPUtils.getInstance().getFloat(Constant.RATE_THREE);
            }
            Float orderAmount = listBean.getActivityPrice() * discount / 10;
            moreFoldsVoListBean.setOrderAmount(orderAmount.intValue());
            moreFoldsVoListBean.setOrderNo(listBean.getPosition());
            moreFoldsVoListBean.setSalePrice(listBean.getSalePrice());
            moreFoldsVoListBeans.add(moreFoldsVoListBean);
            if(i == 0 ){
                if(addressOne != null){
                    moreFoldsVoListBean.setReceiptAddressRef(addressOne.id);
                }else {
                    moreFoldsVoListBean.setReceiptAddressRef(defaultAddress.id);
                }
            }else if(i == 1){
                if(addressTow != null){
                    moreFoldsVoListBean.setReceiptAddressRef(addressTow.id);
                }else {
                    moreFoldsVoListBean.setReceiptAddressRef(defaultAddress.id);
                }
            }else if(i == 2){
                if(addressThree != null){
                    moreFoldsVoListBean.setReceiptAddressRef(addressThree.id);
                }else {
                    moreFoldsVoListBean.setReceiptAddressRef(defaultAddress.id);
                }
            }
        }
        //同一个商品多个数量处理
//        for(int i = 0; i < filterList.size(); i++){
//            ManyCartsBean.ListBean listBean = filterList.get(i);
//            for(int j = 0; j < listBean.getBuyerNumber(); j++) {
//                ManyBuyOrderRequestBean.BuyMoreFoldsVoListBean moreFoldsVoListBean = new ManyBuyOrderRequestBean.BuyMoreFoldsVoListBean();
//                moreFoldsVoListBean.setActivityGoodsId(listBean.getGoodsId());
//                moreFoldsVoListBean.setBuyerCartId(listBean.getId());
//                moreFoldsVoListBean.setBuyNumber(1);
//                moreFoldsVoListBean.setCommodityId(listBean.getCommodityId());
//                moreFoldsVoListBean.setCommodityModel(listBean.getCommodityModel());
//                moreFoldsVoListBean.setCommoditySpec(listBean.getCommoditySpec());
//                moreFoldsVoListBean.setDiscount(listBean.getDiscount());
////            moreFoldsVoListBean.setGoodsCode(listBean.get);//商品货号
//                moreFoldsVoListBean.setGoodsId(listBean.getGoodsId());
//                moreFoldsVoListBean.setGoodsImageUrl(listBean.getGoodsImageUrl());
//                moreFoldsVoListBean.setGoodsName(listBean.getCommodityName());
//                moreFoldsVoListBean.setMerchantId(listBean.getMerchantId());//商家ID
//                float discount = 0;
//                if (listBean.getPosition() == 1) {
//                    discount = SPUtils.getInstance().getFloat(Constant.RATE_ONE);
//                } else if (listBean.getPosition() == 2) {
//                    discount = SPUtils.getInstance().getFloat(Constant.RATE_TWO);
//                } else if (listBean.getPosition() == 3) {
//                    discount = SPUtils.getInstance().getFloat(Constant.RATE_THREE);
//                }
//                Float orderAmount = listBean.getActivityPrice() * discount / 10;
//                moreFoldsVoListBean.setOrderAmount(orderAmount.intValue());
//                if (j != 0) {
//                    moreFoldsVoListBean.setOrderNo(listBean.getPosition() + 1);
//                } else {
//                    moreFoldsVoListBean.setOrderNo(listBean.getPosition());
//                }
//                moreFoldsVoListBean.setSalePrice(listBean.getSalePrice());
//                moreFoldsVoListBeans.add(moreFoldsVoListBean);
//                if (i == 0) {
//                    if (addressOne != null) {
//                        moreFoldsVoListBean.setReceiptAddressRef(addressOne.id);
//                    } else {
//                        moreFoldsVoListBean.setReceiptAddressRef(defaultAddress.id);
//                    }
//                } else if (i == 1) {
//                    if (addressTow != null) {
//                        moreFoldsVoListBean.setReceiptAddressRef(addressTow.id);
//                    } else {
//                        moreFoldsVoListBean.setReceiptAddressRef(defaultAddress.id);
//                    }
//                } else if (i == 2) {
//                    if (addressThree != null) {
//                        moreFoldsVoListBean.setReceiptAddressRef(addressThree.id);
//                    } else {
//                        moreFoldsVoListBean.setReceiptAddressRef(defaultAddress.id);
//                    }
//                }
//            }
//        }
        manyBuyOrderRequestBean.setBuyMoreFoldsVoList(moreFoldsVoListBeans);
        mPresenter.order(manyBuyOrderRequestBean);
    }
}
