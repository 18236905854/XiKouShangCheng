package com.xk.mall.view.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.ZeroCurrentPriceBean;
import com.xk.mall.model.entity.ZeroGoodsBean;
import com.xk.mall.model.impl.ZeroChildImpl;
import com.xk.mall.presenter.ZeroChildPresenter;
import com.xk.mall.utils.EventBusMessage;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.view.activity.ZeroGoodsDetailActivity;
import com.xk.mall.view.adapter.ZeroHotGridAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName ZeroFragment
 * Description 0元拍子页面
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class ZeroFragment extends BaseFragment<ZeroChildPresenter> implements ZeroChildImpl {
    private static final String TAG = "ZeroFragment";

    @BindView(R.id.rv_zero)
    RecyclerView rvZeroHot;

    private String roundId = "";//轮次ID
    private int state;//状态  1:未开始，2:已开始，3:已结束，4:已取消, 5:已流拍
    private List<ZeroGoodsBean> zeroGoodsBeans;
    private List<ZeroCurrentPriceBean> zeroCurrentPriceBeanList=new ArrayList<>();
    private ZeroHotGridAdapter hotGridAdapter;
    public static ZeroFragment getInstance(String roundId,int state){
        ZeroFragment zeroFragment = new ZeroFragment();
        zeroFragment.roundId = roundId;
        zeroFragment.state=state;
        return zeroFragment;
    }

    @Override
    protected ZeroChildPresenter createPresenter() {
        return new ZeroChildPresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_zero;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void prepareFetchData(boolean forceUpdate) {
        super.prepareFetchData(true);//强制刷新fragment
    }

    @Override
    protected void loadLazyData() {
        Log.e(TAG, "loadLazyData: " );
        mPresenter.getGoodsByRoundId(roundId);

        rvZeroHot.setLayoutManager(new GridLayoutManager(mContext, 2));
        if(rvZeroHot.getItemDecorationCount()==0){
            rvZeroHot.addItemDecoration(new GridSpacingItemDecoration(2,30,false));
        }
        zeroGoodsBeans = new ArrayList<>();
        hotGridAdapter = new ZeroHotGridAdapter(mContext, zeroGoodsBeans);
        rvZeroHot.setAdapter(hotGridAdapter);
        hotGridAdapter.setRvListener(new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                //点击进入详情
                Intent intent = new Intent(mContext, ZeroGoodsDetailActivity.class);
                intent.putExtra(ZeroGoodsDetailActivity.ACTIVITY_GOODS_ID,zeroGoodsBeans.get(position).getId());
//                intent.putExtra(ZeroGoodsDetailActivity.GOODS_NAME,zeroGoodsBeans.get(position).getCommodityName());
//                intent.putExtra(ZeroGoodsDetailActivity.GOODS_PRICE,zeroGoodsBeans.get(position).getSalePrice());
//                intent.putExtra(ZeroGoodsDetailActivity.GOODS_LINE_PRICE,zeroGoodsBeans.get(position).getMarketPrice());
//                intent.putExtra(ZeroGoodsDetailActivity.GOODS_ID, zeroGoodsBeans.get(position).getGoodsId());
//                intent.putExtra(ZeroGoodsDetailActivity.ZERO_ID, zeroGoodsBeans.get(position).getId());//活动商品id
//                intent.putExtra(ZeroGoodsDetailActivity.ACTIVITY_ID, zeroGoodsBeans.get(position).getActivityId());
//                intent.putExtra(ZeroGoodsDetailActivity.COMMODITY_ID, zeroGoodsBeans.get(position).getCommodityId());
//                intent.putExtra(ZeroGoodsDetailActivity.BIDDINGNUMBER, zeroGoodsBeans.get(position).getBiddingNumber());//每次加价幅度
//                intent.putExtra(ZeroGoodsDetailActivity.QIPAI_PRICE,zeroGoodsBeans.get(position).getStartPrice());
//                intent.putExtra(ZeroGoodsDetailActivity.GOODS_END_TIME, zeroGoodsBeans.get(position).getCloseTime());
                ActivityUtils.startActivity(intent);
            }
        });

    }


    @Override
    public void onGetGoodsByRoundIdSuccess(BaseModel<List<ZeroGoodsBean>> baseModel) {
        if(zeroGoodsBeans != null){
            zeroGoodsBeans.clear();
        }else {
            zeroGoodsBeans = new ArrayList<>();
        }
        zeroGoodsBeans.addAll(baseModel.getData());
        if(baseModel.getData() != null && baseModel.getData().size() != 0){
            String activityId = baseModel.getData().get(0).getActivityId();
            EventBus.getDefault().post(new EventBusMessage(activityId));
        }
        hotGridAdapter.notifyDataSetChanged();
        mPresenter.getGoodsCurrentPriceByRoundId(roundId);
    }
    //根据轮询id 获取当前商品竞拍价 与剩余时间--添加了图片 跟商品名称 从
    @Override
    public void onGetGoodsCurrentPrice(BaseModel<List<ZeroCurrentPriceBean>> baseModel) {
        zeroCurrentPriceBeanList=baseModel.getData();
      if(zeroGoodsBeans!=null && zeroGoodsBeans.size()>0 &&zeroCurrentPriceBeanList.size()>0){
          for (ZeroGoodsBean zeroGoodsBean : zeroGoodsBeans) {
              for (ZeroCurrentPriceBean zeroCurrentPriceBean : zeroCurrentPriceBeanList) {
                  if(zeroGoodsBean.getId().equals(zeroCurrentPriceBean.getAuctionCommodityId())){//商品id相同
                      zeroGoodsBean.setSalePrice(zeroCurrentPriceBean.getCurrentPrice());
                      zeroGoodsBean.setCloseTime(zeroCurrentPriceBean.getRemainingTime());
                      zeroGoodsBean.setStatus(zeroCurrentPriceBean.getStatus());
                  }
              }
          }
          hotGridAdapter.notifyDataSetChanged();
          //传递轮次时间
          EventBus.getDefault().post(zeroGoodsBeans.get(0));
      }
    }

//    /**
//     * 正在热拍的adapter
//     */
//    class HotGridAdapter extends CommonAdapter<ZeroGoodsBean> {
//
//        public HotGridAdapter(Context context, int layoutId, List<ZeroGoodsBean> datas) {
//            super(context, layoutId, datas);
//        }
//
//        @Override
//        protected void convert(ViewHolder holder, ZeroGoodsBean globalBuyerBean, int position) {
//            ImageView ivLogo = holder.getView(R.id.iv_zero_grid_logo);
//            Button btnZeroHot = holder.getView(R.id.btn_zero_hot);
//            GlideUtil.show(mContext, globalBuyerBean.getGoodsImageUrl(), ivLogo);
//            CountdownView countdownView = holder.getView(R.id.count_zero_hot_grid);
//            countdownView.start(globalBuyerBean.getCloseTime()*1000);
//            holder.setText(R.id.tv_zero_grid_name, globalBuyerBean.getCommodityName());
//            holder.setText(R.id.tv_zero_hot_price, getResources().getString(R.string.money)+PriceUtil.dividePrice(globalBuyerBean.getSalePrice()));
//
////            if(globalBuyerBean.type == 0){
//                btnZeroHot.setText("抢拍");
//                btnZeroHot.setBackgroundResource(R.drawable.bg_zero_hot_enable);
////            }else if(globalBuyerBean.type == 1){
////                btnZeroHot.setText("抢拍中(10s)");
////                btnZeroHot.setBackgroundResource(R.drawable.bg_zero_hot_enable);
////            }else if(globalBuyerBean.type == 2){
////                btnZeroHot.setText("拍中付款");
////                btnZeroHot.setBackgroundResource(R.drawable.bg_zero_hot_pay);
////            }else if(globalBuyerBean.type == 3){
////                btnZeroHot.setText("已结束");
////                btnZeroHot.setBackgroundResource(R.drawable.bg_zero_hot_disable);
////            }
//        }
//    }

}
