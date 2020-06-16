package com.xk.mall.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.RedBagBean;
import com.xk.mall.model.entity.WithdrawAccountBean;
import com.xk.mall.model.eventbean.GetAccountEventBean;
import com.xk.mall.model.eventbean.HideMoneyBean;
import com.xk.mall.model.eventbean.RefreshMoney;
import com.xk.mall.model.eventbean.WithdrawSuccessBean;
import com.xk.mall.model.impl.RedBagImpl;
import com.xk.mall.presenter.RedBagPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.view.widget.CommomDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName MyRedBagActivity
 * Description 我的钱包页面
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class MyRedBagActivity extends BaseActivity<RedBagPresenter> implements RedBagImpl {

    @BindView(R.id.tv_red_bag_money)
    TextView tvRedBagMoney;//显示当前红包总余额
    @BindView(R.id.tv_red_bag_available)
    TextView tvAvailable;//可用余额
    @BindView(R.id.tv_red_bag_review)
    TextView tvReview;//提现审核金额
    @BindView(R.id.iv_red_bag_see_money)
    ImageView ivSeeMoney;//眼睛按钮，点击显示和隐藏余额
    @BindView(R.id.tv_red_experience_balance)
    TextView tvExperience;//体验金额度
    @BindView(R.id.tv_red_bag_cold)
    TextView tvColdMoney;//冻结金额
    @BindView(R.id.rl_red_bag_income)
    RelativeLayout rlIncome;//待结算收入
    @BindView(R.id.tv_red_bag_income)
    TextView tvIncome;//待结算金额
    @BindView(R.id.rl_red_bag_detail)
    RelativeLayout rlDetail;//红包明细
    @BindView(R.id.rl_red_bag_cash_detail)
    RelativeLayout rlCashDetail;//提现明细
    @BindView(R.id.rl_red_bag_pay_psd)
    RelativeLayout rlModifyPayPsd;//修改支付密码
    @BindView(R.id.rl_red_bag_bind_card)
    RelativeLayout rlBindCard;//提现账号绑定

    @BindView(R.id.tv_realname_status)
    TextView tvRealnameStatus;
    @BindView(R.id.rl_my_real_name)
    RelativeLayout rlMyRealName;
    @BindView(R.id.tv_red_bag_tip)
    TextView tvRedBagTip;
    @BindView(R.id.tv_red_bag_cold_tip)
    TextView tvRedBagColdTip;
    @BindView(R.id.rl_jiaoyi_detail)
    RelativeLayout rlJiaoyiDetail;
    @BindView(R.id.tv_pwd_status)
    TextView tvPwdStatus;

    private RedBagBean redBagBean;//红包页面的Bean,用来保存数据
    private boolean isHideCash = false;//是否隐藏当前余额
    private int isSetPwd;//是否设置支付密码
    private int isRealName;//是否实名认证
    private int rate;//手续费率
    private boolean isHaveCard;

    @Override
    protected RedBagPresenter createPresenter() {
        return new RedBagPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_red_bag;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        showRightIcon(true);
        setShowDialog(false);
        setTitle("我的账户");
        setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setRightDrawable(R.drawable.red_bag_question);
        setLeftDrawable(R.drawable.ic_back_white);
        setStatusBar(Color.parseColor("#444444"));
        setDarkStatusIcon(false);//状态栏图标反色
        setOnRightIconClickListener(v -> {
            //点击跳转页面
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(Constant.INTENT_URL, Constant.commonProblem);
            intent.putExtra(Constant.INTENT_TITLE, "常见问题");
            ActivityUtils.startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRealName = SPUtils.getInstance().getInt(Constant.USER_REAL_NAME, 0);
        isSetPwd = SPUtils.getInstance().getInt(Constant.USER_SET_PWD,0);
        tvPwdStatus.setText(isSetPwd ==1 ? "已设置" :"未设置");
        tvRealnameStatus.setText(isRealName==1 ? "已认证" : "未认证");
        mPresenter.getRedBag(MyApplication.userId);
    }

    @Override
    protected void initData() {
        isSetPwd=SPUtils.getInstance().getInt(Constant.USER_SET_PWD,0);
        if(isSetPwd==1){
            tvPwdStatus.setText("已设置");
        }else{
            tvPwdStatus.setText("未设置");
            mPresenter.getSetPayPwd(MyApplication.userId);
        }
        isHideCash = SPUtils.getInstance().getBoolean(Constant.IS_HIDE_CASH, false);
    }


    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    /**
     * 提现成功之后需要重新获取数据并绑定
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getWithdrawSuccess(WithdrawSuccessBean withdrawSuccessBean){
        MyApplication.isPaySuccess = true;
        mPresenter.getRedBag(MyApplication.userId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getAccountSuccess(GetAccountEventBean eventBean){
        isHaveCard = true;
    }

    @OnClick({R.id.iv_red_bag_see_money, R.id.tv_red_bag_cash, R.id.rl_red_bag_income,
            R.id.rl_red_bag_detail, R.id.rl_red_bag_cash_detail, R.id.rl_red_bag_pay_psd,
            R.id.rl_red_bag_bind_card, R.id.rl_my_real_name, R.id.rl_jiaoyi_detail, R.id.tv_transfer})
    public void clickView(View view) {
        switch (view.getId()) {
            case R.id.iv_red_bag_see_money://查看余额
                showOrHideMoney();
                break;

            case R.id.tv_red_bag_cash://去提现
                if(isRealName != 1){
                    new CommomDialog(mContext, R.style.mydialog, "请先实名认证", (dialog, confirm) -> {
                        if (confirm) {
                            ActivityUtils.startActivity(RealNameActivity.class);
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }
                if(isSetPwd != 1){
                    new CommomDialog(mContext, R.style.mydialog, "请先设置支付密码", (dialog, confirm) -> {
                        if (confirm) {
                            ActivityUtils.startActivity(SetPayPwdOneActivity.class);
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }
                if(!isHaveCard){
                    new CommomDialog(mContext, R.style.mydialog, "请先绑定银行卡", (dialog, confirm) -> {
                        if (confirm) {
                            ActivityUtils.startActivity(WithdrawAccountActivity.class);
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }

                if(redBagBean != null){
                    if(redBagBean.balance <= 0){
                        ToastUtils.showShortToast(mContext, "提现余额不足");
                    }else {
                        Intent intent=new Intent(mContext,MyWithdrawActivity.class);
                        intent.putExtra(MyWithdrawActivity.MY_YU_ER,redBagBean.balance);
                        intent.putExtra(MyWithdrawActivity.MY_RATE, rate);
                        intent.putExtra(MyWithdrawActivity.MIN_MONEY, redBagBean.minMoney);
                        intent.putExtra(MyWithdrawActivity.MAX_MONEY, redBagBean.maxMoney);
                        intent.putExtra(MyWithdrawActivity.CENTER_MONEY, redBagBean.centerMoney);
                        intent.putExtra(MyWithdrawActivity.CASH_TIME, redBagBean.arrivalTime);
                        ActivityUtils.startActivity(intent);
                    }
                }
                break;

            case R.id.rl_red_bag_income://待结算收入
                ActivityUtils.startActivity(SettlementingActivity.class);
                break;

            case R.id.rl_red_bag_detail://红包明细
                ActivityUtils.startActivity(RedBagDetailActivity.class);
                break;
            case R.id.rl_jiaoyi_detail://交易明细
                ActivityUtils.startActivity(RedBagDetailActivity.class);
                break;

            case R.id.rl_red_bag_cash_detail://提现明细
                ActivityUtils.startActivity(WithDrawDetailActivity.class);
                break;

            case R.id.rl_red_bag_pay_psd://修改支付密码
                if(isRealName == 1){
                    ActivityUtils.startActivity(SetPayPwdOneActivity.class);
                }else {
                    ToastUtils.showShortToast(mContext, "请先实名认证");
                    ActivityUtils.startActivity(RealNameActivity.class);
                }
                break;

            case R.id.rl_red_bag_bind_card://提现账号绑定
                if(isRealName != 1){
                    new CommomDialog(mContext, R.style.mydialog, "请先实名认证", (dialog, confirm) -> {
                        if (confirm) {
                            ActivityUtils.startActivity(RealNameActivity.class);
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }
                if(isSetPwd == 1){
                    ActivityUtils.startActivity(WithdrawAccountActivity.class);
                }else {
                    ToastUtils.showShortToast(mContext, "请先设置支付密码");
                    ActivityUtils.startActivity(SetPayPwdOneActivity.class);
                }
                break;
            case R.id.rl_my_real_name://我的认证
                ActivityUtils.startActivity(RealNameActivity.class);
                break;

            case R.id.tv_transfer://转账按钮
                if(isRealName != 1){
                    new CommomDialog(mContext, R.style.mydialog, "请先实名认证", (dialog, confirm) -> {
                        if (confirm) {
                            ActivityUtils.startActivity(RealNameActivity.class);
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }
                if(isSetPwd != 1){
                    new CommomDialog(mContext, R.style.mydialog, "请先设置支付密码", (dialog, confirm) -> {
                        if (confirm) {
                            ActivityUtils.startActivity(SetPayPwdOneActivity.class);
                            dialog.dismiss();
                        }
                    }).show();
                    return;
                }
                //跳转转账页面
                if(redBagBean != null){
                    if(redBagBean.balance <= 0){
                        ToastUtils.showShortToast(mContext, "转账余额不足");
                    }else {
                        Intent intent1 = new Intent(mContext, TransferActivity.class);
                        intent1.putExtra(TransferActivity.BALANCE, redBagBean.balance);
                        ActivityUtils.startActivity(intent1);
                    }
                }
                break;
        }
    }

    /**
     * 显示或者隐藏金额
     */
    private void showOrHideMoney() {
        if (isHideCash) {
            isHideCash = false;
            ivSeeMoney.setImageResource(R.drawable.red_bag_see_money);
            if(redBagBean != null && redBagBean.totalSum != 0){
                tvRedBagMoney.setText("" + PriceUtil.dividePrice(redBagBean.totalSum));
            }else {
                tvRedBagMoney.setText("0.00");
            }
            SPUtils.getInstance().put(Constant.IS_HIDE_CASH, false);
            if(redBagBean != null && redBagBean.onWay != 0){
                tvColdMoney.setText("" + PriceUtil.dividePrice(redBagBean.onWay));
            }else {
                tvColdMoney.setText("0.00");
            }
            if(redBagBean != null && redBagBean.frozen != 0){
                tvReview.setText(PriceUtil.dividePrice(redBagBean.frozen));
            }else {
                tvReview.setText("0.00");
            }
            if(redBagBean != null && redBagBean.balance != 0){
                tvAvailable.setText(PriceUtil.dividePrice(redBagBean.balance));
            }else {
                tvAvailable.setText("0.00");
            }
            if(redBagBean != null && redBagBean.experienceBalance != 0){
                tvExperience.setText(PriceUtil.dividePrice(redBagBean.experienceBalance));
            }else {
                tvExperience.setText("0.00");
            }
        } else {
            isHideCash = true;
            ivSeeMoney.setImageResource(R.drawable.red_bag_hide_money);
            tvReview.setText("******");
            tvAvailable.setText("******");
            tvRedBagMoney.setText("******");
            tvColdMoney.setText("******");
            tvExperience.setText("******");
            SPUtils.getInstance().put(Constant.IS_HIDE_CASH, true);
        }
        EventBus.getDefault().post(new HideMoneyBean(isHideCash));
    }

    /**
     * 获取用户红包信息的回调
     */
    @Override
    public void onGetSuccess(BaseModel<RedBagBean> o) {
        hideLoading();
        redBagBean = o.getData();
        bindData(redBagBean);
    }

//    @Override
//    public void onGetAccountListSuc(BaseModel<List<WithdrawAccountBean>> model) {
//        if(model != null && model.getData() != null && model.getData().size() != 0){
//            isHaveCard = true;
//        }
//    }

    //是否设置支付密码回掉
    @Override
    public void onGetSetPwd(BaseModel model) {
        if(model!=null){
            boolean isSet= (boolean) model.getData();
            if(!isSet){
                tvPwdStatus.setText("未设置");
            }
        }
    }

    private void bindData(RedBagBean redBagBean) {
        if(redBagBean != null){
            tvColdMoney.setText(PriceUtil.dividePrice(redBagBean.onWay));
            tvIncome.setText(PriceUtil.dividePrice(redBagBean.onWay));
            rate = redBagBean.rate;
            if (isHideCash) {
                tvColdMoney.setText("******");
                tvRedBagMoney.setText("******");
                tvReview.setText("******");
                tvAvailable.setText("******");
                tvExperience.setText("******");
                ivSeeMoney.setImageResource(R.drawable.red_bag_hide_money);
            } else {
                ivSeeMoney.setImageResource(R.drawable.red_bag_see_money);
                tvColdMoney.setText(PriceUtil.dividePrice(redBagBean.onWay));
                tvReview.setText(PriceUtil.dividePrice(redBagBean.frozen));
                tvAvailable.setText(PriceUtil.dividePrice(redBagBean.balance));
                tvExperience.setText(PriceUtil.dividePrice(redBagBean.experienceBalance));
                tvRedBagMoney.setText(PriceUtil.dividePrice(redBagBean.totalSum));
                RefreshMoney refreshMoney = new RefreshMoney();
                refreshMoney.setBalance(redBagBean.balance);
                EventBus.getDefault().post(refreshMoney);
            }
            isHaveCard = redBagBean.bankCardList != null && redBagBean.bankCardList.size() != 0;
        }else {
            isHaveCard = false;
        }
    }

}
