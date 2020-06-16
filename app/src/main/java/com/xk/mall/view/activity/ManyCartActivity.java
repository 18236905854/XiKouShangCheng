package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ManyBuyRateBean;
import com.xk.mall.model.entity.ManyCartsBean;
import com.xk.mall.model.eventbean.OrderSuccessBean;
import com.xk.mall.model.impl.ManyCartViewImpl;
import com.xk.mall.presenter.ManyCartPresenter;
import com.xk.mall.utils.AntiShakeUtils;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.widget.SlideRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.autosize.internal.CancelAdapt;

/**
 * ClassName ManyCartActivity
 * Description 多买多折购物车页面
 * Author Kay
 * Date 2019/6/26/026
 * Version V1.0
 */
public class ManyCartActivity extends BaseActivity<ManyCartPresenter> implements ManyCartViewImpl{

    @BindView(R.id.rv_many_cart)
    RecyclerView recyclerView;//列表
    @BindView(R.id.tv_many_cart_money)
    TextView tvMoney;//实际付款
    @BindView(R.id.tv_many_cart_discount_money)
    TextView tvDiscountMoney;//优惠的价钱
    @BindView(R.id.state_view_msg)
    MultiStateView stateViewMsg;
    private TextView tvEmpty;
    @BindView(R.id.tv_many_cart_pay)
    TextView tvGoPay;//确认下单按钮

    private List<ManyCartsBean> listData = new ArrayList<>();//所有的商品数据
    private List<ManyCartsBean.ListBean> selectedData = new ArrayList<>();//所有的商品数据

    private ManyCartAdapter manyCartAdapter;
    private String activityId = "";//活动ID
    private String commodityId = "";//商品sku ID

    private float rateOne = 5;
    private float rateTwo = 4;
    private float rateThree = 3;
    @Override
    protected ManyCartPresenter createPresenter() {
        return new ManyCartPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_many_cart;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("购物车");
    }

    @Override
    protected void initData() {
        View view = stateViewMsg.getView(MultiStateView.VIEW_STATE_EMPTY);
        tvEmpty=view.findViewById(R.id.tv_message);
        tvEmpty.setText("购物车空空如已~赶紧去挑挑吧");
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        mPresenter.getCartData(MyApplication.userId);
        manyCartAdapter = new ManyCartAdapter(mContext, R.layout.many_cart_big_item, listData);
        recyclerView.setAdapter(manyCartAdapter);
        recyclerView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                if (null != viewCache) {
                    viewCache.smoothClose();
                }
            }
            return false;
        });
//        initListData();
//        mPresenter.getCartData(MyApplication.userId);
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                //点击跳转商品详情
//                Intent intent = new Intent(mContext, ManyGoodsDetailActivity.class);
//                intent.putExtra(ManyGoodsDetailActivity.GOODS_NAME,manyCartBeans.get(position).name);
//                intent.putExtra(ManyGoodsDetailActivity.GOODS_PRICE,"" + manyCartBeans.get(position).nowPrice);
//                intent.putExtra(ManyGoodsDetailActivity.GOODS_LINE_PRICE,"" + manyCartBeans.get(position).realPrice);
//                ActivityUtils.startActivity(intent);
//            }
        if(MyApplication.switchBean != null && MyApplication.switchBean.getMoreDisCount() == 1){
            tvGoPay.setEnabled(true);
            tvGoPay.setBackgroundResource(R.drawable.bg_login_btn);
        }else {
            tvGoPay.setEnabled(false);
            tvGoPay.setBackgroundResource(R.drawable.bg_login_btn_disable);
        }
    }

    @OnClick({R.id.tv_many_cart_pay})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.tv_many_cart_pay://去结算
                goPay();
                break;
        }
    }

    /**
     * 去结算
     */
    private void goPay() {
        if(getSize() == 0){
            ToastUtils.showShortToast(mContext, "请选择商品");
        }else {
            String price = tvMoney.getText().toString();
            double totalPrice = Double.parseDouble(price.substring(1)) * 100;
            Intent intent = new Intent(mContext, ManyGoodsXiaDanActivity.class);
            intent.putExtra(ManyGoodsXiaDanActivity.TOTAL_PRICE, Double.valueOf(totalPrice).intValue());
            intent.putExtra("select_data", (Serializable) getSelectedList());
            ActivityUtils.startActivity(intent);
        }
    }

    //获取购物车列表数据成功
    @Override
    public void onGetCartDataSuc(BaseModel<List<ManyCartsBean>> baseModel) {
        Logger.e("获取购物车列表数据成功");
        List<ManyCartsBean> data = baseModel.getData();
        hideLoading();
        tvMoney.setText(getResources().getString(R.string.money) + "0");
        tvDiscountMoney.setText("优惠¥0" );
        if(data != null && data.size() > 0){
            listData.clear();
            listData.addAll(data);
            manyCartAdapter.notifyDataSetChanged();
            activityId = data.get(0).getList().get(0).getActivityId();
            commodityId = data.get(0).getList().get(0).getCommodityId();
            Log.e(TAG, "onGetCartDataSuc: " + data.size());
            if(!TextUtils.isEmpty(activityId) && !TextUtils.isEmpty(commodityId)){
                mPresenter.getManyBuyRate(activityId, commodityId);
            }
        }else{
            stateViewMsg.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    //删除成功
    @Override
    public void onDeleteCartSuc(BaseModel baseModel) {
        Logger.e("删除成功");
        mPresenter.getCartData(MyApplication.userId);
    }

    @Override
    public void onGetManyRateSuccess(BaseModel<ManyBuyRateBean> model) {
        if(model.getData() != null){
            rateOne = model.getData().getRateOne();
            rateTwo = model.getData().getRateTwo();
            rateThree = model.getData().getRateThree();
            SPUtils.getInstance().put(Constant.RATE_ONE, rateOne);
            SPUtils.getInstance().put(Constant.RATE_TWO, rateTwo);
            SPUtils.getInstance().put(Constant.RATE_THREE, rateThree);
        }
    }

    /**
     * 购物车adapter
     */
    class ManyCartAdapter extends CommonAdapter<ManyCartsBean> {

        public ManyCartAdapter(Context context, int layoutId, List<ManyCartsBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ManyCartsBean manyCartBean, int position) {
            holder.setText(R.id.tv_merchant_name, manyCartBean.getMerchantName());
            RecyclerView rvChild = holder.getView(R.id.recycleview);
            rvChild.setFocusable(false);
            rvChild.setLayoutManager(new LinearLayoutManager(mContext));
//            rvChild.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(mContext));
            ManyCartChildAdapter manyCartChildAdapter = new ManyCartChildAdapter(mContext, R.layout.many_cart_small_item, manyCartBean.getList());
            rvChild.setAdapter(manyCartChildAdapter);
        }
    }

    /**
     * 获取选中的size
     */
    private int getSize(){
        int size = 0;
        for (ManyCartsBean manyCartsBean : listData){
            for(ManyCartsBean.ListBean listBean : manyCartsBean.getList()){
                if(listBean.getPosition() != 0){
                    size += listBean.getBuyerNumber();
                }
            }
        }
        return size;
    }

    /**
     * 获取选中的数据
     */
    private List<ManyCartsBean> getSelectedList(){
        List<ManyCartsBean> selectedData = new ArrayList<>();
        for (ManyCartsBean manyCartsBean : listData){
            for(ManyCartsBean.ListBean listBean : manyCartsBean.getList()){
                if(listBean.getPosition() != 0){
                    selectedData.add(manyCartsBean);
                    break;
                }
            }
        }
        return selectedData;
    }

    //是否正在计算
    private boolean isCalculation = false;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private void calculationInThread(){
        if(!isCalculation){
            Observable.create((ObservableOnSubscribe<String>) emitter -> {
                calculationMoney();
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Logger.e("计算结束:" + System.currentTimeMillis());
                    });
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setSumMoney(SumMoneyBean money){
        isCalculation = false;
        Logger.e("计算结束:" + System.currentTimeMillis());
        manyCartAdapter.notifyDataSetChanged();
        tvMoney.setText(getResources().getString(R.string.money) + PriceUtil.divideDoublePrice(money.result));
        tvDiscountMoney.setText("优惠¥" + PriceUtil.divideDoublePrice(money.discountResult));
    }

    /**
     * 下单成功之后需要购物车重新获取数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void orderSuccess(OrderSuccessBean orderSuccessBean){
        Logger.e(TAG + "orderSuccess");

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(MyApplication.userId)){
            mPresenter.getCartData(MyApplication.userId);
        }
    }

    class SumMoneyBean {
        public double result = 0;
        public double discountResult = 0;
    }

    /**
     * 计算金额
     */
    private void calculationMoney(){
        Logger.e("开始计算:" + System.currentTimeMillis());
        isCalculation = true;
        double result = 0.0;
        double discountResult = 0.0;//优惠价格
        int maxPos = 0;
        List<ManyCartsBean.ListBean> selectedData = new ArrayList<>();
        for (ManyCartsBean manyCartsBean : listData){
            for(ManyCartsBean.ListBean listBean : manyCartsBean.getList()){
                if(listBean.getPosition() != 0){
                    if(listBean.getBuyerNumber() != 1){
                        for (int i = 0; i < listBean.getBuyerNumber(); i++){
                            selectedData.add(listBean);
                        }
                    }else {
                        selectedData.add(listBean);
                    }
                    if(listBean.getPosition() > maxPos){
                        maxPos = listBean.getPosition();
                    }
                }
            }
        }

        if(selectedData.size() == 0){
            SumMoneyBean moneyBean = new SumMoneyBean();
            moneyBean.result = result;
            moneyBean.discountResult = discountResult;
            EventBus.getDefault().post(moneyBean);
            return;
        }

        //按照position排序，小的在前
        Collections.sort(selectedData, (o1, o2) -> o1.getPosition() - o2.getPosition());
        if(maxPos == 1){
            int selectedSize = selectedData.size();
            if(selectedSize == 1){
                result += selectedData.get(0).getActivityPrice() * rateOne / 10;
                discountResult += selectedData.get(0).getSalePrice();
            }else if(selectedSize == 2){
                result += selectedData.get(0).getActivityPrice() * rateOne / 10;
                result += selectedData.get(1).getActivityPrice() * rateTwo / 10;
                discountResult += selectedData.get(0).getSalePrice();
                discountResult += selectedData.get(1).getSalePrice();
            }else if(selectedSize == 3){
                result += selectedData.get(0).getActivityPrice() * rateOne / 10;
                result += selectedData.get(1).getActivityPrice() * rateTwo / 10;
                result += selectedData.get(2).getActivityPrice() * rateThree / 10;
                discountResult += selectedData.get(0).getSalePrice();
                discountResult += selectedData.get(1).getSalePrice();
                discountResult += selectedData.get(2).getSalePrice();
            }
        }else if(maxPos == 2){
            int selectedSize = selectedData.size();
            if(selectedSize == 2){
                result += selectedData.get(0).getActivityPrice() * rateOne / 10;
                result += selectedData.get(1).getActivityPrice() * rateTwo / 10;
                discountResult += selectedData.get(0).getSalePrice();
                discountResult += selectedData.get(1).getSalePrice();
            }else if(selectedSize == 3){
                result += selectedData.get(0).getActivityPrice() * rateOne / 10;
                discountResult += selectedData.get(0).getSalePrice();
                if(selectedData.get(1).getDiscountMin() > selectedData.get(2).getDiscountMin()){
                    result += selectedData.get(1).getActivityPrice() * rateTwo / 10;
                    result += selectedData.get(2).getActivityPrice() * rateThree / 10;
                    discountResult += selectedData.get(1).getSalePrice();
                    discountResult += selectedData.get(2).getSalePrice();
                }else {
                    result += selectedData.get(1).getActivityPrice() * rateThree / 10;
                    result += selectedData.get(2).getActivityPrice() * rateTwo / 10;
                    discountResult += selectedData.get(1).getSalePrice();
                    discountResult += selectedData.get(2).getSalePrice();
                }
            }
        }else if(maxPos == 3){
            int selectedSize = selectedData.size();
            if(selectedSize == 1){
                result += selectedData.get(0).getActivityPrice() * rateOne / 10;
                discountResult += selectedData.get(0).getSalePrice();
            }else if(selectedSize == 2){
                result += selectedData.get(0).getActivityPrice() * rateOne / 10;
                result += selectedData.get(1).getActivityPrice() * rateTwo / 10;
                discountResult += selectedData.get(0).getSalePrice();
                discountResult += selectedData.get(1).getSalePrice();
            }else {
                result += selectedData.get(0).getActivityPrice() * rateOne / 10;
                discountResult += selectedData.get(0).getSalePrice();
                if(selectedData.get(1).getDiscountMin() > selectedData.get(2).getDiscountMin()){
                    result += selectedData.get(1).getActivityPrice() * rateTwo / 10;
                    result += selectedData.get(2).getActivityPrice() * rateThree / 10;
                    discountResult += selectedData.get(1).getSalePrice();
                    discountResult += selectedData.get(2).getSalePrice();
                }else {
                    result += selectedData.get(1).getActivityPrice() * rateThree / 10;
                    result += selectedData.get(2).getActivityPrice() * rateTwo / 10;
                    discountResult += selectedData.get(1).getSalePrice();
                    discountResult += selectedData.get(2).getSalePrice();
                }
            }
//            result += selectedData.get(0).getActivityPrice() * rateOne / 10;
//            result += selectedData.get(1).getActivityPrice() * rateTwo / 10;
//            result += selectedData.get(2).getActivityPrice() * rateOne / 10;
//            discountResult += selectedData.get(0).getSalePrice() * rateOne / 10;
//            discountResult += selectedData.get(1).getSalePrice() * rateTwo / 10;
//            discountResult += selectedData.get(2).getSalePrice() * rateOne / 10;
        }
        SumMoneyBean moneyBean = new SumMoneyBean();
        moneyBean.result = result;
        moneyBean.discountResult = discountResult - result;
        EventBus.getDefault().post(moneyBean);
    }

    /**
     * 购物车中子rv的adapter
     */
    class ManyCartChildAdapter extends CommonAdapter<ManyCartsBean.ListBean>{

        public ManyCartChildAdapter(Context context, int layoutId, List<ManyCartsBean.ListBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ManyCartsBean.ListBean listBean, int position) {
            ImageView ivLogo = holder.getView(R.id.iv_many_cart_logo);
            GlideUtil.show(mContext, listBean.getGoodsImageUrl(), ivLogo);
            activityId = listBean.getActivityId();
            commodityId = listBean.getCommodityId();
            holder.setText(R.id.tv_many_cart_name, listBean.getCommodityName());
            holder.setText(R.id.tv_many_cart_sku, listBean.getCommodityModel() + "  " + listBean.getCommoditySpec());
//            if(listBean.getMarketPrice() == 0){
//                holder.setText(R.id.tv_many_cart_real_price, "原价:¥" + PriceUtil.dividePrice(listBean.getActivityPrice()));
//            }else {
                holder.setText(R.id.tv_many_cart_real_price, "原价:¥" + PriceUtil.dividePrice(listBean.getSalePrice()));
//            }
            holder.setText(R.id.et_many_cart_num, "" + listBean.getBuyerNumber());
            holder.setText(R.id.tv_many_cart_price,getResources().getString(R.string.money) + PriceUtil.dividePrice(listBean.getActivityPrice()));
            Button btnDelete = holder.getView(R.id.btn_many_cart_delete);
            btnDelete.setOnClickListener(v -> mPresenter.deleteCartData(listBean.getId()));
            TextView etNum = holder.getView(R.id.et_many_cart_num);
            TextView tvDiscount = holder.getView(R.id.tv_discount);
            ImageView cbPlus = holder.getView(R.id.btn_many_cart_plus);
            ImageView cbMin = holder.getView(R.id.btn_many_cart_minus);
            CheckBox cbChoose = holder.getView(R.id.cb_many_cart);
            cbPlus.setOnClickListener(view -> {
                int number = Integer.parseInt(etNum.getText().toString());
                if(number < 3){
                    if(getSize() < 3){//表示选中的
                        number += 1;
                        listBean.setBuyerNumber(number);
                        etNum.setText(String.valueOf(number));
                        mPresenter.updataNum(listBean.getId(), number);
//                        notifyDataSetChanged();
//                        cbPlus.postDelayed(() -> calculationInThread(),200);
                        if(listBean.getPosition() != 0){
                            changeDiscountByPosition(listBean, true, false);
                        }
                    }else {
                        ToastUtils.showShortToast(mContext, "选择商品不能超过三件");
                    }
                }else {
                    ToastUtils.showShortToast(mContext, "最多选择三件");
                }
            });

            cbMin.setOnClickListener(view -> {
                int number = Integer.parseInt(etNum.getText().toString());
                if(number > 1){
                    number -= 1;
                    etNum.setText(String.valueOf(number));
                    listBean.setBuyerNumber(number);
                    mPresenter.updataNum(listBean.getId(), number);
                    if(listBean.getPosition() != 0){
                        changeDiscountByPosition(listBean, false, false);
                    }
                }else {
                    ToastUtils.showShortToast(mContext, "最少选择一件");
                }
            });

            if(listBean.getPosition() == 1){
                tvDiscount.setVisibility(View.VISIBLE);
                if(listBean.getBuyerNumber() >= 3){
                    tvDiscount.setText(changeFloatToString("最低", rateThree));
                    listBean.setDiscountMin(rateThree);
                }else if(listBean.getBuyerNumber() >= 2){
//                    if(getSize() == 2){
                        tvDiscount.setText(changeFloatToString("最低", rateTwo));
                        listBean.setDiscountMin(rateTwo);
//                    }else {
//                        tvDiscount.setText(changeFloatToString("最低", rateOne));
//                        listBean.setDiscountMin(rateOne);
//                    }
                }else if(listBean.getBuyerNumber() >= 1){
                    tvDiscount.setText(changeFloatToString("折扣:", rateOne));
                    listBean.setDiscountMin(rateOne);
                }
                cbChoose.setButtonDrawable(R.drawable.ic_many_cart_one);
            }else if(listBean.getPosition() == 2){
                tvDiscount.setVisibility(View.VISIBLE);
                if(listBean.getBuyerNumber() >= 2){
                    tvDiscount.setText(changeFloatToString("最低", rateThree));
                    listBean.setDiscountMin(rateThree);
                }else if(listBean.getBuyerNumber() >= 1){
                    tvDiscount.setText(changeFloatToString("折扣:",rateTwo));
                    listBean.setDiscountMin(rateTwo);
                }
                cbChoose.setButtonDrawable(R.drawable.ic_many_cart_two);
            }else if(listBean.getPosition() == 3){
                tvDiscount.setVisibility(View.VISIBLE);
                tvDiscount.setText( changeFloatToString("折扣:",rateThree));
                listBean.setDiscountMin(rateThree);
                cbChoose.setButtonDrawable(R.drawable.ic_many_cart_three);
            }else {
                tvDiscount.setVisibility(View.GONE);
            }
            cbChoose.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if(AntiShakeUtils.isInvalidClick(buttonView) || isCalculation){
                    Logger.e("有反应" + isCalculation);
                    return;
                }
                Logger.e("没有反应");
                if(!listBean.isCheck()){
                    //当是选中时，需要计算当前已经选择了几个，计算出当前商品的折扣
                    if(getSize() == 0 && listBean.getBuyerNumber() <= 3){
                        listBean.setCheck(true);
                        listBean.setPosition(1);
                        tvDiscount.setVisibility(View.VISIBLE);
                        if(listBean.getBuyerNumber() == 3){
                            tvDiscount.setText(changeFloatToString("最低",rateThree));
                            listBean.setDiscountMin(rateThree);
                        }else if(listBean.getBuyerNumber() == 2){
                            tvDiscount.setText(changeFloatToString("最低", rateTwo));
                            listBean.setDiscountMin(rateTwo);
                        }else {
                            tvDiscount.setText(changeFloatToString("折扣:", rateOne));
                            listBean.setDiscountMin(rateOne);
                        }
                        cbChoose.setButtonDrawable(R.drawable.ic_many_cart_one);
                        calculationInThread();
                    }else if(getSize() == 1 && listBean.getBuyerNumber() <= 2){
                        listBean.setCheck(true);
                        listBean.setPosition(2);
                        tvDiscount.setVisibility(View.VISIBLE);
                        if(listBean.getBuyerNumber() == 2){
                            tvDiscount.setText(changeFloatToString("最低", rateThree));
                            listBean.setDiscountMin(rateThree);
                        }else {
                            tvDiscount.setText(changeFloatToString("折扣:", rateTwo));
                            listBean.setDiscountMin(rateTwo);
                        }
                        cbChoose.setButtonDrawable(R.drawable.ic_many_cart_two);
                        calculationInThread();
                    }else if(getSize() == 2 && listBean.getBuyerNumber() <= 1){
                        listBean.setCheck(true);
                        listBean.setPosition(3);
                        tvDiscount.setVisibility(View.VISIBLE);
                        tvDiscount.setText(changeFloatToString("折扣:", rateThree));
                        listBean.setDiscountMin(rateThree);
                        cbChoose.setButtonDrawable(R.drawable.ic_many_cart_three);
                        calculationInThread();
                    }else {
                        ToastUtils.showShortToast(mContext, "已经选择三件商品");
                    }
                }else {
                    //之前是选中的，现在取消选中，需要把小于当前数据的折扣的商品变成该商品的折扣
                    listBean.setCheck(false);
                    cbChoose.setButtonDrawable(R.drawable.ic_cb_default);
                    changeDiscountByPosition(listBean, false, true);
                }
            });
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
        return text + discountStr + "折";
    }

    /**
     * 根据位置来改变折扣
     */
    private void changeDiscountByPosition(ManyCartsBean.ListBean clickBean, boolean isPlus, boolean isClear) {
        showLoading();
        for (ManyCartsBean manyCartsBean : listData){
            for(ManyCartsBean.ListBean listBean : manyCartsBean.getList()){
                if(!isPlus){
                    if(listBean.getPosition() - clickBean.getPosition() > 0){
                        Logger.e(listBean.getPosition() + ":需要修改的数据:" + listBean.getCommodityName());
                        Logger.e(clickBean.getPosition() + ":点击的数据:" + clickBean.getCommodityName());
                        //后面的数据pos减去点击数据的pos和点击数据的数量，并且加1(当是一件时数量保持为0)
                        if(clickBean.getPosition() == 2){
                            int pos = listBean.getPosition() - clickBean.getPosition() - clickBean.getBuyerNumber() + 2;
                            pos = pos > 0 ? pos : 0;
                            listBean.setPosition(pos);
                        }else {
                            int pos = listBean.getPosition() - clickBean.getPosition() - clickBean.getBuyerNumber() + 1;
                            pos = pos > 0 ? pos : 0;
                            listBean.setPosition(pos);
                        }
                        Logger.e(listBean.getPosition() + ":需要修改的数据:" + listBean.getCommodityName());
                    }
                }else {
                    Logger.e("kkkkkkkk");
                    if(listBean.getPosition() - clickBean.getPosition() > 0){
                        Logger.e(listBean.getPosition() + ":需要修改的数据:" + listBean.getCommodityName());
                        Logger.e(clickBean.getPosition() + ":点击的数据:" + clickBean.getCommodityName());
                        int pos = listBean.getPosition() + clickBean.getBuyerNumber();
                        pos = pos >= 3 ? 3 : pos;
                        listBean.setPosition(pos);
                        Logger.e(listBean.getPosition() + ":需要修改的数据:" + listBean.getCommodityName());
                    }
                }
            }
        }
        if(isClear){
            clickBean.setPosition(0);
        }
        calculationInThread();
        hideLoading();
    }
}
