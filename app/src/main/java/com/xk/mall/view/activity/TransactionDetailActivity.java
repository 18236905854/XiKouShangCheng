package com.xk.mall.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.labels.LabelsView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.TransactionDetailBean;
import com.xk.mall.utils.BigDecimalUtil;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.StringUtils;
import com.xk.mall.view.adapter.TransactionDetailAdapter;
import com.xk.mall.view.widget.CommonPopupWindow;
import com.xk.mall.view.widget.XkPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.listeners.OnMoreItemPickListener;
import cn.addapp.pickers.listeners.OnMoreWheelListener;

/**
 * 交易明细
 */
public class TransactionDetailActivity extends BaseActivity implements CommonPopupWindow.ViewInterface {
    private static final String TAG = "TransDetailActivity";

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.iv_month)
    ImageView ivMonth;
    @BindView(R.id.ll_select_month)
    LinearLayout llSelectMonth;
    @BindView(R.id.iv_shou_zhi)
    ImageView ivShouZhi;
    @BindView(R.id.ll_select_shouzhi)
    LinearLayout llSelectShouzhi;
    @BindView(R.id.iv_yewu)
    ImageView ivYewu;
    @BindView(R.id.ll_select_yewu)
    LinearLayout llSelectYewu;
    @BindView(R.id.tv_month)
    TextView tvMonth;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    private CommonPopupWindow popupWindow;
    private XkPicker picker;

    private List<String> listName = new ArrayList<>();
    private String searchTime;//搜索时间
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transaction_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("交易明细");
    }

    @Override
    protected void initData() {
        listName.add("吾G限时购");
        listName.add("全球买手");
        listName.add("多买多折");
        listName.add("0元抢");
        listName.add("喜立得");
        listName.add("定制拼团");
        listName.add("O2O线下");

        List<TransactionDetailBean> listData = new ArrayList<>();
        listData.add(new TransactionDetailBean(1, "商城订单 ", "多买多折", "06-18 15:30", "-85.00",0));
        listData.add(new TransactionDetailBean(2, "商城订单 ", "全球买手 ", "06-18 15:30", "-8",0));
        listData.add(new TransactionDetailBean(3, "线下订单  ", "O2O买单 ", "06-18 15:30", "-17.00",1));
        listData.add(new TransactionDetailBean(4, "商城订单 ", "吾G购 ", "06-18 15:30", "-12.00",0));


        TransactionDetailAdapter redBagDetailAdapter = new TransactionDetailAdapter(mContext, listData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
//                Intent todetail = new Intent(TransactionDetailActivity.this, RedBagDetailingActivity.class);
//                todetail.putExtra(RED_BAG_DETAIL, listData.get(position));
//                startActivity(todetail);
            }
        });
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.setAdapter(redBagDetailAdapter);

    }


    @OnClick({R.id.ll_select_month, R.id.ll_select_shouzhi, R.id.ll_select_yewu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_select_month:
                showMonthPopup();
                break;
            case R.id.ll_select_shouzhi:
                showShouZhi();
                break;
            case R.id.ll_select_yewu:
                showYeWu();
                break;
        }
    }

    /**
     * 收支类型弹窗
     */
    private void showShouZhi(){
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(TransactionDetailActivity.this).inflate(R.layout.popup_transaction_sz, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_transaction_sz)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }
    /**
     * 业务类型弹窗
     */
    private void showYeWu(){
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(TransactionDetailActivity.this).inflate(R.layout.popup_transaction_yewu, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_transaction_yewu)
//                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 选择月份弹窗
     */
    private void showMonthPopup() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(TransactionDetailActivity.this).inflate(R.layout.popup_transaction_month, null);
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
        switch (layoutResId){
            case R.layout.popup_transaction_yewu:
                //获得PopupWindow布局里的View
                ImageView imgClose=view.findViewById(R.id.img_close);
                LabelsView labelsView=view.findViewById(R.id.labels_type);
                Button btnConfirm=view.findViewById(R.id.btn_confirm);

                labelsView.setLabels(listName);
                labelsView.setSelects(1);
                labelsView.setOnLabelSelectChangeListener(new LabelsView.OnLabelSelectChangeListener() {
                    @Override
                    public void onLabelSelectChange(TextView label, Object data, boolean isSelect, int position) {
                        if(isSelect){
                            Log.e(TAG, "onLabelSelectChange: "+data.toString() );
                        }
                    }
                });

                imgClose.setOnClickListener(v -> {
                    if (popupWindow != null) {
                        popupWindow.dismiss();
                    }
                });

                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                        Log.e(TAG, "onClick: ");
                    }
                });
                break;

            case R.layout.popup_transaction_sz:
                ImageView close=view.findViewById(R.id.img_close);
                RelativeLayout rlShou=view.findViewById(R.id.rl_shou);
                RelativeLayout rlZhi=view.findViewById(R.id.rl_zhi);
                RelativeLayout rlDaiFuBack=view.findViewById(R.id.rl_df_back);
                RelativeLayout rlDaiFuZhiChu=view.findViewById(R.id.rl_df_zhichu);
                CheckBox checkBoxShou=view.findViewById(R.id.checkbox_shou);
                CheckBox checkBoxZhi=view.findViewById(R.id.checkbox_zhi);
                CheckBox checkBoxDfhk=view.findViewById(R.id.checkbox_dfhk);
                CheckBox checkBoxDfZchu=view.findViewById(R.id.checkbox_dfzc);

                Button confirm=view.findViewById(R.id.btn_confirm);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });
                rlShou.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkBoxShou.setChecked(true);
                        checkBoxZhi.setChecked(false);
                        checkBoxDfhk.setChecked(false);
                        checkBoxDfZchu.setChecked(false);
                    }
                });

                rlZhi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkBoxShou.setChecked(false);
                        checkBoxZhi.setChecked(true);
                        checkBoxDfhk.setChecked(false);
                        checkBoxDfZchu.setChecked(false);
                    }
                });

                rlDaiFuBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkBoxShou.setChecked(false);
                        checkBoxZhi.setChecked(false);
                        checkBoxDfhk.setChecked(true);
                        checkBoxDfZchu.setChecked(false);
                    }
                });

                rlDaiFuZhiChu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkBoxShou.setChecked(false);
                        checkBoxZhi.setChecked(false);
                        checkBoxDfhk.setChecked(false);
                        checkBoxDfZchu.setChecked(true);
                    }
                });




                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                });

                break;
            case R.layout.popup_transaction_month:
                TextView tvResult=view.findViewById(R.id.tv_result);
                ImageView closeMonth=view.findViewById(R.id.img_close);
                ViewGroup linearLayout = view.findViewById(R.id.wheelview_container);
                Button confirmMonth=view.findViewById(R.id.btn_confirm);
                closeMonth.setOnClickListener(v -> {
                    if(popupWindow!=null){
                        popupWindow.dismiss();
                    }
                });

                //确认
                confirmMonth.setOnClickListener(v -> {
                    if(popupWindow!=null){
                        popupWindow.dismiss();
                    }
                    tvMonth.setText(tvResult.getText().toString());
                    searchTime= StringUtils.timeConvert(tvResult.getText().toString());
//                    //TODO 清空数据
//                    ToastUtils.showShortToast(TransactionDetailActivity.this,tvResult.getText().toString());
                });
                //获取当前年月
                Calendar calendar=Calendar.getInstance();
                int year=calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH)+1;
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
                List<String > listMonth= BigDecimalUtil.parseData();
                int yearIndex=0;
                int monthIndex=0;
                for (int i = 0; i <listDataYear.size() ; i++) {
                    if(listDataYear.get(i).contains(String.valueOf(year))){
                        yearIndex=i;
                        break;
                    }
                }
                for (int j = 0; j <listMonth.size() ; j++) {
                    if(listMonth.get(j).contains(String.valueOf(month))){
                        monthIndex=j;
                        break;
                    }
                }
                Log.e(TAG, "getChildView:yearIndex== "+yearIndex );
                Log.e(TAG, "getChildView: monthIndex=="+monthIndex );
                picker.setSelectedIndex(yearIndex,monthIndex);

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
                        tvResult.setText(picker.getSelectedFirstItem()  + item);
                    }

                    @Override
                    public void onThirdWheeled(int index, String item) {

                    }
                });

                Log.e(TAG, "initData: "+listDataYear.get(1));

                linearLayout.addView(picker.getContentView());
                break;
        }
    }
}
