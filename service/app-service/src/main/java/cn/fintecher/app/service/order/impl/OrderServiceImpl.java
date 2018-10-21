package cn.fintecher.app.service.order.impl;

import cn.fintecher.app.mapper.order.EntOrderMapper;
import cn.fintecher.app.mapper.order.EntRepaymentPlanMapper;
import cn.fintecher.app.model.order.EntOrder;
import cn.fintecher.app.model.order.EntRepaymentPlan;
import cn.fintecher.app.service.order.OrderService;
import cn.fintecher.app.service.order.RepaymentService;
import cn.fintecher.util.ChkUtil;
import cn.fintecher.util.DateUtil;
import cn.fintecher.util.SensitiveInfoUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private EntOrderMapper entOrderMapper;
    @Autowired
    private RepaymentService repaymentService;
    @Autowired
    private EntRepaymentPlanMapper entRepaymentPlanMapper;


    /***
     * 客户订单查询
     * @param order
     * @return list
     */
    @Override
    public List<EntOrder> getOrderList(EntOrder order) throws Exception {
        return entOrderMapper.getOrderList(order);
    }

    @Override
    public EntOrder getOrder(EntOrder order) throws Exception {
        //获取订单详情
        order=entOrderMapper.selectByPrimaryKey(order.getId());
        EntRepaymentPlan repaymentRecord =new EntRepaymentPlan();
        repaymentRecord.setId(order.getId());
        //获取所有还款计划集合
        List<EntRepaymentPlan> repaymentPlanIdList=entRepaymentPlanMapper.getEntRepaymentPlanList(repaymentRecord);
        //已还本金
        BigDecimal alreadyPrincipal=new BigDecimal(0);
        //已还利息
        BigDecimal alreadyInterest=new BigDecimal(0);
        for (EntRepaymentPlan plan:repaymentPlanIdList){
            if (plan.getPeriodsNumber()==1){
                order.setPrepositionServiceCharge(plan.getPrepositionServiceCharge());
                order.setServiceCharge(plan.getServiceCharge());
                order.setBond(plan.getBond());
                order.setPoundage(plan.getPoundage());
            }
            if (plan.getState()==1){
                alreadyPrincipal.add(plan.getPrincipal());
                alreadyInterest.add(plan.getInterest());
            }
        }
        order.setAlreadyPrincipal(alreadyPrincipal);
        order.setAlreadyInterest(alreadyInterest);
        return order;
    }

    @Override
    public boolean addOrder(EntOrder order) throws Exception {
        return false;
    }

    @Override
    public boolean updateOrder(EntOrder order) throws Exception {
        return false;
    }

    @Override
    public boolean delOrder(EntOrder order) throws Exception {
        return false;
    }

    /**
     * 根据客户id查询借款总金额,借款总笔数
     * @param customerId 客户id
     * @return
     */
    @Override
    public Map getOrderSumByCustId(String customerId){
        return entOrderMapper.getOrderSumByCustId(customerId);
    }

    @Override
    public List<Map> getOrdersInfo(Map paramMap) {
        List<Map> mapList = entOrderMapper.selectListByParam(paramMap);
        for (Map map : mapList) {
            //数据脱敏
            map.put("real_name", SensitiveInfoUtils.chineseName((String)map.get("real_name")));
            map.put("idcard_num",SensitiveInfoUtils.idCardNum((String)map.get("idcard_num")));
            //合同起止时间
            String start = "";
            if (ChkUtil.isNotEmpty(map.get("create_time"))){
                start = map.get("create_time").toString().substring(0,map.get("create_time").toString().length() - 2);
                map.put("startDate",start);
            }
            String end = "";
            if (ChkUtil.isNotEmpty(map.get("expiry_time"))){
                end = map.get("expiry_time").toString().substring(0,map.get("expiry_time").toString().length() - 2);
            }
            if (ChkUtil.isNotEmpty(start) && ChkUtil.isNotEmpty(end)){
                map.put("startToEndDate",start + "~" + end);
            }


            //还款信息
            String orderId = String.valueOf(map.get("id"));
            //已还本金 已还利息 未还本金 未还利息
            Map repaidSum = repaymentService.getRepaidSum(orderId);

            if (ChkUtil.isNotEmpty(repaidSum.get("returnPrincipal"))){
                BigDecimal returnPrincipal = new BigDecimal(repaidSum.get("returnPrincipal").toString());
                map.put("returnPrincipal",returnPrincipal);//已还本金

                if (ChkUtil.isNotEmpty(repaidSum.get("returnInterest"))){
                    BigDecimal returnInterest = new BigDecimal(repaidSum.get("returnInterest").toString());
                    map.put("returnInterest",returnInterest);//已还利息

                    Map planSum = repaymentService.getPlanSum(orderId);
                    if (ChkUtil.isNotEmpty(planSum.get("planPrincipal"))){
                        BigDecimal planPrincipal = new BigDecimal(planSum.get("planPrincipal").toString());//应还本金
                        map.put("unpaidPrincipal",planPrincipal.subtract(returnPrincipal));//未还本金
                    }
                    if (ChkUtil.isNotEmpty(planSum.get("planInterest"))){
                        BigDecimal planInterest = new BigDecimal(planSum.get("planInterest").toString());//应还利息
                        map.put("unpaidInterest",planInterest.subtract(returnInterest));//未还利息
                    }
                }
            }
            //当前逾期期数
            List<Map> map1List = repaymentService.getRepaymentInfo(orderId);
            if (ChkUtil.isNotEmpty(map1List)){
                BigDecimal shouldTotal = BigDecimal.ZERO;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < map1List.size(); i++) {
                    if (ChkUtil.isNotEmpty((BigDecimal) map1List.get(i).get("principal"))){
                        shouldTotal  = shouldTotal.add((BigDecimal) map1List.get(i).get("principal"));
                    }
                    if (ChkUtil.isNotEmpty((BigDecimal) map1List.get(i).get("interest"))){
                        shouldTotal  = shouldTotal.add((BigDecimal) map1List.get(i).get("interest"));
                    }
                    if (ChkUtil.isNotEmpty((BigDecimal) map1List.get(i).get("service_charge"))){
                        shouldTotal  = shouldTotal.add((BigDecimal) map1List.get(i).get("service_charge"));
                    }
                    if (ChkUtil.isNotEmpty((BigDecimal) map1List.get(i).get("default_interest"))){
                        shouldTotal  = shouldTotal.add((BigDecimal) map1List.get(i).get("default_interest"));
                    }
                    if (ChkUtil.isNotEmpty((BigDecimal) map1List.get(i).get("penalty"))){
                        shouldTotal  = shouldTotal.add((BigDecimal) map1List.get(i).get("penalty"));
                    }
                    if (i == map1List.size() - 1) {
                        sb.append(String.valueOf(map1List.get(i).get("defaultPeriods")));
                    } else {
                        sb.append(String.valueOf(map1List.get(i).get("defaultPeriods")));
                        sb.append(",");
                    }
                }
                map.put("defaultPeriods",sb);//当前逾期期数
                map.put("shouldTotal",shouldTotal);//当前应还总额
            }else {
                map.put("defaultPeriods","");//当前逾期期数
                map.put("shouldTotal",0);//当前应还总额
            }
        }
        return mapList;
    }

    /**
     * 获取订单还款计划
     * @param order 订单
     * @return EntOrder
     */
    @Override
    public Map<String,Object> getRepaymentPlan(EntOrder order)throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        //获取订单详情
        order=entOrderMapper.selectByPrimaryKey(order.getId());
        EntRepaymentPlan repaymentRecord =new EntRepaymentPlan();
        repaymentRecord.setOrderId(order.getId());
        //获取所有还款计划集合
        List<EntRepaymentPlan> repaymentPlanIdList=entRepaymentPlanMapper.getEntRepaymentPlanList(repaymentRecord);
        //已还本金
        BigDecimal alreadyPrincipal=new BigDecimal(0);
        //已还利息
        BigDecimal alreadyInterest=new BigDecimal(0);
        //已还服务费
        BigDecimal alreadyServiceCharge=new BigDecimal(0);

        //本金
        BigDecimal principal=new BigDecimal(0);
        //利息
        BigDecimal interest=new BigDecimal(0);
        //服务费
        BigDecimal serviceCharge=new BigDecimal(0);

        for (EntRepaymentPlan plan:repaymentPlanIdList){
            if (plan.getPeriodsNumber()==1){
                order.setPrepositionServiceCharge(plan.getPrepositionServiceCharge());
                order.setServiceCharge(plan.getServiceCharge());
                order.setBond(plan.getBond());
                order.setPoundage(plan.getPoundage());
            }
            principal=principal.add(plan.getPrincipal());
            interest=interest.add(plan.getInterest());
            serviceCharge=serviceCharge.add(plan.getServiceCharge());
            if (plan.getState()==1){
                alreadyPrincipal.add(plan.getPrincipal());
                alreadyInterest.add(plan.getInterest());
                alreadyServiceCharge.add(plan.getServiceCharge());
            }
            if (plan.getState()==0){
                long days= DateUtil.getBetweenDays(plan.getExpiryTime(),DateUtil.getDateToString(new Date(),DateUtil.STYLE_2));
                if (days<0){
                    plan.setIsOverdue(1);
                }
                if(days>0&&days<30){
                    plan.setThisPeriod(1);
                }
            }
        }
        resultMap.put("repaymentPlanIdList",repaymentPlanIdList);
        resultMap.put("total",principal.add(interest).add(serviceCharge));
        resultMap.put("pending",principal.add(interest).add(serviceCharge).subtract(alreadyPrincipal).subtract(alreadyInterest).subtract(alreadyServiceCharge));
        return resultMap;
    }

    @Override
    public List<EntOrder> getOrderListByMap(Map map) {
        return entOrderMapper.getOrderListByMap(map);
    }


    /***
     * 客户还款中的所有订单
     * @param custId 客户id
     * @return list
     */
    @Override
    public Map<String,Object> getOrdersRepaymentPlan(String custId)throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        EntOrder order=new EntOrder();
        order.setCustId(custId);
        order.setState(4);
        //获取订单详情
        List<EntOrder> orderList=entOrderMapper.getOrderList(order);
        //待还本金总额
        BigDecimal pendingPrincipalTotal=new BigDecimal(0);
        //待还利息总额
        BigDecimal pendingInterestTotal=new BigDecimal(0);
        //待还服务费总额
        BigDecimal pendingServiceChargeTotal=new BigDecimal(0);
        if (orderList.size()>0){
            for (EntOrder entOrder:orderList){
                EntRepaymentPlan repaymentRecord =new EntRepaymentPlan();
                repaymentRecord.setOrderId(entOrder.getId());
                //获取所有还款计划集合
                List<EntRepaymentPlan> repaymentPlanIdList=entRepaymentPlanMapper.getEntRepaymentPlanList(repaymentRecord);
                //待还本金
                BigDecimal pendingPrincipal=new BigDecimal(0);
                //待还利息
                BigDecimal pendingInterest=new BigDecimal(0);
                //待还服务费
                BigDecimal pendingServiceCharge=new BigDecimal(0);
                for (EntRepaymentPlan plan:repaymentPlanIdList){
                    if (plan.getPeriodsNumber()==1){
                        entOrder.setPrepositionServiceCharge(plan.getPrepositionServiceCharge());
                        entOrder.setServiceCharge(plan.getServiceCharge());
                        entOrder.setBond(plan.getBond());
                        entOrder.setPoundage(plan.getPoundage());
                    }
                    if (plan.getState()==0){
                        long days= DateUtil.getBetweenDays(plan.getExpiryTime(),DateUtil.getDateToString(new Date(),DateUtil.STYLE_2));
                        if (days<0){
                            plan.setIsOverdue(1);
                            pendingPrincipal=pendingPrincipal.add(plan.getPrincipal());
                            pendingInterest=pendingInterest.add(plan.getInterest());
                            pendingServiceCharge=pendingServiceCharge.add(plan.getServiceCharge());
                            pendingPrincipalTotal=pendingPrincipalTotal.add(plan.getPrincipal());
                            pendingInterestTotal=pendingInterestTotal.add(plan.getInterest());
                            pendingServiceChargeTotal=pendingServiceChargeTotal.add(plan.getServiceCharge());
                        }
                        if(days>0&&days<31){
                            plan.setThisPeriod(1);
                            pendingPrincipal=pendingPrincipal.add(plan.getPrincipal());
                            pendingInterest=pendingInterest.add(plan.getInterest());
                            pendingServiceCharge=pendingServiceCharge.add(plan.getServiceCharge());
                            pendingPrincipalTotal=pendingPrincipalTotal.add(plan.getPrincipal());
                            pendingInterestTotal=pendingInterestTotal.add(plan.getInterest());
                            pendingServiceChargeTotal=pendingServiceChargeTotal.add(plan.getServiceCharge());
                        }
                    }
                    plan.setTotal(plan.getPrincipal().add(plan.getInterest()).add(plan.getServiceCharge()));
                }
                entOrder.setPendingTotal(pendingPrincipal.add(pendingInterest).add(pendingServiceCharge));
                entOrder.setPlanList(repaymentPlanIdList);
            }

        }
        resultMap.put("orderList",orderList);
        resultMap.put("total",pendingPrincipalTotal.add(pendingInterestTotal).add(pendingServiceChargeTotal));
        return resultMap;
    }


    /***
     * 验证订单结清
     * @param orderId
     * @return
     */
    @Override
    public boolean settleVerification(String orderId){
        EntRepaymentPlan repaymentRecord =new EntRepaymentPlan();
        repaymentRecord.setOrderId(orderId);
        //获取所有还款计划集合
        List<EntRepaymentPlan> repaymentPlanIdList=entRepaymentPlanMapper.getEntRepaymentPlanList(repaymentRecord);
        for (EntRepaymentPlan plan:repaymentPlanIdList){
            if (plan.getState()!=2){
                return false;
            }
        }
        return true;
    }
}
