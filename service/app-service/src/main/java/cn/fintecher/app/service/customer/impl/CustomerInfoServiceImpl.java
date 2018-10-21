package cn.fintecher.app.service.customer.impl;

import cn.fintecher.app.mapper.bank.EntCustBankMapper;
import cn.fintecher.app.mapper.customer.EntCustProductApplyMapper;
import cn.fintecher.app.mapper.customer.EntCustomerInfoMapper;
import cn.fintecher.app.mapper.customer.EntCustomerMapper;
import cn.fintecher.app.mapper.customer.EntCustomerRelationshipMapper;
import cn.fintecher.app.mapper.order.EntOrderMapper;
import cn.fintecher.app.mapper.order.EntRepaymentPlanMapper;
import cn.fintecher.app.model.bank.EntCustBank;
import cn.fintecher.app.model.customer.*;
import cn.fintecher.app.model.order.EntCustQuota;
import cn.fintecher.app.model.order.EntOrder;
import cn.fintecher.app.model.order.EntRepaymentPlan;
import cn.fintecher.app.service.customer.CustomerInfoService;
import cn.fintecher.app.service.order.OrderService;
import cn.fintecher.app.service.order.QuotaService;
import cn.fintecher.app.service.order.RepaymentTableService;
import cn.fintecher.app.service.sys.DictService;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.ChkUtil;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.DateUtil;
import cn.fintecher.util.SensitiveInfoUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月19日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@Service
public class CustomerInfoServiceImpl implements CustomerInfoService {

    @Autowired
    private EntCustomerInfoMapper customerInfoMapper;
    @Autowired
    private EntCustomerMapper customerMapper;

    @Autowired
    private EntCustomerRelationshipMapper customerRelationshipMapper;

    @Autowired
    private EntCustProductApplyMapper custProductApplyMapper;
    @Autowired
    private QuotaService quotaService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RepaymentTableService repaymentTableService;
    @Autowired
    private EntCustBankMapper custBankMapper;
    @Autowired
    private DictService dictService;

    @Autowired
    private EntOrderMapper entOrderMapper;

    @Autowired
    EntRepaymentPlanMapper entRepaymentPlanMapper;

    /**
     * 保存用户详细资料
     * @param customerInfo
     * @return
     */
    @Override
    public Map updateCustInfo(EntCustomerInfo customerInfo)throws Exception {
        Map respMap = new HashMap();
        customerInfo.setUpdateTime(new Date());
        int row =customerInfoMapper.updateByPrimaryKeySelective(customerInfo);
        if(row != 1){
            respMap.put("flag",false);
            respMap.put("msg","message.system.operation.fail");
            return respMap;
        }
        Map map = new HashMap();
        map.put("custId",customerInfo.getCustId());
        map.put("companyCode",customerInfo.getCompanyCode());
        if(null == customerInfo.getProductId() || "".equals(customerInfo.getProductId())){

        }else {
            map.put("productId",customerInfo.getProductId());
            EntCustProductApply cpa = custProductApplyMapper.selectProductApplyByMap(map);
            if(null != cpa){
                if(cpa.getStatus() == 6){

                }else {
                    map.put("status",(short)3);
                    int r = custProductApplyMapper.updateStatusByMap(map);
                    if(r != 1){
                        respMap.put("flag",false);
                        respMap.put("msg","message.system.operation.fail");
                        return respMap;
                    }
                }
            }
        }
        respMap.put("flag",true);
        respMap.put("msg","message.system.operation.success");
        return respMap;
    }

    @Override
    public Map updateCust(EntCustomer customer) throws Exception {
        Map map = new HashMap();
        customer.setUpdateTime(new Date());
        int n = customerMapper.updateByPrimaryKeySelective(customer);
        if(n>0){
            map.put("flag",true);
            map.put("msg","message.system.update.success");
        }else{
            map.put("flag",false);
            map.put("msg","message.system.update.fail");
        }
        return map;
    }


    /**
     * 根据用户id查询用户详细资料
     * @param custId
     * @return
     */
    @Override
    public EntCustomerInfo getInfoByCustId(String custId)throws Exception {
        EntCustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(custId);
        Map map = new HashMap();
        map.put("custId",custId);
        List<EntCustomer> customerList = customerMapper.selectCustomerByMap(map);
        if (customerList != null && customerList.size() > 0) {
            customerInfo.setPhone(customerList.get(0).getPhone());
        }
        //查询未读站内信数量
        map.put("companyCode",UserContextUtil.getUserInfo().getCompanyCode());
        Integer noRead = customerInfoMapper.customerInfoMapper(map);
        customerInfo.setNoRead(noRead);
        //'marry,residence,houseSituation,isStudent,education,workState,occupation,post,monthIncome'
        String marryStatusHH = dictService.getDictNameByDictItemAndDetailCode("marry",String.valueOf(customerInfo.getMarryStatus()));
        String residenceTypeHH = dictService.getDictNameByDictItemAndDetailCode("residence",String.valueOf(customerInfo.getResidenceType()));
        String houseTypeHH = dictService.getDictNameByDictItemAndDetailCode("houseSituation",customerInfo.getHouseType());
        String isStudentHH = dictService.getDictNameByDictItemAndDetailCode("isStudent",String.valueOf(customerInfo.getIsStudent()));
        String educationalTypeHH = dictService.getDictNameByDictItemAndDetailCode("education",customerInfo.getEducationalType());
        String workTypeHH = dictService.getDictNameByDictItemAndDetailCode("workState",customerInfo.getWorkType());
        String vacationTypeHH = dictService.getDictNameByDictItemAndDetailCode("occupation",customerInfo.getVacationType());
        String workPositionHH = dictService.getDictNameByDictItemAndDetailCode("post",customerInfo.getWorkPosition());
        String includeHH = dictService.getDictNameByDictItemAndDetailCode("monthIncome",customerInfo.getInclude());
        customerInfo.setMarryStatusHH(marryStatusHH);
        customerInfo.setResidenceTypeHH(residenceTypeHH);
        customerInfo.setHouseTypeHH(houseTypeHH);
        customerInfo.setIsStudentHH(isStudentHH);
        customerInfo.setEducationalTypeHH(educationalTypeHH);
        customerInfo.setWorkTypeHH(workTypeHH);
        customerInfo.setVacationTypeHH(vacationTypeHH);
        customerInfo.setWorkPositionHH(workPositionHH);
        customerInfo.setIncludeHH(includeHH);
        //获取客户订单信息
        EntOrder entOrder=new EntOrder();
        entOrder.setCustId(custId);
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
                overdueMap.put("custId", custId);
                List<EntRepaymentPlan> repaymentPlenList = entRepaymentPlanMapper.getOverdueEntRepaymentPlan(overdueMap);
                for (EntRepaymentPlan plan : repaymentPlenList) {
                    if (plan.getOverdueDays() < 0) {
                        loanState = 2;
                        continue;
                    }
                }
            }
        }
        customerInfo.setLoanState(loanState);
        return customerInfo;
    }
    /**
     * 查询用户详情[后管]
     * @param custId 客户详情id
     * @return
     */
    @Override
    public Map getCustomerDetail(String custId) {
        Map map = new HashMap();
        HashMap<String, Object> paramCustMap = new HashMap<>();
        paramCustMap.put("custId",custId);
        List<EntCustomer> customers = customerMapper.selectCustomerByMap(paramCustMap);
        if (ChkUtil.isEmpty(customers)){
            map.put("msg","查无数据");
            return map;
        }
        EntCustomer entCustomer = customers.get(0);
        //数据脱敏
        entCustomer.setPhone(SensitiveInfoUtils.mobilePhone(entCustomer.getPhone()));
        map.put("entCustomer",entCustomer);
        if (org.apache.commons.lang.StringUtils.isNotEmpty(entCustomer.getCustId())) {
            EntCustomerInfo entCustomerInfo = customerInfoMapper.selectByPrimaryKey(entCustomer.getCustId());
            //计算年龄
            if (ChkUtil.isNotEmpty(entCustomerInfo.getIdcardNum())){
                entCustomerInfo.setAddress(DateUtil.IdNOToAge(entCustomerInfo.getIdcardNum())+"");
            }
            //数据脱敏
            entCustomerInfo.setRealName(SensitiveInfoUtils.chineseName(entCustomerInfo.getRealName()));
            entCustomerInfo.setIdcardNum(SensitiveInfoUtils.idCardNum(entCustomerInfo.getIdcardNum()));
            map.put("entCustomerInfo", entCustomerInfo);

            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("custId",entCustomer.getCustId());
            List<EntCustomerRelationship> customerRelationship = customerRelationshipMapper.selectCustomerRelationship(paramMap);
            map.put("relationshipList", customerRelationship);

            Map map1 = new HashMap();
            map1.put("custId",entCustomer.getCustId());
            map1.put("status",1);
            List<EntCustBank> custBanks = custBankMapper.findByCustId(map1);
            if (ChkUtil.isNotEmpty(custBanks)){
                //数据脱敏
                custBanks.get(0).setBankBindName(SensitiveInfoUtils.chineseName(custBanks.get(0).getBankBindName()));
                map.put("custBank",custBanks.get(0));
            }
        }
        return map;
    }

    @Transactional
    @Override
    public Map updateCustomerRelationship(EntCustomerRelationship customerRelationship) throws Exception {
        Map respMap = new HashMap();
        UserInfo userInfo= UserContextUtil.getUserInfo();
        customerRelationship.setCustId(userInfo.getUserId());
        int row = 0;
        if("".equals(customerRelationship.getId()) || customerRelationship.getId() == null){
            customerRelationship.setId(CreateIDUtil.getId());
            customerRelationship.setCreateTime(new Date());
            customerRelationship.setCompanyCode(userInfo.getCompanyCode());
            customerRelationship.setStatus((short)1);
            row = customerRelationshipMapper.insertSelective(customerRelationship);
        }else {
            EntCustomerRelationship cr = new EntCustomerRelationship();
            cr.setId(customerRelationship.getId());
            cr.setStatus((short)0);
            cr.setUpdateTime(new Date());
            customerRelationshipMapper.updateByPrimaryKeySelective(cr);
            //重新添加
            customerRelationship.setId(CreateIDUtil.getId());
            customerRelationship.setCreateTime(new Date());
            customerRelationship.setCompanyCode(userInfo.getCompanyCode());
            customerRelationship.setStatus((short)1);
            row = customerRelationshipMapper.insertSelective(customerRelationship);
        }
        if(row != 1){
            respMap.put("flag",false);
            respMap.put("msg","message.system.operation.fail");
            return respMap;
        }
        Map map = new HashMap();
        map.put("custId",userInfo.getUserId());
        map.put("companyCode",userInfo.getCompanyCode());
        map.put("productId",customerRelationship.getProductId());
        EntCustProductApply cpa = custProductApplyMapper.selectProductApplyByMap(map);
        //查询是否有影像资料
        List<EntProductVideo> productVideoList = custProductApplyMapper.selectProductVideoByProdictId(map);
        //再查询一次
        EntCustProductApply cpay = custProductApplyMapper.selectProductApplyByMap(map);
        if(productVideoList != null && productVideoList.size() > 0){
            cpay.setHaveVideo(1);//是否有影像资料 1有 0无
        }else {
            cpay.setHaveVideo(0);
        }
        if(null != cpa){
            if(cpa.getStatus() == 6){

            }else {
                map.put("status",(short)4);
                int r = custProductApplyMapper.updateStatusByMap(map);
                if(r != 1){
                    respMap.put("flag",false);
                    respMap.put("msg","message.system.operation.fail");
                    return respMap;
                }
            }
        }
        respMap.put("cpay",cpay);
        respMap.put("flag",true);
        respMap.put("msg","message.system.operation.success");
        return respMap;
    }

    @Override
    public EntCustomerRelationship selectCustomerRelationship() throws Exception {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        Map map = new HashMap();
        map.put("custId",userInfo.getUserId());
        map.put("status",1);
        List<EntCustomerRelationship> customerRelationship = customerRelationshipMapper.selectCustomerRelationship(map);
        if(customerRelationship.size() > 0 && customerRelationship != null){
            EntCustomerRelationship cr = customerRelationship.get(0);
            String connTypeHH = dictService.getDictNameByDictItemAndDetailCode("mainLinkMan",cr.getConnType());
            String otherCoonTypeHH = dictService.getDictNameByDictItemAndDetailCode("otherLinkMan",cr.getOtherCoonType());
            cr.setConnTypeHH(connTypeHH);
            cr.setOtherCoonTypeHH(otherCoonTypeHH);
            return cr;
        }
        return null;
    }

    @Transactional
    @Override
    public Map updateCustProduct(Map map) {
        String productId = map.get("productId").toString();
        Short applyStatus = Short.parseShort(map.get("applyStatus").toString());
        UserInfo userInfo = UserContextUtil.getUserInfo();
        map.put("custId",userInfo.getUserId());
        map.put("companyCode",userInfo.getCompanyCode());
        EntCustProductApply custProductApply = new EntCustProductApply();
        EntCustProductApply cpa = custProductApplyMapper.selectProductApplyByMap(map);
        int row = 0;
        if (null != cpa) {
            //更新
            custProductApply.setId(cpa.getId());
            custProductApply.setApplyStatus(applyStatus);
            custProductApply.setUpdateTime(new Date());
            row = custProductApplyMapper.updateByPrimaryKeySelective(custProductApply);
        }else {
            custProductApply.setId(CreateIDUtil.getId());
            custProductApply.setCustId(userInfo.getUserId());
            custProductApply.setProductId(productId);
            custProductApply.setCompanyCode(userInfo.getCompanyCode());
            custProductApply.setApplyStatus(applyStatus);
            custProductApply.setCreateTime(new Date());
            custProductApply.setStatus((short)1);
            row = custProductApplyMapper.insertSelective(custProductApply);
        }
        Map respMap = new HashMap();
        if(row != 1){
            respMap.put("flag",false);
            respMap.put("msg","message.system.operation.fail");
        }
        //查询是否有影像资料
        Map vmap = new HashMap();
        vmap.put("productId",productId);
        List<EntProductVideo> productVideoList = custProductApplyMapper.selectProductVideoByProdictId(vmap);
        //再查询一次
        EntCustProductApply cpay = custProductApplyMapper.selectProductApplyByMap(map);
        if(productVideoList != null && productVideoList.size() > 0){
            cpay.setHaveVideo(1);//是否有影像资料 1有 0无
        }else {
            cpay.setHaveVideo(0);
        }
        respMap.put("cpay",cpay);
        respMap.put("flag",true);
        respMap.put("msg","message.system.operation.success");
        return respMap;
    }

    @Override
    public List<Map> selectEntCustomerList(Map map) throws Exception {
        List<Map> customers = customerMapper.selectEntCustomersByMap(map);
        for (Map map1 : customers) {
            //数据脱敏
            map1.put("real_name",SensitiveInfoUtils.chineseName((String)map1.get("real_name")));
            map1.put("idcard_num",SensitiveInfoUtils.idCardNum((String)map1.get("idcard_num")));
            map1.put("phone",SensitiveInfoUtils.mobilePhone((String)map1.get("phone")));
            //日期处理
            if (ChkUtil.isNotEmpty(map1.get("register_time"))){
                map1.put("register_time",map1.get("register_time").toString().substring(0,map1.get("register_time").toString().length()-2));
            }
            String custId = String.valueOf(map1.get("cust_id"));//客户详情id

            //额度信息
            EntCustQuota entCustQuota = new EntCustQuota();
            entCustQuota.setCustId(custId);
            EntCustQuota quota = quotaService.getQuota(entCustQuota);
            map1.put("quota",quota);

            //借款总笔数 借款总金额
            Map orderSumMap = orderService.getOrderSumByCustId(custId);
            map1.put("loanCount",String.valueOf(orderSumMap.get("loanCount")));//借款总笔数
            map1.put("sumAmount",String.valueOf(orderSumMap.get("sumAmount")));//借款总金额

            //逾期总次数 历史最大逾期天数
            //查询用户所有的订单
            HashMap<String, Object> map2 = new HashMap<>();
            map2.put("states","4,5");
            List<EntOrder> list = orderService.getOrderListByMap(map2);
            //listtoString
            HashMap<String, Object> orderIdsMap = new HashMap<>();
                StringBuilder orderIds = new StringBuilder();
            if (ChkUtil.isNotEmpty(list)){
                for (int i = 0; i < list.size(); i++) {
                    if (i == list.size() - 1) {
                        orderIds.append(list.get(i).getId());
                    } else {
                        orderIds.append(list.get(i).getId());
                        orderIds.append(",");
                    }
                }
            }
            Map overdueCountMap = repaymentTableService.getOverdueCount(orderIds.toString());
            map1.put("overdueCount",String.valueOf(overdueCountMap.get("overdueCount")));//逾期总次数
            map1.put("biggestDeafaultDays",String.valueOf(overdueCountMap.get("biggestDeafaultDays")));//历史最大逾期天数
        }
        return customers;
    }

    @Override
    public Map getCreditDetail(String custId) {
        Map infoMap = getCustomerDetail(custId);
        return infoMap;
    }

    @Override
    public EntCustomerInfo getCustomerInfoById(String userId) throws Exception {
        EntCustomerInfo customerInfo = customerInfoMapper.selectByPrimaryKey(userId);
        return customerInfo;
    }

    @Override
    public void updateCustomerAppAliasById(Map map) throws Exception {
        customerInfoMapper.updateCustomerAppAliasById(map);
    }


}