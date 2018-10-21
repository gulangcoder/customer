package cn.fintecher.system.service.impl;

import cn.fintecher.system.model.EntProductLadder;
import cn.fintecher.system.model.EntRepaymentPlan;
import cn.fintecher.system.model.RepaymentModel;
import cn.fintecher.system.service.RepaymentPlanService;
import cn.fintecher.util.ArithUtil;
import cn.fintecher.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
@Service
public class RepaymentPlanServiceImpl implements RepaymentPlanService {

    /**
     * 借款生成还款计划
     * @param repaymentModel 还款实体
     * */
    public RepaymentModel genRepaymentPlan(RepaymentModel repaymentModel) throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("resultState",true);
        //利率转换 //利率类型 0日 1月 2年
        if (repaymentModel.getRateType()==0){
            repaymentModel.setYearRate(repaymentModel.getRate()*360);
            repaymentModel.setMonthRate(repaymentModel.getRate()*30);
            repaymentModel.setDayRate(repaymentModel.getRate());
        }else if (repaymentModel.getRateType()==1){
            repaymentModel.setYearRate(repaymentModel.getRate()*12);
            repaymentModel.setMonthRate(repaymentModel.getRate());
            repaymentModel.setDayRate(repaymentModel.getRate()/30);
        }if (repaymentModel.getRateType()==2){
            repaymentModel.setYearRate(repaymentModel.getRate());
            repaymentModel.setMonthRate(repaymentModel.getRate()/12);
            repaymentModel.setDayRate(repaymentModel.getRate()/360);
        }
        //生成期数 还款时间 本金
        repaymentModel.setPlanList(genPeriods(repaymentModel));
        //利息总额
        double lnterestTota=0d;
        for(EntRepaymentPlan plan : repaymentModel.getPlanList()){
            //生成利息
            plan.setInterest(genInterest(repaymentModel,plan,false));
            lnterestTota=ArithUtil.add(lnterestTota,plan.getInterest());
        }
        //利息总额
        repaymentModel.setLnterestTotal(lnterestTota);
        //本息总额
        repaymentModel.setPrincipalLnterestTotal(ArithUtil.add(lnterestTota,repaymentModel.getDebitAmount()));

        //费用计算规则有 借款本金、借款本息、借款总利息、合同总额  单独循环处理
        for(EntRepaymentPlan plan : repaymentModel.getPlanList()){
            //服务是否开启开关
            if(repaymentModel.getHaveFervice()==0){
                //生成服务费
                plan.setServiceCharge(genServiceCharge(repaymentModel,plan));
                //生成前置服务费 放在还款计划表第一期
                if (plan.getPeriodsNumber()==1) {
                    plan.setPrepositionServiceCharge(genPrepositionServiceCharge(repaymentModel));
                }
            }
            //第一期生成前置服务费
            if (plan.getPeriodsNumber()==1){
                //生成转账手续费 放在还款计划表第一期
                plan.setPoundage(genPoundage(repaymentModel,plan));
                //生成保证金 放在还款计划表第一期
                plan.setBond(genBond(repaymentModel,plan));
            }
            //计算每一期总还款金额
            plan.setTotal(calculationTotal(plan));
        }
        return repaymentModel;
    }

    @Override
    /**
     * 生成还款计划跑批数据
     * @param repaymentModel 还款实体
     * */
    public RepaymentModel genRepaymentBatchResult(List<String> orderIdList) throws Exception {
        //获取订单详情
        // TODO: 2018/9/10   获取订单详情 相关费率 获取订单对应的还款计划
        //违约金计算
        //violateTreatyCalculation();
        return null;
    }



    /**
     * 生成服务费
     * @param repaymentPlan
     * */
    private double genServiceCharge(RepaymentModel repaymentModel,EntRepaymentPlan repaymentPlan){
        double serviceCost=0d;
        //服务费规则 1借款本金、2借款本息、3借款总利息
        //生成按期服务
        if ("1".equals(repaymentModel.getServiceFeeRule())){
            serviceCost=ArithUtil.mul(repaymentModel.getDebitAmount(),repaymentModel.getServiceFeeRate())*0.01*repaymentModel.getPersiodsValue();
        } else if ("2".equals(repaymentModel.getServiceFeeRule())){
            serviceCost=ArithUtil.mul(repaymentModel.getPrincipalLnterestTotal(),repaymentModel.getServiceFeeRate())*0.01*repaymentModel.getPersiodsValue();
        } else if ("3".equals(repaymentModel.getServiceFeeRule())) {
            serviceCost = ArithUtil.mul(repaymentModel.getPrincipalLnterestTotal(), repaymentModel.getServiceFeeRate())*0.01*repaymentModel.getPersiodsValue();
        }
        return serviceCost;
    }

    /**
     * 违约金计算
     * @param repaymentModel
     * */
    private double violateTreatyCalculation(RepaymentModel repaymentModel){
        double prepayment=0.0d;
        double prepaymentPrincipal=0;
        if (repaymentModel.getHavePrepayment()==0){
            //违约金计算基础金额 1：借款未还本金 2：借款未还总额 3:借款本金 4:借款本息 5:借款总利息
            if (repaymentModel.getPrepaymentPrincipal()==1){
                for (EntRepaymentPlan plan : repaymentModel.getPlanList()){
                    if (plan.getState()==0){
                        prepaymentPrincipal+=plan.getPrincipal();
                    }
                }
            } else  if (repaymentModel.getPrepaymentPrincipal()==2){
                for (EntRepaymentPlan plan : repaymentModel.getPlanList()){
                    if (plan.getState()==0){
                        prepaymentPrincipal+=plan.getTotal();
                    }
                }
            } else  if (repaymentModel.getPrepaymentPrincipal()==3){
                for (EntRepaymentPlan plan : repaymentModel.getPlanList()){
                    prepaymentPrincipal+=plan.getPrincipal();
                }
            } else  if (repaymentModel.getPrepaymentPrincipal()==4){
                for (EntRepaymentPlan plan : repaymentModel.getPlanList()){
                    if (plan.getState()==0){
                        prepaymentPrincipal+=plan.getPrincipal()+plan.getInterest();
                    }
                }
            } else  if (repaymentModel.getPrepaymentPrincipal()==5){
                for (EntRepaymentPlan plan : repaymentModel.getPlanList()){
                    if (plan.getState()==0){
                        prepaymentPrincipal+=plan.getInterest();
                    }
                }
            }
            //提前还款违约金 0固定费率 1阶梯费率
            if(repaymentModel.getPrepaymentRateType()==0){
                prepayment=ArithUtil.mul(prepaymentPrincipal, repaymentModel.getPrepaymentRate());
            }else if(repaymentModel.getPrepaymentRateType()==1){
                //提前天数计算 当前时间-借款日（还款开始日）
                long days=DateUtil.getBetweenDays(DateUtil.getDateToString(repaymentModel.getBeginTime(),DateUtil.STYLE_2),DateUtil.getDateToString(new Date(),DateUtil.STYLE_2));
                for (EntProductLadder entProductLadder : repaymentModel.getEntProductLadderPrepayment()){
                    //判断区间范围
                    if ((entProductLadder.getMinDays()<days||entProductLadder.getMinDays()==days)&&days<entProductLadder.getMaxDays()){
                        return ArithUtil.mul(prepaymentPrincipal, entProductLadder.getAmountRate().doubleValue());
                    }
                }
            }
        }
        return prepayment;
    }

    /**
     * 罚息计算
     * @param repaymentModel
     * @param repaymentPlan 还款计划
     * */
    private double penaltyCalculation(RepaymentModel repaymentModel,EntRepaymentPlan repaymentPlan) throws Exception{
        double penaltyInterest=0.0d;
        double principal=0.0d;
        //支持每期本金罚息  罚息配置 1每期借款本金、2借款本息、3借款总利息、4合同总额
        if(repaymentModel.getPenaltyRule()==1){
            principal=repaymentPlan.getPrincipal();
        }else if (repaymentModel.getPenaltyRule()==2){
            // TODO: 2018/9/11   2借款本息
        }else if (repaymentModel.getPenaltyRule()==3){
            // TODO: 2018/9/11 3借款总利息
        }else if (repaymentModel.getPenaltyRule()==4){
            // TODO: 2018/9/11  4合同总额
        }
        if (repaymentPlan.getState()==0){
            long days=DateUtil.getBetweenDays(repaymentPlan.getExpiryTime(),DateUtil.getDateToString(new Date(),DateUtil.STYLE_2));
            if (days>0){
                //逾期费率类型 0固定费率 1阶梯费率
                if(repaymentModel.getOverdueRateType()==0){
                    penaltyInterest= ArithUtil.mul(ArithUtil.mul(principal, repaymentModel.getOverdueRate()),days);
                }else if (repaymentModel.getOverdueRateType()==1) {
                    for (EntProductLadder entProductLadder : repaymentModel.getEntProductLadderOverdue()){
                        //判断区间范围
                        if ((entProductLadder.getMinDays()<days||entProductLadder.getMinDays()==days)&&days<entProductLadder.getMaxDays()){
                            return ArithUtil.mul(ArithUtil.mul(principal, entProductLadder.getAmountRate().doubleValue()),days)*repaymentModel.getPersiodsValue();
                        }
                    }
                }
            }
        }
        return penaltyInterest;
    }

    /**
     * 生成还款期数
     * @param repaymentModel 还款实体
     * */
    private List<EntRepaymentPlan>  genPeriods(RepaymentModel repaymentModel) throws Exception{
       if( repaymentModel.getBeginTime()==null){
           repaymentModel.setBeginTime(new Date());
       }
        double principal = 0d;//月还本金
        String beginTime="";//开始时间
        String endDate=""; //还款时间
        List<EntRepaymentPlan> planList=new ArrayList<>();//还款计划表
        switch (repaymentModel.getPayType()) {
            case "1"://等额本息
                // TODO: 2018/9/7
                break;
            case "2"://等额本金
                // TODO: 2018/9/7
                break;
            case "3"://到期一次还本付息
                // TODO: 2018/9/7
                break;
            case "4" ://先息后本
                // TODO: 2018/9/7
                break;
            case "5" ://等本等息
                //每月还款额=（贷款金额+贷款金额*月利率*借款期数）/借款期数；每月本金=贷款金额/借款期数；每月利息=贷款金额*月利率；总利息=贷款金额*月利率*借款期数；
                for(int i = 1;i <= repaymentModel.getPersiods() ; i++){
                    principal=repaymentModel.getDebitAmount()/repaymentModel.getPersiods();
                    //每期期限类型 0日 1周 2月
                    if (repaymentModel.getPersiodsType()==0){
                        beginTime=DateUtil.getLastTime(DateUtil.getDateToString(repaymentModel.getBeginTime(),DateUtil.STYLE_2), 1, (i-1)*repaymentModel.getPersiodsValue(), DateUtil.STYLE_2);//开始时间
                        // 还款时间以日为期限
                        endDate=DateUtil.getLastTime(DateUtil.getDateToString(repaymentModel.getBeginTime(),DateUtil.STYLE_2), 1, i*repaymentModel.getPersiodsValue(), DateUtil.STYLE_2);//还款时间
                    }else if (repaymentModel.getPersiodsType()==1){
                        beginTime=DateUtil.getLastTime(DateUtil.getDateToString(repaymentModel.getBeginTime(),DateUtil.STYLE_2), 1, (i-1)*7*repaymentModel.getPersiodsValue(), DateUtil.STYLE_2);//开始时间
                        // 还款时间以周为期限
                        endDate=DateUtil.getLastTime(DateUtil.getDateToString(repaymentModel.getBeginTime(),DateUtil.STYLE_2), 1, i*7*repaymentModel.getPersiodsValue(), DateUtil.STYLE_2);//还款时间
                    } else if (repaymentModel.getPersiodsType()==2){
                        beginTime=DateUtil.getLastTime(DateUtil.getDateToString(repaymentModel.getBeginTime(),DateUtil.STYLE_2), 2,(i-1)*repaymentModel.getPersiodsValue(), DateUtil.STYLE_2);//开始时间
                        // 还款时间以月为期限
                        endDate=DateUtil.getLastTime(DateUtil.getDateToString(repaymentModel.getBeginTime(),DateUtil.STYLE_2), 2, i*repaymentModel.getPersiodsValue(), DateUtil.STYLE_2);//还款时间
                    }
                    EntRepaymentPlan entRepaymentPlan=new EntRepaymentPlan();
                    entRepaymentPlan.setPeriodsNumber(i);//当前期数
                    entRepaymentPlan.setPrincipal(principal);//当前本金
                    entRepaymentPlan.setStartTime(beginTime);
                    entRepaymentPlan.setExpiryTime(endDate);
                    entRepaymentPlan.setPayType(repaymentModel.getPayType());
                    planList.add(entRepaymentPlan);
                }
                break;
            default:
                break;
        }
        return planList;
    }

    /**
     * 生成利息
     * @param repaymentModel 还款实体
     * @param repaymentPlan 还款计划
     * @param factor ture根据当前日期比较没有到期的按日计算  false按期计算
     * @return double
     * */
    private double genInterest(RepaymentModel repaymentModel,EntRepaymentPlan repaymentPlan,boolean factor) throws Exception{
        double interest=0d;
        String date=DateUtil.getDateToString(repaymentModel.getBeginTime(),DateUtil.STYLE_2); //还款时间
        if (factor){
            //当前日期与还款日期相差几天
            int days=DateUtil.diffDays(date, repaymentPlan.getExpiryTime());
        }else {
            switch (repaymentModel.getPayType()) {
                case "1"://等额本息
                    // TODO: 2018/9/7
                    break;
                case "2"://等额本金
                    // TODO: 2018/9/7
                    break;
                case "3"://到期一次还本付息
                    // TODO: 2018/9/7
                    break;
                case "4" ://先息后本
                    // TODO: 2018/9/7
                    break;
                case "5" ://等本等息
                    //每月利息=贷款金额*月利率
                    interest=ArithUtil.mul(repaymentModel.getDebitAmount(), repaymentModel.getMonthRate())*0.01*repaymentModel.getPersiodsValue();
                    break;
                default:
                    break;
            }
        }
        return interest;
    }
    /**
     * 生成转账手续费
     * @param repaymentModel 还款实体
     * @param repaymentPlan 还款计划
     * @return repaymentPlan
     * */
    private  double   genPoundage(RepaymentModel repaymentModel,EntRepaymentPlan repaymentPlan){
        double poundage=0d;
        if (repaymentModel.getHavePoundage()==0){
            poundage=repaymentModel.getPoundage();
        }
        return poundage;
    }

    /**
     * 生成保证金
     * @param repaymentModel 还款实体
     * @param repaymentPlan 还款计划
     * @return double
     * */
    private double genBond(RepaymentModel repaymentModel,EntRepaymentPlan repaymentPlan){
        double bond=0d;
        if (repaymentModel.getHaveCash()==0){
            //0固定金额 1比例
            if(repaymentModel.getCashDepositType()==0){
                bond=repaymentModel.getCashDeposit();
            }else {
                ////保证金规则 1：借款本金、2：借款本息:3：借款总利息:
                if ("1".equals(repaymentModel.getCashDepositRule())){
                    bond=repaymentModel.getDebitAmount()*repaymentModel.getCashDeposit()*0.01;
                }else if ("2".equals(repaymentModel.getCashDepositRule())){
                    bond=repaymentModel.getPrincipalLnterestTotal()*repaymentModel.getCashDeposit()*0.01;
                }else if ("3".equals(repaymentModel.getCashDepositRule())){
                    bond=repaymentModel.getLnterestTotal()*repaymentModel.getCashDeposit()*0.01;
                }
            }
        }
        return bond;
    }

    /**
     * 生成前置服务费
     * @param repaymentModel 还款实体
     * @return double
     * */
    private double genPrepositionServiceCharge(RepaymentModel repaymentModel){
        double prepositionServiceCharge=0d;
        //服务费规则 1借款本金、2借款本息、3借款总利息
        //生成前置服务费
        if ("1".equals(repaymentModel.getServiceFeeRule())){
            prepositionServiceCharge=ArithUtil.mul(repaymentModel.getDebitAmount(),repaymentModel.getEarlyServiceFeeRate())*0.01;
        } else if ("2".equals(repaymentModel.getServiceFeeRule())){
            prepositionServiceCharge=ArithUtil.mul(repaymentModel.getPrincipalLnterestTotal(),repaymentModel.getEarlyServiceFeeRate())*0.01;
        } else if ("3".equals(repaymentModel.getServiceFeeRule())) {
            prepositionServiceCharge = ArithUtil.mul(repaymentModel.getPrincipalLnterestTotal(), repaymentModel.getEarlyServiceFeeRate())*0.01;
        }
        return prepositionServiceCharge;
    }

    /**
     * 计算每期还款总额
     * @param repaymentPlan 还款计划
     * @return double
     * */
    private double calculationTotal(EntRepaymentPlan repaymentPlan){
        double total=0;
        total=repaymentPlan.getPrincipal()+repaymentPlan.getInterest()+repaymentPlan.getServiceCharge();
        return total;
    }

}
