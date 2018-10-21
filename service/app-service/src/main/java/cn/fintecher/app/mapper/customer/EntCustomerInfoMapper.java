package cn.fintecher.app.mapper.customer;

import cn.fintecher.app.model.customer.EntCustomerInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface EntCustomerInfoMapper {
    int deleteByPrimaryKey(String custId);

    int insert(EntCustomerInfo record);

    int insertSelective(EntCustomerInfo record);

    EntCustomerInfo selectByPrimaryKey(String custId);

    int updateByPrimaryKeySelective(EntCustomerInfo record);

    int updateByPrimaryKey(EntCustomerInfo record);

    Integer customerInfoMapper(Map map);
    int updateCustomerAppAliasById(Map map);
}