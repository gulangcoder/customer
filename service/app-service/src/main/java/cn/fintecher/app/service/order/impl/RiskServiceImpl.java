package cn.fintecher.app.service.order.impl;

import cn.fintecher.app.model.order.EntCustQuota;
import cn.fintecher.app.service.order.RiskService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Title :* 风控交互接口实现*
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 * @Description: * *
 * Create on : 2018/9/17 14:24
 * Copyright (C) zw.FinTec
 */
@Service
public class RiskServiceImpl implements RiskService {


    /**
     * 调取风控接口 获取客户额度
     * @param quota
     * @return Map
     */
    @Override
    public Map<String,Object> getRiskQuota(EntCustQuota quota)throws Exception{
        Map<String,Object>resultMap=new HashedMap();
        resultMap.put("resultState",true);
        //TODO: 2018/9/17  缺少规则  根据性别设定金额
        resultMap.put("initialAmount",12000);
        resultMap.put("refuseCode","200");
        return resultMap;
    }


    /**
     * 获取客户额度 风控接口回调借口
     * 1.根据返回结果更新额度信息
     * 2.判断是否需要发人审
     * @param request
     * @return Map
     */
    @Override
    public Map<String,Object> riskQuotaCallback(HttpServletRequest request)throws Exception{
        return null;
    }
}
