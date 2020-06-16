package com.xk.mall.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.FightGroupGoodsBean;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.GoodsServerDetailBean;
import com.xk.mall.model.entity.GoodsSkuListBean;
import com.xk.mall.model.entity.GoodsSkuListBean2;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.SkuAttribute;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.FightGroupGoodsDetailViewImpl;
import com.xk.mall.model.request.GroupOrderRequestBody;
import com.xk.mall.model.request.ShareRequestBody;
import com.xk.mall.presenter.GroupGoodsDetailPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.MeiQiaUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.TimeTools;
import com.xk.mall.view.adapter.GoodsImagesDetailAdapter;
import com.xk.mall.view.widget.DialogShare;
import com.xk.mall.view.widget.DialogShareGoods;
import com.xk.mall.view.widget.GlobalBuyerProductSkuDialog;
import com.xk.mall.view.widget.GroupProductSkuDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * ClassName GroupGoodsDetailActivity
 * Description 定制拼团 商品详情
 * Author
 * Date
 * Version
 */
public class GroupGoodsDetailActivity extends BaseActivity<GroupGoodsDetailPresenter> implements FightGroupGoodsDetailViewImpl {
    private static final String TAG = "GroupGoodsDetailActivity";
    public static final String ACTIVITY_GOODS_ID = "activity_goods_id";//活动商品id
    private static final int TIME_DESC = 1001;
    @BindView(R.id.scroll_works_detail)
    NestedScrollView scrollWorksDetail;//滑动view
    @BindView(R.id.banner_works_detail)
    Banner bannerWorksDetail;//banner
    int imageHeight = 375;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_line_price)
    TextView tvLinePrice;
    @BindView(R.id.rl_join_vip)
    RelativeLayout rlJoinVip;
    @BindView(R.id.ll_index)
    LinearLayout llIndex;
    @BindView(R.id.ll_kefu)
    LinearLayout llKefu;
    @BindView(R.id.btn_grab_buy)
    Button btnGrabBuy;
    @BindView(R.id.tv_group_end_time)
    TextView tvGroupEndTime;//倒计时
    @BindView(R.id.tv_postage)
    TextView tvPostage;//邮费
    @BindView(R.id.tv_coupon)
    TextView tvTargetNum;//最低拼团数量
    @BindView(R.id.tv_fh_time)
    TextView tvFahuoTime;
    @BindView(R.id.tv_group_suc_num)//当前拼团成功人数
    TextView tvGroupSucNum;
    @BindView(R.id.tv_merchant_name)
    TextView tvMerchantName;
    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;


    @BindView(R.id.recycleviewDetail)
    RecyclerView recycleviewDetail;

    private GlobalBuyerGoodsDetailBean goodsDetailBean;
    private GroupProductSkuDialog dialog;

    private String activityGoodsId;//活动商品id

    //分享需要
    private String commodityId, activityId, goodsId;
    private long chaTime;
    private int postage;
    private int targetNum;

    @Override
    protected GroupGoodsDetailPresenter createPresenter() {
        return new GroupGoodsDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fight_group_goods_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        setOnRightIconClickListener(v -> {
            //点击分享
            getShareContent();
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == TIME_DESC) {
                chaTime = chaTime - 1000;
                if (chaTime > 0) {
                    btnGrabBuy.setEnabled(true);
                    mHandler.sendEmptyMessageDelayed(TIME_DESC, 1000);
                    tvGroupEndTime.setText(TimeTools.getCountTimeHMSZH(chaTime));
                } else {
                    tvGroupEndTime.setText("已结束");
                    btnGrabBuy.setEnabled(false);
                }
            }
        }
    };

    @Override
    protected void initData() {
//        initDetailData();
        ImmersionBar.setTitleBar(this, toolbar);
        //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        ImmersionBar.with(this).statusBarDarkFont(false, 0.2f)
                .init();
        Intent intent = getIntent();
        activityGoodsId = intent.getStringExtra(ACTIVITY_GOODS_ID);

        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)) {
            rlJoinVip.setVisibility(View.GONE);
        }

        mPresenter.getGoodsDetailByGoodsId(activityGoodsId, ActivityType.ACTIVITY_FIGHT_GROUP, MyApplication.userId);

//        tvGoodsName.setText(name);
//        tvPrice.setText("" + price / 100);
//        tvLinePrice.setText(getResources().getString(R.string.money) + linePrice);
        tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        scrollWorksDetail.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, i, y, i2, i3) -> {
            if (y <= 0) {
                //设置透明
                ll_title_menu.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_dark));
                flRight.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_dark));
                ivLeftBack.setImageResource(R.drawable.ic_back_white);
                ivRight.setImageResource(R.drawable.ic_activity_share_white);
                toolbar.setBackgroundColor(Color.TRANSPARENT);
                toolbar_title.setText("");
                setDarkStatusIcon(false);
            } else if (y > 0 && y <= imageHeight) {
                ivLeftBack.setImageResource(R.drawable.ic_back);
                ivRight.setImageResource(R.drawable.ic_activity_share);
                setDarkStatusIcon(true);
                float scale = (float) y / imageHeight;
                float alpha = (255 * scale);
                // 只是layout背景透明(仿知乎滑动效果)白色透明
                toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                setTitle("");
                ll_title_menu.setBackgroundColor(Color.TRANSPARENT);
                flRight.setBackgroundColor(Color.TRANSPARENT);
            } else {
                ll_title_menu.setBackgroundColor(Color.WHITE);
                flRight.setBackgroundColor(Color.WHITE);
                ivLeftBack.setImageResource(R.drawable.ic_back);
                ivRight.setImageResource(R.drawable.ic_activity_share);
                setDarkStatusIcon(true);
                toolbar.setBackgroundColor(Color.WHITE);
                setTitle("商品详情");
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN) && rlJoinVip != null) {
            rlJoinVip.setVisibility(View.GONE);
        }
    }

//    private void initDetailData() {
//        String json=getResources().getString(R.string.sku_data);
//        Gson gson = new Gson();
//        goodsDetailBean = gson.fromJson(json, GoodsDetailBean.class);
//
//    }

    /**
     * SKU 弹窗
     * 1 加入购物车
     * 2 直接购买
     */
    private void showDialog() {
        if (dialog == null) {
            if(goodsDetailBean != null && goodsDetailBean.getActivityCommodityVo().getSkuList() != null){
                dialog = new GroupProductSkuDialog(this);
                if(MyApplication.switchBean != null && MyApplication.switchBean.getAssemble() == 1){
                    dialog.setPaySwitch(true);
                }else {
                    dialog.setPaySwitch(false);
                }
                dialog.setStock(goodsDetailBean.getActivityCommodityVo().getStock());
                dialog.setPrice(goodsDetailBean.getActivityCommodityVo().getCommodityPrice());
                dialog.setData(goodsDetailBean, (sku, quantity, memo) -> {
                    String resultMemo = memo.substring(0, memo.lastIndexOf(","));
                    buyGoods(sku, quantity, resultMemo);
                });
            }
        }else {
            dialog.show();
        }
    }


    /**
     * 直接购买
     **/
    private void buyGoods(GoodsSkuListBean2 sku, int num, String memo) {
        GroupOrderRequestBody requestBody = new GroupOrderRequestBody();
        requestBody.setFightGroupNumber(targetNum);
        requestBody.setActivityId(activityId);
        requestBody.setBuyerId(MyApplication.userId);
        requestBody.setCommodityId(sku.getId());
        requestBody.setCommodityModel(sku.getGoodsModel());
        requestBody.setCommodityQuantity(num);
        requestBody.setCommoditySpec(sku.getGoodsSpec());
        requestBody.setActivityGoodsId(activityGoodsId);
        requestBody.setGoodsCode(goodsDetailBean.getActivityCommodityVo().getGoodCode());
        requestBody.setGoodsId(goodsId);
        requestBody.setGoodsImageUrl(goodsDetailBean.getActivityCommodityVo().getImageList().get(0).getUrl());
        requestBody.setGoodsName(goodsDetailBean.getActivityCommodityVo().getCommodityName());
        requestBody.setMerchantId(goodsDetailBean.getActivityCommodityVo().getMerchantId());
        int price = goodsDetailBean.getActivityCommodityVo().getCommodityPrice();
        requestBody.setOrderAmount(price * num);
        requestBody.setOrderSource(1);
        Intent intent = new Intent(GroupGoodsDetailActivity.this, GroupGoodsXiaDanActivity.class);
        intent.putExtra("request_entity", requestBody);
        intent.putExtra("unit_price", price);
        intent.putExtra("postage", postage);
        startActivity(intent);
    }

    @OnClick({R.id.rl_join_vip, R.id.ll_index, R.id.ll_kefu, R.id.btn_grab_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_join_vip:
                ActivityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.ll_index:
                ActivityUtils.startActivity(MainActivity.class);
                break;
            case R.id.ll_kefu:
                MeiQiaUtil.initMeiqiaSDK(mContext);
                break;
            case R.id.btn_grab_buy:
                showDialog();
                break;
        }
    }

    /**
     * 获取分享内容
     */
    private void getShareContent() {
        ShareRequestBody.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareRequestBody.ActivityGoodsConditionBean();
        activityGoodsConditionBean.setActivityId(activityId);
        activityGoodsConditionBean.setCommodityId(commodityId);
        activityGoodsConditionBean.setGoodsId(goodsId);
        activityGoodsConditionBean.setActivityGoodsId(activityGoodsId);
        activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_FIGHT_GROUP);
        mPresenter.getShareContent(MyApplication.userId, activityGoodsConditionBean, ShareType.DETAIL_FIGHT_GROUP);
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if (shareBean != null) {
            DialogShareGoods dialogShare = new DialogShareGoods(mContext, shareBean);
            MyApplication.shareType = ShareType.DETAIL_FIGHT_GROUP;
            dialogShare.setListener(() -> {
                Intent intent = new Intent(mContext, PosterActivity.class);
                intent.putExtra(PosterActivity.GOODS_NAME, goodsDetailBean.getActivityCommodityVo().getCommodityName());
                intent.putExtra(PosterActivity.GOODS_IMG, goodsDetailBean.getActivityCommodityVo().getImageList().get(0).getUrl());
                intent.putExtra(PosterActivity.GOODS_PRICE, goodsDetailBean.getActivityCommodityVo().getSalePrice());
                intent.putExtra(PosterActivity.GOODS_ACTIVITY_TYPE, ActivityType.ACTIVITY_FIGHT_GROUP);
                String address = "";
                if(SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
                    String code = SPUtils.getInstance().getString(Constant.USER_INVITE_CODE, "");
                    address = shareBean.getParams().getUrl() + "?extcode=" + code;
                }else {
                    address = shareBean.getParams().getUrl();
                }
                intent.putExtra(PosterActivity.SHARE_ADDRESS, address);
                intent.putExtra(PosterActivity.GOODS_LINE_PRICE, goodsDetailBean.getActivityCommodityVo().getSalePrice());
                intent.putExtra(PosterActivity.GOODS_LINE_PRICE, activityGoodsId);
                ActivityUtils.startActivity(intent);
            });
            dialogShare.show();
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event) {
        if (event.shareType == ShareType.DETAIL_FIGHT_GROUP) {
            ShareSuccessBean.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareSuccessBean.ActivityGoodsConditionBean();
            activityGoodsConditionBean.setActivityId(activityId);
            activityGoodsConditionBean.setCommodityId(commodityId);
            activityGoodsConditionBean.setGoodsId(goodsId);
            activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_FIGHT_GROUP);
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.DETAIL_FIGHT_GROUP, activityGoodsConditionBean);
        }
    }

    @Override
    public void onGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> model) {
        SkuAttribute skuAttribute2=null;
        goodsDetailBean = model.getData();
        bindBanner(goodsDetailBean);
        bindGoods(model.getData());
        //处理数据手动添加  SpecMap() 值
        List<GoodsSkuListBean2> goodsSkuList = goodsDetailBean.getActivityCommodityVo().getSkuList();
        for (GoodsSkuListBean2 goodsSkuListBean : goodsSkuList) {
            SkuAttribute skuAttribute1 = new SkuAttribute("型号", goodsSkuListBean.getGoodsModel());
            if(!TextUtils.isEmpty(goodsSkuListBean.getGoodsSpec())){
                skuAttribute2 = new SkuAttribute("规格", goodsSkuListBean.getGoodsSpec());
            }
            List<SkuAttribute> skuAttributeList = new ArrayList<>();
            skuAttributeList.add(skuAttribute1);
            if(skuAttribute2!=null){
                skuAttributeList.add(skuAttribute2);
            }
            goodsSkuListBean.setSpecMap(skuAttributeList);
        }
    }

    //规则
    private void bindGoods(GlobalBuyerGoodsDetailBean model) {
        if (model != null) {
            GlobalBuyerGoodsDetailBean.BaseRuleVoBean baseRuleModel = model.getBaseRuleVo();
            if (!baseRuleModel.isFlag()) {//包邮
                tvPostage.setText("邮费：" + PriceUtil.dividePrice(baseRuleModel.getPostage()));
                postage = baseRuleModel.getPostage();
            }
            if( baseRuleModel.getFightGroupType()==1){
                tvTargetNum.setText("最低拼团人数" + baseRuleModel.getTargetNumber() + "人");
            }else{
                tvTargetNum.setText("最低拼团件数" + baseRuleModel.getTargetNumber() + "件");
            }

            targetNum = baseRuleModel.getTargetNumber();
            tvFahuoTime.setText("下单后发货时间：成团后" + baseRuleModel.getDeliveryDuration() + "小时");

            tvGoodsName.setText(goodsDetailBean.getActivityCommodityVo().getCommodityName());
            tvPrice.setText(PriceUtil.dividePrice(goodsDetailBean.getActivityCommodityVo().getCommodityPrice()));
            tvGroupSucNum.setText("已成功拼团" + baseRuleModel.getCurrentFightGroupNum() + "件");
            commodityId = goodsDetailBean.getActivityCommodityVo().getId();
            activityId = goodsDetailBean.getActivityCommodityVo().getActivityId();
            goodsId = goodsDetailBean.getActivityCommodityVo().getGoodsId();

            tvMerchantName.setText(goodsDetailBean.getActivityCommodityVo().getMerchantName());
            tvCategoryName.setText(goodsDetailBean.getActivityCommodityVo().getCategoryName());
            //倒计时
            long startTime = DateToolUtils.strToDateLong(goodsDetailBean.getActivityCommodityVo().getStartTime()).getTime();
            long endTime = DateToolUtils.strToDateLong(goodsDetailBean.getActivityCommodityVo().getEndTime()).getTime();
            long currentTime = System.currentTimeMillis();

            if (currentTime < startTime) {
                tvGroupEndTime.setText("未开始");
                btnGrabBuy.setEnabled(false);
            } else if (currentTime > endTime) {
                tvGroupEndTime.setText("已结束");
                btnGrabBuy.setEnabled(false);
            } else {
                chaTime = endTime - currentTime;
                mHandler.sendEmptyMessage(TIME_DESC);
            }
        }
    }

    /**
     * 绑定banner数据
     */
    private void bindBanner(GlobalBuyerGoodsDetailBean model) {
        List<String> detailImgs = new ArrayList<>();
        List<String> imgs = new ArrayList<>();
        if(model != null && model.getActivityCommodityVo() != null && model.getActivityCommodityVo().getImageList() != null){
            if (model.getActivityCommodityVo().getImageList().size() > 0) {
                for (GlobalBuyerGoodsDetailBean.ActivityCommodityVoBean.ImageListBean bean : model.getActivityCommodityVo().getImageList()) {
                    if(bean.getType() == 1){
                        imgs.add(bean.getUrl());
                    }else if(bean.getType() == 2){
                        detailImgs.add(bean.getUrl());
                    }
                }
                bannerWorksDetail.setImages(imgs);
            }
        }
        bannerWorksDetail.setImageLoader(new GlideImageLoader());

        bannerWorksDetail.setBannerStyle(BannerConfig.NUM_INDICATOR);
        bannerWorksDetail.setIndicatorGravity(BannerConfig.RIGHT);
        //设置nearBanner动画效果
        bannerWorksDetail.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        bannerWorksDetail.isAutoPlay(true);
        //设置轮播时间
        bannerWorksDetail.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        bannerWorksDetail.setOnBannerListener(position -> GlideUtil.lookBigImage(mContext,imgs,position));
        bannerWorksDetail.start();
        if(null != model.getOfficialPictures() && !TextUtils.isEmpty(model.getOfficialPictures())){
            detailImgs.add(model.getOfficialPictures());
        }
        if (detailImgs.size() != 0) {
            GoodsImagesDetailAdapter detailImgAdapter = new GoodsImagesDetailAdapter(mContext, detailImgs, new RvListener() {
                @Override
                public void onItemClick(int id, int index) {
                    GlideUtil.lookBigImage(mContext, detailImgs, index);
                }
            });
            recycleviewDetail.setLayoutManager(new LinearLayoutManager(mContext));
            recycleviewDetail.setAdapter(detailImgAdapter);
        }
    }

}
