package cn.fintecher.system.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class EntProductLadder implements Serializable {
    private static final long serialVersionUID = 4495124642451929767L;

    private String id;

    private String companyCode;

    private String productCreditId;

    private Integer minDays;

    private Integer maxDays;

    private BigDecimal amountRate;

    private Short deleteFlag;

    private Short type;

    private String batch;

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

    public String getProductCreditId() {
        return productCreditId;
    }

    public void setProductCreditId(String productCreditId) {
        this.productCreditId = productCreditId == null ? null : productCreditId.trim();
    }

    public Integer getMinDays() {
        return minDays;
    }

    public void setMinDays(Integer minDays) {
        this.minDays = minDays;
    }

    public Integer getMaxDays() {
        return maxDays;
    }

    public void setMaxDays(Integer maxDays) {
        this.maxDays = maxDays;
    }

    public BigDecimal getAmountRate() {
        return amountRate;
    }

    public void setAmountRate(BigDecimal amountRate) {
        this.amountRate = amountRate;
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
}