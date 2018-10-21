package cn.fintecher.system.service;

import cn.fintecher.system.model.requestModel.BorrowRequest;

import java.util.List;
import java.util.Map;

/**
 * 借款管理
 */
public interface BorrowService {
    /**
     * 借款信息
     * @param custId
     * @param orderId
     * @return
     */
    Map getBorrowInfo(String custId,String orderId);

    /**
     * 查询借款管理列表
     * @param borrowRequest
     * @return
     */
    Map getBorrowList(BorrowRequest borrowRequest);


    /**
     * 放款信息
     * @param custId
     * @param orderId
     * @return
     */
    Map getLoanInfo(String custId, String orderId);

    /**
     * 合同信息
     * @param orderId
     * @return
     */
    Map getContractinfo(String orderId) throws Exception;

    /**
     * 风控审核
     * 查询额度申请历史
     * @param custId
     * @return
     */
    List getQuotaList(String custId) throws Exception;
}
