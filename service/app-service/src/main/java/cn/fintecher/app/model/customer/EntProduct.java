package cn.fintecher.app.model.customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EntProduct implements Serializable {

    private static final long serialVersionUID = 3164949001866844399L;

    private String id;

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

    //产品描述
    private String description;

    //是否首页推荐 0是 1否
    private Short recommend;

    //停用:0,启用:1
    private Short status;

    //创建人
    private String createUser;

    //创建时间
    private Date createTime;

    //更新人
    private String updateUser;

    //更新时间
    private Date updateTime;

    private List<EntProductVideo> videoList;

    public List<EntProductVideo> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<EntProductVideo> videoList) {
        this.videoList = videoList;
    }

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
}