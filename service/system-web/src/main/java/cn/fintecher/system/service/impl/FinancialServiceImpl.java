package cn.fintecher.system.service.impl;

import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.client.ManagerFeignUtil;
import cn.fintecher.system.mapper.EntProductCreditMapper;
import cn.fintecher.system.model.EntProductCredit;
import cn.fintecher.system.service.FinancialService;
import cn.fintecher.util.ChkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
@Service
public class FinancialServiceImpl implements FinancialService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private EntProductCreditMapper entProductCreditMapper;
    @Override
    public Map getRepaymentRecordList(Map map) {

        //productCreditIds
        if (ChkUtil.isNotEmpty(map.get("productName")) || ChkUtil.isNotEmpty(map.get("paymentWay")) || ChkUtil.isNotEmpty(map.get("periods"))){
            List<EntProductCredit> productCreditList = entProductCreditMapper.selectCreditListByMap(map);
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
        UserInfo userInfo = UserContextUtil.getUserInfo();
        map.put("companyCode",userInfo.getCompanyCode());
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_REPAYMENT_RECORD_LIST_API,map,Map.class);
        Map retMap = responseEntity.getBody();
        List<Map> list = (List) retMap.get("list");
        for (Map map1 : list) {
            //产品credit
            String productCreditId = map1.get("productCreditId").toString();
            EntProductCredit entProductCredit = entProductCreditMapper.selectByPrimaryKey(productCreditId);
            map1.put("entProductCredit",entProductCredit);
        }
        return retMap;
    }

    @Override
    public List getRepaymentDetail(String repaymentRecordId) {
        ResponseEntity<List> responseEntity = restTemplate.postForEntity(ManagerFeignUtil.APP_SERVICE_API_REPAYMENT_DETAIL_API,repaymentRecordId,List.class);
        List list = responseEntity.getBody();
        return list;
    }
}
