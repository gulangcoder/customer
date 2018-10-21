package cn.fintecher.system.controller;

import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.system.model.EntProduct;
import cn.fintecher.system.service.NoticeService;
import cn.fintecher.util.HeaderUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/notice")
@Api(value = "公告管理", description = "公告管理")
public class NoticeController {

    private static final String ENTITY_NAME = "notice";

    @Autowired
    private NoticeService noticeService;

    @GetMapping("/noticeList" )
    @ApiOperation(value = "公告管理列表", httpMethod = "GET",notes = "公告管理列表")
    public ResponseEntity noticeList(){
        LoggerCommon.info(this.getClass(),"获取公告管理列表");
        String localeTipMsg =null;
        Map map = new HashMap();
        List<String > list = null;
        try {
            list = noticeService.getNoticeList(map);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询公告管理异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取公告管理列表成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(list));
    }

    @GetMapping("/getNoticeDetail" )
    @ApiOperation(value = "公告详情", httpMethod = "GET",notes = "公告详情")
    public ResponseEntity getNoticeDetail(@RequestParam String id){
        LoggerCommon.info(this.getClass(),"获取公告详情");
        String localeTipMsg =null;
        String notice = "";
        try {
            notice = noticeService.getNoticeDetailById(id);
        } catch (Exception e) {
            localeTipMsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询公告详情异常："+ ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    localeTipMsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
        LoggerCommon.info(this.getClass(),"获取公告详情成功");
        return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(notice));
    }
}
