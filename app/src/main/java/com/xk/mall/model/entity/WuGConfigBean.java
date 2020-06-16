package com.xk.mall.model.entity;

import java.util.List;

public class WuGConfigBean {

    /**
     * scheduleModelList : [{"createTime":"","endTime":"","id":"","maxGoodsQuantity":0,"maxGoodsStock":0,"maxPurchase":0,"operatorId":"","partitionId":"","serialNo":0,"startTime":"","status":"","updateTime":""}]
     * scheulePartitionSwitch : 0
     * scheuleSwitch : 0
     */

    private int scheulePartitionSwitch;
    private int scheuleSwitch;
    private List<ScheduleModelListBean> scheduleModelList;

    public int getScheulePartitionSwitch() {
        return scheulePartitionSwitch;
    }

    public void setScheulePartitionSwitch(int scheulePartitionSwitch) {
        this.scheulePartitionSwitch = scheulePartitionSwitch;
    }

    public int getScheuleSwitch() {
        return scheuleSwitch;
    }

    public void setScheuleSwitch(int scheuleSwitch) {
        this.scheuleSwitch = scheuleSwitch;
    }

    public List<ScheduleModelListBean> getScheduleModelList() {
        return scheduleModelList;
    }

    public void setScheduleModelList(List<ScheduleModelListBean> scheduleModelList) {
        this.scheduleModelList = scheduleModelList;
    }

    public static class ScheduleModelListBean {
        /**
         * createTime :
         * endTime :
         * id :
         * maxGoodsQuantity : 0
         * maxGoodsStock : 0
         * maxPurchase : 0
         * operatorId :
         * partitionId :
         * serialNo : 0
         * startTime :
         * status :
         * updateTime :
         */

        private String createTime;
        private long endTime;
        private String id;
        private int maxGoodsQuantity;
        private int maxGoodsStock;
        private int maxPurchase;
        private String operatorId;
        private String partitionId;
        private int serialNo;
        private long startTime;
        private String status;
        private String updateTime;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getMaxGoodsQuantity() {
            return maxGoodsQuantity;
        }

        public void setMaxGoodsQuantity(int maxGoodsQuantity) {
            this.maxGoodsQuantity = maxGoodsQuantity;
        }

        public int getMaxGoodsStock() {
            return maxGoodsStock;
        }

        public void setMaxGoodsStock(int maxGoodsStock) {
            this.maxGoodsStock = maxGoodsStock;
        }

        public int getMaxPurchase() {
            return maxPurchase;
        }

        public void setMaxPurchase(int maxPurchase) {
            this.maxPurchase = maxPurchase;
        }

        public String getOperatorId() {
            return operatorId;
        }

        public void setOperatorId(String operatorId) {
            this.operatorId = operatorId;
        }

        public String getPartitionId() {
            return partitionId;
        }

        public void setPartitionId(String partitionId) {
            this.partitionId = partitionId;
        }

        public int getSerialNo() {
            return serialNo;
        }

        public void setSerialNo(int serialNo) {
            this.serialNo = serialNo;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }
    }
}
