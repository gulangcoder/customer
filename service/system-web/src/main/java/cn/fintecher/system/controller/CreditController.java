package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.system.model.requestModel.CreditRequest;
import cn.fintecher.system.service.CreditService;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/credit")
@Api(value = "授信管理", description = "授信管理")
public class CreditController {
    private static final String ENTITY_NAME = "credit";
    @Autowired
    private CreditService creditService;

    @PostMapping("/getQuotaList")
    @ApiOperation(value = "授信列表",httpMethod = "POST", notes = "授信列表")
    public ResponseEntity getQuotaList(@RequestBody CreditRequest creditRequest){
        LoggerCommon.info(this.getClass(),"查询授信列表");
        String localeTipMsg =null;
        Map map = null;
        try {
            map = creditService.getQuotaList(creditRequest);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询数据列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询数据列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
    }
    @ApiOperation(value = "授信信息详情",httpMethod = "GET", notes = "授信信息")
    @GetMapping("/creditDetail")
    public ResponseEntity getCreditDetail(@RequestParam String custId){
        LoggerCommon.info(this.getClass(),"查询客户授信信息");
        String logmsg = null;
        try {
            Map map = creditService.getCreditDetail(custId);
            LoggerCommon.info(this.getClass(),"查询客户授信信息");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询客户授信信息成功",ENTITY_NAME)).body(map);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询客户授信信息异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

}
