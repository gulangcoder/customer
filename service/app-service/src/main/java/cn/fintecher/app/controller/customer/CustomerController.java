package cn.fintecher.app.controller.customer;

import cn.fintecher.app.model.customer.*;
import cn.fintecher.app.service.customer.CustFileService;
import cn.fintecher.app.service.customer.CustomerInfoService;
import cn.fintecher.app.service.sys.SysParaService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.HeaderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月19日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@RestController
@RequestMapping("/api/customer")
@Api(value = "客户相关接口", description = "客户相关接口")
public class CustomerController {
    private static final String ENTITY_NAME = "customer";

    @Autowired
    private CustomerInfoService customerInfoService;

    @Autowired
    private CustFileService custFileService;

    @Autowired
    private SysParaService sysParaService;


    @ApiOperation(value = "查询用户详情",httpMethod = "GET", notes = "查询用户详情")
    @GetMapping("/getCustomerInfo")
    public ResponseEntity getCustomerInfo(){
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String localeTipMsg=null;
        try {
            EntCustomerInfo customerInfo = customerInfoService.getInfoByCustId(userInfo.getUserId());
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.query.success"),ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(customerInfo));
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询用户详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
    @ApiOperation(value = "查询用户详情[后管]",httpMethod = "GET", notes = "查询用户详情[后管]")
    @GetMapping("/getCustomerDetail")
    public ResponseEntity getCustomerDetail(@ApiParam("客户id")@RequestParam("customerId") String customerId){
        String localeTipMsg=null;
        try {
            Map customerDetail = customerInfoService.getCustomerDetail(customerId);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.query.success"),ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(customerDetail));
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询用户详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "更新用户详情",httpMethod = "POST", notes = "更新用户详情")
    @PostMapping("/updateCustomerInfo")
    public ResponseEntity updateCustomerInfo(@RequestBody EntCustomerInfo entCustomerInfo){
        //设置主键
        entCustomerInfo.setCustId(UserContextUtil.getUserInfo().getUserId());
        entCustomerInfo.setCompanyCode(UserContextUtil.getUserInfo().getCompanyCode());
        String logmsg = null;
        try {
            Map respMap =customerInfoService.updateCustInfo(entCustomerInfo);
            logmsg=LocaleMessage.get(String.valueOf(respMap.get("msg")));
            boolean flag = (boolean)respMap.get("flag");
            if(!flag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message.system.operation.fail", logmsg)).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"更新用户详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
    @ApiOperation(value = "更新用户主表信息",httpMethod = "POST", notes = "更新用户主表信息")
    @PostMapping("/updateCustomer")
    public ResponseEntity updateCustomer(@RequestBody EntCustomer entCustomer){
        String logmsg = null;
        try {
            Map respMap =customerInfoService.updateCust(entCustomer);
            logmsg=LocaleMessage.get(String.valueOf(respMap.get("msg")));
            boolean flag = (boolean)respMap.get("flag");
            if(!flag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message.system.operation.fail", logmsg)).body(null);
            }
            HashMap<String, Object> retMap = new HashMap<>();
            retMap.put("success",true);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(retMap);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"更新用户详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }



    @ApiOperation(value = "查询用户文件",httpMethod = "GET", notes = "查询用户文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileType", value = "文件类型", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getCustFile")
    public ResponseEntity getCustFile(@RequestParam String fileType,@RequestParam String productId){
        String localeTipMsg=null;
        try {
            List<EntCustFile> entCustFile =custFileService.getCustFile(UserContextUtil.getUserInfo().getUserId(),fileType,productId);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.query.success"),ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(entCustFile));
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询用户详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "更新用户文件",httpMethod = "POST", notes = "更新用户文件")
    @PostMapping("/updateCustFile")
    public  ResponseEntity updateCustFile(@RequestBody EntCustFile entCustFile){
        entCustFile.setCustId(UserContextUtil.getUserInfo().getUserId());
        String logmsg = null;
        try {
            Map respMap = custFileService.updateCustFile(entCustFile);
            logmsg = LocaleMessage.get(String.valueOf(respMap.get("msg")));
            boolean flag = (boolean)respMap.get("flag");
            if(!flag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message.system.operation.fail", logmsg)).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"更新用户文件异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "更新客户联系人信息",httpMethod = "POST", notes = "更新客户联系人信息")
    @PostMapping("/updateCustomerRelationship")
    public ResponseEntity updateCustomerRelationship(@RequestBody EntCustomerRelationship customerRelationship){
        LoggerCommon.info(this.getClass(),"更新客户联系人信息");
        //设置关联外键
        customerRelationship.setCustId(UserContextUtil.getUserInfo().getUserId());
        String logmsg = null;
        try {
            Map respMap =customerInfoService.updateCustomerRelationship(customerRelationship);
            logmsg=LocaleMessage.get(String.valueOf(respMap.get("msg")));
            boolean flag = (boolean)respMap.get("flag");
            if(!flag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message.system.operation.fail", logmsg)).body(null);
            }
            EntCustProductApply custProductApply = (EntCustProductApply)respMap.get("cpay");
            LoggerCommon.info(this.getClass(),"更新客户联系人信息成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(custProductApply));
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"更新客户联系人信息异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "查询客户联系人信息",httpMethod = "GET", notes = "查询客户联系人信息")
    @GetMapping("/selectCustomerRelationship")
    public ResponseEntity selectCustomerRelationship(){
        LoggerCommon.info(this.getClass(),"查询客户联系人信息");
        String logmsg = null;
        try {
            EntCustomerRelationship customerRelationship =customerInfoService.selectCustomerRelationship();
            LoggerCommon.info(this.getClass(),"查询客户联系人信息成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.query.success"),ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(customerRelationship));
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询客户联系人信息异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "更新客户产品申请关系",httpMethod = "POST", notes = "更新客户产品申请关系")
    @PostMapping("/updateCustProduct")
    public ResponseEntity updateCustProduct(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"更新客户产品申请关系");
        String logmsg = null;
        try {
            Map respMap =customerInfoService.updateCustProduct(map);
            logmsg=LocaleMessage.get(String.valueOf(respMap.get("msg")));
            boolean flag = (boolean)respMap.get("flag");
            if(!flag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message.system.operation.fail", logmsg)).body(null);
            }
            EntCustProductApply custProductApply = (EntCustProductApply)respMap.get("cpay");
            LoggerCommon.info(this.getClass(),"更新客户产品申请关系成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(custProductApply));
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"更新客户产品申请关系异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "查询客户列表",httpMethod = "POST", notes = "查询客户列表")
    @PostMapping("/customerList")
    public ResponseEntity getCustomerList(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"查询客户列表");
        String logmsg = null;
        PageInfo pageInfo = null;
        List list = null;
        try {
            int pageIndex = Integer.parseInt(map.get("pageIndex").toString());
            int pageSize = Integer.parseInt(map.get("pageSize").toString());
            PageHelper.startPage(pageIndex, pageSize, true);
            list = customerInfoService.selectEntCustomerList(map);
            pageInfo = new PageInfo(list);
            LoggerCommon.info(this.getClass(),"查询客户列表成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("查询客户列表成功",ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询客户列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
    @ApiOperation(value = "授信信息详情",httpMethod = "POST", notes = "授信信息")
    @PostMapping("/creditDetail")
    public ResponseEntity getCreditDetail(@RequestBody String custId){
        LoggerCommon.info(this.getClass(),"查询客户授信信息");
        String logmsg = null;
        try {
            Map map = customerInfoService.getCreditDetail(custId);
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

    @ApiOperation(value = "影像资料更新步骤",httpMethod = "POST", notes = "影像资料更新步骤")
    @PostMapping("/updateCustApplyStatus")
    public  ResponseEntity updateCustApplyStatus(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"影像资料更新步骤");
        String logmsg = null;
        try {
            Map respMap = custFileService.updateCustApplyStatus(map);
            logmsg = LocaleMessage.get(String.valueOf(respMap.get("msg")));
            boolean flag = (boolean)respMap.get("flag");
            if(!flag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "message.system.operation.fail", logmsg)).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"影像资料更新步骤异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "获取影像资料",httpMethod = "GET", notes = "获取影像资料")
    @GetMapping("/getProductVideo")
    public ResponseEntity getProductVideo(@RequestParam String productId){
        LoggerCommon.info(this.getClass(),"获取影像资料");
        String localeTipMsg=null;
        try {
            EntProduct product =custFileService.getProductVideo(productId);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.query.success"),ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(product));
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取影像资料异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "已认证获取影像资料",httpMethod = "GET", notes = "已认证获取影像资料")
    @GetMapping("/getVideo")
    public ResponseEntity getVideo(){
        LoggerCommon.info(this.getClass(),"已认证获取影像资料");
        String localeTipMsg=null;
        try {
            EntCustProductApply custProductApply =custFileService.getVideo();
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.query.success"),ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(custProductApply));
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取已认证获取影像资料异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}