package cn.fintecher.system.service;


import cn.fintecher.system.model.SysOtherConfig;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年07月16日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface SysOtherConfigService {

    /**
     * 根据数据列表
     * @param map
     * @return
     */
    List<SysOtherConfig> getOtherConfigList(Map map);

    /**
     * 根据企业编码查询 三方列表
     * @param companyCode
     * @return
     */
    List<SysOtherConfig> getOtherConfigByCompanyCode(String companyCode)throws Exception;


    /**
     * 根据id查询
     * @param id
     * @return
     */
    SysOtherConfig getOtherConfById(String id);
}
