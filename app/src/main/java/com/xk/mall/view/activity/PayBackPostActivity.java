package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.model.entity.UploadLogoBean;
import com.xk.mall.model.eventbean.PayBackEvent;
import com.xk.mall.model.impl.PayBackPostViewImpl;
import com.xk.mall.model.request.PayBackPostRequestBody;
import com.xk.mall.model.request.SaveShareRequestBody;
import com.xk.mall.presenter.PayBackPostPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.ImagePickerImageLoader;
import com.xk.mall.utils.PriceUtil;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.ClearEditText;
import com.xk.mall.view.widget.CommonPopupWindow;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @ClassName: PayBackPostActivity
 * @Description: 申请退款页面
 * @Author: 卿凯
 * @Date: 2019/12/9/009 9:23
 * @Version: 1.0
 */
public class PayBackPostActivity extends BaseActivity<PayBackPostPresenter> implements PayBackPostViewImpl,CommonPopupWindow.ViewInterface {
    @BindView(R.id.iv_goods_logo)
    ImageView ivGoodsLogo;
    @BindView(R.id.tv_goods_name)
    TextView tvGoodsName;
    @BindView(R.id.tv_goods_sku)
    TextView tvGoodsSku;
    @BindView(R.id.et_back_reason)
    ClearEditText etBackReason;
    @BindView(R.id.tv_back_money)
    TextView tvBackMoney;
    @BindView(R.id.iv_material_one)
    RoundedImageView ivMaterialOne;//第一张图片
    @BindView(R.id.rl_right_close)
    RelativeLayout rlRightClose;//第一张图片的关闭按钮
    @BindView(R.id.iv_material_two)
    RoundedImageView ivMaterialTwo;//第二张图片
    @BindView(R.id.rl_right_close_two)
    RelativeLayout rlRightCloseTwo;//第二张图片的关闭按钮
    @BindView(R.id.iv_material_three)
    RoundedImageView ivMaterialThree;//第三张图片
    @BindView(R.id.rl_right_close_three)
    RelativeLayout rlRightCloseThree;//第三张图片关闭按钮
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.iv_camera_one)
    ImageView ivCameraOne;//第一个相机
    @BindView(R.id.iv_camera_two)
    ImageView ivCameraTwo;//第二个相机
    @BindView(R.id.iv_camera_three)
    ImageView ivCameraThree;//第三个相机

    /**
     * intent传递过来的商品ID的key
     */
    public final static String GOODS_ID = "goods_id";
    /**
     * intent传递过来的商品ID的key
     */
    public final static String GOODS_LOGO = "goods_logo";
    /**
     * intent传递过来的商品ID的key
     */
    public final static String GOODS_SKU = "goods_sku";
    /**
     * intent传递过来的商品名称的key
     */
    public final static String GOODS_NAME = "goods_name";
    /**
     * intent传递过来的商品订单的key
     */
    public final static String GOODS_ORDER_NO = "goods_order_no";
    /**
     * intent传递过来的商品金额的key
     */
    public final static String GOODS_MONEY = "goods_money";
    /**
     * intent传递过来的商品金额的key
     */
    public final static String GOODS_ACTIVITY_TYPE = "goods_activity_type";
    private String goodsId;//商品ID
    private int activityType;//订单类型
    private String orderNo;//订单号
    private int money;//金额
    private int clickPos;//点击的位置
    private CommonPopupWindow popupWindow;//选择图片
    private final int REQUEST_CODE_ALBUM = 1;//打开相册的申请码
    private final int REQUEST_CODE_CAMERA = 2;//打开相机的申请码
    private String imgOne;
    private String imgTwo;
    private String imgThree;

    @Override
    protected PayBackPostPresenter createPresenter() {
        return new PayBackPostPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_back_post;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("申请退款");
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false);                   //是否按矩形区域保存
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(ScreenUtils.getScreenWidth());//裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(ScreenUtils.getScreenWidth());//裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(ScreenUtils.getScreenWidth()); //保存文件的宽度。单位像素
        imagePicker.setOutPutY(ScreenUtils.getScreenWidth()); //保存文件的高度。单位像素
    }

    @Override
    protected void initData() {
        MyApplication.getInstance().addActivity(this);
        initImagePicker();
        goodsId = getIntent().getStringExtra(GOODS_ID);
        orderNo = getIntent().getStringExtra(GOODS_ORDER_NO);
        money = getIntent().getIntExtra(GOODS_MONEY, 0);

        activityType = getIntent().getIntExtra(GOODS_ACTIVITY_TYPE, 0);
        tvBackMoney.setText("¥" + PriceUtil.dividePrice(money));
        String goodsName = getIntent().getStringExtra(GOODS_NAME);
        tvGoodsName.setText(goodsName);
        tvGoodsSku.setText(getIntent().getStringExtra(GOODS_SKU));
        String logo = getIntent().getStringExtra(GOODS_LOGO);
        GlideUtil.showCircleLoading(mContext, logo, ivGoodsLogo);

        etBackReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (XiKouUtils.isNullOrEmpty(s.toString())) {
                    btnSubmit.setEnabled(false);
                } else {
                    btnSubmit.setEnabled(true);
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }

    @OnClick({R.id.iv_material_one, R.id.iv_material_two, R.id.iv_material_three, R.id.rl_right_close,
            R.id.rl_right_close_two, R.id.rl_right_close_three, R.id.btn_submit})
    public void onClickView(View view){
        if(view.getId() == R.id.iv_material_one){
            //点击第一张图片
            clickPos = 1;
            showChooseHead();
        }else if(view.getId() == R.id.iv_material_two){
            //点击第二张图片
            clickPos = 2;
            showChooseHead();
        }else if(view.getId() == R.id.iv_material_three){
            //点击第三种图片
            clickPos = 3;
            showChooseHead();
        }else if(view.getId() == R.id.rl_right_close){
            //第一个关闭
            ivMaterialOne.setImageDrawable(null);
            imgOne = "";
            rlRightClose.setVisibility(View.GONE);
            ivCameraOne.setVisibility(View.VISIBLE);
        }else if(view.getId() == R.id.rl_right_close_two){
            //第二个关闭
            ivMaterialTwo.setImageDrawable(null);
            imgTwo = "";
            rlRightCloseTwo.setVisibility(View.GONE);
            ivCameraTwo.setVisibility(View.VISIBLE);
        }else if(view.getId() == R.id.rl_right_close_three){
            //第三个关闭
            ivMaterialThree.setImageDrawable(null);
            imgThree = "";
            rlRightCloseThree.setVisibility(View.GONE);
            ivCameraThree.setVisibility(View.VISIBLE);
        }else if(view.getId() == R.id.btn_submit){
            //提交
            List<String> images = new ArrayList<>();
            if(!XiKouUtils.isNullOrEmpty(imgOne)){
                images.add(imgOne);
            }
            if(!XiKouUtils.isNullOrEmpty(imgTwo)){
                images.add(imgTwo);
            }
            if(!XiKouUtils.isNullOrEmpty(imgThree)){
                images.add(imgThree);
            }
            PayBackPostRequestBody requestBody = new PayBackPostRequestBody();
            requestBody.setActivityType(activityType);
            requestBody.setGoodsId(goodsId);
            requestBody.setOrderNo(orderNo);
            requestBody.setSalesReturnImageList(images);
            String phone = SPUtils.getInstance().getString(Constant.USER_MOBILE);
            requestBody.setPurchaserPhone(phone);
            requestBody.setSalesReturnMsg(etBackReason.getText().toString().trim());
            mPresenter.postBack(requestBody);
        }

    }

    /**
     * 选择图片
     */
    private void showChooseHead() {
        if(clickPos == 1 && rlRightClose.getVisibility() == View.VISIBLE){
            return;
        }else if(clickPos == 2 && rlRightCloseTwo.getVisibility() == View.VISIBLE){
            return;
        }else if(clickPos == 3 && rlRightCloseThree.getVisibility() == View.VISIBLE){
            return;
        }
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
    public void onPostSuccess(BaseModel<String> baseModel) {
        if (baseModel != null) {
            //需要发送消息给列表页面删除该订单
            PayBackEvent payBackEvent = new PayBackEvent();
            payBackEvent.setOrderNo(orderNo);
            EventBus.getDefault().post(payBackEvent);
            ToastUtils.showShort(baseModel.getData());
            MyApplication.getInstance().closeActivity();
        }
    }

    @Override
    public void onUploadImgSuccess(BaseModel<UploadLogoBean> model) {
        Logger.e("上传图片成功");
        if(model.getData() != null){
            if(clickPos == 1){
                imgOne = model.getData().url;
                GlideUtil.show(mContext, imgOne, ivMaterialOne);
                rlRightClose.setVisibility(View.VISIBLE);
                ivCameraOne.setVisibility(View.GONE);
            }else if(clickPos == 2){
                imgTwo = model.getData().url;
                GlideUtil.show(mContext, imgTwo, ivMaterialTwo);
                rlRightCloseTwo.setVisibility(View.VISIBLE);
                ivCameraTwo.setVisibility(View.GONE);
            }else if(clickPos == 3){
                imgThree = model.getData().url;
                GlideUtil.show(mContext, imgThree, ivMaterialThree);
                rlRightCloseThree.setVisibility(View.VISIBLE);
                ivCameraThree.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId){
            case R.layout.pop_choose_head:
                TextView tvTitle = view.findViewById(R.id.txt_title);
                TextView tvContentOne = view.findViewById(R.id.tv_content_one);
                TextView tvContentTwo = view.findViewById(R.id.tv_content_two);
                TextView tvCancel = view.findViewById(R.id.btn_cancel);
                tvTitle.setText("选择照片来源");
                tvContentOne.setText("相册");
                tvContentTwo.setText("拍照");
                tvContentOne.setOnClickListener(v -> {
                    //打开本地相册
                    Intent intent1 = new Intent(mContext, ImageGridActivity.class);
                    startActivityForResult(intent1, REQUEST_CODE_ALBUM);
                    popupWindow.dismiss();
                });
                tvContentTwo.setOnClickListener(v -> {
                    //直接打开相机拍照
                    Intent intent = new Intent(mContext, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    popupWindow.dismiss();
                });
                tvCancel.setOnClickListener(v -> popupWindow.dismiss());
                break;
        }
    }
}
