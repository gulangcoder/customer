package cn.fintecher.system.service.impl;

import cn.fintecher.common.logger.SysWebLog;
import cn.fintecher.system.client.ManagerFeignUtil;
import cn.fintecher.system.service.WebLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月11日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */

@Service
public class WebLogServiceImpl implements WebLogService {


    @Autowired
    RestTemplate restTemplate;
    /**
     * 查询日志列表
     * @param map
     * @return
     */
    @Override
    public ResponseEntity<PageInfo> getLogList(Map map)throws Exception {
        ResponseEntity<PageInfo> list= restTemplate.getForEntity(ManagerFeignUtil.MANAGER_API_SYSWEBLOG_GETLOGLIST_API
                +"?pageIndex={pageIndex}&pageSize={pageSize}&loginAccount={loginAccount}&companyCode={companyCode}&method={method}&terminalType={terminalType}" +
                "&operateType={operateType}&status={status}&exceptionCode={exceptionCode}&startTime={startTime}&endTime={endTime}" ,PageInfo.class,map);
        return list;
    }

    /**
     * 根据主键查询错误信息
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<String> getErrorMsgById(String id)throws Exception {
        Map param = new HashMap();
        param.put("id",id);
        return  restTemplate.getForEntity(ManagerFeignUtil.MANAGER_API_SYSWEBLOG_GETERRORMSG_API+"?id={id}" ,String.class,param);
    }
}