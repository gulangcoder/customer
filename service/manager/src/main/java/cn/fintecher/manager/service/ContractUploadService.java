package cn.fintecher.manager.service;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @合同管理上传服务@
 * Create on : 2018年09月07日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface ContractUploadService {


    /**
     * 上传
     * @param content word文件内容
     * @param serverList
     * @param uploadParam
     * @return
     */
    Map uploadContract(String content, List<String>serverList,Map uploadParam);
}
