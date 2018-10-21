package cn.fintecher.scheduler.service;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月20日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:hanxiaoxue
 * @version 修改历史:
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface SysParaService {

    List<Map> getCompanyCode();

    String selectByPrimaryKey(String paraName,String companyCode);

    int updateAccessToken(String paraName,String paraValue,String companyCode);
}
