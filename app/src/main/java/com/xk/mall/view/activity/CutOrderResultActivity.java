package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.CutRedBean;
import com.xk.mall.model.entity.CutSuccessBean;
import com.xk.mall.model.eventbean.FinishDetailEventBean;
import com.xk.mall.model.eventbean.PaySuccessBean;
import com.xk.mall.model.impl.CutOrderResultViewImpl;
import com.xk.mall.presenter.CutOrderResultPresenter;
import com.xk.mall.utils.OrderType;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.CutRedDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ClassName PayOrderResultActivity
 * Description 支付结果页面
 * Author
 * Date
 * Version
 */
public class CutOrderResultActivity extends BaseActivity<CutOrderResultPresenter> implements CutOrderResultViewImpl {
    private static final String TAG = "PayOrderResultActivity";
    public static final String PAY_STATUS = "pay_status";//支付是否成功
    public static final String PAY_ORDER_NO = "pay_order_no";//支付的订单号
    public static final String PAY_ORDER_TYPE = "pay_order_type";//支付的订单号类型
    public static final String PAY_MOUNT = "pay_mount";//支付的金额
    public static final String PAY_COUPON = "pay_coupon";//支付的优惠券
    public static final String CUT_ID = "cut_id";//砍价进度ID
    @BindView(R.id.img_pay_status)
    ImageView imgPayStatus;
    @BindView(R.id.tv_pay_status)
    TextView tvPayStatus;
    @BindView(R.id.btn_back_home)
    Button btnBackHome;
    @BindView(R.id.btn_to_do)
    Button btnToDo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_des)
    TextView tvTitleDes;
    @BindView(R.id.rl_title_content)
    RelativeLayout rlTitleContent;
    @BindView(R.id.img_goods1)
    ImageView imgGoods1;
    @BindView(R.id.tv_goodsName)
    TextView tvGoodsName1;
    @BindView(R.id.tv_sale_price)
    TextView tvPrice1;
    @BindView(R.id.tv_line_price)
    TextView tvLinePrice1;
    @BindView(R.id.img_goods2)
    ImageView imgGoods2;
    @BindView(R.id.tv_goodsName2)
    TextView tvGoodsName2;
    @BindView(R.id.tv_price2)
    TextView tvPrice2;
    @BindView(R.id.tv_line_price2)
    TextView tvLinePrice2;
    @BindView(R.id.img_goods3)
    ImageView imgGoods3;
    @BindView(R.id.tv_goodsName3)
    TextView tvGoodsName3;
    @BindView(R.id.tv_price3)
    TextView tvPrice3;
    @BindView(R.id.tv_line_price3)
    TextView tvLinePrice3;
    @BindView(R.id.tv_pay_coupon)
    TextView tvPayCoupon;//显示支付金额  优惠券40 实际支付160.00
    @BindView(R.id.tv_see_red)
    TextView tvSeeRed;//查看红包

    private boolean payStatus;
    private String orderNo;
    private String cutId;
    private int orderType;//订单所属类型
    private int coupon;//优惠券金额
    private int orderMount;//实际支付金额
    private List<CutRedBean> listData = new ArrayList<>();

    @Override
    protected CutOrderResultPresenter createPresenter() {
        return new CutOrderResultPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cut_order_result;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        toolbar_title.setText("支付订单");
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(this);
        orderNo = getIntent().getStringExtra(PAY_ORDER_NO);
        cutId = getIntent().getStringExtra(CUT_ID);
        orderType=getIntent().getIntExtra(PAY_ORDER_TYPE,0);
        payStatus = getIntent().getBooleanExtra(PAY_STATUS, false);
        coupon = getIntent().getIntExtra(PAY_COUPON, 0);
        orderMount = getIntent().getIntExtra(PAY_MOUNT, 0);
        if (payStatus) {
            imgPayStatus.setImageResource(R.mipmap.ic_pay_success);
            if(orderType == OrderType.OTO_TYPE || orderType == OrderType.OTHER_PAY_TYPE){
                btnToDo.setVisibility(View.GONE);
            }else {
                btnToDo.setVisibility(View.VISIBLE);
            }
            EventBus.getDefault().post(new PaySuccessBean(orderNo, orderType));
            tvPayStatus.setText("支付成功");
            btnToDo.setText("查看订单");
            MyApplication.isPaySuccess = true;
            tvPayCoupon.setVisibility(View.VISIBLE);
            if(coupon == 0){
                tvPayCoupon.setText("实际支付" + PriceUtil.dividePrice(orderMount));
            }else {
                tvPayCoupon.setText("优惠券" + PriceUtil.dividePrice(coupon) + "  实际支付" + PriceUtil.dividePrice(orderMount));
            }
        } else {
            tvPayCoupon.setVisibility(View.GONE);
            imgPayStatus.setImageResource(R.mipmap.ic_pay_fail);
            tvPayStatus.setText("支付失败");
            btnToDo.setText("重新支付");
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @OnClick({R.id.btn_back_home, R.id.btn_to_do, R.id.tv_see_red})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back_home:
                MyApplication.getInstance().closeActivity();
                ActivityUtils.startActivity(CutMainActivity.class);
                break;
            case R.id.btn_to_do:
                if (payStatus) {
//                    ToastUtils.showShortToast(mContext, "查看订单");
                    goToOrderDetail();
                } else {
                    finish();
                }

                break;
            case R.id.tv_see_red:
                //点击查看红包
                if(!XiKouUtils.isNullOrEmpty(cutId)){
                    mPresenter.getCutRedList(cutId);
                }
                break;
        }
    }

    /**
     * 跳订单详情页面
     */
    private void goToOrderDetail(){
        Intent intent=new Intent();
        if(orderType==OrderType.ZERO_TYPE){
            intent.setClass(mContext,ZeroOrderDetailActivity.class);
        }
        if(orderType==OrderType.MANY_TYPE){
            intent.setClass(mContext,ManyBuyOrderDetailActivity.class);
        }
        if(orderType==OrderType.CUT_TYPE){
            intent.setClass(mContext,CutOrderDetailActivity.class);

        }
        if(orderType==OrderType.WUG_TYPE){
            intent.setClass(mContext,WuGOrderDetailActivity.class);
        }
        if(orderType==OrderType.GLOBAL_TYPE){
            intent.setClass(mContext,GlobalBuyerOrderDetailActivity.class);
        }
        if(orderType==OrderType.GROUP_TYPE){
            intent.setClass(mContext,GroupOrderDetailActivity.class);
        }
        if(orderType == OrderType.NEW_PERSON_TYPE){
            intent.setClass(mContext,NewPersonOrderDetailActivity.class);
        }
        intent.putExtra("order_no",orderNo);
        if(intent.resolveActivity(getPackageManager()) != null){
            ActivityUtils.startActivity(intent);
            finish();
            //关闭之前的订单详情
            EventBus.getDefault().post(new FinishDetailEventBean());
        }
    }

    //根据type 确定订单类型
    private int getOrderType(int type){
        Intent intent=new Intent();
        int result=0;
        switch (type){
            case 1:
                result=OrderType.ZERO_TYPE;
                break;
            case 2:
                result=OrderType.MANY_TYPE;
                break;
            case 3:
                result=OrderType.CUT_TYPE;
                break;
            case 4:
                result=OrderType.WUG_TYPE;
                break;
            case 5:
                result=OrderType.GLOBAL_TYPE;
                break;
            case 6:
                result=OrderType.GROUP_TYPE;
                break;
            case 7:
                result=OrderType.OTO_TYPE;
                break;

        }
        return result;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //do something.
            Log.e(TAG, "dispatchKeyEvent: ");
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onGetCutRedSuccess(BaseModel<List<CutRedBean>> baseModel) {
        if(baseModel != null && baseModel.getData() != null && baseModel.getData().size() != 0){
            listData = baseModel.getData();
            if(listData != null && listData.size() != 0){
                CutRedDialog dialog = new CutRedDialog(mContext, R.style.mydialog,"", listData);
                dialog.show();
            }else {
                CutRedDialog dialog = new CutRedDialog(mContext, R.style.mydialog,
                        "现金红包正在发放中请稍等", new ArrayList<CutRedBean>());
                dialog.show();
            }
        }
    }
}
