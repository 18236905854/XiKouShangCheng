package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.kennyc.view.MultiStateView;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.MonthAccountBean;
import com.xk.mall.model.entity.RedBagMxBean;
import com.xk.mall.model.entity.RedChooseBean;
import com.xk.mall.model.entity.SettlementMxChildBean;
import com.xk.mall.model.impl.RedBagMXViewImpl;
import com.xk.mall.presenter.RedBagMXPresenter;
import com.xk.mall.utils.BigDecimalUtil;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.StringUtils;
import com.xk.mall.view.adapter.RedBagDetailAdapter;
import com.xk.mall.view.widget.CommonPopupWindow;
import com.xk.mall.view.widget.XkPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.listeners.OnMoreItemPickListener;
import cn.addapp.pickers.listeners.OnMoreWheelListener;

import static com.xk.mall.view.activity.RedBagDetailingActivity.RED_BAG_ID;
import static com.xk.mall.view.activity.RedBagDetailingActivity.RED_BAG_KEY;

/**
 * 红包明细
 */
public class RedBagDetailActivity extends BaseActivity<RedBagMXPresenter> implements CommonPopupWindow.ViewInterface, RedBagMXViewImpl {
    private static final String TAG = "RedBagDetailActivity";
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_shou)
    TextView tvShou;
    @BindView(R.id.tv_zhi)
    TextView tvZhi;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.stateView)
    MultiStateView stateView;
    private CommonPopupWindow popupWindow;
    private CommonPopupWindow topWindow;
    private XkPicker picker;

    private int page=1;
    private int limit=10;
    private String searchTime;//搜索时间
    List<SettlementMxChildBean> listData;
    private RedBagDetailAdapter redBagDetailAdapter;
    private int businessType = -1;
    private RedChooseBean redChooseBean;

    @Override
    protected RedBagMXPresenter createPresenter() {
        return new RedBagMXPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_bag_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("钱包明细");
        showRight(true);
        setRightText("筛选");
        setOnRightClickListener(v -> {
            showMonthPopup();
//            ActivityUtils.startActivity(SuCaiTextActivity.class);
            Log.e(TAG, "initToolbar: 筛选");
        });
    }

    @Override
    protected void initData() {
        searchTime= DateToolUtils.dateToYmString(new Date());
        getServerData();
        mPresenter.getAccountType();
        listData = new ArrayList<>();

        redBagDetailAdapter = new RedBagDetailAdapter(mContext, listData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                SettlementMxChildBean bean = listData.get(position);
                if(bean.getBusinessType() == 7 || bean.getBusinessName().contains("转账")){
                    Intent todetail = new Intent(RedBagDetailActivity.this, TransferDetailingActivity.class);
                    todetail.putExtra(TransferDetailingActivity.RED_BAG_ID, listData.get(position).getId());
                    todetail.putExtra(TransferDetailingActivity.RED_BAG_KEY, listData.get(position).getRefKey());
                    startActivity(todetail);
                }else {
                    Intent todetail = new Intent(RedBagDetailActivity.this, RedBagDetailingActivity.class);
                    todetail.putExtra(RED_BAG_ID, listData.get(position).getId());
                    todetail.putExtra(RED_BAG_KEY, listData.get(position).getRefKey());
                    startActivity(todetail);
                }
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(redBagDetailAdapter);

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getRedBagMXList(MyApplication.userId,searchTime,page,limit, businessType);
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                businessType = -1;
                getServerData();
            }
        });

        //点击头部文字筛选
        findViewById(R.id.toolbar_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(redChooseBean != null){
                    showTopPopup();
                }
            }
        });
    }

    private void getServerData(){
        Logger.e("查询字符:" + businessType);
        mPresenter.getMonthAccount(MyApplication.userId, searchTime, businessType);
        mPresenter.getRedBagMXList(MyApplication.userId,searchTime,page,limit, businessType);
    }

    /**
     * 显示头部过滤数据
     */
    private void showTopPopup() {
        if (topWindow == null){
//            View upView = LayoutInflater.from(RedBagDetailActivity.this).inflate(R.layout.popup_red_title_top, null);
            //测量View的宽高
//            DensityUtils.measureWidthAndHeight(upView);
            topWindow = new CommonPopupWindow.Builder(this)
                    .setView(R.layout.popup_red_title_top)
                    .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                    .setAnimationStyle(R.style.AnimDown)
                    .setViewOnclickListener(this)
                    .create();
            topWindow.showAsDropDown(toolbar);
        }else if(topWindow.isShowing()){
            topWindow.dismiss();
        }else {
            topWindow.showAsDropDown(toolbar);
        }
    }

    private void showMonthPopup() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(RedBagDetailActivity.this).inflate(R.layout.popup_transaction_month, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_transaction_month)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
//                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId) {
            case R.layout.popup_transaction_month:
                TextView tvResult = view.findViewById(R.id.tv_result);
                ImageView imgClose = view.findViewById(R.id.img_close);
                ViewGroup linearLayout = view.findViewById(R.id.wheelview_container);
                Button btnConfirm = view.findViewById(R.id.btn_confirm);
                imgClose.setOnClickListener(v -> {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                });

                //筛选确认
                btnConfirm.setOnClickListener(v -> {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                    tvMonth.setText(tvResult.getText().toString());
                    searchTime=StringUtils.timeConvert(tvResult.getText().toString());
                    page = 1;
                    getServerData();
//                    ToastUtils.showShortToast(RedBagDetailActivity.this, time);
                });
                //获取当前年月
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                if(month < 10){
                   tvResult.setText(year + "年0" + month + "月");
                }else{
                    tvResult.setText(year + "年" + month + "月");
                }


                picker = new XkPicker(this);
                picker.setWeightEnable(true);
                picker.setColumnWeight(1.0f, 1.0f, 0.2f);
                picker.setWheelModeEnable(true);
                picker.setTextSize(16);
                picker.setBackgroundColor(0xFFF4F4F4);
                picker.setSelectedTextColor(0xFF444444);//前四位值是透明度
                picker.setUnSelectedTextColor(0xFF999999);
                picker.setCanLoop(true);
                picker.setOffset(3);
                List<String> listDataYear = picker.getListDataYear();
                List<String> listMonth = BigDecimalUtil.parseData();
                int yearIndex = 0;
                int monthIndex = 0;
                for (int i = 0; i < listDataYear.size(); i++) {
                    if (listDataYear.get(i).contains(String.valueOf(year))) {
                        yearIndex = i;
                        break;
                    }
                }
                for (int j = 0; j < listMonth.size(); j++) {
                    if (listMonth.get(j).contains(String.valueOf(month))) {
                        monthIndex = j;
                        break;
                    }
                }
                Log.e(TAG, "getChildView:yearIndex== " + yearIndex);
                Log.e(TAG, "getChildView: monthIndex==" + monthIndex);
                picker.setSelectedIndex(yearIndex, monthIndex);

                picker.setOnMoreItemPickListener(new OnMoreItemPickListener<String>() {
                    @Override
                    public void onItemPicked(String s1, String s2, String s3) {
                        s3 = !TextUtils.isEmpty(s3) ? ",item3: " + s3 : "";
                    }
                });

                picker.setOnMoreWheelListener(new OnMoreWheelListener() {
                    @Override
                    public void onFirstWheeled(int index, String item) {
                        tvResult.setText(item + picker.getSelectedSecondItem());

                    }

                    @Override
                    public void onSecondWheeled(int index, String item) {
                        tvResult.setText(picker.getSelectedFirstItem() + item);
                    }

                    @Override
                    public void onThirdWheeled(int index, String item) {

                    }
                });

                Log.e(TAG, "initData: " + listDataYear.get(1));

                linearLayout.addView(picker.getContentView());
                break;

            case R.layout.popup_red_title_top:
                LabelsView labelsViewIn = view.findViewById(R.id.labels_in);
                LabelsView labelsViewOut = view.findViewById(R.id.labels_out);
                Button btnSureRed = view.findViewById(R.id.btn_confirm_red);
                labelsViewIn.setOnLabelClickListener((label, data, position) -> {
                    labelsViewOut.clearAllSelect();
                });
                labelsViewOut.setOnLabelClickListener((label, data, position) -> {
                    labelsViewIn.clearAllSelect();
                });

                labelsViewIn.setLabels(redChooseBean.getIn(), (label, position, data) -> data.getName());
                labelsViewOut.setLabels(redChooseBean.getOutcome(), (label, position, data) -> data.getName());
                btnSureRed.setOnClickListener(view1 -> {
                    List<RedChooseBean.InBean> dataIn = labelsViewIn.getSelectLabelDatas();
                    List<RedChooseBean.InBean> dataOut = labelsViewOut.getSelectLabelDatas();
                    if(dataIn != null && dataIn.size() != 0){
                        businessType = dataIn.get(0).getId();
                    }else {
                        businessType = -1;
                    }
                    if(dataOut != null && dataOut.size() != 0){
                        businessType = dataOut.get(0).getId();
                    }else {
                        businessType = -1;
                    }
                    //确定按钮筛选
                    topWindow.dismiss();
                    page = 1;
                    getServerData();
                });
                break;
        }
    }

    //获取数据成功
    @Override
    public void onGetListDataSuc(BaseModel<RedBagMxBean> model) {
        if(model != null && model.getData() != null && model.getData().getResult() != null){
            if(page == 1){
                listData.clear();
            }
            listData.addAll(model.getData().getResult());
            redBagDetailAdapter.notifyDataSetChanged();
            refreshLayout.finishRefresh();
            refreshLayout.finishLoadMore();
            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
            if(page == 1 && model.getData().getResult().size() == 0){
                stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
            if(model.getData().getResult().size() < limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }

        }else {
            stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    @Override
    public void onGetMonthAccountSuccess(BaseModel<MonthAccountBean> model) {
        tvShou.setText("收¥" + PriceUtil.dividePrice(model.getData().getIncomeAmount()));
        tvZhi.setText("支¥" + PriceUtil.dividePrice(model.getData().getExpenditureAmount()));
    }

    @Override
    public void onGetAccountTypeSuccess(BaseModel<RedChooseBean> model) {
        if(model.getData() != null){
            redChooseBean = model.getData();
        }
    }


}
