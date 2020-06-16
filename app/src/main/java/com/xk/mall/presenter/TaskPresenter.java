package com.xk.mall.presenter;

import com.xk.mall.base.BaseModel;
import com.xk.mall.base.BaseObserver;
import com.xk.mall.base.BasePresenter;
import com.xk.mall.model.entity.TaskBean;
import com.xk.mall.model.impl.TaskViewImpl;

/**
 * ClassName TaskPresenter
 * Description 任务中心页面请求Presenter
 * Author 卿凯
 * Date 2019/7/20/020
 * Version V1.0
 */
public class TaskPresenter extends BasePresenter<TaskViewImpl> {
    public TaskPresenter(TaskViewImpl baseView) {
        super(baseView);
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
