package cn.fintecher.app.service.order;

import cn.fintecher.app.model.order.EntCustQuota;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @额度管理接口@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public interface QuotaService {

    /**
     * 查询额度申请历史
     * @param custId
     * */
    List<EntCustQuota> getQuotaList(String custId)throws Exception;

    /**
     * 获取额度信息
     * 根据客户id获取当前客户的额度信息 无额度返回null
     * @param quota 额度
     * @return Quota
     */
    EntCustQuota getQuota(EntCustQuota quota)throws Exception;

    /**
     * 额度信息效验
     * 1.效验额度状态、是否过期、是否大于等于借款金额
     * @param  quota 额度实体
     * @param  principal 借款金额
     * @return boolean
     * */
    Map<String,Object>  validation(EntCustQuota quota, BigDecimal principal)throws Exception;

    /**
     * 客户额度授信
     * 1.效验客户额度、产品信息
     * 2.添加额度信息
     * 3.授权协议
     * 4.调取风控接口
     * @param quota
     * @return Map
     */
    Map<String,Object> creditApply(EntCustQuota quota)throws Exception;

    /**
     * 风控回调
     * */
    boolean riskQuotaCallback(EntCustQuota quota)throws Exception;

    List<Map> getQuotaListByMap(Map map);
}
