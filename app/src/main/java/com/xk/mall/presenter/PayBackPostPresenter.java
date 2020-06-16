package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.UploadLogoBean;
import com.xk.mall.model.impl.PayBackPostViewImpl;
import com.xk.mall.model.request.PayBackPostRequestBody;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @ClassName: PayBackPostPresenter
 * @Description: 申请退款请求体
 * @Author: 卿凯
 * @Date: 2019/12/9/009 9:56
 * @Version: 1.0
 */
public class PayBackPostPresenter extends BasePresenter<PayBackPostViewImpl> {
    public PayBackPostPresenter(PayBackPostViewImpl baseView) {
        super(baseView);
    }

    public void postBack(PayBackPostRequestBody requestBody){
        addDisposable(apiServer.salesReturn(requestBody), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onPostSuccess((BaseModel) o);
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
