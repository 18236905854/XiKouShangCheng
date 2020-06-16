package com.xk.mall.model.entity;

import java.util.List;

/**
 * @ClassName: MaterialRemarkBean
 * @Description: 素材提交页面备注说明对象
 * @Author: 卿凯
 * @Date: 2019/12/10/010 21:25
 * @Version: 1.0
 */
public class MaterialRemarkBean {

    /**
     * topic : 截图说明
     * remarks : ["1、截图请包含分享人、分享时间。","2、请于每日分享最新内容，并于当天提交截图。","3、工作人员将在1-3个工作日内完成审核。","4、通过审核后，您将获得贡献值奖励。"]
     */

    private String topic;
    private List<String> remarks;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<String> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<String> remarks) {
        this.remarks = remarks;
    }
}
