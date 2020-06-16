package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.CouponTotalBean;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.GlobalBuyerViewImpl;
import com.xk.mall.presenter.GlobalBuyerPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.EventBusMessage;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.adapter.GlobalBuyViewPagerAdapter;
import com.xk.mall.view.fragment.GlobalBuyChildFragment;
import com.xk.mall.view.widget.DialogShare;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoaderInterface;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ClassName GlobalBuyerActivity
 * Description 全球买手页面
 * Author 卿凯
 * Date 2019/6/21/021
 * Version V1.0
 */
public class GlobalBuyerActivity extends BaseActivity<GlobalBuyerPresenter> implements GlobalBuyerViewImpl {

    @BindView(R.id.banner_global)
    Banner banner;
    @BindView(R.id.tab_global)
    SlidingTabLayout tabGlobal;//标题栏
    @BindView(R.id.vp_global)
    ViewPager vpGlobal;

    //专场栏的Vie
    @BindView(R.id.ll_special)
    LinearLayout llSpecial;
    @BindView(R.id.ll_special_top_one)
    LinearLayout llSpecialTopOne;
    @BindView(R.id.ll_special_top_two)
    LinearLayout llSpecialTopTwo;
    @BindView(R.id.ll_special_goods_one)
    LinearLayout llSpecialGoodsOne;
    @BindView(R.id.ll_special_goods_two)
    LinearLayout llSpecialGoodsTwo;
    @BindView(R.id.ll_special_goods_three)
    LinearLayout llSpecialGoodsThree;
    @BindView(R.id.ll_special_goods_fourth)
    LinearLayout llSpecialGoodsFourth;

    @BindView(R.id.tv_special_title_one)
    TextView tvSpecialTitleOne;
    @BindView(R.id.tv_special_title_two)
    TextView tvSpecialTitleTwo;
    @BindView(R.id.img_goods_one)
    ImageView imgGoodsOne;
    @BindView(R.id.img_goods_two)
    ImageView imgGoodsTwo;
    @BindView(R.id.img_goods_three)
    ImageView imgGoodsThree;
    @BindView(R.id.img_goods_fourth)
    ImageView imgGoodsFourth;
    @BindView(R.id.tv_goods_coupons_one)
    TextView tvGoodsCouponsOne;
    @BindView(R.id.tv_goods_coupons_two)
    TextView tvGoodsCouponsTwo;
    @BindView(R.id.tv_goods_coupons_three)
    TextView tvGoodsCouponsThree;
    @BindView(R.id.tv_goods_coupons_fourth)
    TextView tvGoodsCouponsFourth;
    @BindView(R.id.tv_goods_price_one)
    TextView tvGoodsPriceOne;
    @BindView(R.id.tv_goods_price_two)
    TextView tvGoodsPriceTwo;
    @BindView(R.id.tv_goods_price_three)
    TextView tvGoodsPriceThree;
    @BindView(R.id.tv_goods_price_fourth)
    TextView tvGoodsPriceFourth;
    @BindView(R.id.rl_global_coupon)
    RelativeLayout rl_global_coupon;
    @BindView(R.id.iv_global_coupon_arrow)
    ImageView iv_global_coupon_arrow;
    @BindView(R.id.tv_global_coupon_tip)
    TextView tv_global_coupon_tip;//我的优惠券
    @BindView(R.id.ll_global_coupon_money)
    LinearLayout ll_global_coupon_money;
    @BindView(R.id.tv_global_total_coupon)
    TextView tv_global_total_coupon;//可用余额
    @BindView(R.id.tv_global_coupon_money)
    TextView tv_global_coupon_money;//吾G寄卖额度
    private boolean isClickShow;//是否显示了优惠券
    private boolean hasData;//是否获取了数据

    private String activityId = "";//活动ID


    @Override
    protected GlobalBuyerPresenter createPresenter() {
        return new GlobalBuyerPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_global_buyer;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("全球买手");
        setRightDrawable(R.mipmap.lc_shared_white);
        setStatusBar(getResources().getColor(R.color.color_red));
        setDarkStatusIcon(false);
        setOnRightIconClickListener(v -> {
            getShareContent();
        });
        bindSpecialClickEvent();
    }

    @Override
    protected void initData() {
        if (!XiKouUtils.isNullOrEmpty(MyApplication.userId)) {
            mPresenter.getCouponTotal(MyApplication.userId);
        }
        mPresenter.getActiveSectionData(ActivityType.ACTIVITY_GLOBAL_BUYER);
        int pointHeight = ScreenUtils.getScreenHeight() * 3 / 5;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, pointHeight, 0, 0);
        rl_global_coupon.setLayoutParams(layoutParams);
        rl_global_coupon.setOnClickListener(v -> checkAndShow());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!XiKouUtils.isNullOrEmpty(MyApplication.userId)) {
            rl_global_coupon.setVisibility(View.VISIBLE);
        } else {
            rl_global_coupon.setVisibility(View.GONE);
        }
    }

    @Keep
    @LoginFilter
    private void checkAndShow() {
        isClickShow = true;
        if (hasData) {
            if (ll_global_coupon_money.getVisibility() == View.VISIBLE) {
                ll_global_coupon_money.setVisibility(View.GONE);
                iv_global_coupon_arrow.setImageResource(R.drawable.ic_global_right);
                tv_global_coupon_tip.setVisibility(View.VISIBLE);
            } else {
                ll_global_coupon_money.setVisibility(View.VISIBLE);
                iv_global_coupon_arrow.setImageResource(R.drawable.ic_global_left);
                tv_global_coupon_tip.setVisibility(View.GONE);
            }
        } else {
            mPresenter.getCouponTotal(MyApplication.userId);
        }
    }

    @Override
    public void onGetDataSuccess(BaseModel<CouponTotalBean> model) {
        if (model != null && model.getData() != null) {
            if (isClickShow) {
                ll_global_coupon_money.setVisibility(View.VISIBLE);
                tv_global_coupon_tip.setVisibility(View.GONE);
                iv_global_coupon_arrow.setImageResource(R.drawable.ic_wug_left);
            }
            isClickShow = false;
            if (model.getData().getBuyGiftAmount() < 0) {
                model.getData().setBuyGiftAmount(0);
            }
            if (model.getData().getCouponUsableSumNum() < 0) {
                model.getData().setCouponUsableSumNum(0);
            }
            hasData = true;
            tv_global_total_coupon.setText(PriceUtil.divideCoupon(model.getData().getCouponUsableSumNum()) + "券");
            tv_global_coupon_money.setText(PriceUtil.divideCoupon(model.getData().getBuyGiftAmount()) + "券");
        }
    }

    @Override
    public void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model) {
        bindBanner(model.getData().getBannerList());
        bindCategorys(model.getData().getSectionList());
        fetchSpecialData(model.getData().getSectionList());
    }

    /**
     * 获取专场对应的数据
     */
    private void fetchSpecialData(List<ActiveSectionBean.SectionListBean> childSectionList) {
        if (childSectionList == null || childSectionList.size() < 2) {
            llSpecial.setVisibility(View.GONE);
        } else {
            specialGoodsBeans.clear();
            categoryInfos.clear();
            orderCategoryNames.clear();
            int i = 0;
            for (ActiveSectionBean.SectionListBean mcategory : childSectionList) {
                if (i < 2) {
                    i++;
                    if (i > 1){
                        mcategory.setCategoryName("四折精品");
                    }
                    mPresenter.getActiveSectionGoods(mcategory.getCategoryName(), mcategory.getId(), ActivityType.ACTIVITY_GLOBAL_BUYER, MyApplication.userId, 1, 2);
                    orderCategoryNames.add(mcategory.getCategoryName());
                } else {
                    return;
                }
            }
        }
    }

    //用来确定专场的显示顺序
    private ArrayList<String> orderCategoryNames = new ArrayList<>();

    /**
     * 绑定该页面的banner
     *
     * @param bannerList
     */
    private void bindBanner(List<BannerBean> bannerList) {
        banner.setImageLoader(new ImageLoaderInterface(){
            @Override
            public void displayImage(Context context, Object path, View imageView) {
                //Glide 加载图片简单用法
                Glide.with(context).load(path).placeholder(R.drawable.ic_loading).error(R.drawable.ic_loading)
                        .centerCrop().into((ImageView) imageView);
            }

            @Override
        public View createImageView(Context context) {
            RoundedImageView roundedImg = new RoundedImageView(context, null);
            roundedImg.setCornerRadius(DensityUtils.dp2px(context, 4));
            return roundedImg;
        }
    });
        //设置图片集合
        List<String> images = new ArrayList<>();
        BannerBean center = null;//中间图片地址
        if (bannerList != null && bannerList.size() > 0) {
            for (int i = 0; i < bannerList.size(); i++) {
                if(bannerList.get(i).getPosition() == 1){
                    images.add(bannerList.get(i).getImageUrl());
                }
            }

            for(int i = 0; i < bannerList.size(); i++){
                if(bannerList.get(i).getPosition() == 2){
                    center = bannerList.get(i);
                    break;
                }
            }
        }


        banner.setImages(images);
        //设置nearBanner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置指示器
        banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
        //设置轮播时间
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

        banner.setOnBannerListener(position -> {
            String skipType = bannerList.get(position).getSkipType();
            String targetParams = bannerList.get(position).getTargetParams();
            String webUrl = bannerList.get(position).getTargetUrl1();
            XiKouUtils.parseBannner(mContext,skipType,targetParams,webUrl);
        });

    }


    /**
     * 绑定种类分类页面的数据
     */
    private void bindCategorys(List<ActiveSectionBean.SectionListBean> childSectionList) {
        if(childSectionList == null || childSectionList.size() == 0){
            tabGlobal.setVisibility(View.GONE);
            vpGlobal.setVisibility(View.GONE);
        }else {
            List<String> category = new ArrayList<>();
            int size=childSectionList.size();
            List<Fragment> fragments = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                if (i > 1) {
                    ActiveSectionBean.SectionListBean mcategory = childSectionList.get(i);
                    category.add(mcategory.getCategoryName());
                    fragments.add(GlobalBuyChildFragment.getInstance(mcategory.getId(), mcategory.getCategoryName()));
                }
            }
            if (fragments.size() <= 4) {
                tabGlobal.setTabSpaceEqual(true);
            } else {
                tabGlobal.setTabSpaceEqual(false);
            }
            GlobalBuyViewPagerAdapter globalBuyViewPagerAdapter = new GlobalBuyViewPagerAdapter(getSupportFragmentManager(), fragments, category);
            vpGlobal.setAdapter(globalBuyViewPagerAdapter);
            vpGlobal.setOffscreenPageLimit(category.size());
            tabGlobal.setViewPager(vpGlobal);
            tabGlobal.setCurrentTab(0);
        }
    }

    /**
     * 获取分享内容
     */
    private void getShareContent(){
        if(!TextUtils.isEmpty(activityId)){
            mPresenter.getShareContent(MyApplication.userId, activityId, ShareType.ACTIVITY_GLOBAL);
        }
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if(shareBean != null){
            DialogShare dialogShare = new DialogShare(mContext, shareBean);
            MyApplication.shareType = ShareType.ACTIVITY_GLOBAL;
            dialogShare.show();
        }
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event){
        if(event.shareType == ShareType.ACTIVITY_GLOBAL){
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.ACTIVITY_GLOBAL);
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void eventGetActivityId(EventBusMessage eventBusMessage){
        if(!TextUtils.isEmpty(eventBusMessage.getMessage())){
            activityId = eventBusMessage.getMessage();
        }
    }

    /**
     * 请求得到分类对应的商品后的回调接口，用请求得到的数据去设置专场view
     *
     * @param categoryName
     * @param categoryId
     * @param model
     */
    @Override
    public void onGetActiveSectionGoodsSuccess(String categoryName,String categoryId,BaseModel<ActiveSectionGoodsPageBean> model) {
        if(model != null && categoryInfos.size() < 2){
            if(categoryName!=null && categoryName.equals(orderCategoryNames.get(0))){
                bindSpecialOne(categoryName,categoryId,model);
            }else {
                bindSpecialTwo(categoryName,categoryId,model);
            }
        }
    }

    private  ArrayList<ActiveSectionGoodsBean> specialGoodsBeans=new ArrayList<>();    //存放专场的四个商品，用于点击事件
    private ArrayList<CategoryInfo> categoryInfos=new ArrayList<>();  //存放专场的分类名和分类id

    /**
     * 绑定专场的第一个类别数据
     *
     * @param categoryName
     * @param model
     */
    private void bindSpecialOne(String categoryName,String categoryId,BaseModel<ActiveSectionGoodsPageBean> model){
        tvSpecialTitleOne.setText(categoryName);
        categoryInfos.add(0,CategoryInfo.build(categoryName,categoryId));
        if(model != null && model.getData().getResult() != null  &&  model.getData().getResult().size() > 1){
            List<ActiveSectionGoodsBean> goodsItems=model.getData().getResult();
            ActiveSectionGoodsBean goodsOneBean=goodsItems.get(0);
            GlideUtil.show(mContext, goodsOneBean.getGoodsImageUrl(),  imgGoodsOne);
            tvGoodsCouponsOne.setText(  PriceUtil.divideCoupon(goodsOneBean.getCouponValue())+ "券");
            tvGoodsPriceOne.setText( mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(goodsOneBean.getCommodityPrice()));
            ActiveSectionGoodsBean goodsTwoBean=goodsItems.get(1);
            GlideUtil.show(mContext, goodsTwoBean.getGoodsImageUrl(),  imgGoodsTwo);
            tvGoodsCouponsTwo.setText(  PriceUtil.divideCoupon(goodsTwoBean.getCouponValue())+ "券");
            tvGoodsPriceTwo.setText( mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(goodsTwoBean.getCommodityPrice()));
            //保证点击事件的数据顺序正确性,因为
            specialGoodsBeans.add(0,goodsOneBean);
            specialGoodsBeans.add(1,goodsTwoBean);
        }else{
            llSpecial.setVisibility(View.GONE);
        }
    }

    /**
     * 绑定专场的第二个类别数据
     * @param categoryName
     * @param model
     */
    private void bindSpecialTwo(String categoryName,String categoryId,BaseModel<ActiveSectionGoodsPageBean> model){
        tvSpecialTitleTwo.setText(categoryName);
        categoryInfos.add(CategoryInfo.build(categoryName,categoryId));
        if(model != null && model.getData().getResult() != null  &&  model.getData().getResult().size() > 1){
            List<ActiveSectionGoodsBean> goodsItems=model.getData().getResult();
            ActiveSectionGoodsBean goodsOneBean=goodsItems.get(0);
            GlideUtil.show(mContext, goodsOneBean.getGoodsImageUrl(),  imgGoodsThree);
            tvGoodsCouponsThree.setText(  PriceUtil.divideCoupon(goodsOneBean.getCouponValue())+ "券");
            tvGoodsPriceThree.setText( mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(goodsOneBean.getCommodityPrice()));
            ActiveSectionGoodsBean goodsTwoBean=goodsItems.get(1);
            GlideUtil.show(mContext, goodsTwoBean.getGoodsImageUrl(),  imgGoodsFourth);
            tvGoodsCouponsFourth.setText(  PriceUtil.divideCoupon(goodsTwoBean.getCouponValue())+ "券");
            tvGoodsPriceFourth.setText( mContext.getResources().getString(R.string.money) + PriceUtil.dividePrice(goodsTwoBean.getCommodityPrice()));
            specialGoodsBeans.add(goodsOneBean);
            specialGoodsBeans.add(goodsTwoBean);
        }else{
//            llSpecial.setVisibility(View.GONE);
        }
    }


    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        //隐藏所有布局
        llSpecial.setVisibility(View.GONE);
        tabGlobal.setVisibility(View.GONE);
        vpGlobal .setVisibility(View.GONE);
        ToastUtils.showShort("请求出错");
    }

    /**
     * 设定专场监听点击事件
     */
    private void bindSpecialClickEvent() {
        llSpecialTopOne.setOnClickListener(clickListener);
        llSpecialTopTwo.setOnClickListener(clickListener);
        llSpecialGoodsOne.setOnClickListener(clickListener);
        llSpecialGoodsTwo.setOnClickListener(clickListener);
        llSpecialGoodsThree.setOnClickListener(clickListener);
        llSpecialGoodsFourth.setOnClickListener(clickListener);
    }

    //处理专场的监听事件
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ll_special_top_one:
                case R.id.ll_special_goods_one:
                case R.id.ll_special_goods_two:
                    startGlobalBuyerChildActivity(1);
                    break;
                case R.id.ll_special_top_two:
                case R.id.ll_special_goods_three:
                case R.id.ll_special_goods_fourth:
                    startGlobalBuyerChildActivity(2);
                    break;

            }
        }
    };

    /**
     * 跳转到全球买手商品详情页
     *
     * @param index
     */
    private void startGlobalBuyerGoodsDetailActivity(int index) {
        int size = specialGoodsBeans.size();
        if (index <= size && index > 0) {
            ActiveSectionGoodsBean sectionGoodsBean = specialGoodsBeans.get(index - 1);
            Intent intent = new Intent(mContext, GlobalBuyerGoodsDetailActivity.class);
            intent.putExtra(GlobalBuyerGoodsDetailActivity.ACTIVITY_GOODS_ID, sectionGoodsBean.getActivityGoodsId());
            ActivityUtils.startActivity(intent);
        }
    }

    /**
     * 跳转到全球买手分类子页
     *
     * @param index
     */
    private void startGlobalBuyerChildActivity(int index) {
        int size = categoryInfos.size();
        if (index <= size && index > 0) {
            CategoryInfo categoryInfo = categoryInfos.get(index - 1);
            Intent intent = new Intent(mContext, GlobalBuyerChildActivity.class);
            intent.putExtra(GlobalBuyerChildActivity.ACTIVITY_ID, ActivityType.ACTIVITY_GLOBAL_BUYER);
            intent.putExtra(GlobalBuyerChildActivity.CATEGORY_ID, categoryInfo.categoryId);
            intent.putExtra(GlobalBuyerChildActivity.TITLE, categoryInfo.categoryName);
            ActivityUtils.startActivity(intent);
        }
    }

    /**
     * 记录专场的商品分类名跟分类id
     */
    private static class CategoryInfo {
        public String categoryName;
        public String categoryId;

        public static CategoryInfo build(String categoryName, String categoryId) {
            CategoryInfo obj = new CategoryInfo();
            obj.categoryName = categoryName;
            obj.categoryId = categoryId;
            return obj;
        }
    }

}
