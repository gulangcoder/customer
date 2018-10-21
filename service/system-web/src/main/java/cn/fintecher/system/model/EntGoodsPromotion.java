package cn.fintecher.system.model;

import java.io.Serializable;

public class EntGoodsPromotion implements Serializable{
    private static final long serialVersionUID = -662001509798453197L;

    //主键
    private String id;

    //商品编号
    private String goodsCode;

    //活动编号
    private String promotionCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode == null ? null : goodsCode.trim();
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode == null ? null : promotionCode.trim();
    }
}