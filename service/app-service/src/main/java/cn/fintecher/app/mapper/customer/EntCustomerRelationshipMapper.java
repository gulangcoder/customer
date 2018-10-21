package cn.fintecher.app.mapper.customer;

import cn.fintecher.app.model.customer.EntCustomerRelationship;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EntCustomerRelationshipMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntCustomerRelationship record);

    int insertSelective(EntCustomerRelationship record);

    EntCustomerRelationship selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntCustomerRelationship record);

    int updateByPrimaryKey(EntCustomerRelationship record);

    List<EntCustomerRelationship> selectCustomerRelationship(Map map);
}