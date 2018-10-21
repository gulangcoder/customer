package cn.fintecher.app.service.order;

import cn.fintecher.app.model.order.EntOrder;

import java.util.List;
import java.util.Map;

/**
 * Title :
 * Description : @订单管理接口@
 * Create on : 2018年09月15日
 * Copyright (C) zw.FinTec
 *
 * @author department:研发部
 *         username:gaozhidong
 * @version 修改历史:
 *          修改人 修改日期 修改描述
 *          -------------------------------------------
 */
public interface OrderService {

    /**
     * 获取客户订单集合
     * @param order 订单
     * @return List
     */
    List<EntOrder> getOrderList(EntOrder order)throws Exception;

    /**
     * 获取客户订单
     * @param order 订单
     * @return EntOrder
     */
    EntOrder getOrder(EntOrder order)throws Exception;

    /**
     * 添加订单
     * @param order
     * @return boolean
     */
    boolean addOrder (EntOrder order)throws Exception;

    /**
     * 修改订单
     * @param order
     * @return boolean
     */
    boolean updateOrder(EntOrder order)throws Exception;

    /**
     * 删除订单
     * @param order 订单id
     * @return boolean
     */
    boolean delOrder (EntOrder order) throws Exception;

    /**
     * 根据客户id查询借款总金额,借款总笔数
     * @param customerId 客户id
     * @return
     */
    Map getOrderSumByCustId(String customerId);

    //根据条件查询订单 关联还款情况
    List<Map> getOrdersInfo(Map map);


    /***
     * 客户订单查询
     * @param order
     * @return list
     */
    Map getRepaymentPlan(EntOrder order)throws Exception;


   // List<EntOrder> getOrders(EntOrder order);
    List<EntOrder> getOrderListByMap(Map map);

    /***
     * 客户还款中的所有订单
     * @param custId 客户id
     * @return list
     */
    Map<String,Object> getOrdersRepaymentPlan(String custId)throws Exception;

    /***
     * 验证订单结清
     * @param orderId
     * @return
     */
    boolean settleVerification(String orderId)throws Exception;
}
