package cn.fintecher.app.service.customer.impl;

import cn.fintecher.app.client.AppFeginUtil;
import cn.fintecher.app.mapper.customer.EntCustomerInfoMapper;
import cn.fintecher.app.mapper.customer.EntCustomerMapper;
import cn.fintecher.app.model.customer.EntContractTemplet;
import cn.fintecher.app.model.customer.EntCustomer;
import cn.fintecher.app.model.customer.EntCustomerInfo;
import cn.fintecher.app.service.customer.RegisterService;
import cn.fintecher.common.response.ResponseInfo;
import cn.fintecher.common.sms.EntMessageSendDetail;
import cn.fintecher.common.sms.EntMessageSendType;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.MD5Util;
import cn.fintecher.util.SeqGeneratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/17
 * @Description:
 */
@Service
public class RegisterServiceImpl implements RegisterService{

    @Autowired
    private EntCustomerMapper customerMapper;

    @Autowired
    private EntCustomerInfoMapper customerInfoMapper;

    @Autowired
    RestTemplate restTemplate;


    @Override
    public List<EntCustomer> selectCustomerByMap(Map map) throws Exception {
        List<EntCustomer> customerList = customerMapper.selectCustomerByMap(map);
        return customerList;
    }

    @Transactional
    @Override
    public Map saveCustomer(Map map) throws Exception {
        Map resultMap = new HashMap();
        String phone = map.get("phone").toString();
        String password = map.get("password").toString();
        String origin = map.get("origin").toString();
        String companyCode = map.get("companyCode").toString();
        String deviceId = map.get("deviceId")==null?null:map.get("deviceId").toString();
        String customerNum = SeqGeneratorUtils.customerNo(companyCode);
        //插入客户详情表获得cust_id
        EntCustomerInfo customerInfo = new EntCustomerInfo();
        customerInfo.setCustId(CreateIDUtil.getId());
        customerInfo.setCompanyCode(companyCode);
        customerInfo.setCustNum(customerNum);
        customerInfo.setAuthStatus((short)1);
        customerInfo.setRealnameVerify((short)1);
        customerInfo.setCreateTime(new Date());
        customerInfo.setUpdateTime(new Date());
        customerInfo.setNickName(phone);
        customerInfo.setHeadImg("moren");
        customerInfo.setAppAlias(deviceId);
        int row = customerInfoMapper.insertSelective(customerInfo);
        if (row != 1) {
            resultMap.put("row",row);
            resultMap.put("msg","message.register.fail");
            return resultMap;
        }
        //插入客户表
        EntCustomer customer = new EntCustomer();
        customer.setId(CreateIDUtil.getId());
        customer.setCustId(customerInfo.getCustId());
        customer.setPhone(phone);
        customer.setPassword(MD5Util.GetMD5Code(password));
        customer.setCompanyCode(companyCode);
        customer.setErrorCount(0);
        customer.setIsBlack(1);//是否黑户 0:是 1:否
        customer.setStatus(0);//状态 0:正常 1:锁定 2:冻结
        customer.setOrigin(Integer.valueOf(origin));//来源 0:安卓 1:IOS
        customer.setRegisterTime(new Date());
        customer.setUpdateTime(new Date());
        int addRow = customerMapper.insertSelective(customer);
        if (addRow != 1) {
            resultMap.put("row",addRow);
            resultMap.put("msg","message.register.fail");
            return resultMap;
        }
        resultMap.put("row",1);
        resultMap.put("msg","message.register.success");
        resultMap.put("custId",customerInfo.getCustId());
        resultMap.put("companyCode",companyCode);
        //注册成功，发送注册通知
        EntMessageSendDetail entMessageSendDetail = new EntMessageSendDetail();
        entMessageSendDetail.setMsgType(EntMessageSendType.ENT_MSG_REGISTER);
        entMessageSendDetail.setCustomerId(customerInfo.getCustId());
        entMessageSendDetail.setCompanyCode(companyCode);
        entMessageSendDetail.setCustomerTel(phone);
        entMessageSendDetail.setCustomerName("测试SMS");
        entMessageSendDetail.setDeviceId(deviceId);
        restTemplate.postForEntity(AppFeginUtil.SYSTEMWEB_API_SAVEMESTEMPLDETAIL_API,entMessageSendDetail,Object.class);
        return resultMap;
    }

    @Override
    public List<EntContractTemplet> getRegisterProtocol(Map map) throws Exception {
        List<EntContractTemplet> list = null;
        ResponseEntity<ResponseInfo> response = restTemplate.postForEntity(AppFeginUtil.SYSTEMWEB_API_CONTRACT_API,map,ResponseInfo.class);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            ResponseInfo responseInfo = response.getBody();
            list = (List) responseInfo.getResponseBody();
        }
        return list;
    }
}
