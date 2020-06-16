package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.MaterialRemarkBean;
import com.xk.mall.model.entity.TaskBean;
import com.xk.mall.model.entity.UploadLogoBean;

/**
 * @ClassName: MaterialPostViewImpl
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/12/8/008 15:53
 * @Version: 1.0
 */
public interface MaterialPostViewImpl extends BaseViewListener {
    //上传图片成功
    void onUploadImgSuccess(BaseModel<String> model);
    //提交成功
    void onSaveSuccess(BaseModel baseModel);
    //获取任务列表成功
    void onGetTaskListSuccess(BaseModel<TaskBean> model);
    //获取说明文字成功
    void onGetRemarksSuccess(BaseModel<MaterialRemarkBean> beanBaseModel);
}
