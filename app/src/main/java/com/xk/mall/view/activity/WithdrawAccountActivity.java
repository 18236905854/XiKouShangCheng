package com.xk.mall.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.EncodeUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.immersionbar.ImmersionBar;
import com.kennyc.view.MultiStateView;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.interfaces.RvButtonListener;
import com.xk.mall.interfaces.RvListener;
import com.xk.mall.model.entity.WithdrawAccountBean;
import com.xk.mall.model.eventbean.GetAccountEventBean;
import com.xk.mall.model.impl.WithdrawAccountViewImpl;
import com.xk.mall.presenter.WithdrawAccountPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.adapter.WithdrawAccountAdapter;
import com.xk.mall.view.widget.RealNameDialog;
import com.xk.mall.view.widget.SwipeItemLayout;
import com.xk.mall.view.widget.pswkeyboard.widget.PopEnterPassword;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现账号
 */
public class WithdrawAccountActivity extends BaseActivity<WithdrawAccountPresenter> implements WithdrawAccountViewImpl {
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.btn_add_newaccount)
    Button btnAddNewaccount;
    @BindView(R.id.stateView)
    MultiStateView stateView;
    private ImageView imageView;
    private TextView  textEmptyView;

    private int isRealName;//是否实名认证
    private List<WithdrawAccountBean>  listData=new ArrayList<>();
    private WithdrawAccountAdapter adapter;
    private int operationIndex;
    private boolean isSelectAccount;//是否从切换账户页面过来的

    @Override
    protected WithdrawAccountPresenter createPresenter() {
        return new WithdrawAccountPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw_account;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        ImmersionBar.with(this).statusBarColor(R.color.color_title).titleBar(myToolbar).init();
        myToolbar.setBackgroundColor(getResources().getColor(R.color.color_title));
        myToolbar.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        listData.clear();
        mPresenter.getMyAccountList(MyApplication.userId);
    }

    @Override
    protected void initData() {
        isSelectAccount= getIntent().getBooleanExtra("isSelectAccount",false);
        adapter = new WithdrawAccountAdapter(mContext, listData);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        recycleView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        recycleView.setAdapter(adapter);
        //删除
        adapter.setRvButtonListener(new RvButtonListener() {
            @Override
            public void onItemClick(View view, int position) {
                operationIndex=position;
                mPresenter.deleteWithdrawAccount(listData.get(position).getId());
//                Log.e(TAG, "onItemClick: "+position );
            }
        });
        //itemClick
        adapter.setOnMainClickListener(new WithdrawAccountAdapter.OnMainClickListener() {
            @Override
            public void onMainClick(int position) {
                if(isSelectAccount){
                    Intent intent=new Intent();
                    intent.putExtra("accout_entity",listData.get(position));
                    setResult(1001,intent);
                    finish();
                }
            }
        });
    }

    @OnClick(R.id.btn_add_newaccount)//添加银行卡
    public void onClick() {
        isRealName = SPUtils.getInstance().getInt(Constant.USER_REAL_NAME, 0);
        if (isRealName == 0) {
            showRealNameDialog();
            return;
        }
        //已经实名认证
        showPwdDialog();

    }

    /**
     * 密码输入弹窗
     */
    private void showPwdDialog() {
        PopEnterPassword popEnterPassword = new PopEnterPassword(this);
        // 显示窗口
        popEnterPassword.showAtLocation(this.findViewById(R.id.layoutContent),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

        popEnterPassword.setOnFinishInputListener(new PopEnterPassword.OnFinishInputListener() {
            @Override
            public void onFinish(String input){
                popEnterPassword.dismiss();
                String key = "xikouxikouxikoux";
                byte[] result = EncryptUtils.encryptAES(input.getBytes(), key.getBytes(), "AES/CBC/PKCS5Padding", key.getBytes());
                mPresenter.verifyPayPwd(MyApplication.userId,EncodeUtils.base64Encode2String(result));
            }
        });

        //重置密码
        popEnterPassword.setOnSetPwdListenner(new PopEnterPassword.OnSetPwdListenner() {
            @Override
            public void OnSetPwd() {
                Intent intent = new Intent(WithdrawAccountActivity.this, SetPayPwdOneActivity.class);
                startActivityForResult(intent, 1000);
            }
        });

    }

    private void showRealNameDialog() {
        new RealNameDialog(mContext, R.style.mydialog, new RealNameDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    Intent intent = new Intent(WithdrawAccountActivity.this, RealNameActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        }).show();
    }

    /**
     * 获取账户列表成功
     * @param model
     */
    @Override
    public void onGetAccountListSuc(BaseModel<List<WithdrawAccountBean>> model) {
        List<WithdrawAccountBean> data = model.getData();
        listData.addAll(data);
        adapter.notifyDataSetChanged();

        imageView = stateView.findViewById(R.id.iv_empty_order);
        textEmptyView = stateView.findViewById(R.id.tv_empty_text);
        imageView.setImageResource(R.mipmap.ic_no_bankcard);
        textEmptyView.setText("暂无银行卡，赶紧去添加吧");
        textEmptyView.setTextColor(Color.parseColor("#999999"));
        if(listData.size()==0){
            stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }else{
            EventBus.getDefault().post(new GetAccountEventBean());
            stateView.setViewState(MultiStateView.VIEW_STATE_CONTENT);
        }
    }

    /**
     * 删除账户成功
     * @param model
     */
    @Override
    public void onDeleteAccountSuc(BaseModel<String> model) {
        WithdrawAccountBean removeBean = listData.get(operationIndex);
        EventBus.getDefault().post(removeBean);
        listData.remove(operationIndex);
        adapter.notifyItemRemoved(operationIndex);
        adapter.notifyDataSetChanged();
        if(listData.size()==0){
            stateView.setViewState(MultiStateView.VIEW_STATE_EMPTY);
        }
    }

    /**
     * 校验支付密码成功
     * @param model
     */
    @Override
    public void onGetVerityPayPwdSuc(BaseModel<String> model) {
            Intent intent = new Intent(WithdrawAccountActivity.this, AddBankCardActivity.class);
            startActivity(intent);
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        //支付密码不正确
//        if(model.getCode()==21073){
            ToastUtils.showShortToast(mContext,model.getMsg());
    }
}
