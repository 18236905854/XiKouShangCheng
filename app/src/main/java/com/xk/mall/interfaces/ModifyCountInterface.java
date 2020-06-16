package com.xk.mall.interfaces;

import android.view.View;

/**
 *  购物车 改变数量的接口
 */
public interface ModifyCountInterface {

    /**
     * 增加操作
     *
     * @param position      元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    void doIncrease(int bigPosition, int position, View showCountView, boolean isChecked);

    /**
     * 删减操作
     *
     * @param position      元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    void doDecrease(int bigPosition, int position, View showCountView, boolean isChecked);

    /**
     * 删除子item
     *
     * @param bigPosition
     * @param position
     */
    void childDelete(int bigPosition, int position);
}
