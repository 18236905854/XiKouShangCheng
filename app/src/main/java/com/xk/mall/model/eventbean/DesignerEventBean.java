package com.xk.mall.model.eventbean;

/**
 *  设计师 eventBean
 */
public class DesignerEventBean {
    private int position;
    private int type;//1关注  0取消关注


    public DesignerEventBean(int position, int type) {
        this.position = position;
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
