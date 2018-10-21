package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseInfo;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.sms.ClMessageAccount;
import cn.fintecher.common.start.cache.AbsCacheLoader;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.SysOtherConfig;
import cn.fintecher.system.service.SysEntOtherConfigService;
import cn.fintecher.system.service.SysOtherConfigService;
import cn.fintecher.system.service.SysParaService;
import cn.fintecher.system.start.SysParaCacheLoader;
import cn.fintecher.util.HeaderUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Title :
 * Description : @系统参数配置@
 * Create on : 2018年07月18日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */

@RestController
@RequestMapping("/api/sysOtherConfig")
@Api(value = "/api/sysOtherConfig", description = "系统参数配置")
public class SysOtherConfigController {

    @Autowired
    private SysParaService sysParaService;

    @Autowired
    private SysOtherConfigService otherConfigService;

    @Autowired
    private SysEntOtherConfigService entOtherConfigService;

    /*@Autowired
    private AbsCacheLoader absCacheLoader;*/
    @Autowired
    @Qualifier("sysParaCacheLoader")
    private SysParaCacheLoader sysParaCacheLoader;


    @ApiOperation(value = "查询当前企业三方数据", notes = "查询当前企业三方数据")
    @GetMapping("/getCompnaySysPara")
    public ResponseEntity getCompnaySysPara(){
        String companyCode= UserContextUtil.getUserInfo().getCompanyCode();

        List<Map> paraList=null;
        List<Map> columns =null;
        List<SysOtherConfig> otherConfigList =null;
        //查询sys_ent_other_config 企业三方关联 数据
        List<Map> list = new ArrayList<>();
        try {
            //查询sys_para表
            paraList = sysParaService.getSysParaByCompanyCode(companyCode);
            //查询sys_other_columns_conf表未存入sys_para的字段
            columns= entOtherConfigService.findNotInSysParaList(companyCode);
            //查询企业相关三方配置，页面只展示企业配置的关联三方
            otherConfigList=otherConfigService.getOtherConfigByCompanyCode(companyCode);
        }catch(Exception e){
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询当前企业三方数据异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        if(otherConfigList!=null&&otherConfigList.size()>0){
            for (SysOtherConfig conf:otherConfigList) {
                Map otMap = new HashMap();
                otMap.put("companyCode", companyCode);
                otMap.put("otherId", conf.getId());
                otMap.put("otherName", conf.getOtherName());
                otMap.put("type", conf.getType());
                List<Map> otList = new ArrayList<>();
                for (Map para : paraList) {
                    if (String.valueOf(para.get("otherId")).equals(conf.getId())) {
                        Map map = new HashMap();
                        map.put("paraName", para.get("paraName"));
                        map.put("paraValue", para.get("paraValue"));
                        map.put("remark", para.get("remark"));
                        map.put("confId",para.get("confId"));
                        otList.add(map);
                    }
                }
                if (otList != null && otList.size() > 0) {
                    otMap.put("paramDetail", otList);
                    list.add(otMap);
                }
            }
            for (SysOtherConfig conf:otherConfigList) {
                Map otMap = new HashMap();
                otMap.put("companyCode", companyCode);
                otMap.put("otherId", conf.getId());
                otMap.put("otherName", conf.getOtherName());
                otMap.put("type", conf.getType());
                List<Map> otList = new ArrayList<>();
                for (Map column : columns) {
                    if (String.valueOf(column.get("otherId")).equals(conf.getId())) {
                        Map map = new HashMap();
                        map.put("paraName", column.get("paraCode"));
                        map.put("remark", column.get("paraName"));
                        map.put("confId",column.get("confId"));
                        otList.add(map);
                    }
                }
                if (otList != null && otList.size() > 0) {
                    otMap.put("paramDetail", otList);
                    list.add(otMap);
                }
            }
        }
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
    }




    /**
     * 获取三方数据
     * */
//    @GetMapping("/getSysPara")
//    @ApiOperation(value = "获取三方数据", notes = "获取三方数据")
//    public ResponseEntity getSysPara(){
//        String companyCode= UserContextUtil.getUserInfo().getCompanyCode();
//        //查询sys_para表
//        List<Map> paraList=sysParaService.getSysParaByCompanyCode(companyCode);
//        //查询sys_other_columns_conf表未存入sys_para的字段
//        List<Map>columns=entOtherConfigService.findNotInSysParaList(companyCode);
//        //查询sys_ent_other_config 企业三方关联 数据
//        List<Map> list = new ArrayList<>();
//        //查询企业相关三方配置，页面只展示企业配置的关联三方
//        List<SysOtherConfig> otherConfigList = otherConfigService.getOtherConfigByCompanyCode(companyCode);
//        if(otherConfigList!=null&&otherConfigList.size()>0){
//            for (SysOtherConfig conf:otherConfigList) {
//                Map otMap = new HashMap();
//                otMap.put("companyCode", companyCode);
//                otMap.put("otherId", conf.getId());
//                otMap.put("otherName", conf.getOtherName());
//                otMap.put("type", conf.getType());
//                List<Map> otList = new ArrayList<>();
//                for (Map para : paraList) {
//                    if (String.valueOf(para.get("otherId")).equals(conf.getId())) {
//                        Map map = new HashMap();
//                        map.put("paraName", para.get("paraName"));
//                        map.put("paraValue", para.get("paraValue"));
//                        map.put("remark", para.get("remark"));
//                        map.put("confId",para.get("confId"));
//                        otList.add(map);
//                    }
//                }
//                if (otList != null && otList.size() > 0) {
//                    otMap.put("paramDetail", otList);
//                    list.add(otMap);
//                }
//            }
//            for (SysOtherConfig conf:otherConfigList) {
//                Map otMap = new HashMap();
//                otMap.put("companyCode", companyCode);
//                otMap.put("otherId", conf.getId());
//                otMap.put("otherName", conf.getOtherName());
//                otMap.put("type", conf.getType());
//                List<Map> otList = new ArrayList<>();
//                for (Map column : columns) {
//                    if (String.valueOf(column.get("otherId")).equals(conf.getId())) {
//                        Map map = new HashMap();
//                        map.put("paraName", column.get("paraCode"));
//                        map.put("remark", column.get("paraName"));
//                        map.put("confId",column.get("confId"));
//                        otList.add(map);
//                    }
//                }
//                if (otList != null && otList.size() > 0) {
//                    otMap.put("paramDetail", otList);
//                    list.add(otMap);
//                }
//            }
//        }
//        UserContextUtil.getHttpServletRequest().setAttribute("list",list);
//        return ResponseEntity.ok().body(list);
//    }


    /**
     * 保存三方配置数据
     * @param data{"otherId":"配置id","companyCode":"企业编码","paraList":[{"paraName":"参数名称","remark":""参数注释,"confId","参数id","paraValue","参数值"}]}
     * @return
     */
    @PostMapping("/saveSysPara")
    @ApiOperation(value = "保存三方配置数据", notes = "保存三方配置数据")
    public ResponseEntity saveSysPara(@RequestBody Map data){
        UserInfo userInfo = UserContextUtil.getUserInfo();
        data.put("createUser",userInfo.getAccount());
        data.put("upateUser",userInfo.getAccount());
        String localeTipMsg=null;
        try {
            Map resMap = sysParaService.saveSysPara(data);
            boolean flag = (boolean) resMap.get("flag");
            localeTipMsg=LocaleMessage.get(resMap.get("msg").toString());
            if (flag) {
                //刷新缓存
                sysParaCacheLoader.refresh();
                LoggerCommon.info(this.getClass(),"企业保存三方配置数据成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(null);
            } else {
                LoggerCommon.info(this.getClass(),"企业保存三方配置数据失败："+localeTipMsg);
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(null);
            }
        }catch (Exception e){
            localeTipMsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"保存三方配置数据异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @PostMapping("/queryClAccount")
    @ApiOperation(value = "查询创蓝账号数据", notes = "查询创蓝账号数据")
    public ResponseEntity<ResponseInfo> queryClAccount(@RequestBody String companyCode){
        LoggerCommon.info(this.getClass(),"查询创蓝账号");
        String localeTipMsg=null;
        if (companyCode==null&&"".equals(companyCode)){
            localeTipMsg = LocaleMessage.get("message.system.request.param.exception");
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initExceptionResponseData());
        }
        try {
            String account = sysParaService.getParaByCompanyAndKey(companyCode,"chuanglan_code_account");
            String password = sysParaService.getParaByCompanyAndKey(companyCode,"chuanglan_code_pwd");
            String host = sysParaService.getParaByCompanyAndKey(companyCode,"chuanglan_host");
            ClMessageAccount clMessageAccount = new ClMessageAccount();
            clMessageAccount.setAccount(account);
            clMessageAccount.setHost(host);
            clMessageAccount.setPassword(password);
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("message.system.operation.success",
                    localeTipMsg)).body(ResponseLogMessageHandel.initSuccessResponseData(clMessageAccount));
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询创蓝账号数据异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

}