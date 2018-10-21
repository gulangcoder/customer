package cn.fintecher.manager.controller;

import cn.fintecher.manager.service.ContractUploadService;
import cn.fintecher.util.uploadFile.util.UploadFileToOssUtil;
import io.swagger.annotations.Api;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/api/contractUpload")
@Api(value = "/api/contractUpload", description = "模板上传")
public class ContractUploadController {


    @Autowired
    private ContractUploadService contractUploadService;


    @PostMapping("/upload")
    public ResponseEntity<Map> upload(@RequestBody Map data){
        List<String> serverList = (List<String>) data.get("serverList");
        String content = (String) data.get("content");
        Map respMap = contractUploadService.uploadContract(content,serverList,data);
        return ResponseEntity.ok().body(respMap);
    }
}