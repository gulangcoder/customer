package cn.fintecher.app.model.product;

import java.math.BigDecimal;
import java.util.Date;

public class EntProductCredit {
    private String id;

    private String companyCode;

    private String productId;

    private String productSeries;

    private String productDetailId;

    private String productSequence;

    private String productName;

    private Integer serial;

    private String paymentWay;

    private Integer periods;

    private Integer periodsDays;

    private Short eachTermType;

    private BigDecimal rate;

    private Short rateFlag;

    private BigDecimal contractRate;

    private Short contractRateFlag;

    private Short paymenyDaysType;

    private String paymentDay;

    private BigDecimal contractOverdueRate;

    private Integer creditProtectionDays;

    private Integer overdueProtectionDays;

    private Short overdueRateType;

    private BigDecimal overdueRate;

    private Short nopromiseCompanyType;

    private String penaltyRule;

    private Short havePrepayment;

    private Short prepaymentRateType;

    private BigDecimal prepaymentRate;

    private Short prepaymentCompanyType;

    private String prepaymentRule;

    private Short haveCash;

    private Short cashDepositType;

    private BigDecimal cashDeposit;

    private String refundWay;

    private Short cashCompanyType;

    private String cashDepositRule;

    private Short haveService;

    private BigDecimal serviceFeeRate;

    private BigDecimal earlyServiceFeeRate;

    private String serviceFeeRule;

    private Short serviceCompanyType;

    private Short havePoundage;

    private BigDecimal poundage;

    private Short poundageCompanyType;

    private String remark;

    private Short status;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private Short deleteFlag;

    private String batch;

    private String paymentWayName;

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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId == null ? null : productId.trim();
    }

    public String getProductSeries() {
        return productSeries;
    }

    public void setProductSeries(String productSeries) {
        this.productSeries = productSeries == null ? null : productSeries.trim();
    }

    public String getProductDetailId() {
        return productDetailId;
    }

    public void setProductDetailId(String productDetailId) {
        this.productDetailId = productDetailId == null ? null : productDetailId.trim();
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

    public Integer getSerial() {
        return serial;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay == null ? null : paymentWay.trim();
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public Integer getPeriodsDays() {
        return periodsDays;
    }

    public void setPeriodsDays(Integer periodsDays) {
        this.periodsDays = periodsDays;
    }

    public Short getEachTermType() {
        return eachTermType;
    }

    public void setEachTermType(Short eachTermType) {
        this.eachTermType = eachTermType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Short getRateFlag() {
        return rateFlag;
    }

    public void setRateFlag(Short rateFlag) {
        this.rateFlag = rateFlag;
    }

    public BigDecimal getContractRate() {
        return contractRate;
    }

    public void setContractRate(BigDecimal contractRate) {
        this.contractRate = contractRate;
    }

    public Short getContractRateFlag() {
        return contractRateFlag;
    }

    public void setContractRateFlag(Short contractRateFlag) {
        this.contractRateFlag = contractRateFlag;
    }

    public Short getPaymenyDaysType() {
        return paymenyDaysType;
    }

    public void setPaymenyDaysType(Short paymenyDaysType) {
        this.paymenyDaysType = paymenyDaysType;
    }


    public BigDecimal getContractOverdueRate() {
        return contractOverdueRate;
    }

    public void setContractOverdueRate(BigDecimal contractOverdueRate) {
        this.contractOverdueRate = contractOverdueRate;
    }

    public Integer getCreditProtectionDays() {
        return creditProtectionDays;
    }

    public void setCreditProtectionDays(Integer creditProtectionDays) {
        this.creditProtectionDays = creditProtectionDays;
    }

    public Integer getOverdueProtectionDays() {
        return overdueProtectionDays;
    }

    public void setOverdueProtectionDays(Integer overdueProtectionDays) {
        this.overdueProtectionDays = overdueProtectionDays;
    }

    public Short getOverdueRateType() {
        return overdueRateType;
    }

    public void setOverdueRateType(Short overdueRateType) {
        this.overdueRateType = overdueRateType;
    }

    public BigDecimal getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(BigDecimal overdueRate) {
        this.overdueRate = overdueRate;
    }

    public Short getNopromiseCompanyType() {
        return nopromiseCompanyType;
    }

    public void setNopromiseCompanyType(Short nopromiseCompanyType) {
        this.nopromiseCompanyType = nopromiseCompanyType;
    }

    public String getPenaltyRule() {
        return penaltyRule;
    }

    public void setPenaltyRule(String penaltyRule) {
        this.penaltyRule = penaltyRule == null ? null : penaltyRule.trim();
    }

    public Short getHavePrepayment() {
        return havePrepayment;
    }

    public void setHavePrepayment(Short havePrepayment) {
        this.havePrepayment = havePrepayment;
    }

    public Short getPrepaymentRateType() {
        return prepaymentRateType;
    }

    public void setPrepaymentRateType(Short prepaymentRateType) {
        this.prepaymentRateType = prepaymentRateType;
    }

    public BigDecimal getPrepaymentRate() {
        return prepaymentRate;
    }

    public void setPrepaymentRate(BigDecimal prepaymentRate) {
        this.prepaymentRate = prepaymentRate;
    }

    public Short getPrepaymentCompanyType() {
        return prepaymentCompanyType;
    }

    public void setPrepaymentCompanyType(Short prepaymentCompanyType) {
        this.prepaymentCompanyType = prepaymentCompanyType;
    }

    public String getPrepaymentRule() {
        return prepaymentRule;
    }

    public void setPrepaymentRule(String prepaymentRule) {
        this.prepaymentRule = prepaymentRule == null ? null : prepaymentRule.trim();
    }

    public Short getHaveCash() {
        return haveCash;
    }

    public void setHaveCash(Short haveCash) {
        this.haveCash = haveCash;
    }

    public Short getCashDepositType() {
        return cashDepositType;
    }

    public void setCashDepositType(Short cashDepositType) {
        this.cashDepositType = cashDepositType;
    }

    public BigDecimal getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(BigDecimal cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public String getRefundWay() {
        return refundWay;
    }

    public void setRefundWay(String refundWay) {
        this.refundWay = refundWay == null ? null : refundWay.trim();
    }

    public Short getCashCompanyType() {
        return cashCompanyType;
    }

    public void setCashCompanyType(Short cashCompanyType) {
        this.cashCompanyType = cashCompanyType;
    }

    public String getCashDepositRule() {
        return cashDepositRule;
    }

    public void setCashDepositRule(String cashDepositRule) {
        this.cashDepositRule = cashDepositRule == null ? null : cashDepositRule.trim();
    }

    public Short getHaveService() {
        return haveService;
    }

    public void setHaveService(Short haveService) {
        this.haveService = haveService;
    }

    public BigDecimal getServiceFeeRate() {
        return serviceFeeRate;
    }

    public void setServiceFeeRate(BigDecimal serviceFeeRate) {
        this.serviceFeeRate = serviceFeeRate;
    }

    public BigDecimal getEarlyServiceFeeRate() {
        return earlyServiceFeeRate;
    }

    public void setEarlyServiceFeeRate(BigDecimal earlyServiceFeeRate) {
        this.earlyServiceFeeRate = earlyServiceFeeRate;
    }

    public String getServiceFeeRule() {
        return serviceFeeRule;
    }

    public void setServiceFeeRule(String serviceFeeRule) {
        this.serviceFeeRule = serviceFeeRule == null ? null : serviceFeeRule.trim();
    }

    public Short getServiceCompanyType() {
        return serviceCompanyType;
    }

    public void setServiceCompanyType(Short serviceCompanyType) {
        this.serviceCompanyType = serviceCompanyType;
    }

    public Short getHavePoundage() {
        return havePoundage;
    }

    public void setHavePoundage(Short havePoundage) {
        this.havePoundage = havePoundage;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public Short getPoundageCompanyType() {
        return poundageCompanyType;
    }

    public void setPoundageCompanyType(Short poundageCompanyType) {
        this.poundageCompanyType = poundageCompanyType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    public Short getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Short deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch == null ? null : batch.trim();
    }

    public String getPaymentWayName() {
        return paymentWayName;
    }

    public void setPaymentWayName(String paymentWayName) {
        this.paymentWayName = paymentWayName;
    }

    public String getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(String paymentDay) {
        this.paymentDay = paymentDay;
    }
}