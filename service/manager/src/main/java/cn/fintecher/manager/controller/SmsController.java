package cn.fintecher.manager.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.manager.service.SmsService;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RestController
@RequestMapping("/api/sms")
@Api(value = "/api/sms", description = "短信发送")
public class SmsController {

    private static final String ENTITY_NAME = "sms";

    @Autowired
    private SmsService smsService;


    @ApiOperation(value = "发送短信", notes = "发送短信")
    @PostMapping("/sendMessage")
    public ResponseEntity sendMessage(@RequestBody Map data){
        String account=data.get("account").toString();
        String password=data.get("password").toString();
        String phone = data.get("phone").toString();
        String msg = data.get("msg").toString();
        String host=data.get("host").toString();
        Map respMap = smsService.sendMessage(account,password,phone,msg,host);
        boolean flag = (boolean) respMap.get("flag");
        String logmsg = LocaleMessage.get(respMap.get("msg").toString());
        if(flag){
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(null));
        }else{
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(ResponseLogMessageHandel.initExceptionResponseData());
        }
    }
}