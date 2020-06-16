package com.xk.mall.view.activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.donkingliang.labels.LabelsView;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.widget.CommomDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName NearShopSearchActivity
 * Description 附近店铺搜索页面
 * Author 卿凯
 * Date 2019/7/23/023
 * Version V1.0
 */
public class NearShopSearchActivity extends BaseActivity {
    @BindView(R.id.et_search)
    EditText editSearch;
    @BindView(R.id.tv_finish)
    TextView tvFinish;
    @BindView(R.id.tv_history)
    TextView tvHistory;
    @BindView(R.id.img_delete)
    ImageView imgDelete;
    @BindView(R.id.labels_history)
    LabelsView labelsHistory;
    private double longitude;
    private double latitude;

    private List<String> historyList;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_near_shop_search;
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
        MyApplication.getInstance().addActivity(NearShopSearchActivity.this);
        longitude = getIntent().getDoubleExtra(Constant.INTENT_LONGITUDE, 0);
        latitude = getIntent().getDoubleExtra(Constant.INTENT_LATITUDE, 0);
        String history = SPUtils.getInstance().getString(Constant.SHOP_SEARCH_HISTORY, "");
        if(history != null && !TextUtils.isEmpty(history)){
            tvHistory.setVisibility(View.VISIBLE);
            labelsHistory.setVisibility(View.VISIBLE);
            imgDelete.setVisibility(View.VISIBLE);
            List<String> historyList = new ArrayList<>();
            if(history.contains("--")){
                String[] historyAry = history.split("--");
                historyList.addAll(Arrays.asList(historyAry));
            }else {
                historyList.add(history);
            }
            labelsHistory.setLabels(historyList);
        }else {
            //隐藏历史记录
            tvHistory.setVisibility(View.GONE);
            labelsHistory.setVisibility(View.GONE);
            imgDelete.setVisibility(View.GONE);
        }


        labelsHistory.setOnLabelClickListener((label, data, position) -> {
            enterResultActivity(data.toString());
            Log.e(TAG, "onLabelClick: "+position );
        });

        editSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH && !TextUtils.isEmpty(editSearch.getText().toString().trim())) {
                enterResultActivity(editSearch.getText().toString().trim());
            }
            return false;
        });
    }

    private void enterResultActivity(String name){
        Intent intent = new Intent(mContext, NearSearchResultActivity.class);
        intent.putExtra(NearSearchResultActivity.SHOP_NAME,name);
        intent.putExtra(Constant.INTENT_LONGITUDE, longitude);
        intent.putExtra(Constant.INTENT_LATITUDE, latitude);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(NearShopSearchActivity.this).toBundle());
    }

    @OnClick({R.id.tv_finish, R.id.img_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                MyApplication.getInstance().closeActivity();
                break;
            case R.id.img_delete:
                new CommomDialog(mContext, R.style.mydialog, "清空历史搜索记录？", (dialog, confirm) -> {
                    if (confirm) {
                        imgDelete.setVisibility(View.GONE);
                        tvHistory.setVisibility(View.GONE);
                        labelsHistory.setVisibility(View.GONE);
                        dialog.dismiss();
                        SPUtils.getInstance().put(Constant.SHOP_SEARCH_HISTORY, "");
                    }
                }).show();
                break;
        }
    }
}
