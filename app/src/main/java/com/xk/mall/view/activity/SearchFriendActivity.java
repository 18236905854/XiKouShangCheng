package com.xk.mall.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.JsonUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.KeyValueBean;
import com.xk.mall.model.entity.TransferInfoBean;
import com.xk.mall.model.impl.TransferViewImpl;
import com.xk.mall.presenter.TransferPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.ClearEditText;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import butterknife.BindView;

/**
 * @ClassName: SearchFriendActivity
 * @Description: 搜索好友页面
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class SearchFriendActivity extends BaseActivity<TransferPresenter> implements TransferViewImpl {
    @BindView(R.id.et_friend_phone)
    ClearEditText etFriendPhone;//用户输入的手机号码
    @BindView(R.id.tv_friend_phone)
    TextView tvFriendPhone;//查询后显示用户手机号
    @BindView(R.id.tv_friend_name)
    TextView tvFriendName;//查询后显示用户昵称,如果用户不存在显示“立即推广”
    @BindView(R.id.tv_friend_history)
    RecyclerView rvFriendHistory;//搜索记录
    @BindView(R.id.rl_friend)
    RelativeLayout rlFriend;//用户信息布局
    private boolean isGetInfo;//是否正确获取用户信息
    private String phone;//用户输入的手机号码
    //用来保存搜索记录
//    private LinkedHashMap<String, KeyValueBean> searchPhone = new LinkedHashMap<>();
    private SearchListAdapter searchListAdapter;
    private List<KeyValueBean> phones = new ArrayList<>();
    private List<KeyValueBean> newPhones = new ArrayList<>();
    private boolean isSearching;


    @Override
    protected TransferPresenter createPresenter() {
        return new TransferPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_friend;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("搜索喜扣好友");
    }

    class SearchListAdapter extends CommonAdapter<KeyValueBean>{

        public SearchListAdapter(Context context, int layoutId, List<KeyValueBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, KeyValueBean keyValueBean, int position) {
            holder.setText(R.id.tv_search_name, keyValueBean.value);
            holder.setText(R.id.tv_search_phone, keyValueBean.key);
        }
    }

    @Override
    protected void initData() {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        rvFriendHistory.setLayoutManager(layoutManager);
        phone = getIntent().getStringExtra("phone");
        if(!XiKouUtils.isNullOrEmpty(phone)){
            tvFriendPhone.setText(phone);
            mPresenter.getTransferInfoByPhone(phone);
        }
        String phoneStr = SPUtils.getInstance().getString(Constant.PAY_SEARCH_PHONE_KEY);
        if(!XiKouUtils.isNullOrEmpty(phoneStr)){
            Type type = new TypeToken<List<KeyValueBean>>(){}.getType();
            phones = GsonUtils.fromJson(phoneStr, type);
            if(phones.size() >= 9){
                phones = phones.subList(0,9);
            }
            newPhones.addAll(phones);
//            for(KeyValueBean keyValueBean : phones){
//                searchPhone.put(keyValueBean.key, keyValueBean);
//            }
        }
        searchListAdapter = new SearchListAdapter(mContext, R.layout.item_search_list, phones);
        rvFriendHistory.setAdapter(searchListAdapter);
        searchListAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                //将电话号码回传过去，关闭页面
                KeyValueBean bean = phones.get(position);
                Intent intent = new Intent();
                intent.putExtra("phone", bean.key);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        etFriendPhone.setOnEditorActionListener((v, actionId, event) -> {
            phone = etFriendPhone.getText().toString().trim();
            String myPhone = SPUtils.getInstance().getString(Constant.USER_MOBILE);
            Logger.e("开始搜索");
            if(XiKouUtils.isNullOrEmpty(phone)) {
                ToastUtils.showShort("请输入手机号码");
            }else if(phone.equals(myPhone)){
                ToastUtils.showShort("不能请自己代付哦");
            }else if(phone.length() < 11 || RegexUtils.isTel(phone)){
                ToastUtils.showShort("手机号码输入有误，请重新输入");
            }else {
                if(!isSearching){
                    isSearching = true;
                    mPresenter.getTransferInfoByPhone(phone);
                }
            }
            return true;
        });

        etFriendPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isGetInfo = false;
                if(XiKouUtils.isNullOrEmpty(s.toString().trim())){
                    tvFriendName.setText("");
                    tvFriendPhone.setText("");
                    etFriendPhone.setTextSize(18);
                    etFriendPhone.setTextColor(Color.parseColor("#CCCCCC"));
                }else {
                    etFriendPhone.setTextSize(25);
                    etFriendPhone.setTextColor(Color.parseColor("#4A4A4A"));
                }
            }
        });

        rlFriend.setOnClickListener(v -> {
            if(isGetInfo){
                Intent intent = new Intent();
                intent.putExtra("phone", phone);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });


    }

    @Override
    public void onGetDataByPhone(BaseModel<TransferInfoBean> model) {
        isSearching = false;
        if(model != null && model.getData() != null){
            tvFriendName.setText(model.getData().getNickName());
            tvFriendName.setTextColor(Color.parseColor("#9B9B9B"));
            tvFriendPhone.setText(phone);
            isGetInfo = true;
            tvFriendPhone.setOnClickListener(null);
//            searchPhone.remove(phone);
//            searchPhone.put(phone, new KeyValueBean(phone, model.getData().getNickName()));
            for(int i = newPhones.size() - 1; i >= 0; i--){
                KeyValueBean bean = newPhones.get(i);
                if(bean.key.equals(phone)){
                    newPhones.remove(i);
                }
            }
            newPhones.add(0,new KeyValueBean(phone, model.getData().getNickName()));
//            ListIterator<Map.Entry> listIterator = new ArrayList<Map.Entry>(
//                    searchPhone.entrySet()).listIterator(searchPhone.size());
//            while (listIterator.hasPrevious()){
//                newPhones.add((KeyValueBean) listIterator.previous().getValue());
//            }
            phones.clear();
            if(newPhones.size() >= 9){
                phones.addAll(newPhones.subList(0, 9));
            }else {
                phones.addAll(newPhones);
            }
            searchListAdapter.notifyDataSetChanged();
            Type type = new TypeToken<List<KeyValueBean>>(){}.getType();
            String phoneStr = GsonUtils.toJson(phones, type);
            SPUtils.getInstance().put(Constant.PAY_SEARCH_PHONE_KEY, phoneStr);
            //刷新recyclerView
        }else {
            isGetInfo = false;
            tvFriendPhone.setText("您的好友还未注册喜扣");
            tvFriendName.setTextColor(Color.parseColor("#20ABE5"));
            tvFriendName.setText("立即推广");
            tvFriendName.setOnClickListener(v -> startActivity(new Intent(mContext, MyPromotionActivity.class)));
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        isGetInfo = false;
        isSearching = false;
        tvFriendPhone.setText("您的好友还未注册喜扣");
        tvFriendName.setTextColor(Color.parseColor("#20ABE5"));
        tvFriendName.setText("立即推广");
        tvFriendName.setOnClickListener(v -> startActivity(new Intent(mContext, MyPromotionActivity.class)));
    }
}
