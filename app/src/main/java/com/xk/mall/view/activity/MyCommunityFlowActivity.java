package com.xk.mall.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.CommunityFlowBean;
import com.xk.mall.model.entity.FlowBean;
import com.xk.mall.model.entity.PhotoInfo;
import com.xk.mall.model.impl.CommunityFlowViewImpl;
import com.xk.mall.presenter.CommunityFlowPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.view.adapter.SheQuFlowAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的社区流量
 */
public class MyCommunityFlowActivity extends BaseActivity<CommunityFlowPresenter> implements CommunityFlowViewImpl {

    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.img_head)
    ImageView imgHead;//头像
    @BindView(R.id.img_vip)
    ImageView imgVip;//VIP图片
    @BindView(R.id.tv_name)
    TextView tvName;//用户名字
    @BindView(R.id.ll_head)
    LinearLayout llHead;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.tv_add_user)
    TextView tvAddUser;//新增用户数据
    @BindView(R.id.tv_zhuan_goods)
    TextView tvZhuanGoods;//转发商品数据
    @BindView(R.id.tv_fq_activity)
    TextView tvFqActivity;//发起活动数量
    @BindView(R.id.tv_gz_sjs)
    TextView tvGzSjs;//关注设计师数量
    @BindView(R.id.tv_start)
    TextView tvStart;//点赞数量
    @BindView(R.id.tv_pin_lun)
    TextView tvPinLun;//评论数
    @BindView(R.id.tv_complete_task)
    TextView tvCompleteTask;//完成任务数
    @BindView(R.id.tv_collec_store)
    TextView tvCollecStore;//收藏店铺数
    @BindView(R.id.iv_has_down)
    ImageView ivHasDown;

    @Override
    protected CommunityFlowPresenter createPresenter() {
        return new CommunityFlowPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shequ_flow;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarColor(R.color.color_title).titleBar(myToolbar).init();
        myToolbar.setBackgroundColor(getResources().getColor(R.color.color_title));
        myToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {
        GlideUtil.showUserCircle(mContext, SPUtils.getInstance().getString(Constant.HEAD_URL), imgHead);
        tvName.setText(SPUtils.getInstance().getString(Constant.NICK_NAME));
        mPresenter.getCommunity(MyApplication.userId);
    }

    @Override
    public void onGetCommunitySuccess(BaseModel<CommunityFlowBean> model) {
        if(model != null){
            CommunityFlowBean communityFlowBean = model.getData();
            tvAddUser.setText(String.valueOf(communityFlowBean.getInviteUsers()));
            tvZhuanGoods.setText(String.valueOf(communityFlowBean.getShareGoods()));
            tvFqActivity.setText(String.valueOf(communityFlowBean.getActivities()));
            tvGzSjs.setText(String.valueOf(communityFlowBean.getFollowDisigners()));
            tvStart.setText(String.valueOf(communityFlowBean.getLikes()));
            tvPinLun.setText(String.valueOf(communityFlowBean.getComments()));
            tvCompleteTask.setText(String.valueOf(communityFlowBean.getTasks()));
            tvCollecStore.setText(String.valueOf(communityFlowBean.getCollection()));
            if(communityFlowBean.getGrowUps() != null && communityFlowBean.getGrowUps().size() != 0){
                SheQuFlowAdapter flowAdapter = new SheQuFlowAdapter(mContext, communityFlowBean.getGrowUps(), (id, position) -> {

                });
                LinearLayoutManager manager = new LinearLayoutManager(this){
                    @Override
                    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
                        if(communityFlowBean.getGrowUps().size() < 4){
                            setMeasuredDimension(widthSpec, ConvertUtils.dp2px(50 * communityFlowBean.getGrowUps().size()));
                        }else {
                            super.onMeasure(recycler, state, widthSpec, heightSpec);
                        }
                    }
                };
                recycleView.setLayoutManager(manager);
                recycleView.setAdapter(flowAdapter);
                if(communityFlowBean.getGrowUps().size() >= 4){
                    ivHasDown.setVisibility(View.VISIBLE);
                }else {
                    ivHasDown.setVisibility(View.GONE);
                }
            }
        }
    }
}
