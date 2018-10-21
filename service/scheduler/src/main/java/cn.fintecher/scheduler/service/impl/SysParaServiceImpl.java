package cn.fintecher.scheduler.service.impl;

import cn.fintecher.scheduler.mapper.SysParaMapper;
import cn.fintecher.scheduler.service.SysParaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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
@Service
public class SysParaServiceImpl implements SysParaService {


    @Autowired
    private SysParaMapper sysParaMapper;

    @Override
    public List<Map> getCompanyCode(){
        return sysParaMapper.getCompanyCode();
    }

    @Override
    public String selectByPrimaryKey(String key, String companyCode) {
        Map sysParam=new HashMap();
        sysParam.put("paraName",key);
        sysParam.put("companyCode",companyCode);
        return sysParaMapper.selectByPrimaryKey(sysParam);
    }

    @Override
    public int updateAccessToken(String paraName, String paraValue, String companyCode){
        Map upParam = new HashMap();
        upParam.put("paraName",paraName);
        upParam.put("paraValue",paraValue);
        upParam.put("companyCode",companyCode);
        return sysParaMapper.updateAccessToken(upParam);
    }
}