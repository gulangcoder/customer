package cn.fintecher.app.service.order;

import cn.fintecher.app.model.order.EntCustQuota;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Title :
 * Description : @风控交互接口@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public interface RiskService {

    /**
     * 调取风控接口 获取客户额度
     * @param quota
     * @return Map
     */
    Map<String,Object> getRiskQuota(EntCustQuota quota)throws Exception;


    /**
     * 获取客户额度 风控接口回调借口
     * 1.根据返回结果更新额度信息
     * 2.判断是否需要发人审
     * @param request
     * @return Map
     */
    Map<String,Object> riskQuotaCallback(HttpServletRequest request)throws Exception;

}
