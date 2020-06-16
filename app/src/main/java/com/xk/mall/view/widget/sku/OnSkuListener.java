package com.xk.mall.view.widget.sku;

import com.xk.mall.model.entity.GoodsSkuListBean;
import com.xk.mall.model.entity.GoodsSkuListBean2;
import com.xk.mall.model.entity.SkuAttribute;
import com.xk.mall.model.entity.SkuListBean;


/**
 * Created by wuhenzhizao on 2017/8/30.
 */
public interface OnSkuListener {
    /**
     * 属性取消选中
     *
     * @param unselectedAttribute
     */
    void onUnselected(SkuAttribute unselectedAttribute);

    /**
     * 属性选中
     *
     * @param selectAttribute
     */
    void onSelect(SkuAttribute selectAttribute);

    /**
     * sku选中
     * SkuListBean
     * @param sku
     */
    void onSkuSelected(GoodsSkuListBean2 sku);
}