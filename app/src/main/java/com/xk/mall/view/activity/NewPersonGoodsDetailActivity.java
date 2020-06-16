package com.xk.mall.view.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gyf.immersionbar.ImmersionBar;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.GoodsSkuListBean2;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.SkuAttribute;
import com.xk.mall.model.entity.WuGOrderBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.NewPersonGoodsDetailViewImpl;
import com.xk.mall.model.request.ShareRequestBody;
import com.xk.mall.presenter.NewPersonGoodsDetailPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.ImageUtil;
import com.xk.mall.utils.MeiQiaUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.CircleProgressBar;
import com.xk.mall.view.widget.DialogShareGoods;
import com.xk.mall.view.widget.MerchantPhoneDialog;
import com.xk.mall.view.widget.NewPersonProductSkuDialog;
import com.xk.mall.view.widget.PosterTipDialog;
import com.xk.mall.view.widget.WuGProductSkuDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ClassName: NewPersonGoodsDetailActivity
 * @Description: 新人专区商品详情页面
 * @Author: 卿凯
 * @Date: 2019/9/5/005 9:36
 * @Version: 1.0
 */
public class NewPersonGoodsDetailActivity extends BaseActivity<NewPersonGoodsDetailPresenter> implements NewPersonGoodsDetailViewImpl {

    @BindView(R.id.stateView)
    MultiStateView stateView;
    @BindView(R.id.scroll_works_detail)
    NestedScrollView scrollWorksDetail;//滑动view
    @BindView(R.id.rv_wug_detail)
    RecyclerView rvDetail;
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
    @BindView(R.id.tv_merchant_name)
    TextView tvMerchantName;
    @BindView(R.id.iv_brand_author)
    ImageView ivBrandAuthor;//品牌授权
    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_wug_detail_delivery)
    TextView tvDelivery;//发货时长
    @BindView(R.id.tv_wug_detail_postage)
    TextView tvPostage;//邮费
    @BindView(R.id.tv_wug_detail_limit)
    TextView tvLimit;//限购数量
    @BindView(R.id.tv_vip_only)
    TextView tvVipOnly;//会员专享

    private GlobalBuyerGoodsDetailBean goodsDetailBean;
    private NewPersonProductSkuDialog dialog;
    private String goodsId = "";//商品sku ID
    private String activityId = "";//活动ID
    private String commodityId = "";//商品spu ID
    private String activityGoodsId = "";//活动商品ID

    private int postage;//邮费
    private int limitNum;//限购数量

    /**intent传递过来的商品spu ID的key*/
    public static final String ACTIVITY_GOODS_ID = "activity_goods_id";

    @Override
    protected NewPersonGoodsDetailPresenter createPresenter() {
        return new NewPersonGoodsDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wug_goods_detail;
    }

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
        ImmersionBar.setTitleBar(this, toolbar);
        //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        ImmersionBar.with(this).statusBarDarkFont(false, 0.2f)
                .init();
        Intent intent = getIntent();
        activityGoodsId = intent.getStringExtra(ACTIVITY_GOODS_ID);

        if(SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
            rlJoinVip.setVisibility(View.GONE);
        }
        tvVipOnly.setText("新人专享");
        mPresenter.getNewGoodsDetail(activityGoodsId, ActivityType.ACTIVITY_NEW_PERSON, MyApplication.userId);

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

        Button btnReplay = stateView.findViewById(R.id.btn_replay);
        CircleProgressBar pbLoading = stateView.findViewById(R.id.pb_header_loading);
        ValueAnimator animator = ValueAnimator.ofInt(0, 100).setDuration(2000);
        animator.addUpdateListener(valueAnimator -> pbLoading.setProgress((int) valueAnimator.getAnimatedValue()));
        animator.start();
        btnReplay.setOnClickListener(v -> mPresenter.getNewGoodsDetail(activityGoodsId, ActivityType.ACTIVITY_NEW_PERSON, MyApplication.userId));
    }

    @Override
    public void onGetNewGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> model) {
        SkuAttribute skuAttribute2=null;
        stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        goodsDetailBean = model.getData();
        bindBanner(goodsDetailBean);
        bindGoods(model.getData());
        bindRule(model.getData().getBaseRuleVo());
        //处理数据手动添加  SpecMap() 值
        List<GoodsSkuListBean2> goodsSkuList = goodsDetailBean.getActivityCommodityVo().getSkuList();
        for (GoodsSkuListBean2 goodsSkuListBean : goodsSkuList) {
            if(XiKouUtils.isNullOrEmpty(goodsSkuListBean.getSkuImage())){
                goodsSkuListBean.setSkuImage(goodsDetailBean.getActivityCommodityVo().getImageList().get(0).getUrl());
            }
            if(TextUtils.isEmpty(goodsSkuListBean.getGoodsModel())){
                goodsSkuListBean.setGoodsModel("型号");
            }
            if(TextUtils.isEmpty(goodsDetailBean.getActivityCommodityVo().getGoodsModel())){
                goodsDetailBean.getActivityCommodityVo().setGoodsModel("型号");
            }
            SkuAttribute skuAttribute1 = new SkuAttribute(goodsDetailBean.getActivityCommodityVo().getGoodsModel(), goodsSkuListBean.getCommodityModel());
            if(!TextUtils.isEmpty(goodsSkuListBean.getCommoditySpec())){
                if(TextUtils.isEmpty(goodsSkuListBean.getGoodsSpec())){
                    goodsSkuListBean.setGoodsSpec("规格");
                }
                if(TextUtils.isEmpty(goodsDetailBean.getActivityCommodityVo().getGoodsSpec())){
                    goodsDetailBean.getActivityCommodityVo().setGoodsSpec("规格");
                }
                skuAttribute2 = new SkuAttribute(goodsDetailBean.getActivityCommodityVo().getGoodsSpec(), goodsSkuListBean.getCommoditySpec());
            }

            List<SkuAttribute> skuAttributeList = new ArrayList<>();
            skuAttributeList.add(skuAttribute1);
            if(skuAttribute2!=null){
                skuAttributeList.add(skuAttribute2);
            }
            goodsSkuListBean.setSpecMap(skuAttributeList);
        }
    }

    /**
     * 绑定商品数据
     */
    private void bindGoods(GlobalBuyerGoodsDetailBean goodsDetailBean) {
        if(goodsDetailBean != null && goodsDetailBean.getActivityCommodityVo() != null){
            tvGoodsName.setText(goodsDetailBean.getActivityCommodityVo().getCommodityName());
            tvPrice.setText(String.valueOf(PriceUtil.dividePrice(goodsDetailBean.getActivityCommodityVo().getCommodityPrice())));
            tvLinePrice.setVisibility(View.VISIBLE);
            tvLinePrice.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(goodsDetailBean.getActivityCommodityVo().getSalePrice()));
            tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            commodityId = goodsDetailBean.getActivityCommodityVo().getId();
            activityId = goodsDetailBean.getActivityCommodityVo().getActivityId();
            goodsId = goodsDetailBean.getActivityCommodityVo().getGoodsId();
            if(TextUtils.isEmpty(goodsDetailBean.getActivityCommodityVo().getMerchantName())){
                tvMerchantName.setText("喜扣商城");
            }else{
                tvMerchantName.setText(goodsDetailBean.getActivityCommodityVo().getMerchantName());
            }
            if(goodsDetailBean.getActivityCommodityVo().getBrandAuthorized() == 1){
                ivBrandAuthor.setVisibility(View.VISIBLE);
            }else {
                ivBrandAuthor.setVisibility(View.GONE);
            }
            tvCategoryName.setText(goodsDetailBean.getActivityCommodityVo().getCategoryName());
            if(goodsDetailBean.getActivityCommodityVo() != null && goodsDetailBean.getActivityCommodityVo().getStock() == 0){
                btnGrabBuy.setEnabled(false);
                btnGrabBuy.setText("已售罄");
            }else {
                if(MyApplication.switchBean != null && MyApplication.switchBean.getNwPerson() == 1){
                    btnGrabBuy.setEnabled(true);
                }else {
                    btnGrabBuy.setEnabled(false);
                }
            }
        }
    }

    /**
     * 绑定规则信息
     */
    private void bindRule(GlobalBuyerGoodsDetailBean.BaseRuleVoBean baseRuleModel) {
        if(baseRuleModel != null){
            if(baseRuleModel.getCouponValue() == 0){
                tvCoupon.setVisibility(View.GONE);
            }else {
                tvCoupon.setVisibility(View.VISIBLE);
                tvCoupon.setText("抢购成功即可获得优惠券" + PriceUtil.dividePrice(baseRuleModel.getCouponValue()));
            }
            if(baseRuleModel.isFlag()){
                tvPostage.setText("全国包邮");
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_poster_tips);
                // 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvPostage.setCompoundDrawablePadding(3);
                tvPostage.setCompoundDrawables(null, null, drawable, null);
                tvPostage.setOnClickListener(v -> new PosterTipDialog(mContext).show());
                postage = 0;
            }else {
                tvPostage.setText("邮费:" + PriceUtil.dividePrice(baseRuleModel.getPostage()));
                postage = baseRuleModel.getPostage();
            }

            tvDelivery.setText("下单后 " + baseRuleModel.getDeliveryDuration() + " 小时内发货");
            if(!baseRuleModel.isBuyLimited()){
                tvLimit.setText("限购数量:不限");
            }else {
                limitNum = baseRuleModel.getBuyLimit();
                tvLimit.setText("限购数量: " + limitNum);
            }
        }
    }

    /**
     * 绑定banner数据
     */
    private void bindBanner(GlobalBuyerGoodsDetailBean model) {
        if(model == null){
            return;
        }
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
        if(detailImgs.size() != 0){
            DetailImgAdapter detailImgAdapter = new DetailImgAdapter(mContext, R.layout.item_goods_detail_img, detailImgs);
            detailImgAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int index) {
                    GlideUtil.lookBigImage(mContext, detailImgs, index);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            rvDetail.setLayoutManager(new LinearLayoutManager(mContext));
            rvDetail.setAdapter(detailImgAdapter);
        }
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if(shareBean != null){
            DialogShareGoods dialogShare = new DialogShareGoods(mContext, shareBean);
            MyApplication.shareType = ShareType.DETAIL_WUG;
            dialogShare.setListener(() -> {
                Intent intent = new Intent(mContext, PosterActivity.class);
                intent.putExtra(PosterActivity.GOODS_NAME, goodsDetailBean.getActivityCommodityVo().getCommodityName());
                intent.putExtra(PosterActivity.GOODS_IMG, goodsDetailBean.getActivityCommodityVo().getImageList().get(0).getUrl());
                intent.putExtra(PosterActivity.GOODS_PRICE, goodsDetailBean.getActivityCommodityVo().getCommodityPrice());
                intent.putExtra(PosterActivity.GOODS_ACTIVITY_TYPE, ActivityType.ACTIVITY_NEW_PERSON);
                String address = "";
                if(SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
                    String code = SPUtils.getInstance().getString(Constant.USER_INVITE_CODE, "");
                    address = shareBean.getParams().getUrl() + "?extcode=" + code;
                }else {
                    address = shareBean.getParams().getUrl();
                }
                intent.putExtra(PosterActivity.SHARE_ADDRESS, address);
                intent.putExtra(PosterActivity.GOODS_LINE_PRICE, goodsDetailBean.getActivityCommodityVo().getSalePrice());
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
        if(event.shareType == ShareType.DETAIL_NEW_PERSON){
            ShareSuccessBean.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareSuccessBean.ActivityGoodsConditionBean();
            activityGoodsConditionBean.setActivityId(activityId);
            activityGoodsConditionBean.setCommodityId(commodityId);
            activityGoodsConditionBean.setGoodsId(goodsId);
            activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_NEW_PERSON);
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.DETAIL_NEW_PERSON, activityGoodsConditionBean);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN) && rlJoinVip != null) {
            rlJoinVip.setVisibility(View.GONE);
        }
    }

    /**
     * SKU 弹窗
     *   1 加入购物车
     *   2 直接购买
     */
    private void showDialog() {
        if(goodsDetailBean != null && goodsDetailBean.getActivityCommodityVo().getSkuList() != null
                && goodsDetailBean.getActivityCommodityVo().getSkuList().size() != 0){
            if (dialog == null) {
                dialog = new NewPersonProductSkuDialog(this);
                if(MyApplication.switchBean != null && MyApplication.switchBean.getNwPerson() == 1){
                    dialog.setPaySwitch(true);
                }else {
                    dialog.setPaySwitch(false);
                }
                dialog.setLimitNum(limitNum);
//                dialog.setStock(goodsDetailBean.getActivityCommodityVo().getStock());
//                dialog.setPrice(goodsDetailBean.getActivityCommodityVo().getCommodityPrice());
                dialog.setData(goodsDetailBean, (sku, quantity, memo) -> {
//                    String resultMemo = memo.substring(0, memo.lastIndexOf(","));
                    //下单购买
                    Logger.e("下单数据:" + memo);
                    buyGoods(sku, quantity, memo);
                });
                dialog.show();
            }else {
                dialog.show();
            }
        }else {
            ToastUtils.showShortToast(mContext, "商品数据有误");
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
    }

    /**
     * 直接购买
     **/
    private void buyGoods(GoodsSkuListBean2 sku, int num, String memo) {
        int totalPrice = sku.getCommodityPrice() * num;
        WuGOrderBean wuGOrderBean = new WuGOrderBean();
        wuGOrderBean.setActivityId(activityId);
        wuGOrderBean.setBuyerId(MyApplication.userId);
        wuGOrderBean.setActivityGoodsId(sku.getId());
        wuGOrderBean.setCommodityId(sku.getCommodityId());
        wuGOrderBean.setCommodityModel(sku.getCommodityModel());
        wuGOrderBean.setCommoditySpec(sku.getCommoditySpec());
//        wuGOrderBean.setConsignmentId(goodsDetailBean.getActivityCommodityVo().getConsignmentId());//设置寄卖用户ID
        wuGOrderBean.setCommodityQuantity(num);
        wuGOrderBean.setUnitPrice(sku.getCommodityPrice());
        wuGOrderBean.setGoodsCode(goodsDetailBean.getActivityCommodityVo().getGoodCode());
        wuGOrderBean.setGoodsId(goodsId);
        wuGOrderBean.setGoodsName(sku.getCommodityName());
        wuGOrderBean.setGoodsImageUrl(sku.getSkuImage());
        wuGOrderBean.setMerchantId(goodsDetailBean.getActivityCommodityVo().getMerchantId());//商家ID
        wuGOrderBean.setOrderAmount(totalPrice);
        wuGOrderBean.setOrderSource(1);
        wuGOrderBean.setOriginalOrderNo(goodsDetailBean.getActivityCommodityVo().getOriginalId());//设置寄卖用户原始订单号
        Intent intent = new Intent(mContext, WuGGoodsXiaDanActivity.class);
        intent.putExtra(WuGGoodsXiaDanActivity.TOTAL_PRICE, totalPrice);
        intent.putExtra(WuGGoodsXiaDanActivity.XIA_DAN_DATA, (Serializable) wuGOrderBean);
        intent.putExtra(WuGGoodsXiaDanActivity.XIA_DAN_COUPON, 0);
        intent.putExtra(WuGGoodsXiaDanActivity.XIA_DAN_POSTAGE, postage);
        startActivity(intent);
    }


    @OnClick({R.id.rl_join_vip,R.id.ll_index, R.id.ll_kefu, R.id.btn_grab_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_join_vip:
                ActivityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.ll_index:
                ActivityUtils.startActivity(MainActivity.class);
                break;
            case R.id.ll_kefu:
                if(goodsDetailBean != null && goodsDetailBean.getActivityCommodityVo() != null &&
                        goodsDetailBean.getActivityCommodityVo().getMerchantPhone() != null){
                    MerchantPhoneDialog dialog = new MerchantPhoneDialog(mContext, R.style.mydialog,
                            goodsDetailBean.getActivityCommodityVo().getMerchantPhone());
                    dialog.show();
                }else {
                    ToastUtils.showShortToast(mContext, "商家电话为空");
                }
                break;
            case R.id.btn_grab_buy:
                showDialog();
                break;
        }
    }

    /**
     * 获取分享内容
     */
    private void getShareContent(){
        if(goodsDetailBean != null && goodsDetailBean.getActivityCommodityVo() != null &&
                goodsDetailBean.getActivityCommodityVo().getSkuList().size() != 0){
            GoodsSkuListBean2 listBean = goodsDetailBean.getActivityCommodityVo().getSkuList().get(0);
            ShareRequestBody.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareRequestBody.ActivityGoodsConditionBean();
            activityGoodsConditionBean.setActivityId(activityId);
            activityGoodsConditionBean.setCommodityId(listBean.getCommodityId());
            activityGoodsConditionBean.setActivityGoodsId(listBean.getId());
            activityGoodsConditionBean.setGoodsId(goodsId);
            activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_NEW_PERSON);
            mPresenter.getShareContent(MyApplication.userId, activityGoodsConditionBean, ShareType.DETAIL_NEW_PERSON);
        }
    }

    /**
     * 商品详情图片的adapter
     */
    private class DetailImgAdapter extends CommonAdapter<String> {

        public DetailImgAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, String url, int position) {
            ImageView ivLogo = holder.getView(R.id.iv_goods_detail_img);
            ivLogo.setImageResource(R.drawable.ic_loading);
            //获取图片真正的宽高
            Glide.with(mContext).asBitmap().load(url).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                    float bitWidth = bitmap.getWidth();
                    float bitHeight = bitmap.getHeight();
                    //图片超出GPU对于openglRender最大限制缩放处理
                    if(bitHeight > ImageUtil.getOpenglRenderLimitValue()){
//                        bitWidth = bitWidth * ImageUtil.getOpenglRenderLimitValue() / bitHeight;
                        bitHeight = ImageUtil.getOpenglRenderLimitValue();
                    }
                    bitmap.setWidth((int) bitWidth);
                    bitmap.setHeight((int) bitHeight);
                    bitWidth = bitmap.getWidth();
                    bitHeight = bitmap.getHeight();
                    float ratio = bitWidth / bitHeight;//真实图片宽高比
                    // 获得屏幕宽高
                    DisplayMetrics displayMetrics = DensityUtils.getDisplayMetrics(mContext);
                    int screenWidth = displayMetrics.widthPixels - DensityUtils.dp2px(mContext,30);
                    float height=(screenWidth/ratio);
//                    Logger.e(position + ",img宽：" + screenWidth + ",img高:" + height);
                    //动态设置ImageView 高度
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivLogo.getLayoutParams();
                    layoutParams.width = screenWidth;
                    layoutParams.height = Float.valueOf(height).intValue();
                    // ImageView 控件设置
                    ivLogo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ivLogo.setAdjustViewBounds(true);
                    ivLogo.setLayoutParams(layoutParams);
                    ivLogo.setImageBitmap(bitmap);
                }
            });
        }
    }
}
