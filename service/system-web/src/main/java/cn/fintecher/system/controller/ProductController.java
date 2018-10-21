package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.EntProduct;
import cn.fintecher.system.model.EntProductDetail;
import cn.fintecher.system.model.ProductCust;
import cn.fintecher.system.service.ProductService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/5
 * @Description:
 */
@RestController
@RequestMapping("/api/product")
@Api(value = "产品管理", description = "产品管理")
@Slf4j
public class ProductController {

    private static final String ENTITY_NAME = "product";

    @Autowired
    private ProductService productService;


    /**
     * @description: 产品系列列表
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    @GetMapping("/productList" )
    @ApiOperation(value = "产品系列列表", httpMethod = "GET",notes = "产品系列列表")
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", dataType = "int", paramType = "query", required = true),
    })*/
    public ResponseEntity productList(@RequestParam(value = "pageSize",required = true) Integer pageSize,
                                      @RequestParam(value = "pageIndex",required = true) Integer pageIndex,
                                      @RequestParam(value = "productSeriesSequence",required = false) String productSeriesSequence,
                                      @RequestParam(value = "productSeries",required = false) String productSeries,
                                      @RequestParam(value = "productSequence",required = false) String productSequence,
                                      @RequestParam(value = "productName",required = false) String productName){
        LoggerCommon.info(this.getClass(),"获取产品系列列表");
        String localeTipMsg =null;
        Map map = new HashMap();
        List<EntProduct> list = null;
        PageInfo pageInfo = null;
        try {
            map.put("companyCode", UserContextUtil.getUserInfo().getCompanyCode());
            map.put("productSeriesSequence", productSeriesSequence);
            map.put("productSeries", productSeries);
            map.put("productSequence", productSequence);
            map.put("productName", productName);
            PageHelper.startPage(pageIndex, pageSize, true);
            list = productService.getProductList(map);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取产品系列列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取产品系列列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
    }

    /**
     * @description: 添加产品系列
     * @param: product
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    @PostMapping("/addProduct")
    @ApiOperation(value = "添加产品系列",httpMethod = "POST", notes = "添加产品系列")
    public ResponseEntity addProduct(@RequestBody EntProduct product){
        LoggerCommon.info(this.getClass(),"添加产品系列");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        List<EntProduct> list = null;
        String localeTipMsg =null;
        try {
            Map map = new HashMap();
            map.put("companyCode",userInfo.getCompanyCode());
            product.setId(CreateIDUtil.getId());
            product.setCompanyCode(userInfo.getCompanyCode());
            product.setCreateUser(userInfo.getAccount());
            product.setCreateTime(new Date());
            product.setUpdateUser(userInfo.getAccount());
            product.setUpdateTime(new Date());
            Map seriesSequenceMap = new HashMap();
            seriesSequenceMap.put("companyCode",userInfo.getCompanyCode());
            seriesSequenceMap.put("id","");
            seriesSequenceMap.put("productSeriesSequence",product.getProductSeriesSequence());
            seriesSequenceMap.put("productSeries",product.getProductSeries());
            boolean checkSeriesSequenceFlag = checkProductSeriesSequence(seriesSequenceMap);
            if (!checkSeriesSequenceFlag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.product.series.sequence.existed"))).body(null);
            }
            boolean checkSeriesFlag = checkProductSeries(seriesSequenceMap);
            if (!checkSeriesFlag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.product.series.existed"))).body(null);
            }
            list = productService.getProductList(map);
            product.setSequence(list.size()+1);
            int addNum = productService.addProduct(product);
            if (addNum != 1) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.save.fail"))).body(null);
            }
            LoggerCommon.info(this.getClass(),"添加产品系列成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.save.success"),ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"添加产品系列异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 修改产品系列
     * @param: product
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    @PostMapping("/updateProduct")
    @ApiOperation(value = "修改产品系列",httpMethod = "POST", notes = "修改产品系列")
    public ResponseEntity updateProduct(@RequestBody EntProduct product) {
        LoggerCommon.info(this.getClass(),"修改产品系列");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String localeTipMsg =null;
        try {
            product.setCompanyCode(userInfo.getCompanyCode());
            product.setUpdateUser(userInfo.getAccount());
            product.setUpdateTime(new Date());
            Map seriesSequenceMap = new HashMap();
            seriesSequenceMap.put("companyCode",userInfo.getCompanyCode());
            seriesSequenceMap.put("id",product.getId());
            seriesSequenceMap.put("productSeriesSequence",product.getProductSeriesSequence());
            seriesSequenceMap.put("productSeries",product.getProductSeries());
            boolean checkSeriesSequenceFlag = checkProductSeriesSequence(seriesSequenceMap);
            if (!checkSeriesSequenceFlag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.product.series.sequence.existed"))).body(null);
            }
            boolean checkSeriesFlag = checkProductSeries(seriesSequenceMap);
            if (!checkSeriesFlag){
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.product.series.existed"))).body(null);
            }
            int updNUm = productService.updateProduct(product);
            if (updNUm != 1) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.update.fail"))).body(null);
            }
            LoggerCommon.info(this.getClass(),"修改产品系列成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.update.success"),ENTITY_NAME)).body(null);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改产品系列异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description:修改状态
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
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
        try {
            //当状态由启用修改为停用时，需要判断该产品系列下的产品详情中是否有产品正在启用
            String st = map.get("status").toString();
            Short s = Short.parseShort(st);
            if(s == 0){
                Map statusMap = new HashMap();
                statusMap.put("productId",map.get("id").toString());
                statusMap.put("status",(short)1);
                List<EntProductDetail> productDetailList = productService.seletctProductDetail(statusMap);
                if (productDetailList != null && productDetailList.size() > 0) {
                    return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.stop.series.product"))).body(null);
                }
            }
            map.put("updateUser",userInfo.getAccount());
            map.put("updateTime",new Date());
            int updStatusNUm = productService.updateProductStatus(map);
            if (updStatusNUm != 1) {
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
     * @description: 查询所有产品系列
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    @GetMapping("/getAllList" )
    @ApiOperation(value = "查询所有产品系列", httpMethod = "GET",notes = "查询所有产品系列")
    public ResponseEntity getAllList() {
        LoggerCommon.info(this.getClass(),"查询所有产品系列");
        String localeTipMsg =null;
        Map map = new HashMap();
        map.put("companyCode", UserContextUtil.getUserInfo().getCompanyCode());
        List<EntProduct> list = null;
        try {
            list = productService.getAllList(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询所有产品系列异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询所有产品系列成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
    }

    /**
     * @description: 获取产品明细
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    @GetMapping("/getProductById" )
    @ApiOperation(value = "获取产品明细", httpMethod = "GET",notes = "获取产品明细")
    public ResponseEntity getProductById(@RequestParam(value = "id") String id){
        LoggerCommon.info(this.getClass(),"获取产品明细");
        String localeTipMsg = LocaleMessage.get("message.system.request.param.exception");
        if(null==id|| StringUtil.isEmpty(id)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(localeTipMsg, ENTITY_NAME)).body(null);
        }
        EntProduct product = null;
        try {
            product = productService.getProductById(id);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取产品明细异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取产品明细成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(product));
    }

    /**
     * @description:校验产品系列序列是否重复
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    public boolean checkProductSeriesSequence(Map map) throws Exception{
        EntProduct product = null;
        String localeTipMsg = null;
        try {
            product = productService.checkSeriesSequence(map);
        } catch (Exception e) {
            LoggerCommon.info(this.getClass(),"校验产品系列序列是否重复："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            throw e;
        }
        if (product != null){
            return false;
        }
        return true;
    }

    /**
     * @description:校验产品系列是否重复
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/5
     */
    public boolean checkProductSeries(Map map) throws Exception{
        EntProduct product = null;
        try {
            product = productService.checkSeries(map);
        } catch (Exception e) {
            LoggerCommon.info(this.getClass(),"校验产品系列是否重复："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            throw e;
        }
        if (product != null){
            return false;
        }
        return true;
    }

    /**
     * @description: 获取产品明细
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    @GetMapping("/getProductInfo" )
    @ApiOperation(value = "获取产品最高额度和最低利率", httpMethod = "GET",notes = "获取产品最高额度和最低利率")
    public ResponseEntity getProductInfo(@RequestParam String productId,@RequestParam String companyCode){
        LoggerCommon.info(this.getClass(),"获取产品最高额度和最低利率");
        String localeTipMsg = null;
        Map resultMap = new HashMap();
        try {
            Map map = new HashMap();
            map.put("companyCode", companyCode);
            map.put("productId", productId);
            resultMap = productService.getProductInfo(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取产品最高额度和最低利率异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取产品最高额度和最低利率成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(resultMap));
    }

    /**
     * @description: 查询所有产品系列与客户关系
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/6
     */
    @GetMapping("/getProductCustList" )
    @ApiOperation(value = "查询所有产品系列与客户关系", httpMethod = "GET",notes = "查询所有产品系列与客户关系")
    public ResponseEntity getProductCustList(@RequestParam String custId,@RequestParam String companyCode) {
        log.info("查询所有产品系列与客户关系");
        String localeTipMsg =null;
        Map map = new HashMap();
        map.put("companyCode", companyCode);
        map.put("custId", custId);
        List<ProductCust> list = null;
        try {
            list = productService.getProductCustList(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            log.info("查询所有产品系列异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        log.info("查询所有产品系列成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
    }
}
