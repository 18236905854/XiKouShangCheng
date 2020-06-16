package com.xk.mall.view.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.donkingliang.labels.LabelsView;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CustomizedFilterBean;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.utils.SpacesItemDecoration;
import com.xk.mall.view.widget.ClearEditText;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ClassName CustomizedFilterActivity
 * Description
 * Author Kay
 * Date 2019/7/2/002
 * Version V1.0
 */
public class CustomizedFilterActivity extends BaseActivity {
    @BindView(R.id.et_search)
    ClearEditText etSearch;//输入框
    @BindView(R.id.tv_finish)
    TextView tvFinish;//取消按钮
    @BindView(R.id.labels_time)
    LabelsView labelsTime;//发布时间
    @BindView(R.id.rv_customized_filter)
    RecyclerView rvCustomizedFilter;//其他筛选
    @BindView(R.id.tv_order_filter_reset)
    TextView tvOrderFilterReset;//重置
    @BindView(R.id.tv_order_filter_success)
    TextView tvOrderFilterSuccess;//完成

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customized_filter;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {

        List<String> hotList = new ArrayList<>();
        hotList.add("不限");
        hotList.add("最近一个月");
        hotList.add("最近三个月");
        hotList.add("最近一年");
        labelsTime.setLabels(hotList);
        labelsTime.setSelects(0);
        labelsTime.setOnLabelSelectChangeListener((label, data, isSelect, position) -> {

        });

        rvCustomizedFilter.setLayoutManager(new GridLayoutManager(mContext, 2));
        List<CustomizedFilterBean> otherList = new ArrayList<>();
        otherList.add(new CustomizedFilterBean("默认(发布时间由近至远)", true));
        otherList.add(new CustomizedFilterBean("按评论量从高到低", false));
        otherList.add(new CustomizedFilterBean("按发布时间从远至近", false));
        otherList.add(new CustomizedFilterBean("按点赞量从高到低", false));
        otherList.add(new CustomizedFilterBean("只看有评论的作品", false));
        otherList.add(new CustomizedFilterBean("只看有点赞的作品", false));
        otherList.add(new CustomizedFilterBean("只看拼团过的作品", false));
        FilterAdapter filterAdapter = new FilterAdapter(mContext, R.layout.item_customized_filter, otherList);
        rvCustomizedFilter.addItemDecoration(new GridSpacingItemDecoration(2, ConvertUtils.dp2px(10), false));
        rvCustomizedFilter.setAdapter(filterAdapter);
        rvCustomizedFilter.setHasFixedSize(false);
    }

    @OnClick({R.id.tv_finish, R.id.tv_order_filter_reset, R.id.tv_order_filter_success})
    public void onClickView(View view){
        switch (view.getId()){
            case R.id.tv_finish:
                if (Build.VERSION.SDK_INT >= 21) {
                    MyApplication.getInstance().closeActivity();
                    finishAfterTransition();
                } else {
                    MyApplication.getInstance().closeActivity();
                    finish();
                }
                break;

            case R.id.tv_order_filter_reset://重置
                labelsTime.setSelects(1);

                break;

            case R.id.tv_order_filter_success://完成
                break;
        }
    }

    /**
     * 其他过滤的adapter
     */
    class FilterAdapter extends CommonAdapter<CustomizedFilterBean>{

        public FilterAdapter(Context context, int layoutId, List<CustomizedFilterBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, CustomizedFilterBean bean, int position) {
            TextView tvContent = holder.getView(R.id.tv_content);
            tvContent.setText(bean.key);
            tvContent.setSelected(bean.isSelect);
            tvContent.setOnClickListener(v -> {
                //设置点中，单选
                bean.isSelect = !bean.isSelect;
                Logger.e("点击事件");
            });
        }
    }

}
