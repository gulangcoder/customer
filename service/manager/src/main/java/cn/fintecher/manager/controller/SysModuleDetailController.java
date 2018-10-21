package cn.fintecher.manager.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.manager.model.SysModuleDetail;
import cn.fintecher.manager.service.SysModuleDetailService;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @系统模块菜单管理@
 * Create on : 2018年06月14日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:hanxiaoxue
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@RestController
@RequestMapping("/api/sysModuleDetail")
@Api(value = "/api/sysModuleDetail", description = "系统模块菜单管理")
public class SysModuleDetailController {

    private static final String ENTITY_NAME = "sysModuleDetail";

    @Resource
    private SysModuleDetailService sysModuleDetailService;


//    @ApiOperation(value = "根据模块主键跳转到对应的页面", httpMethod = "GET", notes = "根据模块主键跳转到对应的页面")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "sysCode", value = "模块主键", dataType = "String", paramType = "query", required = true),
//    })
//    @GetMapping("/toMenuPage")
//    public String toPage(@RequestParam String sysCode){
//        HttpServletRequest request= UserContextUtil.getHttpServletRequest();
//        request.setAttribute("sysCode",sysCode);
//        return "MenuManage";
//    }

//    @GetMapping("/menuIcon")
//    @ApiOperation(value = "字体图标页面", httpMethod = "GET", notes = "字体图标页面")
//    public String menuIcon() {
//        return "FontAwesome";
//    }

    @GetMapping("/getAllMenuDetailList")
    @ApiOperation(value = "获取菜单列表详情", httpMethod = "GET", notes = "获取菜单列表详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sysCode", value = "模块主键", dataType = "String", paramType = "query", required = true)
    })
    public ResponseEntity getAllMenuDetailList(@RequestParam String id,@RequestParam String sysCode) {
        SysModuleDetail moduleDetail = new SysModuleDetail();
        moduleDetail.setId(id);
        moduleDetail.setSysCode(sysCode);
        try {
            List<SysModuleDetail> list = sysModuleDetailService.getAllMenuDetailList(moduleDetail);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
        }catch (Exception e){
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取菜单列表详情异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "根据id查询编辑回显信息", httpMethod = "GET", notes = "根据id查询编辑回显信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "平台系统菜单映射表id", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping("/getMenuById")
    public ResponseEntity getMenuById(@RequestParam String id) {
        try {
            SysModuleDetail moduleDetail = sysModuleDetailService.getMenuById(id);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(moduleDetail));
        }catch (Exception e){
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取菜单列表详情异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }

    }

    @ApiOperation(value = "获取菜单树的集合", httpMethod = "GET", notes = "获取菜单树的集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sysCode", value = "模块主键", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping("/getAllMenuTree")
    public ResponseEntity getAllMenuTree(@RequestParam String sysCode) {
        try {
            List<Map> list = sysModuleDetailService.getAllMenuTree(sysCode);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
        }catch (Exception e){
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取菜单树的集合异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 获取所有的一级菜单
     *@param flag
     *  区分是按钮改变父级菜单还是二级菜单改变菜单
     * @return
     */
    @ApiOperation(value = "获取父级菜单", httpMethod = "GET", notes = "获取父级菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "flag", value = "标识,1查询一级菜单 ,2查询二级菜单", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "sysCode", value = "模块主键", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping("/getOneMenu")
    public ResponseEntity getOneMenu(@RequestParam String flag,@RequestParam String sysCode) {
        try {
            List<SysModuleDetail> list = sysModuleDetailService.getOneMenu(flag, sysCode);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
        }catch (Exception e){
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取父级菜单异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 查询改菜单下是否有未启用和启用的按钮,全部启用或者停用
     *@param
     *  id 菜单id
     *  flag 状态
     * @return
     */
    @ApiOperation(value = "查询改菜单下是否有未启用和启用的按钮,全部启用或者停用", httpMethod = "GET",
            notes = "查询改菜单下是否有未启用和启用的按钮,全部启用或者停用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "平台系统菜单映射表id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "flag", value = "标识,0停用,1启用", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping("/checkMenuChildStatus")
    public ResponseEntity checkMenuChildStatus(@RequestParam String id,@RequestParam String flag) {
        try {
            Map map = sysModuleDetailService.checkMenuChildStatus(id, flag);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
        }catch (Exception e){
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询改菜单下是否有未启用和启用的按钮异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 增加菜单
     *
     * @param menu
     * @return
     */
    @PostMapping("/addMenu")
    @ApiOperation(value = "增加菜单", httpMethod = "POST",notes = "增加菜单")
    public ResponseEntity addMenu(@RequestBody SysModuleDetail menu) {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        //创建人
        menu.setCreateUser(userInfo.getAccount());
        menu.setUpdateUser(userInfo.getAccount());
        String localeTipMsg=null;
        try {
            Map resultMap = sysModuleDetailService.addMenu(menu);
            localeTipMsg=LocaleMessage.get(resultMap.get("msg").toString());
            if (!(boolean) resultMap.get("flag")) {
                LoggerCommon.info(this.getClass(),"增加菜单失败："+localeTipMsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }
            LoggerCommon.info(this.getClass(),"增加菜单成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(resultMap);
        }catch (Exception e){
            localeTipMsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"增加菜单异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }

    }

    /**
     * 编辑菜单
     *
     * @param menu
     * @return
     */
    @PostMapping("/editMenu")
    @ApiOperation(value = "编辑菜单", httpMethod = "POST",notes = "编辑菜单")
    public ResponseEntity editMenu(@RequestBody SysModuleDetail menu) {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        //创建人
        menu.setUpdateUser(userInfo.getAccount());
        String localeTipMsg=null;
        try {
            Map resultMap = sysModuleDetailService.editMenu(menu);
            localeTipMsg=LocaleMessage.get(String.valueOf(resultMap.get("msg")));
            if (!(boolean) resultMap.get("flag")) {
                LoggerCommon.info(this.getClass(),"编辑菜单失败："+localeTipMsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(null);
            }
            LoggerCommon.info(this.getClass(),"编辑菜单成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(resultMap);
        }catch (Exception e){
            localeTipMsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"编辑菜单异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 启用,停用菜单
     *
     * @param moduleDetail {status 状态 0,停用,1启用 id 节点id}
     * @return
     */
    @PostMapping("/updateMenuStatus")
    @ApiOperation(value = "启用,停用菜单", httpMethod = "POST",notes = "启用,停用菜单")
    public ResponseEntity updateMenuStatus(@RequestBody SysModuleDetail moduleDetail) {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        //创建人
        moduleDetail.setCreateUser(userInfo.getAccount());
        moduleDetail.setUpdateTime(new Date());
        String localeTipMsg=null;
        try {
            Map resultMap = sysModuleDetailService.updateMenuStatus(moduleDetail);
            localeTipMsg=LocaleMessage.get(resultMap.get("msg").toString());
            if (!(boolean) resultMap.get("flag")) {
                LoggerCommon.info(this.getClass(),"操作菜单状态失败："+localeTipMsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(null);
            }
            LoggerCommon.info(this.getClass(),"操作菜单状态成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(null);
        }catch (Exception e){
            localeTipMsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"启用,停用菜单异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}