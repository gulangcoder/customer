package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.CompanyProfile;
import cn.fintecher.system.service.CompanyProfileService;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/10
 * @Description:
 */
@RestController
@RequestMapping("/api/companyProfile")
@Api(value = "公司简介配置", description = "公司简介配置")
public class CompanyProfileController {

    private static final String ENTITY_NAME = "companyProfile";

    @Autowired
    private CompanyProfileService companyProfileService;

    @PostMapping("/saveCompanyProfile")
    @ApiOperation(value = "保存公司简介",httpMethod = "POST", notes = "保存公司简介")
    public ResponseEntity saveCompanyProfile(@RequestBody CompanyProfile companyProfile){
        LoggerCommon.info(this.getClass(),"保存公司简介");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        companyProfile.setCompanyCode(userInfo.getCompanyCode());
        Map map = null;
        String localeTipMsg =null;
        try {
            map = companyProfileService.saveCompanyProfile(companyProfile);
            boolean flag = (boolean) map.get("flag");
            localeTipMsg = LocaleMessage.get(map.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"保存公司简介成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg)).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"保存公司简介异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }

    }

    @GetMapping("/getCompanyProfile")
    @ApiOperation(value = "获取公司简介",httpMethod = "GET", notes = "获取公司简介")
    public ResponseEntity getCompanyProfile(){
        LoggerCommon.info(this.getClass(),"获取公司简介");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String companyCode = userInfo.getCompanyCode();
        CompanyProfile companyProfile = null;
        String localeTipMsg =null;
        try {
            companyProfile = companyProfileService.getInfo(companyCode);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取公司简介异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取公司简介成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(companyProfile));
    }
}
