package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.EntPromotion;
import cn.fintecher.system.model.EntPromotionRule;
import cn.fintecher.system.service.ActivityService;
import cn.fintecher.util.DateConversionUtil;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年05月22日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:gq
 * @version 1.0
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@RestController
@RequestMapping("/api/activity")
@Api(value = "活动管理", description = "活动管理")
public class ActivityController {

    private static final String ENTITY_NAME = "activity";

    @Autowired
    private ActivityService activityService;


    /**
     * 活动列表
     * @return
     * @throws Exception
     */
    @GetMapping("/activityList" )
    @ApiOperation(value = "活动列表", httpMethod = "GET",notes = "活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "activiName", value = "活动名称", dataType = "String", paramType = "query", required = false),
    })
    public ResponseEntity activityList(@RequestParam(value = "pageSize") Integer pageSize,
                                       @RequestParam(value = "pageIndex") Integer pageIndex,
                                       @RequestParam(value = "activiName",required = false) String activiName) {
        LoggerCommon.info(this.getClass(),"查询活动列表");
        Map map = new HashMap();
        map.put("companyCode", UserContextUtil.getUserInfo().getCompanyCode());
        map.put("promotionName",activiName);
        PageHelper.startPage(pageIndex, pageSize, true);
        List<EntPromotion> list = null;
        PageInfo pageInfo = null;
        try {
            list = activityService.getEntPromotionList(map);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            String localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询活动列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"成功查询活动列表");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
    }

    /**
     * 添加活动
     * @Param data{"activityId":"活动id","promotionName":"活动名称",
     * "startTime":"活动开始时间","endTime":"活动结束时间","promotionType":"活动类型",
     * "promotionPattern":"活动方式","reservation":"活动是否定制化","status":"是否启用",
     * "arr":[{"val1":"32","val2":"2"},{"val1":"2","val2":"3"}]}
     * @return
     * @throws Exception
     */
    @PostMapping("/addActivity")
    @ApiOperation(value = "添加活动",httpMethod = "POST", notes = "添加活动")
    public ResponseEntity addActivity(@RequestBody Map data) {
        LoggerCommon.info(this.getClass(),"添加活动");
        String localeTipMsg =null;
        try {
            UserInfo userInfo = UserContextUtil.getUserInfo();
            EntPromotion entPromotion = new EntPromotion();
            entPromotion.setPromotionType((String) data.get("promotionType"));//活动类型
            entPromotion.setPromotionName((String) data.get("promotionName"));//活动名称
            entPromotion.setPromotionPattern((String) data.get("promotionPattern"));//活动方式
            entPromotion.setReservation(Integer.parseInt(String.valueOf(data.get("reservation"))));//是否订制
            entPromotion.setStatus(Integer.valueOf(String.valueOf(data.get("status"))));//是否启用
            entPromotion.setStartTime(DateConversionUtil.strConvertToDate((String) data.get("startTime"),DateConversionUtil.STYLE_1));//活动开始时间
            entPromotion.setEndTime(DateConversionUtil.strConvertToDate((String) data.get("endTime"),DateConversionUtil.STYLE_1));//活动结束时间
            entPromotion.setCompanyCode(userInfo.getCompanyCode());//企业编号
            entPromotion.setCreateUser(userInfo.getAccount());//创建人
            entPromotion.setUpdateUser(userInfo.getAccount());//修改人
            Date date = new Date();
            entPromotion.setCreateTime(date);//创建时间
            entPromotion.setUpdateTime(date);//修改时间

            List<EntPromotionRule > list = new ArrayList<>();
            JSONArray jsa = JSONArray.parseArray(JSON.toJSONString((List) data.get("arr")));
            for (int i=0;i<jsa.size();i++){
                EntPromotionRule entPromotionRule = new EntPromotionRule();
                entPromotionRule.setCondit(JSON.toJSONString(jsa.getJSONObject(i)));
                list.add(entPromotionRule);
            }
            Map resmap = activityService.addEntPromotion(entPromotion,list);
            boolean flag =(Boolean) resmap.get("flag");
            localeTipMsg = LocaleMessage.get(resmap.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"添加活动成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.save.fail"))).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"添加活动异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 修改活动
     * @Param data{"activityId":"活动id","promotionName":"活动名称",
     * "startTime":"活动开始时间","endTime":"活动结束时间","promotionType":"活动类型",
     * "promotionPattern":"活动方式","reservation":"活动是否定制化","status":"是否启用",
     * "arr":[{"val1":"32","val2":"2"},{"val1":"2","val2":"3"}]}
     * @return
     * @throws Exception
     */
    @PostMapping("/updateActivity")
    @ApiOperation(value = "修改活动",httpMethod = "POST", notes = "修改活动")
    public ResponseEntity updateActivity(@RequestBody Map data) {
        LoggerCommon.info(this.getClass(),"修改活动");
        String localeTipMsg =null;
        try {
            UserInfo userInfo = UserContextUtil.getUserInfo();
            EntPromotion entPromotion = new EntPromotion();
            entPromotion.setId((String) data.get("activityId"));
            entPromotion.setPromotionType((String) data.get("promotionType"));//活动类型
            entPromotion.setPromotionName((String) data.get("promotionName"));//活动名称
            entPromotion.setPromotionPattern((String) data.get("promotionPattern"));//活动方式
            entPromotion.setReservation(Integer.parseInt(String.valueOf(data.get("reservation"))));//是否订制
            entPromotion.setStatus(Integer.valueOf(String.valueOf(data.get("status"))));//是否启用
            entPromotion.setStartTime(DateConversionUtil.strConvertToDate((String) data.get("startTime"),DateConversionUtil.STYLE_1));//活动开始时间
            entPromotion.setEndTime(DateConversionUtil.strConvertToDate((String) data.get("endTime"),DateConversionUtil.STYLE_1));//活动结束时间
            entPromotion.setCompanyCode(userInfo.getCompanyCode());//企业编号
            entPromotion.setUpdateUser(userInfo.getAccount());//修改人
            Date date = new Date();
            entPromotion.setUpdateTime(date);//修改时间

            List<EntPromotionRule > list = new ArrayList<>();
            JSONArray jsa = JSONArray.parseArray(JSON.toJSONString((List) data.get("arr")));
            for (int i=0;i<jsa.size();i++){
                EntPromotionRule entPromotionRule = new EntPromotionRule();
                entPromotionRule.setCondit(JSON.toJSONString(jsa.getJSONObject(i)));
                list.add(entPromotionRule);
            }
            Map map = activityService.updateEntPromotion(entPromotion,list);
            boolean flag =(boolean) map.get("flag");
            localeTipMsg = LocaleMessage.get(map.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"修改活动成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.update.fail"))).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改活动异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 删除活动
     * @return  activityId
     * @throws Exception
     */
    @PostMapping("/delActivity")
    @ApiOperation(value = "删除活动",httpMethod = "POST", notes = "删除活动")
    public ResponseEntity delActivity(@RequestBody Map data){
        LoggerCommon.info(this.getClass(),"删除活动");
        String localeTipMsg = LocaleMessage.get("message.system.request.param.exception");
        if(null==data.get("activityId")||StringUtil.isEmpty(data.get("activityId").toString())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert( localeTipMsg,ENTITY_NAME)).body(null);
        }
        Map map = null;
        try {
            map = activityService.delEntPromotion(data.get("activityId").toString());
            boolean flag =(boolean) map.get("flag");
            localeTipMsg = LocaleMessage.get(map.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"删除活动成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.delete.fail"))).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.delete.fail");
            LoggerCommon.info(this.getClass(),"删除活动异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 更新活动状态
     * @Param data{"activityId":,"status":}
     * @return
     * @throws Exception
     */
    @PostMapping("/updateActivityStatue")
    @ApiOperation(value = "更新活动状态",httpMethod = "POST", notes = "更新活动状态")
    public ResponseEntity updateActivityStatue(@RequestBody Map data) {
        LoggerCommon.info(this.getClass(),"更新活动状态");
        String localeTipMsg = LocaleMessage.get("message.system.request.param.exception");
        if(null==data.get("activityId")||StringUtil.isEmpty(data.get("activityId").toString())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert( localeTipMsg,ENTITY_NAME)).body(null);
        }
        if(null==data.get("status")||StringUtil.isEmpty(data.get("status").toString())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert( localeTipMsg,ENTITY_NAME)).body(null);
        }
        UserInfo userInfo = UserContextUtil.getUserInfo();
        EntPromotion entPromotion = new EntPromotion();
        entPromotion.setId(data.get("activityId").toString());
        entPromotion.setStatus(Integer.parseInt(data.get("status").toString()));
        entPromotion.setUpdateTime(new Date());
        entPromotion.setUpdateUser(userInfo.getAccount());
        Map map = null;
        try {
            map = activityService.updateStatus(entPromotion);
            boolean flag =(boolean) map.get("flag");
            localeTipMsg = LocaleMessage.get(map.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"更新活动状态成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg)).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"更新活动状态异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 查询活动
     * @return
     * @throws Exception
     */
    @GetMapping("/selActivity")
    @ApiOperation(value = "查询活动", httpMethod = "GET",notes = "查询活动")
    public ResponseEntity selActivity(@RequestParam("activityId")String activityId){
        LoggerCommon.info(this.getClass(),"查询活动");
        String localeTipMsg =null;
        Map map = null;
        try {
            map = activityService.selEntPromotion(activityId);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询活动异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询活动成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
    }

    /**
     * 查询有效的活动
     * @return
     * @throws Exception
     */
    @GetMapping("/getEffectiveActivity")
    @ApiOperation(value = "有效活动列表", httpMethod = "GET",notes = "有效活动列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", dataType = "int", paramType = "query", required = true),
    })
    public ResponseEntity getEffectiveActivity(@RequestParam(value = "pageSize") Integer pageSize,
                                               @RequestParam(value = "pageIndex") Integer pageIndex){
        LoggerCommon.info(this.getClass(),"查询有效活动列表");
        String localeTipMsg =null;
        PageHelper.startPage(pageIndex, pageSize, true);
        List<EntPromotion> list = null;
        PageInfo pageInfo = null;
        try {
            list = activityService.getEffectiveActivity(UserContextUtil.getUserInfo().getCompanyCode());
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询有效活动列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询有效活动列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
    }
}
