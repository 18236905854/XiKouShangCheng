package com.xk.mall.model.entity;

import java.util.List;

/**
 * 红包明细筛选对象
 */
public class RedChooseBean {

    private List<InBean> income;
    private List<InBean> outcome;

    public List<InBean> getIn() {
        return income;
    }

    public void setIn(List<InBean> in) {
        this.income = in;
    }

    public List<InBean> getOutcome() {
        return outcome;
    }

    public void setOutcome(List<InBean> outcome) {
        this.outcome = outcome;
    }

    public static class InBean {
        /**
         * id : 1
         * name : 推荐奖励
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
