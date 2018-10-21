package cn.fintecher.system.model;

import java.util.Date;


/**
 * Title :
 * Description : @还款计划@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:gaozhidong
 * @version 1.0
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */

public class EntRepaymentPlan {

    private String id;

    private String orderId;

    //当前期数编号
    private Integer periodsNumber;

    private String startTime;

    //还款日
    private String expiryTime;

    //当期本金
    private double principal;

    //当期利息
    private double interest;

    //当期服务费
    private double serviceCharge;

    //前置服务费
    private double prepositionServiceCharge;

    //转账手续费
    private double poundage;

    //保证金
    private double bond;

    //罚息
    private double penaltyInterest;

    //0未还款 1已还款
    private Integer state;

    //生成时间
    private Date createTime;

    //利率 年化
    private double rate;

    //还款方式{1:月付息，到期还本 2:到期一次还本付息 3:等额本息 4 分期等额 5 等额本金 6等额本息周}
    private String payType;


    //总额
    private double total;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getPeriodsNumber() {
        return periodsNumber;
    }

    public void setPeriodsNumber(Integer periodsNumber) {
        this.periodsNumber = periodsNumber;
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

    public double getPrincipal() {
        return principal;
    }

    public void setPrincipal(double principal) {
        this.principal = principal;
    }

    public double getInterest() {
        return interest;
    }

    public void setInterest(double interest) {
        this.interest = interest;
    }

    public double getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(double serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public double getPrepositionServiceCharge() {
        return prepositionServiceCharge;
    }

    public void setPrepositionServiceCharge(double prepositionServiceCharge) {
        this.prepositionServiceCharge = prepositionServiceCharge;
    }

    public double getPoundage() {
        return poundage;
    }

    public void setPoundage(double poundage) {
        this.poundage = poundage;
    }

    public double getBond() {
        return bond;
    }

    public void setBond(double bond) {
        this.bond = bond;
    }

    public double getPenaltyInterest() {
        return penaltyInterest;
    }

    public void setPenaltyInterest(double penaltyInterest) {
        this.penaltyInterest = penaltyInterest;
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

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}