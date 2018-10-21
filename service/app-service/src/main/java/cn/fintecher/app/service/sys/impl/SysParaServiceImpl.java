package cn.fintecher.app.service.sys.impl;

import cn.fintecher.app.mapper.sys.SysParaMapper;
import cn.fintecher.app.model.sys.SysPara;
import cn.fintecher.app.service.sys.SysParaService;
import cn.fintecher.util.redis.RedisKeyConstants;
import cn.fintecher.util.redis.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @类注释说明写在此处@
 * Create on : 2018年09月19日
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
    public String getParaByCompanyAndKey(String companyCode, String key)throws Exception {
        String paraValue = "";
        Map sysPara = RedisUtil.get(RedisKeyConstants.SYS_PARA + key + "_" + companyCode);
        if (sysPara == null) {
            Map param = new HashMap();
            param.put("paraName",key);
            param.put("companyCode",companyCode);
            List<SysPara> sysParaList = sysParaMapper.findList(param);
            paraValue = sysParaList.get(0).getParaValue();
        }else{
            paraValue = String.valueOf(sysPara.get("paraValue"));
        }
        return paraValue;
    }
}