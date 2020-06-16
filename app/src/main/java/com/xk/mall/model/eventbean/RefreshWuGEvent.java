package com.xk.mall.model.eventbean;

/**
 * 刷新吾G
 */
public class RefreshWuGEvent {
    private boolean flag;

    public RefreshWuGEvent(boolean flag) {
        this.flag = flag;

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

}
