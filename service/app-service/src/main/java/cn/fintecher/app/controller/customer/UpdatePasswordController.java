package cn.fintecher.app.controller.customer;

import cn.fintecher.app.service.customer.UpdatePasswordService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.redis.RedisKeyConstants;
import cn.fintecher.util.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/18
 * @Description:
 */
@RestController
@RequestMapping("/api/updatePassword")
@Api(value = "修改密码", description = "修改密码")
public class UpdatePasswordController {

    private static final String ENTITY_NAME = "updatePassword";

    @Autowired
    private UpdatePasswordService updatePasswordService;

    /**
     * @description: 忘记密码
     * @param: {"phone":,"password":"","companyCode":"","code":"","sessionId":""}
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/25
     */
    @PostMapping("/forgetPassword")
    @ApiOperation(value = "忘记密码",httpMethod = "POST", notes = "忘记密码")
    public ResponseEntity forgetPassword(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"修改密码");
        String localeTipMsg =null;
        try {
            //校验短信验证码是否正确
            String code = map.get("code").toString();
            String sessionId = map.get("sessionId").toString();
            String redisCode = RedisUtil.get(RedisKeyConstants.SMS_CODE+sessionId);
            if(!code.equals(redisCode)){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.code.error"))).body(null);
            }
            int row = updatePasswordService.updatePassword(map);
            if(row != 1){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.updatePassword.fail"))).body(null);
            }
            LoggerCommon.info(this.getClass(),"修改密码成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.updatePassword.success"),ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.updatePassword.fail");
            LoggerCommon.info(this.getClass(),"修改密码异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 校验是否实名认证
     * @param:{"phone":,"idcardNum":"","companyCode":""}
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/25
     */
    @PostMapping("/checkVerify")
    @ApiOperation(value = "校验是否实名认证",httpMethod = "POST", notes = "校验是否实名认证")
    public ResponseEntity checkVerify(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"校验是否实名认证");
        String localeTipMsg =null;
        try {
            Map resultMap = updatePasswordService.isRealnameVerify(map);
            boolean flag = (boolean)resultMap.get("flag");
            String msg = resultMap.get("msg").toString();
            if(!flag){
                String a= LocaleMessage.get(msg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get(msg))).body(null);
            }
            LoggerCommon.info(this.getClass(),"校验成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(msg,ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"校验异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 忘记密码
     * @param: {"oldPassword":,"newPassword":""}
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/25
     */
    @PostMapping("/resetPassword")
    @ApiOperation(value = "重置密码",httpMethod = "POST", notes = "重置密码")
    public ResponseEntity resetPassword(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"重置密码");
        String localeTipMsg =null;
        try {
            Map resultMap = updatePasswordService.resetPassword(map);
            boolean flag = (boolean)resultMap.get("flag");
            String msg = resultMap.get("msg").toString();
            if(!flag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get(msg))).body(null);
            }
            LoggerCommon.info(this.getClass(),"重置密码成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.updatePassword.success"),ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.updatePassword.fail");
            LoggerCommon.info(this.getClass(),"重置密码异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}
