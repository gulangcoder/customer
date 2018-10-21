package cn.fintecher.app.service.order;

import cn.fintecher.app.model.order.EntRepaymentPlan;
import cn.fintecher.app.model.order.EntRepaymentRecord;
import cn.fintecher.app.model.order.EntRepaymentRecordDetailed;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @还款表操作接口@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public interface RepaymentTableService {

    /**
     * 添加还款计划表
     * @param entRepaymentPlan
     * @return boolean
     */
    boolean addRepaymentPlan(EntRepaymentPlan entRepaymentPlan)throws Exception;

    /**
     * 更新还款计划表
     * @param entRepaymentPlan
     * @return boolean
     */
    boolean updateRepaymentPlan(EntRepaymentPlan entRepaymentPlan)throws Exception;

    /**
     * 删除还款计划表
     * @param entRepaymentPlan
     * @return boolean
     */
    boolean delRepaymentPlan(EntRepaymentPlan entRepaymentPlan)throws Exception;

    /**
     * 获取还款计划表
     * @param entRepaymentPlan
     * @return EntRepaymentPlan
     */
    EntRepaymentPlan getRepaymentPlan(EntRepaymentPlan entRepaymentPlan)throws Exception;

    /**
     * 获取还款计划表集合
     * @param entRepaymentPlan
     * @return List
     */
    List<EntRepaymentPlan> getRepaymentPlanList(EntRepaymentPlan entRepaymentPlan)throws Exception;




    /**
     * 添加还款流水表
     * @param repaymentRecord
     * @return boolean
     */
    boolean addRepaymentRecord(EntRepaymentRecord repaymentRecord)throws Exception;

    /**
     * 更新还款流水表
     * @param repaymentRecord
     * @return boolean
     */
    boolean updateRepaymentRecord(EntRepaymentRecord repaymentRecord)throws Exception;

    /**
     * 删除还款流水表
     * @param repaymentRecord
     * @return boolean
     */
    boolean delRepaymentRecord(EntRepaymentRecord repaymentRecord)throws Exception;

    /**
     * 获取还款流水表
     * @param repaymentRecord
     * @return EntRepaymentPlan
     */
    EntRepaymentRecord getRepaymentRecord(EntRepaymentRecord repaymentRecord)throws Exception;

    /**
     * 获取还款流水表集合
     * @param repaymentRecord
     * @return List
     */
    List<EntRepaymentRecord> getRepaymentRecordList(EntRepaymentRecord repaymentRecord)throws Exception;


    /**
     * 添加还款计划流水明细表
     * @param repaymentRecordDetailed
     * @return boolean
     */
    boolean addEntRepaymentRecordDetailed(EntRepaymentRecordDetailed repaymentRecordDetailed)throws Exception;


    /**
     * 获取还款计划流水明细表集合
     * @param repaymentRecordDetailed
     * @return List
     */
    List<EntRepaymentRecordDetailed> getEntRepaymentRecordDetailedList(EntRepaymentRecordDetailed repaymentRecordDetailed)throws Exception;

    /**
     * 逾期信息统计 (总次数,历史最大逾期天数)
     * @param orderIds
     * @return Map
     */
    Map getOverdueCount(String orderIds);

    //还款列表[后管]
    List<Map> getRepaymentRecordList(Map map);

    /**
     * 根据还款记录id查询还款详情
     * @param repaymentRecordId 还款记录id
     * @return
     */
    List getRepaymentDetail(String repaymentRecordId);
}
