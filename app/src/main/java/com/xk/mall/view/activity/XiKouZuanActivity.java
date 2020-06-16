package com.xk.mall.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.XiKouZuanBean;
import com.xk.mall.view.adapter.XiKouZuanAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName MakeTaskActivity
 * Description 喜扣赚
 * Author
 * Date
 * Version V1.0
 */
public class XiKouZuanActivity extends BaseActivity {
    private static final String TAG = "XiKouZuanActivity";

    @BindView(R.id.recycleview)
    RecyclerView recycleview;


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xi_kou_zuan;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setTextColor(Color.parseColor("#444444"));
        toolbar_title.setText("喜扣赚");
    }

    @Override
    protected void initData() {
        recycleview.setLayoutManager(new LinearLayoutManager(mContext));
        List<XiKouZuanBean> xiKouZuanBeanList=new ArrayList<>();
        xiKouZuanBeanList.add(new XiKouZuanBean(R.mipmap.xkz_wug,"吾G购","买货的券 卖货赚钱"));
        xiKouZuanBeanList.add(new XiKouZuanBean(R.mipmap.xkz_more_buy,"多买多折","最低折扣 折上折技巧"));
        xiKouZuanBeanList.add(new XiKouZuanBean(R.mipmap.xkz_zero_buy,"0元抢拍","巧拍超值物品"));
        xiKouZuanBeanList.add(new XiKouZuanBean(R.mipmap.xgz_all_buy,"全球买手购","折扣最多 券抵现金"));

        XiKouZuanAdapter makeTaskAdapter=new XiKouZuanAdapter(mContext, xiKouZuanBeanList, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                switch (position){
                    case 0:
                        ActivityUtils.startActivity(WuGMainActivity.class);
                        break;
                    case 1:
                        ActivityUtils.startActivity(ManyBuyActivity.class);
                        break;
                    case 2:
                        ActivityUtils.startActivity(ZeroMainActivity.class);
                        break;
                    case 3:
                        ActivityUtils.startActivity(GlobalBuyerActivity.class);
                        break;
                }

            }
        });


        recycleview.setAdapter(makeTaskAdapter);
    }

}
