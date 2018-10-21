package cn.fintecher.system.service;

import cn.fintecher.system.model.EntContractTemplet;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @@合同管理
 * Create on : 2018年09月04日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface EntContractTempletService {

    /**
     * 查询合同模板列表
     * @param companyCode 企业编码
     * @param title 标题
     * @param status 状态
     * @param contractType 模板类型
     * @return
     */
    List<Map> getContractTemplList(String companyCode,String title,Short status,String contractType)throws Exception;


    /**
     * 根据主键查询
     * @param id
     * @return
     */
    Map getConractTemplById(String id)throws Exception;


    /**
     * 保存合同模板
     * @param entContractTemplet
     * @return
     */
    Map saveContractTemplet(EntContractTemplet entContractTemplet)throws Exception;


    /**
     * 修改
     * @param entContractTemplet
     * @return
     */
    Map updateContractTemplet(EntContractTemplet entContractTemplet)throws Exception;


    /**
     * 修改状态
     * @param data
     * @return
     */
    Map updateStatus(Map data)throws Exception;
}
