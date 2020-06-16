package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.CommentsBean;
import com.xk.mall.model.entity.CustomGuanBean;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.eventbean.DesignerEventBean;
import com.xk.mall.model.impl.CustomViewImpl;
import com.xk.mall.model.request.AttentionRequestBody;
import com.xk.mall.model.request.CommentRequestBody;
import com.xk.mall.presenter.CustomPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.view.adapter.WorksDetailCommentAdapter;
import com.xk.mall.view.widget.CommonPopupWindow;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ClassName CustomizedActivity
 * Description 定制馆页面
 * Author 卿凯
 * Date 2019/6/20/020
 * Version V1.0
 */
public class CustomizedActivity extends BaseActivity<CustomPresenter> implements CustomViewImpl, CommonPopupWindow.ViewInterface {
    @BindView(R.id.rv_custom)
    RecyclerView rvCustom;
    @BindView(R.id.state_view_msg)
    MultiStateView stateViewMsg;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout workRefreshLayout;
    private List<CustomGuanBean.ResultBean> listData = new ArrayList<>();;
    private MultiItemTypeAdapter multiItemTypeAdapter;
    private int clickIndex = 0;//点击的下标
    private String attentionDesignerId = "";//关注的设计师ID
    private List<String> lookImage = new ArrayList<>();
    private CommonPopupWindow popupWindow;
    private List<CommentsBean.ResultBean> commentList = new ArrayList<>();
    private int page = 1;
    private int limit = Constant.limit;
    private String workId;//作品id
    private String designerId;//设计师id
    private int commentCount = 0;//评论数量
    private boolean hasMore = true;
    private TextView tvEmptyText;
    //popup
    private SmartRefreshLayout popRefreshLayout;
    private WorksDetailCommentAdapter commentAdapter;
    private TextView tvMsgTitle;
    private EditText etMsg;
    private RecyclerView rvShowMsg;
    private TextView tvPopText;

    //作品刷新的
    private int pagew = 1;
    private boolean hasMorew = true;


    @Override
    protected CustomPresenter createPresenter() {
        return new CustomPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customized;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("定制馆");
        flRight.setVisibility(View.GONE);
        setOnRightIconClickListener(v -> {
            //跳转筛选页面
            ActivityUtils.startActivity(CustomizedFilterActivity.class);
        });
    }
    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }
    /**
     * eventBus接收回调--设计师主页 关注 or 取消关注
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAttent(DesignerEventBean eventBean) {
       if(eventBean!=null){
           if(eventBean.getType()==1){//关注了
               int index=eventBean.getPosition();
               listData.get(index).setIsFollow(1);
               String designerId=listData.get(index).getDesignerId();
               //同一个设计师处理
               for (CustomGuanBean.ResultBean customGuanBean : listData) {
                   if (!TextUtils.isEmpty(designerId) && customGuanBean.getDesignerId().equals(designerId)) {
                       customGuanBean.setIsFollow(1);
                   }
               }

           }else{
               int index=eventBean.getPosition();
               listData.get(index).setIsFollow(0);
               String designerId=listData.get(index).getDesignerId();
               //同一个设计师处理
               for (CustomGuanBean.ResultBean customGuanBean : listData) {
                   if (!TextUtils.isEmpty(designerId) && customGuanBean.getDesignerId().equals(designerId)) {
                       customGuanBean.setIsFollow(0);
                   }
               }
           }
           multiItemTypeAdapter.notifyDataSetChanged();
       }
    }



    @Override
    protected void initData() {
        View view = stateViewMsg.getView(MultiStateView.VIEW_STATE_EMPTY);
        tvEmptyText = view.findViewById(R.id.tv_message);
        mPresenter.getCustomerGuanData(MyApplication.userId, pagew, limit);
        rvCustom.setLayoutManager(new LinearLayoutManager(this));

        initWorks();

        multiItemTypeAdapter = new MultiItemTypeAdapter(mContext, listData);
        multiItemTypeAdapter.addItemViewDelegate(new ManyImgItemDelegate());
        multiItemTypeAdapter.addItemViewDelegate(new SingleImgItemDelegate());
        multiItemTypeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //跳转作品详情
                CustomGuanBean.ResultBean customBean = listData.get(position);
                Intent intent = new Intent(mContext, WorksDetailActivity.class);
                intent.putExtra(Constant.INTENT_DESIGNER_NAME, customBean.getPageName());//设计师名称
                intent.putExtra(Constant.INTENT_DESIGNER_HEAD, customBean.getHeadUrl());//设计师头像
                intent.putExtra(Constant.INTENT_DESIGNER_WORKS_NAME, customBean.getWorkName());//设计师作品名称
                intent.putExtra(Constant.INTENT_DESIGNER_ATTENTION, customBean.getIsFollow());//是否关注
                intent.putExtra(Constant.WORK_TIME, customBean.getShowTime());//发布时间
                intent.putExtra(Constant.WORK_DESCRIPTION, customBean.getDescription());//作品描述
                intent.putExtra(Constant.WORK_NAME, customBean.getWorkName());//作品名称
                intent.putExtra(Constant.WORK_COMMENT_NUM, listData.get(position).getCommentCnt());//评论数量
                intent.putExtra(Constant.WORK_PRAISE_NUM, listData.get(position).getFabulousCnt());//点赞数量
                intent.putExtra(Constant.WORK_IS_PRAISE, listData.get(position).getIsPraise());//是否点赞
                intent.putExtra(Constant.WORK_ID, customBean.getId());//作品id
                intent.putExtra(Constant.WORK_DESIGNER_ID, customBean.getDesignerId());//设计师id
                intent.putExtra(Constant.INTENT_DESIGNER_IMGS, (Serializable) customBean.getImageList());//所有作品图片
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvCustom.setAdapter(multiItemTypeAdapter);
    }

    private void initWorks() {
        workRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                listData.clear();
                 pagew=1;
                 mPresenter.getCustomerGuanData(MyApplication.userId,pagew,limit);
            }
        });

        workRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (hasMorew) {
                    pagew++;
                    mPresenter.getCustomerGuanData(MyApplication.userId,pagew,limit);
                } else {
                    workRefreshLayout.finishLoadMore();
                }
            }
        });

    }

    //获取定制馆数据成功
    @Override
    public void onGetCustomSuc(BaseModel<CustomGuanBean> baseModel){
        if (baseModel != null && baseModel.getData().getResult().size() > 0){
            listData.addAll(baseModel.getData().getResult());
        } else {
            if(pagew==1){
                tvEmptyText.setText("暂无相关数据");
                stateViewMsg.setViewState(MultiStateView.VIEW_STATE_EMPTY);
            }
        }
        if (baseModel.getData().getResult().size() < limit) {//当返回的数据小于请求的条数时，说明没有更多数据了
            hasMorew = false;
        }else{
            hasMorew=true;
        }
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).getImageList().size() < 4) {
                listData.get(i).setViewType(1);
            } else {
                listData.get(i).setViewType(0);
            }
        }
        multiItemTypeAdapter.notifyDataSetChanged();
        if (workRefreshLayout != null) {
            workRefreshLayout.finishLoadMore();
            workRefreshLayout.finishRefresh();
        }
    }

    //点赞成功回调
    @Override
    public void onPraiseorSuc(BaseModel model) {
        listData.get(clickIndex).setIsPraise(1);
        listData.get(clickIndex).setFabulousCnt(listData.get(clickIndex).getFabulousCnt() + 1);
        multiItemTypeAdapter.notifyDataSetChanged();
    }

    //取消点赞成功
    @Override
    public void onCancelPraiseSuc(BaseModel model) {
        listData.get(clickIndex).setIsPraise(0);
        listData.get(clickIndex).setFabulousCnt(listData.get(clickIndex).getFabulousCnt() - 1);
        multiItemTypeAdapter.notifyDataSetChanged();
    }

    //添加关注成功
    @Override
    public void onAttentionSuc(BaseModel model) {
        ToastUtils.showShortToast(mContext, "关注成功");
        listData.get(clickIndex).setIsFollow(1);
        //同一个设计师处理
        for (CustomGuanBean.ResultBean customGuanBean : listData) {
            if (!TextUtils.isEmpty(attentionDesignerId) && customGuanBean.getDesignerId().equals(attentionDesignerId)) {
                customGuanBean.setIsFollow(1);
            }
        }
        attentionDesignerId = "";
        multiItemTypeAdapter.notifyDataSetChanged();
    }

    //获取评论列表成功
    @Override
    public void onGetCommentList(BaseModel<CommentsBean> entity) {
//        Log.e(TAG, "onGetCommentList: 获取评论列表" );
        if (page == 1) {
            commentList.clear();
            commentList.addAll(entity.getData().getResult());
        } else {
            commentList.addAll(entity.getData().getResult());
        }

        if (entity.getData().getResult().size() < limit) {//当返回的数据小于请求的条数时，说明没有更多数据了
            hasMore = false;
        }else{
            hasMore=true;
        }

        if (commentAdapter != null) {
            commentAdapter.notifyDataSetChanged();
        }
        if (popRefreshLayout != null) {
            popRefreshLayout.finishLoadMore();
            popRefreshLayout.finishRefresh();
        }
        showPopUp();
    }

    //发布评论成功
    @Override
    public void publishCommentSuc(CommentRequestBody resultBean, int count) {
        Log.e(TAG, "publishCommentSuc:发布评论成功 ");
        etMsg.setText("");

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etMsg.getWindowToken(), 0);

        CommentsBean.ResultBean addEntity = new CommentsBean.ResultBean();

        addEntity.setCommentContent(resultBean.getCommentContent());
        addEntity.setUserId(MyApplication.userId);
        addEntity.setUserName(SPUtils.getInstance().getString(Constant.NICK_NAME));
        addEntity.setHeadUrl(SPUtils.getInstance().getString(Constant.HEAD_URL));
        addEntity.setCommentTime(TimeUtils.getNowString());
        count++;
        tvMsgTitle.setText(count + "条评价");
        listData.get(clickIndex).setCommentCnt(listData.get(clickIndex).getCommentCnt() + 1);//评论数加1
        multiItemTypeAdapter.notifyDataSetChanged();
        tvPopText.setVisibility(View.GONE);
        commentList.add(0, addEntity);
        commentAdapter.notifyDataSetChanged();
        rvShowMsg.scrollToPosition(0);
    }

    /**
     * 多图的adapter
     */
    public class ManyImgItemDelegate implements ItemViewDelegate<CustomGuanBean.ResultBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.custom_item_many;
        }

        @Override
        public boolean isForViewType(CustomGuanBean.ResultBean item, int position) {
            return item.getViewType() == 0;
        }

        @Override
        public void convert(ViewHolder holder, CustomGuanBean.ResultBean customBean, int bigPosition) {
            holder.setText(R.id.tv_custom_name, customBean.getPageName());
            holder.setText(R.id.tv_custom_time, customBean.getShowTime());

            holder.setText(R.id.tv_custom_content, customBean.getDescription());
            holder.setText(R.id.tv_custom_msg, customBean.getCommentCnt() + "");
            holder.setText(R.id.tv_custom_love, customBean.getFabulousCnt() + "");
            holder.setText(R.id.tv_custom_works_name, customBean.getWorkName());

            TextView tvLove = holder.getView(R.id.tv_custom_love);
            TextView tvMsg = holder.getView(R.id.tv_custom_msg);
            Button btnAttention = holder.getView(R.id.btn_custom_add_attention);

            if (customBean.getIsFollow() == 1) {
                btnAttention.setEnabled(false);
                btnAttention.setText("已关注");
            } else {
                btnAttention.setEnabled(true);
            }

            if (customBean.getIsPraise() == 1) {//点赞
                tvLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources()
                        .getDrawable(R.drawable.custom_love_checked), null, null, null);
            } else {
                tvLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getResources()
                        .getDrawable(R.drawable.custom_love), null, null, null);
            }
            //关注操作
            btnAttention.setOnClickListener(new View.OnClickListener() {
                @Keep
                @LoginFilter
                @Override
                public void onClick(View v) {
                    clickIndex = bigPosition;
                    attentionDesignerId = listData.get(clickIndex).getDesignerId();
                    AttentionRequestBody requestBody = new AttentionRequestBody();
                    requestBody.setUserId(MyApplication.userId);
                    requestBody.setDesignerId(listData.get(bigPosition).getDesignerId());
                    requestBody.setOperationType(Constant.FOLLOW);
                    mPresenter.addAttentionDesigner(requestBody);
                }
            });

            //点赞操作
            tvLove.setOnClickListener(new View.OnClickListener() {
                @Keep
                @LoginFilter
                @Override
                public void onClick(View v) {
                    clickIndex = bigPosition;
                    AttentionRequestBody requestBody = new AttentionRequestBody();
                    requestBody.setDesignerWorkId(listData.get(bigPosition).getId());
                    requestBody.setUserId(MyApplication.userId);
                    if (customBean.getIsPraise() == 1) {
                        requestBody.setOperationType(Constant.CANCEL);
                        mPresenter.cancelPraiseorSuc(requestBody);
                    } else {
                        requestBody.setOperationType(Constant.PRAISE);
                        mPresenter.addOnPraiseorSuc(requestBody);
                    }
                }
            });


            ImageView ivHead = holder.getView(R.id.iv_custom_head);
            GlideUtil.showCircleLoading(mContext, customBean.getHeadUrl(), ivHead);

            RecyclerView rvChild = holder.getView(R.id.rv_custom_child);
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

            ivHead.setOnClickListener(v -> {
                //跳转设计师主页
                Intent intent = new Intent(mContext, DesignerActivity.class);
                intent.putExtra(DesignerActivity.DESIGNER_ID, listData.get(bigPosition).getDesignerId());
                intent.putExtra(DesignerActivity.OPERATION_ID,bigPosition);
                ActivityUtils.startActivity(intent);
            });

            //评论
            tvMsg.setOnClickListener(v -> {
                workId = listData.get(bigPosition).getId();
                designerId = listData.get(bigPosition).getDesignerId();
                //点击显示评论列表
//                mPresenter.getCommentList("5d187fa74405f600012d3767","5d185744b4e57100012a59b1",page,limit);
                mPresenter.getCommentList(workId, designerId, page, limit);
            });
        }
    }

    /**
     * 单图的adapter
     */
    public class SingleImgItemDelegate implements ItemViewDelegate<CustomGuanBean.ResultBean> {
        @Override
        public int getItemViewLayoutId() {
            return R.layout.custom_item_single;
        }

        @Override
        public boolean isForViewType(CustomGuanBean.ResultBean item, int position) {
            return item.getViewType() == 1;
        }

        @Override
        public void convert(ViewHolder holder, CustomGuanBean.ResultBean customBean, int position) {
            holder.setText(R.id.tv_custom_name, customBean.getPageName());
            holder.setText(R.id.tv_custom_time, customBean.getShowTime());
            holder.setText(R.id.tv_custom_content, customBean.getDescription());
            holder.setText(R.id.tv_custom_msg, customBean.getCommentCnt() + "");
            holder.setText(R.id.tv_custom_love, customBean.getFabulousCnt() + "");
            holder.setText(R.id.tv_custom_works_name, customBean.getWorkName());
            TextView tvLove = holder.getView(R.id.tv_custom_love);
            TextView tvMsg = holder.getView(R.id.tv_custom_msg);
            Button btnAttention = holder.getView(R.id.btn_custom_add_attention);

            if (customBean.getIsPraise() == 1) {
                tvLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.custom_love_checked),
                        null, null, null);
            } else {
                tvLove.setCompoundDrawablesWithIntrinsicBounds(mContext.getDrawable(R.drawable.custom_love),
                        null, null, null);
            }
            if (customBean.getIsFollow() == 1) {
                btnAttention.setEnabled(false);
                btnAttention.setText("已关注");
            } else {
                btnAttention.setEnabled(true);
                btnAttention.setText("+ 关注");
            }

            btnAttention.setOnClickListener(new View.OnClickListener() {
                @Keep
                @LoginFilter
                @Override
                public void onClick(View v) {
                    clickIndex = position;
                    attentionDesignerId = listData.get(position).getDesignerId();
                    AttentionRequestBody requestBody = new AttentionRequestBody();
                    requestBody.setUserId(MyApplication.userId);
                    requestBody.setDesignerId(listData.get(position).getDesignerId());
                    requestBody.setOperationType(Constant.FOLLOW);
                    mPresenter.addAttentionDesigner(requestBody);
                }
            });

            //点赞操作
            tvLove.setOnClickListener(new View.OnClickListener() {
                @Keep
                @LoginFilter
                @Override
                public void onClick(View v) {
//                    ToastUtils.showShortToast(mContext,"点赞"+position);
                    clickIndex = position;
                    AttentionRequestBody requestBody = new AttentionRequestBody();
                    requestBody.setDesignerWorkId(listData.get(position).getId());
                    requestBody.setUserId(MyApplication.userId);
                    if (customBean.getIsPraise() == 1) {
                        requestBody.setOperationType(Constant.CANCEL);
                        mPresenter.cancelPraiseorSuc(requestBody);
                    } else {
                        requestBody.setOperationType(Constant.PRAISE);
                        mPresenter.addOnPraiseorSuc(requestBody);
                    }
                }
            });

            ImageView ivLarge = holder.getView(R.id.iv_custom_single_child);
            ImageView ivHead = holder.getView(R.id.iv_custom_head);
            GlideUtil.showCircleLoading(mContext, customBean.getHeadUrl(), ivHead);
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


            ivHead.setOnClickListener(v -> {
                //跳转设计师主页
                Intent intent = new Intent(mContext, DesignerActivity.class);
                intent.putExtra(DesignerActivity.DESIGNER_ID, listData.get(position).getDesignerId());
                intent.putExtra(DesignerActivity.OPERATION_ID,position);
                ActivityUtils.startActivity(intent);
            });

            tvMsg.setOnClickListener(v -> {
                //点击显示评论列表
                workId = listData.get(position).getId();
                designerId = listData.get(position).getDesignerId();
                //点击显示评论列表
//                mPresenter.getCommentList("5d187fa74405f600012d3767","5d185744b4e57100012a59b1",page,limit);
                mPresenter.getCommentList(workId, designerId, page, limit);
            });

        }
    }

    /**
     * 显示评论列表对话框
     */
    private void showPopUp() {
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(CustomizedActivity.this).inflate(R.layout.layout_show_msg, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.layout_show_msg)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        //防止PopupWindow被软件盘挡住
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);

    }

    @Override
    public void getChildView(View view, int layoutResId) {
        Log.e(TAG, "getChildView: =======");
        popRefreshLayout = view.findViewById(R.id.refreshLayout);
        tvPopText = view.findViewById(R.id.tv_pop_text);
        rvShowMsg = view.findViewById(R.id.rv_show_msg);
        tvMsgTitle = view.findViewById(R.id.tv_show_msg_title);
        ImageView ivClose = view.findViewById(R.id.iv_show_msg_close);
        etMsg = view.findViewById(R.id.et_show_msg);//评论输入框
        TextView tvSend = view.findViewById(R.id.tv_show_msg_send);//发送
        if (commentList.size() > 0) {
            tvMsgTitle.setText(commentList.get(0).getCommentCount() + "条评价");
            commentCount = commentList.get(0).getCommentCount();
            tvPopText.setVisibility(View.GONE);
        } else {
            tvPopText.setVisibility(View.VISIBLE);
            tvMsgTitle.setText("0条评价");
        }

//        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
//                     commentList.clear();
//                     page=1;
//            mPresenter.getCommentList(workId,designerId,page,limit);
//        });

        popRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (hasMore) {
                page = page + 1;
                //点击显示评论列表
//                mPresenter.getCommentList("5d187fa74405f600012d3767","5d185744b4e57100012a59b1",page,limit);
                mPresenter.getCommentList(workId, designerId, page, limit);
            } else {
                popRefreshLayout.finishLoadMore();
            }
        });


        ivClose.setOnClickListener(v -> {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        });

        tvSend.setOnClickListener(v -> {
            publishComment();
        });


        commentAdapter = new WorksDetailCommentAdapter(mContext, R.layout.works_detail_comment_item, commentList);
        rvShowMsg.setLayoutManager(new LinearLayoutManager(this));
        rvShowMsg.setAdapter(commentAdapter);
    }

    /**
     * 发布评论
     */
    @Keep
    @LoginFilter
    private void publishComment() {
        if (!TextUtils.isEmpty(etMsg.getText().toString())) {
            CommentRequestBody requestBody = new CommentRequestBody();
            requestBody.setCommentContent(etMsg.getText().toString());
            requestBody.setDesignerId(designerId);
            requestBody.setWorkId(workId);
            requestBody.setUserId(MyApplication.userId);
            mPresenter.publishComment(requestBody, commentCount);
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
            if (position == 3 && size != 4) {
                tvCount.setVisibility(View.VISIBLE);
            } else {
                tvCount.setVisibility(View.GONE);
            }


        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext, model.getMsg());
    }
}
