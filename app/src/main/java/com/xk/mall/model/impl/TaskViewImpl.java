package com.xk.mall.model.impl;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseViewListener;
import com.xk.mall.model.entity.TaskBean;

/**
 * ClassName TaskViewImpl
 * Description 任务中心view接口
 * Author 卿凯
 * Date 2019/7/20/020
 * Version V1.0
 */
public interface TaskViewImpl extends BaseViewListener {
    //获取任务中心数据成功回调
    void onGetTaskListSuccess(BaseModel<TaskBean> model);
}
