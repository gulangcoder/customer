package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.system.service.BannerService;
import cn.fintecher.util.HeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: liangdeng
 * @Date: 2018/9/19
 * @Description:
 */
@RestController
@RequestMapping("/api/banner")
@Api(value = "轮播图管理", description = "轮播图管理")
public class BannerController {

    private static final String ENTITY_NAME = "banner";

    @Autowired
    private BannerService bannerService;

    @GetMapping("/bannerList" )
    @ApiOperation(value = "轮播图管理列表", httpMethod = "GET",notes = "轮播图管理列表")
    public ResponseEntity bannerList(){
        LoggerCommon.info(this.getClass(),"获取轮播图管理列表");
        String localeTipMsg =null;
        Map map = new HashMap();
        List<String > list = null;
        try {
            list = bannerService.getBannerList(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询轮播图管理列表异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取轮播图管理列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
    }
}
