package cn.fintecher.system.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class EntProductCredit implements Serializable {
    private static final long serialVersionUID = 1745840201486055397L;

    private String id;

    //公司编码
    private String companyCode;

    //产品id
    private String productId;

    //产品系列
    private String productSeries;

    //产品详情id
    private String productDetailId;

    //产品序列
    private String productSequence;

    //产品名称
    private String productName;

    //序号
    private Integer serial;

    //还款方式 数据字典查
    private String paymentWay;

    //期数
    private Integer periods;

    //每期期限
    private Integer periodsDays;

    //每期期限类型 0日 1周 2月
    private Short eachTermType;

    //利率
    private BigDecimal rate;

    //利率标识 0日 1月 2年
    private Short rateFlag;

    //合同利率
    private BigDecimal contractRate;

    //合同利率标识 0日 1月 2年
    private Short contractRateFlag;

    //账期类型 0正常账期 1固定日账期
    private Short paymenyDaysType;

    //账期日
    private Integer paymentDay;

    //合同逾期费用率
    private BigDecimal contractOverdueRate;

    //征信保护天数
    private Integer creditProtectionDays;

    //逾期保护天数
    private Integer overdueProtectionDays;

    //逾期费率类型 0固定费率 1阶梯费率
    private Short overdueRateType;

    //类型为固定费率的逾期费率
    private BigDecimal overdueRate;

    //违约是否需要公司占比 0是 1否
    private Short nopromiseCompanyType;

    //罚息配置
    private String penaltyRule;

    //是否提前还款  0是 1否
    private Short havePrepayment;

    //提前还款违约率类型 0固定费率 1阶梯费率
    private Short prepaymentRateType;

    //类型为固定费率的提前还款违约费率
    private BigDecimal prepaymentRate;

    //提前还款是否需要公司占比 0是 1否
    private Short prepaymentCompanyType;

    //提前还款规则
    private String prepaymentRule;

    //有无保证金 0有 1无
    private Short haveCash;

    //保证金类型 0固定金额 1比例
    private Short cashDepositType;

    //保证金或保证金费率
    private BigDecimal cashDeposit;

    //退换方式
    private String refundWay;

    //保证金是否需要公司占比 0是 1否
    private Short cashCompanyType;

    //保证金规则
    private String cashDepositRule;

    //有无服务费 0有 1无
    private Short haveService;

    //分期服务费费率
    private BigDecimal serviceFeeRate;

    //前期服务器费率
    private BigDecimal earlyServiceFeeRate;

    //服务费规则
    private String serviceFeeRule;

    //服务费是否需要公司占比
    private Short serviceCompanyType;

    //有无手续费 0有 1无
    private Short havePoundage;

    //手续费
    private BigDecimal poundage;

    //手续费是否需要公司占比
    private Short poundageCompanyType;

    //备注
    private String remark;

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

    //删除:1,未删除:0
    private Short deleteFlag;

    //唯一标识批次，用于记录修改之前的数据
    private String batch;

    //逾期阶梯费率
    private List<Map> overdueLadder;

    //提前还款阶梯费率
    private List<Map> prepaymentLadder;

    //逾期公司占比
    private List<Map> overdueCompanyRate;

    //提前还款公司占比
    private List<Map> prepaymentCompanyRate;

    //保证金公司占比
    private List<Map> cashCompanyRate;

    //服务费公司占比
    private List<Map> serviceCompanyRate;

    //手续费公司占比
    private List<Map> poundageCompanyRate;

    private List<EntProductLadder> ladderList;

    private List<EntProductCompanyRate> companyRateList;

    public List<Map> getOverdueLadder() {
        return overdueLadder;
    }

    public void setOverdueLadder(List<Map> overdueLadder) {
        this.overdueLadder = overdueLadder;
    }

    public List<Map> getPrepaymentLadder() {
        return prepaymentLadder;
    }

    public void setPrepaymentLadder(List<Map> prepaymentLadder) {
        this.prepaymentLadder = prepaymentLadder;
    }

    public List<Map> getOverdueCompanyRate() {
        return overdueCompanyRate;
    }

    public void setOverdueCompanyRate(List<Map> overdueCompanyRate) {
        this.overdueCompanyRate = overdueCompanyRate;
    }

    public List<Map> getPrepaymentCompanyRate() {
        return prepaymentCompanyRate;
    }

    public void setPrepaymentCompanyRate(List<Map> prepaymentCompanyRate) {
        this.prepaymentCompanyRate = prepaymentCompanyRate;
    }

    public List<Map> getCashCompanyRate() {
        return cashCompanyRate;
    }

    public void setCashCompanyRate(List<Map> cashCompanyRate) {
        this.cashCompanyRate = cashCompanyRate;
    }

    public List<Map> getServiceCompanyRate() {
        return serviceCompanyRate;
    }

    public void setServiceCompanyRate(List<Map> serviceCompanyRate) {
        this.serviceCompanyRate = serviceCompanyRate;
    }

    public List<Map> getPoundageCompanyRate() {
        return poundageCompanyRate;
    }

    public void setPoundageCompanyRate(List<Map> poundageCompanyRate) {
        this.poundageCompanyRate = poundageCompanyRate;
    }

    public List<EntProductLadder> getLadderList() {
        return ladderList;
    }

    public void setLadderList(List<EntProductLadder> ladderList) {
        this.ladderList = ladderList;
    }

    public List<EntProductCompanyRate> getCompanyRateList() {
        return companyRateList;
    }

    public void setCompanyRateList(List<EntProductCompanyRate> companyRateList) {
        this.companyRateList = companyRateList;
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

    public Integer getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(Integer paymentDay) {
        this.paymentDay = paymentDay;
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
}