package cn.fintecher.system.controller;

import cn.fintecher.common.contract.EntContractBookDetail;
import cn.fintecher.common.language.LocaleMessage;
import cn.fintecher.common.logger.LoggerCommon;
import cn.fintecher.common.response.ExceptionEnum;
import cn.fintecher.common.response.ExceptionStackMessage;
import cn.fintecher.common.response.ResponseInfo;
import cn.fintecher.common.response.ResponseLogMessageHandel;
import cn.fintecher.common.upload.impl.UploadFileToOss;
import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.common.userinfo.UserInfo;
import cn.fintecher.system.model.EntContractTemplet;
import cn.fintecher.system.service.EntContractBookDetailService;
import cn.fintecher.system.service.EntContractTempletService;
import cn.fintecher.system.service.SysParaService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.HeaderUtil;
import cn.fintecher.util.UrlFilesToZip;
import cn.fintecher.util.uploadFile.util.UploadFileToOssUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Title :
 * Description : @合同管理@
 * Create on : 2018年09月04日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@RestController
@RequestMapping("/api/entContractTempl")
@Api(value = "/api/entContractTempl", description = "合同管理")
@Slf4j
public class EntContractTempletController {

    private static final String ENTITY_NAME = "entContractTempl";


    @Autowired
    private EntContractTempletService entContractTempletService;

    @Autowired
    private EntContractBookDetailService entContractBookDetailService;

    @Autowired
    private UploadFileToOss uploadFileToOss;

    @Autowired
    private SysParaService sysParaService;

    @ApiOperation(value = "查询合同模板数据列表", notes = "查询合同模板数据列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "合同模板名称", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "contractType", value = "合同模板类型", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "status", value = "状态(1启用;0停用)", dataType = "String", paramType = "query", required = false),
    })
    @GetMapping("/getContractTempletList")
    public ResponseEntity getContractTempletList(@RequestParam(value = "pageSize") Integer pageSize,
                                                 @RequestParam(value = "pageIndex")Integer pageIndex,
                                                 @RequestParam(value = "title",required = false)String title,
                                                 @RequestParam(value = "contractType" ,required = false) String contractType,
                                                 @RequestParam(value = "status",required = false)Short status){
        UserInfo userInfo = UserContextUtil.getUserInfo();
        try {
            PageHelper.startPage(pageIndex, pageSize, true);
            List<Map> contractList = entContractTempletService.getContractTemplList(userInfo.getCompanyCode(), title, status, contractType);
            PageInfo pageInfo = new PageInfo<>(contractList);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询合同模板数据列表异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "根据主键查询合同模板信息", notes = "根据主键查询合同模板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getContractTempletById")
    public ResponseEntity getContractTempletById(@RequestParam String id){
        try {
            Map map = entContractTempletService.getConractTemplById(id);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(map));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"根据主键查询合同模板信息异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "保存合同模板信息", notes = "保存合同模板信息")
    @PostMapping("/saveContractTemplet")
    public ResponseEntity saveContractTemplet(@RequestBody EntContractTemplet entContractTemplet){
        //创建人
        entContractTemplet.setCreateUser(UserContextUtil.getUserInfo().getAccount());
        entContractTemplet.setCompanyCode(UserContextUtil.getUserInfo().getCompanyCode());
        String logmsg = null ;
        try {
            Map respMap = entContractTempletService.saveContractTemplet(entContractTemplet);
            boolean flag = (boolean) respMap.get("flag");
            logmsg=LocaleMessage.get(respMap.get("msg").toString());
            if (flag) {
                LoggerCommon.info(this.getClass(),"保存合同模板信息成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);

            }
            LoggerCommon.info(this.getClass(),"保存合同模板信息失败："+logmsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.system.save.fail");
            LoggerCommon.info(this.getClass(),"保存合同模板信息异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    @ApiOperation(value = "修改合同模板信息", notes = "修改合同模板信息")
    @PostMapping("/updateContractTemplet")
    public ResponseEntity updateContractTemplet(@RequestBody EntContractTemplet entContractTemplet){
        //修改人
        entContractTemplet.setUpdateUser(UserContextUtil.getUserInfo().getAccount());
        entContractTemplet.setCompanyCode(UserContextUtil.getUserInfo().getCompanyCode());
        String logmsg = null ;
        try {
            Map respMap = entContractTempletService.updateContractTemplet(entContractTemplet);
            boolean flag = (boolean) respMap.get("flag");
            logmsg=LocaleMessage.get(respMap.get("msg").toString());
            if (flag) {
                LoggerCommon.info(this.getClass(),"修改合同模板信息成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            }
            LoggerCommon.info(this.getClass(),"修改合同模板信息失败："+logmsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改合同模板信息异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    /**
     * 修改合同模板信息状态
     * @param data {id:,status:}
     * @return
     */
    @ApiOperation(value = "修改合同模板信息状态", notes = "修改合同模板信息状态")
    @PostMapping("/updateContractTempletStatus")
    public ResponseEntity updateContractTempletStatus(@RequestBody Map data){
        data.put("updateUser",UserContextUtil.getUserInfo().getAccount());
        String logmsg =null;
        try {
            Map respMap = entContractTempletService.updateStatus(data);
            boolean flag = (boolean) respMap.get("flag");
            logmsg=LocaleMessage.get(respMap.get("msg").toString());
            if (flag) {
                LoggerCommon.info(this.getClass(),"修改合同模板信息状态成功");
                return ResponseEntity.ok().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
            }
            LoggerCommon.info(this.getClass(),"修改合同模板信息状态失败："+logmsg);
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert(logmsg, ENTITY_NAME)).body(null);
        }catch (Exception e){
            logmsg = LocaleMessage.get("message.system.update.fail");
            LoggerCommon.info(this.getClass(),"修改合同模板信息状态异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "查询合同签订明细列表", notes = "查询合同签订明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "当前页码", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "contractBookNo", value = "合同编号", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "contractTitle", value = "合同名称", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "contractType", value = "合同类型", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "customerName", value = "客户名称", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "borrowNo", value = "贷款编号", dataType = "String", paramType = "query", required = false),
            @ApiImplicitParam(name = "productName", value = "产品名称", dataType = "String", paramType = "query", required = false),
    })
    @GetMapping("/getContractBookDetailList")
    public ResponseEntity getContractBookDetailList(@RequestParam(value = "pageSize") Integer pageSize,
                                                    @RequestParam(value = "pageIndex")Integer pageIndex,
                                                    @RequestParam(value = "contractBookNo",required = false)String contractBookNo,
                                                    @RequestParam(value = "contractTitle",required = false)String contractTitle ,
                                                    @RequestParam(value = "contractType",required = false)String contractType,
                                                    @RequestParam(value = "customerName",required = false)String customerName,
                                                    @RequestParam(value = "borrowNo",required = false)String borrowNo,
                                                    @RequestParam(value = "productName",required = false)String productName){
        UserInfo userInfo = UserContextUtil.getUserInfo();
        Map param = new HashMap();
        param.put("companyCode",userInfo.getCompanyCode());
        param.put("contractBookNo",contractBookNo);
        param.put("contractTitle",contractTitle);
        param.put("contractType",contractType);
        param.put("customerName",customerName);
        param.put("borrowNo",borrowNo);
        param.put("productName",productName);
        try {
            PageHelper.startPage(pageIndex, pageSize, true);
            List<Map> list = entContractBookDetailService.getList(param);
            PageInfo pageInfo = new PageInfo(list);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(pageInfo));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"查询合同签订明细列表异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }



    @ApiOperation(value = "根据主键查询合同签订明细", notes = "根据主键查询合同签订明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", dataType = "String", paramType = "query", required = true),
    })
    @GetMapping("/getConractBookDetailById")
    public ResponseEntity getConractBookDetailById(@RequestParam String id){
        try {
            EntContractBookDetail entContractBookDetail = entContractBookDetailService.getById(id);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(entContractBookDetail.getContent()));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"根据主键查询合同签订明细异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


    @ApiOperation(value = "下载", notes = "下载")
    @GetMapping("/getContractDownUrl")
    public ResponseEntity getContractDownUrl(@RequestParam String  data){
        String[]ids = data.split(",");
        if(null==ids||ids.length<1){
            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("message.system.request.param.exception",ENTITY_NAME)).body(null);
        }
        try {
            List<String> cList = entContractBookDetailService.getListByIds(Arrays.asList(ids));
            if (cList == null || cList.size() < 1 || cList.size() < ids.length) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("message.contract.bookFail.not.down", ENTITY_NAME)).body(null);
            }
            String filename = new String(CreateIDUtil.getId().getBytes("UTF-8"), "ISO8859-1");//控制文件名编码
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(bos);
            UrlFilesToZip s = new UrlFilesToZip();
            int idx = 1;
            for (String oneFile : cList) {
                zos.putNextEntry(new ZipEntry("profile" + idx+".pdf"));
                byte[] bytes = s.getImageFromURL(oneFile);
                zos.write(bytes, 0, bytes.length);
                zos.closeEntry();
                idx++;
            }
            zos.close();
            String bucketName = sysParaService.getParaByCompanyAndKey(UserContextUtil.getUserInfo().getCompanyCode(),"oss_bucketName");
            String endpoint = sysParaService.getParaByCompanyAndKey(UserContextUtil.getUserInfo().getCompanyCode(),"oss_endpoint");
            String accessKeyId = sysParaService.getParaByCompanyAndKey(UserContextUtil.getUserInfo().getCompanyCode(),"oss_accessKeyId");
            String accessKeySecret = sysParaService.getParaByCompanyAndKey(UserContextUtil.getUserInfo().getCompanyCode(),"oss_accessKeySecret");
            File file = new File(UserContextUtil.getHttpServletRequest().getSession().getServletContext().getRealPath("/temp")+filename+".zip");
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bos.toByteArray());
            FileOutputStream os = new FileOutputStream(file);
            os.write(bos.toByteArray());
            String appurl=upload(file,bucketName,endpoint,accessKeyId,accessKeySecret);
            os.close();
            return ResponseEntity.ok().body(appurl);
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            LoggerCommon.info(this.getClass(),"下载合同签订明细异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }


//    @PostMapping("/exportWord")//@RequestBody Map data
//    public ResponseEntity exportWord() {
//        HttpServletRequest request=UserContextUtil.getHttpServletRequest();
////        List<String>ids = (List<String>) data.get("ids");
////        if(null==ids||ids.size()<1){
////            return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("传参异常",ENTITY_NAME)).body(null);
////        }
//        UserInfo userInfo = UserContextUtil.getUserInfo();
//        //获取根目录
//        String url = request.getSession().getServletContext().getRealPath("/fintecher_file");
//        String bucketName = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(),"oss_bucketName");
//        String endpoint = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(),"oss_endpoint");
//        String accessKeyId = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(),"oss_accessKeyId");
//        String accessKeySecret = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(),"oss_accessKeySecret");
//        try {
////            List<EntContractBookDetail>cList = entContractBookDetailService.getListByIds(ids);
////            for (EntContractBookDetail d :cList) {
//                String id = CreateIDUtil.getId();
//                //word内容
////                StringBuilder content=new StringBuilder("<html><body>").append(d.getContent()).append("</body></html>");
//                StringBuilder content=new StringBuilder("<html><body>").append("<p>aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</p>").append("</body></html>");
//                File outFile = new File("D:\\test.pdf");
//                byte contentBytes [] = content.toString().getBytes("GBK");  //这里是必须要设置编码的，不然导出中文就会乱码。
//                ByteArrayInputStream byteStream = new ByteArrayInputStream(contentBytes);
////                POIFSFileSystem poifSystem = new POIFSFileSystem(); //暂时不知道什么意思
////                DirectoryNode root = poifSystem.getRoot(); //同上
////                root.createDocument("WordDocument",byteStream); //这个WordDocument 不可以进行修改 否则乱码
//                FileOutputStream outStream = new FileOutputStream(outFile);
////                poifSystem.writeFilesystem(outStream); //将流 输出到word文档上
////                String appurl=upload(outFile,bucketName,endpoint,accessKeyId,accessKeySecret);
////                byteStream.close();
////                outStream.close();
//
////            outFile = new File("D:\\test.pdf");
////            outStream = new FileOutputStream(outFile);
//
////            }
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return null ;
//
//    }


    private String  upload(File file, String bucketName, String endpoint, String accessKeyId, String accessKeySecret) throws FileNotFoundException {
        String appUrl = UploadFileToOssUtil.appOss(file,"temp",bucketName, endpoint, accessKeyId, accessKeySecret);
        return appUrl;
    }


//    @PostMapping("/test")
//    public  ResponseEntity test(){
//        HttpServletRequest request = UserContextUtil.getHttpServletRequest();
//        UserInfo userInfo = UserContextUtil.getUserInfo();
//        //获取根目录
//        String url = request.getSession().getServletContext().getRealPath("/fintecher_file");
//        String bucketName = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(),"oss_bucketName");
//        String endpoint = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(),"oss_endpoint");
//        String accessKeyId = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(),"oss_accessKeyId");
//        String accessKeySecret = sysParaService.getParaByCompanyAndKey(userInfo.getCompanyCode(),"oss_accessKeySecret");
//        try {
//            StringBuilder content=new StringBuilder("").append("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
//            File outFile = new File("D:\\1.pdf");
//            byte contentBytes []  = content.toString().getBytes("GBK");
//            ByteArrayInputStream byteStream = new ByteArrayInputStream(contentBytes);
//            FileOutputStream outStream = new FileOutputStream(outFile);
//            BaseFont bf = BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H",
//                    BaseFont.NOT_EMBEDDED);//创建字体
//            Font font = new Font(bf,12);//使用字体
//            Document document = new Document();
//            PdfWriter.getInstance(document, outStream);
//            document.open();
//            document.add(new Paragraph(content.toString(),font));//引用字体
//            document.close();
//            String appurl=upload(outFile,bucketName,endpoint,accessKeyId,accessKeySecret);
//            return ResponseEntity.ok().body(appurl);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


    /**
     * 签订合同
     * @param entContractBookDetail
     * @return
     */
    @PostMapping("/saveBookContract")
    public ResponseEntity saveBookContract(@RequestBody EntContractBookDetail entContractBookDetail){
        try {
            Map respMap = entContractBookDetailService.generateBookContract(entContractBookDetail);
            return ResponseEntity.ok().body(ResponseLogMessageHandel.initSuccessResponseData(respMap));
        }catch(Exception e){
            String logmsg = LocaleMessage.get("message.contract.book.fail");
            LoggerCommon.info(this.getClass(),"签订合同异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }

    /**
     * @description: 获取协议
     * @param:
     * @return:
     * @auther: liangdeng
     * @date: 2018/9/20
     */
    @ApiOperation(value = "获取协议", notes = "获取协议")
    @PostMapping("/getProtocol")
    public ResponseEntity<ResponseInfo> getProtocol(@RequestBody Map map){
        log.info("获取协议");
        String ct = map.get("contractType").toString();
        String[] ctypes = ct.split(",");
        try {
            Map parm = new HashMap();
            parm.put("list",Arrays.asList(ctypes));
            parm.put("companyCode",map.get("companyCode").toString());
            List<EntContractTemplet> contractList = entContractBookDetailService.getProtocol(parm);
            if (contractList == null || contractList.size() == 0) {
                return ResponseEntity.badRequest().headers(HeaderUtil.createAlert("message.query.errorMessage", ENTITY_NAME)).body(null);
            }
            return ResponseEntity.ok().headers(HeaderUtil.createAlert("message.system.operation.success",
                    LocaleMessage.get("message.system.successMessage"))).body(ResponseLogMessageHandel.initSuccessResponseData(contractList));
        }catch (Exception e){
            String logmsg = LocaleMessage.get("message.query.errorMessage");
            log.info("获取协议异常："+ExceptionStackMessage.collectExceptionStackMsg(e));
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME,"system.server.exception",
                    logmsg)).body(ResponseLogMessageHandel.initResponseData(HttpStatus.BAD_REQUEST.value(), ExceptionEnum.EXCEPTION_CODE.code(),
                    LocaleMessage.get("system.server.exception"), ExceptionStackMessage.collectExceptionStackMsg(e),null));
        }
    }
}