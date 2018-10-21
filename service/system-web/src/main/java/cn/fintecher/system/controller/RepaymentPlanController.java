package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.system.model.RepaymentModel;
import cn.fintecher.system.service.RepaymentPlanService;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Title :
 * Description : @还款计划@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:gaozhidong
 * @version 1.0
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@RestController
@RequestMapping("/api/repaymentPlan")
@Api(value = "还款计划管理", description = "还款计划管理")
public class RepaymentPlanController {

    private static final String ENTITY_NAME = "repaymentPlan";
    @Resource
    private RepaymentPlanService repaymentPlanService;
    /**
     * 生成还款计划
     * @param
     * */
    @PostMapping("/genRepaymentPlan")
    @ApiOperation(value = "生成还款计划", httpMethod = "POST",notes = "生成还款计划")
    public ResponseEntity genRepaymentPlan(@RequestBody RepaymentModel repaymentModel)throws Exception{
        if (repaymentModel.getDebitAmount()==null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("借款金额不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getPersiods()==null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("期数不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getPersiodsValue()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("期数值不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getBeginTime()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("借款时间不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getRate()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("利率不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getRateType()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("利率类型不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getPersiodsType()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("期数类型不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getHaveFervice()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("服务费开关不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getServiceFeeRate()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("服务费不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getServiceFeeRule()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("服务费规则不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getPayType()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("还款方式不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getEarlyServiceFeeRate()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("前置服务费费率不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getHaveCash()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("保证金开关不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getCashDepositType()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("保证金类型不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getCashDeposit()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("保证金或保证金费率不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getCashDepositRule()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("保证金规则不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getHavePoundage()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("手续费开关不能为空", ENTITY_NAME)).body(null);
        }
        if (repaymentModel.getPoundage()==null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("手续费金额不能为空", ENTITY_NAME)).body(null);
        }


        String logmsg="";
        try {
            repaymentModel=repaymentPlanService.genRepaymentPlan(repaymentModel);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(repaymentModel));
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.repayment.plan");
            LoggerCommon.info(this.getClass(),"生成还款计划异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 还款计划日跑批计算
     * @param paramMap
     * */
    public void repaymentPlanCalculation(Map<String,Object> paramMap){

    }

}
