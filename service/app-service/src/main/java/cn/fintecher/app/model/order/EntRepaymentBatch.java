package cn.fintecher.app.model.order;

import java.math.BigDecimal;
import java.util.Date;
/**
 * Title :
 * Description : @还款计划批处理model@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public class EntRepaymentBatch {
    private String id;

    //订单id
    private String orderId;

    //当期期数
    private Integer periodsNumber;

    //本金
    private BigDecimal principal;

    //利息
    private BigDecimal interest;

    //开始时间
    private Date startTime;

    //到期时间
    private Date expiryTime;

    //服务费
    private BigDecimal serviceCharge;

    //前置服务费
    private BigDecimal prepositionServiceCharge;

    //保证金
    private BigDecimal bond;

    //罚息
    private BigDecimal defaultInterest;

    //违约金
    private BigDecimal penalty;

    //创建时间
    private Date createTime;

    //更新时间
    private Date updateTime;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
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

    public BigDecimal getDefaultInterest() {
        return defaultInterest;
    }

    public void setDefaultInterest(BigDecimal defaultInterest) {
        this.defaultInterest = defaultInterest;
    }

    public BigDecimal getPenalty() {
        return penalty;
    }

    public void setPenalty(BigDecimal penalty) {
        this.penalty = penalty;
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
}