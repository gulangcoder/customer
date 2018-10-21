package cn.fintecher.app.model.product;

import java.math.BigDecimal;
import java.util.Date;

public class EntProductDetail {
    private String id;

    private String productId;

    private String companyCode;

    private Integer sequence;

    private String productSequence;

    private String productName;

    private String productImage;

    private BigDecimal productMinLines;

    private BigDecimal productMaxLines;

    private Integer creditValidity;

    private Short creditFlag;

    private String applicablePeople;

    private String productDescription;

    private Short withholdingSchedule;

    private Short paymentsSchedule;

    private Short prepayment;

    private String shadowData;

    private String associatedCompany;

    private Short status;

    private String remark;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
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

    public String getProductSequence() {
        return productSequence;
    }

    public void setProductSequence(String productSequence) {
        this.productSequence = productSequence == null ? null : productSequence.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
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

    public Integer getCreditValidity() {
        return creditValidity;
    }

    public void setCreditValidity(Integer creditValidity) {
        this.creditValidity = creditValidity;
    }

    public Short getCreditFlag() {
        return creditFlag;
    }

    public void setCreditFlag(Short creditFlag) {
        this.creditFlag = creditFlag;
    }

    public String getApplicablePeople() {
        return applicablePeople;
    }

    public void setApplicablePeople(String applicablePeople) {
        this.applicablePeople = applicablePeople == null ? null : applicablePeople.trim();
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription == null ? null : productDescription.trim();
    }

    public Short getWithholdingSchedule() {
        return withholdingSchedule;
    }

    public void setWithholdingSchedule(Short withholdingSchedule) {
        this.withholdingSchedule = withholdingSchedule;
    }

    public Short getPaymentsSchedule() {
        return paymentsSchedule;
    }

    public void setPaymentsSchedule(Short paymentsSchedule) {
        this.paymentsSchedule = paymentsSchedule;
    }

    public Short getPrepayment() {
        return prepayment;
    }

    public void setPrepayment(Short prepayment) {
        this.prepayment = prepayment;
    }

    public String getShadowData() {
        return shadowData;
    }

    public void setShadowData(String shadowData) {
        this.shadowData = shadowData == null ? null : shadowData.trim();
    }

    public String getAssociatedCompany() {
        return associatedCompany;
    }

    public void setAssociatedCompany(String associatedCompany) {
        this.associatedCompany = associatedCompany == null ? null : associatedCompany.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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