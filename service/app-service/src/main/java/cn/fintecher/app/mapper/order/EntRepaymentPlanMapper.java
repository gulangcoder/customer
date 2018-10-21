package cn.fintecher.app.mapper.order;


import cn.fintecher.app.model.order.EntRepaymentPlan;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntRepaymentPlanMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntRepaymentPlan record);

    int insertSelective(EntRepaymentPlan record);

    EntRepaymentPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntRepaymentPlan record);

    int updateByPrimaryKey(EntRepaymentPlan record);

    /**
     * 逾期信息统计 (总次数,历史最大逾期天数)
     * @param orderIds
     * @return Map
     */
    Map getOverdueCount(String orderIds);

    /**
     * 批量生成还款计划
     * @param recordList
     * @return int
     */
    int insertBatch(List<EntRepaymentPlan> recordList);

    /**
     * 根据订单id查询逾期期数list
     * @param orderId
     * @return
     */
    List<Map> getRepaymentInfo(String orderId);

    /**
     * 获取订单所有还款记录
     * @param entRepaymentPlan
     * @return List
     * */
    List<EntRepaymentPlan> getEntRepaymentPlanList(EntRepaymentPlan entRepaymentPlan);

    /**
     * 批量修改
     * @param recordList
     * @return int
     */
    int updateBatch(List<EntRepaymentPlan> recordList);
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

    /***
     * 获取还款记录
     * @param entRepaymentPlan
     * @return
     */
    List<EntRepaymentPlan> getList(EntRepaymentPlan entRepaymentPlan);

    /***
     * 获取还款记录明细
     * @param map
     * @return
     */
    List<EntRepaymentPlan> getRepaymentRecordDetailed(Map<String,Object> map);

    /***
     * 获取逾期的还款计划
     * @param map
     * @return
     */
    List<EntRepaymentPlan> getOverdueEntRepaymentPlan(Map<String,Object> map);
}