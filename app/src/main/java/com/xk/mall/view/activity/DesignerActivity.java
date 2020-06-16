package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.CustomGuanBean;
import com.xk.mall.model.entity.DesignerInfoBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.eventbean.DesignerEventBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.DesignerViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;
import com.xk.mall.presenter.DesignerPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.view.widget.DialogShare;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ClassName DesignerActivity
 * Description 设计师主页
 * Author 卿凯
 * Date 2019/6/20/020
 * Version V1.0
 */
public class DesignerActivity extends BaseActivity<DesignerPresenter> implements DesignerViewImpl {
    public static final String DESIGNER_ID = "designer_id";//设计师id key
    public static final String OPERATION_ID = "operation_id";//设计师id key
    @BindView(R.id.scroll_designer)
    NestedScrollView scrollView;
    @BindView(R.id.rv_designer)
    RecyclerView rvDesigner;
    @BindView(R.id.iv_designer_logo)
    ImageView ivLogo;//设计师logo
    @BindView(R.id.btn_designer_add_attention)
    Button btnAddAttention;//关注按钮
    @BindView(R.id.tv_description)
    TextView tvDescri;
    @BindView(R.id.tv_designer_fans)
    TextView tvFans;//粉丝数
    @BindView(R.id.tv_designer_loves)
    TextView tvLoves;//点赞数
    @BindView(R.id.tv_designer_works)
    TextView tvWorks;//作品数
    @BindView(R.id.tv_page_name)
    TextView tvPageName;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private String desCri = "";//设计师描述
    private String headUrl = "";//设计师头像
    private boolean isAttention;//是否关注
    private String designerId;//设计师id
    int imageHeight = 210;

    private List<CustomGuanBean.ResultBean> listData = new ArrayList<>();//设计师作品集合
    private MultiItemTypeAdapter multiItemTypeAdapter;
    private List<String> lookImage = new ArrayList<>();
    private int page = 1;
    private boolean hasMore = true;
    private int limit = Constant.limit;
    private int operationIndex;//上个页面穿过来的下标

    @Override
    protected DesignerPresenter createPresenter() {
        return new DesignerPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_designer2;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("");
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        ImmersionBar.with(this).titleBar(toolbar).transparentStatusBar().init();
        setOnRightIconClickListener(v -> {
            mPresenter.getShareContent(MyApplication.userId, designerId, ShareType.DESIGNER_HOME);
        });
    }


    @Override
    protected void initData() {
        Intent intent = getIntent();
        operationIndex=intent.getIntExtra(OPERATION_ID,0);
        designerId = intent.getStringExtra(DESIGNER_ID);
        mPresenter.getDesignerUserInfoById(designerId, MyApplication.userId);
        rvDesigner.setLayoutManager(new LinearLayoutManager(this));
//        rvDesigner.setNestedScrollingEnabled(false);
        mPresenter.getDesignerWorkList(designerId, MyApplication.userId, page, limit);
        initWorkData();
        multiItemTypeAdapter = new MultiItemTypeAdapter(mContext, listData);
        multiItemTypeAdapter.addItemViewDelegate(new ManyImgItemDelegate());
        multiItemTypeAdapter.addItemViewDelegate(new SingleImgItemDelegate());
        rvDesigner.setAdapter(multiItemTypeAdapter);

        scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, i, y, i2, i3) -> {
            if (y <= 0) {
                //设置透明
                toolbar.setBackgroundColor(Color.TRANSPARENT);
            } else if (y > 0 && y <= imageHeight) {
                toolbar.setBackgroundColor(Color.TRANSPARENT);
            } else {
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorMe));
            }
        });
    }

    private void initWorkData() {
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (hasMore) {
                    page++;
                    mPresenter.getDesignerWorkList(designerId, MyApplication.userId, page, limit);
                }else{
                    mRefreshLayout.finishLoadMore();
                }
            }
        });
    }

    //关注 或取消关注
    @Keep
    @LoginFilter
    @OnClick(R.id.btn_designer_add_attention)
    public void onClick() {
        AttentionRequestBody requestBody = new AttentionRequestBody();
        if (isAttention) {
            requestBody.setUserId(MyApplication.userId);
            requestBody.setDesignerId(designerId);
            requestBody.setOperationType(Constant.CANCEL);
            mPresenter.cancelAttentionDesigner(requestBody);
        } else {
            requestBody.setUserId(MyApplication.userId);
            requestBody.setDesignerId(designerId);
            requestBody.setOperationType(Constant.FOLLOW);
            mPresenter.addAttentionDesigner(requestBody);
        }
    }

    /**
     * 单图的adapter
     */
    public class SingleImgItemDelegate implements ItemViewDelegate<CustomGuanBean.ResultBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.designer_item_single;
        }

        @Override
        public boolean isForViewType(CustomGuanBean.ResultBean item, int position) {
            return item.getViewType() == 1;
        }

        @Override
        public void convert(ViewHolder holder, CustomGuanBean.ResultBean customBean, int position) {
            holder.setText(R.id.tv_designer_time, customBean.getShowTime());
            holder.setText(R.id.tv_designer_content, customBean.getDescription());
            holder.setText(R.id.tv_work_name, customBean.getWorkName());
            holder.setText(R.id.tv_designer_msg, customBean.getCommentCnt() + "");
            holder.setText(R.id.tv_designer_love, customBean.getFabulousCnt() + "");
            TextView tvLove = holder.getView(R.id.tv_designer_love);
            if (customBean.getIsPraise() == 1) {
                tvLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.custom_love_checked),
                        null, null, null);
            } else {
                tvLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.custom_love),
                        null, null, null);
            }
            ImageView ivLarge = holder.getView(R.id.iv_designer_single_child);
            GlideUtil.showRadius(mContext, customBean.getImageList().get(0).getImageUrl(), 1, ivLarge);

            //一张图 点击事件
            ivLarge.setOnClickListener(v -> {
                lookImage.clear();
                List<CustomGuanBean.ResultBean.ImageListBean> imageListBeans = customBean.getImageList();
                for (CustomGuanBean.ResultBean.ImageListBean imageListBean : imageListBeans) {
                    lookImage.add(imageListBean.getImageUrl());
                }
                //跳转作品详情
                GlideUtil.lookBigImage(mContext, lookImage, 0);
            });
        }
    }

    /**
     * 多图的adapter
     */
    public class ManyImgItemDelegate implements ItemViewDelegate<CustomGuanBean.ResultBean> {
        @Override
        public int getItemViewLayoutId() {
            return R.layout.designer_item_many;
        }

        @Override
        public boolean isForViewType(CustomGuanBean.ResultBean item, int position) {
            return item.getViewType() == 0;
        }

        @Override
        public void convert(ViewHolder holder, CustomGuanBean.ResultBean customBean, int position) {
            holder.setText(R.id.tv_designer_time, customBean.getShowTime());
            holder.setText(R.id.tv_designer_content, customBean.getDescription());
            holder.setText(R.id.tv_work_name, customBean.getWorkName());
            holder.setText(R.id.tv_designer_msg, customBean.getCommentCnt() + "");
            holder.setText(R.id.tv_designer_love, customBean.getFabulousCnt() + "");
            TextView tvLove = holder.getView(R.id.tv_designer_love);
            if (customBean.getIsPraise() == 1) {
                tvLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources()
                        .getDrawable(R.drawable.custom_love_checked), null, null, null);
            } else {
                tvLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources()
                        .getDrawable(R.drawable.custom_love), null, null, null);
            }

            RecyclerView rvChild = holder.getView(R.id.rv_designer_child);
            rvChild.setLayoutManager(new GridLayoutManager(mContext, 2));
            ManyChildAdapter manyChildAdapter = new ManyChildAdapter(mContext, R.layout.custom_child_item, customBean.getImageList());
            rvChild.setAdapter(manyChildAdapter);

            //子recycleview 点击事件
            manyChildAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int index) {
                    lookImage.clear();
                    //查看大图
                    List<CustomGuanBean.ResultBean.ImageListBean> imageListBeans = customBean.getImageList();
                    for (CustomGuanBean.ResultBean.ImageListBean imageListBean : imageListBeans) {
                        lookImage.add(imageListBean.getImageUrl());
                    }
                    GlideUtil.lookBigImage(mContext, lookImage, index);

                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
        }
    }

    /**
     * 多图的图片adapter
     */
    public class ManyChildAdapter extends CommonAdapter<CustomGuanBean.ResultBean.ImageListBean> {

        private int size = 0;

        public ManyChildAdapter(Context context, int layoutId, List<CustomGuanBean.ResultBean.ImageListBean> datas) {
            super(context, layoutId, datas);
            size = datas.size();
        }

        @Override
        protected void convert(ViewHolder holder, CustomGuanBean.ResultBean.ImageListBean entity, int position) {
            ImageView ivChild = holder.getView(R.id.iv_custom_many_child);
            GlideUtil.showRadius(mContext, entity.getImageUrl(), 1, ivChild);
            TextView tvCount = holder.getView(R.id.tv_custom_many_num);
            tvCount.setText("" + size);
            if (position == 3) {
                tvCount.setVisibility(View.VISIBLE);
            } else {
                tvCount.setVisibility(View.GONE);
            }
        }
    }

    //获取定制馆数据成功
    @Override
    public void onGetCustomSuc(BaseModel<CustomGuanBean> baseModel) {

        if (baseModel != null && baseModel.getData().getResult().size() > 0) {
            listData.addAll(baseModel.getData().getResult());
        }

        if (baseModel.getData().getResult().size() < limit) {//当返回的数据小于请求的条数时，说明没有更多数据了
            hasMore = false;
        }else{
            hasMore=true;
        }

        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).getImageList().size() < 4) {
                listData.get(i).setViewType(1);
            } else {
                listData.get(i).setViewType(0);
            }
        }
        multiItemTypeAdapter.notifyDataSetChanged();
    }


    //成功获取设计师主页个人信息
    @Override
    public void onGetDesignerInfoSuc(BaseModel<DesignerInfoBean> baseModel) {
        headUrl = baseModel.getData().getHeadUrl();
        desCri = baseModel.getData().getDescription();
        tvPageName.setText(baseModel.getData().getPageName());
        tvFans.setText(String.valueOf(baseModel.getData().getFanCnt()));
        tvLoves.setText(String.valueOf(baseModel.getData().getFabulousCnt()));
        tvWorks.setText(String.valueOf(baseModel.getData().getWorkCnt()));
        if (baseModel.getData().getIsFollow() == 1) {
            isAttention = true;
        } else {
            isAttention = false;
        }

        GlideUtil.show(mContext, headUrl, ivLogo);

        tvDescri.setText(desCri);
        if (isAttention) {
//            btnAddAttention.setEnabled(false);
            btnAddAttention.setBackgroundResource(R.drawable.bg_attention_designer);
            btnAddAttention.setText("取消关注");
        } else {
            btnAddAttention.setBackgroundResource(R.drawable.bg_btn_designer_attention);
            btnAddAttention.setText("关注");
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext, model.getMsg());
        Log.e(TAG, "onErrorCode: " + model.getMsg() + "code=" + model.getCode());
    }

    //关注 成功回调
    @Override
    public void onAttentionDesignerSuc(BaseModel baseModel) {
        Log.e(TAG, "onAttentionDesignerSuc: ");
        isAttention = true;
        btnAddAttention.setText("取消关注");
        btnAddAttention.setBackgroundResource(R.drawable.bg_attention_designer);
        EventBus.getDefault().post(new DesignerEventBean(operationIndex,1));
    }

    //取消 成功回调
    @Override
    public void onCancelDegisnerSuc(BaseModel baseModel) {
        Log.e(TAG, "onCancelDegisnerSuc: ");
        isAttention = false;
        btnAddAttention.setText("关注");
        btnAddAttention.setBackgroundResource(R.drawable.bg_btn_designer_attention);
        EventBus.getDefault().post(new DesignerEventBean(operationIndex,0));
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        //显示分享对话框
        ShareBean shareBean = model.getData();
        if(shareBean != null){
            DialogShare dialogShare = new DialogShare(mContext, shareBean);
            MyApplication.shareType = ShareType.DESIGNER_HOME;
            dialogShare.show();
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event){
        if(event.shareType == ShareType.DESIGNER_HOME){
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.DESIGNER_HOME);
        }
    }

}
