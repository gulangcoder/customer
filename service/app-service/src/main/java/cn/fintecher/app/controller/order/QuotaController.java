package cn.fintecher.app.controller.order;

import cn.fintecher.app.model.order.EntCustQuota;
import cn.fintecher.app.service.bank.CustBankService;
import cn.fintecher.app.service.order.QuotaService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.ChkUtil;
import cn.fintecher.util.HeaderUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Title :*额度管理*
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 * @Description: * *
 * Create on : 2018/9/17 16:25
 * Copyright (C) zw.FinTec
 */
@RestController
@RequestMapping("/api/quota")
@Api(value = "/api/quota", description = "额度管理")
public class QuotaController {

    private static final String ENTITY_NAME = "quota";
    @Resource
    private QuotaService quotaService;

    @Autowired
    private CustBankService custBankService;

    @ApiOperation(value = "客户额度授信", notes = "客户额度授信")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "productId", value = "产品系列id", dataType = "String", paramType = "body", required = true),
//    })
    @PostMapping("/creditApply")
    public ResponseEntity creditApply( @RequestBody Map<String,Object> paramMap){
        String logmsg= null;
        String  productId=paramMap.get("productId")==null?null:paramMap.get("productId").toString();
        if (productId==null||"".equals(productId.trim())){
            LocaleMessage.get("message.loan.parameter.failure");
            LoggerCommon.info(this.getClass(),"客户额度授信："+logmsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
        }
        EntCustQuota quota=new EntCustQuota();
        quota.setProductId(productId);
        UserInfo userInfo = UserContextUtil.getUserInfo();
        quota.setCustId(userInfo.getUserId());
        try {
            Map<String,Object> resultMap=quotaService.creditApply(quota);
            logmsg=LocaleMessage.get(resultMap.get("msg").toString());
            if ((boolean)resultMap.get("resultState")){
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
            }else {
                LoggerCommon.info(this.getClass(),"客户额度授信败："+logmsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            }
        } catch (Exception e) {
            logmsg  = LocaleMessage.get("message.quota.exception");
            LoggerCommon.info(this.getClass(),"客户额度授信："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @PostMapping("/getQuota")
    @ApiOperation(value = "获取客户额度信息",httpMethod = "POST", notes = "获取客户额度信息")
    public ResponseEntity getQuota(){
        LoggerCommon.info(this.getClass(),"获取客户额度信息");
        EntCustQuota quota = null;
        try {
            EntCustQuota entCustQuota = new EntCustQuota();
            UserInfo userInfo = UserContextUtil.getUserInfo();
            entCustQuota.setCustId(userInfo.getUserId());
            quota = quotaService.getQuota(entCustQuota);
            //获取绑卡状态
            if (quota!=null) {
                quota.setBindBank(custBankService.getCustBankList(userInfo.getUserId()).size());
            }
            LoggerCommon.info(this.getClass(),"查询成功");
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(quota));
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取客户额度信息异常"+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
    @PostMapping("/getCustQuota")
    @ApiOperation(value = "获取客户额度信息[后管]",httpMethod = "POST", notes = "获取客户额度信息[后管]")
    public ResponseEntity getCustQuota(@RequestBody String custId){
        LoggerCommon.info(this.getClass(),"获取客户额度信息");
        EntCustQuota quota = null;
        try {
            EntCustQuota entCustQuota = new EntCustQuota();
            entCustQuota.setCustId(custId);
            quota = quotaService.getQuota(entCustQuota);
            LoggerCommon.info(this.getClass(),"查询成功");
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(quota));
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取客户额度信息异常"+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @PostMapping("/getQuotaList")
    @ApiOperation(value = "获取客户额度申请历史",httpMethod = "POST", notes = "获取客户额度申请历史")
    public ResponseEntity getQuotaList(@ApiParam(value = "客户详情id",required = true)@RequestBody String custId){
        LoggerCommon.info(this.getClass(),"获取客户额度申请历史");
        if (ChkUtil.isEmpty(custId)){
            UserInfo userInfo = UserContextUtil.getUserInfo();
            custId = userInfo.getUserId();
        }
        try {
            List<EntCustQuota> entCustQuotaList=quotaService.getQuotaList(custId);
            LoggerCommon.info(this.getClass(),"查询成功");
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(entCustQuotaList));
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取客户额度申请历史异常"+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
    @PostMapping("/getCreditList")
    @ApiOperation(value = "授信列表[后管]",httpMethod = "POST", notes = "授信列表[后管]")
    public ResponseEntity getCreditList(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"获取授信列表[后管]");
        PageInfo pageInfo = null;
        try {
            int pageIndex = Integer.parseInt(map.get("pageIndex").toString());
            int pageSize = Integer.parseInt(map.get("pageSize").toString());
            List<Map> list =quotaService.getQuotaListByMap(map);
            pageInfo = new PageInfo(list);
            LoggerCommon.info(this.getClass(),"查询成功");
            return ResponseEntity.ok().body(pageInfo);
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取授信列表[后管]异常"+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}
