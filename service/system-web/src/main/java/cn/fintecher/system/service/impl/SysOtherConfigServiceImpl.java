package cn.fintecher.system.service.impl;


import cn.fintecher.system.mapper.SysOtherConfigMapper;
import cn.fintecher.system.model.SysOtherConfig;
import cn.fintecher.system.service.SysOtherConfigService;
import cn.fintecher.util.CreateIDUtil;
import cn.fintecher.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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

@Service("SysOtherConfigServiceImpl")
public class SysOtherConfigServiceImpl implements SysOtherConfigService {

    @Autowired
    private SysOtherConfigMapper otherConfigMapper;


    @Override
    public List<SysOtherConfig> getOtherConfigList(Map map) {
//        map.put("type",1);
        return otherConfigMapper.findList(map);
    }

    @Override
    public List<SysOtherConfig> getOtherConfigByCompanyCode(String companyCode) throws Exception{
        return otherConfigMapper.getConfListByCompanyCode(companyCode);
    }



    @Override
    public SysOtherConfig getOtherConfById(String id) {
        return otherConfigMapper.selectByPrimaryKey(id);
    }


}