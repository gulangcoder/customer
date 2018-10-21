package cn.fintecher.manager.service.impl;

import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.manager.service.SmsService;
import cn.fintecher.manager.tool.sms.SendSmsApi;
import cn.fintecher.manager.tool.sms.SmsSendResponse;
import cn.fintecher.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@Service
public class SmsServiceImpl implements SmsService {


    @Override
    public Map sendMessage(String account, String password, String phone, String msg, String host) {
        Map respMap = new HashMap();
        Map smsMap = new HashMap();
        smsMap.put("account",account);
        smsMap.put("password",password);
        smsMap.put("phone",phone);
        smsMap.put("msg",msg);
        smsMap.put("host",host);
        Map map= SendSmsApi.sendSms(smsMap);
        //获取发送短信响应对象
        SmsSendResponse smsSendResponse = (SmsSendResponse)map.get("smsSingleResponse");
        if(StringUtil.isNotEmpty(smsSendResponse.getErrorMsg())){
            respMap.put("flag",false);
            respMap.put("msg","message.send.fail");
            return respMap;
        }
        respMap.put("flag",true);
        respMap.put("msg","message.send.success");
        return respMap;
    }
}