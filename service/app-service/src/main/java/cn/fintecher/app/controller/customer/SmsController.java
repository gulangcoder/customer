package cn.fintecher.app.controller.customer;

import cn.fintecher.app.service.customer.SmsService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.IdentifyCodeUtil;
import cn.fintecher.util.redis.RedisKeyConstants;
import cn.fintecher.util.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/17
 * @Description:
 */
@RestController
@RequestMapping("/api/smsCode")
@Api(value = "短信验证码", description = "短信验证码")
public class SmsController {

    private static final String ENTITY_NAME = "smsCode";

    @Autowired
    private SmsService smsService;

    /**
     * @description: 发送短信验证码
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/19
     */
    @GetMapping("/doSendSmsCode")
    @ApiOperation(value = "发送短信验证码",httpMethod = "GET", notes = "短信验证码")
    public ResponseEntity doSendSmsCode(@RequestParam String phone,@RequestParam String companyCode){
        LoggerCommon.info(this.getClass(),"发送短信验证码");
        String localeTipMsg =null;
        String varCode = IdentifyCodeUtil.getIdentifyCode();//生成6位验证码
        LoggerCommon.info(this.getClass(),"短信验证码："+varCode);
        Map respMap = null;
        try {
            respMap = smsService.sendSmsCode(phone,varCode,companyCode);
            boolean flag = (boolean)respMap.get("flag");
            String msg = respMap.get("msg").toString();
            if(flag){
                //存入Redis
                long time = 120L;
                String sessionId = UserContextUtil.getSession().getId();
                System.out.println(sessionId+"===========");
                RedisUtil.set(RedisKeyConstants.SMS_CODE+sessionId,"112233",time);
                LoggerCommon.info(this.getClass(),"发送短信验证码成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get(msg),ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(sessionId));
            }else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get(msg))).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.sms.send.fail");
            LoggerCommon.info(this.getClass(),"发送短信验证码异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }

    }

}
