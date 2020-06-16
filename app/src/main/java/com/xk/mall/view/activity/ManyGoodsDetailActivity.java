package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
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
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.GoodsSkuListBean2;
import com.xk.mall.model.entity.ManyCartsBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.SkuAttribute;
import com.xk.mall.model.eventbean.OrderSuccessBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.ManyGoodsDetailViewImpl;
import com.xk.mall.model.request.ManyAddCartBody;
import com.xk.mall.model.request.ShareRequestBody;
import com.xk.mall.presenter.ManyGoodsDetailPresenter;
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
import com.xk.mall.view.widget.DialogShareGoods;
import com.xk.mall.view.widget.ManyBuyProductSkuDialog;
import com.xk.mall.view.widget.MerchantPhoneDialog;
import com.xk.mall.view.widget.PosterTipDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName ManyGoodsDetailActivity
 * Description 多买多折 商品详情
 * Author
 * Date
 * Version
 */
public class ManyGoodsDetailActivity extends BaseActivity<ManyGoodsDetailPresenter> implements ManyGoodsDetailViewImpl {
    private static final String TAG = "ManyGoodsDetailActivity";
    @BindView(R.id.ll_cart)
    RelativeLayout flCart;//右边购物车
    @BindView(R.id.iv_right_cart)
    ImageView ivCart;//购物车图标
    @BindView(R.id.scroll_works_detail)
    NestedScrollView scrollWorksDetail;//滑动view
    @BindView(R.id.rv_many_goods_detail)
    RecyclerView rvDetail;//商品详情图片
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
    @BindView(R.id.tv_many_goods_add_cart)
    TextView tvManyAddCart;//加入购物车
    @BindView(R.id.tv_many_buy_delivery)
    TextView tvDelivery;
    @BindView(R.id.tv_many_buy_post)
    TextView tvPost;
    @BindView(R.id.tv_merchant_name)
    TextView tvMerchantName;
    @BindView(R.id.iv_brand_author)
    ImageView ivBrandAuthor;//商品授权
    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;
    @BindView(R.id.tv_hot_sale_num)
    TextView tvShareAmount;//分享赚


    /**intent传递过来的值的key*/
    public static final String ACTIVITY_GOODS_ID = "activity_goods_id";//活动ID
//    public static final String COMMODITY_ID = "commodityId";//sku ID
//    public static final String GOODS_ID = "goods_id";// spu ID
//    public static final String GOODS_NAME = "goods_name";//商品名称
//    public static final String GOODS_PRICE = "goods_price";//商品活动价
//    public static final String GOODS_LINE_PRICE = "goods_line_price";//商品销售价

    private GlobalBuyerGoodsDetailBean goodsServerDetailBean;
    private ManyBuyProductSkuDialog dialog;
    int maxLimitNum = 0;//商品最大限购数量
    int minLimitNum = 0;//商品最大限购数量
    private String activityGoodsId = "";//活动商品ID
    private String activityId;//活动id
    private String commodityId;//sku id
//    private String goodsUrl;//商品主图
    private String goodsId = "";//商品ID

    @Override
    protected ManyGoodsDetailPresenter createPresenter() {
        return new ManyGoodsDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_many_goods_detail;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        setDarkStatusIcon(true);
        flCart.setOnClickListener(v -> {
            toCartActivity();
        });
        setOnRightIconClickListener(v -> {
            //点击分享
            getShareContent();
        });
    }
    @Keep
    @LoginFilter
    private void toCartActivity(){
        //跳转购物车页面
        ActivityUtils.startActivity(ManyCartActivity.class);
    }

    @Override
    protected void initData() {
//        initDetailData();
        ImmersionBar.setTitleBar(this, toolbar);
        //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        ImmersionBar.with(this).statusBarDarkFont(false, 0.2f)
                .init();
        Intent intent = getIntent();
        activityGoodsId = intent.getStringExtra(ACTIVITY_GOODS_ID);
        mPresenter.getGoodsDetailByGoodsId(activityGoodsId, ActivityType.ACTIVITY_MANY_BUY, MyApplication.userId);
        if(!TextUtils.isEmpty(MyApplication.userId)){
            mPresenter.getCartData(MyApplication.userId);
        }
        if(SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
            rlJoinVip.setVisibility(View.GONE);
        }

        rvDetail.setNestedScrollingEnabled(false);
        rvDetail.setHasFixedSize(true);
        scrollWorksDetail.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, i, y, i2, i3) -> {
            if (y <= 0) {
                //设置透明
                ll_title_menu.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_dark));
                flRight.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_dark));
                flCart.setBackground(getResources().getDrawable(R.drawable.bg_toolbar_dark));
                ivLeftBack.setImageResource(R.drawable.ic_back_white);
                ivRight.setImageResource(R.drawable.ic_activity_share_white);
                ivCart.setImageResource(R.mipmap.home_ic_cart);
                toolbar.setBackgroundColor(Color.TRANSPARENT);
                toolbar_title.setText("");
                setDarkStatusIcon(false);
            } else if (y > 0 && y <= imageHeight) {
                ivLeftBack.setImageResource(R.drawable.ic_back);
                ivRight.setImageResource(R.drawable.ic_activity_share);
                ivCart.setImageResource(R.drawable.ic_many_buy_cart);
                setDarkStatusIcon(true);
                float scale = (float) y / imageHeight;
                float alpha = (255 * scale);
                // 只是layout背景透明(仿知乎滑动效果)白色透明
                toolbar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                setTitle("");
                ll_title_menu.setBackgroundColor(Color.TRANSPARENT);
                flRight.setBackgroundColor(Color.TRANSPARENT);
                flCart.setBackgroundColor(Color.TRANSPARENT);
            } else {
                ll_title_menu.setBackgroundColor(Color.WHITE);
                flRight.setBackgroundColor(Color.WHITE);
                flCart.setBackgroundColor(Color.WHITE);
                ivLeftBack.setImageResource(R.drawable.ic_back);
                ivRight.setImageResource(R.drawable.ic_activity_share);
                ivCart.setImageResource(R.drawable.ic_many_buy_cart);
                setDarkStatusIcon(true);
                toolbar.setBackgroundColor(Color.WHITE);
                setTitle("商品详情");
            }
        });

    }

    /**
     * SKU 弹窗
     * 1 加入购物车
     * 2 直接购买
     */
    private void showDialog() {
        if(goodsServerDetailBean != null && goodsServerDetailBean.getActivityCommodityVo() != null &&
                goodsServerDetailBean.getActivityCommodityVo().getSkuList() != null &&
                goodsServerDetailBean.getActivityCommodityVo().getSkuList().size() != 0){
            if (dialog == null) {
                dialog = new ManyBuyProductSkuDialog(this);
                int stock = 0;
//                if(MyApplication.switchBean != null && MyApplication.switchBean.getGloberBuyer() == 0){
//                    stock = 0;
//                }else {
                stock = goodsServerDetailBean.getActivityCommodityVo().getStock();
//                }
                if(MyApplication.switchBean != null && MyApplication.switchBean.getMoreDisCount() == 1){
                    dialog.setPaySwitch(true);
                }else {
                    dialog.setPaySwitch(false);
                }
                dialog.setDiscount(MyApplication.rateOne);
//                dialog.setStock(stock);
                dialog.setLimitNum(maxLimitNum);
                dialog.setMinLimitNum(minLimitNum);
                dialog.setData(goodsServerDetailBean, (sku, quantity, memo) -> {
//                    String resultMemo = memo.substring(0, memo.lastIndexOf(","));
//                    Log.e(TAG, "onAdded:resultMemo= " + resultMemo);
                    addCart(sku, quantity, memo);
                });
                dialog.show();
            }else {
                dialog.show();
            }
        }else {
            ToastUtils.showShortToast(mContext, "商品数据有误");
        }
    }


    /**
     * 添加购物车
     * @sku   sku 信息
     * @param num   购买的数量
     * @params  memo  选中sku 信息
     **/
    private void addCart(GoodsSkuListBean2 sku, int num, String memo) {
        ManyAddCartBody requestBody=new ManyAddCartBody();
        requestBody.setActivityGoodsId(sku.getId());
        requestBody.setCommodityName(sku.getCommodityName());//sku 名称   商品名称
        requestBody.setActivityPrice(sku.getSalePrice());//活动价钱  //TODO   *  dicount
        requestBody.setBuyerNumber(num);//购买数量
        requestBody.setBuyerUserId(MyApplication.userId);//购买用户id
        requestBody.setCommodityId(sku.getCommodityId());//sku商品id
        requestBody.setCommodityModel(sku.getCommodityModel());//型号
        requestBody.setActivityId(goodsServerDetailBean.getActivityCommodityVo().getActivityId());//活动id
        if(TextUtils.isEmpty(sku.getCommoditySpec())){
            requestBody.setCommoditySpec("");//商品规格
        }else{
            requestBody.setCommoditySpec(sku.getCommoditySpec());//商品规格
        }
        requestBody.setMerchantName(TextUtils.isEmpty(goodsServerDetailBean.getActivityCommodityVo().getMerchantName()) ?
                "喜扣商城" : goodsServerDetailBean.getActivityCommodityVo().getMerchantName());
        requestBody.setMerchantId(goodsServerDetailBean.getActivityCommodityVo().getMerchantId());
        requestBody.setGoodsImageUrl(sku.getSkuImage());
        requestBody.setGoodsId(goodsServerDetailBean.getActivityCommodityVo().getGoodsId());//商品id

        requestBody.setSalePrice(sku.getMarketPrice());//销售价;
        requestBody.setCommodityUnit(sku.getGoodsUnit());
        mPresenter.addCartManyBuy(requestBody);
//        double totalPrice = 0.00f;
//        CartShopBean.CartListBean entity = new CartShopBean.CartListBean();
////        entity.setGoodsId(goodsServerDetailBean.getGoodsId());
////        entity.setGoodsName(goodsDetailBean.getName());
////        entity.setGoodsImage(goodsDetailBean.getImage());
//
//        entity.setSkuId(sku.getSkuId());
//        entity.setSkuNum(num);
//        entity.setSkuName(sku.getSkuName());
//        entity.setSkuImage(sku.getImage());
//        entity.setSpecMap(sku.getSpecMap());
//
//        double price = Double.parseDouble(sku.getVipPrice());
//        totalPrice = BigDecimalUtil.getMultiply(price, Double.valueOf(num));
//        entity.setUnitPrice(sku.getVipPrice());
//
//        entity.setMemo(memo);
//        List<CartShopBean.CartListBean> selectList = new ArrayList<>();
//        selectList.add(entity);
//
//        showToast("添加购物车成功");
    }


    @OnClick({R.id.rl_join_vip, R.id.ll_index, R.id.ll_kefu, R.id.tv_many_goods_add_cart})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_join_vip:
              ActivityUtils.startActivity(LoginActivity.class);
                break;
            case R.id.ll_index:
                ActivityUtils.startActivity(MainActivity.class);
                break;
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
            case R.id.tv_many_goods_add_cart:
                //加入购物车
                showDialog();
                break;
        }
    }


    /**
     * 获取分享内容
     */
    private void getShareContent(){
        if(goodsServerDetailBean != null && goodsServerDetailBean.getActivityCommodityVo() != null &&
                goodsServerDetailBean.getActivityCommodityVo().getSkuList().size() != 0){
            GoodsSkuListBean2 listBean = goodsServerDetailBean.getActivityCommodityVo().getSkuList().get(0);
            ShareRequestBody.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareRequestBody.ActivityGoodsConditionBean();
            activityGoodsConditionBean.setActivityId(activityId);
            activityGoodsConditionBean.setCommodityId(listBean.getCommodityId());
            activityGoodsConditionBean.setGoodsId(goodsId);
            activityGoodsConditionBean.setActivityGoodsId(listBean.getId());
            activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_MANY_BUY);
            mPresenter.getShareContent(MyApplication.userId, activityGoodsConditionBean, ShareType.DETAIL_MANY_BUY);
        }
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if(shareBean != null){
            DialogShareGoods dialogShare = new DialogShareGoods(mContext, shareBean);
            MyApplication.shareType = ShareType.DETAIL_MANY_BUY;
            dialogShare.setListener(() -> {
                Intent intent = new Intent(mContext, PosterActivity.class);
                intent.putExtra(PosterActivity.GOODS_NAME, goodsServerDetailBean.getActivityCommodityVo().getCommodityName());
                intent.putExtra(PosterActivity.GOODS_IMG, goodsServerDetailBean.getActivityCommodityVo().getImageList().get(0).getUrl());
                intent.putExtra(PosterActivity.GOODS_PRICE, goodsServerDetailBean.getActivityCommodityVo().getCommodityPriceOne());
                intent.putExtra(PosterActivity.GOODS_ACTIVITY_TYPE, ActivityType.ACTIVITY_MANY_BUY);
                String address = "";
                if(SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)){
                    String code = SPUtils.getInstance().getString(Constant.USER_INVITE_CODE, "");
                    address = shareBean.getParams().getUrl() + "?extcode=" + code;
                }else {
                    address = shareBean.getParams().getUrl();
                }
                intent.putExtra(PosterActivity.SHARE_ADDRESS, address);
                intent.putExtra(PosterActivity.GOODS_LINE_PRICE, goodsServerDetailBean.getActivityCommodityVo().getSalePrice());
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
        if(event.shareType == ShareType.DETAIL_MANY_BUY){
            ShareSuccessBean.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareSuccessBean.ActivityGoodsConditionBean();
            activityGoodsConditionBean.setActivityId(activityId);
            activityGoodsConditionBean.setCommodityId(commodityId);
            activityGoodsConditionBean.setGoodsId(goodsId);
            activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_MANY_BUY);
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.DETAIL_MANY_BUY, activityGoodsConditionBean);
        }
    }

    @Override
    public void onGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> model) {
        goodsServerDetailBean = model.getData();
        bindBanner(goodsServerDetailBean);
        bindRule(goodsServerDetailBean.getBaseRuleVo());
        bindGoods(goodsServerDetailBean.getActivityCommodityVo());

    }

    /**
     * 绑定商品信息
     */
    private void bindGoods(GlobalBuyerGoodsDetailBean.ActivityCommodityVoBean activityCommodityVo) {
        SkuAttribute skuAttribute2=null;
        activityId = activityCommodityVo.getActivityId();
        goodsId = activityCommodityVo.getGoodsId();
        commodityId = activityCommodityVo.getId();
        tvGoodsName.setText(activityCommodityVo.getCommodityName());
        tvShareAmount.setText("分享赚" + PriceUtil.dividePrice(activityCommodityVo.getShareAmount()));
        tvPrice.setText(String.valueOf(PriceUtil.dividePrice(activityCommodityVo.getCommodityPriceOne())));
        tvLinePrice.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(activityCommodityVo.getSalePrice()));
        tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        if(TextUtils.isEmpty(activityCommodityVo.getMerchantName())){
            tvMerchantName.setText("喜扣商城");
        }else{
            tvMerchantName.setText(activityCommodityVo.getMerchantName());
        }
        tvCategoryName.setText(activityCommodityVo.getCategoryName());
        if(goodsServerDetailBean.getActivityCommodityVo().getBrandAuthorized() == 1){
            ivBrandAuthor.setVisibility(View.VISIBLE);
        }else {
            ivBrandAuthor.setVisibility(View.GONE);
        }
        //处理数据手动添加  SpecMap() 值
        List<GoodsSkuListBean2> goodsSkuList = activityCommodityVo.getSkuList();
        for (GoodsSkuListBean2 goodsSkuListBean : goodsSkuList) {
            if(XiKouUtils.isNullOrEmpty(goodsSkuListBean.getSkuImage())){
                goodsSkuListBean.setSkuImage(activityCommodityVo.getImageList().get(0).getUrl());
            }
            if(TextUtils.isEmpty(goodsSkuListBean.getGoodsModel())){
                goodsSkuListBean.setGoodsModel("型号");
            }
            if(TextUtils.isEmpty(activityCommodityVo.getGoodsModel())){
                activityCommodityVo.setGoodsModel("型号");
            }
            SkuAttribute skuAttribute1 = new SkuAttribute(activityCommodityVo.getGoodsModel(), goodsSkuListBean.getCommodityModel());
            if(!TextUtils.isEmpty(goodsSkuListBean.getCommoditySpec())){
                if(TextUtils.isEmpty(goodsSkuListBean.getGoodsSpec())){
                    goodsSkuListBean.setGoodsSpec("规格");
                }
                if(TextUtils.isEmpty(activityCommodityVo.getGoodsSpec())){
                    activityCommodityVo.setGoodsSpec("规格");
                }
                skuAttribute2 = new SkuAttribute(activityCommodityVo.getGoodsSpec(), goodsSkuListBean.getCommoditySpec());
            }

            List<SkuAttribute> skuAttributeList = new ArrayList<>();
            skuAttributeList.add(skuAttribute1);
            if(skuAttribute2!=null){
                skuAttributeList.add(skuAttribute2);
            }
            goodsSkuListBean.setSpecMap(skuAttributeList);
        }
        if(activityCommodityVo.getStock() == 0){
            tvManyAddCart.setEnabled(false);
            tvManyAddCart.setText("已售罄");
        }else {
            if(MyApplication.switchBean != null && MyApplication.switchBean.getMoreDisCount() == 1) {
                tvManyAddCart.setEnabled(true);
            }else {
                tvManyAddCart.setEnabled(false);
            }
        }
    }

    /**
     * 绑定规则
     */
    private void bindRule(GlobalBuyerGoodsDetailBean.BaseRuleVoBean baseRuleModel) {
        if(baseRuleModel.getPostage() == 0){
            tvPost.setText("全国包邮");
            Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_poster_tips);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvPost.setCompoundDrawablePadding(3);
            tvPost.setCompoundDrawables(null, null, drawable, null);
            tvPost.setOnClickListener(v -> new PosterTipDialog(mContext).show());
        }else {
            tvPost.setText("邮费:" + PriceUtil.dividePrice(baseRuleModel.getPostage()) + "元");
        }

        tvDelivery.setText("下单后" + baseRuleModel.getDeliveryDuration() + "小时内发货");
        maxLimitNum = baseRuleModel.getMaxLimit();
        minLimitNum = baseRuleModel.getMinLimit();
//        SPUtils.getInstance().put(Constant.RATE_ONE, baseRuleModel.getRateOne());
//        SPUtils.getInstance().put(Constant.RATE_TWO, baseRuleModel.getRateTwo());
//        SPUtils.getInstance().put(Constant.RATE_THREE, baseRuleModel.getRateThree());
    }

    //购物车添加成功
    @Override
    public void onAddCartSuce(BaseModel model) {
        ToastUtils.showShortToast(mContext,"购物车添加成功");
        if(!TextUtils.isEmpty(MyApplication.userId)){
            mPresenter.getCartData(MyApplication.userId);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(MyApplication.userId)){
            mPresenter.getCartData(MyApplication.userId);
        }
        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN) && rlJoinVip != null) {
            rlJoinVip.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetCartDataSuc(BaseModel<List<ManyCartsBean>> baseModel) {
        if(baseModel != null && baseModel.getData() != null && baseModel.getData().size() != 0){
            showCartNum(true);
            int size = 0;
            for (ManyCartsBean manyCartsBean : baseModel.getData()){
                size += manyCartsBean.getList().size();
            }
            tvCartNum.setText(String.valueOf(size));
        }else {
            showCartNum(false);
        }
    }

    /**
     * 下单成功之后需要购物车重新获取数据
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void orderSuccess(OrderSuccessBean orderSuccessBean){
//        if(!TextUtils.isEmpty(MyApplication.userId)){
//            mPresenter.getCartData(MyApplication.userId);
//        }
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
//                    goodsUrl=bean.getUrl();
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
            DetailImgAdapter detailImgAdapter = new DetailImgAdapter(mContext, R.layout.item_goods_detail_img, detailImages);
            detailImgAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, RecyclerView.ViewHolder holder, int index) {
                    GlideUtil.lookBigImage(mContext, detailImages, index);
                }

                @Override
                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                    return false;
                }
            });
            rvDetail.setFocusable(false);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            linearLayoutManager.setAutoMeasureEnabled(true);
            rvDetail.setLayoutManager(linearLayoutManager);
            rvDetail.setAdapter(detailImgAdapter);
        }
    }

    /**
     * 商品详情图片的adapter
     */
    private class DetailImgAdapter extends CommonAdapter<String>{

        public DetailImgAdapter(Context context, int layoutId, List<String> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, String url, int position) {
            ImageView ivLogo = holder.getView(R.id.iv_goods_detail_img);
            ivLogo.setImageResource(R.drawable.ic_loading);
            //获取图片真正的宽高
            Glide.with(mContext).asBitmap().error(R.drawable.ic_loading)
                .load(url).into(new SimpleTarget<Bitmap>() {
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
                    float ratio = bitWidth / bitHeight;
                    // 获得屏幕宽高
                    DisplayMetrics displayMetrics =DensityUtils.getDisplayMetrics(mContext);
                    int screenWidth = displayMetrics.widthPixels - DensityUtils.dp2px(mContext,30);
                    int screenHeiht = displayMetrics.heightPixels;
                    int height=(int)(screenWidth/ratio);
                    //动态设置ImageView 高度
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ivLogo.getLayoutParams();
                    layoutParams.width=screenWidth;
                    layoutParams.height= height;
                    // ImageView 控件设置
                    ivLogo.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ivLogo.setAdjustViewBounds(true);
                    ivLogo.setLayoutParams(layoutParams);
                    ivLogo.setImageBitmap(bitmap);
                }
            });
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext,model.getMsg());
    }
}
