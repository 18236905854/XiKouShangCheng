package com.xk.mall.model.eventbean;

/**
 * 是否刷新 我的地址列表  增加 修改之后
 */
public class RefreshAddressBean {
    private boolean isReresh;

    public RefreshAddressBean(boolean isReresh) {
        this.isReresh = isReresh;
    }

    public boolean isReresh() {
        return isReresh;
    }

    public void setReresh(boolean reresh) {
        isReresh = reresh;
    }
}
