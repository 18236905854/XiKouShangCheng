package com.xk.mall.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseContent;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.model.entity.AddressBean;
import com.xk.mall.model.entity.KeyValueBean;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.entity.UploadLogoBean;
import com.xk.mall.model.entity.UserServerBean;
import com.xk.mall.model.eventbean.BindWXEvent;
import com.xk.mall.model.eventbean.NickNameEventBean;
import com.xk.mall.model.impl.PersonalInfoViewImpl;
import com.xk.mall.presenter.PersonInfoPresenter;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.DensityUtils;
import com.xk.mall.utils.EventBusMessage;
import com.xk.mall.utils.GlideUtil;
import com.xk.mall.utils.ImagePickerImageLoader;
import com.xk.mall.utils.InstallUtil;
import com.xk.mall.view.widget.CommomDialog;
import com.xk.mall.view.widget.CommonPopupWindow;
import com.xk.mall.view.widget.UnbindDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * ClassName PersonalInfoActivity
 * Description 个人信息页面
 * Author 卿凯
 * Date 2019/6/11/011
 * Version V1.0
 */
public class PersonalInfoActivity extends BaseActivity<PersonInfoPresenter> implements CommonPopupWindow.ViewInterface,PersonalInfoViewImpl {

    @BindView(R.id.rl_info_head_icon)
    RelativeLayout rlInfoHeadIcon;//头像行,点击修改头像
    @BindView(R.id.tv_info_vip)
    TextView tvInfoVip;//喜扣会员，当是会员时才显示
    @BindView(R.id.rl_info_id)
    RelativeLayout rlInfoId;//用户ID父布局，不可修改
    @BindView(R.id.tv_info_id)
    TextView tvInfoId;//用户ID
    @BindView(R.id.iv_info_logo)
    ImageView ivLogo;//用户头像
    @BindView(R.id.rl_info_name)
    RelativeLayout rlInfoName;//昵称的父布局
    @BindView(R.id.tv_info_name)
    TextView tvInfoName;//昵称
    @BindView(R.id.rl_info_sex)
    RelativeLayout rlInfoSex;//性别
    @BindView(R.id.tv_info_sex)
    TextView tvInfoSex;//性别
    @BindView(R.id.rl_info_phone)
    RelativeLayout rlInfoPhone;//手机号
    @BindView(R.id.tv_info_phone)
    TextView tvInfoPhone;//显示手机号
    @BindView(R.id.rl_info_wechat)
    RelativeLayout rlInfoWechat;//微信
    @BindView(R.id.tv_info_wechat_unbind)
    TextView tvInfoWechat;//显示已绑定或未绑定
//    @BindView(R.id.rl_info_unbind)
//    RelativeLayout rlUnbind;//解除绑定布局
    @BindView(R.id.iv_info_bind_right)
    ImageView ivBindRight;//绑定箭头
    @BindView(R.id.rl_info_address)
    RelativeLayout rlInfoAddress;//地址管理
    @BindView(R.id.tv_info_address)
    TextView tvInfoAddress;//地址显示无
    @BindView(R.id.btn_info_save)
    Button btnInfoSave;
    private final int REQUEST_CODE_ALBUM = 1;//打开相册的申请码
    private final int REQUEST_CODE_CAMERA = 2;//打开相机的申请码
    private String headUrl = "";//用户头像
    private int sex = 0;//用户性别
    private boolean isBindWechat = false;//是否绑定微信
    private boolean isHasAddress = false;//是否有地址信息
    private IWXAPI api;
    private WXBroadcastReceiver wxBroadcastReceiver;
    private CommonPopupWindow popupWindow;//选择头像
    private CommonPopupWindow popupWindowSex;//选择头像

    @Override
    protected PersonInfoPresenter createPresenter() {
        return new PersonInfoPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        setTitle("个人信息");
    }

    @Override
    protected void initData() {
        //获取信息并绑定
        //调用接口
//        UserServerBean userServerBean = userServerBeanList.get(0);
//        bindUserData(userServerBean);
        mPresenter.getUserInfoById(MyApplication.userId);
//        if(isBindWechat){
//            rlUnbind.setVisibility(View.VISIBLE);
//        }
        initImagePicker();
    }

    /**
     * 绑定用户数据的方法
     */
    private void bindUserData(UserServerBean userServerBean){
        GlideUtil.showUserCircle(mContext, userServerBean.headUrl, ivLogo);
        headUrl = userServerBean.headUrl;
        if(userServerBean.userType == 2){
            tvInfoVip.setVisibility(View.VISIBLE);
        }else {
            tvInfoVip.setVisibility(View.GONE);
        }
        if(userServerBean.sex != 0){
            tvInfoSex.setText((userServerBean.sex == 1? "男":"女"));
            sex = userServerBean.sex;
        }
        tvInfoId.setText(userServerBean.id);
        tvInfoName.setText(userServerBean.nickName);
        tvInfoPhone.setText(userServerBean.mobile);
        if(userServerBean.address == 0){
            //显示"无"
            isHasAddress = false;
            tvInfoAddress.setText("无");
        }else {
            //隐藏"无"
            isHasAddress = true;
            tvInfoAddress.setText("");
        }
        if(userServerBean.isBindWX == 0){
            //未绑定，显示未绑定，隐藏解除绑定
            isBindWechat = false;
            tvInfoWechat.setText("未绑定");
            ivBindRight.setVisibility(View.GONE);
        }else {
            //已绑定，显示已绑定，显示解除绑定
            isBindWechat = true;
            tvInfoWechat.setText("已绑定");
            ivBindRight.setVisibility(View.VISIBLE);
        }
    }


    @OnClick({R.id.rl_info_head_icon, R.id.rl_info_name, R.id.rl_info_sex, R.id.rl_info_phone,
                R.id.rl_info_wechat,R.id.rl_info_address, R.id.btn_info_save, R.id.rl_commend_info})
    public void clickView(View view){
        switch (view.getId()){
            case R.id.rl_info_head_icon://头像
                showChooseHead();
                break;

            case R.id.rl_info_name://昵称
                String name = tvInfoName.getText().toString();
                Intent intent = new Intent(mContext, EditorNameActivity.class);
                intent.putExtra(Constant.INTENT_NAME, name);
                ActivityUtils.startActivity(intent);
                break;

            case R.id.rl_info_sex://选择性别
                showChooseSex();
                break;

            case R.id.rl_info_phone://手机号
                break;

            case R.id.rl_info_wechat://微信点击解绑
                if(isBindWechat){
                    showUnbindDialog();
                }else {
                    //跳转绑定
                    if(InstallUtil.isWeChatAppInstalled(mContext)){
                        regToWx();
                        sendReq();
                    }else {
                        new CommomDialog(mContext, R.style.mydialog, "请先安装微信，再进行授权登录", (dialog, confirm) -> {
                            dialog.dismiss();
                        }).show();
                    }
                }
                break;

            case R.id.rl_info_address://地址管理
                ActivityUtils.startActivity(AddressActivity.class);
                break;

            case R.id.btn_info_save://保存按钮
                saveInfo();
                break;

            case R.id.rl_commend_info://推荐人信息
                ActivityUtils.startActivity(CommendInfoActivity.class);
                break;

        }
    }

    private class WXBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 将该app注册到微信
            api.registerApp(BaseContent.UM_WECHAT_KEY);

        }
    }

    private void sendReq(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo,snsapi_friend,snsapi_message,snsapi_contact";
        req.state = "none";
        api.sendReq(req);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventBusMessage message){
        String code = message.getMessage();
        //用code去请求服务器
        mPresenter.wxLogin(BaseContent.UM_WECHAT_KEY, code);
    }

    /**
     * 注册到微信
     */
    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, BaseContent.UM_WECHAT_KEY, true);

        // 将应用的appId注册到微信
        api.registerApp(BaseContent.UM_WECHAT_KEY);

        //建议动态监听微信启动广播进行注册到微信
        wxBroadcastReceiver = new WXBroadcastReceiver();
        registerReceiver(wxBroadcastReceiver, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(wxBroadcastReceiver != null){
            unregisterReceiver(wxBroadcastReceiver);
        }
    }

    @Override
    public void onErrorCode(BaseModel model) {
        Logger.e("onErrorCode返回的msg:" + model.getMsg());
        if(model.getCode() == BaseObserver.CONNECT_WX_NOT_BIND){
            //微信未绑定手机号，获取msg做为unionid去进行注册，跳转绑定手机号页面
            String unionid = model.getMsg();
            Intent intent = new Intent(mContext, BindPhoneActivity.class);
            intent.putExtra("unionid", unionid);
            intent.putExtra("invite_code","");
            intent.putExtra("user_phone", tvInfoPhone.getText().toString().trim());
            ActivityUtils.startActivity(intent);
        }else {
            super.onErrorCode(model);
        }
    }

    /**
     * 显示解绑确定对话框
     */
    private void showUnbindDialog() {
        long lastTime = SPUtils.getInstance().getLong(Constant.UNBIND_TIME, 0);
        if(System.currentTimeMillis() - lastTime < 90 * 24 * 3600 * 1000){
            //表示距离上次解除绑定时间没有90天
            showToast("90天之内不能再次解除绑定");
        }else {
            new UnbindDialog(mContext, R.style.mydialog, (dialog, confirm) -> {
                if(confirm){
                    //解除微信绑定
                    mPresenter.unbind(MyApplication.userId, 1);
                    dialog.dismiss();
                }
            }).show();
        }
    }

    /**
     * 解绑用户第三方账号成功
     */
    @Override
    public void onUnbindSuccess(BaseModel model) {
        showToast(model.getMsg());
        SPUtils.getInstance().put(Constant.UNBIND_TIME, System.currentTimeMillis());
        isBindWechat = false;
        tvInfoWechat.setText("未绑定");
        ivBindRight.setVisibility(View.GONE);
    }

    @Override
    public void onWXLoginSuccess(BaseModel<LoginBean> o) {
        //绑定页面数据
        isBindWechat = true;
        tvInfoWechat.setText("已绑定");
        ivBindRight.setVisibility(View.VISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private void getBindWxInfo(BindWXEvent event){
        if(event.isSuccess()){
            isBindWechat = true;
            tvInfoWechat.setText("已绑定");
            ivBindRight.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 保存用户信息
     */
    private void saveInfo(){
        String nickName = tvInfoName.getText().toString();
        if(TextUtils.isEmpty(nickName)){
            showToast("请填写用户昵称");
        }
        String phone = tvInfoPhone.getText().toString().trim();
        mPresenter.modifyPersonalInfo(headUrl, MyApplication.userId, nickName, sex, phone);
    }

    /**
     * 选择用户头像
     */
    private void showChooseHead() {
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

    /**
     * 选择用户头像
     */
    private void showChooseSex() {
        if (popupWindowSex != null && popupWindowSex.isShowing()) return;
        View upView = LayoutInflater.from(this).inflate(R.layout.pop_choose_sex, null);
        //测量View的宽高
        DensityUtils.measureWidthAndHeight(upView);
        popupWindowSex = new CommonPopupWindow.Builder(this)
                .setView(R.layout.pop_choose_sex)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, upView.getMeasuredHeight())
                .setBackGroundLevel(0.3f)//取值范围0.0f-1.0f 值越小越暗
                .setAnimationStyle(R.style.AnimUp)
                .setViewOnclickListener(this)
                .create();
        popupWindowSex.showAtLocation(findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void getChildView(View view, int layoutResId) {
        switch (layoutResId){
            case R.layout.pop_choose_head:
                TextView tvTitle = view.findViewById(R.id.txt_title);
                TextView tvContentOne = view.findViewById(R.id.tv_content_one);
                TextView tvContentTwo = view.findViewById(R.id.tv_content_two);
                TextView tvCancel = view.findViewById(R.id.btn_cancel);
                tvTitle.setText("修改头像");
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

            case R.layout.pop_choose_sex:
                TextView tvTitleSex = view.findViewById(R.id.txt_title);
                TextView tvContentOneSex = view.findViewById(R.id.tv_content_one);
                TextView tvContentTwoSex = view.findViewById(R.id.tv_content_two);
                TextView tvCancelSex = view.findViewById(R.id.btn_cancel);
                tvTitleSex.setText("选择性别");
                tvContentOneSex.setText("男");
                tvContentTwoSex.setText("女");
                tvContentOneSex.setOnClickListener(v -> {
                    tvInfoSex.setText("男");
                    popupWindowSex.dismiss();
                });
                tvContentTwoSex.setOnClickListener(v -> {
                    tvInfoSex.setText("女");
                    popupWindowSex.dismiss();
                });
                tvCancelSex.setOnClickListener(v -> popupWindowSex.dismiss());
                break;
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(false);                   //是否按矩形区域保存
        imagePicker.setMultiMode(false);
        imagePicker.setStyle(CropImageView.Style.CIRCLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(800);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(800);                            //保存文件的高度。单位像素
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
    public void getUserInfoSuccess(BaseModel<UserServerBean> userServerBeanBaseModel) {
        bindUserData(userServerBeanBaseModel.getData());
    }

    /**
     * 上传用户头像成功，返回用户头像地址
     */
    @Override
    public void onUploadImgSuccess(BaseModel<UploadLogoBean> model) {
        Logger.e("上传用户头像成功");
        headUrl = model.getData().url;
        GlideUtil.showUserCircle(mContext, headUrl, ivLogo);
    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveName(NickNameEventBean eventBusMessage){
        tvInfoName.setText(eventBusMessage.getNickName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAddAddressSuccess(AddressBean addressBean){
        if(addressBean != null){
            tvInfoAddress.setVisibility(View.GONE);
        }
    }

    /**
     * 用户信息保存成功的回调
     */
    @Override
    public void onSaveSuccess(BaseModel model) {
        showToast("修改成功");
        String nickName = tvInfoName.getText().toString();
        SPUtils.getInstance().put(Constant.NICK_NAME, nickName);
        EventBus.getDefault().post(new KeyValueBean(nickName, headUrl));
        finish();
    }
}
