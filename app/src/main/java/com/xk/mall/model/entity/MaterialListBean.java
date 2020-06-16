package com.xk.mall.model.entity;

import java.util.List;

/**
 * @ClassName: MaterialListBean
 * @Description: 素材的列表对象
 * @Author: 卿凯
 * @Version: 1.6.0
 */
public class MaterialListBean {
    private List<MaterialBean> materialBeans;
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<MaterialBean> getMaterialBeans() {
        return materialBeans;
    }

    public void setMaterialBeans(List<MaterialBean> materialBeans) {
        this.materialBeans = materialBeans;
    }
}
