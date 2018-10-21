package cn.fintecher.app.controller.order;

import cn.fintecher.app.model.bank.EntCustBank;
import cn.fintecher.app.model.order.EntOrder;
import cn.fintecher.app.model.order.EntRepaymentPlan;
import cn.fintecher.app.model.order.EntRepaymentRecord;
import cn.fintecher.app.service.bank.CustBankService;
import cn.fintecher.app.service.order.OrderService;
import cn.fintecher.app.service.order.RepaymentService;
import cn.fintecher.app.service.order.RepaymentTableService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.ChkUtil;
import cn.fintecher.util.HeaderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :*还款管理*
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 * @Description: * *
 * Create on : 2018/9/20 10:48
 * Copyright (C) zw.FinTec
 */
@RestController
@RequestMapping("/api/repayment")
@Api(value = "还款管理", description = "还款管理")
public class RepaymentController {

    private static final String ENTITY_NAME = "repayment";

    @Autowired
    private RepaymentService repaymentService;
    @Autowired
    private RepaymentTableService repaymentTableService;
    @Autowired
    private CustBankService custBankService;
    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "主动还款", notes = "主动还款")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "planIdList", value = "还款计划id", dataType = "List<String> ", paramType = "query", required = true),
//            @ApiImplicitParam(name = "orderId", value = "订单id", dataType = "String", paramType = "query", required = true),
//    })
    @PostMapping("/activeRepayment")
    public ResponseEntity activeRepayment( @RequestBody Map<String,Object> paramMap ){
        List<String>  planIdList=paramMap.get("planIdList")==null?null: (List<String>)paramMap.get("planIdList");
        String  orderId=paramMap.get("orderId")==null?null:paramMap.get("orderId").toString();
        String logmsg= LocaleMessage.get("message.system.successMessage");
        if (planIdList==null||planIdList.size()==0||orderId==null||"".equals(orderId.trim())){
            LocaleMessage.get("message.loan.parameter.failure");
            LoggerCommon.info(this.getClass(),"主动还款失败："+logmsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
        }
        EntRepaymentRecord entRepaymentRecord=new EntRepaymentRecord();
        entRepaymentRecord.setPlanIdList(planIdList);
        entRepaymentRecord.setOrderId(orderId);
        // TODO: 2018/9/17  获取当前登录用户
        try {
            Map<String,Object> resultMap=repaymentService.repayment(entRepaymentRecord);
            if ((boolean)resultMap.get("resultState")){
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(resultMap);
            }else {
                logmsg= LocaleMessage.get(resultMap.get("msg").toString());
                LoggerCommon.info(this.getClass(),"主动还款失败："+logmsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(resultMap);
            }
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"主动还款失败："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "放款信息[后管]",httpMethod = "POST", notes = "放款信息[后管]")
    @GetMapping("/loanInfo")
    public ResponseEntity getLoanInfo(@RequestParam("orderId") String orderId, @RequestParam("custId") String custId){
        LoggerCommon.info(this.getClass(),"根据用户id查询用户银行卡列表");
        HashMap<String, Object> retMap = new HashMap<>();
        String logmsg = null;
        try {
            //银行卡信息
            List<EntCustBank> custBankList = custBankService.getCustBankList(custId);
            if (ChkUtil.isNotEmpty(custBankList)){
                retMap.put("entCustBank",custBankList.get(0));//每人只可绑定一张银行卡
            }
            EntRepaymentPlan repaymentPlan = new EntRepaymentPlan();
            repaymentPlan.setOrderId(orderId);
            //还款计划第一期
            List<EntRepaymentPlan> planList = repaymentTableService.getRepaymentPlanList(repaymentPlan);
            if (ChkUtil.isNotEmpty(planList)){
                retMap.put("planFirst",planList.get(0));
            }
            LoggerCommon.info(this.getClass(),"放款信息查询成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询客户列表成功",ENTITY_NAME)).body(retMap);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询客户列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "根据订单id获取还款计划表集合", notes = "根据订单id获取还款计划表集合")
    @PostMapping("/getRepaymentPlanList")
    public ResponseEntity getRepaymentPlanList(@RequestBody String orderId){
        String logmsg= null;
        try {
            EntRepaymentPlan repaymentPlan = new EntRepaymentPlan();
            repaymentPlan.setOrderId(orderId);
            List<EntRepaymentPlan> planList = repaymentTableService.getRepaymentPlanList(repaymentPlan);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功",ENTITY_NAME)).body(planList);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("message.quota.exception");
            LoggerCommon.info(this.getClass(),"获取还款计划表集合异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "根据订单id获取还款计划第一期", notes = "根据订单id获取还款计划第一期")
    @PostMapping("/getRepaymentPlanFirst")
    public ResponseEntity getRepaymentPlanFirst(@RequestBody String orderId){
        String logmsg= null;
        try {
            EntRepaymentPlan repaymentPlan = new EntRepaymentPlan();
            repaymentPlan.setOrderId(orderId);
            List<EntRepaymentPlan> planList = repaymentTableService.getRepaymentPlanList(repaymentPlan);
            EntRepaymentPlan planFirst = null;
            if (ChkUtil.isNotEmpty(planList)){
                planFirst = planList.get(0);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功",ENTITY_NAME)).body(planFirst);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("message.quota.exception");
            LoggerCommon.info(this.getClass(),"根据订单id获取还款计划第一期异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "还款列表[后管]", notes = "还款后管")
    @PostMapping("/getRepaymentRecordList")
    public ResponseEntity getRepaymentRecordList(@RequestBody Map map){
        String logmsg= null;
        List retList = null;
        PageInfo pageInfo = null;
        try {
            int pageIndex = Integer.parseInt(map.get("pageIndex").toString());
            int pageSize = Integer.parseInt(map.get("pageSize").toString());
            PageHelper.startPage(pageIndex, pageSize, true);
            retList = repaymentTableService.getRepaymentRecordList(map);
            pageInfo = new PageInfo(retList);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功",ENTITY_NAME)).body(pageInfo);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("message.quota.exception");
            LoggerCommon.info(this.getClass(),"获取还款列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "还款详情[后管]", notes = "还款详情")
    @PostMapping("/getRepaymentDetail")
    public ResponseEntity getRepaymentDetail(@RequestBody String repaymentRecordId){
        String logmsg= null;
        try {
            List list = repaymentTableService.getRepaymentDetail(repaymentRecordId);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("操作成功",ENTITY_NAME)).body(list);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("message.quota.exception");
            LoggerCommon.info(this.getClass(),"获取还款详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "获取订单还款计划", notes = "获取订单还款计划")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderId", value = "订单id", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getRepaymentPlan")
    public ResponseEntity getRepaymentPlan(String orderId){
        String logmsg= LocaleMessage.get("message.system.successMessage");
        if (orderId==null||"".equals(orderId.trim())){
            LocaleMessage.get("message.loan.parameter.failure");
            LoggerCommon.info(this.getClass(),"获取订单还款计划："+logmsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
        }
        EntOrder entOrder=new EntOrder();
        entOrder.setId(orderId);
        try {
            Map<String,Object> repaymentPlanMap=orderService.getRepaymentPlan(entOrder);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(repaymentPlanMap);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"获取订单还款计划："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "客户还款中的所有订单", notes = "客户还款中的所有订单")
    @GetMapping("/getOrdersRepaymentPlan")
    public ResponseEntity getOrdersRepaymentPlan(){
        String logmsg= LocaleMessage.get("message.system.successMessage");
        EntOrder entOrder=new EntOrder();
        UserInfo userInfo = UserContextUtil.getUserInfo();
        try {
            Map<String,Object> resultMap=orderService.getOrdersRepaymentPlan(userInfo.getUserId());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(resultMap);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"客户还款中的所有订单："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "客户还款的所有还款记录", notes = "客户还款的所有还款记录")
    @PostMapping("/getRepaymentRecord")
    public ResponseEntity getRepaymentRecord(){
        String logmsg= LocaleMessage.get("message.system.successMessage");
        EntOrder entOrder=new EntOrder();
        UserInfo userInfo = UserContextUtil.getUserInfo();
        try {
            List<EntRepaymentRecord> entRepaymentRecordList=repaymentService.getRepaymentRecord(userInfo.getUserId());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(entRepaymentRecordList);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"客户还款的所有还款记录："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "客户还款的所有还款记录明细", notes = "客户还款的所有还款记录明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "recordId", value = "还款记录id", dataType = "String", paramType = "body", required = true),
    })
    @PostMapping("/getRepaymentRecordDetailed")
    public ResponseEntity getRepaymentRecordDetailed( @RequestBody Map<String,Object> paramMap){
        String logmsg= LocaleMessage.get("message.system.successMessage");
        String  recordId=paramMap.get("recordId")==null?null:paramMap.get("recordId").toString();
        if (recordId==null||"".equals(recordId.trim())){
            LocaleMessage.get("message.loan.parameter.failure");
            LoggerCommon.info(this.getClass(),"客户额度授信："+logmsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
        }
        try {
            List<EntRepaymentPlan> entRepaymentPlanList=repaymentService.getRepaymentRecordDetailed(recordId);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(entRepaymentPlanList);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"客户还款的所有还款记录明细："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}
