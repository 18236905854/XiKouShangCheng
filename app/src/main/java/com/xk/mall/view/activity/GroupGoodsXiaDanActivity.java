package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.ConfirmGroupOrderBean;
import com.xk.mall.model.impl.GroupGoodsViewImpl;
import com.xk.mall.model.request.GroupOrderRequestBody;
import com.xk.mall.presenter.GroupGoodsPresenter;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 定制拼团下单
 */
public class GroupGoodsXiaDanActivity extends BaseActivity<GroupGoodsPresenter> implements GroupGoodsViewImpl {

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
    private GroupOrderRequestBody requestBody;
    private String addressId;
    private int postage;//邮费

    @Override
    protected GroupGoodsPresenter createPresenter() {
        return new GroupGoodsPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_xiadan;
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
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(GroupGoodsXiaDanActivity.this);
        requestBody = (GroupOrderRequestBody) getIntent().getSerializableExtra("request_entity");
        int price = getIntent().getIntExtra("unit_price", 0);
        postage=getIntent().getIntExtra("postage",0);
        tvTotalMoney.setText(PriceUtil.dividePrice(requestBody.getOrderAmount()));
        GlideUtil.show(mContext, requestBody.getGoodsImageUrl(), ivXiadanLogo);
        tvXiadanName.setText(requestBody.getGoodsName());
        tvXiadanPrice.setText("¥"+PriceUtil.dividePrice(price));
        tvXiadanSku.setText(requestBody.getCommodityModel()+" "+requestBody.getCommoditySpec());
        tvXiadanNum.setText("X"+requestBody.getCommodityQuantity());
        mPresenter.getAddressList(MyApplication.userId);
        if(postage!=0){
            tvXiadanPost.setText("邮费："+ PriceUtil.dividePrice(postage) +"元");
        }
    }

    @OnClick({R.id.btn_confirm_order, R.id.rl_no_adress,R.id.rl_have_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm_order://确认订单
                if(TextUtils.isEmpty(addressId)){
                    ToastUtils.showShortToast(mContext, "请选择收货地址");
                    return;
                }
                requestBody.setReceiptAddressRef(addressId);
                mPresenter.onSubmitOrder(requestBody);
                break;
            case R.id.rl_no_adress://没地址
            case R.id.rl_have_address:
                Intent toAddress = new Intent(mContext, AddressActivity.class);
                toAddress.putExtra("is_xiadan",true);
                startActivity(toAddress);
                break;
        }
    }

    //获取地址成功
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
                    break;
                }
            }
            if(addressId == null || TextUtils.isEmpty(addressId)){
                AddressBean bean = list.get(0);
                addressId = bean.id;
                tvReceiverAddress.setText(XiKouUtils.getAddress(mContext,bean));
                tvReceiverName.setText(bean.consigneeName);
                tvReceiverPhone.setText(bean.consigneeMobile);
            }
        }
    }

    //提交订单成功
    @Override
    public void onSubmitOrderSuc(BaseModel<ConfirmGroupOrderBean> baseModel) {
        if(baseModel!=null){
            ConfirmGroupOrderBean data = baseModel.getData();
            Intent intent=new Intent(this,PayOrderActivity.class);
            intent.putExtra(PayOrderActivity.IS_SHOW_COUPON, false);
            intent.putExtra(PayOrderActivity.GOODS_NAME,data.getGoods().getGoodsName());
            intent.putExtra(PayOrderActivity.TOTAL_PRICE,data.getPayAmount() + data.getPostage());
            intent.putExtra(PayOrderActivity.ORDER_NUMBER,data.getOrderNo());
            intent.putExtra(PayOrderActivity.ORDER_TYPE, OrderType.GROUP_TYPE);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext,model.getMsg());
    }
}
