package cn.fintecher.app.service.order.impl;

import cn.fintecher.app.client.AppFeginUtil;
import cn.fintecher.app.client.ManagerFeignUtil;
import cn.fintecher.app.mapper.order.EntCustQuotaMapper;
import cn.fintecher.app.mapper.order.EntOrderMapper;
import cn.fintecher.app.mapper.order.EntRepaymentPlanMapper;
import cn.fintecher.app.mapper.product.EntProductCreditMapper;
import cn.fintecher.app.model.order.EntCustQuota;
import cn.fintecher.app.model.order.EntOrder;
import cn.fintecher.app.model.order.EntRepaymentPlan;
import cn.fintecher.app.model.order.RepaymentModel;
import cn.fintecher.app.model.product.EntProductCredit;
import cn.fintecher.app.service.order.LoanService;
import cn.fintecher.app.service.order.QuotaService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.response.ResponseInfo;
import cn.fintecher.common.sms.EntMessageSendDetail;
import cn.fintecher.common.sms.EntMessageSendType;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Title :
 * Description : @借款管理接口实现@
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
public class LoanServiceImpl implements LoanService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private QuotaService quotaService;

    @Autowired
    private EntCustQuotaMapper entCustQuotaMapper;

    @Autowired
    private EntProductCreditMapper entProductCreditMapper;

    @Autowired
    private EntOrderMapper entOrderMapper;

    @Autowired
    private EntRepaymentPlanMapper entRepaymentPlanMapper;

    /**
     * 借款申请提交
     * 1.效验客户额度、产品信息
     * 2.更新客户额度
     * 3.添加订单信息
     * 4.生成还款计划表信息
     * 5.调取三方代付代扣接口
     * @param order
     * @return Map
     */
    @Transactional
    @Override
    public Map<String,Object> loanApply(EntOrder order)throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("resultState",true);
        resultMap.put("msg", "message.system.successMessage");
        EntCustQuota quota=new EntCustQuota();
        //设置查询参数
        quota.setCustId(order.getCustId());
        quota=quotaService.getQuota(quota);
        //效验客户额度
        Map<String,Object> validationMap=quotaService.validation(quota,order.getLoanAmount());
        if (!(boolean)validationMap.get("resultState")){
            resultMap.put("resultState",false);
            resultMap.put("msg", validationMap.get("msg"));
            return resultMap;
        }
        //效验产品信息
        EntProductCredit entProductCredit=entProductCreditMapper.selectByPrimaryKey(order.getProductCreditId());
        if(entProductCredit.getDeleteFlag()!=0||entProductCredit.getStatus()!=1){
            resultMap.put("resultState",false);
            resultMap.put("msg", "message.quota.applying.product");
            return resultMap;
        }
        order.setProductCreditModel(entProductCredit);
        order.setId(UUID.randomUUID().toString());
        order.setState(1);
        order.setCreateTime(new Date());
        // TODO: 2018/9/19 订单编号
        order.setOrderNo(System.currentTimeMillis()+"");

        List<EntRepaymentPlan> planList=new ArrayList<>();
        // TODO: 2018/9/18 生成还款计划
        Map<String,Object>planMap=genRepaymentPlan(order);
        RepaymentModel repaymentModel=new RepaymentModel();
        if ((boolean)planMap.get("resultState")){
            List<Map>planListMap=(List)((Map)planMap.get("planList")).get("planList");
            BeanUtils.populate(repaymentModel,((Map)planMap.get("planList")));
            for (int i=0;i<planListMap.size();i++){
                EntRepaymentPlan plan=new EntRepaymentPlan();
                Map<String,Object>map=(Map)planListMap.get(i);
                map.put("createTime",new Date());
                BeanUtils.populate(plan,map);
                plan.setId(UUID.randomUUID().toString());
                plan.setCustId(order.getCustId());
                plan.setOrderId(order.getId());
                plan.setState(0);
                planList.add(plan);
            }
        }else {
            resultMap.put("resultState",false);
            resultMap.put("msg", planMap.get("msg"));
            return resultMap;
        }
        //更新客户额度
        quota.setAvailableAmount(quota.getAvailableAmount().subtract(order.getLoanAmount()));
        entCustQuotaMapper.updateByPrimaryKeySelective(quota);
        //添加订单
        entOrderMapper.insertSelective(order);
        //保存还款计划
        entRepaymentPlanMapper.insertBatch(planList);
        //放款流水号
        String grantFlowNumber=UUID.randomUUID().toString();
        // TODO: 2018/9/19 发送信息放款成功消息
        EntMessageSendDetail entMessageSendDetail = new EntMessageSendDetail();
        // TODO: 2018/9/18 放款操作 未对接第三方  默认同步返回放款成功
        Map<String,Object>grantLoanMap=grantLoan(order);
        EntOrder entOrder=new EntOrder();
        if ((boolean)grantLoanMap.get("resultState")){
            entOrder.setState(4);
            entMessageSendDetail.setMsgType(EntMessageSendType.ENT_MSG_LOAN_SUCC);
        }else {
            entMessageSendDetail.setMsgType(EntMessageSendType.ENT_MSG_LOAN_SUCC);
            entOrder.setState(7);
        }
        entOrder.setId(order.getId());
        entOrder.setGrantFlowNumber(grantFlowNumber);
        entOrder.setGrantTime(new Date());
        entOrderMapper.updateByPrimaryKeySelective(entOrder);
      // TODO: 2018/9/27 短信发送
        try{
            restTemplate.postForEntity(AppFeginUtil.SYSTEMWEB_API_SAVEMESTEMPLDETAIL_API,entMessageSendDetail,Object.class);
        }
        catch (Exception e){

        }
        return resultMap;
    }

    /**
     * 生成还款计划表
     * @param order
     * @return Map
     */
    @Override
    public Map<String,Object>  genRepaymentPlan(EntOrder order)throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("resultState",true);
        if(order.getProductCreditModel()==null){
            //效验产品信息
            EntProductCredit entProductCredit=entProductCreditMapper.selectByPrimaryKey(order.getProductCreditId());
            if(entProductCredit.getDeleteFlag()==1||entProductCredit.getStatus()==0){
                resultMap.put("resultState",false);
                resultMap.put("msg", "message.loan.plan.failure");
                return resultMap;
            }
            order.setProductCreditModel(entProductCredit);
        }
        RepaymentModel repaymentModel=new RepaymentModel();
        repaymentModel.setDebitAmount(order.getLoanAmount().doubleValue());
        repaymentModel.setPersiods(order.getProductCreditModel().getPeriods());
        repaymentModel.setPersiodsValue(order.getProductCreditModel().getPeriodsDays());
        repaymentModel.setBeginTime(new Date());
        repaymentModel.setRate(order.getProductCreditModel().getRate()==null?0:order.getProductCreditModel().getRate().doubleValue());
        repaymentModel.setRateType(order.getProductCreditModel().getRateFlag());
        repaymentModel.setPersiodsType((int)order.getProductCreditModel().getEachTermType());
        repaymentModel.setHaveFervice((int)order.getProductCreditModel().getHaveService());
        repaymentModel.setServiceFeeRate(order.getProductCreditModel().getServiceFeeRate()==null?0:order.getProductCreditModel().getServiceFeeRate().doubleValue());
        repaymentModel.setServiceFeeRule(order.getProductCreditModel().getServiceFeeRule());
        repaymentModel.setPayType(order.getProductCreditModel().getPaymentWay());
        repaymentModel.setEarlyServiceFeeRate(order.getProductCreditModel().getEarlyServiceFeeRate()==null?0:order.getProductCreditModel().getEarlyServiceFeeRate().doubleValue());
        repaymentModel.setHaveCash((int)order.getProductCreditModel().getHaveCash());
        repaymentModel.setCashDepositType((int)order.getProductCreditModel().getCashDepositType());
        repaymentModel.setCashDeposit(order.getProductCreditModel().getCashDeposit().doubleValue());
        repaymentModel.setCashDepositRule(order.getProductCreditModel().getCashDepositRule());
        repaymentModel.setHavePoundage((int)order.getProductCreditModel().getHavePoundage());
        repaymentModel.setPoundage(order.getProductCreditModel().getPoundage()==null?0:order.getProductCreditModel().getPoundage().doubleValue());
        //调用还款计划服务生成还款计划
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity requestEntity = new HttpEntity(repaymentModel, requestHeaders);
        ResponseEntity<ResponseInfo> entity = restTemplate.postForEntity(ManagerFeignUtil.SYSTEM_API_REPAYMENTPLAN_GENREPAYMENTPLAN_API,requestEntity, ResponseInfo.class);
        // TODO: 2018/9/18  获取还款计划返回
        if (entity.getStatusCode().equals(HttpStatus.OK)) {
            ResponseInfo responseInfo = entity.getBody();
            Map repaymentMap = (Map) responseInfo.getResponseBody();
            resultMap.put("planList",repaymentMap);
            //BeanUtils.populate();
        }else {
            resultMap.put("resultState",false);
        }
        return resultMap;
    }

    /**
     * 调用第三方代付放款接口
     * 1.更新订单表
     * @param order
     * @return Map
     */
    @Override
    public Map<String,Object> grantLoan(EntOrder order)throws Exception{
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
        return  resultMap;
    }

    /**
     * 第三方代付放款回调接口
     * 1.更新订单表
     * @param request
     * @return Map
     */
    @Override
    public void grantLoanCallback (HttpServletRequest request)throws Exception{
        // TODO: 2018/9/15  根据返回结果更新订单状态
    }

}
