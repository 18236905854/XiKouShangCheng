package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.RealNameInfoBean;
import com.xk.mall.model.entity.RealNameRequestBody;
import com.xk.mall.model.entity.UploadLogoBean;
import com.xk.mall.model.impl.RealNameViewImpl;
import com.xk.mall.presenter.RealNamePresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.ImagePickerImageLoader;
import com.xk.mall.view.widget.ClearEditText;
import com.xk.mall.view.widget.CommonPopupWindow;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的认证（实名认证）
 */
public class RealNameActivity extends BaseActivity<RealNamePresenter> implements RealNameViewImpl,CommonPopupWindow.ViewInterface {
    private final int REQUEST_CODE_ALBUM = 1;//打开相册
    private final int REQUEST_CODE_CAMERA = 2;//打开相机
    @BindView(R.id.img_top_status)
    ImageView imgTopStatus;
    @BindView(R.id.tv_top_status)
    TextView tvTopStatus;
    @BindView(R.id.ll_top_status)
    LinearLayout llTopStatus;
    @BindView(R.id.edit_name)
    ClearEditText editName;
    @BindView(R.id.edit_card_num)
    ClearEditText editCardNum;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.rl_real_status)
    RelativeLayout rlRealStatus;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rl_real_time)
    RelativeLayout rlRealTime;
    @BindView(R.id.img_head)
    ImageView imgHead;
    @BindView(R.id.ll_take_head)
    LinearLayout llTakeHead;
    @BindView(R.id.tv_head_content)
    TextView tvHeadContent;
    @BindView(R.id.img_ghui)
    ImageView imgGhui;
    @BindView(R.id.ll_take_ghui)
    LinearLayout llTakeGhui;
    @BindView(R.id.tv_ghui_conent)
    TextView tvGhuiContent;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.btn_replay)
    Button btnReplay;
    @BindView(R.id.tv_guohui_text)
    TextView tvGuohuiText;
    @BindView(R.id.tv_people_text)
    TextView tvPeoPleText;
    CommonPopupWindow popupWindow;

    private int isRealName;
    private String headUrl,guoHuiUrl;//图片上传oos 路径
    private int operation=0;
//    private int realNameStatus;//0未认证  1认证成功  2 认证失败

    @Override
    protected RealNamePresenter createPresenter() {
        return new RealNamePresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_realname;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1001);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_ALBUM) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Logger.e("获取到的图片数:" + images.size());
                mPresenter.uploadImg(images.get(0).path);
            }else if(data != null && requestCode == REQUEST_CODE_CAMERA){
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Logger.e("相机获取到的图片数:" + images.size());
                mPresenter.uploadImg(images.get(0).path);
            }
            else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Keep
    @LoginFilter
    @Override
    protected void initData() {
        //获取信息并绑定
        initImagePicker();
        isRealName = SPUtils.getInstance().getInt(Constant.USER_REAL_NAME, 0);
        if (isRealName==1) {
            mPresenter.getRealNameInfo(MyApplication.userId);
            llTopStatus.setVisibility(View.VISIBLE);
            rlRealStatus.setVisibility(View.VISIBLE);
            rlRealTime.setVisibility(View.VISIBLE);
            llTakeHead.setVisibility(View.GONE);
            llTakeGhui.setVisibility(View.GONE);
            tvHeadContent.setVisibility(View.VISIBLE);
            tvGhuiContent.setVisibility(View.VISIBLE);
//
            imgTopStatus.setImageResource(R.mipmap.ic_pay_success);
            tvTopStatus.setText("已通过认证");
            editName.setEnabled(false);
            editCardNum.setEnabled(false);
            tvStatus.setText("已通过认证");
            btnReplay.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
        } else {
            llTopStatus.setVisibility(View.GONE);
            rlRealStatus.setVisibility(View.GONE);
            rlRealTime.setVisibility(View.GONE);
            llTakeHead.setVisibility(View.VISIBLE);
            llTakeGhui.setVisibility(View.VISIBLE);
            tvHeadContent.setVisibility(View.GONE);
            tvGhuiContent.setVisibility(View.GONE);

            editName.setEnabled(true);
            editCardNum.setEnabled(true);

            btnReplay.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
        }

    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(1180);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(720);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(590);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(360);                            //保存文件的高度。单位像素
    }

    @OnClick({R.id.ll_take_head, R.id.ll_take_ghui,R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_take_head:
                operation=0;
                showChooseHeadDialog();
                break;
            case R.id.ll_take_ghui:
                operation=1;
                showChooseHeadDialog();
                break;
            case R.id.btn_submit://提交
                if(TextUtils.isEmpty(editName.getText().toString())){
                    ToastUtils.showShortToast(mContext,"姓名不能为空");
                    return;
                }
                if(TextUtils.isEmpty(editCardNum.getText().toString())){
                    ToastUtils.showShortToast(mContext,"身份证不能为空");
                    return;
                }
                if(TextUtils.isEmpty(headUrl)){
                    ToastUtils.showShortToast(mContext,"要上传人物面照片哦~");
                    return;
                }
                if(TextUtils.isEmpty(guoHuiUrl)){
                    ToastUtils.showShortToast(mContext,"要上传国徽面照片哦~");
                    return;
                }
                saveRealName();

                break;
            case R.id.btn_replay://重新认证
                break;
        }
    }

    /**
     * 显示选择上传头像对话框
     */
    private void showChooseHeadDialog(){
        if (popupWindow != null && popupWindow.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.pop_choose_head, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.pop_choose_head)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }


    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId){
            case R.layout.pop_choose_head:
                TextView tvTitle = view.findViewById(R.id.txt_title);
                TextView tvContentOne = view.findViewById(R.id.tv_content_one);
                TextView tvContentTwo = view.findViewById(R.id.tv_content_two);
                TextView tvCancel = view.findViewById(R.id.btn_cancel);
                tvTitle.setText("实名认证");
                tvContentOne.setText("拍照");
                tvContentTwo.setText("本地相册");
                tvContentOne.setOnClickListener(v -> {
                    //直接打开相机拍照
                    Intent intent = new Intent(mContext, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    popupWindow.dismiss();
                });
                tvContentTwo.setOnClickListener(v -> {
                    //打开本地相册
                    Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                    startActivityForResult(intent1, REQUEST_CODE_ALBUM);
                    popupWindow.dismiss();
                });
                tvCancel.setOnClickListener(v -> popupWindow.dismiss());
                break;
        }
    }

    /**
     * 保存信息
     */
    private void saveRealName(){
        RealNameRequestBody body=new RealNameRequestBody();
        body.setUserId(MyApplication.userId);
        body.setRealName(editName.getText().toString());
        body.setIdCard(editCardNum.getText().toString());
        body.setImgFirst(guoHuiUrl);//正面照
        body.setImgSecond(headUrl);//反面照
        body.setMobile(SPUtils.getInstance().getString(Constant.USER_MOBILE));
        body.setIdType(1);
        mPresenter.saveRealNameInfo(body);
    }


    /**
     * 获取实名认证信息成功
     * @param model
     */
    @Override
    public void onGetRealNameInfoSuc(BaseModel<RealNameInfoBean> model) {
        RealNameInfoBean realNameInfoBean = model.getData();

        if(realNameInfoBean!=null){
            int length = realNameInfoBean.getRealName().length();
            StringBuilder buffer = new StringBuilder();
            for(int i = 1; i <= length - 1; i++){
                buffer.append("*");
            }
            buffer.append(realNameInfoBean.getRealName().substring(length - 1, length));
            editName.setText(buffer.toString());
            int cardLength = realNameInfoBean.getIdCard().length();
            StringBuilder cardBuffer = new StringBuilder();
            cardBuffer.append(realNameInfoBean.getIdCard().substring(0, 2));
            for (int i = 1; i < cardLength - 4; i++){
                cardBuffer.append("*");
            }
            cardBuffer.append(realNameInfoBean.getIdCard().substring(cardLength - 2, cardLength));
            editCardNum.setText(cardBuffer.toString());
            tvTime.setText(realNameInfoBean.getCreateTime());
            GlideUtil.show(mContext,realNameInfoBean.getImgFirst(),imgHead);
            GlideUtil.show(mContext,realNameInfoBean.getImgSecond(),imgGhui);
        }
    }

    /**
     * 保存实名认证成功
     * @param model
     */
    @Override
    public void onGetSaveRealNameSuc(BaseModel<String> model) {
        SPUtils.getInstance().put(Constant.USER_REAL_NAME,1);
        finish();
    }

    @Override
    public void onErrorCode(BaseModel model) {
//        super.onErrorCode(model);
        ToastUtils.showShortToast(mContext,model.getMsg());
    }

    /**
     *  上传图片
     * @param model
     */
    @Override
    public void onUploadImgSuccess(BaseModel<UploadLogoBean> model) {
        if(operation==0){//人物
            headUrl = model.getData().url;
            tvPeoPleText.setVisibility(View.INVISIBLE);
            GlideUtil.show(mContext, headUrl, imgHead);

        }else{//国徽
            guoHuiUrl = model.getData().url;
            tvGuohuiText.setVisibility(View.INVISIBLE);
            GlideUtil.show(mContext, guoHuiUrl, imgGhui);
        }
    }
}
