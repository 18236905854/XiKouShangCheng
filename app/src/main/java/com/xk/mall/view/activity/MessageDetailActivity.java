package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.MessageBean;
import com.xk.mall.model.impl.MessageDetailImpl;
import com.xk.mall.presenter.MessageDetailPresenter;
import com.xk.mall.utils.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName MessageDetailActivity
 * Description 消息详情页面
 * Author 卿凯
 * Date 2019/7/4/004
 * Version V1.0
 */
public class MessageDetailActivity extends BaseActivity<MessageDetailPresenter> implements MessageDetailImpl {

    /**
     * 传递过来的消息类型ID的Key
     */
    public static String MSG_TYPE_ID = "msg_type_id";
    /**
     * 传递过来的消息ID的Key
     */
    public static String MSG_ID = "msg_id";
    @BindView(R.id.tv_message_detail_title)
    TextView tvMessageDetailTitle;
    @BindView(R.id.tv_message_detail_time)
    TextView tvMessageDetailTime;
    @BindView(R.id.tv_message_detail_see)
    TextView tvMessageDetailSee;
    @BindView(R.id.iv_message_detail_img)
    ImageView ivMessageDetailImg;
    @BindView(R.id.tv_message_detail_content)
    TextView tvMessageDetailContent;

    @Override
    protected MessageDetailPresenter createPresenter() {
        return new MessageDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String msgTypeId = intent.getStringExtra(MSG_TYPE_ID);
        String msgId = intent.getStringExtra(MSG_ID);
        mPresenter.getMsgDetail(msgTypeId, msgId);
    }

    /**
     * 获取消息详情成功
     */
    @Override
    public void onMessageDetailSuccess(BaseModel<MessageBean.MessageChildBean> childBeanBaseModel) {
        if(childBeanBaseModel.getData() != null){
            bindModel(childBeanBaseModel.getData());
        }
    }

    /**
     * 绑定数据
     */
    private void bindModel(MessageBean.MessageChildBean messageChildBean){
        tvMessageDetailTitle.setText(messageChildBean.title);
        tvMessageDetailContent.setText(messageChildBean.content);
        if(messageChildBean.img != null){
            ivMessageDetailImg.setVisibility(View.VISIBLE);
            GlideUtil.show(mContext, messageChildBean.img, ivMessageDetailImg);
        }else {
            ivMessageDetailImg.setVisibility(View.GONE);
        }
        List<String> images = new ArrayList<>();
        images.add(messageChildBean.img);
        ivMessageDetailImg.setOnClickListener(v -> GlideUtil.lookBigImage(mContext,images,0));
        tvMessageDetailTime.setText(messageChildBean.sendTime);
    }

}
