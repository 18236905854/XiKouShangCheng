package com.xk.mall.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.BankBean;
import com.xk.mall.model.entity.ResultPageBean;
import com.xk.mall.model.impl.ChooseBankViewImpl;
import com.xk.mall.presenter.ChooseBankPresenter;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.view.widget.ClearEditText;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @ClassName: ChooseBankActivity
 * @Description: 选择银行卡页面
 * @Author: 卿凯
 * @Date: 2019/10/8/008 14:37
 * @Version: 1.0
 */
public class ChooseBankActivity extends BaseActivity<ChooseBankPresenter> implements ChooseBankViewImpl {

    @BindView(R.id.et_search_bank)
    ClearEditText etSearchBank;
    @BindView(R.id.tv_search_bank)
    TextView tvSearch;
    @BindView(R.id.rvChooseBank)
    RecyclerView rvChooseBank;
    @BindView(R.id.refresh_choose_bank)
    SmartRefreshLayout refreshLayout;
    int page = 1;
    private List<BankBean> bankBeans;
    private BankAdapter bankAdapter;
    private String name = "";
    private int limit = 200;

    @Override
    protected ChooseBankPresenter createPresenter() {
        return new ChooseBankPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_choose_bank;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("选择银行卡");
    }

    @Override
    protected void initData() {
        //获取数据
        mPresenter.getBankList(name, page, limit);
        bankBeans = new ArrayList<>();
        bankAdapter = new BankAdapter(mContext, R.layout.item_choose_bank, bankBeans);
        rvChooseBank.setLayoutManager(new LinearLayoutManager(mContext));
        rvChooseBank.setAdapter(bankAdapter);
        bankAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                BankBean bankBean = bankBeans.get(position);
                Intent intent = new Intent();
                intent.putExtra("bankName", bankBean.getName());
                intent.putExtra("bankCode", bankBean.getCode());
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout -> {
            page += 1;
            mPresenter.getBankList(name, page, limit);
        });

        etSearchBank.setOnEditorActionListener((v, actionId, event) -> {
            name = v.getText().toString().trim();
            page = 1;
            mPresenter.getBankList(name, page, limit);
            return false;
        });

        etSearchBank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = s.toString();
                if(TextUtils.isEmpty(name)){
                    page = 1;
                    mPresenter.getBankList(name, page, limit);
                }
            }
        });

        tvSearch.setOnClickListener(v -> {
            page = 1;
            mPresenter.getBankList(name, page, limit);
        });
    }

    @Override
    public void onGetBankListSuccess(BaseModel<ResultPageBean<BankBean>> model) {
        refreshLayout.finishLoadMore();
        if(model != null && model.getData() != null && model.getData().getResult() != null&&
                model.getData().getResult().size() != 0){
            //绑定数据
            if(page == 1){
                bankBeans.clear();
            }
            bankBeans.addAll(model.getData().getResult());
            bankAdapter.notifyDataSetChanged();
            if(model.getData().getResult().size() < limit){
                refreshLayout.setEnableLoadMore(false);
            }else {
                refreshLayout.setEnableLoadMore(true);
            }
        }else {
            //显示空页面
        }
    }

    class BankAdapter extends CommonAdapter<BankBean>{

        public BankAdapter(Context context, int layoutId, List<BankBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, BankBean bankBean, int position) {
            ImageView ivLogo = holder.getView(R.id.ivBankIcon);
            GlideUtil.show(mContext, bankBean.getImage(), ivLogo);
            holder.setText(R.id.tvBankName, bankBean.getName());
        }
    }
}
