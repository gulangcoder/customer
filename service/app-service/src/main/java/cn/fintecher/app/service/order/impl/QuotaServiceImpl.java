package cn.fintecher.app.service.order.impl;

import cn.fintecher.app.client.AppFeginUtil;
import cn.fintecher.app.mapper.order.EntCustQuotaMapper;
import cn.fintecher.app.mapper.order.EntOrderMapper;
import cn.fintecher.app.mapper.order.EntRepaymentPlanMapper;
import cn.fintecher.app.mapper.product.EntProductDetailMapper;
import cn.fintecher.app.model.order.EntCustQuota;
import cn.fintecher.app.model.order.EntOrder;
import cn.fintecher.app.model.order.EntRepaymentPlan;
import cn.fintecher.app.model.product.EntProduct;
import cn.fintecher.app.model.product.EntProductDetail;
import cn.fintecher.app.service.order.QuotaService;
import cn.fintecher.app.service.order.RiskService;
import cn.fintecher.app.service.product.ProductService;
import cn.fintecher.common.sms.EntMessageSendDetail;
import cn.fintecher.common.sms.EntMessageSendType;
import cn.fintecher.util.ChkUtil;
import cn.fintecher.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Title :
 * Description : @额度管理接口实现@
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
public class QuotaServiceImpl implements QuotaService {

    @Resource
    private ProductService productService;

    @Resource
    private EntCustQuotaMapper entCustQuotaMapper;

    @Resource
    private RiskService riskService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EntProductDetailMapper entProductDetailMapper;

    @Autowired
    private EntOrderMapper entOrderMapper;

    @Autowired
    EntRepaymentPlanMapper entRepaymentPlanMapper;

    /**
     * 查询额度申请历史
     *  @ param custModel
     * */
    @Override
    public List<EntCustQuota> getQuotaList(String custId)throws Exception{
        Map<String,Object> paramMap=new HashedMap();
        paramMap.put("custId",custId);
        return  entCustQuotaMapper.getList(paramMap);
    }

    /**
     * 获取额度信息
     * 根据客户id获取当前客户的额度信息 无额度返回null
     * @param quota 额度
     * @return Quota
     */
    @Override
    public EntCustQuota getQuota(EntCustQuota quota)throws Exception{
        Map<String,Object> paramMap=new HashedMap();
        paramMap.put("custId",quota.getCustId());
        List<EntCustQuota> quotaList=entCustQuotaMapper.getList(paramMap);
        EntCustQuota entCustQuota=null;
        if (quotaList.size()>0){
            entCustQuota=quotaList.get(0);
            if(entCustQuota.getTotalAmount()!=null&&entCustQuota.getAvailableAmount()!=null){
                entCustQuota.setUsedAmount(entCustQuota.getTotalAmount().subtract(entCustQuota.getAvailableAmount()));
            }
            entCustQuota.setRefuseCount(0);
            //额度状态：0提交审核中，1正常，2产品下架，3额度失效,4拒绝
            if (entCustQuota.getState()==4){
                paramMap.put("createTime",new Date());
                paramMap.put("state",4);
                entCustQuota.setRefuseCount(entCustQuotaMapper.getList(paramMap).size());
            }
        }
        //获取客户订单信息
        EntOrder entOrder=new EntOrder();
        entOrder.setCustId(quota.getCustId());
        List<EntOrder> orderList=entOrderMapper.getOrderList(entOrder);
        int grantCount=0;//已放款的笔数
        int settleCount=0;//结清的笔数
        int loanState=0;//无欠款
        if (orderList.size()>0) {
            for (EntOrder order : orderList) {
                if (order.getState() == 4) {
                    grantCount++;
                } else if (order.getState() == 5) {
                    settleCount++;
                }
            }
            //存在还款中的借款
            if (grantCount > 0) {
                loanState = 1;
                Map<String, Object> overdueMap = new HashedMap();
                overdueMap.put("custId", quota.getCustId());
                List<EntRepaymentPlan> repaymentPlenList = entRepaymentPlanMapper.getOverdueEntRepaymentPlan(overdueMap);
                for (EntRepaymentPlan plan : repaymentPlenList) {
                    if (plan.getOverdueDays() < 0) {
                        loanState = 2;
                        continue;
                    }
                }
            }
        }
        if (ChkUtil.isNotEmpty(entCustQuota)){
            entCustQuota.setLoanState(loanState);
        }
        return  entCustQuota;
    }



    /**
     * 额度信息效验
     * 1.效验额度状态、是否过期、是否大于等于借款金额
     * @param  quota 额度实体
     * @param  principal 借款金额
     * @return boolean
     * */
    @Override
    public Map<String,Object> validation(EntCustQuota quota, BigDecimal principal)throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("resultState",true);

        if (quota==null){
            resultMap.put("resultState",false);
            resultMap.put("msg", "message.quota.exception");
            return  resultMap;
        }
        //当前额度状态不可用
        if (quota.getState()!=1){
            resultMap.put("resultState",false);
            resultMap.put("msg", "message.quota.state.unavailable");
            return  resultMap;
        }
        if (quota.getAvailableAmount().compareTo(principal)<0){
            resultMap.put("resultState",false);
            resultMap.put("msg", "message.quota.small.mag");
            return  resultMap;
        }
        return  resultMap;
    }

    /**
     * 客户额度授信
     * 1.效验客户额度、产品信息
     * 2.添加额度信息
     * 3.授权协议
     * 4.调取风控接口
     * @param quota
     * @return Map
     */
    @Override
    public Map<String,Object> creditApply(EntCustQuota quota)throws Exception{
        Map<String,Object> resultMap=new HashedMap();
        resultMap.put("resultState",true);
        resultMap.put("msg", "message.system.successMessage");
        //效验客户额度
        EntCustQuota entCustQuota=getQuota(quota);
        if (entCustQuota!=null) {
            //额度状态：0提交审核中，1正常，2产品下架，3额度失效，4拒绝
            if (entCustQuota.getState() == 0) {
                resultMap.put("resultState", false);
                resultMap.put("msg", "message.quota.applying");
                return resultMap;
            } else if (entCustQuota.getState() == 1) {
                resultMap.put("resultState", false);
                resultMap.put("msg", "message.quota.applying.completion");
                return resultMap;
            } else if (entCustQuota.getState() == 2) {
                //额度更新为失效 重新授信
                entCustQuota.setState(3);
                entCustQuotaMapper.updateByPrimaryKey(entCustQuota);
            }
        }
        //效验产品信息
        EntProduct entProduct=new EntProduct();
        entProduct.setId(quota.getProductId());
        if(!productService.validation(entProduct)){
            resultMap.put("resultState",false);
            resultMap.put("msg", "message.quota.applying.product");
            return resultMap;
        }
        //产品id
        EntProductDetail detail=new EntProductDetail();
        detail.setProductId(quota.getProductId());
        List<EntProductDetail> detailList= entProductDetailMapper.getEntProductDetailList(detail);
        //先判断系列下有无产品
        if (detailList.size()==0){
            resultMap.put("resultState",false);
            resultMap.put("msg", "message.quota.applying.product");
            return resultMap;
        }
        //添加客户额度 状态授信中
        quota.setId(UUID.randomUUID().toString());
        quota.setState(0);
        quota.setCreateTime(new Date());
        quota.setCreditSendTime(new Date());//授信发起时间
        entCustQuotaMapper.insertSelective(quota);

        // TODO: 2018/9/15 保存签订授权协议

        // TODO: 2018/9/15 调取风控接口 暂未对接  默认同步返回结果
        Map<String,Object> riskMap=riskService.getRiskQuota(quota);
        EntCustQuota quotaModel=new EntCustQuota();
        quotaModel.setId(quota.getId());
        if ((boolean)riskMap.get("resultState")){
            quotaModel.setState(1);
            quotaModel.setCreditReturnTime(new Date());
            // TODO: 2018/9/21 随机金额
            int initialAmount=(new Random().nextInt(100) +1)*1000;
            quotaModel.setInitialAmount(new BigDecimal(initialAmount));
            quotaModel.setAvailableAmount(new BigDecimal(initialAmount));
            quotaModel.setTotalAmount(new BigDecimal(initialAmount));

            // TODO: 2018/9/21 随机产品id
            int detaiCount=new Random().nextInt(detailList.size());
            quotaModel.setProductDetailId(detailList.get(detaiCount).getId());
            quotaModel.setCreditResult(1);
            quotaModel.setEnableTime(new Date());
            quotaModel.setEffectiveTime( DateUtil.getDateAddDays(new Date(),90));
            quotaModel.setUpdateTime(new Date());

            // TODO: 2018/9/19 发送信息额度申请成功信息
            EntMessageSendDetail entMessageSendDetail = new EntMessageSendDetail();
            entMessageSendDetail.setMsgType(EntMessageSendType.ENT_MSG_APPLY_QUOTA_SUCC);
            entMessageSendDetail.setMoney(initialAmount+"");
            // TODO: 2018/9/27   暂时不发送
            try{
                restTemplate.postForEntity(AppFeginUtil.SYSTEMWEB_API_SAVEMESTEMPLDETAIL_API,entMessageSendDetail,Object.class);
            }
            catch (Exception e){

            }
        }else {
            quotaModel.setState(3);
            quotaModel.setCreditReturnTime(new Date());
            quotaModel.setInitialAmount((BigDecimal) riskMap.get("resultState"));
            quotaModel.setRefuseCode(riskMap.get("refuseCode").toString());
            quotaModel.setCreditResult(2);
            quotaModel.setUpdateTime(new Date());

            // TODO: 2018/9/19 发送信息额度申请失败信息
            EntMessageSendDetail entMessageSendDetail = new EntMessageSendDetail();
            entMessageSendDetail.setMsgType(EntMessageSendType.ENT_MSG_APPLY_QUOTA_FAIL);
            try{
                restTemplate.postForEntity(AppFeginUtil.SYSTEMWEB_API_SAVEMESTEMPLDETAIL_API,entMessageSendDetail,Object.class);
            }
            catch (Exception e){

            }
        }
        entCustQuotaMapper.updateByPrimaryKeySelective(quotaModel);
        return  resultMap;
    }

    /**
     * 风控回调
     * */
    @Override
    public boolean riskQuotaCallback(EntCustQuota quota)throws Exception{
        // TODO: 2018/9/15
        return  true;
    }

    @Override
    public List<Map> getQuotaListByMap(Map map) {
        return entCustQuotaMapper.getQuotaListByMap(map);
    }
}
