package cn.fintecher.app.service.order;

import cn.fintecher.app.model.order.EntOrder;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Title :
 * Description : @借款管理接口@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public interface LoanService {

    /**
     * 借款申请提交
     * 1.效验客户额度、产品信息
     * 2.更新客户额度
     * 3.添加订单信息
     * 4.生成还款计划表信息
     * 5.调取三方代付代扣接口
     * @param order
     * @return Map
     */
    Map<String,Object> loanApply(EntOrder order)throws Exception;

    /**
     * 生成还款计划表
     * @param order
     * @return Map
     */
    Map<String,Object> genRepaymentPlan(EntOrder order)throws Exception;

    /**
     * 调用第三方代付放款接口
     * 1.更新订单表
     * @param order
     * @return Map
     */
    Map<String,Object> grantLoan(EntOrder order)throws Exception;

    /**
     * 第三方代付放款回调接口
     * 1.更新订单表
     * @param request
     * @return Map
     */
    void grantLoanCallback (HttpServletRequest request)throws Exception;

}
