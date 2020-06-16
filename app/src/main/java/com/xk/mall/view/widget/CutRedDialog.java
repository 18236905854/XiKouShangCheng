package com.xk.mall.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xk.mall.R;
import com.xk.mall.model.entity.CutRedBean;
import com.xk.mall.model.entity.CutSuccessBean;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.SpacesItemDecoration;
import com.xk.mall.utils.XiKouUtils;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * 喜立得支付成功之后显示砍价红包的对话框
 */
public class CutRedDialog extends Dialog implements View.OnClickListener{

    private Button submitTxt;
    private ImageView cancelTxt;
    private RecyclerView rvSeeRed;
    private TextView tvCutContent;//中间文字

    private Context mContext;
    private String content;//确定按钮
    private List<CutRedBean> listData;

    public CutRedDialog(Context context, int themeResId,String content, List<CutRedBean> listData) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listData = listData;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_see_red);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        rvSeeRed = findViewById(R.id.rv_see_red);
        rvSeeRed.setLayoutManager(new LinearLayoutManager(mContext));
        submitTxt =  findViewById(R.id.btn_submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = findViewById(R.id.img_cha);
        cancelTxt.setOnClickListener(this);
        tvCutContent = findViewById(R.id.tv_cut_content);
        if(!XiKouUtils.isNullOrEmpty(content)){
            tvCutContent.setText(content);
            submitTxt.setText("知道了");
        }else {
            submitTxt.setText("确定");
        }
        SeeRedAdapter seeRedAdapter = new SeeRedAdapter(mContext, R.layout.item_see_red, listData);
        rvSeeRed.setAdapter(seeRedAdapter);
        rvSeeRed.addItemDecoration(new SpacesItemDecoration(1));
    }

    class SeeRedAdapter extends CommonAdapter<CutRedBean>{

        public SeeRedAdapter(Context context, int layoutId, List<CutRedBean> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, CutRedBean cutListBean, int position) {
            ImageView imgHead = holder.getView(R.id.iv_cut_list_logo);
            TextView tvName = holder.getView(R.id.tv_cut_list_name);
            TextView tvTime = holder.getView(R.id.tv_time);
            TextView tvCutMoney = holder.getView(R.id.tv_cut_list_money);
            GlideUtil.show(mContext, cutListBean.getAssistUserHeadImageUrl(), imgHead);
            tvName.setTextColor(Color.parseColor("#444444"));
            tvName.setText(cutListBean.getAssistUserName());
            tvTime.setText(cutListBean.getCreateTime());
            tvCutMoney.setText(PriceUtil.dividePrice(cutListBean.getRedPackAmount()) + "元");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_cha:
                this.dismiss();
                break;
            case R.id.btn_submit:
                this.dismiss();
                break;
        }
    }

}
