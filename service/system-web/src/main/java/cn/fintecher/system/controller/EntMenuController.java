package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.EntMenu;
import cn.fintecher.system.model.UserDataPermission;
import cn.fintecher.system.service.EntMenuService;
import cn.fintecher.system.service.UserDataPermissionService;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//cn.fintecher.common.userinfo;


/**
 * Title :
 * Description : 获取系统菜单功能
 * Create on : 2018年06月11日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:zhangtao
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@RestController
@RequestMapping("/api/entMenu")
@Api(value = "/api/entMenu", description = "系统菜单")
public class EntMenuController {
    @Autowired
    private EntMenuService entMenuService;
    @Autowired
    private UserDataPermissionService userDataPermissionService;

    private static final String ENTITY_NAME = "entMenu";
    /**
     * 获取企业模块对应下的菜单
     * @param entModuleCode 企业模块code
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "获取企业模块对应下的菜单", notes = "获取企业模块对应下的菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "entModuleCode", value = "企业模块code", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getEntMenuByCode")
    public ResponseEntity getEntMenuByCode(@RequestParam String entModuleCode) {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String companyCode = userInfo.getCompanyCode();
        String userId = userInfo.getUserId();
        try {
            Map returnMap = entMenuService.getEntMenuByEntCode(userId, entModuleCode, companyCode);
            userInfo.setDataPermission((Map<String, Map<String, String>>) returnMap.get("dataRoleList"));
            userInfo.setPermissionList((List<String>) returnMap.get("permissionList"));
            UserContextUtil.setUserInfo(userInfo);
            Map object = new HashMap();
            object.put("menuList", returnMap.get("menuList"));
            object.put("allBtnIds", returnMap.get("allBtnIds"));
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(object));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取企业模块对应下的菜单异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 获取菜单树的集合
     *
     * @return
     */
    @GetMapping("/getAllMenuTree")
    @ApiOperation(value = "获取菜单树的集合", notes = "获取菜单树的集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysCode", value = "sysCode", dataType = "String", paramType = "query", required = true),
    })
    public ResponseEntity getAllMenuTree(@RequestParam String sysCode) {
        try {
            List list = entMenuService.getAllMenuTree(sysCode);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
        }catch(Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取菜单树的集合异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/25 16:14
     * @Params: parentId：父id
     * @Params: entMenuId：ent_menu的id
     * @Params: menuId：ent_menu的sys_menu_code
     * @Params: roleId：角色id
     * @Params: userId：用户id
     * @Params: sysCode：系统code（ent_menu的sys_code）
     * @Description: 获取菜单详情
     * @return:
     */
    @GetMapping("/getMenuDetail")
    @ApiOperation(value = "获取菜单详情", notes = "获取菜单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父id", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "entMenuId", value = "ent_menu的id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "menuId", value = "ent_menu的sys_menu_code", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "roleId", value = "角色id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sysCode", value = "系统code", dataType = "String", paramType = "query", required = true),
    })
    public ResponseEntity getMenuDetail(@RequestParam(required = false) String parentId,
                                        @RequestParam String entMenuId,
                                        @RequestParam  String menuId,
                                        @RequestParam  String roleId,
                                        @RequestParam  String userId,
                                        @RequestParam  String sysCode) {
        List<EntMenu> list = new ArrayList();
        List retList = new ArrayList();
        try {
            if(StringUtil.isEmpty(parentId)){//如果是一级菜单查子集
                    list = entMenuService.getSubMenuList(menuId, sysCode);
                if(!ObjectUtils.isEmpty(list)){
                    for (int i = 0; i < list.size(); i++) {
                        String subEntMenuId = list.get(i).getId();
                        String subMenuId = list.get(i).getSysMenuCode();
                        Map map = getMunu(subEntMenuId, userId, subMenuId, roleId, sysCode);
                        if(map!=null){
                            retList.add(map);
                        }
                    }
                }
            }else{//否则查自己
                Map map = getMunu(entMenuId, userId, menuId, roleId, sysCode);
                retList.add(map);
            }
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取菜单详情异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(retList));
    }

    public Map getMunu(String entMenuId, String userId, String menuId, String roleId, String sysCode)throws Exception{
        Map map = new HashMap();
        UserDataPermission userDataPermission = new UserDataPermission();
        userDataPermission.setMenuId(entMenuId);
        userDataPermission.setUserId(userId);
        String id = null;
        try {
            id = userDataPermissionService.getIdByUserIdAndMenuId(userDataPermission);
        }catch (Exception e){
            throw e;
        }
        if(StringUtil.isEmpty(id)){
            userId = "";
        }
        try {
            map = entMenuService.getMenuDetail(menuId, roleId, userId, sysCode);
        }catch (Exception e){
            throw e;
        }
        return map;
    }
}
