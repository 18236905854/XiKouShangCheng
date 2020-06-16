package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.UploadLogoBean;

/**
 * @ClassName: PayBackPostViewImpl
 * @Description: java类作用描述
 * @Author: 卿凯
 * @Date: 2019/12/9/009 9:57
 * @Version: 1.0
 */
public interface PayBackPostViewImpl extends BaseViewListener {
    //申请成功
    void onPostSuccess(BaseModel<String> baseModel);
    //上传图片成功
    void onUploadImgSuccess(BaseModel<UploadLogoBean> model);
}
