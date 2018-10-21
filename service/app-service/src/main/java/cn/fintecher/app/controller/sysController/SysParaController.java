package cn.fintecher.app.controller.sysController;

import cn.fintecher.app.service.sys.SysParaService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月20日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@RestController
@RequestMapping("/api/sysPara")
@Api(value = "系统参数", description = "系统参数")
public class SysParaController {

    private static final String ENTITY_NAME = "sysPara";
    @Autowired
    private SysParaService sysParaService;


    @ApiOperation(value = "根据系统参数编码查询系统参数", httpMethod = "GET", notes = "根据系统参数编码查询系统参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paraName", value = "参数编码", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping("/getSysParaValue")
    public ResponseEntity getSysParaValue(String paraName){
        try {
            String paraValue = sysParaService.getParaByCompanyAndKey(UserContextUtil.getUserInfo().getCompanyCode(),paraName);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(paraValue));
        } catch (Exception e) {
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"根据系统参数编码查询系统参数异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}