package com.xk.mall.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 首页消息点数
 */
public class HomeMessageBean implements Serializable{

    private int totalUnreadNum; //未读消息数
    private List<TypesListBean> typesList;
    public int getTotalUnreadNum() {
        return totalUnreadNum;
    }

    public void setTotalUnreadNum(int totalUnreadNum) {
        this.totalUnreadNum = totalUnreadNum;
    }

    public List<TypesListBean> getTypesList() {
        return typesList;
    }

    public void setTypesList(List<TypesListBean> typesList) {
        this.typesList = typesList;
    }

    public static class TypesListBean  implements Serializable {
        /**
         * id :类型id：1：系统消息，2：订单助手，3：活动消息，4：平台公告
         * name :消息类型名称
         * unreadNum : 0 未读数
         */

        private String id;
        private String name;
        private int unreadNum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUnreadNum() {
            return unreadNum;
        }

        public void setUnreadNum(int unreadNum) {
            this.unreadNum = unreadNum;
        }
    }

}
