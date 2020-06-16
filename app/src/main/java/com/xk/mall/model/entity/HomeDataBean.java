package com.xk.mall.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 首页列表数据
 */
public class HomeDataBean implements Serializable {
  private int type;//type=1 吾G限时购   type=2 喜立得 type=3 分享  type 4 多买多得 type 5 0元购
  private String title;
  private String titleDesc;
  private Integer mainUrl;
  private List<HomeGoodsBean> homeGoodsBeanList;

    public HomeDataBean(int type, String title, String titleDesc, Integer mainUrl, List<HomeGoodsBean> homeGoodsBeanList) {
        this.type = type;
        this.title = title;
        this.titleDesc = titleDesc;
        this.mainUrl = mainUrl;
        this.homeGoodsBeanList = homeGoodsBeanList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    public Integer getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(Integer mainUrl) {
        this.mainUrl = mainUrl;
    }

    public List<HomeGoodsBean> getHomeGoodsBeanList() {
        return homeGoodsBeanList;
    }

    public void setHomeGoodsBeanList(List<HomeGoodsBean> homeGoodsBeanList) {
        this.homeGoodsBeanList = homeGoodsBeanList;
    }

    public static class HomeGoodsBean{
      private Integer image;
      private int goodsId;
      private String name;
      private String price;
      private String linePrice;
      private String vipDiscount;//会员折扣
      private int hotSellNum;//热卖
      private int giftCoupon;//赠券

        public HomeGoodsBean(Integer image, int goodsId, String name, String price, String linePrice, String vipDiscount, int hotSellNum, int giftCoupon) {
            this.image = image;
            this.goodsId = goodsId;
            this.name = name;
            this.price = price;
            this.linePrice = linePrice;
            this.vipDiscount = vipDiscount;
            this.hotSellNum = hotSellNum;
            this.giftCoupon = giftCoupon;
        }

        public Integer getImage() {
            return image;
        }

        public void setImage(Integer image) {
            this.image = image;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getLinePrice() {
            return linePrice;
        }

        public void setLinePrice(String linePrice) {
            this.linePrice = linePrice;
        }

        public String getVipDiscount() {
            return vipDiscount;
        }

        public void setVipDiscount(String vipDiscount) {
            this.vipDiscount = vipDiscount;
        }

        public int getHotSellNum() {
            return hotSellNum;
        }

        public void setHotSellNum(int hotSellNum) {
            this.hotSellNum = hotSellNum;
        }

        public int getGiftCoupon() {
            return giftCoupon;
        }

        public void setGiftCoupon(int giftCoupon) {
            this.giftCoupon = giftCoupon;
        }
    }
}
