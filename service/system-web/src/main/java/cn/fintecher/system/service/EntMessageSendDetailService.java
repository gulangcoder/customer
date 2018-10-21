package cn.fintecher.system.service;

import cn.fintecher.common.sms.EntMessageSendDetail;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface EntMessageSendDetailService {

    /**
     * 查询数据列表
     * @param map
     * @return
     */
    List<Map> getDetailList(Map map)throws Exception;

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    EntMessageSendDetail getDetailById(String id)throws Exception;






    Map sendMessage(EntMessageSendDetail entMessageSendDetail)throws Exception;


    List<EntMessageSendDetail> getMsgTemplListOfApp(Map param) throws Exception;

    EntMessageSendDetail getMsgTemplDetailOfAppById(String id)throws Exception;
}
