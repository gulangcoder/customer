package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.EntCustomer;
import cn.fintecher.system.service.EntCustomerService;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Title :
 * Description : @客户管理@
 * Create on : 2018年06月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:chengrui
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@RestController
@RequestMapping("/api/customer")
@Api(value = "/api/customer", description = "客户管理")
public class EntCustomerController {

    private static final String ENTITY_NAME = "entCustomer";
    @Autowired
    private EntCustomerService entCustomerService;


    @PostMapping("/getList")
    @ApiOperation(value = "客户列表",httpMethod = "POST", notes = "客户列表")
    public ResponseEntity selectEntCustomers(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"查询数据列表");
        String localeTipMsg =null;
        Map retMap = null;
        try {
            UserInfo userInfo = UserContextUtil.getUserInfo();
            map.put("companyCode",userInfo.getCompanyCode());
            retMap = entCustomerService.selectEntCustomers(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询数据列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询数据列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(retMap));
    }

    @GetMapping("/getCustomerDetail")
    @ApiOperation(value = "查询客户详情",httpMethod = "GET", notes = "查询客户详情")
    public ResponseEntity getCustomerDetail(
            @ApiParam("客户id")@RequestParam("custId") String custId){
        LoggerCommon.info(this.getClass(),"关联查询客户详情");
        String localeTipMsg =null;
        Map map = new HashMap();
        try {
            map = entCustomerService.getCustomerDetail(custId);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"关联查询客户详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"关联查询客户详情成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
    }

    @PostMapping("/updateCustomer")
    @ApiOperation(value = "修改客户信息",httpMethod = "POST", notes = "修改客户信息")
    public ResponseEntity updateCustomer(@RequestBody EntCustomer entCustomer){
        LoggerCommon.info(this.getClass(),"修改客户信息");
        String localeTipMsg =null;
        try {
            Map map = entCustomerService.updateCustomer(entCustomer);
            boolean flag =(Boolean) map.get("flag");
            localeTipMsg = LocaleMessage.get(map.get("msg").toString());
            if (flag){
                localeTipMsg = LocaleMessage.get(map.get("msg").toString());
                LoggerCommon.info(this.getClass(),"修改客户信息成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg)).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"修改客户信息异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

}
