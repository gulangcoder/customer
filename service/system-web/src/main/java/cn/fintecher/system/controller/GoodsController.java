package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.EntPromotion;
import cn.fintecher.system.model.Goods;
import cn.fintecher.system.service.GoodsService;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @商品管理@
 * Create on : 2018年06月12日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@RestController
@RequestMapping("/api/goods")
@Api(value = "商品管理", description = "商品管理")
public class GoodsController {

    private static final String ENTITY_NAME = "goods";
    @Resource
    private GoodsService goodsService;


    //添加商品
    @PostMapping("/addGoods")
    @ApiOperation(value = "添加商品",httpMethod = "POST", notes = "添加商品")
    public ResponseEntity addGoods(@RequestBody Goods goods) {
        LoggerCommon.info(this.getClass(),"添加商品");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        goods.setCreateUser(userInfo.getAccount());//创建人
        goods.setCompanyCode(userInfo.getCompanyCode());//公司编码
        String localeTipMsg =null;
        try {
            if (goodsService.addGoods(goods)==1){
                LoggerCommon.info(this.getClass(),"添加商品成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.save.success"),ENTITY_NAME)).body(null);
            }else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.save.fail"))).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"添加商品异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    //修改商品
    @PostMapping("/updateGoods")
    @ApiOperation(value = "修改商品",httpMethod = "POST", notes = "修改商品")
    public ResponseEntity updateGoods(@RequestBody Goods goods){
        LoggerCommon.info(this.getClass(),"修改商品");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        goods.setUpdateUser(userInfo.getAccount());//修改人
        //goodsCategory.setCompanyCode(userInfo.getCompanyCode());//公司编码
        String localeTipMsg =null;
        try {
            if (goodsService.updateGoods(goods)==1){
                LoggerCommon.info(this.getClass(),"修改商品成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.update.success"),ENTITY_NAME)).body(null);
            }else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.update.fail"))).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"添加商品异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    //删除商品
    @PostMapping("/delGoods")
    @ApiOperation(value = "删除商品",httpMethod = "POST", notes = "删除商品")
    public ResponseEntity delGoods(@RequestBody Map map){
        LoggerCommon.info(this.getClass(),"删除商品");
        String localeTipMsg = LocaleMessage.get("message.system.request.param.exception");
        if(null==map.get("goodsId")|| StringUtil.isEmpty(map.get("goodsId").toString())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert( localeTipMsg,ENTITY_NAME)).body(null);
        }
        Goods goods=new Goods();
        UserInfo userInfo = UserContextUtil.getUserInfo();
        goods.setUpdateUser(userInfo.getAccount());
        goods.setId(map.get("goodsId").toString());
        try {
            if (goodsService.delGoods(goods)==1){
                LoggerCommon.info(this.getClass(),"删除活动成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.delete.success"),ENTITY_NAME)).body(null);
            }else {
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
     * 根据条件数据列表
     * @return ResultVO
     */
    @GetMapping("/getList" )
    @ApiOperation(value = "根据条件数据列表", httpMethod = "GET",notes = "根据条件数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", dataType = "int", paramType = "query", required = true),
    })
    public ResponseEntity getList(@RequestParam(value = "pageSize") Integer pageSize,
                                  @RequestParam(value = "pageIndex") Integer pageIndex,
                                  @RequestParam(value = "goodsName",required = false) String goodsName) {
        LoggerCommon.info(this.getClass(),"查询数据列表");
        String localeTipMsg =null;
        Map map = new HashMap();
        map.put("companyCode",UserContextUtil.getUserInfo().getCompanyCode());
        map.put("name",goodsName);
        PageHelper.startPage(pageIndex, pageSize, true);
        List<Goods> goodsCategoryList= null;
        PageInfo pageInfo = null;
        try {
            goodsCategoryList = goodsService.getList(map);
            pageInfo = new PageInfo(goodsCategoryList);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询活动异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"查询数据列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
    }

    /**
     * 商品分配活动
     * param data{"goodsId":,activeIds[1,2,3]}
     * @return ResultVO
     */
    @PostMapping("/assignActivity")
    @ApiOperation(value = "商品分配活动",httpMethod = "POST", notes = "商品分配活动")
    public ResponseEntity assignActivity(@RequestBody Map data){
        LoggerCommon.info(this.getClass(),"商品分配活动");
        String localeTipMsg = null;
        try {
            boolean flag = goodsService.assignActivity(data);
            if (flag) {
                LoggerCommon.info(this.getClass(),"商品分配活动成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.operation.success"),ENTITY_NAME)).body(null);
            }else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.operation.fail"))).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"商品分配活动异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }

    }

    /**
     * 获取选择的活动
     * @return ResultVO
     */
    @GetMapping("/getAssignActivity")
    @ApiOperation(value = "获取选择的活动",httpMethod = "GET", notes = "获取选择的活动")
    public ResponseEntity getAssignActivity(@RequestParam("goodsCode")String goodsCode){
        LoggerCommon.info(this.getClass(),"获取选择的活动");
        String localeTipMsg =null;
        List<EntPromotion> list = null;
        try {
            list = goodsService.getAssignActivity(goodsCode);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询活动异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取选择的活动成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
    }

    //修改商品状态 data{"goodsId":,"status":}
    @PostMapping("/updateStatus")
    @ApiOperation(value = "修改商品状态",httpMethod = "POST", notes = "修改商品状态")
    public ResponseEntity updateStatus(@RequestBody Map data){
        LoggerCommon.info(this.getClass(),"修改商品状态");
        String localeTipMsg = null;
        try {
            int n = goodsService.updateStatus(data);
            if(n>0){
                LoggerCommon.info(this.getClass(),"修改商品状态成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.operation.success"),ENTITY_NAME)).body(null);
            }else{
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.operation.fail"))).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"修改商品状态异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    //获取商品
    @GetMapping("/getGoodsById")
    @ApiOperation(value = "获取商品",httpMethod = "GET", notes = "获取商品")
    public ResponseEntity getGoodsById(@RequestParam("id")String id){
        //goodsCategory.setCompanyCode(userInfo.getCompanyCode());//公司编码
        LoggerCommon.info(this.getClass(),"获取商品");
        String localeTipMsg =null;
        Goods goods= null;
        try {
            goods = goodsService.getGoodsById(id);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"获取商品异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取商品成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(goods));
    }

    //修改商品上下架
    @PostMapping("/updateGoodsStatus")
    @ApiOperation(value = "修改商品上下架",httpMethod = "POST", notes = "修改商品上下架")
    public ResponseEntity updateGoodsStatus(@RequestBody Goods goods){
        LoggerCommon.info(this.getClass(),"修改商品上下架");
        String localeTipMsg = null;
        UserInfo userInfo = UserContextUtil.getUserInfo();
        goods.setUpdateUser(userInfo.getAccount());//修改人
        //goodsCategory.setCompanyCode(userInfo.getCompanyCode());//公司编码
        try {
            if (goodsService.updateGoods(goods)==1){
                LoggerCommon.info(this.getClass(),"修改商品上下架成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.operation.success"),ENTITY_NAME)).body(null);
            }else {
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "system.server.exception", LocaleMessage.get("message.system.operation.fail"))).body(null);
            }
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.system.operation.fail");
            LoggerCommon.info(this.getClass(),"删除活动异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}
