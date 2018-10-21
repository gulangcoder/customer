package cn.fintecher.app.mapper.order;


import cn.fintecher.app.model.order.EntOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntOrderMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntOrder record);

    int insertSelective(EntOrder record);

    EntOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntOrder record);

    int updateByPrimaryKey(EntOrder record);

    Map getOrderSumByCustId(String customerId);
    List<EntOrder> getOrderListByMap(Map map);

    //借款管理列表
    List<Map> selectListByParam(Map map);

    /***
     * 获取客户订单集合
     * @param entOrder
     * @return
     */
    List<EntOrder>  getOrderList(EntOrder entOrder);
}