package cn.fintecher.app.service.order;

import cn.fintecher.app.model.order.EntRepaymentPlan;
import cn.fintecher.app.model.order.EntRepaymentRecord;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @还款处理接口@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public interface RepaymentService {

    /**
     * 还款操作
     * 1.效验还款记录中的还款计划信息
     * 2.计算还款金额
     * 3.更新还款计划信息
     * 4.区分主动还款、按期还款
     * 5.添加还款记录及明细信息
     * 6.调取第三方代付接口
     * @param repaymentRecord 还款记录
     * @return Map
     */
    Map<String,Object> repayment(EntRepaymentRecord repaymentRecord)throws Exception;

    /**
     * 调用第三方代扣接口
     * @param repaymentRecord
     * @return Map
     */
    Map<String,Object> repaymentWithhold (EntRepaymentRecord repaymentRecord)throws Exception;

    /**
     * 第三方代扣接口回调接口
     * 1.更新还款计划
     * 2.更新还款流水
     * 3.判断是订单是否结清（更新订单信息、释放额度）
     * @param request
     * @return repaymentRecord
     */
    Map<String,Object> repaymentWithholdCallback (HttpServletRequest request) throws Exception;

    /**
     * 还款计划批处理
     * 1.获取未结清订单（获取还款计划表）
     * 2.计算还款计划罚息、违约金金额（添加还款计划批处理表）
     * 3.判断是订单是否结清（更新订单信息、释放额度）
     * @return repaymentRecord
     */
    Map<String,Object> repaymentBatch ()throws Exception;

    /**
     * 根据订单id查询已还本金,利息
     * @param orderId
     * @return
     */
    Map getRepaidSum(String orderId);
    /**
     * 根据订单id查询应还本金,利息
     * @param orderId
     * @return
     */
    Map getPlanSum(String orderId);

    /**
     * 根据订单id查询逾期期数list
     * @param orderId
     * @return
     */
    List<Map> getRepaymentInfo(String orderId);

    /**
     * 获取客户还款记录
     * @param custId
     * @return
     */
    List<EntRepaymentRecord> getRepaymentRecord(String custId) throws Exception;

    /**
     * 获取客户还款记录明细
     * @param recordId
     * @return
     */
    List<EntRepaymentPlan> getRepaymentRecordDetailed(String recordId) throws Exception;

}
