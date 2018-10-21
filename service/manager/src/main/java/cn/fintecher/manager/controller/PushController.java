package cn.fintecher.manager.controller;

import cn.fintecher.manager.tool.push.JiGuangApi;
import cn.fintecher.util.HeaderUtil;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Title :
 * Description : @极光推送@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@RestController
@RequestMapping("/api/push")
@Api(value = "/api/push", description = "极光推送")
public class PushController {

    private static final String ENTITY_NAME = "push";


    @ApiOperation(value = "极光推送", notes = "极光推送")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "masterSecret", value = "极光masterSecret", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "appKey", value = "极光appKey", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "title", value = "推送的标题", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "message", value = "推送内容", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "deviceId", value = "手机设备号", dataType = "String", paramType = "query", required = false),
    })
    @PostMapping("/jiguangPush")
    public ResponseEntity jiguangPush(@RequestBody Map data){
        String title= String.valueOf(data.get("title"));
        String message=String.valueOf(data.get("message"));
        String deviceId = String.valueOf(data.get("deviceId"));
        String masterSecret = String.valueOf(data.get("masterSecret"));
        String appKey = String.valueOf(data.get("appKey"));
        try {
            boolean flag =JiGuangApi.alias(title,message,deviceId,masterSecret,appKey);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIRequestException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("推送成功",ENTITY_NAME)).body(null);
    }
}