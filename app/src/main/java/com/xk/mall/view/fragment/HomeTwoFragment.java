package com.xk.mall.view.fragment;


import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.ActiveSectionBean;
import com.xk.mall.model.entity.ActiveSectionGoodsPageBean;
import com.xk.mall.model.entity.ActivityRoundBean;
import com.xk.mall.model.entity.BannerBean;
import com.xk.mall.model.entity.HomeBean;
import com.xk.mall.model.entity.HomeFuncationBean;
import com.xk.mall.model.entity.HomeMessageBean;
import com.xk.mall.model.eventbean.HomeMessageReadChangeBean;
import com.xk.mall.model.impl.HomeDataImpl;
import com.xk.mall.presenter.HomePresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.GridSpacingItemDecoration;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.activity.CutGoodsDetailActivity;
import com.xk.mall.view.activity.CutMainActivity;
import com.xk.mall.view.activity.GlobalBuyerActivity;
import com.xk.mall.view.activity.GlobalBuyerGoodsDetailActivity;
import com.xk.mall.view.activity.HomeMessageActivity;
import com.xk.mall.view.activity.HomeSearchActivity;
import com.xk.mall.view.activity.LoginActivity;
import com.xk.mall.view.activity.MakeTaskActivity;
import com.xk.mall.view.activity.ManyBuyActivity;
import com.xk.mall.view.activity.ManyGoodsDetailActivity;
import com.xk.mall.view.activity.NewPersonActivity;
import com.xk.mall.view.activity.NewPersonGoodsDetailActivity;
import com.xk.mall.view.activity.NewProductActivity;
import com.xk.mall.view.activity.ProtocolWebViewActivity;
import com.xk.mall.view.activity.ScanActivity;
import com.xk.mall.view.activity.WebViewActivity;
import com.xk.mall.view.activity.WuGGoodsDetailActivity;
import com.xk.mall.view.activity.WuGMainActivity;
import com.xk.mall.view.activity.ZeroGoodsDetailActivity;
import com.xk.mall.view.activity.ZeroMainActivity;
import com.xk.mall.view.adapter.HomeFunctionAdapter;
import com.xk.mall.view.adapter.HomeGlobalAdapter;
import com.xk.mall.view.adapter.HomeKanLiDeAdapter;
import com.xk.mall.view.adapter.HomeMoreBuyAdapter;
import com.xk.mall.view.adapter.HomeWuGAdapter;
import com.xk.mall.view.adapter.HomeZeroAdapter;
import com.xk.mall.view.widget.CircleProgressBar;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.XKBannerTwo;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import q.rorbin.badgeview.QBadgeView;


/**
 * @ClassName HomeFragment
 * @Description 首页页面
 * @Author 卿凯
 * @Date 2019/6/4/004
 * @Version V1.0
 */
public class HomeTwoFragment extends BaseFragment<HomePresenter> implements HomeDataImpl, EasyPermissions.PermissionCallbacks {
    private static final String TAG = "HomeFragment";
    private static final int REQUEST_CODE = 10000;
    /**`
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 1001;
    @BindView(R.id.stateView)
    MultiStateView stateView;
    @BindView(R.id.banner_home)
    XKBannerTwo bannerHome;
    @BindView(R.id.img_message)
    ImageView imgMessage;
    @BindView(R.id.et_near_search)
    TextView etSearch;
    @BindView(R.id.iv_cart)
    ImageView imgCart;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recycler_top)
    RecyclerView recyclerTop;
//    @BindView(R.id.app_bar)
//    AppBarLayout appBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.tv_wu_title)
    TextView tvWuTitle;
    @BindView(R.id.tv_wug_title_des)
    TextView tvWugTitleDes;
    @BindView(R.id.rl_wug)
    RelativeLayout rlWug;
    @BindView(R.id.iv_wug_main)
    ImageView ivWugMain;
    @BindView(R.id.recycleviewWug)
    RecyclerView recycleviewWug;
    @BindView(R.id.recycleview_zero_buy)
    RecyclerView recyclerViewZeroBuy;
    @BindView(R.id.tv_juli_over)
    TextView tvJuliOver;
    @BindView(R.id.cv_countdown)
    CountdownView cvCountdown;
    @BindView(R.id.rl_zero_pai)
    LinearLayout rlZeroPai;
    @BindView(R.id.tv_zero_title)
    TextView tvZeroTitle;//0元抢标题
    @BindView(R.id.tv_kan_title)
    TextView tvKanTitle;
    @BindView(R.id.tv_kan_title_des)
    TextView tvKanTitleDes;
    @BindView(R.id.rl_kan_lide)
    RelativeLayout rlKanLide;
    @BindView(R.id.iv_kan_main)
    ImageView ivKanMain;
    @BindView(R.id.recycleviewKanLi)
    RecyclerView recycleviewKanLi;
    @BindView(R.id.img_share)
    ImageView imgShare;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_des)
    TextView tvTitleDes;
    @BindView(R.id.rl_title_content)
    RelativeLayout rlTitleContent;
    @BindView(R.id.recyclerview_more_buy)
    RecyclerView recyclerviewMoreBuy;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.scrollView)
    NestedScrollView nestedScrollView;
    //全球买手
    @BindView(R.id.rl_global_title_content)
     RelativeLayout rlGlobalTtileContent;
    @BindView(R.id.tv_global_title)
     TextView tvGlobalTitle;
    @BindView(R.id.tv_global_title_des)
     TextView tvGlobalTitleDes;
    @BindView(R.id.recycleViewGlobal)
     RecyclerView recyclerViewGlobal;
    QBadgeView qBadgeView;
    @BindView(R.id.rl_jia_one)
     RelativeLayout rlJiaOne;

    HomeMessageBean homeMessageBean;//首页取到的消息

    private HomeWuGAdapter wuGAdapter;
    private HomeKanLiDeAdapter kanLiDeAdapter;
    private HomeMoreBuyAdapter moreBuyAdapter;
    private HomeGlobalAdapter homeGlobalAdapter;
    private HomeZeroAdapter zeroAdapter;

    private List<HomeBean.HomeBuyGiftActivityModelBean.HomeBuyGiftCommodityModelsBean> wuGListData = new ArrayList<>();
    private List<HomeBean.HomeBargainActivityModelBean.HomeBargainCommodityModelsBean> kanListData = new ArrayList<>();
    private List<HomeBean.HomeDiscountActivityModelBean.HomeDiscountCommodityModelsBean> moreBuyListData = new ArrayList<>();
    private List<HomeBean.HomePageGlobalBuyerActivityModelBean.HomeGlobalBuyerCommodityModelsBean> globalLisData=new ArrayList<>();
    private List<HomeBean.HomeAuctionActivityModelBean.HomeAuctionCommodityModels> zeroListData = new ArrayList<>();

    private List<BannerBean> listBanner = new ArrayList<>();

    public static HomeTwoFragment getInstance() {
        HomeTwoFragment sf = new HomeTwoFragment();
        return sf;
    }


    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        ImmersionBar.setTitleBar(this, toolBar);
        initGoodsData();
        switchAppBar();
        initFunctionData();
        Button btnReplay = stateView.findViewById(R.id.btn_replay);
        btnReplay.setOnClickListener(v -> getHomeData(MyApplication.userId));

        tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(mContext,ManyCartTwoActivity.class);
//                startActivity(intent);
//                    MoreWindow moreWindow = new MoreWindow(getActivity());
//                    moreWindow.showMoreWindow(stateView, 0);
            }
        });
    }

    @Override
    protected void loadLazyData() {
        getHomeData(MyApplication.userId);
        Button btnReplay = stateView.findViewById(R.id.btn_replay);
        CircleProgressBar pbLoading = stateView.findViewById(R.id.pb_header_loading);
        ValueAnimator animator = ValueAnimator.ofInt(0, 100).setDuration(2000);
        animator.addUpdateListener(valueAnimator -> pbLoading.setProgress((int) valueAnimator.getAnimatedValue()));
        animator.start();
        btnReplay.setOnClickListener(v -> getHomeData(MyApplication.userId));
        mRefreshLayout.setOnRefreshListener(refreshLayout -> {
            listBanner.clear();
            wuGListData.clear();
            kanListData.clear();
            moreBuyListData.clear();
            globalLisData.clear();
            zeroListData.clear();
            getHomeData(MyApplication.userId);
        });
    }

    //onActivityResult方法中接收扫描结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                String result = data.getStringExtra(Constant.SCAN_RESULT);
                //https://m.luluxk.com/login.html?code=W2A9TJ
                Log.e(TAG, "onActivityResult: " + result);

                if (result != null && result.contains("luluxk")) {
                    if(result.contains("shareDetail")){//分享商品详情，扫码进入商品详情
                        parseUrl(result);
                    }else {
                        int start = result.indexOf("?");
                        String subTemp = result.substring(start + 1);
                        if (subTemp.contains(Constant.BASE_CODE_KEY)) {//code 注册邀请码
                            String code = subTemp.substring(8);
                            if (!SPUtils.getInstance().getBoolean(Constant.IS_LOGIN)) {
                                if (!TextUtils.isEmpty(code)) {
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    intent.putExtra(LoginActivity.IS_SHOW_CODE, true);
                                    intent.putExtra(LoginActivity.INVITE_CODE, code);
                                    ActivityUtils.startActivity(intent);
                                } else {
                                    ToastUtils.showShortToast(mContext, "邀请码为空");
                                }
                            } else {
                                ToastUtils.showShortToast(mContext, "您已登录并绑定邀请码");
                            }
                        }else if(RegexUtils.isURL(result) && result.contains("luluxk")){
                            Intent intent = new Intent(mContext, WebViewActivity.class);
                            intent.putExtra(Constant.INTENT_URL, result);
                            ActivityUtils.startActivity(intent);
                        }
                    }
                } else {//跳系统网页
                    if (result != null) {
                        if (result.contains("https://") || result.contains("http://")) {
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(result);
                            intent.setData(content_url);
                            startActivity(intent);
                        } else {
                            ToastUtils.showShortToast(mContext, "解析二维码失败");
                        }
                    } else {
                        ToastUtils.showShortToast(mContext, "解析二维码失败");
                    }
                }
            } else {
                ToastUtils.showShortToast(mContext, "解析二维码失败");
            }
        }
    }

    /**
     * 解析分享的地址，并跳转也难
     * @param url 分享地址
     */
    private void parseUrl(String url){
        if(XiKouUtils.isNullOrEmpty(url)){
            return;
        }
        Intent intent = new Intent();
        String activityGoodsId = "";
        // 匹配规则
        String reg = "/(.*?)\\?";
        Pattern pattern = Pattern.compile(reg);
        if(url.contains("ZeroBuyDetail")){//0元抢
            int index = url.indexOf("ZeroBuyDetail");
            url = url.substring(index + "ZeroBuyDetail".length());
            intent.setComponent(new ComponentName(mContext, ZeroGoodsDetailActivity.class));
        }else if(url.contains("myGDetail")){//吾G
            int index = url.indexOf("myGDetail");
            url = url.substring(index + "myGDetail".length());
            intent.setComponent(new ComponentName(mContext, WuGGoodsDetailActivity.class));
        }else if(url.contains("BargainBuyDetail")){//喜利得
            int index = url.indexOf("BargainBuyDetail");
            url = url.substring(index + "BargainBuyDetail".length());
            intent.setComponent(new ComponentName(mContext, CutGoodsDetailActivity.class));
        }else if(url.contains("GlobalBuyDetail")){//全球买手
            int index = url.indexOf("GlobalBuyDetail");
            url = url.substring(index + "GlobalBuyDetail".length());
            intent.setComponent(new ComponentName(mContext, GlobalBuyerGoodsDetailActivity.class));
        }else if(url.contains("DiscountBuyDetail")){//多买多折
            int index = url.indexOf("DiscountBuyDetail");
            url = url.substring(index + "DiscountBuyDetail".length());
            intent.setComponent(new ComponentName(mContext, ManyGoodsDetailActivity.class));
        }else if(url.contains("newPersonBuyDetail")){//新人专区
            int index = url.indexOf("newPersonBuyDetail");
            url = url.substring(index + "newPersonBuyDetail".length());
            intent.setComponent(new ComponentName(mContext, NewPersonGoodsDetailActivity.class));
        }
        // 内容 与 匹配规则 的测试
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()){
            // 不包含前后的两个字符
            activityGoodsId = matcher.group(1);
            LogUtils.e("活动商品ID:" + activityGoodsId);
            intent.putExtra("activity_goods_id", activityGoodsId);
        }
        ActivityUtils.startActivity(intent);
    }


    //初始化banner
    private void initBanner(List<BannerBean> listBanner) {
        List<String> images = new ArrayList<>();
        if (listBanner != null && listBanner.size() > 0) {
            for (int i = 0; i < listBanner.size(); i++) {
                images.add(listBanner.get(i).getImageUrl());
            }
        }
        //设置图片加载器
        bannerHome.setImageLoader(new GlideImageLoader());
        //设置图片集合

//        images.add(R.mipmap.banner01);
//        images.add(R.mipmap.banner02);
//        images.add(R.mipmap.banner03);
//        images.add(R.mipmap.banner04);
//        images.add(R.mipmap.banner05);
        bannerHome.setImages(images);
        //设置nearBanner动画效果
        bannerHome.setBannerAnimation(Transformer.Default);
        //设置自动轮播，默认为true
        bannerHome.isAutoPlay(true);
        //设置指示器
        bannerHome.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
        //设置轮播时间
        bannerHome.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        bannerHome.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if(listBanner == null || listBanner.size() < position){
                    return;
                }
                String skipType = listBanner.get(position).getSkipType();
                String targetParams = listBanner.get(position).getTargetParams();
                String webUrl = listBanner.get(position).getTargetUrl1();
                XiKouUtils.parseBannner(mContext, skipType, targetParams, webUrl);
            }

        });
        bannerHome.start();

    }

    //初始化数据
    private void initFunctionData() {
        recyclerTop.setLayoutManager(new GridLayoutManager(mContext, 4));
        List<HomeFuncationBean> listData = new ArrayList<>();
        listData.add(new HomeFuncationBean(R.mipmap.home_ic_many, "多买多折"));
        listData.add(new HomeFuncationBean(R.mipmap.home_ic_wu_g, "吾G购"));
        listData.add(new HomeFuncationBean(R.mipmap.home_ic_global, "全球买手"));
        listData.add(new HomeFuncationBean(R.mipmap.home_ic_cut, "喜立得"));
        listData.add(new HomeFuncationBean(R.mipmap.home_ic_zero, "0元抢"));
        listData.add(new HomeFuncationBean(R.mipmap.home_ic_new_person, "新人专区"));
        listData.add(new HomeFuncationBean(R.mipmap.home_ic_new_product, "新品价到"));
        listData.add(new HomeFuncationBean(R.mipmap.home_ic_task_center, "任务中心"));
        HomeFunctionAdapter homeFunctionAdapter = new HomeFunctionAdapter(mContext, listData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if (listData.get(position).getTitle().equals("吾G购")) {
                    goToWuG();
                } else if (listData.get(position).getTitle().equals("全球买手")) {
                    ActivityUtils.startActivity(GlobalBuyerActivity.class);
                } else if (listData.get(position).getTitle().equals("喜立得")) {
                    ActivityUtils.startActivity(CutMainActivity.class);
                } else if (listData.get(position).getTitle().equals("任务中心")) {
                    goToTask();
                }else if (listData.get(position).getTitle().equals("多买多折")) {
                    ActivityUtils.startActivity(ManyBuyActivity.class);
                }else if (listData.get(position).getTitle().equals("0元抢")) {
                    if(zeroListData == null || zeroListData.size() == 0){
                        Logger.e("首页获取0元拍轮次");
                        mPresenter.getActivityType(ActivityType.ACTIVITY_ZERO);
                    }else {
                        ActivityUtils.startActivity(ZeroMainActivity.class);
                    }
                }else if (listData.get(position).getTitle().equals("新人专区")) {
//                    ActivityUtils.startActivity(NewPersonActivity.class);
                    mPresenter.getActiveSectionData(ActivityType.ACTIVITY_NEW_PERSON);
                }else if (listData.get(position).getTitle().equals("新品价到")) {
                    ActivityUtils.startActivity(NewProductActivity.class);
                }
            }
        });

        recyclerTop.setAdapter(homeFunctionAdapter);
    }

    @Keep
    @LoginFilter
    private void goToWuG(){
        ActivityUtils.startActivity(WuGMainActivity.class);
    }

    private void getHomeData(String userId) {
        mPresenter.getHomeGoodsData();
        mPresenter.getBannnerData(1, 1);
        mPresenter.getBannnerData(1, 2);
        if (!TextUtils.isEmpty(userId)) {
            mPresenter.getUnreadMessage(userId);
        }
    }

    private void initGoodsData() {
        recycleviewWug.setNestedScrollingEnabled(false);
        recycleviewWug.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        wuGAdapter = new HomeWuGAdapter(mContext, wuGListData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if(position < wuGListData.size()){
                    //吾G 商品详情
                    HomeBean.HomeBuyGiftActivityModelBean.HomeBuyGiftCommodityModelsBean wuGGoods = wuGListData.get(position);
                    Intent intent = new Intent(mContext, WuGGoodsDetailActivity.class);
                    intent.putExtra(WuGGoodsDetailActivity.ACTIVITY_GOODS_ID, wuGGoods.getId());
                    ActivityUtils.startActivity(intent);
                }else {
                    Intent toWuGMain=new Intent(mContext,WuGMainActivity.class);
                    ActivityUtils.startActivity(toWuGMain);
                }

            }
        });
        recycleviewWug.setAdapter(wuGAdapter);

        recycleviewKanLi.setNestedScrollingEnabled(false);
        recycleviewKanLi.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        kanLiDeAdapter = new HomeKanLiDeAdapter(mContext, kanListData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if(position < kanListData.size()){
                    //点击进入喜立得商品详情
                    Intent intent = new Intent(mContext, CutGoodsDetailActivity.class);
                    HomeBean.HomeBargainActivityModelBean.HomeBargainCommodityModelsBean kanModelsBean = kanListData.get(position);
                    intent.putExtra(CutGoodsDetailActivity.ACTIVITY_GOODS_ID, kanModelsBean.getId());
                    ActivityUtils.startActivity(intent);
                }else{
                    Intent cutMain = new Intent(mContext, CutMainActivity.class);
                    startActivity(cutMain);
                }
            }
        });
        recycleviewKanLi.setAdapter(kanLiDeAdapter);


        recyclerViewZeroBuy.setNestedScrollingEnabled(false);
        recyclerViewZeroBuy.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        zeroAdapter = new HomeZeroAdapter(mContext, zeroListData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if(position < zeroListData.size()){
                    //点击进入喜立得商品详情
                    Intent intent = new Intent(mContext, ZeroGoodsDetailActivity.class);
                    HomeBean.HomeAuctionActivityModelBean.HomeAuctionCommodityModels kanModelsBean = zeroListData.get(position);
                    intent.putExtra(CutGoodsDetailActivity.ACTIVITY_GOODS_ID, kanModelsBean.getId());
                    ActivityUtils.startActivity(intent);
                }else{
                    Intent cutMain = new Intent(mContext, ZeroMainActivity.class);
                    startActivity(cutMain);
                }
            }
        });
        recyclerViewZeroBuy.setAdapter(zeroAdapter);

        recyclerviewMoreBuy.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerviewMoreBuy.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        moreBuyAdapter = new HomeMoreBuyAdapter(mContext, moreBuyListData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                //点击进入多买多折商品详情页面
                Intent intent = new Intent(mContext, ManyGoodsDetailActivity.class);
                HomeBean.HomeDiscountActivityModelBean.HomeDiscountCommodityModelsBean manyBean = moreBuyListData.get(position);
                intent.putExtra(ManyGoodsDetailActivity.ACTIVITY_GOODS_ID, manyBean.getId());
                ActivityUtils.startActivity(intent);
            }
        });
        recyclerviewMoreBuy.setAdapter(moreBuyAdapter);

        //全球买手
        recyclerViewGlobal.setNestedScrollingEnabled(false);

        recyclerViewGlobal.setLayoutManager(new GridLayoutManager(mContext, 2));
        recyclerViewGlobal.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        homeGlobalAdapter = new HomeGlobalAdapter(mContext, globalLisData, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if(position < globalLisData.size()){
                    Intent intent=new Intent(mContext, GlobalBuyerGoodsDetailActivity.class);
                    intent.putExtra(GlobalBuyerGoodsDetailActivity.ACTIVITY_GOODS_ID, globalLisData.get(position).getId());
                    ActivityUtils.startActivity(intent);
                }else {
                    Intent globalMain=new Intent(mContext, GlobalBuyerActivity.class);
                    ActivityUtils.startActivity(globalMain);
                }
            }
        });
        recyclerViewGlobal.setAdapter(homeGlobalAdapter);

    }

    private void switchAppBar() {
//        appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(AppBarLayout appBarLayout, State state) {
//                if (state == State.EXPANDED) {
//                    //展开状态
//                    etSearch.setBackgroundResource(R.drawable.bg_home_search);
//                    imgMessage.setImageResource(R.mipmap.home_ic_message);
//                    imgCart.setImageResource(R.drawable.ic_scan);
//                } else if (state == State.COLLAPSED) {
//                    //折叠状态
//                    etSearch.setBackgroundResource(R.drawable.bg_home_search_black);
//                    imgMessage.setImageResource(R.mipmap.home_ic_message_black);
//                    imgCart.setImageResource(R.drawable.ic_scan_gray);
//
//                } else {
//                    //中间状态
//                    etSearch.setBackgroundResource(R.drawable.bg_home_search_black);
//                    imgMessage.setImageResource(R.mipmap.home_ic_message_black);
//                    imgCart.setImageResource(R.drawable.ic_scan_gray);
//                }
//            }
//        });
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (nestedScrollView, i, y, i2, i3) -> {
            if (y <= 0) {
                //设置透明
                etSearch.setBackgroundResource(R.drawable.bg_home_search);
                imgMessage.setImageResource(R.mipmap.home_ic_message);
                imgCart.setImageResource(R.drawable.ic_scan);
                toolBar.setBackgroundColor(Color.TRANSPARENT);

            } else if (y > 0 && y <= 260) {
                etSearch.setBackgroundResource(R.drawable.bg_home_search_black);
                imgMessage.setImageResource(R.mipmap.home_ic_message_black);
                imgCart.setImageResource(R.drawable.ic_scan_gray);
                float scale = (float) y / 260;
                float alpha = (255 * scale);
                // 只是layout背景透明(仿知乎滑动效果)白色透明
                toolBar.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            } else {
                etSearch.setBackgroundResource(R.drawable.bg_home_search_black);
                imgMessage.setImageResource(R.mipmap.home_ic_message_black);
                imgCart.setImageResource(R.drawable.ic_scan_gray);
                toolBar.setBackgroundColor(Color.WHITE);
            }
        });
    }


    //获取未读消息
    @Override
    public void onGetUnreadMessSuc(BaseModel<HomeMessageBean> model) {
        homeMessageBean = model.getData();
        if (homeMessageBean.getTotalUnreadNum() != 0) {
            //显示红点
            if(qBadgeView == null){
                qBadgeView = new QBadgeView(getActivity());
                qBadgeView.bindTarget(imgMessage)
                        .setBadgeGravity(Gravity.END | Gravity.TOP)
                        .setBadgePadding(3.5f, true)
                        .setGravityOffset(0, 8, true)
                        .setBadgeText("");
            }else {
                qBadgeView.hide(true);
            }
        }else {
            if(qBadgeView != null){
                qBadgeView.hide(true);
            }
        }
        Log.e(TAG, "onGetUnreadMessSuc: ");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void messageChange(HomeMessageReadChangeBean messageReadChangeBean){
        Logger.e("收到消息点击提醒");
        mPresenter.getUnreadMessage(MyApplication.userId);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    //获取首页数据
    @Override
    public void onGetHomeDataSuc(BaseModel<HomeBean> model) {
        mRefreshLayout.finishRefresh(1500);
        stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        HomeBean homeData = model.getData();
        if (homeData != null) {
            //吾G
            HomeBean.HomeBuyGiftActivityModelBean wuGModel = homeData.getHomeBuyGiftActivityModel();
            if (wuGModel != null && wuGModel.getHomeBuyGiftCommodityModels() != null && wuGModel.getHomeBuyGiftCommodityModels().size() != 0) {
//                rlWug.setVisibility(View.VISIBLE);
//                recycleviewWug.setVisibility(View.VISIBLE);
                tvWuTitle.setText(wuGModel.getActivityName());
                if (TextUtils.isEmpty(wuGModel.getActivityDesc())) {
                    tvWugTitleDes.setText("购物即送 双倍赠劵");
                } else {
                    tvWugTitleDes.setText(wuGModel.getActivityDesc());
                }
                GlideUtil.showRadius(mContext, wuGModel.getBannerUrl(), 5, ivWugMain);
                wuGListData.addAll(wuGModel.getHomeBuyGiftCommodityModels());
                wuGAdapter.notifyDataSetChanged();
            } else {
                rlWug.setVisibility(View.GONE);
                recycleviewWug.setVisibility(View.GONE);
            }
            // 0元拍
            HomeBean.HomeAuctionActivityModelBean zeroPaiModel = homeData.getHomeAuctionActivityModel();
            if (zeroPaiModel != null && zeroPaiModel.getHomeAuctionCommodityModels() != null && zeroPaiModel.getHomeAuctionCommodityModels().size() != 0) {
                rlZeroPai.setVisibility(View.VISIBLE);
                tvZeroTitle.setText(zeroPaiModel.getActivityName());
                long endTime = zeroPaiModel.getEndTime();
                long currentTime = System.currentTimeMillis();
                long timeValue = endTime - currentTime;
                if(timeValue > 0){
                    tvJuliOver.setVisibility(View.VISIBLE);
                    cvCountdown.setVisibility(View.VISIBLE);
                    cvCountdown.start(timeValue);
                    cvCountdown.setOnCountdownEndListener(cv -> mRefreshLayout.autoRefresh());
                }else {
                    tvJuliOver.setVisibility(View.GONE);
                    cvCountdown.setVisibility(View.GONE);
                }

                zeroListData.addAll(zeroPaiModel.getHomeAuctionCommodityModels());
                zeroAdapter.notifyDataSetChanged();
            } else {
                //隐藏布局
                rlZeroPai.setVisibility(View.GONE);
            }

            //喜立得
            HomeBean.HomeBargainActivityModelBean kanDiDeModel = homeData.getHomeBargainActivityModel();
            if (kanDiDeModel != null && kanDiDeModel.getHomeBargainCommodityModels() != null && kanDiDeModel.getHomeBargainCommodityModels().size() != 0) {
                rlKanLide.setVisibility(View.VISIBLE);
                recycleviewKanLi.setVisibility(View.VISIBLE);
                tvKanTitle.setText(kanDiDeModel.getActivityName());
                if (TextUtils.isEmpty(kanDiDeModel.getActivityDesc())) {
                    tvKanTitleDes.setText("分享好友 砍价得红包");
                } else {
                    tvKanTitleDes.setText(kanDiDeModel.getActivityDesc());
                }
                GlideUtil.showRadius(mContext, kanDiDeModel.getBannerUrl(), 5, ivKanMain);
                kanListData.addAll(kanDiDeModel.getHomeBargainCommodityModels());
                kanLiDeAdapter.notifyDataSetChanged();
            } else {
                rlKanLide.setVisibility(View.GONE);
                recycleviewKanLi.setVisibility(View.GONE);
            }

            //多买多折
            HomeBean.HomeDiscountActivityModelBean moreBuyModel = homeData.getHomeDiscountActivityModel();
            if (moreBuyModel != null && moreBuyModel.getHomeDiscountCommodityModels() != null && moreBuyModel.getHomeDiscountCommodityModels().size() != 0) {
                rlTitleContent.setVisibility(View.VISIBLE);
                recyclerviewMoreBuy.setVisibility(View.VISIBLE);
                tvTitle.setText(moreBuyModel.getActivityName());
                if (TextUtils.isEmpty(moreBuyModel.getActivityDesc())) {
                    tvTitleDes.setText("多买多折扣 分享折上折");
                } else {
                    tvTitleDes.setText(moreBuyModel.getActivityDesc());
                }
                MyApplication.rateOne = moreBuyModel.getHomeDiscountCommodityModels().get(0).getRateOne();
                moreBuyListData.addAll(moreBuyModel.getHomeDiscountCommodityModels());
                moreBuyAdapter.notifyDataSetChanged();
            } else {
                rlTitleContent.setVisibility(View.GONE);
                recyclerviewMoreBuy.setVisibility(View.GONE);
            }

            //全球买手
            HomeBean.HomePageGlobalBuyerActivityModelBean globalModel = homeData.getHomePageGlobalBuyerActivityModel();
            if(globalModel != null && globalModel.getHomeGlobalBuyerCommodityModels() != null && globalModel.getHomeGlobalBuyerCommodityModels().size() != 0){
                rlGlobalTtileContent.setVisibility(View.VISIBLE);
                recyclerViewGlobal.setVisibility(View.VISIBLE);
                tvGlobalTitle.setText(globalModel.getActivityName());

                if (TextUtils.isEmpty(globalModel.getActivityDesc())) {
                    tvGlobalTitleDes.setText("全球百款品牌大促销");
                } else {
                    tvGlobalTitleDes.setText(globalModel.getActivityDesc());
                }
                globalLisData.addAll(globalModel.getHomeGlobalBuyerCommodityModels());
                homeGlobalAdapter.notifyDataSetChanged();
            }else{
                rlGlobalTtileContent.setVisibility(View.GONE);
                recyclerViewGlobal.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onRoundSuccess(BaseModel<List<ActivityRoundBean>> baseModel) {
        if(baseModel == null ||  baseModel.getData() == null || baseModel.getData().size() == 0){
            ToastUtils.showShortToast(mContext, "活动筹划中");
        }else {
            ActivityUtils.startActivity(ZeroMainActivity.class);
        }
    }

    //获取轮播图
    @Override
    public void onGetHomeBannerSuc(BaseModel<List<BannerBean>> model, int position) {
        if (position == 1) {
            listBanner.clear();
            List<BannerBean> data = model.getData();
            if (data != null && data.size() > 0) {
                listBanner.addAll(data);
                initBanner(listBanner);
            }
        } else if (position == 2) {
            //绑定中间banner
            if (model.getData() != null && model.getData().size() != 0) {
                BannerBean bannerBean = model.getData().get(0);
                GlideUtil.show(mContext, bannerBean.getImageUrl(), imgShare);
                imgShare.setOnClickListener(v -> {
                    String skipType = bannerBean.getSkipType();
                    String targetParams = bannerBean.getTargetParams();
                    String webUrl = bannerBean.getTargetUrl1();
                    XiKouUtils.parseBannner(mContext, skipType, targetParams, webUrl);
                });
            }
        }
    }

    @Override
    public void onGetActiveSectionSuccess(BaseModel<ActiveSectionBean> model, int activityType) {
        if(model.getData() != null && model.getData().getSectionList() != null && model.getData().getSectionList().size() != 0){
            List<ActiveSectionBean.SectionListBean> sectionList = model.getData().getSectionList();
            String sectionId = sectionList.get(0).getId();
            if(null != sectionId && !TextUtils.isEmpty(sectionId)){
                if(activityType == ActivityType.ACTIVITY_NEW_PERSON){
                    mPresenter.getActiveSectionGoods(sectionId, ActivityType.ACTIVITY_NEW_PERSON, MyApplication.userId, 1, 1);
                }
            }
        }else {
            ToastUtils.showShortToast(mContext, "活动筹划中");
        }
    }

    @Override
    public void onGetActiveSectionGoodsSuccess(BaseModel<ActiveSectionGoodsPageBean> model, int activityType) {
        if (model.getData() != null && model.getData().getResult() != null && model.getData().getResult().size() != 0) {
            if(activityType == ActivityType.ACTIVITY_NEW_PERSON) {
                 ActivityUtils.startActivity(NewPersonActivity.class);
            }
        }else {
            ToastUtils.showShortToast(mContext, "活动筹划中");
        }

    }

    @Keep
    @LoginFilter
    private void goToTask() {
        ActivityUtils.startActivity(MakeTaskActivity.class);
    }

    @Keep
    @LoginFilter
    private void goToMessage() {
        Intent intent = new Intent(mContext, HomeMessageActivity.class);
        if (homeMessageBean != null) {
            intent.putExtra(Constant.HOME_MESSAGE, homeMessageBean);
        }
        ActivityUtils.startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changeHomeMessageBean(HomeMessageBean homeMessageBean){
        if(homeMessageBean != null){
            this.homeMessageBean = homeMessageBean;
            if (homeMessageBean.getTotalUnreadNum() == 0 && qBadgeView != null){
                qBadgeView.hide(true);
            }
        }
    }

    @OnClick({R.id.img_message, R.id.iv_cart, R.id.et_near_search, R.id.rl_wug, R.id.iv_wug_main,
            R.id.rl_zero_pai, R.id.rl_kan_lide, R.id.iv_kan_main, R.id.rl_title_content,R.id.rl_global_title_content,R.id.rl_jia_one})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_message:
                goToMessage();
                break;
            case R.id.iv_cart: //扫一扫
                startPermissionsScanner();
                break;
            case R.id.et_near_search://搜索
                ActivityUtils.startActivity(HomeSearchActivity.class);
                break;
            case R.id.rl_wug://吾G
            case R.id.iv_wug_main:
                ActivityUtils.startActivity(WuGMainActivity.class);
                break;
            case R.id.rl_zero_pai://0 元拍
                ActivityUtils.startActivity(ZeroMainActivity.class);
                break;
            case R.id.rl_kan_lide://喜立得
            case R.id.iv_kan_main:
                ActivityUtils.startActivity(CutMainActivity.class);
                break;
//            case R.id.img_share://分享
//                ToastUtils.showShortToast(mContext, "share");
//                break;
            case R.id.rl_title_content://多买多得
                ActivityUtils.startActivity(ManyBuyActivity.class);
                break;
            case R.id.rl_global_title_content://全球买手
                ActivityUtils.startActivity(GlobalBuyerActivity.class);
                break;
            case R.id.rl_jia_one://假一罚三
                Intent intent = new Intent(mContext, ProtocolWebViewActivity.class);
                intent.putExtra(Constant.INTENT_URL, Constant.qualityUrl);
                intent.putExtra(ProtocolWebViewActivity.IS_SHOW, false);
                ActivityUtils.startActivity(intent);
                break;
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
        super.onErrorCode(model);
        mRefreshLayout.finishRefresh(1500);
//        stateView.setViewState(MultiStateView.VIEW_STATE_ERROR);
        Log.e(TAG, "onErrorCode:==================== " + model.getCode());
        Log.e(TAG, "onErrorCode:==================== " + model.getMsg());
    }


    @AfterPermissionGranted(REQUEST_CAMERA_PERM)
    private void startPermissionsScanner() {
        //检查是否获取该权限
        if (hasPermissions()) {
            //具备权限 直接进行操作
            startScanner();
        } else {
            //权限拒绝 申请权限
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限

            EasyPermissions.requestPermissions(this,
                    getResources().getString(R.string.easy_permissions_camera),
                    REQUEST_CAMERA_PERM, Manifest.permission.CAMERA, Manifest.permission.VIBRATE
            );
        }
    }

    /**
     * 将结果转发到EasyPermissions
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 将结果转发到EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode,
                permissions, grantResults, mContext);
    }

    //跳转扫一扫
    private void startScanner() {
        Intent scanner = new Intent(mContext, ScanActivity.class);
        startActivityForResult(scanner, REQUEST_CODE);
    }

    /**
     * 判断是否添加了权限
     *
     * @return true
     */
    private boolean hasPermissions() {
        return EasyPermissions.hasPermissions(mContext, Manifest.permission.CAMERA, Manifest.permission.VIBRATE);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        startScanner();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            if (EasyPermissions.somePermissionPermanentlyDenied(getActivity(), perms)) {
                AppSettingsDialog.Builder builder = new AppSettingsDialog.Builder(getActivity());
                builder.setTitle("允许权限")
                        .setRationale("没有该权限，此应用程序部分功能可能无法正常工作。打开应用设置界面以修改应用权限")
                        .setPositiveButton("去设置")
                        .setNegativeButton("取消")
                        .setRequestCode(REQUEST_CAMERA_PERM)
                        .build()
                        .show();
            }
        }
    }


}
