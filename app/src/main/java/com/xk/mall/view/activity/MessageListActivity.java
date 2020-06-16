package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.kennyc.view.MultiStateView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.MessageBean;
import com.xk.mall.model.eventbean.HomeMessageReadChangeBean;
import com.xk.mall.model.impl.MessageListImpl;
import com.xk.mall.presenter.MessageListPresenter;
import com.xk.mall.utils.GlideUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName MessageListActivity
 * Description 消息列表页面
 * Author 卿凯
 * Date 2019/7/4/004
 * Version V1.0
 */
public class MessageListActivity extends BaseActivity<MessageListPresenter> implements MessageListImpl {

    @BindView(R.id.refresh_message)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.rv_message)
    RecyclerView rvMessage;
    @BindView(R.id.state_view_msg)
    MultiStateView stateViewMsg;
    private String msgTypeId = "";//消息类型ID 1：系统消息，2：订单助手，3：活动消息，4：平台公告
    public static String MSG_TYPE_ID = "msg_type_id";//传递的消息类型的ID的key
    private int page = 1;//当前页数
    private List<MessageBean.MessageChildBean> result;
    private MessageListAdapter messageListAdapter;
    private ActivityMessageListAdapter activityMessageListAdapter;

    @Override
    protected MessageListPresenter createPresenter() {
        return new MessageListPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle();
    }

    @Override
    protected void initData() {
        result = new ArrayList<>();
        rvMessage.setLayoutManager(new LinearLayoutManager(mContext));
        mPresenter.getMessageList(MyApplication.userId, msgTypeId, page, 10);
        smartRefreshLayout.setOnRefreshListener(refreshLayout -> {
            page = 1;
            mPresenter.getMessageList(MyApplication.userId, msgTypeId, page, 10);
        });

        smartRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getMessageList(MyApplication.userId, msgTypeId, page, 10);
        });
    }

    /***
     * 根据传入的值设置不同的标题
     */
    private void setTitle() {
        Intent intent = getIntent();
        msgTypeId = intent.getStringExtra(MSG_TYPE_ID);
        if (msgTypeId.equals("1")) {
            setTitle("系统消息");
        } else if (msgTypeId.equals("2")) {
            setTitle("订单助手");
        } else if (msgTypeId.equals("3")) {
            setTitle("活动消息");
        } else if (msgTypeId.equals("4")) {
            setTitle("公告");
        }
    }

    /**
     * 获取消息列表成功
     */
    @Override
    public void onGetListSuccess(BaseModel<MessageBean> messageBeanBaseModel) {
        smartRefreshLayout.finishRefresh();
        smartRefreshLayout.finishLoadMore();
        if(messageBeanBaseModel != null && messageBeanBaseModel.getData() != null){
            if(page == 1){
                result.clear();
            }
            result.addAll(messageBeanBaseModel.getData().result);
            if(page == 1 && messageBeanBaseModel.getData().result.size() == 0){
                stateViewMsg.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
            if(messageBeanBaseModel.getData().result.size() < 10){
                smartRefreshLayout.setEnableLoadMore(false);
            }else {
                smartRefreshLayout.setEnableLoadMore(true);
            }
            bindList(result);
        }else {
            stateViewMsg.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    /**
     * 绑定列表adapter
     */
    private void bindList(List<MessageBean.MessageChildBean> messageChildBeans){
        switch (msgTypeId){
            case "1":
            case "2":
            case "4":
                if(messageListAdapter == null){
                    messageListAdapter = new MessageListAdapter(mContext, R.layout.item_message_list, messageChildBeans);
                    rvMessage.setAdapter(messageListAdapter);
                    messageListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            //点击跳转消息详情
                            messageChildBeans.get(position).isRead = 1;
                            messageListAdapter.notifyItemChanged(position);
                            goDetail(messageChildBeans.get(position));
                        }

                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            return false;
                        }
                    });
                }else {
                    messageListAdapter.notifyDataSetChanged();
                }
                break;

            case "3":
                if(activityMessageListAdapter == null){
                    activityMessageListAdapter = new ActivityMessageListAdapter(mContext, R.layout.item_activity_message_list, messageChildBeans);
                    rvMessage.setAdapter(activityMessageListAdapter);
                    activityMessageListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                            //点击跳转详情
                            messageChildBeans.get(position).isRead = 1;
                            activityMessageListAdapter.notifyItemChanged(position);
                            goDetail(messageChildBeans.get(position));
                        }

                        @Override
                        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                            return false;
                        }
                    });
                }else {
                    activityMessageListAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /***
     * 跳转详情方法
     * @param messageChildBean
     */
    private void goDetail(MessageBean.MessageChildBean messageChildBean){
        EventBus.getDefault().post(new HomeMessageReadChangeBean());
        if(null != messageChildBean.url){
            if(messageChildBean.url.startsWith("xk://")){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(messageChildBean.url));
                if(intent.resolveActivity(getPackageManager()) != null){
                    ActivityUtils.startActivity(intent);
                }
            }else if(messageChildBean.url.startsWith("http")){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse(messageChildBean.url);
                intent.setData(content_url);
                startActivity(intent);
            }else {
                Intent intent = new Intent(mContext, MessageDetailActivity.class);
                intent.putExtra(MessageDetailActivity.MSG_TYPE_ID, msgTypeId);
                intent.putExtra(MessageDetailActivity.MSG_ID, messageChildBean.id);
                ActivityUtils.startActivity(intent);
            }
        }else {
            Intent intent = new Intent(mContext, MessageDetailActivity.class);
            intent.putExtra(MessageDetailActivity.MSG_TYPE_ID, msgTypeId);
            intent.putExtra(MessageDetailActivity.MSG_ID, messageChildBean.id);
            ActivityUtils.startActivity(intent);
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        stateViewMsg.setViewState(MultiStateView.VIEW_STATE_EMPTY);
    }

    /**
     * 系统消息，公告，订单助手的消息的adapter
     */
    class MessageListAdapter extends CommonAdapter<MessageBean.MessageChildBean>{

        public MessageListAdapter(Context context, int layoutId, List<MessageBean.MessageChildBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, MessageBean.MessageChildBean messageChildBean, int position) {
            holder.setText(R.id.tv_message_list_title, messageChildBean.title);
            holder.setText(R.id.tv_message_list_content, messageChildBean.content);
            holder.setText(R.id.tv_message_list_time, messageChildBean.sendTime);
            TextView tvIsRead = holder.getView(R.id.tv_message_list_read);
            if(messageChildBean.isRead == 1){
                tvIsRead.setText("已读");
                tvIsRead.setTextColor(Color.parseColor("#999999"));
                tvIsRead.setBackgroundResource(R.drawable.bg_msg_read);
            }else {
                tvIsRead.setText("未读");
                tvIsRead.setTextColor(Color.parseColor("#F94119"));
                tvIsRead.setBackgroundResource(R.drawable.bg_msg_unread);
            }
        }
    }

    /**
     * 活动消息的adapter
     */
    class ActivityMessageListAdapter extends CommonAdapter<MessageBean.MessageChildBean>{

        public ActivityMessageListAdapter(Context context, int layoutId, List<MessageBean.MessageChildBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, MessageBean.MessageChildBean messageChildBean, int position) {
            holder.setText(R.id.tv_message_list_title, messageChildBean.title);
            holder.setText(R.id.tv_message_list_content, messageChildBean.content);
            holder.setText(R.id.tv_message_list_time, messageChildBean.sendTime);
            ImageView ivLogo = holder.getView(R.id.iv_message_list_img);
            TextView tvContent = holder.getView(R.id.tv_message_list_content);
            if(messageChildBean.img == null || TextUtils.isEmpty(messageChildBean.img)){
                //隐藏图片
                ivLogo.setVisibility(View.GONE);
                tvContent.setMaxLines(6);
            }else {
                ivLogo.setVisibility(View.VISIBLE);
                tvContent.setMaxLines(2);
                GlideUtil.show(mContext, messageChildBean.img, ivLogo);
            }
        }
    }
}
