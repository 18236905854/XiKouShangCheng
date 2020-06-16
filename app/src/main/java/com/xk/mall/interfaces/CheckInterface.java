package com.xk.mall.interfaces;

/**
 * 购物车
 */
public interface CheckInterface {
    /**
     * 组选框状态改变触发的事件
     *
     * @param bigPosition 外层 下标
     * @param position    元素位置
     * @param isChecked   元素选中与否
     */
    void checkGroup(int bigPosition, int position, boolean isChecked);
}
