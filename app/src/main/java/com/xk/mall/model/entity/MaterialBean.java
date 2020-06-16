package com.xk.mall.model.entity;

import java.util.List;

/**
 * @ClassName: MaterialBean
 * @Description: 素材数据
 * @Author: 卿凯
 * @Date: 2019/12/9/009 16:35
 * @Version: 1.0
 */
public class MaterialBean {

    /**
     * advertImgModels : [{"imgId":"","imgSort":0,"imgSpare":"","imgUrl":"","materialId":""}]
     * createTime :
     * materialName :
     * materialRemark :
     * materialSpare :
     * materialType :
     * updateTime :
     */

    private String createTime;//创建时间
    private String materialName;//素材名称
    private String materialRemark;//描述备注
    private String materialSpare;//预留
    private String materialType;//素材类型
    private String updateTime;//更新时间
    private List<AdvertImgModelsBean> advertImgModels;//素材图片信息

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialRemark() {
        return materialRemark;
    }

    public void setMaterialRemark(String materialRemark) {
        this.materialRemark = materialRemark;
    }

    public String getMaterialSpare() {
        return materialSpare;
    }

    public void setMaterialSpare(String materialSpare) {
        this.materialSpare = materialSpare;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<AdvertImgModelsBean> getAdvertImgModels() {
        return advertImgModels;
    }

    public void setAdvertImgModels(List<AdvertImgModelsBean> advertImgModels) {
        this.advertImgModels = advertImgModels;
    }

    public static class AdvertImgModelsBean {
        /**
         * imgId :
         * imgSort : 0
         * imgSpare :
         * imgUrl :
         * materialId :
         */

        private String imgId;
        private int imgSort;
        private String imgSpare;
        private String imgUrl;
        private String materialId;

        public String getImgId() {
            return imgId;
        }

        public void setImgId(String imgId) {
            this.imgId = imgId;
        }

        public int getImgSort() {
            return imgSort;
        }

        public void setImgSort(int imgSort) {
            this.imgSort = imgSort;
        }

        public String getImgSpare() {
            return imgSpare;
        }

        public void setImgSpare(String imgSpare) {
            this.imgSpare = imgSpare;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getMaterialId() {
            return materialId;
        }

        public void setMaterialId(String materialId) {
            this.materialId = materialId;
        }
    }
}
