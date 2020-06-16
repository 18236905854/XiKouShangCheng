package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.CutSuccessBean;
import com.xk.mall.model.entity.ShareSuccessBean;
import com.xk.mall.model.eventbean.ShareSuccessEvent;
import com.xk.mall.model.impl.CutContinuelViewImpl;
import com.xk.mall.model.request.CutOrderRequestBody;
import com.xk.mall.presenter.CutContinuePresenter;
import com.xk.mall.utils.ActivityType;
import com.xk.mall.utils.DateToolUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.ShareType;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.ShareCutDialog;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import retrofit2.http.Path;

/**
 * ClassName CutContinueActivity
 * Description 继续砍价页面
 * Author 卿凯
 * Date 2019/6/28/028
 * Version V1.0
 */
public class CutContinueActivity extends BaseActivity<CutContinuePresenter> implements CutContinuelViewImpl {
    @BindView(R.id.iv_cut_recommend_logo)
    ImageView ivCutRecommendLogo;//图标
    @BindView(R.id.tv_cut_name)
    TextView tvCutName;//商品名
    @BindView(R.id.tv_cut_man)
    TextView tvCutMan;//8人助力得底价
    @BindView(R.id.tv_cut_now_price)
    TextView tvCutNowPrice;//当前价格
    @BindView(R.id.tv_cut_real_price)
    TextView tvCutRealPrice;//真实价格
    @BindView(R.id.cv_countdown_cut)
    CountdownView cvCountdownCut;//倒计时
    @BindView(R.id.pb_fight_group)
    ProgressBar progressBar;//进度条
    @BindView(R.id.tv_cut_continue_cut_price)
    TextView tvCutPrice;//当前砍价金额
    @BindView(R.id.tv_cut_continue_share)
    TextView tvShare;//分享好友按钮
    @BindView(R.id.tv_cut_time_buy)
    TextView tvTimeBuy;//时间不多直接买下
    @BindView(R.id.rv_cut_continue)
    RecyclerView rvCutContinue;
    @BindView(R.id.ll_cut_ing)
    LinearLayout llCutIng;
    @BindView(R.id.tv_cut_success_price)
    TextView tvCutSuccessPrice;
    @BindView(R.id.btn_cut_success)
    TextView btnCutSuccess;
    @BindView(R.id.ll_cut_success)
    LinearLayout llCutSuccess;
    @BindView(R.id.tv_cut_time_desc)
    TextView tvCutTimeDesc;
    @BindView(R.id.tv_cut_sku)
    TextView tvSku;//显示sku内容

    private String goodsImageUrl, goodsName;
    private int salePrice, linePrice, helpMan;
    private int currentPrice;
    private double  bargainCount=0;//被砍价成功次数

    private List<CutSuccessBean.UserBargainRecordVoListBean> recordList = new ArrayList<>();//好友砍价记录
    private ShareCutDialog shareCutDialog;
    private String activityId;
    private String activityGoodsId;//活动商品
    private int cutState;//1 继续砍价(只显示分享) 2 砍价完成(人数没满显示分享，跟时间不多了) （人数满了 砍价成功去支付）
    private CutListAdapter cutListAdapter;
//    private String receiveAddresId;//收货地址id
    private String commodityId,commodityModel,commoditySpec,goodsId,goodsCode,bussId;
    private String id;//用户发起砍价id
    private int postage;
    private int unit_price;
    private CutSuccessBean cutSuccessBean;


    @Override
    protected CutContinuePresenter createPresenter() {
        return new CutContinuePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_continue;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("继续砍价");
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(CutContinueActivity.this);
        goodsName = getIntent().getStringExtra("goods_name");
        goodsImageUrl = getIntent().getStringExtra("goods_img");
        salePrice = getIntent().getIntExtra("sale_price", 0);
        linePrice = getIntent().getIntExtra("line_price", 0);
        cutState = getIntent().getIntExtra("cut_state", 0);
        activityId=getIntent().getStringExtra("activity_id");
        activityGoodsId=getIntent().getStringExtra("cut_id");
        commodityId=getIntent().getStringExtra("commodityId");
        commodityModel=getIntent().getStringExtra("commodityModel");
        commoditySpec=getIntent().getStringExtra("commoditySpec");
        goodsId=getIntent().getStringExtra("goodsId");
        goodsCode=getIntent().getStringExtra("goodsCode");
        bussId=getIntent().getStringExtra("bussId");
        postage=getIntent().getIntExtra("postage",0);
        unit_price=getIntent().getIntExtra("unit_price",0);

        mPresenter.goCutContinue(MyApplication.userId,activityId,activityGoodsId);

        GlideUtil.show(mContext, goodsImageUrl, ivCutRecommendLogo);
        tvCutName.setText(goodsName);
        tvSku.setText(commodityModel + " " + commoditySpec);

        tvCutNowPrice.setText("¥" + PriceUtil.dividePrice(salePrice));
        tvCutRealPrice.setText("¥" + PriceUtil.dividePrice(linePrice));

        tvCutRealPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        rvCutContinue.setLayoutManager(new GridLayoutManager(mContext, 4));
        cutListAdapter = new CutListAdapter(mContext, R.layout.item_cut_list, recordList);
        rvCutContinue.setAdapter(cutListAdapter);
    }

    /**
     * 获取砍价进度 成功
     *
     * @param model
     */
    @Override
    public void onGoodsContinueSuccess(BaseModel<CutSuccessBean> model) {
        CutSuccessBean data = model.getData();
        if (data != null) {
            cutSuccessBean=data;
            cutState=data.getState();
            //时间
            String createTime = data.getCreateTime();
//            int valie=cutRecommendBean.getBargainEffectiveTimed();
            long endTime = DateToolUtils.strToDateLong(createTime).getTime() +  data.getBargainEffectiveTimed() * 60 * 60 * 1000;//砍价截止时间
            long currentTime = System.currentTimeMillis();
            bargainCount = data.getBargainCount();
            helpMan =data.getBargainNumber();
            SpannableString spannableString = new SpannableString("满" + helpMan + "人砍至底价");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#F94119"));
            spannableString.setSpan(colorSpan,  1, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvCutMan.setText(spannableString);
            if(bargainCount >0){
               double progress= bargainCount / helpMan * 100;
                progressBar.setProgress((int) progress);
            }else{
                progressBar.setProgress(0);
            }

            if(currentTime < endTime){
                cvCountdownCut.start(endTime-currentTime);
            }else{
                cvCountdownCut.setVisibility(View.GONE);
                tvCutTimeDesc.setText("砍价时间已过期");
            }

            if(cutState == 1){
                setTitle("继续砍价");
//                showDialog();
                llCutIng.setVisibility(View.VISIBLE);
                llCutSuccess.setVisibility(View.GONE);
                tvTimeBuy.setVisibility(View.GONE);
                if(currentTime < endTime ){
                    showDialog();
                }
            }else if(cutState == 2 || cutState ==3) {//砍价成功
                if( bargainCount < helpMan ){// 砍得人数没满
                    llCutIng.setVisibility(View.VISIBLE);
                    llCutSuccess.setVisibility(View.GONE);
                    tvTimeBuy.setVisibility(View.VISIBLE);
                }else{
                    setTitle("砍价成功");
                    llCutIng.setVisibility(View.GONE);
                    llCutSuccess.setVisibility(View.VISIBLE);
                }
            }

            currentPrice = data.getCurrentPrice();
            tvCutPrice.setText("已砍至：¥"+PriceUtil.dividePrice(currentPrice));
            tvCutSuccessPrice.setText("砍价成功：¥"+PriceUtil.dividePrice(currentPrice));
            id=data.getId();
//            receiveAddresId=data.getAddress();
            if(data.getUserBargainRecordVoList() != null && data.getUserBargainRecordVoList().size() != 0){
                if(data.getUserBargainRecordVoList().size() >= helpMan){
                    recordList.clear();
                    recordList.addAll(data.getUserBargainRecordVoList());
                }else {
                    recordList.addAll(data.getUserBargainRecordVoList());
                    for(int  i = data.getUserBargainRecordVoList().size(); i < helpMan; i++){
                        CutSuccessBean.UserBargainRecordVoListBean bean = new CutSuccessBean.UserBargainRecordVoListBean();
                        recordList.add(bean);
                    }
                }
            }else {
                //添加几个默认数据
                for(int i = 0; i < helpMan; i++){
                    CutSuccessBean.UserBargainRecordVoListBean bean = new CutSuccessBean.UserBargainRecordVoListBean();
                    recordList.add(bean);
                }
            }
            cutListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onShareCallback(BaseModel model) {

    }

    @OnClick({R.id.tv_cut_continue_share, R.id.tv_cut_time_buy, R.id.btn_cut_success})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cut_continue_share:
                showDialog();
                break;
            case R.id.tv_cut_time_buy://不砍了支付
                if(cutState == 1){
                    ToastUtils.showShortToast(mContext,"现在还不能支付哟，赶紧邀请好友砍价吧~");
                }else if(cutState == 2 || cutState == 3){
                    gotoPay();
                }
                break;
            case R.id.btn_cut_success://砍成功 支付
                gotoPay();
                break;
        }
    }


    /**
     * 砍价榜的Adapter
     */
    class CutListAdapter extends CommonAdapter<CutSuccessBean.UserBargainRecordVoListBean> {

        public CutListAdapter(Context context, int layoutId, List<CutSuccessBean.UserBargainRecordVoListBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, CutSuccessBean.UserBargainRecordVoListBean cutListBean, int position) {
            ImageView imgHead = holder.getView(R.id.iv_cut_list_logo);
            TextView tvName = holder.getView(R.id.tv_cut_list_name);
            TextView tvTime = holder.getView(R.id.tv_time);
            TextView tvCutMoney = holder.getView(R.id.tv_cut_list_money);

            if(XiKouUtils.isNullOrEmpty(cutListBean.getAssistUserId())){
                imgHead.setImageDrawable(getResources().getDrawable(R.drawable.ic_cut_default_logo));
                tvName.setText("等待助力");
                tvName.setTextColor(Color.parseColor("#CCCCCC"));
                tvTime.setText("");
                tvCutMoney.setText("");
            }else {
                GlideUtil.show(mContext, cutListBean.getAssistUserHeadImageUrl(), imgHead);
                tvName.setTextColor(Color.parseColor("#444444"));
                tvName.setText(cutListBean.getAssistUserName());
                tvTime.setText(cutListBean.getCreateTime());
                tvCutMoney.setText(PriceUtil.dividePrice(cutListBean.getBargainPrice()) + "元");
            }
        }
    }



    private void showDialog() {
        //显示分享对话框
        if (shareCutDialog == null) {
            shareCutDialog = new ShareCutDialog(mContext, R.style.mydialog,cutSuccessBean);
        }
        shareCutDialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void shareCallback(ShareSuccessEvent event){
        if(event.shareType == ShareType.DETAIL_CUT){
            ShareSuccessBean.ActivityGoodsConditionBean activityGoodsConditionBean = new ShareSuccessBean.ActivityGoodsConditionBean();
            activityGoodsConditionBean.setActivityId(activityId);
            activityGoodsConditionBean.setCommodityId(commodityId);
            activityGoodsConditionBean.setGoodsId(goodsId);
            activityGoodsConditionBean.setActivityType(ActivityType.ACTIVITY_CUT);
            mPresenter.shareSuccessCallback(MyApplication.userId, ShareType.DETAIL_CUT, activityGoodsConditionBean);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    /**
     * 去支付
     */
    private void gotoPay(){
        CutOrderRequestBody requestBody=new CutOrderRequestBody();
        requestBody.setActivityGoodsId(activityGoodsId);
        requestBody.setActivityId(activityId);
        requestBody.setBuyerId(MyApplication.userId);
        requestBody.setCommodityId(commodityId);
        requestBody.setCommodityModel(commodityModel);
        requestBody.setCommodityQuantity(1);
        requestBody.setCommoditySpec(commoditySpec);
        requestBody.setGoodsCode(goodsCode);
        requestBody.setGoodsId(goodsId);
        requestBody.setGoodsImageUrl(goodsImageUrl);
        requestBody.setGoodsName(goodsName);
        requestBody.setId(id);//用户发起砍价主键id
        requestBody.setMerchantId(bussId);//商家id
        requestBody.setOrderAmount(currentPrice);
        requestBody.setReceiptAddressRef("");
        requestBody.setSalePrice(linePrice);
        requestBody.setOrderSource(1);
        requestBody.setCreateType(2);
        Intent intent=new Intent(mContext,CutXiaDanActivity.class);

        intent.putExtra("request_entity",requestBody);
        intent.putExtra("postage",postage);
        if(cutState == 3 || cutState == 2){//状态是3或者2时表示砍价完成，后端是这样处理的，不要问为什么
            intent.putExtra("is_cut", true);
        }
        intent.putExtra("unit_price",unit_price);
        startActivity(intent);
    }
}
