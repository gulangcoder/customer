package cn.fintecher.system.service;

import cn.fintecher.common.logger.SysWebLog;
import com.github.pagehelper.PageInfo;
import org.springframework.http.ResponseEntity;

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
public interface WebLogService {

    /**
     * 查询日志列表
     * @param map
     * @return
     */
    ResponseEntity<PageInfo> getLogList(Map map)throws Exception;


    /**
     * 根据主键查询错误信息
     * @param id
     * @return
     */
    ResponseEntity<String> getErrorMsgById(String id)throws Exception;
}
