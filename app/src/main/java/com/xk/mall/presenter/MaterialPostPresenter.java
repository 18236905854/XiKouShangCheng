package com.xk.mall.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.MaterialRemarkBean;
import com.xk.mall.model.entity.TaskBean;
import com.xk.mall.model.entity.UploadLogoBean;
import com.xk.mall.model.impl.MaterialPostViewImpl;
import com.xk.mall.model.request.SaveShareRequestBody;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @ClassName: MaterialPostPresenter
 * @Description: 上传图片请求类
 * @Author: 卿凯
 * @Date: 2019/12/8/008 15:52
 * @Version: 1.0
 */
public class MaterialPostPresenter extends BasePresenter<MaterialPostViewImpl> {

    public MaterialPostPresenter(MaterialPostViewImpl baseView) {
        super(baseView);
    }

    /**
     * 获取备注信息
     */
    public void getRemark(){
        addDisposable(apiServer.getRemarks("screenshot"), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetRemarksSuccess((BaseModel<MaterialRemarkBean>) o);
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
        if(file.length() >= 20 * 1024 * 1024){
            ToastUtils.showShort("上传图片不能超过20M");
            return;
        }
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型

//3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据
        builder.addFormDataPart("folderName", "user");//传入服务器需要的key，和相应value值
        builder.addFormDataPart("file","aa.png",body); //添加图片数据，body创建的请求体

//4.创建List<MultipartBody.Part> 集合，
//  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
//  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
        List<MultipartBody.Part> parts=builder.build().parts();

        addDisposable(apiServer.uploadMaterialImg(parts), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onUploadImgSuccess((BaseModel<String>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    /**
     * 保存审核图片
     */
    public void saveAuditImg(SaveShareRequestBody requestBody){
        addDisposable(apiServer.saveAuditImg(requestBody), new BaseObserver(baseView) {
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
     * 获取任务中心列表
     * @param userId
     */
    public void getTaskList(String userId){
        addDisposable(apiServer.getTaskList(userId), new BaseObserver(baseView) {
            @Override
            public void onSuccess(Object o) {
                baseView.onGetTaskListSuccess((BaseModel<TaskBean>) o);
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
