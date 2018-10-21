package cn.fintecher.app.service.customer.impl;

import cn.fintecher.app.client.AppFeginUtil;
import cn.fintecher.app.client.ManagerFeignUtil;
import cn.fintecher.app.service.customer.SmsService;
import cn.fintecher.common.logger.SysWebLog;
import cn.fintecher.common.response.ResponseInfo;
import cn.fintecher.common.sms.ClMessageAccount;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/17
 * @Description:
 */
@Service
public class SmsServiceImpl implements SmsService{

    @Autowired
    RestTemplate restTemplate;

    @Override
    public Map checkSms(Map map) throws Exception {

        //TODO: 校验验证码是否正确

        //TODO: 获取验证码有效时间比对是否过期
        return null;
    }

    @Override
    public Map sendSmsCode(String phone,String code,String companyCode) {
        Map respMap = new HashMap();
        String content = "";
        //查询短信验证码模板
        Map tMap = new HashMap();
        tMap.put("companyCode",companyCode);
        tMap.put("code",code);
        tMap.put("effectiveTime","2");
        ResponseEntity template = restTemplate.getForEntity(AppFeginUtil.SYSTEMWEB_API_SMSCODETEMPLATE_API+"?companyCode={companyCode}&code={code}&effectiveTime={effectiveTime}",String.class,tMap);
        if(template.getStatusCode().equals(HttpStatus.OK)){
            String a = template.getBody().toString();
            JSONObject jsonObject= JSONObject.fromObject(template.getBody().toString());
            ResponseInfo responseInfo = (ResponseInfo)JSONObject.toBean(jsonObject, ResponseInfo.class);
            if(null == responseInfo){
                respMap.put("flag",false);
                respMap.put("msg","message.sms.send.fail");
                return respMap;
            }
            content = responseInfo.getResponseBody().toString();
        }
        //查询代理商账号
        ResponseEntity<ResponseInfo> response = restTemplate.postForEntity(AppFeginUtil.SYSTEMWEB_API_QUERYClACCOUNT_API,companyCode,ResponseInfo.class);
        if (response.getStatusCode().equals(HttpStatus.OK)){
            ResponseInfo responseInfo = response.getBody();
            Map map = (Map)responseInfo.getResponseBody();
            if(map.isEmpty()){
                respMap.put("flag",false);
                respMap.put("msg","message.sms.send.fail");
                return respMap;
            }
            //调用 manager服务 发送短信
            Map<String, Object> postParameters = new HashMap<>();
            postParameters.put("account", map.get("account").toString());
            postParameters.put("password", map.get("password").toString());
            postParameters.put("host", map.get("host").toString());
            postParameters.put("phone", phone);
            postParameters.put("msg", content);
            ResponseEntity info = restTemplate.postForEntity(AppFeginUtil.MANAGER_API_SENDSMSCODE_API,postParameters,Object.class);
        }
        respMap.put("flag",true);
        respMap.put("msg","message.sms.send.success");
        return respMap;
    }
}
