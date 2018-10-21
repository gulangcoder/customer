package cn.fintecher.system.service.impl;

import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.client.ManagerFeignUtil;
import cn.fintecher.system.mapper.EntContractBookDetailMapper;
import cn.fintecher.system.mapper.EntProductCreditMapper;
import cn.fintecher.system.model.EntProductCredit;
import cn.fintecher.system.model.EntProductDetail;
import cn.fintecher.system.model.requestModel.BorrowRequest;
import cn.fintecher.system.service.BorrowService;
import cn.fintecher.system.service.EntContractBookDetailService;
import cn.fintecher.system.service.ProductDetailService;
import cn.fintecher.util.ChkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BorrowServiceImpl implements BorrowService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private EntProductCreditMapper entProductCreditMapper;
    @Autowired
    private EntContractBookDetailMapper entContractBookDetailMapper;
    @Autowired
    private EntContractBookDetailService entContractBookDetailService;
    @Autowired
    private ProductDetailService productDetailService;
    //借款信息
    //1.额度 2.还款计划
    @Override
    public Map getBorrowInfo(String custId,String orderId) {
        HashMap retMap = new HashMap();
        //还款计划
        ResponseEntity<List> responseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_REPAYMENTPLAN_LIST_API,orderId,List.class);
        List planList = responseEntity.getBody();
        retMap.put("planList",planList);
        //额度
        ResponseEntity<Map> quotaResponseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_GET_QUOPTA_API,custId,Map.class);
        Map quota = (Map) quotaResponseEntity.getBody().get("responseBody");
        retMap.put("quota",quota);
        return retMap;
    }

    @Override
    public Map getBorrowList(BorrowRequest borrowRequest) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageIndex",borrowRequest.getPageIndex());
        map.put("pageSize",borrowRequest.getPageSize());
        map.put("orderNo",borrowRequest.getOrderNo());
        map.put("state",borrowRequest.getState());
        map.put("realName",borrowRequest.getRealName());
        map.put("idcardNum",borrowRequest.getIdcardNum());
        UserInfo userInfo = UserContextUtil.getUserInfo();
        map.put("companyCode",userInfo.getCompanyCode());
        //productCreditIds
        if (ChkUtil.isNotEmpty(borrowRequest.getProductName()) || ChkUtil.isNotEmpty(borrowRequest.getPaymentWay()) || ChkUtil.isNotEmpty(borrowRequest.getPeriods())) {
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("productName",borrowRequest.getProductName());
            paramMap.put("paymentWay",borrowRequest.getPaymentWay());
            paramMap.put("periods",borrowRequest.getPeriods());
            List<EntProductCredit> productCreditList = entProductCreditMapper.selectCreditListByMap(paramMap);
            if (ChkUtil.isNotEmpty(productCreditList)){
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < productCreditList.size(); i++) {
                    if (i == productCreditList.size() - 1) {
                        sb.append(productCreditList.get(i).getId());
                    } else {
                        sb.append(productCreditList.get(i).getId());
                        sb.append(",");
                    }
                }
                map.put("productCreditIds",sb);
            }
        }
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_GET_ORDERS_INFO_API,map,Map.class);
        Map respMap = (Map) responseEntity.getBody().get("responseBody");
        List<Map> list = (List) respMap.get("list");
        for (Map orderMap : list) {
            //合同信息
//            String orderId = (String) orderMap.get("id");
//            EntContractBookDetail entContractBookDetail = new EntContractBookDetail();
//            entContractBookDetail.setOrderId(orderId);
//            List<Map> bookDetails = entContractBookDetailMapper.selectListByParams(entContractBookDetail);
//            if (ChkUtil.isNotEmpty(bookDetails)){
//                Map detailMap = bookDetails.get(0);
//                if (ChkUtil.isNotEmpty(detailMap)){
//                    if (ChkUtil.isNotEmpty(detailMap.get("contract_start_date")) && ChkUtil.isNotEmpty(detailMap.get("contract_end_date"))){
//                        orderMap.put("startToEndDate",detailMap.get("contract_start_date").toString() + " ~ " + detailMap.get("contract_end_date").toString());
//                    }
//                }
//            }
            //产品信贷费率
            String productCreditId = (String) orderMap.get("product_credit_id");
            EntProductCredit entProductCredit = entProductCreditMapper.selectByPrimaryKey(productCreditId);
            orderMap.put("entProductCredit",entProductCredit);

        }
        return respMap;
    }

    @Override
    public Map getLoanInfo(String custId, String orderId) {

        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(ManagerFeignUtil.APP_SERVICE_API_LOAN_INFO_API+"?custId="+custId+"&orderId="+orderId,Map.class);
        Map map = responseEntity.getBody();
        return map;
    }

    @Override
    public Map getContractinfo(String orderId) throws Exception {
        Map retMap = new HashMap<>();
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_REPAYMENT_PLAN_FIRST_API,orderId,Map.class);
        Map planMap = responseEntity.getBody();
        retMap.put("planFirst",planMap);//还款计划第一期
        //合同信息
        Map paramMap = new HashMap<>();
        paramMap.put("orderId",orderId);
        UserInfo userInfo = UserContextUtil.getUserInfo();
        paramMap.put("companyCode",userInfo.getCompanyCode());
        List<Map> contractList = entContractBookDetailService.getList(paramMap);
        retMap.put("contractList",contractList);//合同列表
        return retMap;
    }

    @Override
    public List getQuotaList(String custId) throws Exception {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_GET_QUOPTA_LIST_API,custId,Map.class);
        List<Map> qutoList = (List<Map>) responseEntity.getBody().get("responseBody");
        for (Map map : qutoList) {
            String productDetailId = map.get("productDetailId").toString();
            if (ChkUtil.isNotEmpty(productDetailId)){
                EntProductDetail productDetail = productDetailService.getProductDeatilById(productDetailId);
                map.put("productDetail",productDetail);
            }
        }
        return qutoList;
    }
}

