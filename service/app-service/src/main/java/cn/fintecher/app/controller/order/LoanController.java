package cn.fintecher.app.controller.order;

import cn.fintecher.app.model.order.EntOrder;
import cn.fintecher.app.model.product.EntProduct;
import cn.fintecher.app.model.product.EntProductCredit;
import cn.fintecher.app.service.order.LoanService;
import cn.fintecher.app.service.product.ProductService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Title :*借款管理*
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 * @Description: * *
 * Create on : 2018/9/20 9:21
 * Copyright (C) zw.FinTec
 */
@RestController
@RequestMapping("/api/loan")
@Api(value = "借款管理", description = "借款管理")
public class LoanController {

    private static final String ENTITY_NAME = "loan";

    @Autowired
    private LoanService loanService;

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "借款申请", notes = "借款申请")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "productCreditId", value = "产品信率id", dataType = "String", paramType = "body", required = true),
//            @ApiImplicitParam(name = "loanAmount", value = "借款金额", dataType = "Double", paramType = "body", required = true),
//            @ApiImplicitParam(name = "loanPurpose", value = "借款用途", dataType = "String", paramType = "body", required = true),
//    })
    @PostMapping("/loanApply")
    public ResponseEntity loanApply(@RequestBody Map<String,Object> paramMap){
        String productCreditId=paramMap.get("productCreditId")==null?null:paramMap.get("productCreditId").toString();
        Double loanAmount=paramMap.get("loanAmount")==null?null:Double.parseDouble(paramMap.get("loanAmount").toString());
        String loanPurpose=paramMap.get("loanPurpose")==null?null:paramMap.get("loanPurpose").toString();
        String logmsg= LocaleMessage.get("message.system.successMessage");
        if (productCreditId==null||"".equals(productCreditId.trim())||loanAmount==null||loanPurpose==null||"".equals(loanPurpose.trim())){
            LocaleMessage.get("message.loan.parameter.failure");
            LoggerCommon.info(this.getClass(),"借款申请失败："+logmsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
        }
        EntOrder entOrder=new EntOrder();
        entOrder.setProductCreditId(productCreditId);
        entOrder.setLoanAmount(new BigDecimal(loanAmount));
        UserInfo userInfo = UserContextUtil.getUserInfo();
        entOrder.setCustId(userInfo.getUserId());
        entOrder.setCompanyCode(userInfo.getCompanyCode());
        entOrder.setLoanPurpose(loanPurpose);
        try {
            Map<String,Object> resultMap=loanService.loanApply(entOrder);
            if ((boolean)resultMap.get("resultState")){
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(resultMap);
            }else {
                logmsg= LocaleMessage.get(resultMap.get("msg").toString());
                LoggerCommon.info(this.getClass(),"借款申请失败："+logmsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            }
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"借款申请失败："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "获取还款计划", notes = "获取还款计划")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "productCreditId", value = "产品信率id", dataType = "String", paramType = "body", required = true),
//            @ApiImplicitParam(name = "loanAmount", value = "借款金额", dataType = "Double", paramType = "body", required = true),
//    })
    @PostMapping("/genRepaymentPlan")
    public ResponseEntity genRepaymentPlan(@RequestBody Map<String,Object> paramMap){
        String productCreditId=paramMap.get("productCreditId")==null?null:paramMap.get("productCreditId").toString();
        Double loanAmount=paramMap.get("loanAmount")==null?null:Double.parseDouble(paramMap.get("loanAmount").toString());
        String logmsg= LocaleMessage.get("message.system.successMessage");
        if (productCreditId==null||"".equals(productCreditId.trim())){
            LocaleMessage.get("message.loan.parameter.failure");
            LoggerCommon.info(this.getClass(),"获取还款计划失败："+logmsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
        }
        EntOrder entOrder=new EntOrder();
        entOrder.setProductCreditId(productCreditId);
        entOrder.setLoanAmount(new BigDecimal(loanAmount));
        UserInfo userInfo = UserContextUtil.getUserInfo();
        entOrder.setCustId(userInfo.getUserId());
        entOrder.setCompanyCode(userInfo.getCompanyCode());
        try {
            Map<String,Object> resultMap=loanService.genRepaymentPlan(entOrder);
            if ((boolean)resultMap.get("resultState")){
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(resultMap.get("planList"));
            }else {
                logmsg= LocaleMessage.get(resultMap.get("msg").toString());
                resultMap.put("resultState",false);
                resultMap.put("msg", resultMap.get("msg"));
                LoggerCommon.info(this.getClass(),"获取还款计划失败："+logmsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            }
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"获取还款计划失败："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "获取产品信息", notes = "获取产品信息")
    @ApiImplicitParams({
    })
    @PostMapping("/getProduct")
    public ResponseEntity getProduct(){
        String logmsg= null;
        EntProduct entProduct=new EntProduct();
        UserInfo userInfo = UserContextUtil.getUserInfo();
        entProduct.setCompanyCode(userInfo.getCompanyCode());
        try {
            List<EntProduct> entProductList=productService.getProductList(entProduct);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(entProductList);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"获取产品信息失败："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "获取产品详情信息", notes = "获取产品详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "产品信率id", dataType = "String", paramType = "body", required = true),
    })
    @PostMapping("/getProductById")
    public ResponseEntity getProductById(){
        String logmsg= null;
        EntProduct entProduct=new EntProduct();
        UserInfo userInfo = UserContextUtil.getUserInfo();
        entProduct.setCompanyCode(userInfo.getCompanyCode());
        try {
            List<EntProduct> entProductList=productService.getProductList(entProduct);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(entProductList);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"获取产品信息失败："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "获取产品信率信息", notes = "获取产品信率信息")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "paymentWay", value = "0等额本息 1等额本金 2先息后本 3到期一次还本付息 4等本等息", dataType = "String", paramType = "query", required = false),
//            @ApiImplicitParam(name = "productDetailId", value = "产品详情id", dataType = "String", paramType = "body", required = true),
//    })
    @PostMapping("/getProductCredit")
    public ResponseEntity getProductCredit(@RequestBody Map<String,Object> paramMap){
        String paymentWay=paramMap.get("paymentWay")==null?null:paramMap.get("paymentWay").toString();
        String productDetailId=paramMap.get("productDetailId")==null?null:paramMap.get("productDetailId").toString();
        String logmsg= "查询成功";
        EntProductCredit entProductCredit=new EntProductCredit();
        entProductCredit.setPaymentWay(paymentWay);
        UserInfo userInfo = UserContextUtil.getUserInfo();
        try {
            List<EntProductCredit> productCreditList=productService.getProductCredit(entProductCredit,userInfo.getUserId());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(productCreditList);
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"获取产品信息失败："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}
