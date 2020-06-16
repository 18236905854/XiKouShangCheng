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

import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.MonthAccountBean;
import com.xk.mall.model.entity.RedBagMxBean;
import com.xk.mall.model.entity.SettlementMxChildBean;
import com.xk.mall.model.entity.WithDrawCountBean;
import com.xk.mall.model.entity.WithDrawDetailBean;
import com.xk.mall.model.entity.WithDrawMxBean;
import com.xk.mall.model.entity.WithDrawMxChildBean;
import com.xk.mall.model.impl.RedBagMXViewImpl;
import com.xk.mall.model.impl.WithDrawMXViewImpl;
import com.xk.mall.presenter.RedBagMXPresenter;
import com.xk.mall.presenter.WithDrawMXPresenter;
import com.xk.mall.utils.BigDecimalUtil;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.StringUtils;
import com.xk.mall.view.adapter.WithDrawDetailAdapter;
import com.xk.mall.view.widget.CommonPopupWindow;
import com.xk.mall.view.widget.XkPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.listeners.OnMoreItemPickListener;
import cn.addapp.pickers.listeners.OnMoreWheelListener;

import static com.xk.mall.view.activity.WithDrawDetailingActivity.WITHDRAW_DETAIL;

/**
 * 提现明细
 */
public class WithDrawDetailActivity extends BaseActivity<WithDrawMXPresenter> implements CommonPopupWindow.ViewInterface, WithDrawMXViewImpl {
    private static final String TAG = "WithDrawDetailActivity";
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_withdrawed)
    TextView tvWithdrawed;
    @BindView(R.id.tv_withdrawing)
    TextView tvWithdrawing;
    @BindView(R.id.stateView)
    MultiStateView stateView;

    private CommonPopupWindow popupWindow;
    private XkPicker picker;

    private int page=1;
    private int limit=10;
    private String searchTime;//搜索时间
    List<WithDrawMxChildBean> listData;
    private WithDrawDetailAdapter withDrawDetailAdapter;

    @Override
    protected WithDrawMXPresenter createPresenter() {
        return new WithDrawMXPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("提现明细");
        showRight(true);
        setRightText("筛选");
        setOnRightClickListener(v -> {
            showMonthPopup();
        });
    }

    @Override
    protected void initData() {
        searchTime= DateToolUtils.dateToYmString(new Date());
        mPresenter.getWithDrawMXList(MyApplication.userId,searchTime,page,limit);
        mPresenter.getMonthCount(MyApplication.userId, searchTime);
        listData = new ArrayList<>();

        withDrawDetailAdapter = new WithDrawDetailAdapter(mContext, listData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                Intent todetail = new Intent(WithDrawDetailActivity.this, WithDrawDetailingActivity.class);
                todetail.putExtra(WITHDRAW_DETAIL, listData.get(position).getCashId());
                startActivity(todetail);
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(withDrawDetailAdapter);
        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getWithDrawMXList(MyApplication.userId,searchTime,page,limit);
        });
    }

    private void showMonthPopup() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(WithDrawDetailActivity.this).inflate(R.layout.popup_transaction_month, null);
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


    /**
     * 获取列表成功
     */
    @Override
    public void onGetListDataSuc(BaseModel<WithDrawMxBean> model) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if(model != null && model.getData() != null && model.getData().getResult() != null){
            if(page == 1){
                listData.clear();
            }
            listData.addAll(model.getData().getResult());
            withDrawDetailAdapter.notifyDataSetChanged();
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
    public void onGetMonthCountSuccess(BaseModel<WithDrawCountBean> model) {
        if(model != null && model.getData() != null){
            tvWithdrawed.setText("已提现 ¥" + PriceUtil.dividePrice(model.getData().getHaveCashAmount()));
            tvWithdrawing.setText("提现中 ¥" + PriceUtil.dividePrice(model.getData().getOnWayAmount()));
        }else {
            tvWithdrawed.setText("已提现 ¥0.00");
            tvWithdrawing.setText("提现中 ¥0.00");
        }
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
                    page = 1;
                    searchTime= StringUtils.timeConvert(tvResult.getText().toString());
                    mPresenter.getWithDrawMXList(MyApplication.userId,searchTime,page, limit);
                    mPresenter.getMonthCount(MyApplication.userId, searchTime);
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
        }
    }
}
