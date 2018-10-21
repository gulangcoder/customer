package cn.fintecher.system.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class EntProductCompanyRate implements Serializable {
    private static final long serialVersionUID = 8524877667740896604L;

    private String id;

    private String productCreditId;

    private String companyId;

    private String companyCode;

    private String companyName;

    private BigDecimal rate;

    private Short deleteFlag;

    private Short type;

    private String batch;

    private Short kind;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProductCreditId() {
        return productCreditId;
    }

    public void setProductCreditId(String productCreditId) {
        this.productCreditId = productCreditId == null ? null : productCreditId.trim();
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode == null ? null : companyCode.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public Short getKind() {
        return kind;
    }

    public void setKind(Short kind) {
        this.kind = kind;
    }
}