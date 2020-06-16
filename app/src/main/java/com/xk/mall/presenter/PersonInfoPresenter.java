package com.xk.mall.presenter;

import com.xk.mall.MyApplication;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.LoginBean;
import com.xk.mall.model.entity.UploadLogoBean;
import com.xk.mall.model.entity.UserServerBean;
import com.xk.mall.model.impl.PersonalInfoViewImpl;
import com.xk.mall.model.request.PersonalInfoRequestBody;
import com.xk.mall.model.request.UnbindRequestBody;
import com.xk.mall.model.request.UploadImgRequestBody;
import com.xk.mall.model.request.WXLoginRequestBody;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * ClassName PersonInfoPresenter
 * Description 我的用户页面的Presenter
 * Author 卿凯
 * Date 2019/6/19/019
 * Version V1.0
 */
public class PersonInfoPresenter extends BasePresenter<PersonalInfoViewImpl> {
    public PersonInfoPresenter(PersonalInfoViewImpl baseView) {
        super(baseView);
    }

    /**
     * 根据用户ID获取用户信息接口
     */
    public void getUserInfoById(String userId){
        addDisposable(apiServer.getUserInfoById(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.getUserInfoSuccess((BaseModel<UserServerBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 修改用户信息
     */
    public void modifyPersonalInfo(String headUrl, String userId, String nickName, int sex, String mobile){
        addDisposable(apiServer.modifyPersonalInfo(userId, new PersonalInfoRequestBody(headUrl, userId, nickName, sex, mobile)),
                new BaseObserver(baseView) {
                    @Override
                    public void onSuccess(Object o) {
                        baseView.onSaveSuccess((BaseModel) o);
                    }

                    @Override
                    public void onError(String msg) {

                    }
                });
    }

    /**
     * 解绑第三方账号
     */
    public void unbind(String userId, int loginType){
        addDisposable(apiServer.unBind(new UnbindRequestBody(loginType, userId)), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onUnbindSuccess((BaseModel) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 微信绑定
     */
    public void wxLogin(String appId, String code){
        addDisposable(apiServer.wxLogin(new WXLoginRequestBody(appId, code)), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onWXLoginSuccess((BaseModel<LoginBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 上传用户头像
     * @param filePath 文件路径
     */
    public void uploadImg(String filePath){

        //1.创建MultipartBody.Builder对象
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型

//2.获取图片，创建请求体
        File file = new File(filePath);
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型

//3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据
        builder.addFormDataPart("folderName", "user");//传入服务器需要的key，和相应value值
        builder.addFormDataPart("file","aa.png",body); //添加图片数据，body创建的请求体

//4.创建List<MultipartBody.Part> 集合，
//  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
//  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
        List<MultipartBody.Part> parts=builder.build().parts();

        addDisposable(apiServer.uploadImg(parts), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onUploadImgSuccess((BaseModel<UploadLogoBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
