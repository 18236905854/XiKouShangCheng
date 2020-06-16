package com.xk.mall.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.dao.MySQLiteOpenHelper;
import com.xk.mall.gen.AddressServerBeanDao;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.GoodsServerDetailBean;
import com.xk.mall.model.entity.GoodsSkuListBean;
import com.xk.mall.model.entity.GoodsSkuListBean2;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.SkuAttribute;
import com.xk.mall.model.entity.ZeroAuctionBean;
import com.xk.mall.model.entity.ZeroGoodsDetailBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.ZeroDetailViewImpl;
import com.xk.mall.model.request.ShareRequestBody;
import com.xk.mall.model.request.ZeroGivePriceRequestBody;
import com.xk.mall.model.request.ZeroOrderRequestBody;
import com.xk.mall.presenter.ZeroDetailPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.MeiQiaUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.view.adapter.GoodsImagesDetailAdapter;
import com.xk.mall.view.widget.DialogShare;
import com.xk.mall.view.widget.DialogShareGoods;
import com.xk.mall.view.widget.MerchantPhoneDialog;
import com.xk.mall.view.widget.PosterTipDialog;
import com.xk.mall.view.widget.UIHandler;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * ClassName ZeroGoodsDetailActivity
 * Description 0元抢拍商品详情
 * Author 卿凯
 * Date 2019/6/25/025
 * Version V1.0
 */
public class ZeroGoodsDetailActivity extends BaseActivity<ZeroDetailPresenter> implements ZeroDetailViewImpl, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.scroll_zero_detail)
    NestedScrollView scrollWorksDetail;//滑动view
    @BindView(R.id.banner_zero_detail)
    Banner bannerWorksDetail;//banner
    int imageHeight = 375;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;//商品名字
    @BindView(R.id.tv_price)
    TextView tvPrice;//当前竞拍价格
    @BindView(R.id.tv_line_price)
    TextView tvLinePrice;//真实价格
    @BindView(R.id.tv_zero_detail_give_times)
    TextView tvGiveTimes;//出价次数

    @BindView(R.id.rl_zero_detail_give_tip)
    RelativeLayout rlGiveTip;//出价记录
    @BindView(R.id.tv_zero_detail_give_tip)
    TextView tvGiveTip;//出价记录和总次数
    @BindView(R.id.tv_zero_detail_end_time)
    TextView tvEndTime;//离竞拍结束时间，当没开始时显示"竞拍尚未开始",开始后显示"离竞拍结束时间"并显示倒计时
    @BindView(R.id.count_zero_detail)
    CountdownView countdownView;
    @BindView(R.id.rv_zero_detail)
    RecyclerView rvZeroDetail;
    @BindView(R.id.ll_index)
    LinearLayout llIndex;
    @BindView(R.id.ll_kefu)
    LinearLayout llKefu;
    @BindView(R.id.btn_grab_buy)
    Button btnGrabBuy;
    @BindView(R.id.tv_zero_detail_success)
    TextView tvSuccess;//竞拍成功显示文字
    @BindView(R.id.tv_youfei)
    TextView tvYoufei;
//    @BindView(R.id.tv_qipai_price)
//    TextView tvQiPaiPrice;//起拍价
//    @BindView(R.id.tv_jia_price)
//    TextView tvJiaPrice;//加价
    @BindView(R.id.tv_coupon_each_time)
    TextView tvCouponEachTime;//出价费  多少优惠券一次
    @BindView(R.id.tv_daoji_time)
    TextView tvDaoJiTime;//倒计时
    @BindView(R.id.recycleviewDetail)
    RecyclerView recycleviewDetail;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_merchant_name)
    TextView tvMerchatName;
    @BindView(R.id.iv_brand_author)
    ImageView ivBrandAuthor;//品牌授权
    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;
    @BindView(R.id.ll_zero_time)
    LinearLayout llZeroTime;//抢拍倒计时按钮
    @BindView(R.id.count_zero_time)
    CountdownView countdownZeroView;
    private int giveTimes = 0;//出价次数
    private String goodsId = "";//商品ID
    private String activityGoodsId;//活动商品id
    private String activityId = "";//活动ID
    private String commodityId = "";//商品spu ID
    private int loopTime =2000;//轮询毫秒数
    private boolean isEnd = false;//商品竞拍是否结束
    private int countDownTime;//倒计时时长
    private int expendNumber;//出价费
    private boolean isSuccess;//是否竞拍成功
    private int postage;//邮费
    private GlobalBuyerGoodsDetailBean goodsServerDetailBean;
    private boolean isPaied;//是否拍过 10秒倒计时

    public static final String ACTIVITY_GOODS_ID = "activity_goods_id";//活动商品id

    private boolean isHasRecord = false;//是否有出价记录
    private int biddingNum;//每次加价
    private int currentPrice;//当前竞价
    private int qiPaiPrice;//起拍价
    private List<ZeroAuctionBean.RecordListBean> chuJiaLogList = new ArrayList<>();//出价记录
    private GivePriceAdapter logAdapter;
    private long endTime;

    @Override
    protected ZeroDetailPresenter createPresenter() {
        return new ZeroDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_zero_detail;
    }


    Handler loopHandler;

    Runnable loopRunnable = new Runnable() {
        @Override
        public void run() {
            loopHandler.postDelayed(this, loopTime );
            if (!isEnd) {
                Log.e(TAG, loopTime+"毫秒执行一次");
                mPresenter.getGoodsLunXu(activityGoodsId, MyApplication.userId);
            }
        }
    };

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        setOnRightIconClickListener(v -> {
            //点击分享
            getShareContent();
        });
    }

    @Override
    protected void initData() {
        loopHandler = new UIHandler<>(mContext);
        MyApplication.getInstance().addActivity(ZeroGoodsDetailActivity.this);
        ImmersionBar.setTitleBar(this, toolbar);
        //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f)
                .init();
        Intent intent = getIntent();
        Logger.e("倒计时时间:" + endTime);
        countdownView.start(endTime * 1000);
        activityGoodsId = intent.getStringExtra(ACTIVITY_GOODS_ID);
        //
        setShowDialog(false);
        mPresenter.getGoodsDetail(activityGoodsId, ActivityType.ACTIVITY_ZERO, MyApplication.userId);

         llIndex.setOnClickListener(v -> {
             ActivityUtils.startActivity(MainActivity.class);
         });
        bindTimes();
        initLocationPermission();

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
                ll_title_menu.setBackgroundColor(Color.TRANSPARENT);
                flRight.setBackgroundColor(Color.TRANSPARENT);
                setTitle("");
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

        rvZeroDetail.setLayoutManager(new LinearLayoutManager(mContext));
        //开始轮询
        loopHandler.postDelayed(loopRunnable, loopTime );

//        //根据开关控制确定按钮
//        if(MyApplication.switchBean != null && MyApplication.switchBean.getAuction() == 1){
//            btnGrabBuy.setEnabled(true);
//            btnGrabBuy.setBackgroundResource(R.drawable.bg_login_btn);
//        }else{
//            btnGrabBuy.setEnabled(false);
//            btnGrabBuy.setBackgroundResource(R.drawable.bg_login_btn_disable);
//        }
    }

    /**
     * 设置出价次数
     */
    private void bindTimes() {
        if (giveTimes != 0) {
            SpannableString spannableString = new SpannableString("出价" + PriceUtil.divideCoupon(expendNumber) + "优惠券1次(已出价" + giveTimes + "次)");
            RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.8f);
            spannableString.setSpan(sizeSpan, spannableString.length() - 7, spannableString.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            btnGrabBuy.setText(spannableString);
        }
    }

    @Keep
    @LoginFilter
    @OnClick({ R.id.ll_kefu, R.id.btn_grab_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_kefu:
                if(goodsServerDetailBean != null && goodsServerDetailBean.getActivityCommodityVo() != null &&
                        goodsServerDetailBean.getActivityCommodityVo().getMerchantPhone() != null){
                    MerchantPhoneDialog dialog = new MerchantPhoneDialog(mContext, R.style.mydialog,
                            goodsServerDetailBean.getActivityCommodityVo().getMerchantPhone());
                    dialog.show();
                }else {
                    ToastUtils.showShortToast(mContext, "商家电话为空");
                }
                break;
            case R.id.btn_grab_buy://出价
                if (isSuccess) {
//                    ToastUtils.showShortToast(mContext, "支付");
                    goToPay();
                } else {
                    ZeroGivePriceRequestBody requestBody = new ZeroGivePriceRequestBody();
                    requestBody.setArea(SPUtils.getInstance().getString(Constant.LOCATION));
                    requestBody.setUserId(MyApplication.userId);
                    requestBody.setUserName(SPUtils.getInstance().getString(Constant.NICK_NAME));
                    requestBody.setActivityId(activityId);
                    requestBody.setId(activityGoodsId);
                    requestBody.setCommodityId(commodityId);
                    mPresenter.offer(requestBody);
                    btnGrabBuy.setEnabled(false);
                }
                break;
        }
    }


    @OnClick({R.id.rl_zero_detail_give_tip})//点击出价记录
    public void onClick() {
        if (isHasRecord) {
            Intent intent = new Intent(mContext, ZeroGivePriceRecordActivity.class);
            intent.putExtra(ZeroGivePriceRecordActivity.GOODS_ID, activityGoodsId);
            intent.putExtra(ZeroGivePriceRecordActivity.EXPANDNUMBER_ID,  PriceUtil.divideCoupon(expendNumber));
            ActivityUtils.startActivity(intent);
        }
    }

    //获取出价记录 只取前面2条--轮询成功回调
    @Override
    public void onGetZeroGoodsLunXunSuc(BaseModel<ZeroAuctionBean> model) {
        bindRecord(model.getData());
    }

    /**
     * 获取分享内容
     */
    private void getShareContent(){
        ShareRequestBody.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareRequestBody.ActivityGoodsConditionBean();
        activityGoodsConditionBean.setActivityId(activityId);
        activityGoodsConditionBean.setCommodityId(commodityId);
        activityGoodsConditionBean.setActivityGoodsId(activityGoodsId);
        activityGoodsConditionBean.setGoodsId(goodsId);
        activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_ZERO);
        mPresenter.getShareContent(MyApplication.userId, activityGoodsConditionBean, ShareType.DETAIL_ZERO);
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if(shareBean != null){
            DialogShareGoods dialogShare = new DialogShareGoods(mContext, shareBean);
            MyApplication.shareType = ShareType.DETAIL_ZERO;
            dialogShare.setListener(() -> {
                Intent intent = new Intent(mContext, PosterActivity.class);
                intent.putExtra(PosterActivity.GOODS_NAME, goodsServerDetailBean.getActivityCommodityVo().getCommodityName());
                intent.putExtra(PosterActivity.GOODS_IMG, goodsServerDetailBean.getActivityCommodityVo().getImageList().get(0).getUrl());
                intent.putExtra(PosterActivity.GOODS_PRICE, 0);
                intent.putExtra(PosterActivity.GOODS_ACTIVITY_TYPE, ActivityType.ACTIVITY_ZERO);
                String address = "";
                if(SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
                    String code = SPUtils.getInstance().getString(Constant.USER_INVITE_CODE, "");
                    address = shareBean.getParams().getUrl() + "?extcode=" + code;
                }else {
                    address = shareBean.getParams().getUrl();
                }
                intent.putExtra(PosterActivity.SHARE_ADDRESS, address);
                intent.putExtra(PosterActivity.GOODS_LINE_PRICE, goodsServerDetailBean.getActivityCommodityVo().getMarketPrice());
                intent.putExtra(PosterActivity.GOODS_ACTIVITY_ID, activityGoodsId);
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
    public void shareCallback(ShareSuccessEvent event){
        if(event.shareType == ShareType.DETAIL_ZERO){
            ShareSuccessBean.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareSuccessBean.ActivityGoodsConditionBean();
            activityGoodsConditionBean.setActivityId(activityId);
            activityGoodsConditionBean.setCommodityId(commodityId);
            activityGoodsConditionBean.setGoodsId(goodsId);
            activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_ZERO);
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.DETAIL_ZERO, activityGoodsConditionBean);
        }
    }

    /**
     * 获取0元竞拍商品详情成功
     */
    @Override
    public void onGetZeroGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> beanBaseModel) {
        mPresenter.getGoodsLunXu(activityGoodsId,MyApplication.userId);
        goodsServerDetailBean = beanBaseModel.getData();
        if(goodsServerDetailBean == null || goodsServerDetailBean.getActivityCommodityVo() == null
            || goodsServerDetailBean.getBaseRuleVo() == null){
            ToastUtils.showShortToast(mContext, "获取商品出错");
            return;
        }
        bindBanner(goodsServerDetailBean);
        bindRule(goodsServerDetailBean.getBaseRuleVo());
        bindGoods(goodsServerDetailBean.getActivityCommodityVo());

    }

    /**
     * 绑定商品信息
     */
    private void bindGoods(GlobalBuyerGoodsDetailBean.ActivityCommodityVoBean activityCommodityVo) {
        activityId = activityCommodityVo.getActivityId();
        goodsId = activityCommodityVo.getGoodsId();
        commodityId = activityCommodityVo.getSkuList().get(0).getId();
        tvGoodsName.setText(activityCommodityVo.getCommodityName());
//        tvPrice.setText(PriceUtil.dividePrice(activityCommodityVo.getSalePrice()));
        tvLinePrice.setText( "￥"+PriceUtil.dividePrice(activityCommodityVo.getSalePrice()));
        tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        biddingNum=activityCommodityVo.getBiddingNumber();//每次加价
        qiPaiPrice=activityCommodityVo.getStartPrice();//起拍价

//        tvJiaPrice.setText(PriceUtil.dividePrice(biddingNum) + "元");
//        tvQiPaiPrice.setText(PriceUtil.dividePrice(qiPaiPrice) + "元");

        tvMerchatName.setText(activityCommodityVo.getMerchantName());
        tvCategoryName.setText(activityCommodityVo.getCategoryName());
        if(goodsServerDetailBean.getActivityCommodityVo().getBrandAuthorized() == 1){
            ivBrandAuthor.setVisibility(View.VISIBLE);
        }else {
            ivBrandAuthor.setVisibility(View.GONE);
        }
        //处理数据手动添加  SpecMap() 值
        List<GoodsSkuListBean2> goodsSkuList = activityCommodityVo.getSkuList();
        for (GoodsSkuListBean2 goodsSkuListBean : goodsSkuList) {
            SkuAttribute skuAttribute1 = new SkuAttribute("型号", goodsSkuListBean.getGoodsModel());
            SkuAttribute skuAttribute2 = new SkuAttribute("规格", goodsSkuListBean.getGoodsSpec());
            List<SkuAttribute> skuAttributeList = new ArrayList<>();
            skuAttributeList.add(skuAttribute1);
            skuAttributeList.add(skuAttribute2);
            goodsSkuListBean.setSpecMap(skuAttributeList);
        }
        if(MyApplication.switchBean != null && MyApplication.switchBean.getAuction() == 1){
            btnGrabBuy.setEnabled(true);
        }else {
            btnGrabBuy.setEnabled(false);
        }

    }

    /**
     * 绑定规则
     */
    private void bindRule(GlobalBuyerGoodsDetailBean.BaseRuleVoBean baseRuleModel) {
        if (baseRuleModel != null) {
            expendNumber = baseRuleModel.getExpendNumber();
//            //出价费 从规则中来
            tvCouponEachTime.setText(PriceUtil.divideCoupon(expendNumber) + "优惠券/次");
            btnGrabBuy.setText("出价" + PriceUtil.divideCoupon(expendNumber) + "优惠券1次");

            if (baseRuleModel.isFlag() || baseRuleModel.getPostage() == 0) {
                postage = 0;
                tvYoufei.setText("全国包邮");
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_poster_tips);
                // 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvYoufei.setCompoundDrawablePadding(3);
                tvYoufei.setCompoundDrawables(null, null, drawable, null);
                tvYoufei.setOnClickListener(v -> new PosterTipDialog(mContext).show());
            } else {
                postage = baseRuleModel.getPostage();
                tvYoufei.setText("邮费：" + PriceUtil.dividePrice(baseRuleModel.getPostage()) + "元");
            }

           //倒计时长
            if(baseRuleModel.getPostponeRange()==0){
                countDownTime = 10;
                //倒计时长
                tvDaoJiTime.setText("10s");
            }else{
                countDownTime = baseRuleModel.getPostponeRange();
                tvDaoJiTime.setText(baseRuleModel.getPostponeRange() + "s");
            }
            int appLoopSeconds = baseRuleModel.getAppLoopSeconds();
            if (appLoopSeconds != 0) {
                loopTime = appLoopSeconds;
                loopHandler.postDelayed(loopRunnable, loopTime );
//                loopTime = 14000;
            }
        }
    }

    /**
     * 绑定banner数据
     */
    private void bindBanner(GlobalBuyerGoodsDetailBean model) {
        List<String> stringImages = new ArrayList<>();
        List<String> detailImages = new ArrayList<>();
        if (model.getActivityCommodityVo() == null || model.getActivityCommodityVo().getImageList().size() <= 0) {
            List<Integer> imgs = new ArrayList<>();
            imgs.add(R.mipmap.wug_detail_banner);
            imgs.add(R.mipmap.wug_detail_banner);
            imgs.add(R.mipmap.wug_detail_banner);
            imgs.add(R.mipmap.wug_detail_banner);
            imgs.add(R.mipmap.wug_detail_banner);
            bannerWorksDetail.setImages(imgs);
        } else {

            for (GlobalBuyerGoodsDetailBean.ActivityCommodityVoBean.ImageListBean bean : model.getActivityCommodityVo().getImageList()) {
                if(bean.getType() == 1){//主图
                    stringImages.add(bean.getUrl());
                }else if(bean.getType() == 2){//详情图
                    detailImages.add(bean.getUrl());
                }
            }
            bannerWorksDetail.setImages(stringImages);
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
        bannerWorksDetail.setOnBannerListener(position -> GlideUtil.lookBigImage(mContext,stringImages,position));
        bannerWorksDetail.start();
        if(null != model.getOfficialPictures() && !TextUtils.isEmpty(model.getOfficialPictures())){
            detailImages.add(model.getOfficialPictures());
        }
        if(detailImages.size() != 0){
            GoodsImagesDetailAdapter detailImgAdapter = new GoodsImagesDetailAdapter(mContext, detailImages, new RvListener() {
                @Override
                public void onItemClick(int id, int index) {
                    GlideUtil.lookBigImage(mContext, detailImages, index);
                }
            });

            recycleviewDetail.setLayoutManager(new LinearLayoutManager(mContext));
            recycleviewDetail.setAdapter(detailImgAdapter);
        }
    }

    /**
     * 绑定出价记录
     */
    private void bindRecord(ZeroAuctionBean zeroAuctionBean) {
        chuJiaLogList.clear();

        giveTimes = zeroAuctionBean.getRecordCountForUser();
        endTime = zeroAuctionBean.getRemainingTime() * 1000;
        Logger.e("倒计时时间:" + endTime);
        countdownView.start(endTime);
        bindTimes();
        if (zeroAuctionBean.getStatus() == 1) {//未开始
            tvStatus.setVisibility(View.VISIBLE);
            countdownView.setVisibility(View.GONE);
            tvEndTime.setVisibility(View.GONE);
            tvStatus.setText("未开始");
            isEnd = false;
            btnGrabBuy.setEnabled(false);
            btnGrabBuy.setText("未开始");
        } else if (zeroAuctionBean.getStatus() == 2) {//已开始
            isEnd = false;//轮询前置条件
            tvStatus.setVisibility(View.GONE);
            countdownView.setVisibility(View.VISIBLE);
            btnGrabBuy.setEnabled(true);
            if(endTime != 0 && endTime <= zeroAuctionBean.getPostponeStart() * 1000){//当剩余时间小于等于倒计时时长的2倍就显示抢拍倒计时
                llZeroTime.setVisibility(View.VISIBLE);
                countdownZeroView.start(endTime);
            }
//            if (!isPaied) {
//                btnGrabBuy.setText("出价" + PriceUtil.dividePrice(expendNumber) + "优惠券1次");
//            }
        } else if(zeroAuctionBean.getStatus() ==3) {//已结束
            isEnd = true;
            tvEndTime.setVisibility(View.GONE);
            tvStatus.setVisibility(View.VISIBLE);
            countdownView.setVisibility(View.GONE);
            tvStatus.setText("已结束");
            btnGrabBuy.setText("已结束");
            btnGrabBuy.setEnabled(false);
        }else{
            tvStatus.setVisibility(View.VISIBLE);
            countdownView.setVisibility(View.GONE);
            tvEndTime.setVisibility(View.GONE);
            tvStatus.setText("轮次状态异常，请退出重新刷新轮次");
            btnGrabBuy.setEnabled(false);
            btnGrabBuy.setText("轮次状态异常");
            isEnd = false;
        }
//        if(zeroAuctionBean.getStatus()==2){//已开始
//
//        }else{
//            isFirstRequest=true;
//        }
        currentPrice = zeroAuctionBean.getCurrentPrice();
//        tvPrice.setText(PriceUtil.divideCoupon(zeroAuctionBean.getCurrentPrice())+"劵");


        if (zeroAuctionBean.getRecordList().size() == 0) {
            rvZeroDetail.setVisibility(View.GONE);
            tvGiveTimes.setVisibility(View.GONE);
            tvGiveTip.setText("暂无出价记录");
            isHasRecord = false;
        } else {
            isHasRecord = true;
            tvGiveTip.setText("出价记录");
            //出价次数
            tvGiveTimes.setText("(" + zeroAuctionBean.getRecordCount() + ")");
            rvZeroDetail.setVisibility(View.VISIBLE);
            tvGiveTimes.setVisibility(View.VISIBLE);
        }

        chuJiaLogList.addAll(zeroAuctionBean.getRecordList());
        if (logAdapter == null) {
            logAdapter = new GivePriceAdapter(mContext, R.layout.zero_give_price_item, chuJiaLogList);
            rvZeroDetail.setAdapter(logAdapter);
        } else {
            logAdapter.notifyDataSetChanged();
        }
        ////竞拍成功
        if (zeroAuctionBean.getWinnerId() !=null && zeroAuctionBean.getWinnerId().equals(MyApplication.userId)) {//竞拍成功
            tvSuccess.setVisibility(View.VISIBLE);
            btnGrabBuy.setText("去支付");
            btnGrabBuy.setEnabled(false);
            isSuccess = true;
            //后台默认生成订单----orderNo 跳0元拍订单详情
            ToastUtils.showShortToast(mContext,"竞拍成功!!!");
            String orderNo = zeroAuctionBean.getOrderNo();
            Log.e(TAG, "bindRecord:订单单号== "+orderNo);
            if(!TextUtils.isEmpty(orderNo)){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(mContext,ZeroOrderDetailActivity.class);
                        intent.putExtra(ZeroOrderDetailActivity.ORDER_NO,orderNo);
                        ActivityUtils.startActivity(intent);
                        finish();
                    }
                },1000);

            }else {
                //重新调用接口
                mPresenter.getGoodsLunXu(goodsId, MyApplication.userId);
            }
        } else {
            tvSuccess.setVisibility(View.GONE);
        }

        //根据开光控制 按钮
        if(MyApplication.switchBean != null && MyApplication.switchBean.getAuction() == 1 && zeroAuctionBean.getStatus() == 2 ){
            btnGrabBuy.setEnabled(true);
        }else{
            btnGrabBuy.setEnabled(false);
        }
    }

    //竞价成功
    @Override
    public void onGivePriceSuccess(BaseModel model) {
        btnGrabBuy.setEnabled(true);
//        isPaied = true;
        //开启倒计时
//        startCountDownTime();
        showToast(model.getMsg());
        giveTimes += 1;
        bindTimes();
        //添加到集合
        ZeroAuctionBean.RecordListBean add = new ZeroAuctionBean.RecordListBean();
        add.setArea(SPUtils.getInstance().getString(Constant.LOCATION));
        add.setUserId(MyApplication.userId);
        add.setUserName(SPUtils.getInstance().getString(Constant.NICK_NAME));
        currentPrice = currentPrice + biddingNum;
        add.setAuctionPrice(currentPrice);
        chuJiaLogList.add(0, add);
        logAdapter.notifyDataSetChanged();
//        tvPrice.setText(PriceUtil.dividePrice(currentPrice));

    }

    @AfterPermissionGranted(PERMISSION_CODE_LOCATION)
    private void initLocationPermission(){
        if(!EasyPermissions.hasPermissions(this, LOCATION_PERMISSION)){
            EasyPermissions.requestPermissions(this, getString(R.string.easy_permissions_location),
                    PERMISSION_CODE_LOCATION, LOCATION_PERMISSION);
        }else {
            startLocation();
        }
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private final int PERMISSION_CODE_LOCATION = 101;
    private static final String[] LOCATION_PERMISSION = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
    };

    private void startLocation(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setOnceLocation(true);
        //设置配置信息
        mLocationClient.setLocationOption(mLocationOption);
        //设置定位回调监听
        mLocationClient.setLocationListener(mAMapLocationListener);
        //启动定位
        mLocationClient.startLocation();
    }

    //异步获取定位结果
    AMapLocationListener mAMapLocationListener = amapLocation -> {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //解析定位结果
                String city = amapLocation.getCity();//获取城市
                SPUtils.getInstance().put(Constant.LOCATION, city);
            }
        }
    };
    /**
     * 开启倒计时
     */
//    private void startCountDownTime() {
//        Observable.interval(0, 1, TimeUnit.SECONDS) //0延迟  每隔1秒触发
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
//                .take(countDownTime) //设置循环次数
//                .map(aLong -> countDownTime - aLong) //从countDownTime-1
//                .doOnSubscribe(disposable -> btnGrabBuy.setClickable(false))
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        tvDaoJiTime.setText(aLong + "s");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        isPaied = false;
//                        tvDaoJiTime.setText(countDownTime + "s");
//                        btnGrabBuy.setClickable(true);
//                        btnGrabBuy.setEnabled(true);
//                    }
//                });
//    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        showToast(model.getMsg());
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        //启动定位
        startLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }


    class GivePriceAdapter extends CommonAdapter<ZeroAuctionBean.RecordListBean> {

        public GivePriceAdapter(Context context, int layoutId, List<ZeroAuctionBean.RecordListBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, ZeroAuctionBean.RecordListBean givePriceBean, int position) {
            holder.setText(R.id.tv_give_price_item_name, givePriceBean.getUserName());
            holder.setText(R.id.tv_give_price_item_address, givePriceBean.getArea());
            holder.setText(R.id.tv_give_price_item_price,  "+"+PriceUtil.divideCoupon(expendNumber)+"优惠券");
            if (position == 0) {
                holder.setText(R.id.tv_give_price_item_state, "当前");
            } else {
                holder.setText(R.id.tv_give_price_item_state, "出局");
            }
        }

        @Override
        public int getItemCount() {
            return getDatas().size() > 2 ? 2 : getDatas().size();
        }
    }

    /**
     * 下单
     */
    private void goToPay() {
        if(goodsServerDetailBean != null && goodsServerDetailBean.getActivityCommodityVo() != null){
            GoodsSkuListBean2 sku = goodsServerDetailBean.getActivityCommodityVo().getSkuList().get(0);
            ZeroOrderRequestBody requestBody = new ZeroOrderRequestBody();
            requestBody.setActivityGoodsId(activityGoodsId);
            requestBody.setActivityId(activityId);
            requestBody.setBuyerId(MyApplication.userId);
            requestBody.setCommodityAuctionPrice(currentPrice);
            requestBody.setCommodityId(sku.getId());
            requestBody.setCommodityModel(sku.getGoodsModel());
            requestBody.setCommodityQuantity(1);
            requestBody.setCommoditySpec(sku.getGoodsSpec());
            requestBody.setGoodsCode(goodsServerDetailBean.getActivityCommodityVo().getGoodCode());
            requestBody.setGoodsId(goodsId);
            requestBody.setGoodsImageUrl(goodsServerDetailBean.getActivityCommodityVo().getImageList().get(0).getUrl());
            requestBody.setGoodsName(goodsServerDetailBean.getActivityCommodityVo().getCommodityName());
            requestBody.setMerchantId(goodsServerDetailBean.getActivityCommodityVo().getMerchantId());
            requestBody.setOrderAmount(currentPrice + postage);
            requestBody.setOrderSource(1);
            Intent intent = new Intent(ZeroGoodsDetailActivity.this, ZeroXiaDanActivity.class);
            intent.putExtra("request_entity", requestBody);
            intent.putExtra("unit_price", currentPrice);
            intent.putExtra("postage", postage);
            startActivity(intent);
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLocationClient != null && mAMapLocationListener != null){
            mLocationClient.unRegisterLocationListener(mAMapLocationListener);
        }
        loopHandler.removeCallbacks(loopRunnable);
        loopHandler = null;
        MyApplication.getInstance().removeActivity(this);
    }
}
