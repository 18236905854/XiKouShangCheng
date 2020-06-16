package com.xk.mall.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.xk.mall.MyApplication;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.activity.LoginActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;


/**
 * File descripition:   ftagment 基类
 */

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseViewListener {
    public Context mContext;
    protected P mPresenter;
    private boolean isRefreshToken = false;//刷新token时的标志位

    protected abstract P createPresenter();

    private View rootView;
    /**
     * 是否初始化过布局
     */
    protected boolean isViewInitiated;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInitiated;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutId(), null, false);
        mContext = getActivity();
        mPresenter = createPresenter();

        if (isRegisteredEventBus()) {
            if(!EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().register(this);
            }
        }
        ButterKnife.bind(this, rootView);
//        this.initToolbar(savedInstanceState);
        this.initData();
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        //加载数据
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetchData();
        }
    }

    public void prepareFetchData() {
        prepareFetchData(false);
    }

    /**
     * 判断懒加载条件
     *
     * @param forceUpdate 强制更新，好像没什么用？
     */
    public void prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            loadLazyData();
            isDataInitiated = true;
        }
    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 数据初始化操作
     */
    protected abstract void initData();

    /**
     * 懒加载
     */
    protected abstract void loadLazyData();

    /**
     * 当前页面是否可见
     * @return
     */
    protected boolean isFragmentVisible() {
        return isVisibleToUser;
    }

    /**
     * 是否注册事件分发
     *
     * @return true 注册；false 不注册，默认不注册
     */
    protected boolean isRegisteredEventBus() {
        return false;
    }

//    /**
//     * 处理顶部title
//     *
//     * @param savedInstanceState
//     */
//    protected abstract void initToolbar(Bundle savedInstanceState);


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

    @Override
    public void onErrorCode(BaseModel model) {
        if (isRefreshToken && model.getCode() == 1) {
            //刷新token时refreshToken过期
            ActivityUtils.startActivity(LoginActivity.class);
        } else if (model.getCode() == BaseObserver.NETWORK_ERROR) {
            ToastUtils.showShort("网络连接失败");
        } else if (model.getCode() == BaseObserver.CONNECT_TIMEOUT) {
            ToastUtils.showShort("网络连接超时");
        }else if (model.getCode() == BaseObserver.CONNECT_ERROR || model.getCode() == BaseObserver.BAD_NETWORK) {
            ToastUtils.showShort("网络连接错误");
        } else if (model.getCode() == BaseObserver.CONNECT_NOT_LOGIN) {//未登录
//            ActivityUtils.startActivity(LoginActivity.class);
        } else if (model.getCode() == BaseObserver.CONNECT_INVALID_TOKEN) {//token过期，需要刷新token
            isRefreshToken = true;
            MyApplication.token = "";
            mPresenter.refreshToken(MyApplication.refreshToken);
        }else {
            if(!NetworkUtils.isConnected() || !NetworkUtils.isAvailableByPing()){
                ToastUtils.showShort("当前网络不可用，请检查手机网络设置！");
            }else {
                if(TextUtils.isEmpty(model.getMsg())){
                    Logger.e("BaseFragment:系统错误");
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        this.rootView = null;
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (isRegisteredEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(getActivity() != null){
            ButterKnife.bind(getActivity()).unbind();
        }
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }


    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }
}
