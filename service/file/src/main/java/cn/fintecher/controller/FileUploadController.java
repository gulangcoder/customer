package cn.fintecher.controller;

import cn.fintecher.common.file.UploadFileEntity;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.repository.UploadFileToMongoRepository;
import cn.fintecher.service.UploadFileCridFsService;
import cn.fintecher.service.UploadFileToMongoService;
import cn.fintecher.util.HeaderUtil;
import com.google.common.collect.Lists;
import com.mongodb.gridfs.GridFSDBFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;


/**
 * @Description 文件服务器
 * @Author coder_bao
 * @Date 2018/9/28 14:15
 */
@RestController
@RequestMapping("/api/fileUpload")
@Api(value = "/api/fileUpload", description = "文件服务器接口")
public class FileUploadController {
    private static final String ENTITY_NAME="file_upload";
    @Autowired
    private UploadFileToMongoService uploadFileToMongoService;
    @Autowired
    private UploadFileToMongoRepository uploadFileToMongoRepository;
    @Autowired
    private UploadFileCridFsService uploadFileCridFsService;

    /**
     * @Description 文件上传
     * @Param file 文件大小小于16M
     * @return
     * @Author coder_bao
     * @Date 2018/9/28 14:34
     */
    @PostMapping(value="/uploadFile",headers = {"content-type=multipart/mixed", "content-type=multipart/form-data"}, consumes = {"multipart/form-data"})
    @ApiOperation(value = "文件上传", notes = "文件上传")
    public ResponseEntity uploadFile(@RequestBody MultipartFile file){
        LoggerCommon.info(this.getClass(),"文件上传开始");
        if (file.isEmpty()){
            LoggerCommon.info(this.getClass(),"文件上传失败,文件为空");
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"Access parameter is empty",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionResponseData());
        }

        try {
            UploadFileEntity uploadFileEntity = uploadFileToMongoService.saveFile(file);
            LoggerCommon.info(this.getClass(),"文件上传成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.successMessage"), ENTITY_NAME))
                    .body(ResponseLogMessageHandel.initSuccessResponseData(uploadFileEntity));
        } catch (Exception e) {
            LoggerCommon.info(this.getClass(),"文件上传失败,异常信息："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"Access parameter is empty",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionDataResponse(e));
        }
    }

    /**
     * @Description 文件上传
     * @Param file 大文件上传
     * @return
     * @Author coder_bao
     * @Date 2018/9/28 14:34
     */
    @PostMapping(value="/uploadCridFsFile",headers = {"content-type=multipart/mixed", "content-type=multipart/form-data"}, consumes = {"multipart/form-data"})
    @ApiOperation(value = "CridFs文件上传", notes = "CridFs文件上传")
    public ResponseEntity uploadCridFsFile(@RequestBody MultipartFile file){
        LoggerCommon.info(this.getClass(),"文件上传开始");
        if (file.isEmpty()){
            LoggerCommon.info(this.getClass(),"文件上传失败,文件为空");
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"Access parameter is empty",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionResponseData());
        }
        try {
            UploadFileEntity uploadFileEntity = uploadFileCridFsService.uploadFile(file);
            LoggerCommon.info(this.getClass(),"文件上传成功");
            return ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.successMessage"), ENTITY_NAME))
                    .body(ResponseLogMessageHandel.initSuccessResponseData(uploadFileEntity));
        } catch (Exception e) {
            LoggerCommon.info(this.getClass(),"文件上传失败,异常信息："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"Access parameter is empty",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionDataResponse(e));
        }
    }

    /**
     * @Description 根据文件id查询上传文件
     * @Param
     * @return
     * @Author coder_bao
     * @Date 2018/9/28 14:54
     */
    @GetMapping("/getAllUploadFileByIds")
    @ApiOperation(value = "根据文件id查询上传文件列表", notes = "根据文件id查询上传文件列表")
    public ResponseEntity getAllUploadFileByIds(@RequestParam(required = true) List<String> ids){
        LoggerCommon.info(this.getClass(),"查询所有的上传文件列表");
        try{
            List<UploadFileEntity> fileLists = Lists.newArrayList(uploadFileToMongoRepository.findAll(ids));
            LoggerCommon.info(this.getClass(),"成功查询所有的上传文件列表");
            return  ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.successMessage"), ENTITY_NAME))
                    .body(ResponseLogMessageHandel.initSuccessResponseData(fileLists));
        }catch (Exception e){
            LoggerCommon.info(this.getClass(),"查询所有的上传文件列表异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionDataResponse(e));
        }
    }

    /**
     * @Description 分页查询上传普通文件列表
     * @Param
     * @return
     * @Author coder_bao
     * @Date 2018/9/28 14:54
     */
    @GetMapping("/queryUploadFileList")
    @ApiOperation(value = "分页查询上传文件列表", notes = "分页查询上传文件列表")
    public ResponseEntity queryUploadFileList(@RequestParam(required = false) String companyCode,@RequestParam(required = true) int pageIndex,@RequestParam(required = true) int pageSize){
        LoggerCommon.info(this.getClass(),"分页查询上传文件列表");
        try{
            List<UploadFileEntity> fileLists = uploadFileToMongoService.listFilesByPage(companyCode,pageIndex,pageSize);
            LoggerCommon.info(this.getClass(),"成功分页查询上传文件列表");
            return  ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.successMessage"), ENTITY_NAME))
                    .body(ResponseLogMessageHandel.initSuccessResponseData(fileLists));
        }catch (Exception e){
            LoggerCommon.info(this.getClass(),"分页查询上传文件列表异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"query file page list exception",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionDataResponse(e));
        }
    }

    /**
     * @Description 分页查询上传大文件列表
     * @Param
     * @return
     * @Author coder_bao
     * @Date 2018/9/28 14:54
     */
    @GetMapping("/queryUploadCridFsFileList")
    @ApiOperation(value = "分页查询CridFs文件列表", notes = "分页查询CridFs文件列表")
    public ResponseEntity queryUploadCridFsFileList(@RequestParam(required = false) String companyCode,@RequestParam(required = true) int pageIndex,@RequestParam(required = true) int pageSize){
        LoggerCommon.info(this.getClass(),"分页查询上传Big File列表");
        try{
            List<UploadFileEntity> fileLists = uploadFileCridFsService.getUploadCridFsFileList(companyCode,pageIndex,pageSize);
            LoggerCommon.info(this.getClass(),"成功分页查询上传Big File列表");
            return  ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.successMessage"), ENTITY_NAME))
                    .body(ResponseLogMessageHandel.initSuccessResponseData(fileLists));
        }catch (Exception e){
            LoggerCommon.info(this.getClass(),"分页查询上传Big File列表异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"query file page list exception",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionDataResponse(e));
        }
    }

    /**
     * @Description 删除文件
     * @Param id 文件id,不是cridfsid
     * @return
     * @Author coder_bao
     * @Date 2018/9/28 14:34
     */
    @PostMapping("/deleteFileCridFsById")
    @ApiOperation(value = "CridFs删除文件", notes = "CridFs删除文件")
    public ResponseEntity deleteFileCridFsById(@RequestBody(required = true) String id){
        LoggerCommon.info(this.getClass(),"删除CridFs文件");
        try{
            uploadFileCridFsService.removeFile(id);
            LoggerCommon.info(this.getClass(),"成功删除CridFs文件");
            return  ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.successMessage"), ENTITY_NAME))
                    .body(ResponseLogMessageHandel.initSuccessResponseData(null));
        }catch (Exception e){
            LoggerCommon.info(this.getClass(),"删除CridFs文件异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionDataResponse(e));
        }
    }

    /**
     * @Description 删除文件
     * @Param id 文件id,不是cridfsid
     * @return
     * @Author coder_bao
     * @Date 2018/9/28 14:34
     */
    @PostMapping("/deleteFileById")
    @ApiOperation(value = "删除文件", notes = "删除文件")
    public ResponseEntity deleteFileById(@RequestBody(required = true) String id){
        LoggerCommon.info(this.getClass(),"删除文件");
        try{
            uploadFileToMongoService.removeFile(id);
            LoggerCommon.info(this.getClass(),"成功删除文件");
            return  ResponseEntity.ok().headers(HeaderUtil.createAlert(LocaleMessage.get("message.system.successMessage"), ENTITY_NAME))
                    .body(ResponseLogMessageHandel.initSuccessResponseData(null));
        }catch (Exception e){
            LoggerCommon.info(this.getClass(),"删除文件异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionDataResponse(e));
        }
    }

    /**
     * 下载文件
     *
     * @param id
     * @return
     */
    @GetMapping("/downFileCridFs/{id}")
    @ApiOperation(value = "CridFs文件下载", notes = "CridFs文件下载")
    public ResponseEntity downFileCridFs(@PathVariable String id){
        LoggerCommon.info(this.getClass(),"CridFs文件下载开始");
        try {
            UploadFileEntity file = uploadFileCridFsService.getFileById(id);
            if (file != null) {
                GridFSDBFile gridFSDBFile = uploadFileCridFsService.getFileContent(id);
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                gridFSDBFile.writeTo(os);
                LoggerCommon.info(this.getClass(),"CridFs文件下载成功");
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getRealName() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                        .header(HttpHeaders.CONTENT_LENGTH, file.getSize() + "")
                        .header("Connection", "close")
                        .body(os.toByteArray());
            } else {
                LoggerCommon.info(this.getClass(),"CridFs文件下载失败，未找到文件");
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"file not found!",
                        LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionResponseData());
            }

        }catch (Exception e){
            LoggerCommon.info(this.getClass(),"CridFs下载文件异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"file download exception",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionDataResponse(e));
        }
    }
    @GetMapping("/downFile/{id}")
    @ApiOperation(value = "文件下载", notes = "文件下载")
    public ResponseEntity downFile(@PathVariable String id){
        LoggerCommon.info(this.getClass(),"文件下载开始");
        try {
            UploadFileEntity file = uploadFileToMongoService.getFileById(id);
            if (file != null) {
                LoggerCommon.info(this.getClass(),"文件下载成功");
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + file.getRealName() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                        .header(HttpHeaders.CONTENT_LENGTH, file.getSize() + "")
                        .header("Connection", "close")
                        .body(file.getContent());

            } else {
                LoggerCommon.info(this.getClass(),"文件下载失败，未找到文件");
                return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"file not found!",
                        LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionResponseData());
            }
        }catch (Exception e){
            LoggerCommon.info(this.getClass(),"下载文件异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"file download exception",
                    LocaleMessage.get("message.system.errorMessage"))).body(ResponseLogMessageHandel.initExceptionDataResponse(e));
        }
    }

}
