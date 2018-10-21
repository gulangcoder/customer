package cn.fintecher.system.service.impl;

import cn.fintecher.system.client.ManagerFeignUtil;
import cn.fintecher.system.mapper.EntContractBookDetailMapper;
import cn.fintecher.system.mapper.EntProductCreditMapper;
import cn.fintecher.system.model.EntCustomer;
import cn.fintecher.system.service.EntCustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntCustomerServiceImpl implements EntCustomerService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private EntProductCreditMapper entProductCreditMapper;
    @Autowired
    private EntContractBookDetailMapper entContractBookDetailMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(EntCustomerServiceImpl.class);

    @Override
    public Map selectEntCustomers(Map map) {

        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_CUSTOMER_LIST_API,map,Map.class);
        Map respMap = (Map) responseEntity.getBody().get("responseBody");
        List<Map> list = (List) respMap.get("list");

        return respMap;
    }

    @Override
    public Map getCustomerDetail(String customerId) {
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(ManagerFeignUtil.APP_SERVICE_API_CUSTOMER_DETAIL_API+"?customerId="+customerId,Map.class);
        Map respMap = (Map) responseEntity.getBody().get("responseBody");
        return respMap;
    }
    @Override
    public Map updateCustomer(EntCustomer record) {
        Map respMap = new HashMap();
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_UPDATE_DETAIL_API,record,Map.class);
        Map retMap = responseEntity.getBody();
        boolean flag = (boolean) retMap.get("success");
        if (flag){
            respMap.put("flag",true);
            respMap.put("msg","message.system.update.success");
        }
        return respMap;
    }

}
