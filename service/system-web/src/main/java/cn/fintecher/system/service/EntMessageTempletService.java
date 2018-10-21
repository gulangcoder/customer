package cn.fintecher.system.service;

import cn.fintecher.system.model.EntMessageTemplet;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月04日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface EntMessageTempletService {

    /**
     * 查询数据列表
     * @param companyCode
     * @param msgType
     * @param title
     * @param status
     * @return
     */
    List<Map> getMsgTemplList(String companyCode, String msgType, String title, Short status)throws Exception;


    /**
     * 根据主键查询
     * @param id
     * @return
     */
    EntMessageTemplet getMsgTemplById(String id)throws Exception;


    /**
     * 保存
     * @param entMessageTemplet
     * @return
     */
    Map saveMessageTemplet(EntMessageTemplet entMessageTemplet)throws Exception;


    /**
     * 修改
     * @param entMessageTemplet
     * @return
     */
    Map updateMessageTemplet(EntMessageTemplet entMessageTemplet)throws Exception;


    /**
     * 修改状态
     * @param data
     * @return
     */
    Map updateStatus(Map data)throws Exception;

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    EntMessageTemplet getMsgTemplDetailById(String id)throws Exception;

    List<Map> getTempDetailList(Map param);
}
