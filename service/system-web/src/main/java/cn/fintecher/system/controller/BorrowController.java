package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.system.model.requestModel.BorrowRequest;
import cn.fintecher.system.service.BorrowService;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/borrow")
@Api(value = "借款管理", description = "借款管理")
public class BorrowController {
    private static final String ENTITY_NAME = "borrow";

    @Autowired
    private BorrowService borrowService;

    @PostMapping("/getList")
    @ApiOperation(value = "借款列表",httpMethod = "POST", notes = "借款列表")
    public ResponseEntity getBorrowInfo(@RequestBody BorrowRequest borrowRequest){
        LoggerCommon.info(this.getClass(),"查询借款列表");
        String localeTipMsg =null;
        Map map = null;
        try {
            map = borrowService.getBorrowList(borrowRequest);
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
    @GetMapping("/borrowInfo")
    @ApiOperation(value = "借款信息",httpMethod = "GET", notes = "借款信息")
    public ResponseEntity selectEntCustomers(@RequestParam("orderId") String orderId,@RequestParam("custId") String custId){
        LoggerCommon.info(this.getClass(),"查询借款信息");
        String localeTipMsg =null;
        Map borrowInfo = null;
        try {
            borrowInfo = borrowService.getBorrowInfo(custId ,orderId);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询数据列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询数据列表成功");
        return ResponseEntity.ok().body(borrowInfo);
    }

    @ApiOperation(value = "放款信息",httpMethod = "GET", notes = "放款信息")
    @GetMapping("/loanInfo")
    public ResponseEntity getLoanInfo(@RequestParam("orderId") String orderId, @RequestParam("custId") String custId){
        LoggerCommon.info(this.getClass(),"查询放款信息");
        Map retMap = new HashMap<>();
        String logmsg = null;
        try {
            retMap = borrowService.getLoanInfo(custId,orderId);
            LoggerCommon.info(this.getClass(),"放款信息查询成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功",ENTITY_NAME)).body(retMap);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询客户列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "合同信息",httpMethod = "GET", notes = "合同信息")
    @GetMapping("/getContractinfo")
    public ResponseEntity getContractinfo(@RequestParam("orderId") String orderId){
        LoggerCommon.info(this.getClass(),"查询合同信息");
        Map retMap = new HashMap<>();
        String logmsg = null;
        try {
            retMap = borrowService.getContractinfo(orderId);
            LoggerCommon.info(this.getClass(),"放款信息查询成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功",ENTITY_NAME)).body(retMap);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询客户列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "风控审核",httpMethod = "GET", notes = "风控审核")
    @GetMapping("/getQuotaList")
    public ResponseEntity getQuotaList(@RequestParam("custId") String custId){
        LoggerCommon.info(this.getClass(),"查询风控审核");
        String logmsg = null;
        List quotaList = null;
        try {
            quotaList = borrowService.getQuotaList(custId);
            LoggerCommon.info(this.getClass(),"查询风控审核成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功",ENTITY_NAME)).body(quotaList);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询风控审核异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}
