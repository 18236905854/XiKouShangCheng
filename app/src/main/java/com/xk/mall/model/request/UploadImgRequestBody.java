package com.xk.mall.model.request;

import java.io.FileInputStream;

/**
 * ClassName UploadImgRequestBody
 * Description 上传用户头像的请求体Bean
 * Author Kay
 * Date 2019/6/19/019
 * Version V1.0
 */
public class UploadImgRequestBody {
    public FileInputStream file;//文件流

    public UploadImgRequestBody(FileInputStream file) {
        this.file = file;
    }
}
