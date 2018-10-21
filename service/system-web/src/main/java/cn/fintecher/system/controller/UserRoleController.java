package cn.fintecher.system.controller;


import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.service.UserRoleService;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Title :
 * Description : 用户角色管理
 * Create on : 2018年05月24日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username: wangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@RestController
@RequestMapping("/api/userRole")
@Api(value = "/api/userRole", description = "用户角色管理")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/addUserRole")
    @ApiOperation(value = "添加角色权限", notes = "添加角色权限")
    //data{"userId":,"roleIds":[1,2,3]}
    public ResponseEntity addUserRole(@RequestBody Map data){
        if(null==data.get("userId")||StringUtil.isEmpty(data.get("userId").toString())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("message.system.request.param.exception",ENTITY_NAME)).body(null);
        }
        String[]roleIds= (String[]) data.get("roleIds");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        try {
            userRoleService.addUserRole(data.get("userId").toString(), userInfo.getAccount(), roleIds);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.operation.success"), ENTITY_NAME)).body(null);
        }catch (Exception e){
            String logmsg=LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"添加角色权限异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}
