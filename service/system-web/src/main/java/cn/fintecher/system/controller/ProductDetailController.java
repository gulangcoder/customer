package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.EntProductCredit;
import cn.fintecher.system.model.EntProductDetail;
import cn.fintecher.system.model.Organization;
import cn.fintecher.system.service.OrganizationService;
import cn.fintecher.system.service.ProductDetailService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
 * @Date: 2018/9/5
 * @Description:
 */
@RestController
@RequestMapping("/api/productDetail")
@Api(value = "产品详情管理", description = "产品详情管理")
public class ProductDetailController {

    private static final String ENTITY_NAME = "productDetail";

    @Autowired
    private ProductDetailService productDetailService;

    @Autowired
    private OrganizationService organizationService;

    /**
     * @description: 产品系列列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    @GetMapping("/productDetailList" )
    @ApiOperation(value = "产品详情列表", httpMethod = "GET",notes = "产品详情列表")
    public ResponseEntity productDetailList(@RequestParam(value = "pageSize",required = true) Integer pageSize,
                                            @RequestParam(value = "pageIndex",required = true) Integer pageIndex,
                                            @RequestParam(value = "productSequence",required = false) String productSequence,
                                            @RequestParam(value = "productName",required = false) String productName,
                                            @RequestParam(value = "productId") String productId) {
        LoggerCommon.info(this.getClass(),"获取产品详情列表");
        String localeTipMsg =null;
        List<EntProductDetail> list = null;
        PageInfo pageInfo = null;
        try {
            Map map = new HashMap();
            map.put("productId", productId);
            map.put("productSequence", productSequence);
            map.put("productName", productName);
            PageHelper.startPage(pageIndex, pageSize, true);
            list = productDetailService.getProductDeatilList(map);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取产品详情列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取产品详情列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
    }

    /**
     * @description: 添加产品详情
     * @param: product
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    @PostMapping("/addProductDetail")
    @ApiOperation(value = "添加产品详情",httpMethod = "POST", notes = "添加产品详情")
    public ResponseEntity addProductDetail(@RequestBody EntProductDetail productDetail) {
        LoggerCommon.info(this.getClass(),"添加产品详情");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        List<EntProductDetail> list = null;
        String localeTipMsg =null;
        try {
            Map map = new HashMap();
            map.put("companyCode",userInfo.getCompanyCode());
            productDetail.setId(CreateIDUtil.getId());
            productDetail.setCompanyCode(userInfo.getCompanyCode());
            productDetail.setCreateUser(userInfo.getAccount());
            productDetail.setCreateTime(new Date());
            productDetail.setUpdateUser(userInfo.getAccount());
            productDetail.setUpdateTime(new Date());
            Map sequenceMap = new HashMap();
            sequenceMap.put("companyCode",userInfo.getCompanyCode());
            sequenceMap.put("id","");
            sequenceMap.put("productSequence",productDetail.getProductSequence());
            sequenceMap.put("productName",productDetail.getProductName());
            boolean checkSeriesSequenceFlag = checkProductSequence(sequenceMap);
            if (!checkSeriesSequenceFlag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.product.sequence.existed"))).body(null);
            }
            boolean checkSeriesFlag = checkProductName(sequenceMap);
            if (!checkSeriesFlag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.product.name.existed"))).body(null);
            }
            list = productDetailService.getProductDeatilList(map);
            productDetail.setSequence(list.size()+1);
            int addNum = productDetailService.addProductDetail(productDetail);
            if (addNum != 1) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.save.fail"))).body(null);
            }
            LoggerCommon.info(this.getClass(),"添加产品详情成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.save.success"),ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"添加产品详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 修改产品详情
     * @param: product
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    @PostMapping("/updateProductDetail")
    @ApiOperation(value = "修改产品详情",httpMethod = "POST", notes = "修改产品详情")
    public ResponseEntity updateProductDetail(@RequestBody EntProductDetail productDetail) {
        LoggerCommon.info(this.getClass(),"修改产品详情");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String localeTipMsg =null;
        try {
            productDetail.setCompanyCode(userInfo.getCompanyCode());
            productDetail.setUpdateUser(userInfo.getAccount());
            productDetail.setUpdateTime(new Date());
            Map sequenceMap = new HashMap();
            sequenceMap.put("companyCode",userInfo.getCompanyCode());
            sequenceMap.put("id",productDetail.getId());
            sequenceMap.put("productSequence",productDetail.getProductSequence());
            sequenceMap.put("productName",productDetail.getProductName());
            boolean checkSeriesSequenceFlag = checkProductSequence(sequenceMap);
            if (!checkSeriesSequenceFlag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.product.sequence.existed"))).body(null);
            }
            boolean checkSeriesFlag = checkProductName(sequenceMap);
            if (!checkSeriesFlag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.product.name.existed"))).body(null);
            }
            int updNum = productDetailService.updatePriductDteail(productDetail);
            if (updNum != 1) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.update.fail"))).body(null);
            }
            LoggerCommon.info(this.getClass(),"修改产品详情成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.update.success"),ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改产品详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 获取产品详情
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    @GetMapping("/getProducDetailById")
    @ApiOperation(value = "获取产品详情",httpMethod = "GET", notes = "获取产品详情")
    public ResponseEntity getProducDetailById(@RequestParam("productDetailId") String productDetailId){
        LoggerCommon.info(this.getClass(),"获取产品详情");
        String localeTipMsg = LocaleMessage.get("message.system.request.param.exception");
        if(null==productDetailId|| StringUtil.isEmpty(productDetailId)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(null);
        }
        EntProductDetail productDetail = null;
        try {
            productDetail = productDetailService.getProductDeatilById(productDetailId);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取产品详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取产品详情成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(productDetail));
    }

    /**
     * @description: 修改状态
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
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
        List<EntProductCredit> creditList = null;
        try {
            //当状态由启用修改为停用时，需要判断该产品详情下的产品信率中是否有产品正在启用
            String st = map.get("status").toString();
            Short s = Short.parseShort(st);
            if(s == 0){
                Map statusMap = new HashMap();
                statusMap.put("productDetailId",map.get("id").toString());
                statusMap.put("statuszw",(short)1);
                creditList = productDetailService.selectCreditListByMap(statusMap);
                if (creditList != null && creditList.size() > 0) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.stop.credit.product"))).body(null);
                }
            }
            //map.put("status",Short.parseShort(map.get("status").toString()));
            map.put("updateUser",userInfo.getAccount());
            map.put("updateTime",new Date());
            int updStatusNum = productDetailService.updatePriductDteailStatus(map);
            if (updStatusNum != 1) {
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
     * @description: 获取组织架构关联公司
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    @GetMapping("/getOrgCompany")
    @ApiOperation(value = "获取组织架构关联公司",httpMethod = "GET", notes = "获取组织架构关联公司")
    public ResponseEntity getOrgCompany(){
        LoggerCommon.info(this.getClass(),"获取组织架构关联公司");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        List<Organization> organizationList = null;
        String localeTipMsg =null;
        try {
            Map map = new HashMap();
            map.put("companyCode",userInfo.getCompanyCode());
            organizationList = organizationService.getOrgListByMap(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取组织架构关联公司异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取组织架构关联公司成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(organizationList));
    }

    /**
     * @description: 产品名称列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    @GetMapping("/productNameList" )
    @ApiOperation(value = "产品名称列表", httpMethod = "GET",notes = "产品名称列表")
    public ResponseEntity productNameList(@RequestParam(value = "productId",required = false) String productId){
        LoggerCommon.info(this.getClass(),"产品名称列表");
        String localeTipMsg =null;
        List<EntProductDetail> list = null;
        try {
            Map map = new HashMap();
            map.put("companyCode", UserContextUtil.getUserInfo().getCompanyCode());
            map.put("productId", productId);
            list = productDetailService.getProductNameList(map);

        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取组织架构关联公司异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取产品名称列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
    }

    /**
     * @description:校验产品序列是否重复
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    public boolean checkProductSequence(Map map) throws Exception{
        EntProductDetail productDetail = null;
        try {
            productDetail = productDetailService.checkProductSequence(map);
        } catch (Exception e) {
            LoggerCommon.info(this.getClass(),"校验产品序列是否重复："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            throw e;
        }
        if (productDetail != null) {
            return  false;
        }
        return  true;
    }

    /**
     * @description:校验产品名称是否重复
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    public boolean checkProductName(Map map) throws Exception{
        EntProductDetail productDetail = null;
        try {
            productDetail = productDetailService.checkProductName(map);
        } catch (Exception e) {
            LoggerCommon.info(this.getClass(),"校验产品名称是否重复："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            throw e;
        }
        if (productDetail != null) {
            return  false;
        }
        return  true;
    }
}
