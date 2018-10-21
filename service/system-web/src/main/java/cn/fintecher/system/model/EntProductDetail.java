package cn.fintecher.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EntProductDetail implements Serializable {
    private static final long serialVersionUID = 1341448965834174092L;

    private String id;

    //产品系列序列id
    private String productId;

    //企业编号
    private String companyCode;

    //序号
    private Integer sequence;

    //产品序列
    private String productSequence;

    //产品名称
    private String productName;

    //产品图片
    private String productImage;

    //产品最小额度
    private BigDecimal productMinLines;

    //产品最大额度
    private BigDecimal productMaxLines;

    //授信有效期
    private Integer creditValidity;

    //授信有效期标识 0:日 1:月
    private Short creditFlag;

    //使用人群
    private String applicablePeople;

    //产品描述
    private String productDescription;

    //是否支持按期代扣 0:是 1:否
    private Short withholdingSchedule;

    //是否支持按期还款 0:是 1:否
    private Short paymentsSchedule;

    //是否支持提前还款 0:是 1:否
    private Short prepayment;

    //影像资料
    private String shadowData;

    //关联公司
    private String associatedCompany;

    //停用:0,启用:1
    private Short status;

    //备注
    private String remark;

    //创建人
    private String createUser;

    //创建时间
    private Date createTime;

    //更新人
    private String updateUser;

    //更新时间
    private Date updateTime;

    private List<EntProductDetailCompany> associatedCompanyList;

    public List<EntProductDetailCompany> getAssociatedCompanyList() {
        return associatedCompanyList;
    }

    public void setAssociatedCompanyList(List<EntProductDetailCompany> associatedCompanyList) {
        this.associatedCompanyList = associatedCompanyList;
    }

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