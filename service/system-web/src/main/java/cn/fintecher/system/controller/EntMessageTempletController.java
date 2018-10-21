package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.sms.EntMessageSendDetail;
import cn.fintecher.common.sms.EntMessageSendType;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.EntMessageTemplet;
import cn.fintecher.system.service.EntMessageSendDetailService;
import cn.fintecher.system.service.EntMessageTempletService;
import cn.fintecher.system.service.SysParaService;
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

import java.util.*;

/**
 * Title :
 * Description : @消息管理@
 * Create on : 2018年09月04日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@RestController
@RequestMapping("/api/entMsgTempl")
@Api(value = "/api/entMsgTempl", description = "通知管理")
public class EntMessageTempletController {

    private static final String ENTITY_NAME = "entMsgTempl";

    @Autowired
    private EntMessageTempletService entMessageTempletService;


    @Autowired
    private EntMessageSendDetailService entMessageSendDetailService;


    @Autowired
    private SysParaService sysParaService;




    @ApiOperation(value = "查询通知模板数据列表", notes = "查询通知模板数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "通知模板名称", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "msgType", value = "通知类型", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "status", value = "状态(1启用;0停用)", dataType = "short", paramType = "query", required = false),
    })
    @GetMapping("/getMsgTemplList")
    public ResponseEntity getMsgTemplList(@RequestParam Integer pageSize,
                                          @RequestParam Integer pageIndex,
                                          @RequestParam(value = "msgType",required = false) String msgType,
                                          @RequestParam(value = "title",required = false) String title,
                                          @RequestParam(value = "status",required = false) Short status){
        UserInfo userInfo = UserContextUtil.getUserInfo();
        try {
            PageHelper.startPage(pageIndex, pageSize, true);
            List<Map> list = entMessageTempletService.getMsgTemplList(userInfo.getCompanyCode(), msgType, title, status);
            PageInfo pageInfo = new PageInfo<>(list);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询通知模板数据列表异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "根据主键查询通知模板", notes = "根据主键查询通知模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "通知模板名称", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getMsgTemplById")
    public ResponseEntity getMsgTemplById(@RequestParam String id){
        try {
            //EntMessageTemplet entMessageTemplet = entMessageTempletService.getMsgTemplById(id);
            EntMessageTemplet entMessageTemplet = entMessageTempletService.getMsgTemplDetailById(id);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(entMessageTemplet));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"根据主键查询通知模板异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "保存消息通知模板", notes = "保存消息通知模板")
    @PostMapping("/saveMsgTemplet")
    public ResponseEntity saveMsgTemplet(@RequestBody EntMessageTemplet entMessageTemplet){
        UserInfo userInfo = UserContextUtil.getUserInfo();
        entMessageTemplet.setCreateUser(userInfo.getAccount());
        entMessageTemplet.setCompanyCode(userInfo.getCompanyCode());
        String logmsg =null;
        try {
            Map respMap = entMessageTempletService.saveMessageTemplet(entMessageTemplet);
            boolean flag = (boolean) respMap.get("flag");
            logmsg=LocaleMessage.get(respMap.get("msg").toString());
            if (flag) {
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            }
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"保存消息通知模板异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "修改消息通知模板", notes = "修改消息通知模板")
    @PostMapping("/updateMsgTemplet")
    public ResponseEntity updateMsgTemplet(@RequestBody EntMessageTemplet entMessageTemplet){
        UserInfo userInfo = UserContextUtil.getUserInfo();
        entMessageTemplet.setUpdateUser(userInfo.getAccount());
        String logmsg=null;
        try {
            Map respMap = entMessageTempletService.updateMessageTemplet(entMessageTemplet);
            boolean flag = (boolean) respMap.get("flag");
            logmsg=LocaleMessage.get(respMap.get("msg").toString());
            if (flag) {
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            }
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改消息通知模板异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    /**
     * 修改消息通知模板状态
     * @param data {id:,status:}
     * @return
     */
    @ApiOperation(value = "修改消息通知模板状态", notes = "修改消息通知模板状态")
    @PostMapping("/updateMsgTemplStatus")
    public ResponseEntity updateMsgTemplStatus(@RequestBody Map data){
        data.put("updateUser",UserContextUtil.getUserInfo().getAccount());
        String logmsg = null ;
        try {
            Map respMap = entMessageTempletService.updateStatus(data);
            boolean flag = (boolean) respMap.get("flag");
            logmsg=LocaleMessage.get(respMap.get("msg").toString());
            if (flag) {
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            }
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改消息通知模板异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }



    @ApiOperation(value = "查询通知明细列表", notes = "查询通知明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "customerName", value = "客户姓名", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "customerTel", value = "客户手机号", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "msgSendType", value = "发送类型(1极光;0短信)", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "msgType", value = "通知类型(数据字典：itemCode=msgType)", dataType = "short", paramType = "query", required = false),
            @ApiImplicitParam(name = "sendTime", value = "发送时间", dataType = "date", paramType = "query", required = false),
            @ApiImplicitParam(name = "endTime", value = "发送时间", dataType = "date", paramType = "query", required = false),
    })
    @GetMapping("/getMsgTemplDetailList")
    public ResponseEntity getMsgTemplDetailList(@RequestParam(value = "pageSize") Integer pageSize,
                                          @RequestParam(value = "pageIndex")Integer pageIndex,
                                          @RequestParam(value = "customerName",required = false)String customerName,
                                          @RequestParam(value = "customerTel",required = false)String customerTel,
                                          @RequestParam(value = "msgSendType",required = false)String msgSendType,
                                          @RequestParam(value = "msgType",required = false)String msgType,
                                          @RequestParam(value = "sendTime",required = false)String sendTime,
                                          @RequestParam(value = "endTime",required = false)String endTime){
        UserInfo userInfo = UserContextUtil.getUserInfo();
        Map param = new HashMap();
        param.put("companyCode",userInfo.getCompanyCode());
        param.put("customerName",customerName);
        param.put("customerTel",customerTel);
        param.put("msgSendType",msgSendType);
        param.put("msgType",msgType);
        param.put("sendTime",sendTime);
        param.put("endTime",endTime);
        try {
            PageHelper.startPage(pageIndex, pageSize, true);
            List<Map> list = entMessageSendDetailService.getDetailList(param);
            PageInfo pageInfo = new PageInfo(list);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询通知明细列表异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "根据主键查询通知明细", notes = "根据主键查询通知明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getMsgTemplDetailById")
    public ResponseEntity getMsgTemplDetailById(@RequestParam String id){
        try {
            EntMessageSendDetail entMessageSendDetail = entMessageSendDetailService.getDetailById(id);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(entMessageSendDetail));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"根据主键查询通知明细异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "发送消息", notes = "发送消息")
    @PostMapping("/saveMesTemplDetail")
    public ResponseEntity saveMesTemplDetail(@RequestBody EntMessageSendDetail entMessageSendDetail){
        String logmsg=null;
        try {
            Map respMap=entMessageSendDetailService.sendMessage(entMessageSendDetail);
            logmsg = LocaleMessage.get(respMap.get("msg").toString());
            if((boolean)respMap.get("flag")){
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(entMessageSendDetail));
            }
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(ResponseLogMessageHandel.initExceptionResponseData());
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.send.fail");
            LoggerCommon.info(this.getClass(),"发送消息异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "查询短信验证码模板内容", notes = "查询短信验证码模板内容")
    @GetMapping("/queryMesTemplContent")
    public ResponseEntity queryMesTemplContent(@RequestParam String companyCode,
                                               @RequestParam String code,
                                               @RequestParam String effectiveTime){
        String logmsg=null;
        try {
            Map param = new HashMap();
            param.put("companyCode",companyCode);
            param.put("msgType", EntMessageSendType.ENT_MSG_REGISTER_CODE);
            param.put("status",1);
            List<Map> list_code= entMessageTempletService.getTempDetailList(param);
            String content= null;
            if (list_code==null||list_code.size()<1){
                logmsg = LocaleMessage.get("message.msgTempl.not.existed");
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(ResponseLogMessageHandel.initExceptionResponseData());
            }else{
                content= String.valueOf(list_code.get(0).get("content"));
                content = content.replace("${code}",code);
                content = content.replace("${number}",effectiveTime+ "");
            }
            logmsg = LocaleMessage.get("message.system.successMessage");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(content));
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询短信验证码模板内容异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @GetMapping("/getMsgTemplListOfApp")
    @ApiOperation(value = "app查询站内信消息列表", notes = "app查询站内信消息列表")
    public ResponseEntity getMsgTemplListOfApp(){
        LoggerCommon.info(this.getClass(),"app查询站内信消息列表：");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        Map param = new HashMap();
        param.put("companyCode",userInfo.getCompanyCode());
        param.put("customerId",userInfo.getUserId());
        try {
            List<EntMessageSendDetail> list = entMessageSendDetailService.getMsgTemplListOfApp(param);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"app查询站内信消息列表异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "app查看消息详情", notes = "app查看消息详情")
    @GetMapping("/getMsgTemplDetailOfAppById")
    public ResponseEntity getMsgTemplDetailOfAppById(@RequestParam String id){
        LoggerCommon.info(this.getClass(),"app查询站内信消息列表：");
        try {
            EntMessageSendDetail entMessageSendDetail = entMessageSendDetailService.getMsgTemplDetailOfAppById(id);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(entMessageSendDetail));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"app查看消息详情异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}