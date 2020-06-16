package com.xk.mall.model.entity;

/**
 * @ClassName: BankBean
 * @Description: 银行卡对象
 * @Author: 卿凯
 * @Date: 2019/10/8/008 14:44
 * @Version: 1.0
 */
public class BankBean {

    /**
     * code :
     * id : 0
     * image :
     * isDel : 0
     * level : 0
     * name :
     * swift :
     */

    private String code;
    private String image;
    private int level;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
