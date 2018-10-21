package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.EntProductCredit;
import cn.fintecher.system.service.ProductCreditService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.DateConversionUtil;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/6
 * @Description:
 */
@RestController
@RequestMapping("/api/productCredit")
@Api(value = "产品信贷费率", description = "产品信贷费率")
public class ProductCreditController {

    private static final String ENTITY_NAME = "productCredit";

    @Autowired
    private ProductCreditService productCreditService;

    /**
     * @description: 产品系列列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    @GetMapping("/productCreditList" )
    @ApiOperation(value = "产品信贷费率列表", httpMethod = "GET",notes = "产品信贷费率列表")
    public ResponseEntity productCreditList(@RequestParam(value = "pageSize") Integer pageSize,
                                            @RequestParam(value = "pageIndex") Integer pageIndex,
                                            @RequestParam(value = "productSequence",required = false) String productSequence,
                                            @RequestParam(value = "productName",required = false) String productName,
                                            @RequestParam(value = "paymentWay",required = false) String paymentWay,
                                            @RequestParam(value = "status",required = false) Short status,
                                            @RequestParam(value = "periods",required = false) Integer periods)  {
        LoggerCommon.info(this.getClass(),"获取产品信贷费率列表");
        String localeTipMsg =null;
        List<EntProductCredit> list = null;
        PageInfo pageInfo = null;
        try {
            Map map = new HashMap();
            map.put("companyCode", UserContextUtil.getUserInfo().getCompanyCode());
            map.put("productSequence", productSequence);
            map.put("productName", productName);
            map.put("paymentWay", paymentWay);
            map.put("statuszw", status);
            map.put("periods", periods);
            PageHelper.startPage(pageIndex, pageSize, true);
            list = productCreditService.getProductCreditList(map);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取产品信贷费率列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取产品信贷费率列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
    }

    /**
     * @description: 添加产品信率
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    @PostMapping("/addProductCredit")
    @ApiOperation(value = "添加产品信率",httpMethod = "POST", notes = "添加产品信率")
    public ResponseEntity addProductCredit(@RequestBody EntProductCredit productCredit){
        LoggerCommon.info(this.getClass(),"添加产品信率");
        String localeTipMsg =null;
        UserInfo userInfo = UserContextUtil.getUserInfo();
        List<EntProductCredit> list = null;
        try {
            productCredit.setId(CreateIDUtil.getId());
            productCredit.setCompanyCode(userInfo.getCompanyCode());
            productCredit.setCreateUser(userInfo.getAccount());
            productCredit.setCreateTime(new Date());
            productCredit.setUpdateUser(userInfo.getAccount());
            productCredit.setUpdateTime(new Date());
            productCredit.setDeleteFlag((short)0);
            productCredit.setBatch(CreateIDUtil.getId()+"-"+ DateConversionUtil.getCurrentTime());
            Map map = new HashMap();
            map.put("companyCode", userInfo.getCompanyCode());
            list = productCreditService.getProductCreditList(map);
            productCredit.setSerial(list.size()+1);
            map.put("id","");
            map.put("productId",productCredit.getProductId());
            map.put("productDetailId",productCredit.getProductDetailId());
            map.put("periods",productCredit.getPeriods());
            map.put("periodsDays",productCredit.getPeriodsDays());
            map.put("eachTermType",productCredit.getEachTermType());
            EntProductCredit pcd = productCreditService.getProductCreditByMap(map);
            if(null != pcd){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.periods.existed"))).body(null);
            }
            int addNum = productCreditService.addProductCredit(productCredit);
            if (addNum != 1) {
                if(addNum == 9){
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.ladder.rate.error"))).body(null);
                }else {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.save.fail"))).body(null);
                }
            }
            LoggerCommon.info(this.getClass(),"添加产品信率成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.save.success"),ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"添加产品信率异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 修改产品信率
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    @PostMapping("/updateProductCredit")
    @ApiOperation(value = "修改产品信率",httpMethod = "POST", notes = "修改产品信率")
    public ResponseEntity updateProductCredit(@RequestBody EntProductCredit productCredit){
        LoggerCommon.info(this.getClass(),"修改产品信率");
        String localeTipMsg =null;
        try {
            UserInfo userInfo = UserContextUtil.getUserInfo();
            Map map = new HashMap();
            map.put("companyCode", userInfo.getCompanyCode());
            map.put("id",productCredit.getId());
            map.put("productId",productCredit.getProductId());
            map.put("productDetailId",productCredit.getProductDetailId());
            map.put("periods",productCredit.getPeriods());
            map.put("periodsDays",productCredit.getPeriodsDays());
            map.put("eachTermType",productCredit.getEachTermType());
            EntProductCredit pcd = productCreditService.getProductCreditByMap(map);
            if(null != pcd){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.periods.existed"))).body(null);
            }
            int updateNum = productCreditService.updateProductCredit(productCredit);
            if (updateNum != 1) {
                if(updateNum == 9){
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.ladder.rate.error"))).body(null);
                }else {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.update.fail"))).body(null);
                }
            }
            LoggerCommon.info(this.getClass(),"修改产品信率成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.update.success"),ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改产品信率异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 修改状态
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改状态",httpMethod = "POST", notes = "修改状态")
    public ResponseEntity updateStatus(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"修改状态");
        String localeTipMsg = LocaleMessage.get("message.system.request.param.exception");
        if(null==map.get("id")|| StringUtil.isEmpty(map.get("id").toString())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(null);
        }
        UserInfo userInfo = UserContextUtil.getUserInfo();
        //Short.parseShort(map.get("status").toString())
        try {
            map.put("updateUser",userInfo.getAccount());
            map.put("updateTime",new Date());
            int updStatusNum = productCreditService.updatePriductCreditStatus(map);
            if (updStatusNum != 1){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.operation.fail"))).body(null);
            }
            LoggerCommon.info(this.getClass(),"修改状态成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.operation.success"),ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"修改状态异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 获取信率详情
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    @GetMapping("/getDetailCreditById")
    @ApiOperation(value = "获取信率详情",httpMethod = "GET", notes = "获取信率详情")
    public ResponseEntity getDetailCreditById(@RequestParam("id") String id){
        LoggerCommon.info(this.getClass(),"获取信率详情");
        String localeTipMsg = LocaleMessage.get("message.system.request.param.exception");
        if(null==id|| StringUtil.isEmpty(id)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(null);
        }
        EntProductCredit productCredit = null;
        try {
            productCredit = productCreditService.getDetailCreditById(id);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取信率详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取信率详情成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(productCredit));
    }

    /**
     * @description: 获取期数
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/7
     */
    @GetMapping("/getPeriodsList")
    @ApiOperation(value = "获取期数",httpMethod = "GET", notes = "获取期数")
    public ResponseEntity getPeriodsList(){
        LoggerCommon.info(this.getClass(),"获取期数");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String localeTipMsg = null;
        List<Map> list = null;
        try {
            Map map = new HashMap();
            map.put("companyCode", userInfo.getCompanyCode());
            list = productCreditService.getPeriodsList(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取期数异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取期数成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
    }
    /**
     * @description: 获取产品名称
     * @param:
     * @return:
     */
    @GetMapping("/getProductNameList")
    @ApiOperation(value = "获取产品名称",httpMethod = "GET", notes = "获取产品名称")
    public ResponseEntity getProductNameList(){
        LoggerCommon.info(this.getClass(),"获取期数");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String localeTipMsg = null;
        List<Map> list = null;
        try {
            Map map = new HashMap();
            map.put("companyCode", userInfo.getCompanyCode());
            list = productCreditService.getProductNameList(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取期数异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取期数成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
    }
}
