package cn.fintecher.manager.service.impl;

import cn.fintecher.common.userinfo.UserContextUtil;
import cn.fintecher.manager.mapper.SysParaMapper;
import cn.fintecher.manager.service.ContractUploadService;
import cn.fintecher.manager.service.SysParaService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.uploadFile.util.UploadFileToOssUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月07日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
@Service
public class ContractUploadServiceImpl implements ContractUploadService {




    @Override
    public Map uploadContract(String content, List<String> serverList, Map uploadParam) {
        //临时保存路径
        String saveDir=UserContextUtil.getSession().getServletContext().getRealPath("/fintecher_file");
        saveDir+=CreateIDUtil.getId()+".pdf";
        File outFile = new File(saveDir);
        byte contentBytes [] = new byte[0];  //这里是必须要设置编码的，不然导出中文就会乱码。
        try {
//                    contentBytes = content.toString().getBytes("GBK");
//                    ByteArrayInputStream byteStream = new ByteArrayInputStream(contentBytes);
//                    POIFSFileSystem poifSystem = new POIFSFileSystem(); //暂时不知道什么意思
//                    DirectoryNode root = poifSystem.getRoot(); //同上
//                    root.createDocument("WordDocument",byteStream); //这个WordDocument 不可以进行修改 否则乱码
//                    FileOutputStream outStream = new FileOutputStream(outFile);
//                    poifSystem.writeFilesystem(outStream); //将流 输出到word文档上
//                    //调用oss上传
//                    String appurl=UploadFileToOssUtil.appOss(outFile,"hlt",bucketName, endpoint, accessKeyId, accessKeySecret);
//                    respMap.put("url",appurl);
//                    byteStream.close();
//                    outStream.close();
            contentBytes = content.toString().getBytes("GBK");
            ByteArrayInputStream byteStream = new ByteArrayInputStream(contentBytes);
            FileOutputStream outStream = new FileOutputStream(outFile);
            BaseFont bf = BaseFont.createFont( "STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);//创建字体
            Font font = new Font(bf,12);//使用字体
            Document document = new Document();
            PdfWriter.getInstance(document, outStream);
            document.open();
            document.add(new Paragraph(content.toString(),font));//引用字体
            document.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        for (String severType:serverList ){
            if(severType.equals("oss")){//oss上传
                Map  respMap =new HashMap();
                String bucketName= String.valueOf(uploadParam.get("bucketName"));
                String endpoint= String.valueOf(uploadParam.get("endpoint"));
                String accessKeyId= String.valueOf(uploadParam.get("accessKeyId"));
                String accessKeySecret= String.valueOf(uploadParam.get("accessKeySecret"));
                //上传
                String appurl=UploadFileToOssUtil.appOss(outFile,"saas",bucketName,endpoint,accessKeyId,accessKeySecret);
                respMap.put("url",appurl);
                respMap.put("flag",true);
                return respMap;
            }else if(severType.equals("ftp")){
                //TODO:文件服务器上传
            }else{
                //TODO:其他
            }
        }
        return null;
    }
}