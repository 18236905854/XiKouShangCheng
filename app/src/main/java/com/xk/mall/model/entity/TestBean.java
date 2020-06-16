package com.xk.mall.model.entity;

import java.util.List;

/**
 * @ClassName TestBean
 * @Description 测试接口返回数据的Bean
 * @Author 卿凯
 * @Date 2019/5/31/031
 * @Version ${VERSION}
 */
public class TestBean {
    public int pageCount;
    public int totalCount;
    public List<MainBean> result;

    @Override
    public String toString() {
        return "TestBean{" +
                "pageCount=" + pageCount +
                ", totalCount=" + totalCount +
                ", result=" + result +
                '}';
    }
}
