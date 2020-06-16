package com.xk.mall.model.entity;

/**
 * @ClassName: UpdateAppBean
 * @Description: App更新对象
 * @Author: 卿凯
 * @Date: 2019/11/27/027 17:15
 * @Version: 1.0
 */
public class UpdateAppBean {
    //app名称
    private String appName;
    //app类别
    private int appType;
    //内部版本号
    private String foreignVersionNumber;
    //外部版本号
    private String internallyVersionNumber;
    //版本说明
    private String versionNotes;
    //app平台(1:IOS   2:Android)
    private int versionPlatform;
    //是否强制更新(0:否    1:是)
    private int ifForceUpdate;
    //版本更新时间
    private String versionUpdateDate;
    //下载链接地址
    private String downloadLink;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getAppType() {
        return appType;
    }

    public void setAppType(int appType) {
        this.appType = appType;
    }

    public String getForeignVersionNumber() {
        return foreignVersionNumber;
    }

    public void setForeignVersionNumber(String foreignVersionNumber) {
        this.foreignVersionNumber = foreignVersionNumber;
    }

    public String getInternallyVersionNumber() {
        return internallyVersionNumber;
    }

    public void setInternallyVersionNumber(String internallyVersionNumber) {
        this.internallyVersionNumber = internallyVersionNumber;
    }

    public String getVersionNotes() {
        return versionNotes;
    }

    public void setVersionNotes(String versionNotes) {
        this.versionNotes = versionNotes;
    }

    public int getVersionPlatform() {
        return versionPlatform;
    }

    public void setVersionPlatform(int versionPlatform) {
        this.versionPlatform = versionPlatform;
    }

    public int getIfForceUpdate() {
        return ifForceUpdate;
    }

    public void setIfForceUpdate(int ifForceUpdate) {
        this.ifForceUpdate = ifForceUpdate;
    }

    public String getVersionUpdateDate() {
        return versionUpdateDate;
    }

    public void setVersionUpdateDate(String versionUpdateDate) {
        this.versionUpdateDate = versionUpdateDate;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
}
