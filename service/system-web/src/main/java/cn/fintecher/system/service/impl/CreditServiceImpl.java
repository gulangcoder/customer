package cn.fintecher.system.service.impl;

import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.client.ManagerFeignUtil;
import cn.fintecher.system.model.EntProductDetail;
import cn.fintecher.system.model.requestModel.CreditRequest;
import cn.fintecher.system.service.CreditService;
import cn.fintecher.system.service.DictDetailService;
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
public class CreditServiceImpl implements CreditService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private DictDetailService dictDetailService;
    @Autowired
    private ProductDetailService productDetailService;
    @Override
    public Map getQuotaList(CreditRequest creditRequest) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pageIndex",creditRequest.getPageIndex());
        map.put("pageSize",creditRequest.getPageSize());
        map.put("state",creditRequest.getState());
        map.put("realName",creditRequest.getRealName());
        map.put("idcardNum",creditRequest.getIdcardNum());
        map.put("startTime",creditRequest.getStartTime());
        map.put("startTime",creditRequest.getEndTime());
        UserInfo userInfo = UserContextUtil.getUserInfo();
        //map.put("companyCode",userInfo.getCompanyCode());

        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_GET_CREDITLIST_API,map,Map.class);
        Map respMap = (Map) responseEntity.getBody();
        List<Map> list = (List<Map>) respMap.get("list");
        for (Map map1 : list) {
            String productDetailId = (String) map1.get("productDetailId");
            if (ChkUtil.isNotEmpty(productDetailId)){
                EntProductDetail productDetail = productDetailService.getProductDeatilById(productDetailId);
                map1.put("productDetail",productDetail);
            }
        }
        return respMap;
    }

    @Override
    public Map getCreditDetail(String custId) throws Exception {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_CREDIT_DETAIL_API,custId,Map.class);
        Map retMap = responseEntity.getBody();
        Map entCustomerInfo = (Map) retMap.get("entCustomerInfo");
        String marry = dictDetailService.getDictNameByDictItemAndDetailCode("marry", entCustomerInfo.get("marryStatus").toString());
        entCustomerInfo.put("marryStatus",marry);
        String residence = dictDetailService.getDictNameByDictItemAndDetailCode("residence", entCustomerInfo.get("residenceType").toString());
        entCustomerInfo.put("residenceType",residence);
        String houseSituation = dictDetailService.getDictNameByDictItemAndDetailCode("houseSituation", entCustomerInfo.get("houseType").toString());
        entCustomerInfo.put("houseType",houseSituation);
        String education = dictDetailService.getDictNameByDictItemAndDetailCode("education", entCustomerInfo.get("educationalType").toString());
        entCustomerInfo.put("educationalType",education);
        String workState = dictDetailService.getDictNameByDictItemAndDetailCode("workState", entCustomerInfo.get("workType").toString());
        entCustomerInfo.put("workType",workState);
        String occupation = dictDetailService.getDictNameByDictItemAndDetailCode("occupation", entCustomerInfo.get("vacationType").toString());
        entCustomerInfo.put("vacationType",occupation);
        String post = dictDetailService.getDictNameByDictItemAndDetailCode("post", entCustomerInfo.get("workPosition").toString());
        entCustomerInfo.put("workPosition",occupation);
        String monthIncome = dictDetailService.getDictNameByDictItemAndDetailCode("monthIncome", entCustomerInfo.get("include").toString());
        entCustomerInfo.put("include",monthIncome);
        return retMap;
    }
}
