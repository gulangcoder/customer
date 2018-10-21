package cn.fintecher.app.controller.customer;

import cn.fintecher.app.model.customer.CustomerResponse;
import cn.fintecher.app.model.customer.EntContractTemplet;
import cn.fintecher.app.model.customer.EntCustomer;
import cn.fintecher.app.model.customer.EntCustomerInfo;
import cn.fintecher.app.service.customer.CustomerInfoService;
import cn.fintecher.app.service.customer.LoginService;
import cn.fintecher.app.service.customer.RegisterService;
import cn.fintecher.common.constant.AppConstant;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.redis.RedisKeyConstants;
import cn.fintecher.util.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/17
 * @Description:
 */
@RestController
@RequestMapping("/api/registerAndLogin")
@Api(value = "客户注册登录", description = "客户注册登录")
public class RegisterAndLoginController {

    private static final String ENTITY_NAME = "register";

    private static final String LOGIN_NAME = "login";

    @Autowired
    private RegisterService registerService;

    @Autowired
    private LoginService loginService;

    @Value("${sessionTimeout}")
    private String sessionTimeout;
    @Autowired
    private CustomerInfoService customerInfoService;

    /**
     * @description: 获取协议
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/20
     */
    @GetMapping("/getProtocol")
    @ApiOperation(value = "获取协议",httpMethod = "GET", notes = "获取协议")
    public ResponseEntity getProtocol(@RequestParam String contractType,@RequestParam String companyCode){
        LoggerCommon.info(this.getClass(),"获取协议");
        String localeTipMsg =null;
        Map map = new HashMap();
        try {
            map.put("contractType",contractType);
            map.put("companyCode",companyCode);
            List<EntContractTemplet> list = registerService.getRegisterProtocol(map);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.successMessage"),ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(list));
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取协议异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
    /**
     * @description: 注册
     * @param: product
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    @PostMapping("/doRegister")
    @ApiOperation(value = "注册",httpMethod = "POST", notes = "注册")
    public ResponseEntity doRegister(@RequestBody Map map,HttpServletRequest request){
        LoggerCommon.info(this.getClass(),"注册");
        HttpSession session = request.getSession();
        String localeTipMsg =null;
        try {
            //校验短信验证码是否正确
            String code = map.get("code").toString();
            String sessionId = map.get("sessionId").toString();
            String redisCode = RedisUtil.get(RedisKeyConstants.SMS_CODE+sessionId);
            if(!code.equals(redisCode)){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.code.error"))).body(null);
            }
            //校验手机号是否已注册
            List<EntCustomer> customerList = registerService.selectCustomerByMap(map);
            if(customerList != null && customerList.size() > 0){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.phone.register"))).body(null);
            }
            //保存客户注册信息
            Map resultMap = registerService.saveCustomer(map);
            String msg = resultMap.get("msg").toString();
            String row = resultMap.get("row").toString();
            Integer addRow = Integer.valueOf(row);
            if (addRow != 1) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get(msg))).body(null);
            }
            //存入Redis
            String loginIp =request.getRemoteAddr();
            String custId = resultMap.get("custId").toString();
            String companyCode = resultMap.get("companyCode").toString();
            UserInfo userInfo = new UserInfo();
            userInfo.setTel(map.get("phone").toString());
            userInfo.setUserId(custId);
            userInfo.setCompanyCode(companyCode);
            userInfo.setAppAlias(map.get("deviceId")==null?null:map.get("deviceId").toString());
            //UserContextUtil.setUserInfo(UserContextUtil.getSession().getId(),userInfo);
            userInfo.setUserType(3);
            UserContextUtil.setUserInfo(session.getId(),userInfo);
            CustomerResponse response = new CustomerResponse();
            response.setToken(UserContextUtil.getSession().getId());
            response.setUserInfo(userInfo);
            LocaleMessage.setLocale(response.getToken());
            RedisUtil.set(RedisKeyConstants.LOGIN_PREFIX + companyCode + "-" + custId, loginIp, Integer.parseInt(sessionTimeout));
            LoggerCommon.info(this.getClass(),"注册成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get(msg),ENTITY_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(response));
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.register.fail");
            LoggerCommon.info(this.getClass(),"注册异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @PostMapping("/doLogin")
    @ApiOperation(value = "登录",httpMethod = "POST", notes = "登录")
    public ResponseEntity doLogin(@RequestBody Map map,HttpServletRequest request){
        LoggerCommon.info(this.getClass(),"登录");
        HttpSession session = request.getSession();
        String localeTipMsg =null;
        try {
            Map resultMap = loginService.login(map);
            boolean flag = (boolean)resultMap.get("flag");
            String msg = resultMap.get("msg").toString();
            if(!flag){
                String returnMsg = LocaleMessage.get(msg);
                if(null!=resultMap.get("count")){
                    String count = resultMap.get("count").toString();
                    returnMsg = returnMsg.replace("{count}",count);
                }
                if(null!=resultMap.get("time")){
                    String time = resultMap.get("time").toString();
                    returnMsg = returnMsg.replace("{time}",time);
                }
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(LOGIN_NAME, "system.server.exception", returnMsg)).body(null);
            }
            //登录成功后校验用户是否更换设备登录的
            String custId = resultMap.get("custId")==null?null:resultMap.get("custId").toString();
            if (custId!=null&&!"".equals(custId)){
                EntCustomerInfo entCustomerInfo = customerInfoService.getCustomerInfoById(custId);
                String deviceId = map.get("deviceId")==null?null:map.get("deviceId").toString();
                Map update_map = new HashMap();
                update_map.put("appAlias",deviceId);
                update_map.put("custId",custId);
                if (deviceId!=null&&!"".equals(deviceId)){
                    if (entCustomerInfo.getAppAlias()==null||"".equals(entCustomerInfo.getAppAlias())){
                        customerInfoService.updateCustomerAppAliasById(update_map);
                    }
                    if (entCustomerInfo.getAppAlias()!=null&&!"".equals(entCustomerInfo.getAppAlias())
                            &&!entCustomerInfo.getAppAlias().equals(deviceId)){
                        customerInfoService.updateCustomerAppAliasById(update_map);
                    }
                }
            }
            //存入Redis
            String loginIp =request.getRemoteAddr();
            String companyCode = resultMap.get("companyCode").toString();
            String tel = String.valueOf(resultMap.get("phone"));
            UserInfo userInfo = new UserInfo();
            userInfo.setTel(tel);
            userInfo.setUserId(custId);
            userInfo.setCompanyCode(companyCode);
            userInfo.setAppAlias(map.get("deviceId")==null?null:map.get("deviceId").toString());
            //UserContextUtil.setUserInfo(UserContextUtil.getSession().getId(),userInfo);
            userInfo.setUserType(3);
            UserContextUtil.setUserInfo(session.getId(),userInfo);
            CustomerResponse response = new CustomerResponse();
            response.setToken(UserContextUtil.getSession().getId());
            System.out.println("app login:"+UserContextUtil.getSession().getId());
            response.setUserInfo(userInfo);
            LocaleMessage.setLocale(response.getToken());
            RedisUtil.set(RedisKeyConstants.LOGIN_PREFIX + companyCode + "-" + custId, loginIp, Integer.parseInt(sessionTimeout));
            LoggerCommon.info(this.getClass(),"登录成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get(msg),LOGIN_NAME)).body(ResponseLogMessageHandel.initSuccessResponseData(response));
        } catch (Exception e) {
           localeTipMsg = LocaleMessage.get("message.login.fail");
            LoggerCommon.info(this.getClass(),"登录异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 退出
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/20
     */
    @ApiOperation(value = "退出",httpMethod = "POST", notes = "退出")
    @PostMapping("/logout")
    public ResponseEntity logout(){
        UserInfo userInfo = UserContextUtil.getUserInfo();
        if (userInfo != null) {
            RedisUtil.del(RedisKeyConstants.LOGIN_PREFIX  + userInfo.getCompanyCode() + "-" + userInfo.getUserId());
            RedisUtil.del(AppConstant.APP_USER_INFO + UserContextUtil.getUserTokenId());
        }
        return ResponseEntity.ok().body(null);
    }
}
