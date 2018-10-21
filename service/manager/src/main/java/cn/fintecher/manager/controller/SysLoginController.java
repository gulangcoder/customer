package cn.fintecher.manager.controller;

import cn.fintecher.common.constant.AppConstant;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseInfo;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.manager.model.UserLoginRequest;
import cn.fintecher.manager.model.UserLoginResponse;
import cn.fintecher.manager.service.ILoginService;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.StringUtil;
import cn.fintecher.util.redis.RedisKeyConstants;
import cn.fintecher.util.redis.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年05月22日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:zh-pc
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@RestController
@RequestMapping("/api/syslogin")
@Api(value = "登录相关", description = "登陆相关")
public class SysLoginController {
    private static final String ENTITY_NAME = "syslogin";

    @Value("${duplicateLogin}")
    private String duplicateLogin;
    @Value("${sessionTimeout}")
    private String sessionTimeout;
    @Autowired
    private ILoginService loginService;

//    @GetMapping("/queryUserPermission")
//    @ApiOperation(value="获取用户权限url",notes = "获取用户权限url")
//    public ResponseEntity<Object> queryUserPermission(@RequestParam @ApiParam("序列名称") String userId)throws Exception{
//        Map<String, Object> returnMap = loginService.getRoleList(userId);
//        Object object = returnMap.get("permissionList");
//        return new ResponseEntity<>(object, HttpStatus.OK);
//    }


    @GetMapping("/index")
    @ApiOperation(value = "加载系统菜单", notes = "加载系统菜单")
    public ResponseEntity index(HttpServletRequest request) {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String userId = userInfo.getUserId();

        Map<String, Object> returnMap =null;
        try {
            returnMap=loginService.getRoleList(userId);
            loginService.updateLoginInfo(userId, UserContextUtil.getHttpServletRequest().getRemoteAddr());
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"加载系统菜单异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        userInfo.setDataPermission((Map<String, Map<String, String>>) returnMap.get("dataRoleList"));
        userInfo.setPermissionList((java.util.List<String>) returnMap.get("permissionList"));
        UserContextUtil.setUserInfo(userInfo);
        UserLoginResponse response = new UserLoginResponse();
        Map<String,Object> data = new HashMap<>();
        data.put("menuList",returnMap.get("menuList"));
        response.setData(data);
        response.setToken(UserContextUtil.getSession().getId());
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(response));
    }

    @PostMapping("/syslogin")
    @ApiOperation(value = "系统用户登陆", notes = "系统用户登陆")
    public ResponseEntity doLogin(@RequestBody UserLoginRequest loginRequest, HttpServletRequest request) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        HttpSession session = request.getSession();
        Map<String, String> returnMap=null;
        String localeTipMsg=null;
        try {
            returnMap = loginService.login(username.trim(), password.trim());
        }catch (Exception e){
            localeTipMsg=LocaleMessage.get("system.server.exception");
            LoggerCommon.info(this.getClass(),"系统用户登陆异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg))
                    .body(ResponseLogMessageHandel.initExceptionResponseData());
        }
        localeTipMsg = LocaleMessage.get(returnMap.get("message"));
        if ("0".equals(returnMap.get("code"))) {
            UserContextUtil.setAttribute("message", returnMap.get("message"));
            LoggerCommon.info(this.getClass(),"登录失败："+localeTipMsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "password error", localeTipMsg))
                    .body(ResponseLogMessageHandel.initExceptionResponseData());
        } else {
            String lastLoginIp = RedisUtil.get(RedisKeyConstants.LOGIN_PREFIX + username);
            String loginIp = request.getRemoteAddr();
            // 獲取配置文件中重複登錄是否開啟 1 表示關閉  0 表示開啟
            //String duplicateLogin = PropertyConfigurerUtil.getContextProperty("duplicateLogin");
            if ("0".equals(duplicateLogin) && lastLoginIp != null && !loginIp.equals(lastLoginIp)) {
                UserContextUtil.setAttribute("message", "用户已登录!");
                LoggerCommon.info(this.getClass(),"登录失败："+localeTipMsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "The user has logining", localeTipMsg)).body(
                        ResponseLogMessageHandel.initExceptionResponseData()
                );
            }
            UserInfo userInfo = new UserInfo();
            userInfo.setAccount(username);
            userInfo.setOrgId(returnMap.get("org_id"));
            userInfo.setRealName(returnMap.get("real_name"));
            userInfo.setUserId(returnMap.get("user_id"));
            userInfo.setUserType(1);

            UserContextUtil.setUserInfo(session.getId(),userInfo);
            System.out.println("set user key:"+session.getId());
//            UserContextUtil.setUserInfo(userInfo);
            //查询用户角色
            Map<String, Object> roleInfo = null;
            try {
                roleInfo= loginService.getUserRoleInfo(userInfo.getUserId());
            }catch (Exception e){
                localeTipMsg=LocaleMessage.get("system.server.exception");
                LoggerCommon.info(this.getClass(),"系统用户登陆查询用户角色异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg))
                        .body(ResponseLogMessageHandel.initExceptionResponseData());
            }
            if(ObjectUtils.isEmpty(roleInfo)){
                UserContextUtil.setAttribute("message", "用户未分配角色，请联系管理员！");
                localeTipMsg = LocaleMessage.get("message.user.not.assigned.role");
                LoggerCommon.info(this.getClass(),"登录失败："+localeTipMsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "The user have logined", localeTipMsg)).body(
                        ResponseLogMessageHandel.initExceptionResponseData()
                );
            }else if(1 != Short.valueOf(roleInfo.get("status").toString())){
                UserContextUtil.setAttribute("message", "用户角色已停用，请联系管理员！");
               localeTipMsg = LocaleMessage.get("message.user.not.assigned.role");
                LoggerCommon.info(this.getClass(),"登录失败："+localeTipMsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "The user have logined", localeTipMsg)).body(
                        ResponseLogMessageHandel.initExceptionResponseData()
                );
            }
            UserLoginResponse response = new UserLoginResponse();
            response.setUserInfo(userInfo);

            response.setToken(session.getId());
            UserContextUtil.setAttribute("roleName", roleInfo.get("roleName"));
            RedisUtil.set(RedisKeyConstants.LOGIN_PREFIX + username, loginIp, Integer.parseInt(sessionTimeout));
            LocaleMessage.setLocale(session.getId());
            LoggerCommon.info(this.getClass(),"登录成功");
            System.out.println("login result:"+response.getToken());
            return ResponseEntity.ok().body(
                    ResponseLogMessageHandel.initSuccessResponseData(response)
            );
        }
    }


    /**
     * 退出
     *
     * @return
     * @throws IOException
     */
    @ApiOperation(value = "退出",httpMethod = "POST", notes = "退出")
    @PostMapping("/logout")
    public ResponseEntity logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        if (UserContextUtil.getUserInfo() != null) {
            RedisUtil.del(RedisKeyConstants.LOGIN_PREFIX + UserContextUtil.getUserInfo().getAccount());
            RedisUtil.del(AppConstant.APP_USER_INFO + UserContextUtil.getUserTokenId());
        }
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/loadindex")
    @ApiOperation(value = "测试多语言响应", notes = "测试多语言响应")
    public ResponseEntity loadindex(HttpServletRequest request,String userId){
        LoggerCommon.info(this.getClass(),"测试多语言响应");
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        try {
//            double d = 1/0;
//            responseInfo.setResponseBody(list);
        }catch (Exception e){
            String localeMsg = LocaleMessage.get("message.system.errorMessage");
            LoggerCommon.info(this.getClass(),"测试多语言响应异常信息："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"The user have logined",
                    localeMsg)).body(
                    ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(),ExceptionEnum.TIME_OUT_EXCEPTION_CODE.code(),
                            ExceptionEnum.TIME_OUT_EXCEPTION_CODE.description(),ExceptionStackMessage.collectExceptionStackMsg(e),null)
            );
        }

        String localeMsg = LocaleMessage.get("message.system.successMessage");
        LoggerCommon.info(this.getClass(),"测试多语言响应成功");
        return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeMsg, ENTITY_NAME)).body(list);
    }



    /**
     * 修改密码
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/updateUserPwd")
    public ResponseEntity updateUserPwd(@RequestBody Map data){
        if(null==data.get("oldPwd")||StringUtil.isEmpty(data.get("oldPwd").toString())){
            return ResponseEntity.badRequest().header("旧密码不可为空",ENTITY_NAME).body(null);
        }
        if(null==data.get("newPwd")||StringUtil.isEmpty(data.get("newPwd").toString())){
            return ResponseEntity.badRequest().header("新密码不可为空",ENTITY_NAME).body(null);
        }
        String newPwd= data.get("newPwd").toString();
        String oldPwd= data.get("oldPwd").toString();
        String logmsg = null;
        try {
            Map<String, Object> respMap = loginService.updateUserPwd(UserContextUtil.getUserInfo().getAccount(), oldPwd, newPwd);
            boolean flag = (boolean) respMap.get("flag");
            logmsg=LocaleMessage.get(respMap.get("msg").toString());
            if (flag) {
                LoggerCommon.info(this.getClass(),"修改密码成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            } else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            }
        }catch (Exception e){
            logmsg=LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改密码异常信息："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"The user have logined",
                    logmsg)).body(ResponseLogMessageHandel.initExceptionDataResponse(e));
        }
    }
}
