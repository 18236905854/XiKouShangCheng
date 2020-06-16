package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.CutSuccessBean;
import com.xk.mall.model.entity.GlobalBuyerGoodsDetailBean;
import com.xk.mall.model.entity.GoodsSkuListBean2;
import com.xk.mall.model.entity.ShareBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.entity.SkuAttribute;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.CutGoodsDetailViewImpl;
import com.xk.mall.model.request.CutOrderRequestBody;
import com.xk.mall.model.request.CutRequestBody;
import com.xk.mall.model.request.ShareRequestBody;
import com.xk.mall.presenter.CutGoodsDetailPresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.GlideImageLoader;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.MeiQiaUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.SpacesItemDecoration;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.adapter.GoodsImagesDetailAdapter;
import com.xk.mall.view.widget.CutProductSkuDialog;
import com.xk.mall.view.widget.CutRuleDialog;
import com.xk.mall.view.widget.DialogShareGoods;
import com.xk.mall.view.widget.MerchantPhoneDialog;
import com.xk.mall.view.widget.PosterTipDialog;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

/**
 * ClassName CutGoodsDetailActivity
 * Description 喜立得 商品详情
 * Author
 * Date
 * Version
 */
public class CutGoodsDetailActivity extends BaseActivity<CutGoodsDetailPresenter> implements CutGoodsDetailViewImpl {
    private static final String TAG = "CutGoodsDetailActivity";
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
    @BindView(R.id.tv_alone_buy)
    TextView tvAloneBuy;
    private static final int TIME_DESC = 1001;

    public static final String ACTIVITY_GOODS_ID = "activity_goods_id";

    @BindView(R.id.tv_cut_goods_tip)
    TextView tvCutTip;//当前砍价人数，砍至多少钱
    @BindView(R.id.recycleviewDetail)
    RecyclerView recycleviewDetail;
    @BindView(R.id.tv_youfei)
    TextView tvPostage;//邮费
    @BindView(R.id.tv_kan_people_num)
    TextView tv_kan_people_num;
    @BindView(R.id.tv_fahuo_time)
    TextView tvFaHuoTime;//下单发货时间
    @BindView(R.id.tv_cut_time_all)
    TextView tvCutTimeAll;//砍价总时长
    @BindView(R.id.tv_top_tip)
    TextView tvTopTip;
    @BindView(R.id.tv_merchant_name)
    TextView tvMerchantName;
    @BindView(R.id.iv_brand_author)
    ImageView ivBrandAuthor;//品牌授权
    @BindView(R.id.tv_category_name)
    TextView tvCategoryName;
    @BindView(R.id.tv_alone_price)
    TextView tvAlonePrice;
    @BindView(R.id.ll_alone_buy)
    LinearLayout llAloneBuy;
    @BindView(R.id.ll_cut_list)
    LinearLayout llCutList;//砍价历史
    @BindView(R.id.rv_cut_list)
    RecyclerView rvCutList;//砍价历史的view
    @BindView(R.id.tv_cut_rule)
    TextView tvCutRule;//砍价规则

    private GlobalBuyerGoodsDetailBean goodsDetailBean;
    private CutProductSkuDialog dialog;
    private String selectAddresId = "";//选择地址id
    private String activityGoodsId = "";//活动商品ID
    private String activityId = "";//活动ID
    private String goodsId = "";//商品ID
    private String commodityId = "";//商品Sku ID
    private String merchantId;//商家id
    private int postage;//邮费
    private String goodsImageUrl;//商品图片
    private int salePrice, linePrice;
    private int bargainNumber;//多少人助力
    private GoodsSkuListBean2 selectedSku;//当前发起砍价的sku
    private int bargainEffectiveTime;//砍价有效时长--小时
    private List<GoodsSkuListBean2> cutListBean = new ArrayList<>();//正在砍价的sku
    private int cutState;//3种状态  0 未发起砍价 1 继续砍价  2 砍价成功 --支付

    @Override
    protected CutGoodsDetailPresenter createPresenter() {
        return new CutGoodsDetailPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_goods_detail;
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
//        initDetailData();
        initView();
        MyApplication.getInstance().addActivity(this);
        ImmersionBar.setTitleBar(this, toolbar);
        //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
        ImmersionBar.with(this).statusBarDarkFont(false, 0.2f)
                .init();
        Intent intent = getIntent();
        activityGoodsId = intent.getStringExtra(ACTIVITY_GOODS_ID);
        setShowDialog(false);
//        mPresenter.getGoodsDetailByGoodsId(activityGoodsId, ActivityType.ACTIVITY_CUT, MyApplication.userId);

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
        //根据开关控制确定按钮
        if (MyApplication.switchBean != null && MyApplication.switchBean.getBargin() == 1) {
            btnGrabBuy.setEnabled(true);
            btnGrabBuy.setBackgroundResource(R.drawable.bg_cut_detail_buy);
            llAloneBuy.setEnabled(true);
            llAloneBuy.setBackgroundResource(R.drawable.bg_cut_alone_buy);
            tvAlonePrice.setTextColor(Color.parseColor("#BB9445"));
            tvAloneBuy.setTextColor(Color.parseColor("#BB9445"));
        } else {
            btnGrabBuy.setEnabled(false);
            btnGrabBuy.setBackgroundResource(R.drawable.bg_cut_detail_buy_no);
            llAloneBuy.setEnabled(false);
            llAloneBuy.setBackgroundResource(R.drawable.bg_cut_alone_buy_no);
            tvAlonePrice.setTextColor(Color.parseColor("#B8B5AF"));
            tvAloneBuy.setTextColor(Color.parseColor("#B8B5AF"));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getGoodsDetailByGoodsId(activityGoodsId, ActivityType.ACTIVITY_CUT, MyApplication.userId);
        if (SPUtils.getInstance().getBoolean(Constant.IS_LOGIN) && rlJoinVip != null) {
            rlJoinVip.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(bannerWorksDetail != null){
            bannerWorksDetail.releaseBanner();
        }
        MyApplication.getInstance().removeActivity(this);
    }

    private void initView() {
        rlJoinVip.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);
        });

        llIndex.setOnClickListener(v -> {
            ActivityUtils.startActivity(MainActivity.class);
        });

    }

    /**
     * SKU 弹窗
     * 1 加入购物车
     * 2 直接购买
     */
    private void showDialog(boolean isBuySingle) {
        if(goodsDetailBean != null && goodsDetailBean.getActivityCommodityVo().getSkuList() != null
                && goodsDetailBean.getActivityCommodityVo().getSkuList().size() != 0) {
            if (dialog == null) {
                dialog = new CutProductSkuDialog(this);
                dialog.setSingleBuy(isBuySingle);
                dialog.setData(goodsDetailBean, (sku, quantity, memo) -> {
                    selectedSku = sku;
                    if(isBuySingle){
                        goBuySingle(sku, quantity, memo);
                    }else {
                        if(sku.getBargainStatus() == 3){
                            //跳转继续砍价页面
                            toContinueActivity();
                        }else {
                            goToCutPrice(sku, quantity, memo);
                        }
                    }
                });
                dialog.show();
            }else {
                dialog.setSingleBuy(isBuySingle);
                dialog.setData(goodsDetailBean, (sku, quantity, memo) -> {
                    selectedSku = sku;
                    if(isBuySingle){
                        goBuySingle(sku, quantity, memo);
                    }else {
                        if(sku.getBargainStatus() == 3){
                            //跳转继续砍价页面
                            toContinueActivity();
                        }else {
                            goToCutPrice(sku, quantity, memo);
                        }
                    }
                });
                dialog.show();
            }
        }else {
            ToastUtils.showShortToast(mContext, "获取数据有误");
        }
    }

    @Keep
    @LoginFilter
    @OnClick({R.id.ll_kefu, R.id.btn_grab_buy,R.id.ll_alone_buy, R.id.tv_cut_rule})
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.btn_grab_buy://我要砍价（--弹出地址--选地址---确认地址---在砍价）---已改成下单页面选择地址
                    //调接口砍价
                showDialog(false);
                break;
            case R.id.ll_alone_buy://单独购买
                buySingle();
                break;
            case R.id.tv_cut_rule:
                //弹出砍价规则对话框
                showCutRuleDialog();
                break;
        }
    }

    private void showCutRuleDialog(){
        String content = getResources().getString(R.string.cut_rule_content);
        content = String.format(content, bargainNumber, 24);
        new CutRuleDialog(mContext, R.style.mydialog, "喜立得活动介绍", content,"知道了", (dialog, confirm) -> {
            if(confirm){
                dialog.dismiss();
            }
        }).show();
    }

    /**
     * 单独购买
     */
    @Keep
    @LoginFilter
    private void buySingle(){
        showDialog(true);
    }

    private void goBuySingle(GoodsSkuListBean2 sku, int num, String memo){
        CutOrderRequestBody requestBody = new CutOrderRequestBody();
        requestBody.setActivityGoodsId(sku.getId());
        requestBody.setActivityId(activityId);
        requestBody.setBuyerId(MyApplication.userId);
        requestBody.setCommodityId(sku.getCommodityId());
        requestBody.setCommodityModel(sku.getCommodityModel());
        requestBody.setCommodityQuantity(1);
        requestBody.setCommoditySpec(sku.getCommoditySpec());
        requestBody.setGoodsCode(goodsDetailBean.getActivityCommodityVo().getGoodCode());
        requestBody.setGoodsId(goodsId);
        requestBody.setGoodsImageUrl(sku.getSkuImage());
        requestBody.setGoodsName(sku.getCommodityName());
        requestBody.setId("");//用户发起砍价主键id
        requestBody.setMerchantId(goodsDetailBean.getActivityCommodityVo().getMerchantId());//商家id
        requestBody.setOrderAmount(goodsDetailBean.getActivityCommodityVo().getSalePrice());
        requestBody.setReceiptAddressRef("");
        requestBody.setSalePrice(linePrice);
        requestBody.setOrderSource(1);
        requestBody.setCreateType(1);
        Intent intent=new Intent(mContext,CutXiaDanActivity.class);
        intent.putExtra("request_entity",requestBody);
        intent.putExtra("postage",postage);
        intent.putExtra("unit_price",goodsDetailBean.getActivityCommodityVo().getSalePrice());
        startActivity(intent);
    }

    /**
     * 去砍价
     */
    @Keep
    @LoginFilter
    private void goToCutPrice(GoodsSkuListBean2 sku, int num, String memo) {
        selectedSku = sku;
        CutRequestBody cutRequestBody = new CutRequestBody();
        cutRequestBody.setActivityId(activityId);
        cutRequestBody.setUserId(MyApplication.userId);
        cutRequestBody.setCommodityId(sku.getCommodityId());
        cutRequestBody.setAddress(selectAddresId);
        if (goodsDetailBean != null) {
            cutRequestBody.setMerchantId(goodsDetailBean.getActivityCommodityVo().getMerchantId());
        }
        cutRequestBody.setId(sku.getId());
        mPresenter.goCut(cutRequestBody);
    }


    /**
     * 获取喜立得商品详情成功的回调
     */
    @Override
    public void onGoodsDetailSuccess(BaseModel<GlobalBuyerGoodsDetailBean> model) {
        goodsDetailBean = model.getData();
        bindRule(model.getData().getBaseRuleVo());
        bindBanner(goodsDetailBean);
        bindGoods(model.getData().getActivityCommodityVo());

        //处理数据手动添加  SpecMap() 值
        List<GoodsSkuListBean2> goodsSkuList = goodsDetailBean.getActivityCommodityVo().getSkuList();
        SkuAttribute skuAttribute2 = null;
        cutListBean.clear();
        for (GoodsSkuListBean2 goodsSkuListBean : goodsSkuList) {
            if(goodsSkuListBean.getBargainStatus() == 3){//正在砍价
                cutListBean.add(goodsSkuListBean);
            }
            if(goodsSkuListBean.getBargainEffectiveTimed() != 0){
                bargainEffectiveTime = goodsSkuListBean.getBargainEffectiveTimed();
            }
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
        bindCutList();
    }

    /**
     * 绑定正在砍价的数据
     */
    private void bindCutList(){
        if(cutListBean.size() == 0){
            llCutList.setVisibility(View.GONE);
        }else {
            llCutList.setVisibility(View.VISIBLE);
            rvCutList.setLayoutManager(new LinearLayoutManager(mContext));
            CutHistoryAdapter cutHistoryAdapter = new CutHistoryAdapter(mContext, R.layout.item_cut_history, cutListBean);
            rvCutList.addItemDecoration(new SpacesItemDecoration(1));
            rvCutList.setAdapter(cutHistoryAdapter);
        }
        if(bargainEffectiveTime == 0){
            bargainEffectiveTime = 24;
        }
        tvCutTimeAll.setText("砍价时长" + bargainEffectiveTime + "小时");
    }


    private void bindRule(GlobalBuyerGoodsDetailBean.BaseRuleVoBean baseRuleVoBean) {
        if (baseRuleVoBean != null) {
            bargainNumber = baseRuleVoBean.getBargainNumber();

//            tv_limit_buy.setText("限购数量：" + baseRuleVoBean.getBuyLimit());

            if (baseRuleVoBean.isFlag() || baseRuleVoBean.getPostage() == 0) {
                postage = 0;
                tvPostage.setText("全国包邮");
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_poster_tips);
                // 这一步必须要做,否则不会显示.
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                tvPostage.setCompoundDrawablePadding(3);
                tvPostage.setCompoundDrawables(null, null, drawable, null);
                tvPostage.setOnClickListener(v -> new PosterTipDialog(mContext).show());
            } else {
                postage = baseRuleVoBean.getPostage();
                tvPostage.setText("邮费：" + PriceUtil.dividePrice(baseRuleVoBean.getPostage()) + "元");
            }
            tvFaHuoTime.setText("下单后" + baseRuleVoBean.getDeliveryDuration() + "小时内发货");
        }
    }

    /**
     * 绑定商品信息
     */
    private void bindGoods(GlobalBuyerGoodsDetailBean.ActivityCommodityVoBean activityCommodityVoBean) {
        cutState = activityCommodityVoBean.getBargainState();
        tvAlonePrice.setText("¥"+PriceUtil.dividePrice(activityCommodityVoBean.getSalePrice()));
        //折扣率 * 商品价钱 / 10000
        int lastPrice = activityCommodityVoBean.getDiscount() * goodsDetailBean.getActivityCommodityVo().getSalePrice() / 10;
        String bottomPrice = PriceUtil.dividePrice(lastPrice);
        if(activityCommodityVoBean.getBrandAuthorized() == 1){
            ivBrandAuthor.setVisibility(View.VISIBLE);
        }else {
            ivBrandAuthor.setVisibility(View.GONE);
        }

//        tvTopTip.setText(bargainNumber + "人砍价" + activityCommodityVoBean.getDiscount() + "折底价:¥" + bottomPrice);
        tvTopTip.setText(bargainNumber + "人可砍金额: ¥" + PriceUtil.dividePrice(activityCommodityVoBean.getCutPrice()));
        btnGrabBuy.setText("我要砍价");
        tvCutTip.setVisibility(View.INVISIBLE);

        if (activityCommodityVoBean != null) {
            activityId = activityCommodityVoBean.getActivityId();//活动ID
            goodsId = activityCommodityVoBean.getGoodsId();//商品id
            salePrice = activityCommodityVoBean.getCommodityPrice();
            linePrice = activityCommodityVoBean.getSalePrice();

            if (activityCommodityVoBean.getSkuList().size() > 0) {
                commodityId = activityCommodityVoBean.getSkuList().get(0).getId();//商品sku ID
            }
            merchantId = activityCommodityVoBean.getMerchantId();
            tvGoodsName.setText(activityCommodityVoBean.getCommodityName());
            tvPrice.setText("" + PriceUtil.dividePrice(activityCommodityVoBean.getCommodityPrice()));
            tvLinePrice.setText(getResources().getString(R.string.money) + PriceUtil.dividePrice(activityCommodityVoBean.getSalePrice()));
            tvLinePrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            tvCutTip.setText("当前砍价" + activityCommodityVoBean.getBargainCount() + "人,已砍至¥" +
                    PriceUtil.dividePrice(activityCommodityVoBean.getCurrentPrice()));
            tv_kan_people_num.setText("已有" + activityCommodityVoBean.getBargainSuccessCount() + "人砍价成功");
            tvMerchantName.setText(activityCommodityVoBean.getMerchantName());
            tvCategoryName.setText(activityCommodityVoBean.getCategoryName());
            int stock = activityCommodityVoBean.getStock();
            if(stock <= 0){
                btnGrabBuy.setEnabled(false);
                btnGrabBuy.setBackgroundResource(R.drawable.bg_cut_detail_buy_no);
                btnGrabBuy.setText("已售罄");
                llAloneBuy.setEnabled(false);
                llAloneBuy.setBackgroundResource(R.drawable.bg_cut_alone_buy_no);
                tvAlonePrice.setTextColor(Color.parseColor("#B8B5AF"));
                tvAloneBuy.setTextColor(Color.parseColor("#B8B5AF"));
            }
        }

//        if (MyApplication.switchBean != null && MyApplication.switchBean.getBargin() == 1) {
//            btnGrabBuy.setEnabled(true);
//            btnGrabBuy.setBackgroundResource(R.drawable.bg_wugdetail_buy);
//        } else {
//            btnGrabBuy.setEnabled(false);
//            btnGrabBuy.setBackgroundResource(R.drawable.bg_login_btn_disable);
//        }
    }

    /**
     * 砍价成功的回调
     */
    @Override
    public void onCutSuccess(BaseModel<CutSuccessBean> model) {
        ToastUtils.showShortToast(mContext, "发起砍价成功");
        CutSuccessBean cutSuccessBean = model.getData();
//        if (cutSuccessBean != null) {
//            cutState = cutSuccessBean.getState();
//            tvCutTip.setVisibility(View.VISIBLE);
//            llEndTime.setVisibility(View.VISIBLE);
//            btnGrabBuy.setText("继续砍价");
//            //倒计时
//            bargainCreateTime = cutSuccessBean.getCreateTime();
//            bargainEffectiveTime=cutSuccessBean.getBargainEffectiveTimed();
//            long endTime= DateToolUtils.strToDateLong(bargainCreateTime).getTime() + bargainEffectiveTime * 60 * 60 * 1000;//砍价截止时间
//            chaTime = endTime - System.currentTimeMillis();
//            mHandler.sendEmptyMessage(TIME_DESC);
        toContinueActivity();
//        }
    }

    private void toContinueActivity() {
        if(selectedSku != null){
            Intent intent = new Intent(CutGoodsDetailActivity.this, CutContinueActivity.class);
            intent.putExtra("goods_name", selectedSku.getCommodityName());
            intent.putExtra("goods_img", selectedSku.getSkuImage());
            intent.putExtra("sale_price", selectedSku.getCommodityPrice());
            intent.putExtra("line_price", selectedSku.getSalePrice());
            intent.putExtra("activity_id", activityId);
            intent.putExtra("cut_id", selectedSku.getId());
            intent.putExtra("cut_state", selectedSku.getBargainStatus());
            intent.putExtra("commodityId", selectedSku.getCommodityId());
            intent.putExtra("commodityModel", selectedSku.getCommodityModel());
            intent.putExtra("commoditySpec", selectedSku.getCommoditySpec());
            intent.putExtra("goodsId", goodsId);
            intent.putExtra("goodsCode", selectedSku.getGoodsModel());
            intent.putExtra("bussId", merchantId);
            intent.putExtra("postage", postage);
            intent.putExtra("unit_price", goodsDetailBean.getActivityCommodityVo().getSalePrice());

            startActivity(intent);
        }
    }

    /**
     * 获取分享内容
     */
    private void getShareContent() {
        ShareRequestBody.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareRequestBody.ActivityGoodsConditionBean();
        activityGoodsConditionBean.setActivityId(activityId);
        activityGoodsConditionBean.setCommodityId(commodityId);
        activityGoodsConditionBean.setActivityGoodsId(activityGoodsId);
        activityGoodsConditionBean.setGoodsId(goodsId);
        activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_CUT);
        mPresenter.getShareContent(MyApplication.userId, activityGoodsConditionBean, ShareType.DETAIL_CUT);
    }

    @Override
    public void onGetShareSuccess(BaseModel<ShareBean> model) {
        ShareBean shareBean = model.getData();
        if (shareBean != null) {
            DialogShareGoods dialogShare = new DialogShareGoods(mContext, shareBean);
            MyApplication.shareType = ShareType.DETAIL_CUT;
            dialogShare.setListener(() -> {
                Intent intent = new Intent(mContext, PosterActivity.class);
                intent.putExtra(PosterActivity.GOODS_NAME, goodsDetailBean.getActivityCommodityVo().getCommodityName());
                intent.putExtra(PosterActivity.GOODS_IMG, goodsDetailBean.getActivityCommodityVo().getImageList().get(0).getUrl());
                intent.putExtra(PosterActivity.GOODS_PRICE, goodsDetailBean.getActivityCommodityVo().getCommodityPrice());
                intent.putExtra(PosterActivity.GOODS_ACTIVITY_TYPE, ActivityType.ACTIVITY_CUT);
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
    public void shareCallback(ShareSuccessEvent event) {
        if (event.shareType == ShareType.DETAIL_CUT) {
            ShareSuccessBean.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareSuccessBean.ActivityGoodsConditionBean();
            activityGoodsConditionBean.setActivityId(activityId);
            activityGoodsConditionBean.setCommodityId(commodityId);
            activityGoodsConditionBean.setGoodsId(goodsId);
            activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_CUT);
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.DETAIL_CUT, activityGoodsConditionBean);
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext, model.getMsg());
    }

    /**
     * 绑定banner数据
     */
    private void bindBanner(GlobalBuyerGoodsDetailBean model) {
        List<String> imagesBanner = new ArrayList<>();
        List<String> imagesDetail = new ArrayList<>();
        if(model != null && model.getActivityCommodityVo() != null && model.getActivityCommodityVo().getImageList() != null){
            if (model.getActivityCommodityVo().getImageList().size() > 0) {
                for (GlobalBuyerGoodsDetailBean.ActivityCommodityVoBean.ImageListBean bean : model.getActivityCommodityVo().getImageList()) {
                    if(bean.getType() == 1){
                        imagesBanner.add(bean.getUrl());
                    }else if(bean.getType() == 2){
                        imagesDetail.add(bean.getUrl());
                    }
                }
                bannerWorksDetail.setImages(imagesBanner);
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
        if(imagesBanner.size() != 0){
            goodsImageUrl = imagesBanner.get(0);
        }
        //banner设置方法全部调用完毕时最后调用
        bannerWorksDetail.setOnBannerListener(position -> GlideUtil.lookBigImage(mContext, imagesBanner, position));
        bannerWorksDetail.start();
        if(null != model.getOfficialPictures() && !TextUtils.isEmpty(model.getOfficialPictures())){
            imagesDetail.add(model.getOfficialPictures());
        }
        if (imagesDetail.size() != 0) {
            GoodsImagesDetailAdapter detailImgAdapter = new GoodsImagesDetailAdapter(mContext, imagesDetail, new RvListener() {
                @Override
                public void onItemClick(int id, int index) {
                    GlideUtil.lookBigImage(mContext, imagesDetail, index);
                }
            });
            recycleviewDetail.setLayoutManager(new LinearLayoutManager(mContext));
            recycleviewDetail.setAdapter(detailImgAdapter);
        }
    }

    class CutHistoryAdapter extends CommonAdapter<GoodsSkuListBean2>{

        private CountdownView countdownView;

        public CutHistoryAdapter(Context context, int layoutId, List<GoodsSkuListBean2> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, GoodsSkuListBean2 goodsSkuListBean2, int position) {
            countdownView = holder.getView(R.id.count_down_cut_list);
            ImageView ivHead = holder.getView(R.id.iv_cut_logo);
            GlideUtil.showCircleLoading(mContext, goodsSkuListBean2.getSkuImage(), ivHead);
            holder.setText(R.id.tv_cut_sku, goodsSkuListBean2.getCommodityModel() + " " + goodsSkuListBean2.getCommoditySpec());
            long endTime = TimeUtils.string2Millis(goodsSkuListBean2.getBargainCreateTime()) + goodsSkuListBean2.getBargainEffectiveTimed() * 3600 * 1000;
            long subTime = endTime - System.currentTimeMillis();
            refreshTime(subTime);
            countdownView.setOnCountdownEndListener(cv -> mPresenter.getGoodsDetailByGoodsId(activityGoodsId, ActivityType.ACTIVITY_CUT, MyApplication.userId));
            holder.setOnClickListener(R.id.tv_cut_go_continue, view -> {
                //点击跳转继续砍价页面
                Intent intent = new Intent(CutGoodsDetailActivity.this, CutContinueActivity.class);
                intent.putExtra("goods_name", goodsSkuListBean2.getCommodityName());
                intent.putExtra("goods_img", goodsSkuListBean2.getSkuImage());
                intent.putExtra("sale_price", goodsSkuListBean2.getCommodityPrice());
                intent.putExtra("line_price", goodsSkuListBean2.getSalePrice());
                intent.putExtra("activity_id", activityId);
                intent.putExtra("cut_id", goodsSkuListBean2.getId());
                intent.putExtra("cut_state", goodsSkuListBean2.getBargainStatus());
                intent.putExtra("commodityId", goodsSkuListBean2.getCommodityId());
                intent.putExtra("commodityModel", goodsSkuListBean2.getCommodityModel());
                intent.putExtra("commoditySpec", goodsSkuListBean2.getCommoditySpec());
                intent.putExtra("goodsId", goodsId);
                intent.putExtra("goodsCode", goodsSkuListBean2.getGoodsModel());
                intent.putExtra("bussId", merchantId);
                intent.putExtra("postage", postage);
                intent.putExtra("unit_price", goodsSkuListBean2.getSalePrice());
                startActivity(intent);
            });
        }

        public void refreshTime(long leftTime) {
            if (leftTime > 0) {
                countdownView.start(leftTime);
            } else {
                countdownView.stop();
                countdownView.allShowZero();
            }
        }
    }

}
