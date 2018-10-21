package cn.fintecher.manager.service;

import java.util.Map;

/**
 * Title :
 * Description : @微信支付管理@
 * Create on : 2018年09月11日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:gaozhidong
 * @version 1.0
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface WeChatPayService {

    /**
     * 微信支付
     * @param map
     * @return
     */
    Map WeChatPaySdk(Map<String,Object> map) throws Exception;
}
