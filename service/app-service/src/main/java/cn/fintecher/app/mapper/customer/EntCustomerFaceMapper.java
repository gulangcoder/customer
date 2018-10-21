package cn.fintecher.app.mapper.customer;

import cn.fintecher.app.model.customer.EntCustomerFace;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface EntCustomerFaceMapper {
    int deleteByPrimaryKey(String id);

    int insert(EntCustomerFace record);

    int insertSelective(EntCustomerFace record);

    EntCustomerFace selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EntCustomerFace record);

    int updateByPrimaryKey(EntCustomerFace record);

    int deleteFaceByMap(Map dmap);
}