package com.xk.mall.model.entity;

/**
 * @ClassName: RankResultBean
 * @Description: 提升排名成功返回对象
 * @Author: 卿凯
 * @Date: 2019/8/30/030 18:54
 * @Version: 1.0
 */
public class RankResultBean {

    /**
     * ranking : 0
     * result : false
     */

    private int ranking;
    private boolean result;

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
