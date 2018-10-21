package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.Company;
import cn.fintecher.system.service.CompanyService;
import cn.fintecher.util.HeaderUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : 企业管理
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
@RequestMapping("/api/company")
@Api(value = "企业管理", description = "企业管理")
public class CompanyController {
    private static final String ENTITY_NAME = "company";

    @Autowired
    private CompanyService companyService;


    /**
     * @Author: wangtao
     * @Date: 2018/05/24 15:02
     * @Params: map
     *            分页参数及查询参数
     * @Description: 获取企业列表
     * @return: ResultVO
     */
    @GetMapping("/getCompanyList" )
    @ApiOperation(value = "企业列表", httpMethod = "GET",notes = "企业列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "companyName", value = "企业名称", dataType = "String", paramType = "query", required = true),
    })
    public ResponseEntity getCompanyList(@RequestParam(value = "pageSize") Integer pageSize,
                                         @RequestParam(value = "pageIndex") Integer pageIndex,
                                         @RequestParam(value = "companyName") String companyName){
        LoggerCommon.info(this.getClass(),"查询企业列表");
        Map map = new HashMap();
        map.put("companyName",companyName);
        PageHelper.startPage(pageIndex, pageSize, true);
        List<Company> list = null;
        PageInfo pageInfo = null;
        try {
            list = companyService.getCompanyList(map);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询企业列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"成功查询企业列表");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 18:33
     * @Params:
     * @Description: 获取所有启用的企业
     * @return: ResultVO
     */
    @GetMapping("/getAllCompanyList")
    @ApiOperation(value = "获取所有启用的企业", httpMethod = "GET",notes = "获取所有启用的企业")
    public ResponseEntity getAllCompanyList(){
        LoggerCommon.info(this.getClass(),"获取所有启用的企业");
        List<Company> list = null;
        try {
            list = companyService.getAllCompanyList();
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取所有启用的企业异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取所有启用的企业成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 11:03
     * @Params: company
     *              企业实体
     * @Description: 添加企业
     * @return: ResultVO
     */
    @PostMapping("/addCompany")
    @ApiOperation(value = "添加企业",httpMethod = "POST", notes = "添加企业")
    public ResponseEntity addCompany(@RequestBody Company company) {
        LoggerCommon.info(this.getClass(),"添加企业");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String createUser = userInfo.getRealName();
        company.setCreateUser(createUser);
        Map map = null;
        String localeTipMsg =null;
        try {
            map = companyService.addCompany(company);
            boolean flag =(Boolean) map.get("flag");
            localeTipMsg = LocaleMessage.get(map.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"添加企业成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg)).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"添加企业异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }

    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 15:03
     * @Params: company
     *              企业实体
     * @Description: 修改企业信息
     * @return: ResultVO
     */
    @PostMapping("/updateCompany")
    @ApiOperation(value = "修改企业信息",httpMethod = "POST", notes = "修改企业信息")
    public ResponseEntity updateCompany(@RequestBody Company company){
        LoggerCommon.info(this.getClass(),"修改企业信息");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String updateUser = userInfo.getRealName();
        company.setUpdateUser(updateUser);
        Map map = null;
        String localeTipMsg =null;
        try {
            map = companyService.updateCompany(company);
            boolean flag =(Boolean) map.get("flag");
            localeTipMsg = LocaleMessage.get(map.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"修改企业信息成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg)).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改企业信息异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 13:54
     * @Params: companyCode
     *              企业code
     *@Params: status（1.启用，0.停用）
     *             状态
     * @Description:
     * @return: ResultVO
     */
    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改企业状态",httpMethod = "POST", notes = "修改企业状态")
    public ResponseEntity updateStatus(@RequestParam("status") Short status,@RequestParam("companyCode") String companyCode){
        LoggerCommon.info(this.getClass(),"修改企业状态");
        Company company = new Company();
        company.setCompanyCode(companyCode);
        company.setStatus(status);
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String updateUser = userInfo.getRealName();
        company.setUpdateUser(updateUser);
        Map map = null;
        String localeTipMsg =null;
        try {
            map = companyService.updateCompany(company);
            boolean flag =(Boolean) map.get("flag");
            localeTipMsg = LocaleMessage.get(map.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"修改企业状态成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg)).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改企业信息异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @Author: wangtao
     * @Date: 2018/05/24 15:19
     * @Params: companyCode
     *              公司code
     * @Description: 获取企业详情
     * @return:
     */
    @PostMapping("/getCompanyById")
    @ApiOperation(value = "获取企业详情",httpMethod = "POST", notes = "获取企业详情")
    public ResponseEntity getCompanyById(@RequestParam("companyCode")String companyCode){
        LoggerCommon.info(this.getClass(),"获取企业详情");
        Company company = null;
        String localeTipMsg =null;
        try {
            company = companyService.getCompanyById(companyCode);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取企业详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取企业详情成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(company));
    }
}
