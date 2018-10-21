package cn.fintecher.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/19
 * @Description:
 */
public class ProductCust implements Serializable{
    private static final long serialVersionUID = -4493510001413414919L;

    private String productId;

    //企业编号
    private String companyCode;

    //序号
    private Integer sequence;

    //产品系列序列
    private String productSeriesSequence;

    //产品系列
    private String productSeries;

    //产品图片
    private String productImage;

    //产品最小额度
    private BigDecimal productMinLines;

    //产品最大额度
    private BigDecimal productMaxLines;

    //适用人群
    private String applicablePeople;

    //是否首页推荐 0是 1否
    private Short recommend;

    private String custId;

    private Short applyStatus;

    private BigDecimal rate;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getProductSeriesSequence() {
        return productSeriesSequence;
    }

    public void setProductSeriesSequence(String productSeriesSequence) {
        this.productSeriesSequence = productSeriesSequence;
    }

    public String getProductSeries() {
        return productSeries;
    }

    public void setProductSeries(String productSeries) {
        this.productSeries = productSeries;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public BigDecimal getProductMinLines() {
        return productMinLines;
    }

    public void setProductMinLines(BigDecimal productMinLines) {
        this.productMinLines = productMinLines;
    }

    public BigDecimal getProductMaxLines() {
        return productMaxLines;
    }

    public void setProductMaxLines(BigDecimal productMaxLines) {
        this.productMaxLines = productMaxLines;
    }

    public String getApplicablePeople() {
        return applicablePeople;
    }

    public void setApplicablePeople(String applicablePeople) {
        this.applicablePeople = applicablePeople;
    }

    public Short getRecommend() {
        return recommend;
    }

    public void setRecommend(Short recommend) {
        this.recommend = recommend;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Short getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Short applyStatus) {
        this.applyStatus = applyStatus;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
