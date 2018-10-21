package cn.fintecher.manager.controller;

import cn.fintecher.manager.service.AlipayService;
import cn.fintecher.manager.service.WeChatPayService;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Title :
 * Description : @支付管理@
 * Create on : 2018年09月11日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:gaozhidong
 * @version 1.0
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@RestController
@RequestMapping("/api/Pay")
@Api(value = "支付管理", description = "支付管理")
public class PayController {

    @Resource
    private AlipayService alipayService;

    @Resource
    private WeChatPayService weChatPayService;

    @ApiOperation(value = "阿里支付",  httpMethod = "POST", notes = "阿里支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyCode", value = "公司code", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "transactionId", value = "交易订单id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "transactionName", value = "交易名称", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "transactionTotalAmount", value = "交易金额", dataType = "Double", paramType = "query", required = true)
    })
    @PostMapping("/alipaySdk")
    public ResponseEntity alipaySdk(String companyCode,String transactionId,String transactionName,Double transactionTotalAmount)throws Exception{
        Map<String,Object> paramMap=new HashedMap();
        if (companyCode==null||"".equals(companyCode.trim())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("公司code不能为空", ENTITY_NAME)).body(null);
        }
        if (transactionId==null||"".equals(transactionId.trim())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("交易订单id不能为空", ENTITY_NAME)).body(null);
        }
        if (transactionName==null||"".equals(transactionName.trim())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("交易名称不能为空", ENTITY_NAME)).body(null);
        }
        if (transactionTotalAmount==null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("交易金额有误", ENTITY_NAME)).body(null);
        }

        Map<String,Object> resultMap=alipayService.alipaySDKPayment(paramMap);
        if ((boolean) resultMap.get("flag")){
            return ResponseEntity.ok().body(resultMap.get("form").toString());
        }else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("操作失败",resultMap.get("mag").toString())).body(null);
        }
    }

    @ApiOperation(value = "微信支付",httpMethod = "POST", notes = "微信支付")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "companyCode", value = "公司code", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "transactionId", value = "交易订单id", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "transactionName", value = "交易名称", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "transactionTotalAmount", value = "交易金额", dataType = "Double", paramType = "query", required = true)
    })
    @PostMapping("/weChatPaySdk")
    public ResponseEntity weChatPaySdk(String companyCode,String transactionId,String transactionName,Double transactionTotalAmount)throws Exception{
        Map<String,Object> paramMap=new HashedMap();
        if (companyCode==null||"".equals(companyCode.trim())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("公司code不能为空", ENTITY_NAME)).body(null);
        }
        if (transactionId==null||"".equals(transactionId.trim())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("交易订单id不能为空", ENTITY_NAME)).body(null);
        }
        if (transactionName==null||"".equals(transactionName.trim())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("交易名称不能为空", ENTITY_NAME)).body(null);
        }
        if (transactionTotalAmount==null){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("交易金额有误", ENTITY_NAME)).body(null);
        }

        Map<String,Object> resultMap=weChatPayService.WeChatPaySdk(paramMap);
        if ((boolean) resultMap.get("flag")){
            return ResponseEntity.ok().body(resultMap.get("payParam"));
        }else {
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("操作失败",resultMap.get("mag").toString())).body(null);
        }
    }

}
