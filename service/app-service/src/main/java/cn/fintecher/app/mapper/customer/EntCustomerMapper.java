package cn.fintecher.app.mapper.customer;

import cn.fintecher.app.model.customer.EntCustomer;
import cn.fintecher.app.model.customer.EntCustomerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntCustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntCustomer record);

    int insertSelective(EntCustomer record);

    EntCustomer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntCustomer record);

    int updateByPrimaryKey(EntCustomer record);

    List<EntCustomer> selectCustomerByMap(Map map);

    int updatePasswordByMap(Map map);

    EntCustomerInfo selectCustomerInfoByMap(Map map);

    int updateCustomer(EntCustomer customer);

    List<Map> selectEntCustomersByMap(Map map);
}