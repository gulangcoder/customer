package cn.fintecher.app.controller.ocr;

import cn.fintecher.app.model.customer.EntCustomerInfo;
import cn.fintecher.app.service.customer.CustomerInfoService;
import cn.fintecher.app.service.ocr.OcrService;
import cn.fintecher.app.service.sys.SysParaService;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.ocr.AliveCertification;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@RestController
@RequestMapping("/api/ocr")
public class OcrController{

    private static final String ENTITY_NAME = "ocr";

    @Autowired
    private OcrService ocrService;
    @Resource
    private SysParaService sysParaService;

    @Autowired
    private CustomerInfoService customerInfoService;

    @Value("${ocr.url}")
    private String aliveUrl;

    @Value("${ocr.version}")
    private String version;

    @Value("${ocr.type_nonce}")
    private String type;

    @Value("${ocr.type_sign}")
    private String signType;

    @Value("${ocr.faceResultUrl}")
    private String faceResultUrl;

    @Value("${ocr.singVersion}")
    private String singVersion;


    @GetMapping("/getNonceTicket")
    @ApiOperation(value = "获取NonceTicket活体数据", httpMethod = "GET", notes = "获取NonceTicket活体数据")
    public ResponseEntity getNonceTicket() {
        String localeTipMsg=null;
        String str = null;
        try {
            String nonceStr = UUID.randomUUID().toString().replace("-", "");
            UserInfo userInfo = UserContextUtil.getUserInfo();
            String user_id = userInfo.getUserId();
            String companyCode = userInfo.getCompanyCode();
            String app_id = sysParaService.getParaByCompanyAndKey(companyCode,"ocr_appid");
            Map ticketMap = new HashMap();
            ticketMap.put("user_id",user_id);
            ticketMap.put("url",aliveUrl);
            ticketMap.put("type",type);
            ticketMap.put("version",version);
            ticketMap.put("companyCode",companyCode);
            ticketMap.put("app_id",app_id);
            str = ocrService.getNonceTicket(ticketMap);
            JSONObject json1 = JSONObject.parseObject(str);
            String logmsg = null;
            if(json1 == null){
                logmsg=LocaleMessage.get("message.system.errorMessage");
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
            }
            if(!(json1.get("code")!= null && "0".equals(json1.get("code")+""))){
                logmsg=LocaleMessage.get("message.system.errorMessage");
                LoggerCommon.info(this.getClass(),json1.get("msg")+"");
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
            }
            Map api_tickets = (Map)((List)json1.get("tickets")).get(0);
            String api_ticket = api_tickets.get("value")+"";
            ArrayList<String> list = new ArrayList<String>();
            list.add(app_id);
            list.add(user_id);
            list.add(nonceStr);
            list.add(version);
            Collections.sort(list);
            String sign = AliveCertification.sign(list,api_ticket);
            String orderNo = UUID.randomUUID().toString().replace("-", "");
            EntCustomerInfo customerInfo = customerInfoService.getCustomerInfoById(userInfo.getUserId());
            Map map = new HashMap();
            map.put("sign",sign);
            map.put("userId",user_id);
            map.put("real_name",customerInfo.getRealName());
            map.put("idcard_num",customerInfo.getIdcardNum());
            map.put("nonceStr",nonceStr);
            map.put("version",version);
            map.put("app_id",app_id);
            map.put("type_nonce",type);
            map.put("orderNo",orderNo);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"获取活体数据异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @GetMapping("/getSignTicket")
    @ApiOperation(value = "获取SignTicket活体数据", httpMethod = "GET", notes = "获取SignTicket活体数据")
    public ResponseEntity getSignTicket() {
        String localeTipMsg=null;
        String str = null;
        try {
            String orderNo = UUID.randomUUID().toString().replace("-", "");
            String nonceStr = UUID.randomUUID().toString().replace("-", "");
            UserInfo userInfo = UserContextUtil.getUserInfo();
            String user_id = userInfo.getUserId();
            String companyCode = userInfo.getCompanyCode();
            String app_id = sysParaService.getParaByCompanyAndKey(companyCode,"ocr_appid");
            Map ticketMap = new HashMap();
            ticketMap.put("url",aliveUrl);
            ticketMap.put("type",signType);
            ticketMap.put("version",version);
            ticketMap.put("companyCode",companyCode);
            ticketMap.put("app_id",app_id);
            str = ocrService.getTencentOcrSignTicket(ticketMap);
            JSONObject json1 = JSONObject.parseObject(str);
            String logmsg = null;
            if(json1 == null){
                logmsg=LocaleMessage.get("message.system.errorMessage");
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
            }
            if(!(json1.get("code")!= null && "0".equals(json1.get("code")+""))){
                logmsg=LocaleMessage.get("message.system.errorMessage");
                LoggerCommon.info(this.getClass(),json1.get("msg")+"");
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
            }
            Map api_tickets = (Map)((List)json1.get("tickets")).get(0);
            String api_ticket = api_tickets.get("value").toString();
            Map signMap = AliveCertification.getTencentOcrServiceSingStr(app_id,orderNo,singVersion,api_ticket,nonceStr);
            //String singMsg = signMap.get("msg").toString();
            boolean signFlag = (boolean)signMap.get("flag");
            if(!signFlag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",LocaleMessage.get("message.system.operation.fail"))).body(null);
            }
            String validationSign =  signMap.get("singStr").toString();
            EntCustomerInfo customerInfo = customerInfoService.getCustomerInfoById(userInfo.getUserId());
            Map map = new HashMap();
            map.put("sign",validationSign);
            map.put("userId",user_id);
            map.put("nonceStr",nonceStr);
            map.put("version",version);
            map.put("app_id",app_id);
            map.put("type_nonce",signType);
            map.put("orderNo",orderNo);
            map.put("real_name",customerInfo.getRealName());
            map.put("idcard_num",customerInfo.getIdcardNum());
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"获取活体数据异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @PostMapping("/validation")
    @ApiOperation(value = "认证", httpMethod = "POST", notes = "认证")
    public ResponseEntity validation(@RequestBody Map pmap){
        String localeTipMsg=null;
        try {
            String nonceStr = pmap.get("nonceStr").toString();
            String sign = pmap.get("sign").toString();
            UserInfo userInfo =UserContextUtil.getUserInfo();
            String user_id = userInfo.getUserId();
            String companyCode = userInfo.getCompanyCode();
            Map map = new HashMap();
            map.put("user_id",user_id);
            map.put("companyCode",companyCode);
            map.put("nonceStr",nonceStr);
            map.put("sign",sign);
            boolean flag = ocrService.validation(map);
            if(flag){
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.check.success"),ENTITY_NAME)).body(null);
            }
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.check.fail"),ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"认证异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 
     * @param:singStr nonceStr orderNo
     * @return: 
     * @auther: liangdeng
     * @date: 2018/9/29 
     */
    @PostMapping("/saveCustomerFace")
    @ApiOperation(value = "人脸识别信息保存", httpMethod = "POST", notes = "人脸识别信息保存")
    public ResponseEntity saveCustomerFace(@RequestBody Map pmap, HttpServletRequest request){
        String localeTipMsg=null;
        String str = null;
        try {
            UserInfo userInfo = UserContextUtil.getUserInfo();
            String companyCode = userInfo.getCompanyCode();
            String user_id = userInfo.getUserId();
            String bucketName = sysParaService.getParaByCompanyAndKey(companyCode,"oss_bucketName");
            String endpoint = sysParaService.getParaByCompanyAndKey(companyCode,"oss_endpoint");
            String accessKeyId = sysParaService.getParaByCompanyAndKey(companyCode,"oss_accessKeyId");
            String accessKeySecret = sysParaService.getParaByCompanyAndKey(companyCode,"oss_accessKeySecret");
            String path = request.getSession().getServletContext().getRealPath("/fintecher_file/");
            String app_id = sysParaService.getParaByCompanyAndKey(companyCode,"ocr_appid");
            //String nonceStr =  pmap.get("nonceStr").toString();
            //String sign =  pmap.get("sign").toString();
            //获取扫描结果 需要重新获取signticket 去生成新的签名
            String orderNo =  pmap.get("orderNo").toString();
            String nonceStr = UUID.randomUUID().toString().replace("-", "");
            Map ticketMap = new HashMap();
            ticketMap.put("url",aliveUrl);
            ticketMap.put("type",signType);
            ticketMap.put("version",version);
            ticketMap.put("companyCode",companyCode);
            ticketMap.put("app_id",app_id);
            str = ocrService.getTencentOcrSignTicket(ticketMap);
            JSONObject json1 = JSONObject.parseObject(str);
            String logmsg = null;
            if(json1 == null){
                logmsg=LocaleMessage.get("message.system.errorMessage");
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
            }
            if(!(json1.get("code")!= null && "0".equals(json1.get("code")+""))){
                logmsg=LocaleMessage.get("message.system.errorMessage");
                LoggerCommon.info(this.getClass(),json1.get("msg")+"");
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
            }
            Map api_tickets = (Map)((List)json1.get("tickets")).get(0);
            String api_ticket = api_tickets.get("value").toString();
            Map signMap = AliveCertification.getTencentOcrServiceSingStr(app_id,orderNo,singVersion,api_ticket,nonceStr);
            //String singMsg = signMap.get("msg").toString();
            boolean signFlag = (boolean)signMap.get("flag");
            if(!signFlag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",LocaleMessage.get("message.system.operation.fail"))).body(null);
            }
            String validationSign =  signMap.get("singStr").toString();
            //保存
            pmap.put("appId",app_id);
            pmap.put("faceResultUrl",faceResultUrl);
            pmap.put("singVersion",singVersion);
            pmap.put("bucketName",bucketName);
            pmap.put("endpoint",endpoint);
            pmap.put("accessKeyId",accessKeyId);
            pmap.put("accessKeySecret",accessKeySecret);
            pmap.put("path",path);
            pmap.put("validationSign",validationSign);
            pmap.put("nonce",nonceStr);
            Map resultMap = ocrService.saveCustomerFace(pmap);
            String msg = resultMap.get("msg").toString();
            boolean flag = (boolean)resultMap.get("flag");
            if(flag){
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get(msg),ENTITY_NAME)).body(null);
            }
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",LocaleMessage.get(msg))).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"认证异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

}
