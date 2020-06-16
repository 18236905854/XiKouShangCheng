package com.xk.mall.view.activity;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.donkingliang.labels.LabelsView;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.HomeSearchGoodsPageBean;
import com.xk.mall.model.entity.XiKouZuanBean;
import com.xk.mall.model.impl.HomeSearchViewImpl;
import com.xk.mall.presenter.HomeSearchPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.KeyBoardUtils;
import com.xk.mall.view.adapter.XiKouZuanAdapter;
import com.xk.mall.view.widget.CommomDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ClassName HomeSearchActivity
 * Description 首页搜索
 * Author
 * Date
 * Version V1.0
 */
public class HomeSearchActivity extends BaseActivity {
    private static final String TAG = "HomeSearchActivity";
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
    @BindView(R.id.labels_hot)
    LabelsView labelsHot;

    private List<String> historyList = new ArrayList<>();
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_search;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        String history = SPUtils.getInstance().getString(Constant.HOME_SEARCH_HISTORY, "");
        Logger.e("搜索历史:" + history);
        if(history != null && !TextUtils.isEmpty(history)){
            tvHistory.setVisibility(View.VISIBLE);
            labelsHistory.setVisibility(View.VISIBLE);
            imgDelete.setVisibility(View.VISIBLE);
            List<String> historyList = new ArrayList<>();
            if(history.contains("--")){
                String[] historyAry = history.split("--");
                String[] newHistoryAry = new String[history.length()];
                if(historyAry.length >= 9){
                    for(int i = 0; i < 9; i++){
                        newHistoryAry[i] = historyAry[i];
                    }
                    historyList.addAll(Arrays.asList(newHistoryAry));
                }else {
                    historyList.addAll(Arrays.asList(historyAry));
                }
            }else {
                historyList.add(history);
            }
            List<String> newHistoryList = new ArrayList<>();
            for(String s : historyList){
                if(s != null && !TextUtils.isEmpty(s)){
                    newHistoryList.add(s);
                }
            }
            labelsHistory.setLabels(newHistoryList);
        }else {
            //隐藏历史记录
            tvHistory.setVisibility(View.GONE);
            labelsHistory.setVisibility(View.GONE);
            imgDelete.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(HomeSearchActivity.this);

        labelsHistory.setOnLabelClickListener((label, data, position) -> {
            enterResultActivity(data.toString());
            Log.e(TAG, "onLabelClick: "+position );
        });

        labelsHistory.setOnLabelClickListener((label, data, position) -> {
            enterResultActivity(data.toString());
            Log.e(TAG, "onLabelClick: "+position );
        });

        labelsHot.setOnLabelClickListener((label, data, position) -> {
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


    @OnClick({R.id.tv_finish, R.id.img_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_finish:
                MyApplication.getInstance().closeActivity();
                break;
            case R.id.img_delete:
                new CommomDialog(mContext, R.style.mydialog, "清空历史搜索记录？", new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            historyList.clear();
                            labelsHistory.setLabels(historyList);
                            imgDelete.setVisibility(View.GONE);
                            tvHistory.setVisibility(View.GONE);
                            labelsHistory.setVisibility(View.GONE);
                            SPUtils.getInstance().put(Constant.HOME_SEARCH_HISTORY, "");
                            dialog.dismiss();
                        }
                    }
                }).show();
                break;
        }
    }

    private void enterResultActivity(String name){
        Intent intent=new Intent(mContext, SearchResultActivity.class);
        intent.putExtra(SearchResultActivity.SEARCH_NAME,name);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(HomeSearchActivity.this).toBundle());
    }
}
