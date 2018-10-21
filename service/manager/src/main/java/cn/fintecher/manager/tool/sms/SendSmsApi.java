package cn.fintecher.manager.tool.sms;

import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionStackMessage;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Title :
 * Description :创蓝发送短信接口
 * Create on : 2018年06月28日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:zhangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
public class SendSmsApi {
    /**
     * 手机发送短信接口
     * @param map host 接口服务器地址
     * @return
     */
    public static Map sendSms(Map map) {
        Map returnMap = new HashMap();
        String account = map.get("account").toString();//用户账号
        String password = map.get("password").toString();//用户账号密码
        String phone = map.get("phone").toString();//用户手机号码
        String report = "false";//是否需要状态报告（默认false），选填
        String msg = map.get("msg").toString();
        String host = map.get("host").toString();
        try {
            LoggerCommon.info(SendSmsApi.class,"发送短信Url--->"+host);
            SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone,report);
            LoggerCommon.info(SendSmsApi.class,"请求之前数据-->"+smsSingleRequest);
            String requestJson = JSON.toJSONString(smsSingleRequest);
            String response = ChuangLanSmsUtil.sendSmsByPost(host, requestJson);
            LoggerCommon.info(SendSmsApi.class,"接收到的结果--->"+response);
            SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
            LoggerCommon.info(SendSmsApi.class,"接收到的结果--->"+smsSingleResponse);
            returnMap.put("smsSingleResponse",smsSingleResponse);
            return returnMap;
        } catch (Exception ex) {
            LoggerCommon.info(SendSmsApi.class,"发送短信接口--->"+ExceptionStackMessage.collectExceptionStackMsg(ex));
            throw ex;
        }
    }
//    public static void main(String[] args) throws Exception {
//        String msg = "【秒付分期】您的合同编号为1223456的前置服务包支付成功，请继续进件";
//        Map map = new HashMap();
//        map.put("account","N3637302");
//        map.put("password","oTNSiXHGOg48a3");
//        map.put("tel","15755020540");
//        map.put("msg",msg);
//        map.put("host","http://smssh1.253.com/msg/send/json");
//        System.out.print("接收短信接口接收data"+ SendSmsApi.sendSms(map));
//    }
}
