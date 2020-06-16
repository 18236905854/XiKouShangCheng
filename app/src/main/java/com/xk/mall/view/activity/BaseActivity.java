package com.xk.mall.view.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.UMShareAPI;
import com.xk.mall.MyApplication;
import com.xk.mall.R;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.utils.Constant;
import com.xk.mall.utils.StatusBarUtil;
import com.xk.mall.view.widget.CustomDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.swipebacklayout.BGAKeyboardUtil;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * File descripition: activity基类
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseViewListener, BGASwipeBackHelper.Delegate {
    protected final String TAG = this.getClass().getSimpleName();
    public Context mContext;
    protected P mPresenter;
    private BGASwipeBackHelper mSwipeBackHelper;

    protected abstract P createPresenter();
    //错误提示框  警告框  成功提示框 加载进度框 （只是提供个案例 可自定义）
    private CustomDialog promptDialog;
    private boolean isRefreshToken = false;
    private boolean isShowDialog=true;//是否显示dialog;

    public boolean getIsShowDialog() {
        return isShowDialog;
    }

    public void setShowDialog(boolean showDialog) {
        isShowDialog = showDialog;
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_title_menu)
    LinearLayout ll_title_menu;//左边返回按钮的布局
    @BindView(R.id.iv_left_back)
    ImageView ivLeftBack;//左边返回按钮
    @BindView(R.id.ll_search)
    RelativeLayout flRight;//右边按钮的布局
    @BindView(R.id.tv_cart_num)
    TextView tvCartNum;
    @BindView(R.id.iv_right_img)
    ImageView ivRight;//右边图标
    @BindView(R.id.tv_title_left)
    TextView tv_title_left;//当左边是文字时显示
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;//标题栏正中间的文字
    @BindView(R.id.tv_title_right)
    TextView tv_title_right;//右边文字，需要时显示




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSwipeBackFinish();
        super.onCreate(savedInstanceState);
        Logger.e("ActivityName:"+TAG);
        mContext = this;
        if(isRegisteredEventBus()){
            EventBus.getDefault().register(mContext);
        }
        setContentView(getLayoutId());
        //避免切换横竖屏
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if(isSetStatusBarColor()){
            setStatusBar(getResources().getColor(R.color.colorWhite));
        }
        showLeft(true);
        showRight(false);
        ll_title_menu.setOnClickListener(v->{
            onBackPressed();
        });
        //友盟
        PushAgent.getInstance(this).onAppStart();
        this.initToolbar(savedInstanceState);
        this.initData();
    }

    protected boolean isSetStatusBarColor(){
        return true;
    }

    /**
     * 初始化滑动返回。在 super.onCreate(savedInstanceState) 之前调用该方法
     */
    private void initSwipeBackFinish() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, this);

        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 下面几项可以不配置，这里只是为了讲述接口用法。

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f);
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false);
    }

    /**
     * 设置APP字体不随系统字体大小改变
     * @return
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if(resources != null){
            Configuration config = new Configuration();
            config.setToDefaults();
            resources.updateConfiguration(config, resources.getDisplayMetrics());
        }
        return resources;
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     *
     * @return
     */
    @Override
    public boolean isSupportSwipeBack() {
        return true;
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    @Override
    public void onSwipeBackLayoutSlide(float slideOffset) {
    }

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    @Override
    public void onSwipeBackLayoutCancel() {
    }

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    @Override
    public void onSwipeBackLayoutExecuted() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.P){
            mSwipeBackHelper.swipeBackward();
        }else {
            BGAKeyboardUtil.closeKeyboard(this);
            finish();
            overridePendingTransition(0, 0);
        }
    }


    /**
     * 友盟统计需要添加的方法
     */
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    /**
     * 友盟统计需要添加的方法
     */
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
        backClicked();
    }

    public void backClicked(){
        if (getSupportFragmentManager().popBackStackImmediate())
        {
            getSupportFragmentManager().popBackStack();
            return;
        }

        popActivity();
    }

    /**
     * 退出页面时的转场动画
     */
    private void popActivity() {
        ActivityUtils.finishActivity(this,R.anim.register_push_left_in, R.anim.register_push_right_out);
    }

    /**
     * 是否注册事件分发
     *
     * @return true 注册；false 不注册，默认不注册
     */
    protected boolean isRegisteredEventBus() {
        return false;
    }

    /**
     * 设置标题
     * @param title
     */
    protected void setTitle(String title) {
        toolbar_title.setText(title);
    }

    /**
     * 设置标题文字颜色
     * @param color
     */
    protected void setTitleTextColor(int color){
        toolbar_title.setTextColor(color);
    }

    /**
     * 设置左边文字
     * @param title
     */
    protected void setLeftText(String title){
        tv_title_left.setText(title);
    }

    /**
     * 设置右边文字
     * @param title
     */
    protected void setRightText(String title){
        tv_title_right.setText(title);
    }

    /**
     * 右边文字按钮的点击事件
     */
    protected void setOnRightClickListener(View.OnClickListener onRightClickListener){
        tv_title_right.setOnClickListener(v -> {
            if(onRightClickListener != null){
                onRightClickListener.onClick(v);
            }
        });
    }

    /**
     * 右边图标按钮的点击事件
     */
    protected void setOnRightIconClickListener(View.OnClickListener onRightIconClickListener){
        ivRight.setOnClickListener(v -> {
            if(onRightIconClickListener != null){
                onRightIconClickListener.onClick(v);
            }
        });
        flRight.setOnClickListener(v -> {
            if(onRightIconClickListener != null){
                onRightIconClickListener.onClick(v);
            }
        });
    }

    /**
     * 是否显示右边文字
     * @param isShowRight
     */
    protected void showRight(boolean isShowRight) {
        if(isShowRight){
            tv_title_right.setVisibility(View.VISIBLE);
        }else {
            tv_title_right.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示右边文字
     * @param isShowRight
     */
    protected void showCartNum(boolean isShowRight) {
        if(isShowRight){
            tvCartNum.setVisibility(View.VISIBLE);
        }else {
            tvCartNum.setVisibility(View.GONE);
        }
    }

    /**
     * 是否显示左边返回按钮，主页不显示
     * @param isShow
     */
    protected void showLeft(boolean isShow) {
        if(isShow){
            ll_title_menu.setVisibility(View.VISIBLE);
        }else {
            ll_title_menu.setVisibility(View.GONE);
        }
    }

    /**
     * 设置左边返回按钮
     */
    protected void setLeftDrawable(int drawable){
        ivLeftBack.setImageResource(drawable);
    }

    /**
     * 是否显示右边图标
     */
    protected void showRightIcon(boolean isShow){
        if(isShow){
            flRight.setVisibility(View.VISIBLE);
        }else {
            flRight.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边图标
     */
    protected void setRightDrawable(int drawable){
        ivRight.setImageResource(drawable);
    }

    /**
     * 是否显示左边返回文字
     * @param isShow true : 显示  false:隐藏
     */
    protected void showLeftText(boolean isShow){
        if(isShow){
            tv_title_left.setVisibility(View.VISIBLE);
        }else {
            tv_title_left.setVisibility(View.GONE);
        }
    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();
    /**
     * 处理顶部title
     *
     * @param savedInstanceState
     */
    protected abstract void initToolbar(Bundle savedInstanceState);
    /**
     * 数据初始化操作
     */
    protected abstract void initData();

    /**
     * 此处设置沉浸式地方
     */
    protected void setStatusBar(int color) {
        //设置状态栏颜色
        toolbar.setBackgroundColor(color);
        StatusBarUtil.setColorNoTranslucent(this, color);
        setDarkStatusIcon(true);
    }

    /**
     * 说明：Android 6.0+ 状态栏图标原生反色操作
     */
    protected void setDarkStatusIcon(boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            if (decorView == null) return;

            int vis = decorView.getSystemUiVisibility();
            if (dark) {
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(vis);
        }
    }

    /**
     * 封装toast方法（自行去实现）
     *
     * @param str
     */
    public void showToast(String str) {
        ToastUtils.showShort(str);
    }

    public void showLongToast(String str) {
        ToastUtils.showLong(str);
    }
    @Override
    public void showError(String msg) {
        showToast(msg);
    }
    /**
     * 返回所有状态  除去指定的值  可设置所有（根据需求）
     *
     * @param model
     */
    @Override
    public void onErrorCode(BaseModel model) {
        if(isRefreshToken && model.getCode() == 1){
            //刷新token时refreshToken过期
            ActivityUtils.startActivity(LoginActivity.class);
        }else if (model.getCode() == BaseObserver.NETWORK_ERROR) {
            ToastUtils.showShort("网络连接失败");
        }else if (model.getCode() == BaseObserver.CONNECT_TIMEOUT) {
            ToastUtils.showShort("网络连接超时");
        }else if (model.getCode() == BaseObserver.CONNECT_ERROR || model.getCode() == BaseObserver.BAD_NETWORK) {
            ToastUtils.showShort("网络连接错误");
        }else if(model.getCode() == BaseObserver.CONNECT_NOT_LOGIN){//未登录
            ActivityUtils.startActivity(LoginActivity.class);
        }else if(model.getCode() == BaseObserver.CONNECT_INVALID_TOKEN){//token过期，需要刷新token
            isRefreshToken = true;
            MyApplication.token = "";
            mPresenter.refreshToken(MyApplication.refreshToken);
        }else {
            if(!NetworkUtils.isConnected() || !NetworkUtils.isAvailableByPing()){
                ToastUtils.showShort("当前网络不可用，请检查手机网络设置！");
            }else {
                if(TextUtils.isEmpty(model.getMsg())){
                    Logger.e("BaseActivity:系统错误");
                    ToastUtils.showShort("系统错误");
                }else {
                    ToastUtils.showShort(model.getMsg());
                }
            }
        }
    }

    @Override
    public void onRefreshTokenSuccess(BaseModel<LoginBean> o) {
        MyApplication.token = o.getData().token;
        MyApplication.userId = o.getData().userId;
        SPUtils.getInstance().put(Constant.SP_TOKEN, MyApplication.token);
        SPUtils.getInstance().put(Constant.SP_USER_ID, MyApplication.userId);
    }

    //显示加载进度框回调
    @Override
    public void showLoading() {
        if(getIsShowDialog()){
            showLoadingDialog();
        }
    }
    //隐藏进度框回调
    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }
    /**
     * 进度款消失
     */
    private void closeLoadingDialog() {
        if (promptDialog != null) {
            new Handler().postDelayed(() -> promptDialog.dismiss(), 400);
        }
    }
    /**
     * 加载中...
     */
    private void showLoadingDialog() {
        if (promptDialog == null) {
            promptDialog = new CustomDialog(mContext, R.style.mydialog);
//            promptDialog = CustomDialog.show(BaseActivity.this, R.layout.loading_dialog, (dialog, v) -> {
//                CircleProgressBar pbLoading = v.findViewById(R.id.pb_header_loading);
//                ValueAnimator animator = ValueAnimator.ofInt(0, 100).setDuration(2000);
//                animator.addUpdateListener(valueAnimator -> pbLoading.setProgress((int) valueAnimator.getAnimatedValue()));
//                animator.start();
//            });
        }
        promptDialog.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(promptDialog != null){
            promptDialog.dismiss();
        }
        if(isRegisteredEventBus()){
            EventBus.getDefault().unregister(mContext);
        }
        UMShareAPI.get(mContext).release();
//        BaseDialog.unload();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
