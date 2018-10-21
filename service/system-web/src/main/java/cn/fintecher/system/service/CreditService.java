package cn.fintecher.system.service;

import cn.fintecher.system.model.requestModel.CreditRequest;

import java.util.Map;

/**
 * 授信管理
 */
public interface CreditService {
    /**
     * 查询授信列表
     * @param creditRequest
     * @return
     */
    Map getQuotaList(CreditRequest creditRequest) throws Exception;

    Map getCreditDetail(String custId) throws Exception;
}
