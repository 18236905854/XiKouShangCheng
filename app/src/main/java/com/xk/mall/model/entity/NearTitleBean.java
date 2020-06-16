package com.xk.mall.model.entity;

import java.io.Serializable;

/**
 * ClassName NearTitleBean
 * Description 附近模块的标题Bean
 * Author 卿凯
 * Date 2019/6/15/015
 * Version V1.0
 */
public class NearTitleBean implements Serializable {
    public String id = "";//唯一标识，用来请求数据
    public String title = "";//标题，用来显示

    public NearTitleBean(String id, String title) {
        this.id = id;
        this.title = title;
    }
}
