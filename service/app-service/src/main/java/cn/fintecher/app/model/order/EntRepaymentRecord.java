package cn.fintecher.app.model.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Title :
 * Description : @还款流水model@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public class EntRepaymentRecord {
    //主键 还款流水号
    private String id;

    //订单id
    private String orderId;

    //当前期数
    private Integer periodsNumber;

    //本金
    private BigDecimal principal;

    //利息
    private BigDecimal interest;

    //服务费
    private BigDecimal serviceCharge;

    //罚息
    private BigDecimal defaultInterest;

    //违约金
    private BigDecimal penalty;

    //开始时间
    private Date createTime;

    //还款流水号
    private String repaymentNumber;

    //状态：0还款中，1还款完成，2还款失败
    private Integer state;

    //对应的还款计划
    private List<String> planIdList=new ArrayList<>();

    //对应的还款计划
    private List<EntRepaymentPlan> repaymentPlanList=new ArrayList<>();

    //对应的还款明细
    private List<EntRepaymentRecordDetailed> entRepaymentRecordDetailedList=new ArrayList<>();

    //还款总额
    private BigDecimal total;

    //还款类型 还款类型:0手动，1系统跑批
    private Integer repaymentType;

    public Integer getRepaymentType() {
        return repaymentType;
    }

    public void setRepaymentType(Integer repaymentType) {
        this.repaymentType = repaymentType;
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

    public BigDecimal getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(BigDecimal serviceCharge) {
        this.serviceCharge = serviceCharge;
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

    public List<EntRepaymentPlan> getRepaymentPlanList() {
        return repaymentPlanList;
    }

    public void setRepaymentPlanList(List<EntRepaymentPlan> repaymentPlanIdList) {
        this.repaymentPlanList = repaymentPlanIdList;
    }

    public List<String> getPlanIdList() {
        return planIdList;
    }

    public void setPlanIdList(List<String> planIdList) {
        this.planIdList = planIdList;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public List<EntRepaymentRecordDetailed> getEntRepaymentRecordDetailedList() {
        return entRepaymentRecordDetailedList;
    }

    public void setEntRepaymentRecordDetailedList(List<EntRepaymentRecordDetailed> entRepaymentRecordDetailedList) {
        this.entRepaymentRecordDetailedList = entRepaymentRecordDetailedList;
    }

    public String getRepaymentNumber() {
        return repaymentNumber;
    }

    public void setRepaymentNumber(String repaymentNumber) {
        this.repaymentNumber = repaymentNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}