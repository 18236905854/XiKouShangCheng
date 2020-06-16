package com.xk.mall.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.FightGroupBean;
import com.xk.mall.model.impl.FightGroupViewImpl;
import com.xk.mall.presenter.GroupPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.EventBusMessage;
import com.xk.mall.view.activity.GroupGoodsDetailActivity;
import com.xk.mall.view.adapter.FightGroupAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName GroupMainFragment
 * Description 拼团页面的fragement
 * Author 卿凯
 * Date 2019/7/4/004
 * Version V1.0
 */
public class GroupMainFragment extends BaseFragment<GroupPresenter> implements FightGroupViewImpl {
    @BindView(R.id.rv_fight_group)
    RecyclerView rvFightGroup;
    @BindView(R.id.refresh_fight_group)
    SmartRefreshLayout mRefreshLayout;
    private String typeId = "";//1 在售 2 预售
    private int page = 1;//页数
    private int limit = 10;//每页的条数
    private List<ActiveSectionGoodsBean> fightGroupBeanList;
    private FightGroupAdapter adapter;

    @Override
    protected GroupPresenter createPresenter() {
        return new GroupPresenter(this);
    }

    /**
     * 构造方法
     */
    public static GroupMainFragment getInstance(String titleType) {
        GroupMainFragment fragment = new GroupMainFragment();
        fragment.typeId = titleType;
        return fragment;
    }



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fight_group;
    }

    @Override
    protected void initData() {

    }



    @Override
    protected void loadLazyData() {
        mPresenter.getFightGroupData(typeId, ActivityType.ACTIVITY_FIGHT_GROUP, MyApplication.userId,page, limit);
        rvFightGroup.setLayoutManager(new LinearLayoutManager(mContext));
        fightGroupBeanList = new ArrayList<>();

        adapter = new FightGroupAdapter(mContext, R.layout.fight_group_item, fightGroupBeanList);
        rvFightGroup.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //点击进入定制拼团商品详情
                Intent intent = new Intent(mContext, GroupGoodsDetailActivity.class);
                intent.putExtra(GroupGoodsDetailActivity.ACTIVITY_GOODS_ID, fightGroupBeanList.get(position).getActivityGoodsId());
                ActivityUtils.startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getFightGroupData(typeId,ActivityType.ACTIVITY_FIGHT_GROUP,MyApplication.userId,page, limit);
        });
    }


    @Override
    public void onGetFightGroupDataSuccess(BaseModel<ActiveSectionGoodsPageBean> model) {
        if(page == 1){
            fightGroupBeanList.clear();
            if(model.getData().getResult() != null && model.getData().getResult().size() != 0){
                String activityId = model.getData().getResult().get(0).getActivityId();
                EventBus.getDefault().post(new EventBusMessage(activityId));
            }
            fightGroupBeanList.addAll(model.getData().getResult());
            adapter.notifyDataSetChanged();
        }else {
            int size = fightGroupBeanList.size();
            fightGroupBeanList.addAll(model.getData().getResult());
            adapter.notifyItemRangeChanged(size, fightGroupBeanList.size());
        }
        if(model.getData().getResult().size() < limit){
            mRefreshLayout.setEnableLoadMore(false);
        }else {
            mRefreshLayout.setEnableLoadMore(true);
        }
    }
}
