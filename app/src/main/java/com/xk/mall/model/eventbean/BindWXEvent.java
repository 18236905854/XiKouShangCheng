package com.xk.mall.model.eventbean;

public class BindWXEvent {
    private boolean isSuccess;//是否绑定成功

    public BindWXEvent(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
