package cn.fintecher.app.service.order.impl;

import cn.fintecher.app.mapper.order.EntOrderMapper;
import cn.fintecher.app.mapper.order.EntRepaymentPlanMapper;
import cn.fintecher.app.mapper.order.EntRepaymentRecordDetailedMapper;
import cn.fintecher.app.mapper.order.EntRepaymentRecordMapper;
import cn.fintecher.app.model.order.EntOrder;
import cn.fintecher.app.model.order.EntRepaymentPlan;
import cn.fintecher.app.model.order.EntRepaymentRecord;
import cn.fintecher.app.model.order.EntRepaymentRecordDetailed;
import cn.fintecher.app.service.order.OrderService;
import cn.fintecher.app.service.order.RepaymentService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.sms.EntMessageSendDetail;
import cn.fintecher.common.sms.EntMessageSendType;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Title :
 * Description : @还款处理接口实现@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
@Service
public class RepaymentServiceImpl implements RepaymentService {
    @Autowired
    private EntRepaymentPlanMapper entRepaymentPlanMapper;

    @Autowired
    private EntRepaymentRecordMapper entRepaymentRecordMapper;

    @Autowired
    private EntRepaymentRecordDetailedMapper entRepaymentRecordDetailedMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EntOrderMapper entOrderMapper;



    @Autowired
    private RestTemplate restTemplate;
    /**
     * 还款操作
     * 1.效验还款记录中的还款计划信息
     * 2.计算还款金额
     * 3.更新还款计划信息
     * 4.添加还款记录及明细信息
     * 5.调取第三方代付接口
     * @param repaymentRecord 还款记录
     * @return Map
     */
    @Override
    public Map<String,Object> repayment(EntRepaymentRecord repaymentRecord)throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("resultState",true);
        resultMap.put("msg", "message.system.successMessage");
        EntRepaymentPlan entRepaymentPlan=new EntRepaymentPlan();
        entRepaymentPlan.setOrderId(repaymentRecord.getOrderId());
        //获取所有还款计划集合
        List<EntRepaymentPlan> repaymentPlanIdList=entRepaymentPlanMapper.getEntRepaymentPlanList(entRepaymentPlan);
        repaymentRecord.setRepaymentPlanList(repaymentPlanIdList);
        //验证还款计划表（上期状态、当前状态、连续性）
        Map<String,Object> verificationMap=verification(repaymentRecord);
        if (!(boolean)verificationMap.get("resultState")){
            resultMap.put("resultState",false);
            resultMap.put("msg", verificationMap.get("msg"));
            return resultMap;
        }
        //还款金额计算
        repaymentRecord.setRepaymentPlanList((List<EntRepaymentPlan>)verificationMap.get("planList"));
        //总额
        Map<String,Object> totalMap=repaymentTotal(repaymentRecord);
        repaymentRecord.setTotal(new BigDecimal(totalMap.get("total").toString()));
        //更新还款计划信息 状态还款中
        for (EntRepaymentPlan plan:repaymentRecord.getRepaymentPlanList()) {
            plan.setState(1);
        }
        entRepaymentPlanMapper.updateBatch(repaymentRecord.getRepaymentPlanList());

        //添加还款流水表（状态还款中）
        repaymentRecord.setId(UUID.randomUUID().toString());
        repaymentRecord.setCreateTime(new Date());
        repaymentRecord.setState(0);
        repaymentRecord.setPrincipal(new BigDecimal(totalMap.get("principal").toString()));
        repaymentRecord.setServiceCharge(new BigDecimal(totalMap.get("serviceCharge").toString()));
        repaymentRecord.setInterest(new BigDecimal(totalMap.get("interest").toString()));
        entRepaymentRecordMapper.insertSelective(repaymentRecord);

        //添加还款流水明细表
        List<EntRepaymentRecordDetailed> detailedList=new ArrayList<>();
        for (EntRepaymentPlan plan:repaymentRecord.getRepaymentPlanList()) {
            EntRepaymentRecordDetailed  detailed=new EntRepaymentRecordDetailed();
            detailed.setId(UUID.randomUUID().toString());
            detailed.setRepaymentPlanId(plan.getId());
            detailed.setRepaymentRecordId(repaymentRecord.getId());
            detailed.setCreateTime(new Date());
            detailedList.add(detailed);
        }
        entRepaymentRecordDetailedMapper.insertBatch(detailedList);

        EntMessageSendDetail entMessageSendDetail = new EntMessageSendDetail();
        //调取第三方代付接口  默认设置还款成功
        Map<String,Object>withholdMap=repaymentWithhold(repaymentRecord);
        if ((boolean)withholdMap.get("resultState")){
            // TODO: 2018/9/20  同步id是直接取值的  异步要关联查询获取相关数据id
            resultMap.put("msg", "message.system.successMessage");
            //更新还款计划信息 状态还款中
            for (EntRepaymentPlan plan:repaymentRecord.getRepaymentPlanList()) {
                plan.setState(2);
            }
            entRepaymentPlanMapper.updateBatch(repaymentRecord.getRepaymentPlanList());
            EntRepaymentRecord repaymentupdate=new EntRepaymentRecord();
            repaymentupdate.setId(withholdMap.get("id").toString());
            repaymentupdate.setState(1);
            entRepaymentRecordMapper.updateByPrimaryKeySelective(repaymentupdate);
            //订单结清验证
            if (orderService.settleVerification(repaymentRecord.getOrderId())){
                EntOrder record =new EntOrder();
                record.setState(5);
                record.setId(repaymentRecord.getOrderId());
                entOrderMapper.updateByPrimaryKeySelective(record);
            }
            // TODO: 2018/9/19 发送信息放款成功消息
            entMessageSendDetail.setMsgType(EntMessageSendType.ENT_MSG_WITHDRAW_SUCC);
        }else {
            resultMap.put("resultState",false);
            resultMap.put("msg", withholdMap.get("msg"));
            //更新还款计划信息 状态还款中
            for (EntRepaymentPlan plan:repaymentRecord.getRepaymentPlanList()) {
                plan.setState(2);
            }
            entRepaymentPlanMapper.updateBatch(repaymentRecord.getRepaymentPlanList());
            EntRepaymentRecord repaymentupdate=new EntRepaymentRecord();
            repaymentupdate.setId(withholdMap.get("id").toString());
            repaymentupdate.setState(1);
            entRepaymentRecordMapper.updateByPrimaryKeySelective(repaymentupdate);

            // TODO: 2018/9/19 发送信息放款成功消息
            entMessageSendDetail.setMsgType(EntMessageSendType.ENT_MSG_WITHDRAW_FAIL);
        }
        entMessageSendDetail.setCompanyCode("");
        entMessageSendDetail.setCustomerTel("");
        entMessageSendDetail.setCustomerName("测试SMS");
        //restTemplate.postForEntity(AppFeginUtil.SYSTEMWEB_API_SAVEMESTEMPLDETAIL_API,entMessageSendDetail,Object.class);
        return  resultMap;
    }

    /**
     * 调用第三方代扣接口
     * @param repaymentRecord
     * @return Map
     */
    @Override
    public Map<String,Object> repaymentWithhold (EntRepaymentRecord repaymentRecord)throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("resultState",true);
        resultMap.put("id",repaymentRecord.getId());
        //resultMap.put("msg", LocaleMessage.get("message.system.successMessage"));
        // TODO: 2018/9/15  获取三方状态code msg
        /*
        *    resultMap.put("resultState",false);
        *    resultMap.put("resultCode","200");
        *    resultMap.put("msg", "三方msg");
        *
        * */
        return  resultMap;
    }

    /**
     * 第三方代扣接口回调接口
     * 1.更新还款计划
     * 2.更新还款流水
     * 3.判断是订单是否结清（更新订单信息、释放额度）
     * @param request
     * @return repaymentRecord
     */
    @Override
    public Map<String,Object> repaymentWithholdCallback (HttpServletRequest request) throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("resultState",true);
        resultMap.put("msg", LocaleMessage.get("message.system.successMessage"));
        // TODO: 2018/9/15  获取三方状态code msg
        /*
        *    resultMap.put("resultState",false);
        *    resultMap.put("resultCode","200");
        *    resultMap.put("msg", "三方msg");
        *
        * */

        // TODO: 2018/9/15 更新还款计划 已还或未还

        // TODO: 2018/9/15 更新还款流水 成功或失败

        // TODO: 2018/9/15 判断是订单是否结清 结清更新订单表 释放客户额度

        return  resultMap;
    }

    /**
     * 还款计划批处理
     * 1.获取未结清订单（获取还款计划表）
     * 2.计算还款计划罚息、违约金金额（添加还款计划批处理表）
     * @return repaymentRecord
     */
    @Override
    public Map<String,Object> repaymentBatch ()throws Exception{
        // TODO: 2018/9/15
        return  null;
    }

    /**
     * 根据订单id查询已还本金,利息
     * @param orderId
     * @return
     */
    @Override
    public Map getRepaidSum(String orderId) {
        return entRepaymentPlanMapper.getRepaidSum(orderId);
    }
    /**
     * 根据订单id查询应还本金,利息
     * @param orderId
     * @return
     */
    @Override
    public Map getPlanSum(String orderId) {
        return entRepaymentPlanMapper.getPlanSum(orderId);
    }

    @Override
    public List<Map> getRepaymentInfo(String orderId) {
        return entRepaymentPlanMapper.getRepaymentInfo(orderId);
    }

    /**
     * 验证还款计划表（上期状态、当前状态、连续性）
     * @param repaymentRecord
     * @return Map
     * */
    private Map<String,Object> verification(EntRepaymentRecord repaymentRecord){
        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("resultState",true);
        List<EntRepaymentPlan> planList=repaymentRecord.getRepaymentPlanList();
        List<String> planIdList=repaymentRecord.getPlanIdList();
        List<EntRepaymentPlan> vPlanList=new ArrayList<>();
        //循环遍历出要还款的计划表 先循环所有计划表 保证要还款的计划表按期排列
        for (int i=0;i<planList.size();i++){
            EntRepaymentPlan plan=planList.get(i);
            for (int j=0;j<planIdList.size();j++){
                if (plan.getId().equals(planIdList.get(j).trim())){
                    vPlanList.add(plan);
                }
            }
        }
        //验证上一期状态
        int periodsNumber=vPlanList.get(0).getPeriodsNumber();
        //还款计划表从1开始
        if (periodsNumber!=1){
            periodsNumber=periodsNumber-2;
            EntRepaymentPlan plan=planList.get(periodsNumber);
            //获取上一期还款计划
            if (plan.getState()!=2){
                resultMap.put("resultState",false);
                resultMap.put("msg","message.repayment.verification");
                return resultMap;
            }
        }
        //验证待还款的还款计划是否连续 当前状态
        periodsNumber=vPlanList.get(0).getPeriodsNumber();
        for(int i=0;i<vPlanList.size();i++){
            if (periodsNumber!=vPlanList.get(i).getPeriodsNumber()){
                resultMap.put("resultState",false);
                resultMap.put("msg","message.repayment.verification.continuity");
            }
            if (0!=vPlanList.get(i).getState()){
                resultMap.put("resultState",false);
                resultMap.put("msg","message.repayment.verification.state");
            }
            periodsNumber++;
        }
        resultMap.put("planList",vPlanList);
        return  resultMap;
    }
    /**
     * 计算还款金额
     * @param repaymentRecord
     * @return double
     * */
    private Map<String,Object> repaymentTotal(EntRepaymentRecord repaymentRecord){
        Map<String,Object> resultMap=new HashedMap();
        BigDecimal principal=new BigDecimal(0);
        BigDecimal interest=new BigDecimal(0);
        BigDecimal serviceCharge=new BigDecimal(0);
        for (EntRepaymentPlan plan:repaymentRecord.getRepaymentPlanList()) {
            principal=principal.add(plan.getPrincipal());
            interest=interest.add(plan.getInterest());
            serviceCharge=serviceCharge.add(plan.getServiceCharge());
        }
        resultMap.put("total",principal.add(interest).add(serviceCharge));
        resultMap.put("principal",principal);
        resultMap.put("interest",interest);
        resultMap.put("serviceCharge",serviceCharge);
        return  resultMap;
    }


    /**
     * 获取客户还款记录
     * @param custId
     * @return
     */
    public List<EntRepaymentRecord> getRepaymentRecord(String custId) throws Exception{
        Map<String,Object>paramMap=new HashedMap();
        paramMap.put("custId",custId);
        List<EntRepaymentRecord> entRepaymentRecordList=entRepaymentRecordMapper.getRepaymentRecordListByUser(paramMap);
        for (EntRepaymentRecord repaymentRecord:entRepaymentRecordList){
            repaymentRecord.setTotal(repaymentRecord.getPrincipal().add(repaymentRecord.getServiceCharge()).add(repaymentRecord.getInterest()));
        }
        return  entRepaymentRecordList;
    }

    /**
     * 获取客户还款记录明细
     * @param recordId
     * @return
     */
    @Override
    public List<EntRepaymentPlan> getRepaymentRecordDetailed(String recordId) throws Exception{
        Map<String,Object>paramMap=new HashedMap();
        paramMap.put("recordId",recordId);
        List<EntRepaymentPlan> entRepaymentRecordList=entRepaymentPlanMapper.getRepaymentRecordDetailed(paramMap);
        for(EntRepaymentPlan plan:entRepaymentRecordList){
            plan.setTotal(plan.getPrincipal().add(plan.getInterest()).add(plan.getServiceCharge()));
        }
        return  entRepaymentRecordList;
    }

}
