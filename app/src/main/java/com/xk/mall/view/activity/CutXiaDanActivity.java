package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ConfirmCutOrderBean;
import com.xk.mall.model.impl.CutXiaDanViewImpl;
import com.xk.mall.model.request.CutOrderRequestBody;
import com.xk.mall.presenter.CutXiaDanPresenter;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.PosterTipDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 喜立得  下单
 */
public class CutXiaDanActivity extends BaseActivity<CutXiaDanPresenter> implements CutXiaDanViewImpl {

    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.btn_confirm_order)
    Button btnConfirmOrder;

    @BindView(R.id.rl_no_adress)
    RelativeLayout rlNoAdress;
    @BindView(R.id.tv_receiver_name)
    TextView tvReceiverName;
    @BindView(R.id.tv_receiver_phone)
    TextView tvReceiverPhone;
    @BindView(R.id.tv_receiver_address)
    TextView tvReceiverAddress;
    @BindView(R.id.rl_have_address)
    RelativeLayout rlHaveAddress;

    @BindView(R.id.iv_xiadan_logo)
    ImageView ivXiadanLogo;
    @BindView(R.id.tv_xiadan_price)
    TextView tvXiadanPrice;
    @BindView(R.id.tv_wug_xiadan_name)
    TextView tvXiadanName;
    @BindView(R.id.tv_xiadan_sku)
    TextView tvXiadanSku;
    @BindView(R.id.tv_xiadan_num)
    TextView tvXiadanNum;
    @BindView(R.id.tv_xiadan_post)
    TextView tvXiadanPost;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.ll_address_out_range)
    LinearLayout llAddressOutRange;
    private boolean isOutRange;
    private CutOrderRequestBody requestBody;
    private String addressId;
    private int postage;//邮费
    @BindView(R.id.tv_cut_goods_xiadan_remarks)
    TextView tvRemarks;
    String remarks;//备注内容
    private boolean isCut;//是否从砍价而来

    @Override
    protected CutXiaDanPresenter createPresenter() {
        return new CutXiaDanPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_xiadan;
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setText("确认订单");
    }

    /**
     * eventBus接收回调
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAddress(AddressBean addressBean) {
        Logger.e("接收到地址"+addressBean.address);
        rlNoAdress.setVisibility(View.GONE);
        rlHaveAddress.setVisibility(View.VISIBLE);
        if(addressBean!=null){
            addressId = addressBean.id;
            tvReceiverAddress.setText(XiKouUtils.getAddress(mContext,addressBean));
            tvReceiverName.setText(addressBean.consigneeName);
            tvReceiverPhone.setText(addressBean.consigneeMobile);
            isOutRange = !addressBean.outRange;
            if(isOutRange){
                llAddressOutRange.setVisibility(View.VISIBLE);
            }else {
                llAddressOutRange.setVisibility(View.GONE);
            }
        }

    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(CutXiaDanActivity.this);
        requestBody = (CutOrderRequestBody) getIntent().getSerializableExtra("request_entity");
        int price = getIntent().getIntExtra("unit_price", 0);
        postage = getIntent().getIntExtra("postage", 0);
        isCut = getIntent().getBooleanExtra("is_cut", false);
        tvTotalMoney.setText(PriceUtil.dividePrice(requestBody.getOrderAmount()));
        GlideUtil.show(mContext, requestBody.getGoodsImageUrl(), ivXiadanLogo);
        tvXiadanName.setText(requestBody.getGoodsName());
        tvXiadanPrice.setText("¥" + PriceUtil.dividePrice(price));
        tvXiadanSku.setText(requestBody.getCommodityModel() + " " + requestBody.getCommoditySpec());
        tvXiadanNum.setText("X" + requestBody.getCommodityQuantity());
//        mPresenter.getAddressById(addressId);
        mPresenter.getAddressList(MyApplication.userId);

        if (postage != 0) {
            tvXiadanPost.setText("邮费：" + PriceUtil.dividePrice(postage) + "元");
        }else {
            tvXiadanPost.setText("全国包邮");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @OnClick({R.id.btn_confirm_order ,R.id.rl_no_adress,R.id.rl_have_address, R.id.rl_cut_xiadan_remarks, R.id.ll_address_out_range})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_order://确认订单
                if(TextUtils.isEmpty(addressId)){
                    ToastUtils.showShortToast(mContext, "请选择收货地址");
                    return;
                }
                if(isOutRange){
                    ToastUtils.showShortToast(mContext, getResources().getString(R.string.address_out_range));
                    return;
                }
                requestBody.setReceiptAddressRef(addressId);
                if(remarks != null){
                    requestBody.setRemarks(remarks);
                }
                mPresenter.onSubmitOrder(requestBody);
                break;
            case R.id.rl_no_adress://没地址
            case R.id.rl_have_address:
                Intent toAddress = new Intent(mContext, AddressActivity.class);
                toAddress.putExtra("is_xiadan",true);
                startActivity(toAddress);
                break;

            case R.id.rl_cut_xiadan_remarks:
                remarks = tvRemarks.getText().toString().trim();
                Intent intent = new Intent(mContext, RemarksActivity.class);
                intent.putExtra("remarks_content", remarks);
                startActivityForResult(intent, 101);
                break;

            case R.id.ll_address_out_range:
                //显示对话框
                new PosterTipDialog(mContext).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == 101 && data != null){
            remarks = data.getStringExtra("remarks_content");
            tvRemarks.setText(remarks);
        }
    }

    /**
     * 获取个人地址列表
     * @param addressBeanList
     */
    @Override
    public void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList) {
        List<AddressBean> list = addressBeanList.getData();
        if (list.size() == 0) {
            rlNoAdress.setVisibility(View.VISIBLE);
            rlHaveAddress.setVisibility(View.GONE);
        } else {
            rlNoAdress.setVisibility(View.GONE);
            rlHaveAddress.setVisibility(View.VISIBLE);

            for (AddressBean bean : list) {
                if (bean.defaultId == 1) {//默认地址
                    addressId = bean.id;
                    tvReceiverAddress.setText(XiKouUtils.getAddress(mContext,bean));
                    tvReceiverName.setText(bean.consigneeName);
                    tvReceiverPhone.setText(bean.consigneeMobile);
                    isOutRange = !bean.outRange;
                    if(isOutRange) {
                        llAddressOutRange.setVisibility(View.VISIBLE);
                    }
                    break;
                }
            }
            if(addressId == null || TextUtils.isEmpty(addressId)){
                AddressBean bean = list.get(0);
                addressId = bean.id;
                tvReceiverAddress.setText(XiKouUtils.getAddress(mContext,bean));
                tvReceiverName.setText(bean.consigneeName);
                tvReceiverPhone.setText(bean.consigneeMobile);
                if(addressId != null){
                    //显示超出配送范围
                    isOutRange = !bean.outRange;
                    if(isOutRange){
                        llAddressOutRange.setVisibility(View.VISIBLE);
                    }else {
                        llAddressOutRange.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    //提交订单成功
    @Override
    public void onSubmitOrderSuc(BaseModel<ConfirmCutOrderBean> baseModel) {
        if (baseModel != null) {
            ConfirmCutOrderBean data = baseModel.getData();
            Intent intent = new Intent(this, PayOrderActivity.class);
            intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
            intent.putExtra(PayOrderActivity.IS_CUT, isCut);
            intent.putExtra(PayOrderActivity.CUT_ID, requestBody.getId());
            intent.putExtra(PayOrderActivity.GOODS_NAME, data.getGoods().getGoodsName());
            intent.putExtra(PayOrderActivity.TOTAL_PRICE, data.getPayAmount() + data.getPostage());
            intent.putExtra(PayOrderActivity.ORDER_NUMBER, data.getOrderNo());
            intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.CUT_TYPE);
            startActivity(intent);
            MyApplication.getInstance().closeActivity();
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        Log.e(TAG, "onErrorCode: "+model.getMsg() );
        ToastUtils.showShortToast(mContext, model.getMsg());
    }

}
