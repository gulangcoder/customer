package cn.fintecher.app.controller.bank;

import cn.fintecher.app.model.bank.BankInfo;
import cn.fintecher.app.model.bank.EntCustBank;
import cn.fintecher.app.service.bank.CustBankService;
import cn.fintecher.app.service.sys.SysParaService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.HttpRequestUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/api/bank")
@Api(value = "银行卡相关接口", description = "银行卡相关接口")
public class CustBankController {

    private static final String ENTITY_NAME = "bank";

    @Autowired
    private SysParaService sysParaService;

    @Autowired
    private CustBankService custBankService;

    @Value("${cardInfo.path}")
    private String cardInfoPath;

    /**
     * 绑卡
     * @param map
     * @return
     * @throws Exception
     */
    @PostMapping("/bindingCard")
    @ApiOperation(value = "绑卡", httpMethod = "POST", notes = "绑卡")
    public ResponseEntity bindingCard(@RequestBody Map map){
        Map rmap = null;
        String logmsg = null;
        try {
            String cardno = map.get("cardno").toString();
            String idCardNo = map.get("idCardNo").toString();
            String name = map.get("name").toString();
            String tel = map.get("tel").toString();
            String bankName = map.get("bankName").toString();
            String bankCode = map.get("bankCode").toString();
            UserInfo userInfo = UserContextUtil.getUserInfo();
            String companyCode = userInfo.getCompanyCode();
            //易宝绑卡请求地址
            String authbindcardreqUri = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_bindcard_request_url");//"/rest/v1.0/paperorder/unified/auth/request";
            //商户编号
            String merchantno = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_merchantno");//"10015908980";
            //appkey
            String appKey = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_app_key");
            //secretKey
            String secretKey = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_secret_key");
            //基地址
            String serverRoot = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_server_root");
            Map<String,String> param = new HashMap();
            param.put("authbindcardreqUri",authbindcardreqUri);//易宝请求地址
            //param.put("tel","15178534169");//手机号  userInfo.getTel();
            param.put("tel",tel);//手机号;
            param.put("cardno",cardno);//银行卡号
            param.put("merchantno",merchantno);//商户号
            param.put("idCardNo",idCardNo);//身份证号
            param.put("name",name);//姓名
            //param.put("custId","1111");//客户id   userInfo.getId()
            param.put("custId",userInfo.getUserId());//客户id
            param.put("appKey",appKey);
            param.put("secretKey",secretKey);
            param.put("serverRoot",serverRoot);
            param.put("bankName",bankName);
            param.put("bankCode",bankCode);
            rmap = custBankService.bindingCard(param);
            boolean b = (boolean)rmap.get("flag");
            logmsg = LocaleMessage.get(String.valueOf(rmap.get("msg")));
            if(!b){
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(rmap);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(rmap);
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"绑卡异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }



    @PostMapping("/bindingCardResend")
    @ApiOperation(value = "重新绑卡", httpMethod = "POST", notes = "重新绑卡")
    public ResponseEntity bindingCardResend() throws Exception {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        //String companyCode = "YCSM201806140001";//userInfo.getCompanyCode();//
        String companyCode = userInfo.getCompanyCode();
        //重发地址
        String authbindcardresendUri = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_bindcard_resend_url");//"/rest/v1.0/paperorder/auth/resend";
        //商户编号
        String merchantno = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_merchantno");//"10015908980";
        //appkey
        String appKey = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_app_key");
        //secretKey
        String secretKey = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_secret_key");
        //基地址
        String serverRoot = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_server_root");
        Map<String,String> param = new HashMap<>();
        param.put("authbindcardresendUri",authbindcardresendUri);
        param.put("merchantno",merchantno);
        param.put("appKey",appKey);
        param.put("secretKey",secretKey);
        param.put("serverRoot",serverRoot);
        //param.put("custId","1111");//userInfo.getId()
        param.put("custId",userInfo.getUserId());//客户id
        Map map = custBankService.bindingCardResend(param);
        boolean b = (boolean)map.get("flag");
        String logmsg = LocaleMessage.get(String.valueOf(map.get("msg")));
        if(!b){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(map);
        }
        return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(map);
    }


    /**
     * 绑卡确认接口
     * @param smsCode 短信验证码
     * @return
     * @throws Exception
     */
    @PostMapping("/bindingCardConfirm")
    @ApiOperation(value = "绑卡确认接口", httpMethod = "POST", notes = "绑卡确认接口")
    public ResponseEntity bindingCardConfirm(@RequestBody String smsCode) throws Exception {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        //String companyCode = "YCSM201806140001";
        //企业编号
        String companyCode = userInfo.getCompanyCode();//"YCSM201806140001";
        //确认绑卡地址
        String authbindcardconfirmUri = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_bindcard_confirm_url");//"/rest/v1.0/paperorder/auth/confirm";
        //商户编号
        String merchantno = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_merchantno");//"10015908980";
        //appkey
        String appKey = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_app_key");
        //secretKey
        String secretKey = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_secret_key");
        //基地址
        String serverRoot = sysParaService.getParaByCompanyAndKey(companyCode,"yibao_server_root");
        Map param = new HashMap();
        //param.put("custId","1111");//客户id  userInfo.getId()
        param.put("custId",userInfo.getUserId());//客户id
        param.put("appKey",appKey);
        param.put("secretKey",secretKey);
        param.put("serverRoot",serverRoot);
        param.put("merchantno",merchantno);//商户号
        param.put("smsCode",smsCode);//短信验证码
        param.put("authbindcardconfirmUri",authbindcardconfirmUri);//确认绑卡接口

        Map map = custBankService.bindingCardConfirm(param);
        boolean b = (boolean)map.get("flag");
        String logmsg = LocaleMessage.get(String.valueOf(map.get("msg")));
        if(!b){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(map);
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(map);
    }


    /**
     * 查询用户所有银行卡
     * @return
     */
    @GetMapping("/getCustBankList")
    @ApiOperation(value = "查询用户所有银行卡", httpMethod = "GET", notes = "查询用户所有银行卡")
    public ResponseEntity getCustBankList(){
        UserInfo userInfo = UserContextUtil.getUserInfo();
        try {
            List<EntCustBank> custBanks= custBankService.getCustBankList(userInfo.getUserId());
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(custBanks));
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询用户详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 调用阿里的根据银行卡号码获取银行卡归属地信息接口 获取银行卡对应信息
     * @param:cardNo 银行卡号
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/29
     */
    @GetMapping("/getCustBankInfo")
    @ApiOperation(value = "获取银行卡对应信息", httpMethod = "GET", notes = "查询用户所有银行卡")
    public ResponseEntity getCustBankInfo(@RequestParam String cardNo){
        try {
            String getCardInfoPath = cardInfoPath;
            getCardInfoPath = getCardInfoPath +"?_input_charset=utf-8&cardNo="+cardNo+"&cardBinCheck=true";
            JSONObject json = HttpRequestUtil.httpRequest(getCardInfoPath,"GET","");
            //json.toJSONString()  "{\"bank\":\"CMB\",\"stat\":\"ok\",\"validated\":true,\"cardType\":\"DC\",\"messages\":[],\"key\":\"6214855493724922\"}";
            BankInfo bankInfo = new BankInfo();
            bankInfo.setBank(json.getString("bank"));
            bankInfo.setStat(json.getString("stat"));
            bankInfo.setValidated(json.getString("validated"));
            bankInfo.setCardType(json.getString("cardType"));
            bankInfo.setMessages(json.getString("messages"));
            bankInfo.setKey(json.getString("key"));
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(bankInfo));
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询用户详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}