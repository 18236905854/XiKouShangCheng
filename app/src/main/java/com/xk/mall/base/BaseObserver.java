package com.xk.mall.base;

import android.text.TextUtils;

import com.google.gson.JsonParseException;
import com.xk.mall.utils.NetWorkUtils;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;

/**
 * File descripition:   数据处理基类
 */

public abstract class BaseObserver<T> extends DisposableObserver<T> {

    public static final int CODE = BaseContent.basecode;

    protected BaseViewListener view;
    /**
     * 网络连接失败  无网
     */
    public static final int NETWORK_ERROR = 100000;
    /**
     * 解析数据失败
     */
    public static final int PARSE_ERROR = 108;
    /**
     * 网络问题
     */
    public static final int BAD_NETWORK = 107;
    /**
     * 连接错误
     */
    public static final int CONNECT_ERROR = 106;
    /**
     * 连接超时
     */
    public static final int CONNECT_TIMEOUT = 105;

    /**
     * 未登录
     */
    public static final int CONNECT_NOT_LOGIN = 401;
    /**
     * 手机号码未注册
     */
    public static final int CONNECT_NOT_REGISTER = 1001;
    /**
     * 手机号码已注册但未绑定
     */
    public static final int CONNECT_NOT_BIND = 1002;
    /**
     * 用户已绑定其他账号
     */
    public static final int CONNECT_HAS_BIND_OTHER = 1008;
    /**
     * 微信未绑定手机号
     */
    public static final int CONNECT_WX_NOT_BIND = 1006;
    /**
     * 验证码不匹配
     */
    public static final int CONNECT_CODE_NOT_MATCH = 1010;
    /**
     * 邀请码无效
     */
    public static final int CONNECT_INVALID_INVATATION_CODE = 1013;
    /***
     *token过期，需要调用刷新token的接口
     */
    public static final int CONNECT_INVALID_TOKEN = 10002;
    /**
     * 其他code  提示
     */
    public static final int OTHER_MESSAGE = 20000;


    public BaseObserver(BaseViewListener view) {
        this.view = view;
    }

    @Override
    protected void onStart() {
        if (view != null) {
            view.showLoading();
        }
    }

    @Override
    public void onNext(T o) {
        try {
            if (view != null) {
                view.hideLoading();
            }
            BaseModel model = (BaseModel) o;
            if (model.getCode() == CODE) {
                onSuccess(o);
                /*服务器返回的指定成功 code  设置是否回调  解开注释回调*//*
                if (view != null) {
                    view.onErrorCode(model);
                }*/
            } else {
                if (view != null) {
                    view.onErrorCode(model);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            onError(e.toString());
        }
    }

    @Override
    public void onError(Throwable e) {
        if (view != null) {
            view.hideLoading();
        }
        if (e instanceof HttpException) {
            //   HTTP错误
            onException(BAD_NETWORK, "");
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            //   连接错误
            onException(CONNECT_ERROR, "");
        } else if (e instanceof InterruptedIOException) {
            //  连接超时
            onException(CONNECT_TIMEOUT, "");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            //  解析错误
            onException(PARSE_ERROR, "");
            e.printStackTrace();


            /**
             * 此处很重要
             * 为何这样写：因为开发中有这样的需求   当服务器返回假如0是正常 1是不正常  当返回0时：我们gson 或 fastJson解析数据
             * 返回1时：我们不想解析（可能返回值出现以前是对象 但是现在数据为空变成了数组等等，于是在不改后台代码的情况下  我们前端需要处理）
             * 但是用了插件之后没有很有效的方法控制解析 所以处理方式为  当服务器返回不等于0时候  其他状态都抛出异常 然后提示
             * 代码上一级在 MyGsonResponseBodyConverter 中处理  前往查看逻辑
             */
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            int code = exception.getErrorCode();
            switch (code) {
                //未登录（此处只是案例 供理解）
                case CONNECT_NOT_LOGIN:
                    onException(CONNECT_NOT_LOGIN, exception.getMessage());
                    break;
                case CONNECT_NOT_REGISTER://手机号码未注册
                    onException(CONNECT_NOT_REGISTER, exception.getMessage());
                    break;
                case CONNECT_NOT_BIND://手机号码已注册但未绑定
                    onException(CONNECT_NOT_BIND, exception.getMessage());
                    break;
                case CONNECT_CODE_NOT_MATCH://验证码不匹配
                    onException(CONNECT_CODE_NOT_MATCH, exception.getMessage());
                    break;
                case CONNECT_HAS_BIND_OTHER://该手机号已绑定其他用户
                    onException(CONNECT_HAS_BIND_OTHER, exception.getMessage());
                    break;
                case CONNECT_INVALID_TOKEN://访问网关错误，需要刷新token
                    onException(CONNECT_INVALID_TOKEN, exception.getMessage());
                    break;
                //其他不等于0 的所有状态
                default:
                    view.onErrorCode(new BaseModel(exception.getMessage(), code));
                    break;
            }
        } else {
            if (e != null) {
                onError(e.toString());
            } else {
                onError("未知错误");
            }
        }

    }

    /**
     * 中间拦截一步  判断是否有网络  这步判断相对准确  此步去除也可以
     *
     * @param unknownError
     * @param message
     */
    private void onException(int unknownError, String message) {
        //做一个处理，防止服务端返回为null或者空串
        message = checkMsg(unknownError, message);
        BaseModel model = new BaseModel(message, unknownError);
        if (!NetWorkUtils.isAvailableByPing()) {
            model.setCode(NETWORK_ERROR);
            model.setMsg("当前网络不可用，请检查手机网络设置！");
        }
        onExceptions(model.getCode(), model.getMsg());
        if (view != null) {
            view.onErrorCode(model);
        }
    }

    /**
     * 检查返回的msg
     */
    private String checkMsg(int code, String msg){
       String result = "";
       if(msg == null || TextUtils.isEmpty(msg)){
            result = getMsgByCode(code);
       }else {
           result = msg;
       }
       return result;
    }

    /**
     * 根据状态码返回msg
     */
    private String getMsgByCode(int code){
        String result = "";
        switch (code){
            case CONNECT_NOT_LOGIN:
                result = "未登录";
                break;
            case CONNECT_NOT_REGISTER://手机号码未注册
                result = "手机号未注册";
                break;
            case CONNECT_NOT_BIND://手机号码已注册但未绑定
                result = "手机号已注册但未绑定";
                break;
            case CONNECT_CODE_NOT_MATCH://验证码不匹配
                result = "验证码不匹配";
                break;
            case CONNECT_HAS_BIND_OTHER://该手机号已绑定其他用户
                result = "该手机号已绑定其他用户";
                break;
            case CONNECT_INVALID_TOKEN://访问网关错误，需要刷新token
                result = "需要刷新token";
                break;
        }
        return result;
    }

    private void onExceptions(int unknownError, String message) {
        switch (unknownError) {
            case CONNECT_ERROR:
                onError("网络连接错误");
                break;
            case CONNECT_TIMEOUT:
                onError("网络连接超时");
                break;
            case BAD_NETWORK:
                onError("网络超时");
                break;
            case PARSE_ERROR:
                onError("数据解析失败");
                break;
            //未登录
            case CONNECT_NOT_LOGIN:
//                onError("未登录");
                break;
            //正常执行  提示信息
            case OTHER_MESSAGE:
                onError(message);
                break;
            //网络不可用
            case NETWORK_ERROR:
                onError("当前网络不可用，请检查手机网络设置！");
                break;
            default:
                break;
        }
    }

    //消失写到这 有一定的延迟  对dialog显示有影响
    @Override
    public void onComplete() {
       /* if (view != null) {
            view.hideLoading();
        }*/
    }

    public abstract void onSuccess(T o);

    public abstract void onError(String msg);
}
