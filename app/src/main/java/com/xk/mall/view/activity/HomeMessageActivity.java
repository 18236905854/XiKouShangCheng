package com.xk.mall.view.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.HomeMessageBean;
import com.xk.mall.model.impl.HomeMessageViewImpl;
import com.xk.mall.presenter.HomeMessagePresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.MeiQiaUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;

/**
 * ClassName HomeMessageActivity
 * Description 首页消息
 * Author
 * Date
 * Version
 */
public class HomeMessageActivity extends BaseActivity<HomeMessagePresenter> implements HomeMessageViewImpl {


    @BindView(R.id.rl_order)
    RelativeLayout rlOrder;
    @BindView(R.id.rl_activity)
    RelativeLayout rlActivity;
    @BindView(R.id.rl_sys)
    RelativeLayout rlSys;
    @BindView(R.id.rl_notice)
    RelativeLayout rlNotice;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;//订单消息未读数
    QBadgeView orderBadgeView;
    @BindView(R.id.tv_activity_num)
    TextView tvActivityNum;//活动消息未读数
    QBadgeView activityBadgeView;
    @BindView(R.id.tv_system_num)
    TextView tvSystemNum;//系统消息未读数
    QBadgeView systemBadgeView;
    @BindView(R.id.tv_notice_num)
    TextView tvNoticeNum;//公告消息未读数
    QBadgeView noticeBadgeView;
    private HomeMessageBean messageBean;

    @Override
    protected HomeMessagePresenter createPresenter() {
        return new HomeMessagePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_message;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        showLeft(true);
        showRight(true);
        setStatusBar(getResources().getColor(R.color.color_title));
        setDarkStatusIcon(false);
        setTitle("消息");
        setRightText("客服");
        toolbar_title.setTextColor(getResources().getColor(R.color.white));
        toolbar_title.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        tv_title_right.setTextColor(getResources().getColor(R.color.white));
        ivLeftBack.setImageResource(R.drawable.ic_back_white);
        setOnRightClickListener(v -> {
            MeiQiaUtil.initMeiqiaSDK(mContext);
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        messageBean = (HomeMessageBean) intent.getSerializableExtra(Constant.HOME_MESSAGE);

        if(messageBean != null){
            for(HomeMessageBean.TypesListBean typesListBean : messageBean.getTypesList()){
                if(typesListBean.getId().equals("1") && typesListBean.getUnreadNum() != 0){
                    //显示系统消息未读数
                    if(systemBadgeView == null){
                        systemBadgeView = new QBadgeView(this);
                    }
                    showBadgeView(tvSystemNum, systemBadgeView, typesListBean);
                }else if(typesListBean.getId().equals("2") && typesListBean.getUnreadNum() != 0){
                    //显示订单助手消息未读数
                    if(orderBadgeView == null){
                        orderBadgeView = new QBadgeView(this);
                    }
                    showBadgeView(tvOrderNum, orderBadgeView, typesListBean);
                }else if(typesListBean.getId().equals("3") && typesListBean.getUnreadNum() != 0){
                    //显示活动消息未读数
                    if(activityBadgeView == null){
                        activityBadgeView = new QBadgeView(this);
                    }
                    showBadgeView(tvActivityNum, activityBadgeView, typesListBean);
                }else if(typesListBean.getId().equals("4") && typesListBean.getUnreadNum() != 0){
                    //显示平台公告未读数
                    if (noticeBadgeView == null){
                        noticeBadgeView = new QBadgeView(this);
                    }
                    showBadgeView(tvNoticeNum, noticeBadgeView, typesListBean);
                }
            }
        }
    }

    /**
     * 显示未读消息数
     * @param tvTarget  目标tv
     * @param typesListBean 数据
     */
    private void showBadgeView(TextView tvTarget, QBadgeView orderBadge, HomeMessageBean.TypesListBean typesListBean){
        orderBadge.bindTarget(tvTarget).setBadgeGravity(Gravity.CENTER).setBadgeNumber(typesListBean.getUnreadNum()).setExactMode(false);
        orderBadge.setBadgeTextSize(13,true);
        orderBadge.setBadgePadding(6, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.rl_order, R.id.rl_activity, R.id.rl_sys, R.id.rl_notice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_order:
                Intent intentOrder = new Intent(mContext, MessageListActivity.class);
                intentOrder.putExtra(MessageListActivity.MSG_TYPE_ID, "2");
                ActivityUtils.startActivity(intentOrder);
                readMessage("2");
                if(orderBadgeView !=null){
                    orderBadgeView.hide(true);
                }
                break;
            case R.id.rl_activity:
                Intent intentActivity = new Intent(mContext, MessageListActivity.class);
                intentActivity.putExtra(MessageListActivity.MSG_TYPE_ID, "3");
                ActivityUtils.startActivity(intentActivity);
                readMessage("3");
                if(activityBadgeView != null){
                    activityBadgeView.hide(true);
                }
                break;
            case R.id.rl_sys:
                Intent intentSys = new Intent(mContext, MessageListActivity.class);
                intentSys.putExtra(MessageListActivity.MSG_TYPE_ID, "1");
                ActivityUtils.startActivity(intentSys);
                readMessage("1");
                if(systemBadgeView != null){
                    systemBadgeView.hide(true);
                }
                break;
            case R.id.rl_notice:
                Intent intentNotice = new Intent(mContext, MessageListActivity.class);
                intentNotice.putExtra(MessageListActivity.MSG_TYPE_ID, "4");
                ActivityUtils.startActivity(intentNotice);
                readMessage("4");
                if(noticeBadgeView != null){
                    noticeBadgeView.hide(true);
                }
                break;
        }
    }

    private void readMessage(String id){
        if(messageBean != null){
            int size = messageBean.getTotalUnreadNum();
            for(HomeMessageBean.TypesListBean typesListBean : messageBean.getTypesList()){
                if(typesListBean.getId().equals(id) && typesListBean.getUnreadNum() != 0){
                    //显示系统消息未读数
                    messageBean.setTotalUnreadNum(size - typesListBean.getUnreadNum());
                    typesListBean.setUnreadNum(0);
                }
            }
            Logger.e("发送消息");
            EventBus.getDefault().post(messageBean);
        }
    }

    @Override
    public void onGetUnreadMessSuc(BaseModel<HomeMessageBean> model) {

    }
}
