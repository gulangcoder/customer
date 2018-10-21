package cn.fintecher.app.controller.sysController;

import cn.fintecher.app.service.sys.DictService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/dict")
@Api(value = "数据字典", description = "数据字典")
public class DictController {

    private static final String ENTITY_NAME = "dict";


    @Autowired
    private DictService dictService;

    @ApiOperation(value = "根据字典编码查询字典明细", httpMethod = "GET", notes = "根据字典编码查询字典明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemCode", value = "数据字典编码", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping("/getDictDetailByItemCode")
    public ResponseEntity getDictDetailByItemCode(@RequestParam String itemCode){
        try {
            List<Map> detailList = dictService.getDictByItemCode(itemCode,UserContextUtil.getUserInfo().getCompanyCode());
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(detailList));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"根据字典编码查询字典明细异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }

    }


    @ApiOperation(value = "查询多个字典明细", httpMethod = "GET", notes = "查询多个字典明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemCodes", value = "查询多个字典明细", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping("/getDicDetailByItemList")
    public ResponseEntity getDicDetailByItemList(@RequestParam String itemCodes){
        LoggerCommon.info(this.getClass(),"查询多个字典明细");
        String localeTipMsg = LocaleMessage.get("message.system.request.param.exception");
        if(null==itemCodes|| cn.fintecher.util.StringUtil.isEmpty(itemCodes)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(null);
        }
        Map map = new HashMap();
        String[] codeList = itemCodes.split(",");
        try {
            for (int i = 0; i < codeList.length; i++) {
                List<Map> detailList = dictService.getDictByItemCode(codeList[i],UserContextUtil.getUserInfo().getCompanyCode());
                map.put(codeList[i],detailList);
            }
            LoggerCommon.info(this.getClass(),"查询多个字典明细成功");
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
        } catch (Exception e) {
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询多个字典明细异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}