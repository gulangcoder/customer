package cn.fintecher.system.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import java.util.Date;
import java.util.List;

/**
 * Title :
 * Description : @还款实体@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:gaozhidong
 * @version 1.0
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@ApiModel(value = "还款列表传入参数")
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepaymentModel {
    //还款计划
    private List<EntRepaymentPlan> planList;
    //借款金额
    private Double debitAmount;

    //利息总金额
    private Double lnterestTotal;

    //本息总金额
    private Double principalLnterestTotal;

    //期数
    private  Integer persiods;

    //期数值 每一期的期限：如一期是2个月
    private  Integer persiodsValue;

    //开始时间
    private Date beginTime;

    //利率
    private Double rate;

    //利率类型 0日 1月 2年
    private Double rateType;

    //月利率
    private Double monthRate;

    //日利率
    private Double dayRate;

    //月利率
    private Double yearRate;

    //期数类型  0日 1周 2月
    private Integer persiodsType;

    //服务费开关 0开 1关
    private Integer haveFervice;

    //服务费费率
    private Double serviceFeeRate;

    //前置服务费费率
    private Double earlyServiceFeeRate;

    //服务费规则 1借款本金、2借款本息、3借款总利息
    private String serviceFeeRule;

    //还款方式{1:月付息，到期还本 2:到期一次还本付息 3:等额本息 4 分期等额 5 等额本金 6等额本息周}
    private String payType;

    //提前还款违约金开关  0是 1否
    private Integer havePrepayment;

    //违约金计算基础金额 1：借款未还本金 2：借款未还总额 3:借款本金 4:借款本息 5:借款总利息 6:合同总额
    private  Integer prepaymentPrincipal;

    //提前还款违约金 0固定费率 1阶梯费率
    private Integer prepaymentRateType;

    //提前还款固定费率
    private Double prepaymentRate;

    //提前还款违约金区间利率
    private List<EntProductLadder> entProductLadderPrepayment;

    //罚息规则 罚息配置 1每期借款本金、2借款本息、3借款总利息、4合同总额
    private Integer penaltyRule;

    //逾期保护天数
    private Integer overdueProtectionDays;

    //逾期费率类型 0固定费率 1阶梯费率
    private Integer overdueRateType;

    //逾期费率类型固定费率
    private Double overdueRate;

    //逾期费率区间利率
    private List<EntProductLadder> entProductLadderOverdue;

    //有无保证金 0有 1无
    private Integer  haveCash;

    //保证金类型 0固定金额 1比例
    private Integer cashDepositType;

    //保证金或保证金费率
    private Double cashDeposit;

    //保证金规则 1：借款本金、2：借款本息:3：借款总利息:4：合同总额
    private String cashDepositRule;

    //转账手续费开关 有无手续费 0有 1无
    private Integer havePoundage;

    // 手续费poundage
    private Double  poundage;


    public List<EntRepaymentPlan> getPlanList() {
        return planList;
    }

    public void setPlanList(List<EntRepaymentPlan> planList) {
        this.planList = planList;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }


    public Integer getPersiods() {
        return persiods;
    }

    public void setPersiods(Integer persiods) {
        this.persiods = persiods;
    }

    public Integer getPersiodsValue() {
        return persiodsValue;
    }

    public void setPersiodsValue(Integer persiodsValue) {
        this.persiodsValue = persiodsValue;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getPersiodsType() {
        return persiodsType;
    }

    public void setPersiodsType(Integer persiodsType) {
        this.persiodsType = persiodsType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Double getRateType() {
        return rateType;
    }

    public void setRateType(Double rateType) {
        this.rateType = rateType;
    }

    public Double getMonthRate() {
        return monthRate;
    }

    public void setMonthRate(Double monthRate) {
        this.monthRate = monthRate;
    }

    public Double getDayRate() {
        return dayRate;
    }

    public void setDayRate(Double dayRate) {
        this.dayRate = dayRate;
    }

    public Double getYearRate() {
        return yearRate;
    }

    public void setYearRate(Double yearRate) {
        this.yearRate = yearRate;
    }

    public Double getLnterestTotal() {
        return lnterestTotal;
    }

    public void setLnterestTotal(Double lnterestTotal) {
        this.lnterestTotal = lnterestTotal;
    }

    public Double getPrincipalLnterestTotal() {
        return principalLnterestTotal;
    }

    public void setPrincipalLnterestTotal(Double principalLnterestTotal) {
        this.principalLnterestTotal = principalLnterestTotal;
    }

    public Integer getHaveFervice() {
        return haveFervice;
    }

    public void setHaveFervice(Integer haveFervice) {
        this.haveFervice = haveFervice;
    }

    public Double getServiceFeeRate() {
        return serviceFeeRate;
    }

    public void setServiceFeeRate(Double serviceFeeRate) {
        this.serviceFeeRate = serviceFeeRate;
    }

    public String getServiceFeeRule() {
        return serviceFeeRule;
    }

    public void setServiceFeeRule(String serviceFeeRule) {
        this.serviceFeeRule = serviceFeeRule;
    }

    public Integer getHavePrepayment() {
        return havePrepayment;
    }

    public void setHavePrepayment(Integer havePrepayment) {
        this.havePrepayment = havePrepayment;
    }

    public Integer getPrepaymentRateType() {
        return prepaymentRateType;
    }

    public void setPrepaymentRateType(Integer prepaymentRateType) {
        this.prepaymentRateType = prepaymentRateType;
    }

    public Double getPrepaymentRate() {
        return prepaymentRate;
    }

    public void setPrepaymentRate(Double prepaymentRate) {
        this.prepaymentRate = prepaymentRate;
    }

    public Integer getOverdueProtectionDays() {
        return overdueProtectionDays;
    }

    public void setOverdueProtectionDays(Integer overdueProtectionDays) {
        this.overdueProtectionDays = overdueProtectionDays;
    }

    public Integer getOverdueRateType() {
        return overdueRateType;
    }

    public void setOverdueRateType(Integer overdueRateType) {
        this.overdueRateType = overdueRateType;
    }

    public Double getOverdueRate() {
        return overdueRate;
    }

    public void setOverdueRate(Double overdueRate) {
        this.overdueRate = overdueRate;
    }

    public Integer getPrepaymentPrincipal() {
        return prepaymentPrincipal;
    }

    public void setPrepaymentPrincipal(Integer prepaymentPrincipal) {
        this.prepaymentPrincipal = prepaymentPrincipal;
    }

    public List<EntProductLadder> getEntProductLadderPrepayment() {
        return entProductLadderPrepayment;
    }

    public void setEntProductLadderPrepayment(List<EntProductLadder> entProductLadderPrepayment) {
        this.entProductLadderPrepayment = entProductLadderPrepayment;
    }

    public List<EntProductLadder> getEntProductLadderOverdue() {
        return entProductLadderOverdue;
    }

    public void setEntProductLadderOverdue(List<EntProductLadder> entProductLadderOverdue) {
        this.entProductLadderOverdue = entProductLadderOverdue;
    }

    public Integer getPenaltyRule() {
        return penaltyRule;
    }

    public void setPenaltyRule(Integer penaltyRule) {
        this.penaltyRule = penaltyRule;
    }

    public Double getEarlyServiceFeeRate() {
        return earlyServiceFeeRate;
    }

    public void setEarlyServiceFeeRate(Double earlyServiceFeeRate) {
        this.earlyServiceFeeRate = earlyServiceFeeRate;
    }

    public Integer getHaveCash() {
        return haveCash;
    }

    public void setHaveCash(Integer haveCash) {
        this.haveCash = haveCash;
    }

    public Integer getCashDepositType() {
        return cashDepositType;
    }

    public void setCashDepositType(Integer cashDepositType) {
        this.cashDepositType = cashDepositType;
    }

    public Double getCashDeposit() {
        return cashDeposit;
    }

    public void setCashDeposit(Double cashDeposit) {
        this.cashDeposit = cashDeposit;
    }

    public Integer getHavePoundage() {
        return havePoundage;
    }

    public void setHavePoundage(Integer havePoundage) {
        this.havePoundage = havePoundage;
    }

    public Double getPoundage() {
        return poundage;
    }

    public void setPoundage(Double poundage) {
        this.poundage = poundage;
    }

    public String getCashDepositRule() {
        return cashDepositRule;
    }

    public void setCashDepositRule(String cashDepositRule) {
        this.cashDepositRule = cashDepositRule;
    }
}
