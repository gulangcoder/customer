package cn.fintecher.common.upload.impl;

import cn.fintecher.common.upload.UploadFileService;
import cn.fintecher.util.uploadFile.util.UploadFileToOssUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Title :
 * Description : @阿里云上传下载@
 * Create on : 2018年06月04日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:chengrui
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------<
 */
@Service
public class UploadFileToOss implements UploadFileService {

    @Override
    public Map uploadFile(Object... objs) {
        Map map = new HashMap();
        try {
            map = UploadFileToOssUtil.getFileOss((HttpServletRequest) objs[0], String.valueOf(objs[1]), String.valueOf(objs[2]), String.valueOf(objs[3]),String.valueOf(objs[4]), String.valueOf(objs[5]), String.valueOf(objs[6]), String.valueOf(objs[7]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public String downloadFile(Object... objs) {
        String fileName = UploadFileToOssUtil.saveUrlAs(String.valueOf(objs[0]), String.valueOf(objs[1]), String.valueOf(objs[2]), String.valueOf(objs[3]));
        return fileName;
    }
}
