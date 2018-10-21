package cn.fintecher.manager.service;

import java.util.Map;

/**
 * Title :
 * Description : @支付宝支付管理@
 * Create on : 2018年09月11日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:gaozhidong
 * @version 1.0
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface AlipayService {
    /**
     * 支付宝支付(SDK版本)
     * @param map
     * @return
     */
    Map alipaySDKPayment(Map<String,Object> map) throws Exception;

}
