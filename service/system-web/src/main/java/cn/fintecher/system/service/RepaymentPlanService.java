package cn.fintecher.system.service;

import cn.fintecher.system.model.RepaymentModel;

import java.util.List;

/**
 * Title :
 * Description : @还款计划@
 * Create on : 2018年09月05日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 * username:gaozhidong
 * @version 1.0
 * 修改人 修改日期 修改描述
 * -------------------------------------------<
 */
public interface RepaymentPlanService {

    //借款生成还款计划
    public RepaymentModel genRepaymentPlan(RepaymentModel repaymentModel) throws Exception;

    //生成还款计划跑批数据
    public RepaymentModel genRepaymentBatchResult(List<String> orderIdList) throws Exception;
}
