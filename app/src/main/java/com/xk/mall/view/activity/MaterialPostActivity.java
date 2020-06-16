package com.xk.mall.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.MaterialRemarkBean;
import com.xk.mall.model.entity.TaskBean;
import com.xk.mall.model.entity.UploadLogoBean;
import com.xk.mall.model.impl.MaterialPostViewImpl;
import com.xk.mall.model.request.SaveShareRequestBody;
import com.xk.mall.presenter.MaterialPostPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.ImagePickerImageLoader;
import com.xk.mall.utils.XiKouUtils;
import com.xk.mall.view.widget.CommonPopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ClassName MaterialPostActivity
 * Description 素材提交页面
 * Author 卿凯
 * Date 2019/6/29/029
 * Version V1.0
 */
public class MaterialPostActivity extends BaseActivity<MaterialPostPresenter> implements CommonPopupWindow.ViewInterface, MaterialPostViewImpl {

    @BindView(R.id.iv_material_one)
    ImageView ivMaterialOne;//第一张图片
    @BindView(R.id.iv_material_two)
    ImageView ivMaterialTwo;//第二张图片
    @BindView(R.id.iv_material_three)
    ImageView ivMaterialThree;//第三张图片
    @BindView(R.id.btn_submit)
    Button btnSubmit;//提交按钮
    @BindView(R.id.rl_right_close)
    RelativeLayout rlRightClose;//第一个关闭按钮
    @BindView(R.id.iv_plus_one)
    ImageView ivPlusOne;//第一个加
    @BindView(R.id.rl_right_close_two)
    RelativeLayout rlRightCloseTwo;//第二个关闭按钮
    @BindView(R.id.iv_plus_two)
    ImageView ivPlusTwo;//第二个加号
    @BindView(R.id.rl_right_close_three)
    RelativeLayout rlRightCloseThree;//第三个关闭按钮
    @BindView(R.id.iv_plus_three)
    ImageView ivPlusThree;
    @BindView(R.id.tv_times)
    TextView tvTimes;//次数
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;//说明信息
    private String imgOne;//第一张图片
    private String imgTwo;//第二张图片
    private String imgThree;//第三张图片
    private final int REQUEST_CODE_ALBUM = 1;//打开相册的申请码
    private final int REQUEST_CODE_CAMERA = 2;//打开相机的申请码
    private CommonPopupWindow popupWindow;//选择图片
    private int clickPos;//点击的位置
    private int maxTimes;//最大次数
    private int cutTime;//进入完成次数


    @Override
    protected MaterialPostPresenter createPresenter() {
        return new MaterialPostPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_material_post;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("提交截图");
    }

    @OnClick({R.id.rl_material_right, R.id.ll_material_left})
    public void clickView(View view) {
        if(view.getId() == R.id.rl_material_right){
            ActivityUtils.startActivity(ShareCheckActivity.class);
        }else if(view.getId() == R.id.ll_material_left){
            finish();
        }
    }

    @Override
    protected void initData() {
        initImagePicker();
        mPresenter.getRemark();
        mPresenter.getTaskList(MyApplication.userId);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false);                   //是否按矩形区域保存
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(800);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(800);                            //保存文件的高度。单位像素
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
            ivPlusOne.setVisibility(View.VISIBLE);
        }else if(view.getId() == R.id.rl_right_close_two){
            //第二个关闭
            ivMaterialTwo.setImageDrawable(null);
            imgTwo = "";
            rlRightCloseTwo.setVisibility(View.GONE);
            ivPlusTwo.setVisibility(View.VISIBLE);
        }else if(view.getId() == R.id.rl_right_close_three){
            //第三个关闭
            ivMaterialThree.setImageDrawable(null);
            imgThree = "";
            rlRightCloseThree.setVisibility(View.GONE);
            ivPlusThree.setVisibility(View.VISIBLE);
        }else if(view.getId() == R.id.btn_submit){
            //提交数据关闭页面
            SaveShareRequestBody saveShareRequestBody = new SaveShareRequestBody();
            saveShareRequestBody.setUserId(MyApplication.userId);
            String nickName = SPUtils.getInstance().getString(Constant.NICK_NAME);
            String phone = SPUtils.getInstance().getString(Constant.USER_MOBILE);
            saveShareRequestBody.setUserName(nickName);
            saveShareRequestBody.setUserPhone(phone);
            List<SaveShareRequestBody.ImgModelsBean> imgModelsBeans = new ArrayList<>();
            if(!XiKouUtils.isNullOrEmpty(imgOne)){
                SaveShareRequestBody.ImgModelsBean imgModelsBean = new SaveShareRequestBody.ImgModelsBean();
                imgModelsBean.setAuditImgUrl(imgOne);
                imgModelsBeans.add(imgModelsBean);
            }
            if(!XiKouUtils.isNullOrEmpty(imgTwo)){
                SaveShareRequestBody.ImgModelsBean imgModelsBean = new SaveShareRequestBody.ImgModelsBean();
                imgModelsBean.setAuditImgUrl(imgTwo);
                imgModelsBeans.add(imgModelsBean);
            }
            if(!XiKouUtils.isNullOrEmpty(imgThree)){
                SaveShareRequestBody.ImgModelsBean imgModelsBean = new SaveShareRequestBody.ImgModelsBean();
                imgModelsBean.setAuditImgUrl(imgThree);
                imgModelsBeans.add(imgModelsBean);
            }
            saveShareRequestBody.setImgModels(imgModelsBeans);
            mPresenter.saveAuditImg(saveShareRequestBody);
            finish();
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

    @Override
    public void onUploadImgSuccess(BaseModel<String> model) {
        Logger.e("上传图片成功");
        if(model.getData() != null){
            if(clickPos == 1){
                imgOne = model.getData();
                GlideUtil.show(mContext, imgOne, ivMaterialOne);
                rlRightClose.setVisibility(View.VISIBLE);
                ivPlusOne.setVisibility(View.GONE);
            }else if(clickPos == 2){
                imgTwo = model.getData();
                GlideUtil.show(mContext, imgTwo, ivMaterialTwo);
                rlRightCloseTwo.setVisibility(View.VISIBLE);
                ivPlusTwo.setVisibility(View.GONE);
            }else if(clickPos == 3){
                imgThree = model.getData();
                GlideUtil.show(mContext, imgThree, ivMaterialThree);
                rlRightCloseThree.setVisibility(View.VISIBLE);
                ivPlusThree.setVisibility(View.GONE);
            }
            if(cutTime < maxTimes){
                btnSubmit.setEnabled(true);
                btnSubmit.setText("提交");
            }else {
                btnSubmit.setEnabled(false);
                btnSubmit.setText("今日任务数已满，明天再来");
            }
        }
    }

    @Override
    public void onSaveSuccess(BaseModel baseModel) {
        if(baseModel != null){
            //提交成功
            ToastUtils.showShort("提交成功，请等待审核");
            ActivityUtils.startActivity(ShareCheckActivity.class);
            finish();
        }
    }

    @Override
    public void onGetTaskListSuccess(BaseModel<TaskBean> model) {
        //获取任务值成功
        if(model.getData() != null && model.getData().getList() != null){
            for(TaskBean.ListBean bean : model.getData().getList()){
                if(bean.getType() == 6){//内容分享任务
                    maxTimes = bean.getMaxTimes();
                    cutTime = bean.getCurTimes();
                }
            }
            tvTimes.setText("今日可完成" + maxTimes + "次，已完成" + cutTime + "次");
            if(cutTime >= maxTimes){
                btnSubmit.setEnabled(false);
                btnSubmit.setText("今日任务数已满，明天再来");
            }
        }
    }

    @Override
    public void onGetRemarksSuccess(BaseModel<MaterialRemarkBean> beanBaseModel) {
        if(beanBaseModel != null && beanBaseModel.getData() != null){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(beanBaseModel.getData().getTopic()).append("\n");
            for(String content : beanBaseModel.getData().getRemarks()){
                stringBuilder.append(content + "\n");
            }
            tvRemarks.setText(stringBuilder.toString());
        }
    }
}
