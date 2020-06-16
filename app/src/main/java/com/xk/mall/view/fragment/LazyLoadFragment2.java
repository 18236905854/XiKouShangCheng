package com.xk.mall.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.xk.mall.MyApplication;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.utils.Constant;
import com.xk.mall.view.activity.LoginActivity;


/**
 * 懒加载 ftagment 基类
 */

public abstract class LazyLoadFragment2<P extends BasePresenter> extends Fragment implements BaseViewListener {
    public Context mContext;
    protected P mPresenter;
    private boolean isRefreshToken = false;//刷新token时的标志位

    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;
    private View rootView;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFirstVisibleInitData();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mPresenter = createPresenter();
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (rootView == null) {
            rootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFirstVisibleInitData();
                    isFirstVisible = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView ? rootView : view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = true;
    }

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected void onFirstVisibleInitData() {

    }

//    /**
//     * 是否注册事件分发
//     *
//     * @return true 注册；false 不注册，默认不注册
//     */
//    protected boolean isRegisteredEventBus() {
//        return false;
//    }
    protected abstract P createPresenter();

    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onErrorCode(BaseModel model) {
        if(isRefreshToken && model.getCode() == 1){
            //刷新token时refreshToken过期
            ActivityUtils.startActivity(LoginActivity.class);
        }else if (model.getCode() == BaseObserver.NETWORK_ERROR) {

        }else if(model.getCode() == BaseObserver.CONNECT_NOT_LOGIN){//未登录
//            ActivityUtils.startActivity(LoginActivity.class);
        }else if(model.getCode() == BaseObserver.CONNECT_INVALID_TOKEN){//token过期，需要刷新token
            isRefreshToken = true;
            mPresenter.refreshToken(MyApplication.refreshToken);
        }
    }

    @Override
    public void onRefreshTokenSuccess(BaseModel<LoginBean> loginBean) {
        MyApplication.token = loginBean.getData().token;
        MyApplication.userId = loginBean.getData().userId;
        SPUtils.getInstance().put(Constant.SP_TOKEN, MyApplication.token);
        SPUtils.getInstance().put(Constant.SP_USER_ID, MyApplication.userId);
    }
}
