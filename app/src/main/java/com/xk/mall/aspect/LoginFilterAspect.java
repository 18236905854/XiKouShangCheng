package com.xk.mall.aspect;

import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.xk.mall.annotation.LoginFilter;
import com.xk.mall.core.ILogin;
import com.xk.mall.core.LoginAssistant;
import com.xk.mall.utils.Constant;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * 登录切面
 */
@Aspect
public class LoginFilterAspect {
    private static final String TAG = "LoginFilterAspect";

    @Pointcut("execution(@com.xk.mall.annotation.LoginFilter * *(..))")
    public void loginFilter() {
    }

    @Around("loginFilter()")
    public void aroundLoginPoint(ProceedingJoinPoint joinPoint) throws Throwable {

        ILogin iLogin = LoginAssistant.getInstance().getiLogin();
        if (iLogin == null) {
            throw new Exception("LoginSDK 没有初始化！");
        }

        Signature signature = joinPoint.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new Exception("LoginFilter 注解只能用于方法上");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        LoginFilter loginFilter = methodSignature.getMethod().getAnnotation(LoginFilter.class);
        if (loginFilter == null) {
            return;
        }

        Context param = LoginAssistant.getInstance().getApplicationContext();
        Log.e(TAG, "aroundLoginPoint: "+iLogin.isLogin(param) );
        Log.e(TAG, "sputils:=== "+ SPUtils.getInstance().getBoolean(Constant.IS_LOGIN,false) );
        if (iLogin.isLogin(param)) {
            joinPoint.proceed();
        } else {
            iLogin.login(param, loginFilter.userDefine());
        }

    }
}
