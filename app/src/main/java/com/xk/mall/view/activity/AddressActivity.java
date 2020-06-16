package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.eventbean.RefreshAddressBean;
import com.xk.mall.model.impl.AddressViewImpl;
import com.xk.mall.presenter.AddressPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.adapter.AddressAdapter;
import com.xk.mall.view.widget.SwipeItemLayout;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName AddressActivity
 * Description 我的地址页面
 * Author 卿凯
 * Date 2019/6/10/010
 * Version V1.0
 */
public class AddressActivity extends BaseActivity<AddressPresenter> implements AddressViewImpl {
    @BindView(R.id.btn_add_address)
    Button btnAddAddress;//添加地址按钮
    @BindView(R.id.rv_address)
    RecyclerView rvAddress;//列表
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;//地址列表
    @BindView(R.id.rl_no_address)
    RelativeLayout rlNoAddress;//没有地址布局
    @BindView(R.id.tv_no_address_add)
    TextView tvNoAddressAdd;//没有地址的布局新增地址
    private boolean isXiaDanAddress;//是否下单选择地址
    private List<AddressBean> addressBeanList = new ArrayList<>();//数据列表
    private AddressAdapter addressAdapter;
    private int deletePos = 0;//点击删除的地址

    @Override
    protected AddressPresenter createPresenter() {
        return new AddressPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adress;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("我的地址");
    }

    @Override
    protected void initData() {
        if(!TextUtils.isEmpty(MyApplication.userId)){
            mPresenter.getAddress(MyApplication.userId);
        }
        isXiaDanAddress= getIntent().getBooleanExtra("is_xiadan",false);

        //获取本地或者服务器保存的地址图片
        rvAddress.setLayoutManager(new LinearLayoutManager(this));
        addressAdapter = new AddressAdapter(mContext, R.layout.address_item, addressBeanList);
        addressAdapter.setXiaDan(isXiaDanAddress);
        rvAddress.setAdapter(addressAdapter);

        rvAddress.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
//        addressAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                Log.e(TAG, "onItemClick: " );
//
//                if(isXiaDanAddress){//是否下单选择地址
//                    EventBus.getDefault().post(addressBeanList.get(position));
//                    finish();
//                }
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
//                return false;
//            }
//        });
        addressAdapter.setOnMainClickListener(position -> {
            if(isXiaDanAddress){//是否下单选择地址
                AddressBean addressBean = addressBeanList.get(position);
                if(addressBean.outRange){
                    EventBus.getDefault().post(addressBean);
                    finish();
                }else {
                    ToastUtils.showShortToast(mContext, "当前地址超出配送范围，请选择其他收货地址");
                }
            }else {
                AddressBean addressBean = addressBeanList.get(position);
                Intent intent = new Intent(mContext, EditorAddressActivity.class);
                intent.putExtra(Constant.INTENT_ADDRESS_BEAN, addressBean);
                intent.putExtra(EditorAddressActivity.ADDRESS_ID, addressBean.id);
                ActivityUtils.startActivity(intent);
            }
        });

        addressAdapter.setDeleteListener((addressBean, position) -> {
            deletePos = position;
            mPresenter.deleteAddress(addressBean.id);
        });
    }

    @OnClick({R.id.btn_add_address, R.id.tv_no_address_add})
    public void clickView(View view){
        switch (view.getId()){
            case R.id.btn_add_address:
            case R.id.tv_no_address_add:
                //添加地址
                Intent intent = new Intent(mContext, EditorAddressActivity.class);
                if(addressBeanList == null || addressBeanList.size() == 0){
                    intent.putExtra(EditorAddressActivity.IS_SET_DEFAULT, true);
                }
                ActivityUtils.startActivity(intent);
                break;
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onInsertOrUpdateAddressSuccess(RefreshAddressBean addressBean){
        if(addressBean.isReresh()){
            mPresenter.getAddress(MyApplication.userId);
        }
    }


    /**
     * 获取我的地址数据成功
     */
    @Override
    public void onGetAddressListSuc(BaseModel<List<AddressBean>> addressBeanList) {
        this.addressBeanList.clear();
        this.addressBeanList.addAll(addressBeanList.getData());
        if(this.addressBeanList == null || this.addressBeanList.size() == 0){
            rlAddress.setVisibility(View.GONE);
            rlNoAddress.setVisibility(View.VISIBLE);
        }else {
            rlNoAddress.setVisibility(View.GONE);
            rlAddress.setVisibility(View.VISIBLE);
            addressAdapter.notifyDataSetChanged();
        }

    }

    /**
     * 删除地址成功的回调
     */
    @Override
    public void onDeleteAddressSuccess(BaseModel model) {
        ToastUtils.showShortToast(mContext, model.getMsg());
            addressBeanList.remove(deletePos);
            addressAdapter.notifyItemRemoved(deletePos);
            mPresenter.getAddress(MyApplication.userId);
    }

}
