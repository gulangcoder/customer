package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.system.model.requestModel.FinancialReq;
import cn.fintecher.system.service.FinancialService;
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
@RequestMapping("/api/financial")
@Api(value = "财务管理", description = "财务管理")
public class FinancialController {
    private static final String ENTITY_NAME = "financial";

    @Autowired
    private FinancialService financialService;

    @ApiOperation(value = "还款列表",httpMethod = "POST", notes = "还款列表")
    @PostMapping("/repaymentRecordList")
    public ResponseEntity getRepaymentRecordList(@RequestBody FinancialReq financialReq){
        LoggerCommon.info(this.getClass(),"查询还款列表");
        Map retMap = new HashMap<>();
        String logmsg = null;
        try {
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("pageSize",financialReq.getPageSize());
            paramMap.put("pageIndex",financialReq.getPageIndex());
            paramMap.put("id",financialReq.getId());
            paramMap.put("orderNo",financialReq.getOrderNo());
            paramMap.put("realName",financialReq.getRealName());
            paramMap.put("idcardNum",financialReq.getIdcardNum());
            paramMap.put("startTime",financialReq.getStartTime());
            paramMap.put("endTime",financialReq.getEndTime());
            paramMap.put("productName",financialReq.getProductName());
            paramMap.put("paymentWay",financialReq.getPaymentWay());
            paramMap.put("periods",financialReq.getPeriods());
            retMap = financialService.getRepaymentRecordList(paramMap);
            LoggerCommon.info(this.getClass(),"查询还款列表成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功",ENTITY_NAME)).body(retMap);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询还款列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "还款详情[后管]", notes = "还款详情")
    @GetMapping("/getRepaymentDetail")
    public ResponseEntity getRepaymentDetail(@RequestParam String repaymentRecordId){
        String logmsg= null;
        try {
            List list = financialService.getRepaymentDetail(repaymentRecordId);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功",ENTITY_NAME)).body(list);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("message.quota.exception");
            LoggerCommon.info(this.getClass(),"获取还款详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }



}
