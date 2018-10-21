package cn.fintecher.app.controller.sysController;

import cn.fintecher.app.service.sys.SysParaService;
import cn.fintecher.common.constant.AppConstant;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.upload.impl.UploadFileToOss;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.app.service.sys.DictService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.uploadFile.util.UploadFileToOssUtil;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @富文本编辑器@
 * Create on : 2018年06月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:chengrui
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@RestController
@RequestMapping("/api/rich")
@Api(value = "/api/rich", description = "富文本编辑器")
public class RichController {

    private static final String ENTITY_NAME = "rich";

    @Autowired
    private UploadFileToOss uploadFileToOss;

    @Autowired
    private SysParaService sysParaService;

//    @RequestMapping("/toPage")
//    public String toPage() {return "SystemManage/Richtext";}

    @PostMapping("/uploadFile")
    @ApiOperation(value = "富文本编辑器上传文件", httpMethod = "POST",notes = "富文本编辑器上传文件")
    public ResponseEntity uploadFile(HttpServletRequest request) {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        JSONObject jsonObject = new JSONObject();
        //获取根目录
        String url = request.getSession().getServletContext().getRealPath("/fintecher_file");
        //给图片命名
        String id = CreateIDUtil.getId();
        String logmsg =null;
        try {
            //oss参数, 后面写入配置文件中
            String bucketName = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(), "oss_bucketName");
            String endpoint = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(), "oss_endpoint");
            String accessKeyId = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(), "oss_accessKeyId");
            String accessKeySecret = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(), "oss_accessKeySecret");
            //上传文件
            Map map = uploadFileToOss.uploadFile(request, url, id, "aaa", bucketName, endpoint, accessKeyId, accessKeySecret);
            Map<String,Object>resultMap= new HashMap();
            if (null != map && map.size() > 0) {
                List<Map> fileList = (List<Map>) map.get("fileList");
                resultMap.put("error", 0);
                resultMap.put("url", fileList);
                return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(resultMap));
            } else {
                logmsg=LocaleMessage.get("message.upload.error");
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg,ENTITY_NAME)).body(null);
            }
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.upload.error");
            LoggerCommon.info(this.getClass(),"富文本编辑器上传文件异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }

    }

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", httpMethod = "POST", notes = "上传文件")
    public ResponseEntity upload(HttpServletRequest request) {
        UserInfo userInfo = UserContextUtil.getUserInfo();
        //获取根目录
        String url = request.getSession().getServletContext().getRealPath("/fintecher_file");
        //给图片命名
        String id = CreateIDUtil.getId();
        //oss参数, 后面写入配置文件中
        String logmsg =null;
        try {
            String bucketName = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(), "oss_bucketName");
            String endpoint = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(), "oss_endpoint");
            String accessKeyId = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(), "oss_accessKeyId");
            String accessKeySecret = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(), "oss_accessKeySecret");
            //上传文件
            Map map = uploadFileToOss.uploadFile(request, url, id, "hlt", bucketName, endpoint, accessKeyId, accessKeySecret);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.upload.error");
            LoggerCommon.info(this.getClass(),"上传文件异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * 将Base64位图片上传到oss并返回url
     * @return
     * @throws Exception
     */
    @PostMapping("/getUrl")
    @ApiOperation(value = "Base64位上传文件", httpMethod = "POST", notes = "Base64位上传文件")
    public ResponseEntity Base64ToOss(@RequestBody String base64Code, HttpServletRequest request) {
        //String base64Code = pmap.get("base64Code").toString();
        LoggerCommon.info(this.getClass(),"Base64位上传文件");
        UserInfo userInfo = UserContextUtil.getUserInfo();
        String companyCode = userInfo.getCompanyCode();
        Map<String, String> srcMap = new HashMap<>();
        String bucketName = null;
        String logmsg =null;
        try {
            bucketName = sysParaService.getParaByCompanyAndKey(companyCode,"oss_bucketName");
            String endpoint = sysParaService.getParaByCompanyAndKey(companyCode,"oss_endpoint");
            String accessKeyId = sysParaService.getParaByCompanyAndKey(companyCode,"oss_accessKeyId");
            String accessKeySecret = sysParaService.getParaByCompanyAndKey(companyCode,"oss_accessKeySecret");
            srcMap.put("card",base64Code);
            String path = request.getSession().getServletContext().getRealPath("/fintecher_file/");
            Map map = UploadFileToOssUtil.uploadToOSS(srcMap,path,null,bucketName,endpoint,accessKeyId,accessKeySecret);
            LoggerCommon.info(this.getClass(),"Base64位上传文件成功:"+map.get("card"));
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
        } catch (Exception e) {
            logmsg = LocaleMessage.get("message.upload.error");
            LoggerCommon.info(this.getClass(),"Base64位上传文件异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}
