package com.xk.mall.model.entity;

import java.util.List;

/**
 * ClassName ActiveSectionBean
 * Description 活动版块Bean
 * Author 卿凯
 * Date 2019/7/29/029
 * Version V1.0
 */
public class ActiveSectionBean {

    private List<BannerBean> bannerList;
    private List<SectionListBean> sectionList;

    public List<BannerBean> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<BannerBean> bannerList) {
        this.bannerList = bannerList;
    }

    public List<SectionListBean> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<SectionListBean> sectionList) {
        this.sectionList = sectionList;
    }

    public static class SectionListBean {
        /**
         * categoryName :
         * childSectionList : [{"categoryName":"","childSectionList":[{}],"id":""}]
         * id :
         */

        private String categoryName;
        private String categoryDescribe;//板块子标题
        private String id;
        private List<ChildSectionListBeanX> childSectionList;

        public String getCategoryChildName() {
            return categoryDescribe;
        }

        public void setCategoryChildName(String categoryChildName) {
            this.categoryDescribe = categoryChildName;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<ChildSectionListBeanX> getChildSectionList() {
            return childSectionList;
        }

        public void setChildSectionList(List<ChildSectionListBeanX> childSectionList) {
            this.childSectionList = childSectionList;
        }

        public static class ChildSectionListBeanX {
            /**
             * categoryName :
             * childSectionList : [{}]
             * id :
             */

            private String categoryName;
            private String id;
            private List<ChildSectionListBean> childSectionList;

            public String getCategoryName() {
                return categoryName;
            }

            public void setCategoryName(String categoryName) {
                this.categoryName = categoryName;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<ChildSectionListBean> getChildSectionList() {
                return childSectionList;
            }

            public void setChildSectionList(List<ChildSectionListBean> childSectionList) {
                this.childSectionList = childSectionList;
            }

            public static class ChildSectionListBean {
            }
        }
    }
}
