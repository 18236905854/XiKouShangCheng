package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.base.BasePresenter;

import butterknife.BindView;

/**
 * @ClassName: RemarksActivity
 * @Description: 备注页面
 * @Author: 卿凯
 * @Date: 2019/10/14/014 10:14
 * @Version: 1.0
 */
public class RemarksActivity extends BaseActivity {
    @BindView(R.id.et_remarks_content)
    EditText etRemarksContent;
    @BindView(R.id.tv_remarks_size)
    TextView tvRemarksSize;
    @BindView(R.id.btn_remarks_sure)
    Button btnRemarksSure;
    /**intent传递过来的备注文字的key*/
    public static String REMARKS_CONTENT = "remarks_content";
    /**intent传递过来的是否可以编辑的key*/
    public static String REMARKS_ISEDIT = "is_edit";


    private int maxLength = 50;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remarks;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("订单备注");
    }

    @Override
    protected void initData() {
        String remarks = getIntent().getStringExtra(REMARKS_CONTENT);
        boolean isEdit = getIntent().getBooleanExtra(REMARKS_ISEDIT, true);
        if(!isEdit){
            etRemarksContent.setEnabled(false);
            tvRemarksSize.setVisibility(View.GONE);
            btnRemarksSure.setVisibility(View.GONE);
        }else {
            etRemarksContent.setEnabled(true);
            tvRemarksSize.setVisibility(View.VISIBLE);
            btnRemarksSure.setVisibility(View.VISIBLE);
        }
        if(remarks != null){
            etRemarksContent.setText(remarks);
        }
        etRemarksContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.toString().trim().length();
                tvRemarksSize.setText(length + "/" + maxLength);
            }
        });

        btnRemarksSure.setOnClickListener(v -> {
            String content = etRemarksContent.getText().toString().trim();
            Intent intent = new Intent();
            intent.putExtra("remarks_content", content);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

}
