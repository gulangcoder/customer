package cn.fintecher.app.model.order;

import cn.fintecher.app.model.product.EntProductCredit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Title :
 * Description : @订单model@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public class EntOrder {

    private String id;

    //订单编号
    private String orderNo;

    //用户id
    private String custId;
    
    // TODO: 2018/9/15  custModel

    //1借款申请;2放款审批;3放款中;4已放款;5结清;6拒绝;7放款失败
    private Integer state;

    //产品信贷费率
    private String productCreditId;

    //产品信贷费率
    private EntProductCredit productCreditModel;

    //产品id
    private String productId;

    //产品名称
    private String productName;

    //借款金额
    private BigDecimal loanAmount;

    //放款流水号
    private String grantFlowNumber;

    //放款时间
    private Date grantTime;

    //到期时间
    private Date expiryTime;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //公司编码
    private String companyCode;

    //借款用途
    public String loanPurpose;

    //前置服务费
    private BigDecimal prepositionServiceCharge;

    //服务费
    private BigDecimal serviceCharge;

    //保证金
    private BigDecimal bond;

    //转账手续费
    private BigDecimal poundage;

    //已还本金
    private BigDecimal alreadyPrincipal;

    //已还利息
    private BigDecimal alreadyInterest;

    //期限
    private Integer periods;

    //利率
    private BigDecimal rate;

    //还款方式
    private String paymentWayName;

    //还款计划
    private List<EntRepaymentPlan> planList;

    //待还总额
    private BigDecimal pendingTotal;

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public List<EntRepaymentPlan> getPlanList() {
        return planList;
    }

    public void setPlanList(List<EntRepaymentPlan> planList) {
        this.planList = planList;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public EntProductCredit getProductCreditModel() {
        return productCreditModel;
    }

    public void setProductCreditModel(EntProductCredit productCreditModel) {
        this.productCreditModel = productCreditModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getProductCreditId() {
        return productCreditId;
    }

    public void setProductCreditId(String productCreditId) {
        this.productCreditId = productCreditId == null ? null : productCreditId.trim();
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getGrantFlowNumber() {
        return grantFlowNumber;
    }

    public void setGrantFlowNumber(String grantFlowNumber) {
        this.grantFlowNumber = grantFlowNumber == null ? null : grantFlowNumber.trim();
    }

    public Date getGrantTime() {
        return grantTime;
    }

    public void setGrantTime(Date grantTime) {
        this.grantTime = grantTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public BigDecimal getPrepositionServiceCharge() {
        return prepositionServiceCharge;
    }

    public void setPrepositionServiceCharge(BigDecimal prepositionServiceCharge) {
        this.prepositionServiceCharge = prepositionServiceCharge;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getBond() {
        return bond;
    }

    public void setBond(BigDecimal bond) {
        this.bond = bond;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public BigDecimal getAlreadyPrincipal() {
        return alreadyPrincipal;
    }

    public void setAlreadyPrincipal(BigDecimal alreadyPrincipal) {
        this.alreadyPrincipal = alreadyPrincipal;
    }

    public BigDecimal getAlreadyInterest() {
        return alreadyInterest;
    }

    public void setAlreadyInterest(BigDecimal alreadyInterest) {
        this.alreadyInterest = alreadyInterest;
    }

    public Integer getPeriods() {
        return periods;
    }

    public void setPeriods(Integer periods) {
        this.periods = periods;
    }

    public BigDecimal getRete() {
        return rate;
    }

    public void setRete(BigDecimal rete) {
        this.rate = rete;
    }

    public String getPaymentWayName() {
        return paymentWayName;
    }

    public void setPaymentWayName(String paymentWayName) {
        this.paymentWayName = paymentWayName;
    }

    public BigDecimal getPendingTotal() {
        return pendingTotal;
    }

    public void setPendingTotal(BigDecimal pendingTotal) {
        this.pendingTotal = pendingTotal;
    }
}