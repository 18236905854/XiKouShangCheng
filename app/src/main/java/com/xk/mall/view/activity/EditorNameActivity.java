package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.orhanobut.logger.Logger;
import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.eventbean.NickNameEventBean;
import com.xk.mall.utils.Constant;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

/**
 * ClassName EditorNameActivity
 * Description 修改昵称页面
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class EditorNameActivity extends BaseActivity {

    @BindView(R.id.et_editor_name)
    EditText etEditorName;
    @BindView(R.id.iv_editor_name_clear)
    ImageView ivEditorNameClear;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_editor_name;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("修改昵称");
        showRight(true);
        setRightText("保存");
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String name = intent.getStringExtra(Constant.INTENT_NAME);
        if(!TextUtils.isEmpty(name)){
            etEditorName.setText(name);
            ivEditorNameClear.setVisibility(View.VISIBLE);
        }

        etEditorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = s.toString();
                if(TextUtils.isEmpty(name)){
                    ivEditorNameClear.setVisibility(View.GONE);
                }else {
                    ivEditorNameClear.setVisibility(View.VISIBLE);
                }
            }
        });

        ivEditorNameClear.setOnClickListener(v -> {
          etEditorName.setText("");
        });

        setOnRightClickListener(v -> {
            Logger.e("setOnRightClickListener");
            String lastName = etEditorName.getText().toString();
            if(TextUtils.isEmpty(lastName)){
                showToast("用户昵称不能为空");
            }else if(lastName.length() >= 20){
                showToast("用户昵称格式不正确");
            }else {
                EventBus.getDefault().post(new NickNameEventBean(lastName));
                finish();
            }
        });
    }
}
