package cn.fintecher.manager.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.logger.SysWebLog;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseInfo;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.manager.model.SysModule;
import cn.fintecher.manager.model.SysWebLogRequest;
import cn.fintecher.manager.service.SysParaService;
import cn.fintecher.manager.service.SysWebLogService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.HeaderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SysWebLogController
 * @Description
 * @Author coder_bao
 * @Date 2018/9/5 11:31
 */
@RestController
@RequestMapping("/api/weblog")
public class SysWebLogController {
    private static final String ENTITY_NAME = "sys_web_log";
    @Autowired
    private SysWebLogService sysWebLogService;

    @PostMapping(value = "/addweblog")
    @ApiOperation(value = "新增系统日志", notes = "新增系统日志")
    public ResponseEntity<ResponseInfo> addSysWebLogInfo(@RequestBody SysWebLog sysWebLog){
        LoggerCommon.info(this.getClass(),"新增系统日志");
        sysWebLog.setId(CreateIDUtil.getId());
        try {
            sysWebLogService.addSysWebLogInfo(sysWebLog);
        } catch (Exception e) {
            String localeMsg = LocaleMessage.get("message.system.errorMessage");
            LoggerCommon.info(this.getClass(),"新增系统日志异常信息："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"The addSysWebLogInfo Exception",
                    localeMsg)).body(null);
        }
        LoggerCommon.info(this.getClass(),"新增系统日志成功");
        return ResponseEntity.ok().body(null);
    }

    @ApiOperation(value = "查询日志列表", httpMethod = "GET", notes = "查询日志列表")
    @GetMapping("/getSysWebLogList")
    public ResponseEntity getSysWebLogList(@RequestParam(value = "pageSize",required = true) Integer pageSize,
                                           @RequestParam(value = "pageIndex",required = true) Integer pageIndex,
                                           @RequestParam(value = "loginAccount",required = false) String loginAccount,
                                           @RequestParam(value = "companyCode",required = false) String companyCode,
                                           @RequestParam(value = "method",required = false) String method,
                                           @RequestParam(value = "terminalType",required = false) String terminalType,
                                           @RequestParam(value = "operateType",required = false) String operateType,
                                           @RequestParam(value = "status",required = false) String status,
                                           @RequestParam(value = "exceptionCode",required = false) String exceptionCode,
                                           @RequestParam(value = "startTime",required = false) String startTime,
                                           @RequestParam(value = "endTime",required = false) String endTime){
        LoggerCommon.info(this.getClass(),"查询日志列表");
        PageInfo pageInfo = null;
        SysWebLogRequest sysWebLogRequest  = new SysWebLogRequest(loginAccount,companyCode,method,terminalType,operateType,status,exceptionCode,startTime,endTime,pageSize,pageIndex);
        try {
            PageHelper.startPage(pageIndex, pageSize, true);
            List<SysWebLog> sysWebLogList = sysWebLogService.getSysWebLogList(sysWebLogRequest);
            pageInfo = new PageInfo(sysWebLogList);
        } catch (Exception e) {
            String localeMsg = LocaleMessage.get("message.system.errorMessage");
            LoggerCommon.info(this.getClass(),"查询日志列表异常信息："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"成功查询日志列表数据");
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.successMessage"),ENTITY_NAME)).body(pageInfo);
    }

    @ApiOperation(value = "查询异常日志堆栈信息", httpMethod = "GET", notes = "查询异常日志堆栈信息")
    @GetMapping("/getExceptionStackMsgById")
    public ResponseEntity getExceptionStackMsgById(@RequestParam(value = "id") String id){
        LoggerCommon.info(this.getClass(),"查询异常日志堆栈信息");
        String exceptionStacMsg = null;
        try {
            exceptionStacMsg = sysWebLogService.getExceptionStackMsgById(id);
        } catch (Exception e) {
            String localeMsg = LocaleMessage.get("message.system.errorMessage");
            LoggerCommon.info(this.getClass(),"查询异常日志堆栈信息异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"成功查询异常日志堆栈信息");
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("find success",ENTITY_NAME)).body(
                ResponseLogMessageHandel.initSuccessResponseData(exceptionStacMsg)
        );
    }


}
