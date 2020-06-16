package com.xk.mall.view.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.donkingliang.labels.LabelsView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.OrderType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ClassName OrderFilterActivity
 * Description 订单筛选页面
 * Author 卿凯
 * Date 2019/6/29/029
 * Version V1.0
 */
public class OrderFilterActivity extends BaseActivity {
    private static final String TAG = "OrderFilterActivity";
    @BindView(R.id.et_search)
    EditText editSearch;//输入框
    @BindView(R.id.tv_finish)
    TextView tvFinish;//取消按钮
    @BindView(R.id.labels_time)
    LabelsView labelsTime;//创建时间label
    @BindView(R.id.tv_activity)
    TextView tvActivity;
    @BindView(R.id.labels_activity)
    LabelsView labelsActivity;//活动版块
    @BindView(R.id.et_order_filter_before)
    EditText etOrderFilterBefore;
    @BindView(R.id.et_order_filter_last)
    EditText etOrderFilterLast;
    @BindView(R.id.tv_order_filter_reset)
    TextView tvOrderFilterReset;
    @BindView(R.id.tv_order_filter_success)
    TextView tvOrderFilterSuccess;

    /**intent传递过来的订单类型key*/
    public static String ORDER_TYPE = "order_type";
    private int orderType = 0;
    private int timeFlag = 0;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_filter;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void initData() {
        //之前是传递过来的，现在改成选择
//        orderType = getIntent().getIntExtra(ORDER_TYPE, -1);
        boolean isShowActivity = getIntent().getBooleanExtra("is_show_activity", true);
        if(!isShowActivity){
            orderType = OrderType.SELL_TYPE;
            tvActivity.setVisibility(View.GONE);
            labelsActivity.setVisibility(View.GONE);
        }
        MyApplication.getInstance().addActivity(OrderFilterActivity.this);
        List<String> hotList = new ArrayList<>();
        hotList.add("不限");
        hotList.add("最近一个月");
        hotList.add("最近三个月");

        List<String> activityList = new ArrayList<>();
        activityList.add("0元抢");
        activityList.add("多买多折");
        activityList.add("喜立得");
        activityList.add("全球买手");
        activityList.add("吾G购");
        activityList.add("新人专区");
        labelsActivity.setLabels(activityList);
        labelsActivity.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                if(position == 0){
                    orderType = OrderType.ZERO_TYPE;
                }else if(position == 1){
                    orderType = OrderType.MANY_TYPE;
                }else if(position == 2){
                    orderType = OrderType.CUT_TYPE;
                }else if(position == 3){
                    orderType = OrderType.GLOBAL_TYPE;
                }else if(position == 4){
                    orderType = OrderType.WUG_TYPE;
                }else if(position == 5){
                    orderType = OrderType.NEW_PERSON_TYPE;
                }
            }
        });

        labelsTime.setLabels(hotList);

        labelsTime.setOnLabelClickListener((label, data, position) -> {
            timeFlag = position;
            //新需求需要选择活动板块，如果不选则表示全部，必须要点击完成才能进入搜索结果页面
//            enterResultActivity("", 0, 0);
        });

        editSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(editSearch.getText().toString().trim())) {
//                    //如果actionId是搜索的id，则进行下一步的操作
                enterResultActivity(editSearch.getText().toString().trim(), 0, 0);

            }
            return false;
        });

    }

    @OnClick({R.id.tv_finish, R.id.tv_order_filter_reset, R.id.tv_order_filter_success})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                finish();
                break;

            case R.id.tv_order_filter_reset://重置
                editSearch.setText("");
                etOrderFilterBefore.setText("");
                etOrderFilterLast.setText("");
                labelsTime.clearAllSelect();
                labelsActivity.clearAllSelect();
                break;

            case R.id.tv_order_filter_success://完成
                double left = 0.0;
                if(!TextUtils.isEmpty(etOrderFilterBefore.getText().toString())){
                    left = Double.parseDouble(etOrderFilterBefore.getText().toString());
                }
                double right = 0.0;
                if(!TextUtils.isEmpty(etOrderFilterLast.getText().toString())){
                    right = Double.parseDouble(etOrderFilterLast.getText().toString());
                }
//                if(right == 0){
//                    ToastUtils.showShortToast(mContext, "最高金额不能为0");
//                    return;
//                }
                if(right < left){
                    ToastUtils.showShortToast(mContext, "最高金额不能小于最低金额");
                    return;
                }
                String name = editSearch.getText().toString();
                enterResultActivity(name, left, right);
                break;
        }
    }

    //订单类型;1-0元竞拍订单;2-多买多折订单;3-喜立得订单;4-吾G订单;5-全球买手订单;6-定制拼团订单;7:OTO订单
//    public static int ZERO_TYPE = 1;
//    public static int MANY_TYPE = 2;
//    public static int CUT_TYPE = 3;
//    public static int WUG_TYPE = 4;
//    public static int GLOBAL_TYPE = 5;
//    public static int GROUP_TYPE = 6;
//    public static int OTO_TYPE = 7;

    /**
     * 根据订单类型跳转页面
     */
    private void enterResultActivity(String name, double left, double right){
        if (orderType == OrderType.ZERO_TYPE) {
            Intent intent = new Intent(mContext, ZeroOrderSearchResultActivity.class);
            intent.putExtra(ZeroOrderSearchResultActivity.SEARCH_NAME,name);
            intent.putExtra(ZeroOrderSearchResultActivity.ORDER_TYPE,orderType);
            intent.putExtra(ZeroOrderSearchResultActivity.TIME_FLAG,timeFlag);
            intent.putExtra(ZeroOrderSearchResultActivity.LEFT_PRICE,Double.valueOf(left * 100).intValue());
            intent.putExtra(ZeroOrderSearchResultActivity.RIGHT_PRICE,Double.valueOf(right * 100).intValue());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(OrderFilterActivity.this).toBundle());
        } else if (orderType == OrderType.MANY_TYPE) {
            Intent intent = new Intent(mContext, ManyBuyOrderSearchResultActivity.class);
            intent.putExtra(ManyBuyOrderSearchResultActivity.SEARCH_NAME,name);
            intent.putExtra(ManyBuyOrderSearchResultActivity.ORDER_TYPE,orderType);
            intent.putExtra(ManyBuyOrderSearchResultActivity.TIME_FLAG,timeFlag);
            intent.putExtra(ManyBuyOrderSearchResultActivity.LEFT_PRICE,Double.valueOf(left * 100).intValue());
            intent.putExtra(ManyBuyOrderSearchResultActivity.RIGHT_PRICE,Double.valueOf(right * 100).intValue());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(OrderFilterActivity.this).toBundle());
        } else if (orderType == OrderType.CUT_TYPE) {
            Intent intent = new Intent(mContext, CutOrderSearchResultActivity.class);
            intent.putExtra(CutOrderSearchResultActivity.SEARCH_NAME,name);
            intent.putExtra(CutOrderSearchResultActivity.ORDER_TYPE,orderType);
            intent.putExtra(CutOrderSearchResultActivity.TIME_FLAG,timeFlag);
            intent.putExtra(CutOrderSearchResultActivity.LEFT_PRICE,Double.valueOf(left * 100).intValue());
            intent.putExtra(CutOrderSearchResultActivity.RIGHT_PRICE,Double.valueOf(right * 100).intValue());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(OrderFilterActivity.this).toBundle());
        } else if (orderType == OrderType.WUG_TYPE) {
            Intent intent = new Intent(mContext, WuGOrderSearchResultActivity.class);
            intent.putExtra(WuGOrderSearchResultActivity.SEARCH_NAME,name);
            intent.putExtra(WuGOrderSearchResultActivity.ORDER_TYPE,orderType);
            intent.putExtra(WuGOrderSearchResultActivity.TIME_FLAG,timeFlag);
            intent.putExtra(WuGOrderSearchResultActivity.LEFT_PRICE,Double.valueOf(left * 100).intValue());
            intent.putExtra(WuGOrderSearchResultActivity.RIGHT_PRICE,Double.valueOf(right * 100).intValue());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(OrderFilterActivity.this).toBundle());
        } else if (orderType == OrderType.GLOBAL_TYPE) {
            Intent intent = new Intent(mContext, GlobalOrderSearchResultActivity.class);
            intent.putExtra(GlobalOrderSearchResultActivity.SEARCH_NAME,name);
            intent.putExtra(GlobalOrderSearchResultActivity.ORDER_TYPE,orderType);
            intent.putExtra(GlobalOrderSearchResultActivity.TIME_FLAG,timeFlag);
            intent.putExtra(GlobalOrderSearchResultActivity.LEFT_PRICE,Double.valueOf(left * 100).intValue());
            intent.putExtra(GlobalOrderSearchResultActivity.RIGHT_PRICE,Double.valueOf(right * 100).intValue());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(OrderFilterActivity.this).toBundle());
        } else if (orderType == OrderType.GROUP_TYPE) {
            Intent intent = new Intent(mContext, GroupOrderSearchResultActivity.class);
            intent.putExtra(GroupOrderSearchResultActivity.SEARCH_NAME,name);
            intent.putExtra(GroupOrderSearchResultActivity.ORDER_TYPE,orderType);
            intent.putExtra(GroupOrderSearchResultActivity.TIME_FLAG,timeFlag);
            intent.putExtra(GroupOrderSearchResultActivity.LEFT_PRICE,Double.valueOf(left * 100).intValue());
            intent.putExtra(GroupOrderSearchResultActivity.RIGHT_PRICE,Double.valueOf(right * 100).intValue());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(OrderFilterActivity.this).toBundle());
        } else if (orderType == OrderType.OTO_TYPE) {//OTO订单

        }else if(orderType == OrderType.NEW_PERSON_TYPE){//新人专区订单
            Intent intent = new Intent(mContext, WuGOrderSearchResultActivity.class);
            intent.putExtra(WuGOrderSearchResultActivity.SEARCH_NAME,name);
            intent.putExtra(WuGOrderSearchResultActivity.ORDER_TYPE,orderType);
            intent.putExtra(WuGOrderSearchResultActivity.TIME_FLAG,timeFlag);
            intent.putExtra(WuGOrderSearchResultActivity.LEFT_PRICE,Double.valueOf(left * 100).intValue());
            intent.putExtra(WuGOrderSearchResultActivity.RIGHT_PRICE,Double.valueOf(right * 100).intValue());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(OrderFilterActivity.this).toBundle());
        }else if(orderType == OrderType.SELL_TYPE){//寄卖订单
            Intent intent = new Intent(mContext, SellOrderSearchResultActivity.class);
            intent.putExtra(SellOrderSearchResultActivity.SEARCH_NAME,name);
            intent.putExtra(SellOrderSearchResultActivity.ORDER_TYPE,orderType);
            intent.putExtra(SellOrderSearchResultActivity.TIME_FLAG,timeFlag);
            intent.putExtra(SellOrderSearchResultActivity.LEFT_PRICE,Double.valueOf(left * 100).intValue());
            intent.putExtra(SellOrderSearchResultActivity.RIGHT_PRICE,Double.valueOf(right * 100).intValue());
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(OrderFilterActivity.this).toBundle());
        }else {//搜全部活动板块
            ToastUtils.showShortToast(mContext, "请选择活动板块");
        }


    }
}
