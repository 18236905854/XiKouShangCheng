package com.xk.mall.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * ClassName AddressServerBean
 * Description 服务端返回的所有地址的Bean
 * Author 卿凯
 * Date 2019/7/4/004
 * Version V1.0
 */
@Entity
public class AddressServerBean {
    @Unique
    public int areaId = 0;//区域ID
    public int parentId = 0;//父级区域id 省级区域传0
    public String name = "";//区域名称
    public int level = 0;//区域等级  等级,1-省；2-市；3-区；4-街道
    @Generated(hash = 2146069498)
    public AddressServerBean(int areaId, int parentId, String name, int level) {
        this.areaId = areaId;
        this.parentId = parentId;
        this.name = name;
        this.level = level;
    }
    @Generated(hash = 1616156284)
    public AddressServerBean() {
    }
    public int getAreaId() {
        return this.areaId;
    }
    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }
    public int getParentId() {
        return this.parentId;
    }
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
}
