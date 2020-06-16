package com.xk.mall.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ChangeToActivityBean;
import com.xk.mall.model.entity.TaskBean;
import com.xk.mall.model.eventbean.ChangeToNearBean;
import com.xk.mall.model.impl.TaskViewImpl;
import com.xk.mall.presenter.TaskPresenter;
import com.xk.mall.view.adapter.MakeTaskAdapter;
import com.xk.mall.view.widget.DialogShare;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * ClassName MakeTaskActivity
 * Description 任务中心
 * Author
 * Date
 * Version V1.0
 */
public class MakeTaskActivity extends BaseActivity<TaskPresenter> implements TaskViewImpl {
    private static final String TAG = "MakeTaskActivity";
    @BindView(R.id.tv_complete_current)
    TextView tvCompleteCurrent;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_task_sum_value)
    TextView tvTaskSumValue;//我的贡献值数
    @BindView(R.id.tv_make_task_foot)
    TextView tvFoot;


    @Override
    protected TaskPresenter createPresenter() {
        return new TaskPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_make_task;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setTextColor(Color.parseColor("#444444"));
        toolbar_title.setText("任务中心");
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getTaskList(MyApplication.userId);
    }

    @Override
    public void onGetTaskListSuccess(BaseModel<TaskBean> model) {
        if (model.getData() != null) {
            bindTaskList(model.getData());
        }
    }

    /**
     * 绑定数据
     */
    private void bindTaskList(TaskBean data) {
        tvTaskSumValue.setText("我的贡献值  " + data.getTaskValue());
        tvCompleteCurrent.setText("(" + data.getTodayTotalValue() + "/" + data.getMaxLimitValue() + ")");
        tvFoot.setText("每日最高获得" + data.getMaxLimitValue() + "个贡献值");
        recycleview.setLayoutManager(new LinearLayoutManager(mContext));
        MakeTaskAdapter makeTaskAdapter = new MakeTaskAdapter(mContext, R.layout.item_make_task, data.getList());
//        HeaderAndFooterWrapper headerAndFooterWrapper = new HeaderAndFooterWrapper(makeTaskAdapter);
//        RelativeLayout rlFoot = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.layout_make_task_foot, null);
//        TextView tvFoot = rlFoot.findViewById(R.id.tv_make_task_foot);
//        tvFoot.setText("每日最高获得" + data.getMaxLimitValue() + "个贡献值");
//        headerAndFooterWrapper.addFootView(rlFoot);
        recycleview.setAdapter(makeTaskAdapter);
        makeTaskAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                TaskBean.ListBean listBean = data.getList().get(position);
                if(listBean.getCompleteStatus() == 0){
                    goMakeTask(listBean);
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    /**
     * 去做任务
     */
    private void goMakeTask(TaskBean.ListBean listBean){
        if(listBean.getUrl().startsWith("xk")){
            if(listBean.getUrl().contains("near")){
                //跳转附近页面
                EventBus.getDefault().post(new ChangeToNearBean());
                finish();
            }else if(listBean.getUrl().contains("activity")){
                //跳转活动页面
                Intent it = new Intent();
                it.setAction(Intent.ACTION_VIEW);
                it.setData(Uri.parse(listBean.getUrl()));
                if(it.resolveActivity(getPackageManager()) != null){
                    startActivity(it);
                }else {
                    EventBus.getDefault().post(new ChangeToActivityBean());
                    finish();
                }
            }else {
                Intent it = new Intent();
                it.setAction(Intent.ACTION_VIEW);
                it.setData(Uri.parse(listBean.getUrl()));
                if(it.resolveActivity(getPackageManager()) != null){
                    startActivity(it);
                }
            }
        }else if(listBean.getUrl().startsWith("http")){
            //分享下载链接
            DialogShare dialogShare = new DialogShare(mContext);
            dialogShare.setUrl(listBean.getUrl());
            dialogShare.show();
        }
    }

}
