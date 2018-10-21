package cn.fintecher.app.model.product;

import java.math.BigDecimal;
import java.util.Date;

public class EntProduct {
    private String id;

    private String companyCode;

    private Integer sequence;

    private String productSeriesSequence;

    private String productSeries;

    private String productImage;

    private BigDecimal productMinLines;

    private BigDecimal productMaxLines;

    private String applicablePeople;

    private String description;

    private Short recommend;

    private Short status;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    //最低利率
    private BigDecimal minRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
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
        this.productSeriesSequence = productSeriesSequence == null ? null : productSeriesSequence.trim();
    }

    public String getProductSeries() {
        return productSeries;
    }

    public void setProductSeries(String productSeries) {
        this.productSeries = productSeries == null ? null : productSeries.trim();
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
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
        this.applicablePeople = applicablePeople == null ? null : applicablePeople.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Short getRecommend() {
        return recommend;
    }

    public void setRecommend(Short recommend) {
        this.recommend = recommend;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getMinRate() {
        return minRate;
    }

    public void setMinRate(BigDecimal minRate) {
        this.minRate = minRate;
    }
}