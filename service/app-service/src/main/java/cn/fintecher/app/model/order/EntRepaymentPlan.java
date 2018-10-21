package cn.fintecher.app.model.order;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Title :
 * Description : @还款计划model@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public class EntRepaymentPlan {

    private String id;

    //客户id
    private String custId;

    //订单id
    private String orderId;

    //订单实体
    private EntOrder order;

    //当前期数
    private Integer periodsNumber;

    //本金
    private BigDecimal principal;

    //利息
    private BigDecimal interest;

    //开始时间
    private String startTime;

    //到期时间
    private String expiryTime;

    //服务费
    private BigDecimal serviceCharge;

    //前置服务费
    private BigDecimal prepositionServiceCharge;

    //保证金
    private BigDecimal bond;

    //转账手续费
    private BigDecimal poundage;

    //状态 0待还 1还款中 2已还款
    private Integer state;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

    //是否逾期 0否 1是
    private int isOverdue;

    //是否本期 0否 1是
    private int thisPeriod;

    //逾期天数
    private int overdueDays;

    //本期还款总额
    private BigDecimal total;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public Integer getPeriodsNumber() {
        return periodsNumber;
    }

    public void setPeriodsNumber(Integer periodsNumber) {
        this.periodsNumber = periodsNumber;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public BigDecimal getPrepositionServiceCharge() {
        return prepositionServiceCharge;
    }

    public void setPrepositionServiceCharge(BigDecimal prepositionServiceCharge) {
        this.prepositionServiceCharge = prepositionServiceCharge;
    }

    public BigDecimal getBond() {
        return bond;
    }

    public void setBond(BigDecimal bond) {
        this.bond = bond;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
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

    public EntOrder getOrder() {
        return order;
    }

    public void setOrder(EntOrder order) {
        this.order = order;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public int getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(int isOverdue) {
        this.isOverdue = isOverdue;
    }

    public int getThisPeriod() {
        return thisPeriod;
    }

    public void setThisPeriod(int thisPeriod) {
        this.thisPeriod = thisPeriod;
    }

    public int getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(int overdueDays) {
        this.overdueDays = overdueDays;
    }
}