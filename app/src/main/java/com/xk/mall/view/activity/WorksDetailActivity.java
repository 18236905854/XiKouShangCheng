package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.CommentsBean;
import com.xk.mall.model.entity.CustomGuanBean;
import com.xk.mall.model.impl.CustomViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;
import com.xk.mall.model.request.CommentRequestBody;
import com.xk.mall.presenter.CustomPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.view.adapter.WorksDetailCommentAdapter;
import com.xk.mall.view.widget.CommonPopupWindow;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName WorksDetailActivity
 * Description 作品详情页面
 * Author 卿凯
 * Date 2019/6/21/021
 * Version V1.0
 */
public class WorksDetailActivity extends BaseActivity<CustomPresenter> implements CustomViewImpl,CommonPopupWindow.ViewInterface {
    @BindView(R.id.scroll_works_detail)
    NestedScrollView scrollWorksDetail;//滑动view
    @BindView(R.id.banner_works_detail)
    Banner bannerWorksDetail;//banner
    @BindView(R.id.iv_custom_head)
    ImageView ivCustomHead;//头像
    @BindView(R.id.tv_custom_name)
    TextView tvCustomName;//名称
    @BindView(R.id.tv_custom_time)
    TextView tvCustomTime;//时间
    @BindView(R.id.btn_custom_add_attention)
    Button btnCustomAddAttention;//添加关注
    @BindView(R.id.tv_work_name)
    TextView tvWorkName;
    @BindView(R.id.tv_works_detail_content)
    TextView tvWorksDetailContent;//内容
    @BindView(R.id.rv_works_detail)
    RecyclerView rvWorksDetail;
    @BindView(R.id.et_works_detail)
    TextView etWorksDetail;//评价输入框
    @BindView(R.id.tv_works_detail_love)
    TextView tvWorksDetailLove;//点赞按钮
    @BindView(R.id.tv_works_detail_msg)
    TextView tvWorksDetailMsg;//评论按钮

    int imageHeight = 375;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private int isPraise = 0;//是否点赞
    private int commentNum;
    private int praiseNum;

    private List<CommentsBean.ResultBean> commentList = new ArrayList<>();
    private int page = 1;
    private int limit = Constant.limit;
    private boolean hasMore=true;
    private WorksDetailCommentAdapter commentAdapter;
    private String workId;
    private String designerId;
    private CommonPopupWindow popupWindow;
    private EditText commentText;
    @Override
    protected CustomPresenter createPresenter() {
        return new CustomPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_works_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void initData() {
        ImmersionBar.setTitleBar(this, toolbar);
        //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        ImmersionBar.with(this).statusBarDarkFont(false, 0.2f)
                .init();
        Intent intent = getIntent();
        designerId = intent.getStringExtra(Constant.WORK_DESIGNER_ID);//设计师id
        workId = intent.getStringExtra(Constant.WORK_ID);//作品id
        String name = intent.getStringExtra(Constant.INTENT_DESIGNER_NAME);//设计师名称
        String headUrl = intent.getStringExtra(Constant.INTENT_DESIGNER_HEAD);//设计师头像
        int isAttention = intent.getIntExtra(Constant.INTENT_DESIGNER_ATTENTION, 0);//是否关注
        String workTime = intent.getStringExtra(Constant.WORK_TIME);
        String workDescription = intent.getStringExtra(Constant.WORK_DESCRIPTION);
        String workName=intent.getStringExtra(Constant.WORK_NAME);
        commentNum = intent.getIntExtra(Constant.WORK_COMMENT_NUM, 0);
        praiseNum = intent.getIntExtra(Constant.WORK_PRAISE_NUM, 0);
        isPraise = intent.getIntExtra(Constant.WORK_IS_PRAISE, 0);

        List<CustomGuanBean.ResultBean.ImageListBean> listBeans = (List<CustomGuanBean.ResultBean.ImageListBean>) intent.getSerializableExtra(Constant.INTENT_DESIGNER_IMGS);
        List<String> listImages = new ArrayList<>();
        for (int i = 0; i < listBeans.size(); i++) {
            listImages.add(listBeans.get(i).getImageUrl());
        }

        getCommentData();

        tvCustomName.setText(name);
        if(headUrl == null || TextUtils.isEmpty(headUrl)){
            ivCustomHead.setImageResource(R.drawable.mq_me_head_icon);
        }else {
            GlideUtil.showCircleLoading(mContext, headUrl, ivCustomHead);
        }
        tvCustomTime.setText(workTime);
        tvWorkName.setText(workName);
        tvWorksDetailContent.setText(workDescription);

        if (isAttention == 0) {
            btnCustomAddAttention.setEnabled(true);
            btnCustomAddAttention.setText("+ 关注");
        } else {
            btnCustomAddAttention.setEnabled(false);
            btnCustomAddAttention.setText("已关注");
        }

        if (isPraise == 1) {//点赞
            tvWorksDetailLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources()
                    .getDrawable(R.drawable.custom_love_checked), null, null, null);
        } else {
            tvWorksDetailLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources()
                    .getDrawable(R.drawable.custom_love), null, null, null);
        }
        tvWorksDetailLove.setText(String.valueOf(praiseNum));
        tvWorksDetailMsg.setText(String.valueOf(commentNum));


        bannerWorksDetail.setImageLoader(new GlideImageLoader());
        bannerWorksDetail.setImages(listImages);
        bannerWorksDetail.setBannerStyle(BannerConfig.NUM_INDICATOR);
        bannerWorksDetail.setIndicatorGravity(BannerConfig.RIGHT);
        //设置nearBanner动画效果
        bannerWorksDetail.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        bannerWorksDetail.isAutoPlay(true);
        //设置轮播时间
        bannerWorksDetail.setDelayTime(1500);
        //banner设置方法全部调用完毕时最后调用
        bannerWorksDetail.start();

        bannerWorksDetail.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                GlideUtil.lookBigImage(mContext, listImages, position);
            }
        });

        commentAdapter = new WorksDetailCommentAdapter(mContext, R.layout.works_detail_comment_item, commentList);
        rvWorksDetail.setLayoutManager(new LinearLayoutManager(mContext));
        rvWorksDetail.setAdapter(commentAdapter);

        scrollWorksDetail.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, i, y, i2, i3) -> {
            if (y <= 0) {
                //设置透明
                ll_title_menu.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_dark));
                flRight.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_dark));
                ivLeftBack.setImageResource(R.drawable.ic_back_white);
                ivRight.setImageResource(R.drawable.ic_right_white);
                toolbar.setBackgroundColor(Color.TRANSPARENT);
                setDarkStatusIcon(false);
            } else if (y > 0 && y <= imageHeight) {
                ivLeftBack.setImageResource(R.drawable.ic_back);
                ivRight.setImageResource(R.drawable.ic_right);
                setDarkStatusIcon(true);
                float scale = (float) y / imageHeight;
                float alpha = (255 * scale);
                // 只是layout背景透明(仿知乎滑动效果)白色透明
                toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                ll_title_menu.setBackgroundColor(Color.TRANSPARENT);
                flRight.setBackgroundColor(Color.TRANSPARENT);
            } else {
                ll_title_menu.setBackgroundColor(Color.WHITE);
                flRight.setBackgroundColor(Color.WHITE);
                ivLeftBack.setImageResource(R.drawable.ic_back);
                ivRight.setImageResource(R.drawable.ic_right);
                setDarkStatusIcon(true);
                toolbar.setBackgroundColor(Color.WHITE);
            }
        });

    }

    private void getCommentData() {
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (hasMore) {
                    page = page + 1;
                    //点击显示评论列表
                    mPresenter.getCommentList(workId,designerId,page,limit);
                } else {
                    mRefreshLayout.finishLoadMore();
                }
            }
        });

        mPresenter.getCommentList(workId,designerId,page,limit);
    }

    @OnClick({R.id.et_works_detail, R.id.btn_custom_add_attention,R.id.tv_works_detail_love})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_custom_add_attention://关注
                toAttent();
                break;
            case R.id.et_works_detail:
                //弹出输入框对话框
                showPopUp();
                break;
            case R.id.tv_works_detail_love://点赞
                dianZan();
                break;

        }
    }
    @Keep
    @LoginFilter
    private void toAttent(){
        AttentionRequestBody requestBody = new AttentionRequestBody();
        requestBody.setUserId(MyApplication.userId);
        requestBody.setDesignerId(designerId);
        requestBody.setOperationType(Constant.FOLLOW);
        mPresenter.addAttentionDesigner(requestBody);
    }
    @Keep
    @LoginFilter
    private void dianZan(){
        AttentionRequestBody dianZanBody=new AttentionRequestBody();
        dianZanBody.setDesignerWorkId(workId);
        dianZanBody.setUserId(MyApplication.userId);
        if (isPraise == 1){
            dianZanBody.setOperationType(Constant.CANCEL);
            mPresenter.cancelPraiseorSuc(dianZanBody);
        }else {
            dianZanBody.setOperationType(Constant.PRAISE);
            mPresenter.addOnPraiseorSuc(dianZanBody);
        }
    }

    //无用
    @Override
    public void onGetCustomSuc(BaseModel<CustomGuanBean> baseModel) {

    }

    //点赞成功
    @Override
    public void onPraiseorSuc(BaseModel model) {
        isPraise=1;
        praiseNum=praiseNum+1;
        tvWorksDetailLove.setText(String.valueOf(praiseNum));
        tvWorksDetailLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources()
                .getDrawable(R.drawable.custom_love_checked), null, null, null);
    }

    //取消点赞成功
    @Override
    public void onCancelPraiseSuc(BaseModel model) {
        isPraise=0;
        praiseNum=praiseNum-1;
        tvWorksDetailLove.setText(String.valueOf(praiseNum));
        tvWorksDetailLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources()
                .getDrawable(R.drawable.custom_love), null, null, null);
    }

    //关注成功
    @Override
    public void onAttentionSuc(BaseModel model) {
        btnCustomAddAttention.setEnabled(false);
        btnCustomAddAttention.setText("已关注");
    }

    //获取评论列表成功
    @Override
    public void onGetCommentList(BaseModel<CommentsBean> entity) {
        if (page == 1) {
            commentList.clear();
            commentList.addAll(entity.getData().getResult());
        } else {
            commentList.addAll(entity.getData().getResult());
        }

        if (entity.getData().getResult().size() < limit) {//当返回的数据小于请求的条数时，说明没有更多数据了
            hasMore = false;
        }

        commentAdapter.notifyDataSetChanged();

        if(mRefreshLayout!=null){
            mRefreshLayout.finishLoadMore();
            mRefreshLayout.finishRefresh();
        }

    }

    //发布评论成功
    @Override
    public void publishCommentSuc(CommentRequestBody resultBean, int count) {
        commentText.setText("");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(commentText.getWindowToken(), 0);

        CommentsBean.ResultBean addEntity=new CommentsBean.ResultBean();
        addEntity.setCommentContent(resultBean.getCommentContent());
        addEntity.setUserId(MyApplication.userId);
        addEntity.setUserName(SPUtils.getInstance().getString(Constant.NICK_NAME));
        addEntity.setHeadUrl(SPUtils.getInstance().getString(Constant.HEAD_URL));
        addEntity.setCommentTime(TimeUtils.getNowString());
        count++;

        tvWorksDetailMsg.setText(String.valueOf(count));

        commentList.add(0,addEntity);
        commentAdapter.notifyDataSetChanged();

    }
    /**
     * 显示评论列表对话框
     */
    private void showPopUp(){
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(WorksDetailActivity.this).inflate(R.layout.layout_bottom_comment, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.layout_bottom_comment)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.6f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        //防止PopupWindow被软件盘挡住
//        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);

    }

    @Override
    public void getChildView(View view, int layoutResId) {
         commentText = view.findViewById(R.id.et_show_msg);
        TextView bt_comment = view.findViewById(R.id.tv_show_msg_send);

        //发送评论
        bt_comment.setOnClickListener(new View.OnClickListener() {
            @Keep
            @LoginFilter
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(commentText.getText().toString())){
                    if(popupWindow!=null){
                        popupWindow.dismiss();
                    }
                    CommentRequestBody requestBody=new CommentRequestBody();
                    requestBody.setCommentContent(commentText.getText().toString());
                    requestBody.setDesignerId(designerId);
                    requestBody.setWorkId(workId);
                    requestBody.setUserId(MyApplication.userId);
                    mPresenter.publishComment(requestBody,commentNum);
                }
            }
        });


    }
}
