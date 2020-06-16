package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.isseiaoki.simplecropview.util.Logger;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.DesignerCirclBean;
import com.xk.mall.model.impl.DesignerCircleViewImpl;
import com.xk.mall.presenter.DesignerCirclePresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideUtil;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName DesignerCircleActivity
 * Description 设计师圈页面
 * Author 卿凯
 * Date 2019/6/21/021
 * Version V1.0
 */
public class DesignerCircleActivity extends BaseActivity<DesignerCirclePresenter> implements DesignerCircleViewImpl {
    @BindView(R.id.rv_designer_circle)
    RecyclerView rvDesignerCircle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private List<DesignerCirclBean.ResultBean> listData;
    private int page = 1;
    private int limit = Constant.limit;
    private boolean hasMore = true;//是否有更多
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    @Override
    protected DesignerCirclePresenter createPresenter() {
        return new DesignerCirclePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_designer_circle;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("设计师圈");
    }

    @Override
    protected void initData() {
        rvDesignerCircle.setLayoutManager(new LinearLayoutManager(this));
        listData = new ArrayList<>();

        MultiItemTypeAdapter  multiItemTypeAdapter = new MultiItemTypeAdapter(mContext, listData);
        multiItemTypeAdapter.addItemViewDelegate(new LeftImgItemDelegate());
        multiItemTypeAdapter.addItemViewDelegate(new RightImgItemDelegate());
        multiItemTypeAdapter.addItemViewDelegate(new SingleImgItemDelegate());
        multiItemTypeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //跳转设计师主页
                DesignerCirclBean.ResultBean circleBean = listData.get(position - 1);
                Intent intent = new Intent(mContext, DesignerActivity.class);
                intent.putExtra(DesignerActivity.DESIGNER_ID,circleBean.getId());
                intent.putExtra(Constant.INTENT_DESIGNER_NAME, circleBean.getPageName());//设计师名称
                intent.putExtra(Constant.INTENT_DESIGNER_HEAD, circleBean.getHeadUrl());//设计师头像
                ActivityUtils.startActivity(intent);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

         headerAndFooterWrapper = new HeaderAndFooterWrapper(multiItemTypeAdapter);
        View headView = LayoutInflater.from(mContext).inflate(R.layout.layout_designer_circle_head, rvDesignerCircle, false);
        headerAndFooterWrapper.addHeaderView(headView);
        rvDesignerCircle.setAdapter(headerAndFooterWrapper);

        getRequestData();

        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
                page=1;
                getRequestData();
        });
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            if (hasMore) {
                page++;
                getRequestData();
            } else {
                mRefreshLayout.finishLoadMore();
//                ToastUtils.showShortToast(DesignerCircleActivity.this, "没有更多数据了");
            }
        });
    }

    /**
     * 请求获取数据
     */
    private void getRequestData() {
        mPresenter.getDesignerCircleData(page,limit);
    }


    //获取设计师圈数据
    @Override
    public void onGetDegisnerCircelSuc(BaseModel<DesignerCirclBean> entity) {
        Logger.e("获取设计圈数据"+entity.getData().getResult());
        if (page == 1) {
            listData.clear();
            if (entity == null || entity.getData().getResult().size() == 0) {
                Logger.e("显示空布局");
            } else {
                listData.addAll(entity.getData().getResult());
            }
        } else {
            listData.addAll(entity.getData().getResult());
        }
        //index+1 求4的余数
        for (int i=0;i<listData.size();i++){
            int index= i+1;
            if(index % 4==1){//布局单张图 0   左边1 右图 2
                listData.get(i).setType(0);
            }else if(index % 4==2){//左边 type=1
                listData.get(i).setType(1);
            }else if(index %4==3 ){//
                listData.get(i).setType(0);
            }else if(index % 4==0){
                listData.get(i).setType(2);
            }
        }

        if (entity.getData().getResult().size() < limit) {//当返回的数据小于请求的条数时，说明没有更多数据了
            hasMore = false;
        }

//        multiItemTypeAdapter.notifyDataSetChanged();
        headerAndFooterWrapper.notifyDataSetChanged();
        mRefreshLayout.finishLoadMore();
        mRefreshLayout.finishRefresh();
    }

    /**
     * 单个大图的adapter
     */
    public class SingleImgItemDelegate implements ItemViewDelegate<DesignerCirclBean.ResultBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.designer_circle_item;
        }

        @Override
        public boolean isForViewType(DesignerCirclBean.ResultBean item, int position) {
            return item.getType() == 0;
        }

        @Override
        public void convert(ViewHolder holder, DesignerCirclBean.ResultBean customBean, int position) {
            ImageView ivHead = holder.getView(R.id.iv_circle_head);
            GlideUtil.show(mContext, customBean.getHeadUrl(), ivHead);
            TextView textView=holder.getView(R.id.tv_circle_designer_name);
            if(TextUtils.isEmpty(customBean.getPageName())){
                textView.setText("");
            }else{
                textView.setText(customBean.getPageName());
            }
            holder.setText(R.id.tv_circle_designer_name, customBean.getPageName());
            holder.setText(R.id.tv_circle_designer_msg, "" + customBean.getCommentCount());
            holder.setText(R.id.tv_circle_designer_love, "" + customBean.getFabulousCount());
        }
    }

    /**
     * 左边图的adapter
     */
    public class LeftImgItemDelegate implements ItemViewDelegate<DesignerCirclBean.ResultBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.designer_circle_left_item;
        }

        @Override
        public boolean isForViewType(DesignerCirclBean.ResultBean item, int position) {
            return item.getType()==1;
        }

        @Override
        public void convert(ViewHolder holder, DesignerCirclBean.ResultBean customBean, int position) {
            ImageView ivHead = holder.getView(R.id.iv_circle_head);
            GlideUtil.show(mContext, customBean.getHeadUrl(), ivHead);
            holder.setText(R.id.tv_circle_designer_name, customBean.getPageName());
            holder.setText(R.id.tv_circle_designer_msg, "" + customBean.getCommentCount());
            holder.setText(R.id.tv_circle_designer_love, "" + customBean.getFabulousCount());

        }
    }

    /**
     * 右边图的adapter
     */
    public class RightImgItemDelegate implements ItemViewDelegate<DesignerCirclBean.ResultBean> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.designer_circle_right_item;
        }

        @Override
        public boolean isForViewType(DesignerCirclBean.ResultBean item, int position) {
            return item.getType() == 2;
        }

        @Override
        public void convert(ViewHolder holder, DesignerCirclBean.ResultBean customBean, int position) {
            ImageView ivHead = holder.getView(R.id.iv_circle_head);
            GlideUtil.show(mContext, customBean.getHeadUrl(), ivHead);
            holder.setText(R.id.tv_circle_designer_name, customBean.getPageName());
            holder.setText(R.id.tv_circle_designer_msg, "" + customBean.getCommentCount());
            holder.setText(R.id.tv_circle_designer_love, "" + customBean.getFabulousCount());

        }
    }

}
