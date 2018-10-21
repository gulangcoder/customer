package cn.fintecher.system.controller;


import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.GoodsCategory;
import cn.fintecher.system.service.GoodsCategoryService;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @商品类别@
 * Create on : 2018年06月11日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@RestController
@RequestMapping("/api/goodsCategory")
@Api(value = "商品类别管理", description = "商品类别管理")
public class GoodsCategoryController {

    private static final String ENTITY_NAME = "goodsCategory";

    @Resource
    private GoodsCategoryService goodsCategoryService;

    /**
     * 添加商品类别
     * @param goodsCategory
     * @return ResultVO
     */
    @PostMapping("/addGoodsCategory")
    @ApiOperation(value = "添加商品类别",httpMethod = "POST", notes = "添加商品类别")
    public ResponseEntity addGoodsCategory(@RequestBody GoodsCategory goodsCategory){
        LoggerCommon.info(this.getClass(),"添加商品类别");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        goodsCategory.setCreateUser(userInfo.getAccount());//创建人
        goodsCategory.setCompanyCode(userInfo.getCompanyCode());//公司编码
        Map<String,Object> resultMap= null;
        String localeTipMsg =null;
        try {
            resultMap = goodsCategoryService.addGoodsCategory(goodsCategory);
            boolean flag =(Boolean) resultMap.get("flag");
            localeTipMsg = LocaleMessage.get(resultMap.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"添加商品类别成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg)).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"添加商品类别异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 修改商品类别
     * @param goodsCategory
     * @return ResultVO
     */
    @PostMapping("/updateGoodsCategory")
    @ApiOperation(value = "修改商品类别",httpMethod = "POST", notes = "修改商品类别")
    public ResponseEntity updateGoodsCategory(@RequestBody GoodsCategory goodsCategory){
        LoggerCommon.info(this.getClass(),"修改商品类别");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        goodsCategory.setUpdateUser(userInfo.getAccount());//修改人
        goodsCategory.setCompanyCode(userInfo.getCompanyCode());//公司编码
        Map<String,Object> resultMap= null;
        String localeTipMsg =null;
        try {
            resultMap = goodsCategoryService.updateGoodsCategory(goodsCategory);
            boolean flag =(Boolean) resultMap.get("flag");
            localeTipMsg = LocaleMessage.get(resultMap.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"修改商品类别成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg)).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改商品类别异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 删除商品类别
     * @param
     * @return ResultVO
     */
    @PostMapping("/delGoodsCategory")
    @ApiOperation(value = "删除商品类别",httpMethod = "POST", notes = "删除商品类别")
    public ResponseEntity delGoodsCategory(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"删除商品类别");
        String localeTipMsg = LocaleMessage.get("message.system.request.param.exception");
        if(null==map.get("goodsCategoryId")|| StringUtil.isEmpty(map.get("goodsCategoryId").toString())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert( localeTipMsg,ENTITY_NAME)).body(null);
        }
        GoodsCategory goodsCategory=new GoodsCategory();
        UserInfo userInfo = UserContextUtil.getUserInfo();
        goodsCategory.setUpdateUser(userInfo.getAccount());
        goodsCategory.setId(map.get("goodsCategoryId").toString());
        Map<String,Object> resultMap= null;
        try {
            resultMap = goodsCategoryService.delGoodsCategory(goodsCategory);
            boolean flag =(Boolean) resultMap.get("flag");
            localeTipMsg = LocaleMessage.get(resultMap.get("msg").toString());
            if(flag){
                LoggerCommon.info(this.getClass(),"删除商品类别成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(localeTipMsg,ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", localeTipMsg)).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.delete.fail");
            LoggerCommon.info(this.getClass(),"删除商品类别异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 根据条件数据列表
     * @return ResultVO
     */
    @GetMapping("/getList")
    @ApiOperation(value = "根据条件数据列表",httpMethod = "GET", notes = "根据条件数据列表")
    public ResponseEntity getList(){
        LoggerCommon.info(this.getClass(),"查询数据列表");
        String localeTipMsg =null;
        Map map = new HashMap();
        map.put("companyCode",UserContextUtil.getUserInfo().getCompanyCode());
        List<GoodsCategory> goodsCategoryList= null;
        try {
            goodsCategoryList = goodsCategoryService.getList(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询数据列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询数据列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(goodsCategoryList));
    }

    /**
     * 获取商品类型数据
     * @param id
     * @return ResultVO
     */
    @GetMapping("/getGoodsCategory")
    @ApiOperation(value = "获取商品类型数据",httpMethod = "GET", notes = "获取商品类型数据")
    public ResponseEntity getGoodsCategory(@RequestParam("id")String id){
        LoggerCommon.info(this.getClass(),"获取商品类型数据");
        String localeTipMsg =null;
        GoodsCategory goodsCategory= null;
        try {
            goodsCategory = goodsCategoryService.getGoodsCategory(id);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取商品类型数据异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取商品类型数据成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(goodsCategory));
    }


    /**
     * 获取商品类型联动
     * @param parentId
     * @return ResultVO
     */
    @GetMapping("/getGoodsCategoryLstByParentId")
    @ApiOperation(value = "获取商品类型联动",httpMethod = "GET", notes = "获取商品类型联动")
    public ResponseEntity getGoodsCategoryLstByParentId(@RequestParam("parentId")String parentId){
        LoggerCommon.info(this.getClass(),"获取商品类型联动");
        String localeTipMsg =null;
        Map<String,Object>map=new HashMap<>();
        //商品类型第一级联动时没有id 默认获取商品分类第一级数据
        List<GoodsCategory> goodsCategoryList= null;
        try {
            if ("".equals(parentId)||parentId==null){
                map.put("companyCode",UserContextUtil.getUserInfo().getCompanyCode());
                map.put("goodsGrade",1);
            }else {
                map.put("parentId",parentId);
            }
            goodsCategoryList = goodsCategoryService.getGoodsCategoryLstByParentId(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取商品类型联动异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取商品类型联动成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(goodsCategoryList));
    }
}
